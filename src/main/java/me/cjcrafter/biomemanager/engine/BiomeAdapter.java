package me.cjcrafter.biomemanager.engine;

import me.cjcrafter.biomemanager.compatibility.BiomeWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

/**
 * Interface de abstracao para operacoes de biomas.
 * Permite implementacoes especificas por versao do Minecraft,
 * reduzindo a dependencia direta de NMS no codigo principal.
 */
public interface BiomeAdapter {

    /**
     * Cria um novo bioma customizado baseado em um bioma existente.
     *
     * @param key  A chave unica para o novo bioma
     * @param base O bioma base para copiar propriedades
     * @return O wrapper do bioma criado
     */
    BiomeWrapper createBiome(NamespacedKey key, BiomeWrapper base);

    /**
     * Obtem o wrapper do bioma em uma posicao especifica.
     *
     * @param block O bloco para verificar
     * @return O wrapper do bioma ou null se o chunk nao estiver carregado
     */
    BiomeWrapper getBiomeAt(Block block);

    /**
     * Define o bioma em um bloco especifico.
     *
     * @param block   O bloco alvo
     * @param wrapper O wrapper do bioma a definir
     * @return true se o bioma foi definido com sucesso
     */
    boolean setBiome(Block block, BiomeWrapper wrapper);

    /**
     * Registra um bioma customizado no registry do servidor.
     *
     * @param wrapper  O wrapper do bioma a registrar
     * @param isCustom Se o bioma e customizado (nao vanilla)
     */
    void registerBiome(BiomeWrapper wrapper, boolean isCustom);

    /**
     * Verifica se a versao atual suporta registro dinamico de biomas.
     *
     * @return true se registro dinamico e suportado
     */
    boolean supportsDynamicRegistration();

    /**
     * Obtem a versao do Minecraft suportada por este adapter.
     *
     * @return String da versao (ex: "1.21.4")
     */
    String getMinecraftVersion();
}
