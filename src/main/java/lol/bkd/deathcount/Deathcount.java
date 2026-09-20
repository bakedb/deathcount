package lol.bkd.deathcount;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.configuration.file.FileConfiguration;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.util.Collection;

public final class Deathcount extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.saveResource("config.yml", false);
        this.registerCommand("setdeathcount", new SetDeathcount());
    }

    public class DeathListener implements Listener {
        @EventHandler
        public void onDeath(PlayerDeathEvent event) {

            boolean show_total_deathcount = Deathcount.this.getConfig().getBoolean("show-total-deathcount");

            // Handle player death
            NamespacedKey deathcount = new NamespacedKey(Deathcount.this, "deathcount");
            Player player = event.getEntity();
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            int current_deathcount = pdc.getOrDefault(deathcount, PersistentDataType.INTEGER, 0);
            current_deathcount++;

            // Handle total deathcount for all players
            World world = Bukkit.getWorld("world");
            PersistentDataContainer pdcWorld = world.getPersistentDataContainer();
            int world_deathcount = pdcWorld.getOrDefault(deathcount, PersistentDataType.INTEGER, 0);
            world_deathcount++;

            // Handle death message
            Component death_message = event.deathMessage()
                    .append(Component.text(". " + player.getName() + " has died " + current_deathcount + " times."));
            Component extended_death_message = event.deathMessage()
                    .append(Component.text(". " + player.getName() + " has died " + current_deathcount + " times. The total deathcount for all players is " + world_deathcount + "."));
            if (show_total_deathcount) {
                event.deathMessage(extended_death_message);
                event.deathScreenMessageOverride(death_message);
            }
            else {
                event.deathMessage(death_message);
            }
            pdc.set(deathcount, PersistentDataType.INTEGER, current_deathcount);
            pdcWorld.set(deathcount, PersistentDataType.INTEGER, world_deathcount);
        }
    }

    public class SetDeathcount implements BasicCommand {

        @Override
        public void execute(CommandSourceStack commandSourceStack, String[] args) {
            if (args.length == 0) {
                commandSourceStack.getSender().sendRichMessage("<red>You must provide an integer value to set the target's death count to.");
                return;
            }
            else if (args.length == 1) {
                commandSourceStack.getSender().sendRichMessage("<red>You must provide a target.");
                return;
            }

            try {
                int new_deathcount = Integer.parseInt(args[0]);

                NamespacedKey deathcount = new NamespacedKey(Deathcount.this, "deathcount");
                Player player = Bukkit.getPlayerExact(args[1]);
                if (player == null) {
                    commandSourceStack.getSender().sendRichMessage("<red>Player not found.");
                    return;
                }
                PersistentDataContainer pdc = player.getPersistentDataContainer();
                pdc.set(deathcount, PersistentDataType.INTEGER, new_deathcount);


            } catch (NumberFormatException e) {
                commandSourceStack.getSender().sendRichMessage("<red>You must provide a full number.");
                return;
            }
        }

        @Override
        public boolean canUse(CommandSender sender) {
            return BasicCommand.super.canUse(sender);
        }

        @Override
        public String permission() {
            return "deathcount.setdeathcount.use";
        }

        @Override
        public Collection<String> suggest(final CommandSourceStack source, final String[] args) {
            if (args.length != 0) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .toList();
            }
            return java.util.List.of();
        }
    }
}
