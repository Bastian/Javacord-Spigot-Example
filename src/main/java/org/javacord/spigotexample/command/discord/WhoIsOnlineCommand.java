package org.javacord.spigotexample.command.discord;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.stream.Collectors;

public class WhoIsOnlineCommand implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Ignore any message that is not !whoIsOnline
        if (!event.getMessageContent().equalsIgnoreCase("!whoIsOnline")) {
            return;
        }

        // Collect the names of all online players
        String onlinePlayers = Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));

        // Check if there are any online players
        if (onlinePlayers.isEmpty()) {
            event.getChannel().sendMessage("There are no players online!");
            return;
        }

        // Display the names of all online players
        new MessageBuilder()
                .append("The following players are currently online:")
                .appendCode("", onlinePlayers)
                .send(event.getChannel());
    }
}
