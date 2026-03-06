package me.cjcrafter.biomemanager.engine;

import me.cjcrafter.biomemanager.BiomeManager;
import me.cjcrafter.biomemanager.BiomeRegistry;
import me.cjcrafter.biomemanager.compatibility.BiomeCompatibilityAPI;
import me.cjcrafter.biomemanager.compatibility.BiomeWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gerenciador central de registros de biomas.
 * Fornece uma camada de abstracao sobre o BiomeRegistry para
 * operacoes mais avancadas e thread-safe.
 */
public class BiomeRegistryManager {

    private static BiomeRegistryManager instance;

    private final Set<NamespacedKey> pendingRegistrations;
    private final Set<NamespacedKey> failedRegistrations;

    private BiomeRegistryManager() {
        pendingRegistrations = ConcurrentHashMap.newKeySet();
        failedRegistrations = ConcurrentHashMap.newKeySet();
    }

    public static synchronized BiomeRegistryManager getInstance() {
        if (instance == null) {
            instance = new BiomeRegistryManager();
        }
        return instance;
    }

    /**
     * Registra um novo bioma customizado de forma segura.
     *
     * @param key  A chave do bioma
     * @param base O bioma base vanilla
     * @return O wrapper do bioma criado, ou null em caso de erro
     */
    public BiomeWrapper registerCustomBiome(NamespacedKey key, Biome base) {
        if (pendingRegistrations.contains(key)) {
            BiomeManager.inst().debug.warn("Biome '" + key + "' is already being registered");
            return null;
        }

        if (failedRegistrations.contains(key)) {
            BiomeManager.inst().debug.warn("Biome '" + key + "' previously failed to register");
            return null;
        }

        pendingRegistrations.add(key);

        try {
            BiomeWrapper baseWrapper = BiomeRegistry.getInstance().getBukkit(base);
            BiomeWrapper wrapper = BiomeCompatibilityAPI.getBiomeCompatibility().createBiome(key, baseWrapper);
            
            if (wrapper != null) {
                wrapper.register(true);
                BiomeManager.inst().debug.info("Successfully registered custom biome: " + key);
            }
            
            return wrapper;
        } catch (Exception ex) {
            failedRegistrations.add(key);
            BiomeManager.inst().debug.error("Failed to register biome: " + key + " - " + ex.getMessage());
            return null;
        } finally {
            pendingRegistrations.remove(key);
        }
    }

    /**
     * Verifica se um bioma pode ser registrado.
     *
     * @param key A chave do bioma
     * @return true se o bioma pode ser registrado
     */
    public boolean canRegister(NamespacedKey key) {
        return !pendingRegistrations.contains(key) 
            && !failedRegistrations.contains(key)
            && BiomeRegistry.getInstance().get(key) == null;
    }

    /**
     * Obtem todos os biomas que falharam ao registrar.
     *
     * @return Conjunto de chaves de biomas com falha
     */
    public Set<NamespacedKey> getFailedRegistrations() {
        return Set.copyOf(failedRegistrations);
    }

    /**
     * Limpa o cache de falhas para permitir nova tentativa.
     *
     * @param key A chave do bioma para limpar
     */
    public void clearFailure(NamespacedKey key) {
        failedRegistrations.remove(key);
    }

    /**
     * Limpa todas as falhas de registro.
     */
    public void clearAllFailures() {
        failedRegistrations.clear();
    }
}
