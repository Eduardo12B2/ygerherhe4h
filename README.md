<div align="center">

# BiomeManager

[![Spigot](https://img.shields.io/badge/-Spigot-orange?logo=data%3Aimage%2Fx-icon%3Bbase64%2CAAABAAEAEBAQAAAAAAAoAQAAFgAAACgAAAAQAAAAIAAAAAEABAAAAAAAgAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAND%2FAOhGOgA%2F6OIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAiAAAAAAAAACIAAAAAAAAAIgAAAAAAAAAAAAAAAAAAABEAAAAzMQABEQAAARMzEBERARERETMxERAAAAARMzEAAAAAAAETMwAAAAAAABEwAAAAAAAAERAAAAAAAAABAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAD%2F%2BQAA%2F%2FkAAP%2F5AAD%2F8AAA%2BDAAAPAgAAAAAAAAAAEAAAADAADwDwAA%2FB8AAPwfAAD8HwAA%2Fj8AAP4%2FAADwBwAA)](https://www.spigotmc.org/resources/106419/)
[![Discord](https://img.shields.io/discord/306158221473742848.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/ERVgpfg)

</div>

© 2024-2025 CJCrafter, All rights reserved.

## Versoes Suportadas

| Minecraft | Status |
|-----------|--------|
| 1.19.4 | Suportado |
| 1.20.1 | Suportado |
| 1.20.2 | Suportado |
| 1.20.4 | Suportado |
| 1.20.6 | Suportado |
| 1.21.0 | Suportado |
| 1.21.4 | Suportado (v3.8.0+) |
| 1.21.11 | Suportado (v3.9.0+) |

## Changelog v3.9.0

### Correcoes Criticas
- **CORRIGIDO**: `NoClassDefFoundError: me/deecaad/core/lib/adventure/text/Component`
  - Removido relocate de `net.kyori` que conflitava com MechanicsCore 4.2.5+
  - MechanicsCore 4.2.5 usa Adventure nativo do Paper, nao mais shaded
- **CORRIGIDO**: `NullPointerException` no `onDisable()` quando plugin falha ao inicializar
- **CORRIGIDO**: Uso de API obsoleta `MechanicsCore.getPlugin().adventure`

### Novas Funcionalidades
- Suporte completo para **Paper 1.21.11** (NMS mapeamento R7)
- Novo modulo de compatibilidade `Biome_1_21_R7`

### Mudancas Tecnicas
- Atualizado paperweight-userdev para 2.0.0-beta.17
- Atualizado MechanicsCore para 4.2.5
- Atualizado ProtocolLib para 5.4.0
- Atualizado Adventure API para 4.17.0
- Migrado mensagens para usar `sender.sendMessage(Component)` nativo do Paper
- Adicionado null checks no `saveToConfig()` para prevenir crashes

### Dependencias Atualizadas
| Dependencia | Versao Anterior | Versao Atual |
|-------------|-----------------|--------------|
| MechanicsCore | 3.4.1 | 4.2.5 |
| ProtocolLib | 5.3.0 | 5.4.0 |
| paperweight-userdev | 1.7.7 | 2.0.0-beta.17 |
| Adventure API | 4.15.0 | 4.17.0 |

## Changelog v3.8.0

### Novas Funcionalidades
- Suporte completo para Minecraft 1.21.4
- Nova arquitetura de Biome Engine com interfaces de abstracao
- BiomeRegistryManager para registro thread-safe de biomas
- WorldGenAdapter para manipulacao de geracao de mundo

### Mudancas Tecnicas
- Refatorado sistema de registries para usar `lookupOrThrow()`
- Adaptado para mudancas no `RegistryFriendlyByteBuf`
- Corrigido acesso a campos frozen do MappedRegistry

### Migracoes Importantes
- O plugin agora usa `IdentityHashMap` para intrusive holders
- Metodos de som agora usam `location()` em vez de `getLocation()`
- BuiltInRegistries agora usa `getValue()` em vez de `get()`
