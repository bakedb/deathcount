package lol.bkd.deathcount;

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
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public final class Deathcount extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        World world = Bukkit.getWorld("world");
    }

    public class DeathListener implements Listener {
        @EventHandler
        public void onDeath(PlayerDeathEvent event) {
            NamespacedKey deathcount = new NamespacedKey(Deathcount.this, "deathcount");
            Player player = event.getEntity();
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            int current_deathcount = pdc.getOrDefault(deathcount, PersistentDataType.INTEGER, 0);
            current_deathcount++;
            String death_message = event.getDeathMessage();
            death_message = death_message + ". " + player.getName() + " has died " + current_deathcount + " times.";
            event.setDeathMessage(death_message);
            pdc.set(deathcount, PersistentDataType.INTEGER, current_deathcount);
        }
    }
}
