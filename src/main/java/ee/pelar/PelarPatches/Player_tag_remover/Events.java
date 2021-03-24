package ee.pelar.PelarPatches.Player_tag_remover;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class Events implements Listener {

    private static List<String> tags;

    public Events(List<String> tags) {
        Events.tags = tags;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Entity e = event.getPlayer();
        tags.forEach(e::removeScoreboardTag);
    }
}
