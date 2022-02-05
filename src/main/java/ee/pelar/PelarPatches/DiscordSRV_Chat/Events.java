package ee.pelar.PelarPatches.DiscordSRV_Chat;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePostProcessEvent;
import org.bukkit.event.Listener;

public class Events implements Listener {

    @Subscribe
    public void onDiscordGuildMessage(DiscordGuildMessagePostProcessEvent event) {
        String nickname = event.getMember().getNickname();
        if (nickname != null) {
            String processed = event.getProcessedMessage();
            event.setProcessedMessage(processed.replace("%nickname%", event.getMember().getNickname()));
        }
    }

}
