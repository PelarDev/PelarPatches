## Fixes
* `authme-geyser` - cancel AuthMe session login if player is connecting from `localhost` (happens because of Geyser bridge)
* `discordsrv-griefprevention` - cancel sending message to Discord's Minecraft channel if player is soft-muted
* `discordsrv-chat` - adds `%nickname%` placeholder to *DiscordSRV* -> *Minecraft* chat message to display server's local name (configure in `DiscordSRV/messages.yml`)