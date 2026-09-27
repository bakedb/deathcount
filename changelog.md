# Release 1.1.3
- Fixed Spigot build.
- Spigot now has the same features as the Paper version minus command support.
- Setting the world object now uses player.getWorld() instead of Bukkit.getWorld("world"), preventing errors regarding world folders not named "world".
- Switched from saveResource to saveDefaultConfig.
- Improved metadata handling when working with the plugin.
- Spigot can now use a config file.
- Command permissions are now explicitly defined to op-only.

# Release 1.1.2
- Added a bare-bones Spigot build
- Added compatibility for Paper 1.21.11+

# Release 1.1.1
- Added support for versions from 26.1.1 to 26.3.

# Release 1.1
- Added 26.3 support.
- Added config file (config version is now 1).
- Added config option to toggle "The total deathcount for all players is [all player's death count]." message.
- Converted Strings to Components in the death message handler.
- Added `/deathcount:setdeathcount` command.

# Release 1.0
- Initial release

<!-- Template:
# Release <major>.<minor>.[hotfix]
- Added [version] support.
- Config version is now [version].
-->