package ee.pelar.PelarPatches.CoreProtect_login;

import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.RegisteredListener;

import java.util.regex.Pattern;

public class Events implements Listener {

    private RegisteredListener coPCPE;
    private final Pattern filterCmd;

    public Events(String regex) {
        this.filterCmd = Pattern.compile(regex);
        for (RegisteredListener listener : PlayerCommandPreprocessEvent.getHandlerList().getRegisteredListeners()) {
            if (listener.getPlugin().getName().equals("CoreProtect")) {
                this.coPCPE = listener;
                PlayerCommandPreprocessEvent.getHandlerList().unregister(listener);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCoLog(PlayerCommandPreprocessEvent event) {
        if (this.coPCPE == null) return;
        if (!filterCmd.matcher(event.getMessage()).find()) {
            try {
                this.coPCPE.callEvent(event);
            } catch (EventException e) {
                e.printStackTrace();
            }
        }

    }
}
