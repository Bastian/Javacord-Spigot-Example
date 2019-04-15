package org.javacord.spigotexample;

import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class SpigotExample extends JavaPlugin {

    private DiscordApi api;

    @Override
    public void onEnable() {
        // Connect to Discord
        new DiscordApiBuilder()
                .setToken("<yourToken>") // Set the token of the bot here
                .login() // Log the bot in
                .thenAccept(this::onConnectToDiscord) // Call #onConnectToDiscord(...) after a successful login
                .exceptionally(error -> {
                    // Log a warning when the login to Discord failed (wrong token?)
                    getLogger().warning("Failed to connect to Discord! Disabling plugin!");
                    getPluginLoader().disablePlugin(this);
                    return null;
                });
    }

    @Override
    public void onDisable() {
        if (api != null) {
            // Make sure to disconnect the bot when the plugin gets disabled
            api.disconnect();
            api = null;
        }
    }

    private void onConnectToDiscord(DiscordApi api) {
        this.api = api;

        // Log a message that the connection was successful and log the url that is needed to invite the bot
        getLogger().info("Connected to Discord as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Open the following url to invite the bot: " + api.createBotInvite());
    }
}
