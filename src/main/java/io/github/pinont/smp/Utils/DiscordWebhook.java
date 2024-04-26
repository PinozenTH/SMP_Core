package io.github.pinont.smp.Utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.github.pinont.smp.Core.plugin;
import static io.github.pinont.smp.Utils.JsonUtils.toJson;

public class DiscordWebhook {

    public static void sendMessage(String content) throws IOException {
        if (plugin.getConfig().getString("enable_discord_webhook").equals("false")) {
            Msg.console(content);
            return;
        }
        URL url = new URL(Objects.requireNonNull(plugin.getConfig().getString("webhook")));
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Map<String, String> jsonPayload = new HashMap<>();
        jsonPayload.put("content", content);

        String json = toJson(jsonPayload);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Message sent successfully!");
        } else {
            System.out.println("Failed to send message. Response code: " + responseCode);
        }

        connection.disconnect();
    }

    public static void sendEmbedMessage(String title, String description, String color) throws IOException {
        if (plugin.getConfig().getString("enable_discord_webhook").equals("false")) {
            Msg.console(title + "\n" + description);
            return;
        }
        URL url = new URL(Objects.requireNonNull(plugin.getConfig().getString("webhook")));
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", title);
        embed.put("description", description);

        // Parse the color string to an integer
        int colorInt = Integer.parseInt(color.replace("#", ""), 16);
        embed.put("color", colorInt);

        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put("embeds", new Object[]{embed});

        String json = toJson(jsonPayload);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Embedded message sent successfully!");
        } else {
            System.out.println("Failed to send embedded message. Response code: " + responseCode);
        }

        connection.disconnect();
    }

    // image
    public static void sendEmbedMessage(String title, String description, String color, String image) throws IOException {
        if (plugin.getConfig().getString("enable_discord_webhook").equals("false")) {
            Msg.console(title + "\n" + description);
            return;
        }
        URL url = new URL(Objects.requireNonNull(plugin.getConfig().getString("webhook")));
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", title);
        embed.put("description", description);
        embed.put("image", image);

        // Parse the color string to an integer
        int colorInt = Integer.parseInt(color.replace("#", ""), 16);
        embed.put("color", colorInt);

        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put("embeds", new Object[]{embed});

        String json = toJson(jsonPayload);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Embedded message sent successfully!");
        } else {
            System.out.println("Failed to send embedded message. Response code: " + responseCode);
        }

        connection.disconnect();
    }
}