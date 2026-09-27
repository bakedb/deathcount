# Deathcount

This Minecraft Paper plugin counts the amount of deaths that each player has, and announces it server-wide once they die again.
![img.png](img/img.png)

## Notes
The death screen message will not be modified if the show death messages in chat gamerule is disabled.

If you see "[playername] has died. [playername] has died [x] times." in chat when a normal death message (such as "[playername] drowned.") was expected, that means that the plugin cannot retrieve event.deathMessage (or event.getDeathMessage for Spigot) for some reason.

## Configuration
You can configure the following options in plugins/Deathcount/config.yml:
- `show-total-deathcount`: Toggle "The total deathcount for all players is [all player's death count]." message.

## Spigot Support
Note that there is a Spigot version available, but it is very bare-bones and only implements death counters and only modifies the death messages in chat.
Please use the Paper version if you are able to.

## Commands
`/deathcount:setdeathcount <deaths> <target>`

The permission name is `deathcount.setdeathcount.use` for LuckPerms and similar things.

## TODO

- Configurable messages
- Update and add images