/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppoe;

/**
 *
 * @author RC_Student_lab
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MessageStorage {

    public static void saveMessagesToText(List<Map<String, String>> messages) {
        try (FileWriter file = new FileWriter("messages.txt")) {
            for (Map<String, String> msg : messages) {
                String json = "{"
                        + "\"id\":\"" + msg.get("id") + "\", "
                        + "\"recipient\":\"" + msg.get("recipient") + "\", "
                        + "\"message\":\"" + msg.get("message") + "\", "
                        + "\"hash\":\"" + msg.get("hash") + "\""
                        + "}";
                file.write(json + System.lineSeparator());
            }
            System.out.println("Messages saved to messages.txt");
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    public static void saveMessagesToJson(List<Map<String, String>> messages) {
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write("[\n");
            for (int i = 0; i < messages.size(); i++) {
                Map<String, String> msg = messages.get(i);
                file.write("  {\n");
                file.write("    \"id\": \"" + msg.get("id") + "\",\n");
                file.write("    \"recipient\": \"" + msg.get("recipient") + "\",\n");
                file.write("    \"cell\": \"" + msg.get("cell") + "\",\n");
                file.write("    \"message\": \"" + msg.get("message") + "\",\n");
                file.write("    \"hash\": \"" + msg.get("hash") + "\"\n");
                file.write("  }" + (i < messages.size() - 1 ? "," : "") + "\n");
            }
            file.write("]");
            System.out.println("Messages saved to messages.json");
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }
}

