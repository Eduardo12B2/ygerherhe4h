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
| 1.21.4+ | Suportado (v3.8.0+) |

## Changelog v3.8.0

### Novas Funcionalidades
- Suporte completo para Minecraft 1.21.4 (1.21.11)
- Nova arquitetura de Biome Engine com interfaces de abstracao
- BiomeRegistryManager para registro thread-safe de biomas
- WorldGenAdapter para manipulacao de geracao de mundo

### Mudancas Tecnicas
- Atualizado paperweight-userdev para 1.7.7
- Atualizado ProtocolLib para 5.3.0
- Atualizado WorldEdit para 7.3.9
- Refatorado sistema de registries para usar `lookupOrThrow()`
- Adaptado para mudancas no `RegistryFriendlyByteBuf`
- Corrigido acesso a campos frozen do MappedRegistry

### Migracoes Importantes
- O plugin agora usa `IdentityHashMap` para intrusive holders
- Metodos de som agora usam `location()` em vez de `getLocation()`
- BuiltInRegistries agora usa `getValue()` em vez de `get()`
