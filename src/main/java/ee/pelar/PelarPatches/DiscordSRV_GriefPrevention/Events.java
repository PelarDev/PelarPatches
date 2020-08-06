package ee.pelar.PelarPatches.DiscordSRV_GriefPrevention;

import github.scarsz.discordsrv.api.events.GameChatMessagePreProcessEvent;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class Events implements Listener {

    private DataStore dataStore = GriefPrevention.instance.dataStore;

    @EventHandler
    public void onChatMessage(GameChatMessagePreProcessEvent e) {
        if (dataStore.isSoftMuted(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            String msg = ChatColor.translateAlternateColorCodes(
                    '&',
                    "&c<PelarPatches> &7=> &fPlayer is soft-muted, not sending message to Discord!"
            );
            getServer().getLogger().info(msg);
        }
    }
}
