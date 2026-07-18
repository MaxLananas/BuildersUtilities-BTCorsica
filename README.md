# BuildersUtilities — BTE Corse

[![License](https://img.shields.io/github/license/TehBrian/BuildersUtilities)](LICENSE)
[![Paper 1.21.10](https://img.shields.io/badge/Paper-1.21.10-blue)](https://papermc.io/)
[![Java 21](https://img.shields.io/badge/Java-21-orange)](https://adoptium.net/)

A curated bundle of builder tools, forked and adapted for **Build The Earth — Corse**.

---

## About

Fork of [TehBrian's BuildersUtilities][tehbrian-bu] (itself a fork of
[Arcaniax's Builder's Utilities][arcaniax-bu]), migrated to **Paper 1.21.10**
and **Java 21** with new features tailored for BTE servers.

[tehbrian-bu]: https://github.com/TehBrian/BuildersUtilities
[arcaniax-bu]: https://www.spigotmc.org/resources/builders-utilities.42361/

## Commands

| Command | Alias | Description |
|---|---|---|
| `/bu` | `/butils`, `/buildersutilities` | Open the ability menu |
| `/bu help` | | Show all available commands |
| `/bu rc` | | Reload chunks around you |
| `/bu reload` | | Reload plugin configuration |
| `/bloc` | | Open the special items menu |
| `/ac` | `/armorcolor`, `/acc` | Open the armor color creator |
| `/bn` | `/banner`, `/bc` | Open the banner creator |
| `/af [on\|off]` | `/advfly`, `/advancedfly` | Toggle advanced fly |
| `/nv [on\|off]` | `/nightvision` | Toggle night vision |
| `/nc [on\|off]` | `/noclip` | Toggle noclip |
| `/wp add <name>` | `/waypoint` | Save your current position |
| `/wp tp <name>` | | Teleport to a waypoint |
| `/wp list` | | List your waypoints |
| `/wp remove <name>` | | Delete a waypoint |
| `/build time <value>` | | Set time (DAY, NOON, SUNSET, NIGHT, MIDNIGHT, SUNRISE) |
| `/build weather <value>` | | Set weather (CLEAR, RAIN, THUNDER) |

## Features

- **Ability Menu** — Toggle noclip, night vision, advanced fly, iron door toggle, double slab break, glazed terracotta rotation
- **Special Items** — Access to command blocks, barriers, light blocks, debug stick, and more
- **Armor Color Creator** — RGB slider to create custom leather armor colors
- **Banner Creator** — Full banner designer with undo, randomize, and reset
- **Waypoints** — Personal waypoint system with persistent storage per player
- **World Interactions** — PlotSquared and WorldGuard restrictions respected
- **Configurable Messages** — All messages use MiniMessage syntax and support a custom prefix
- **Chunk Reloader** — Instantly resend surrounding chunks to your client

## Permissions

| Permission | Default | Description |
|---|---|---|
| `buildersutilities.ability` | true | Open the ability menu |
| `buildersutilities.bloc` | true | Open the special items menu |
| `buildersutilities.armorcolor` | true | Open the armor color creator |
| `buildersutilities.banner` | true | Open the banner creator |
| `buildersutilities.waypoint` | true | Use waypoints |
| `buildersutilities.build` | true | Use build helper commands |
| `buildersutilities.advancedfly` | op | Toggle advanced fly |
| `buildersutilities.noclip` | op | Toggle noclip |
| `buildersutilities.rc` | op | Reload chunks |
| `buildersutilities.reload` | op | Reload configuration |

See [plugin.yml](src/main/resources/plugin.yml) for the full list.

## Configuration

All configuration files are generated on first run:

- `config.yml` — Plugin settings (physics, redstone, explosions, etc.)
- `lang.yml` — All messages, fully customizable with MiniMessage
- `special.yml` — Items shown in the special items menu
- `waypoints.yml` — Per-player waypoint storage (auto-generated)

## Building

This project uses Gradle and requires **Java 21**.

```bash
./gradlew build
```

The built JAR can be found in `build/libs`.

## Migration

This plugin was migrated from the original BuildersUtilities with the following changes:

- Paper 1.21.10 support (was targeting unreleased API)
- Java 21 target (removed Java 25 dependencies)
- Replaced removed Bukkit tags (`Tag.GLAZED_TERRACOTTA`, `Tag.ITEMS_DYES`)
- Replaced removed NMS API (`ChunkPos.longKey()` → `ChunkPos.asLong()`)
- Inlined `agna-paper` and `mayi-paper` libraries as source code
- Added waypoints, time/weather commands, and `/bu help`

## License

This project is licensed under the [MIT License](LICENSE).

Original plugin by [Arcaniax](https://www.spigotmc.org/resources/builders-utilities.42361/).
Fork/rewrite by [TehBrian](https://github.com/TehBrian/BuildersUtilities).
BT Corsica adaptation by Me :3.
```
