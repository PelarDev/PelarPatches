package ee.pelar.PelarPatches.AuthMe_Geyser;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.RestoreSessionEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;


public class Events implements Listener {

    private AuthMeApi authMeApi = AuthMeApi.getInstance();

    @EventHandler
    public void onSessionRestore(RestoreSessionEvent e) {

        Player p = e.getPlayer();
        if (authMeApi.getLastIp(p.getName()).equals("127.0.0.1")) {
            e.setCancelled(true);
            String msg = ChatColor.translateAlternateColorCodes(
                    '&',
                    "&c<PelarPatches> &7=> &fGeyser detected and session restore cancelled!"
            );
            getServer().getLogger().info(msg);
        }

    }
}
