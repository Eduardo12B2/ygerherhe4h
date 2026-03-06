package me.cjcrafter.biomemanager.compatibility;

import com.comphenix.protocol.events.PacketEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.cjcrafter.biomemanager.BiomeManager;
import me.cjcrafter.biomemanager.BiomeRegistry;
import me.cjcrafter.biomemanager.events.BiomePacketEvent;
import me.deecaad.core.utils.LogLevel;
import me.deecaad.core.utils.ReflectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;

import java.lang.reflect.Field;

/**
 * Implementacao de compatibilidade para Minecraft 1.21.4 (mapeamento R3).
 * Adaptado para mudancas no sistema de registries e chunk sections.
 */
public class v1_21_R3 implements BiomeCompatibility {

    private static final Field chunkBiomesField;

    static {
        chunkBiomesField = ReflectionUtil.getField(ClientboundLevelChunkPacketData.class, byte[].class);
    }

    private final Registry<Biome> biomes;

    public v1_21_R3() {
        biomes = MinecraftServer.getServer().registryAccess().lookupOrThrow(Registries.BIOME);
        
        for (Biome biome : biomes) {
            ResourceLocation nmsKey = biomes.getKey(biome);
            if (nmsKey == null) {
                BiomeManager.inst().debug.error("Could not find key for: " + biome);
                continue;
            }

            try {
                NamespacedKey key = new NamespacedKey(nmsKey.getNamespace(), nmsKey.getPath());
                BiomeRegistry.getInstance().add(key, new BiomeWrapper_1_21_R3(biome));
            } catch (Throwable ex) {
                BiomeManager.inst().debug.error("Failed to load biome: " + nmsKey);
                BiomeManager.inst().debug.log(LogLevel.ERROR, ex.getMessage(), ex);
            }
        }
    }

    private Biome getBiome(NamespacedKey key) {
        return biomes.getValue(ResourceLocation.fromNamespaceAndPath(key.getNamespace(), key.getKey()));
    }

    @Override
    public BiomeWrapper createBiome(NamespacedKey key, BiomeWrapper base) {
        return new BiomeWrapper_1_21_R3(key, (BiomeWrapper_1_21_R3) base);
    }

    @Override
    public BiomeWrapper getBiomeAt(Block block) {
        ServerLevel world = ((CraftWorld) block.getWorld()).getHandle();

        BlockPos pos = new BlockPos(block.getX(), block.getY(), block.getZ());
        LevelChunk chunk = world.getChunkIfLoaded(pos);
        if (chunk == null)
            return null;

        Biome biome = world.getBiome(pos).value();
        ResourceKey<Biome> location = biomes.getResourceKey(biome).orElseThrow();
        NamespacedKey key = new NamespacedKey(location.location().getNamespace(), location.location().getPath());

        BiomeWrapper wrapper = BiomeRegistry.getInstance().get(key);
        if (wrapper == null)
            wrapper = new BiomeWrapper_1_21_R3(getBiome(key));

        return wrapper;
    }

    @Override
    public void handleChunkBiomesPacket(PacketEvent event) {
        ClientboundLevelChunkWithLightPacket packet = (ClientboundLevelChunkWithLightPacket) event.getPacket().getHandle();
        ClientboundLevelChunkPacketData chunkData = packet.getChunkData();

        int ySections = ((CraftWorld) event.getPlayer().getWorld()).getHandle().getSectionsCount();
        BiomeWrapper[] biomeArray = new BiomeWrapper[4 * 4 * 4 * ySections];
        LevelChunkSection[] sections = new LevelChunkSection[ySections];

        int counter = 0;
        
        // Obtem o buffer de leitura - em 1.21.4 usa RegistryFriendlyByteBuf
        RegistryFriendlyByteBuf sectionBuffer = new RegistryFriendlyByteBuf(
            chunkData.getReadBuffer(), 
            MinecraftServer.getServer().registryAccess()
        );
        
        for (int i = 0; i < ySections; i++) {
            sections[i] = new LevelChunkSection(this.biomes);
            sections[i].read(sectionBuffer);

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        Biome nmsBiome = sections[i].getNoiseBiome(x, y, z).value();
                        int id = this.biomes.getId(nmsBiome);
                        biomeArray[counter++] = BiomeRegistry.getInstance().getById(id);
                    }
                }
            }
        }

        BiomePacketEvent bukkitEvent = new BiomePacketEvent(event, biomeArray);
        Bukkit.getPluginManager().callEvent(bukkitEvent);

        int bufferSize = 0;
        counter = 0;
        for (LevelChunkSection section : sections) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        BiomeWrapper wrapper = biomeArray[counter++];
                        if (wrapper == null)
                            continue;

                        int id = wrapper.getId();
                        section.setBiome(x, y, z, Holder.direct(this.biomes.byId(id)));
                    }
                }
            }

            bufferSize += section.getSerializedSize();
        }

        byte[] bytes = new byte[bufferSize];
        ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
        buffer.writerIndex(0);
        RegistryFriendlyByteBuf friendlyByteBuf = new RegistryFriendlyByteBuf(
            buffer, 
            MinecraftServer.getServer().registryAccess()
        );
        
        for (LevelChunkSection section : sections) {
            section.write(friendlyByteBuf);
        }

        ReflectionUtil.setField(chunkBiomesField, chunkData, bytes);
    }
}
