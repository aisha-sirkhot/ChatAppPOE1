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
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageStorage {

    // Save to JSON
    public static void saveMessagesToJson(List<Map<String, String>> messages) {
        JSONArray jsonMessages = new JSONArray();

        for (Map<String, String> msg : messages) {
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("id", msg.get("id"));
            jsonMessage.put("recipient", msg.get("recipient"));
            jsonMessage.put("cell", msg.get("cell"));
            jsonMessage.put("message", msg.get("message"));
            jsonMessage.put("hash", msg.get("hash"));
            jsonMessages.add(jsonMessage);
        }

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(jsonMessages.toJSONString());
            System.out.println("Messages saved to messages.json");
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    // Load from JSON
    public static List<Map<String, String>> loadMessagesFromJson() {
        List<Map<String, String>> messages = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("messages.json")) {
            Object obj = parser.parse(reader);
            JSONArray jsonMessages = (JSONArray) obj;

            for (Object o : jsonMessages) {
                JSONObject jsonMessage = (JSONObject) o;
                Map<String, String> msg = new HashMap<>();
                msg.put("id", (String) jsonMessage.get("id"));
                msg.put("recipient", (String) jsonMessage.get("recipient"));
                msg.put("cell", (String) jsonMessage.get("cell"));
                msg.put("message", (String) jsonMessage.get("message"));
                msg.put("hash", (String) jsonMessage.get("hash"));
                messages.add(msg);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error loading messages: " + e.getMessage());
        }

        return messages;
    }
    public static List<Map<String, String>> loadStoredMessagesFromJson() {
    List<Map<String, String>> storedMessages = new ArrayList<>();

    try (FileReader reader = new FileReader("storedMessages.json")) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(reader);

        for (Object obj : jsonArray) {
            JSONObject jsonMsg = (JSONObject) obj;
            Map<String, String> msgMap = new HashMap<>();

            msgMap.put("recipient", (String) jsonMsg.get("recipient"));
            msgMap.put("cell", (String) jsonMsg.get("cell"));
            msgMap.put("message", (String) jsonMsg.get("message"));
            msgMap.put("id", (String) jsonMsg.get("id"));
            msgMap.put("hash", (String) jsonMsg.get("hash"));

            storedMessages.add(msgMap);
        }

    } catch (IOException | ParseException e) {
        System.out.println("Error loading stored messages: " + e.getMessage());
    }

    return storedMessages;
}

}
