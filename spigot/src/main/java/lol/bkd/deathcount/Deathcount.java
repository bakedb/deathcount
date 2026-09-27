package lol.bkd.deathcount;

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

public final class Deathcount extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }

    public class DeathListener implements Listener {
        @EventHandler
        public void onDeath(PlayerDeathEvent event) {

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
            String base_message = event.getDeathMessage();
            if (base_message == null) {
                base_message = player.getName() + " has died.";
            }
            String death_message = base_message + ". " + player.getName() + " has died " + current_deathcount + " times. The total deathcount for all players is " + world_deathcount + ".";
            event.setDeathMessage(death_message);

            pdc.set(deathcount, PersistentDataType.INTEGER, current_deathcount);
            pdcWorld.set(deathcount, PersistentDataType.INTEGER, world_deathcount);
        }
    }
}
