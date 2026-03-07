package me.cjcrafter.biomemanager.engine;

import me.cjcrafter.biomemanager.compatibility.BiomeWrapper;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Interface para adaptacao de geracao de mundo.
 * Permite manipulacao de biomas durante a geracao de chunks
 * sem dependencia direta de APIs internas do Minecraft.
 */
public interface WorldGenAdapter {

    /**
     * Injeta um bioma customizado na geracao de mundo.
     *
     * @param world   O mundo alvo
     * @param wrapper O wrapper do bioma a injetar
     * @return true se a injecao foi bem sucedida
     */
    boolean injectBiome(World world, BiomeWrapper wrapper);

    /**
     * Remove um bioma customizado da geracao de mundo.
     *
     * @param world   O mundo alvo
     * @param wrapper O wrapper do bioma a remover
     * @return true se a remocao foi bem sucedida
     */
    boolean removeBiome(World world, BiomeWrapper wrapper);

    /**
     * Verifica se um bioma esta presente na geracao de um mundo.
     *
     * @param world   O mundo para verificar
     * @param wrapper O wrapper do bioma
     * @return true se o bioma esta na geracao
     */
    boolean isBiomeInWorld(World world, BiomeWrapper wrapper);

    /**
     * Forca a regeneracao dos biomas em uma regiao.
     *
     * @param world O mundo alvo
     * @param minX  Coordenada X minima (em chunks)
     * @param minZ  Coordenada Z minima (em chunks)
     * @param maxX  Coordenada X maxima (em chunks)
     * @param maxZ  Coordenada Z maxima (em chunks)
     */
    void regenerateBiomes(World world, int minX, int minZ, int maxX, int maxZ);
}
