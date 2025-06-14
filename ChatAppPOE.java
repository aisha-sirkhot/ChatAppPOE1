/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapppoe;
import com.mycompany.chatapppoe.ChatAppPOE.Message;
import static com.mycompany.chatapppoe.ChatAppPOE.MessageStorage.loadMessagesFromJson;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.ParseException;






/**
 *
 * @author RC_Student_lab
 */

public class ChatAppPOE {
    
    static String currentUser = "";
    static ArrayList<String> fullMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();



    static final String MESSAGE_FILE = "messages.json";
    static List<Map<String, String>> messages = new ArrayList<>();


    
    
    public static void main(String[] args) {
        Scanner text = new Scanner(System.in);
        String username = captureUsername(text);
        String password = capturePassword(text);
        String phoneNumber = capturePhoneNumber(text);

        System.out.println("Registration complete!");
        System.out.println(Login.registerUser(username, password, phoneNumber));

        System.out.println("\nPlease log in now.");
//part 1
        boolean loginSuccess = false;

        while (!loginSuccess) {
            System.out.print("Enter username: ");
            // ask user to input their username
            String enteredUsername = text.next();

            System.out.print("Enter password: ");
            String enteredPassword = text.next();
            // ask user to input their password

            loginSuccess = Login.loginUser(username, password, enteredUsername, enteredPassword);
            // check if user's inputs are correct

            // Show status message
            System.out.println(Login.returnLoginStatus(loginSuccess));
            //part 2
            if (loginSuccess) {
                loadMessagesFromJson();
                boolean exit = false;
List<Map<String, String>> messages = new ArrayList<>();
List<Map<String, String>> storedMessages = new ArrayList<>();
//menu for part 2
boolean running = true;
while (running) {
    System.out.println("Welcome to QuickChat");
    System.out.println("\nMenu:");
    System.out.println("1. Send Message");
    System.out.println("2. Show Recent Message");
    System.out.println("3. Quit");
    System.out.println("4. Manage Messages");
    System.out.print("Choose an option: ");
    
//let user choose an option 
    int choice = text.nextInt();
    text.nextLine(); 
    
    switch (choice) {
        
        case 1:
    System.out.print("How many messages do you want to send/store? ");
    //user must type the amount of messages they want to send
    int numMessages = 0;
    while (true) {
        try {
            numMessages = Integer.parseInt(text.nextLine().trim());
            if (numMessages <= 0) {
                System.out.print("Please enter a positive number: ");
               
            } else {
                break;
            }
        } catch (NumberFormatException e) {
            System.out.print("Invalid input. Enter a valid number: ");
        }
    }

    for (int i = 0; i < numMessages; i++) {
        System.out.println("\nMessage " + (i + 1) + ":");

        System.out.println();  // add a blank line to separate previous input
System.out.print("Enter recipient name: ");
//user must enter the name of the person they want to message
String recipient = text.nextLine();
        

        System.out.print("Enter recipient cell number: ");
        //user must enter the phone number of the person they want to message
        String cell = text.nextLine();
        
        while (!Message.checkRecipientCell(cell)) {
            System.out.print("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again ");
            cell = text.nextLine();
        }

        System.out.print("Enter message ID: ");
        //user must enter the ID of the person they want to message
        String messageId = text.nextLine();
        while (!Message.checkMessageId(messageId)) {
            System.out.print("Invalid message ID (must be 10 characters or less). Enter again: ");
            messageId = text.nextLine();
        }

        System.out.print("Enter your message (max 250 characters): ");
        String messageText = text.nextLine();
        while (messageText.length() > 250) {
            System.out.print("Message exceeds 250 characters, please reduce size: ");
            messageText = text.nextLine();
        }

        int messageNumber = messages.size() + 1;
        String hash = Message.createMessageHash(messageId, messageNumber, messageText);

        // Ask user what to do with the message
        String option = "";
        while (true) {
            
            System.out.print("Do you want to send, store, or disregard this message? (send/store/disregard): ");
            //user must choose what theywish to do with the three options
            option = text.nextLine().trim().toLowerCase();

            if (option.equals("send")) {
                Map<String, String> msg = new HashMap<>();
                msg.put("id", messageId);
                msg.put("recipient", recipient);
                msg.put("cell", cell);
                msg.put("message", messageText);
                msg.put("hash", hash);
                msg.put("sender", "You");

                messages.add(msg);

                // Save all messages so far to file
                MessageStorage.saveMessagesToJson(messages);

                System.out.println("Message succesfully sent"); 
                break;

            } else if (option.equals("store")) {
                Map<String, String> msg = new HashMap<>();
                msg.put("id", messageId);
                msg.put("recipient", recipient);
                msg.put("cell", cell);
                msg.put("message", messageText);
                msg.put("hash", hash);
                msg.put("sender", "You");

                storedMessages.add(msg);
                System.out.println("Message stored to send later.");
                break;

            } else if (option.equals("disregard")) {
                System.out.println("Message disregarded.");
                break;

            } else {
                System.out.println("Invalid option. Please type 'send', 'store', or 'disregard'.");
                

            }
        }
    }

    System.out.println("All messages processed and stored.");
    break;

case 2:
    System.out.println("\n--- Recent Messages ---");

    if (messages.isEmpty() && storedMessages.isEmpty()) {
        System.out.println("No messages have been sent or stored yet.");
    } else {
        if (!messages.isEmpty()) {
            System.out.println("\nSent Messages:");
            for (Map<String, String> msg : messages) {
                System.out.println("To: " + msg.get("recipient"));
                System.out.println("Cell: " + msg.get("cell"));
                System.out.println("Message: " + msg.get("message"));
                System.out.println("ID: " + msg.get("id"));
                System.out.println("Hash: " + msg.get("hash"));
                System.out.println("------------------------");
                msg.put("sender", "You");

            }
        }

        if (!storedMessages.isEmpty()) {
            System.out.println("\nStored Messages:");
            for (Map<String, String> msg : storedMessages) {
                System.out.println("To: " + msg.get("recipient"));
                System.out.println("Cell: " + msg.get("cell"));
                System.out.println("Message: " + msg.get("message"));
                System.out.println("ID: " + msg.get("id"));
                System.out.println("Hash: " + msg.get("hash"));
                System.out.println("------------------------");
                msg.put("sender", "You");

            }
        }
    }
    break;


case 3:
            System.out.println("Exiting the program. Goodbye!");
            running = false;  //end loop
            break;

        default:
            System.out.println("Invalid option. Please try again.");
            break;        
            
            case 4:
    manageMessages(text);
    break;

    }
}
            }}}

   


        
    
    public class Message {

    public static boolean checkRecipientCell(String cell) {
        return cell.matches("0\\d{9}"); // starts with 0, 10 digits total
    }

    public static boolean checkMessageId(String id) {
        return id.length() <= 10;
    }

    public static String createMessageHash(String messageId, int messageNumber, String messageText) {
        //hash
        return Integer.toHexString((messageId + messageNumber + messageText).hashCode());
    }

    public static String sendMessageOption(Scanner scanner) {
        System.out.print("Type 'send' to send, 'store' to store, or other to discard: ");
        return scanner.nextLine().trim().toLowerCase();
        
        
    }
}

    
public class MessageStorage {

    public static void saveMessagesToJson(List<Map<String, String>> messages) {
    try (FileWriter file = new FileWriter("messages.json")) {
        file.write("[\n");
        for (int i = 0; i < messages.size(); i++) {
            Map<String, String> msg = messages.get(i);
            file.write("  {\n");
            file.write("    \"id\": \"" + msg.get("id") + "\",\n");
            file.write("    \"recipient\": \"" + msg.get("recipient") + "\",\n");
            file.write("    \"cell\": \"" + msg.get("cell") + "\",\n");
            file.write("    \"message\": \"" + msg.get("message").replace("\"", "\\\"") + "\",\n");
            file.write("    \"hash\": \"" + msg.get("hash") + "\"\n");
            file.write("  }" + (i < messages.size() - 1 ? "," : "") + "\n");
            msg.put("sender", "You");

        }
        file.write("]");
        System.out.println("Messages saved to messages.json");
    } catch (IOException e) {
        System.out.println("Error saving messages: " + e.getMessage());
    }
}

    // Load messages from messages.json
    public static List<Map<String, String>> loadMessagesFromJson() {
        List<Map<String, String>> messages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }

            String json = jsonBuilder.toString().trim();
            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1);
                String[] entries = json.split("\\},\\s*\\{");

                for (String entry : entries) {
                    entry = entry.trim();
                    if (!entry.startsWith("{")) entry = "{" + entry;
                    if (!entry.endsWith("}")) entry = entry + "}";

                    entry = entry.replaceAll("[{}\"]", "");
                    String[] fields = entry.split(",");

                    Map<String, String> msg = new HashMap<>();
                    for (String field : fields) {
                        String[] keyValue = field.split(":", 2);
                        if (keyValue.length == 2) {
                            msg.put(keyValue[0].trim(), keyValue[1].trim());
                        }
                    }
                    messages.add(msg);
                }
            }

        } catch (IOException e) {
            System.out.println("No saved messages found.");
        }
        return messages;
    }
}
//part 3
public static void manageMessages(Scanner text) {
    boolean managing = true;
    messages = loadMessagesFromJson(); // Load messages from JSON

    while (managing) {
        System.out.println("\n--- Manage Messages ---");
        System.out.println("1. View Messages");
        System.out.println("2. Edit Message");
        System.out.println("3. Delete Message");
        System.out.println("4. Search Messages");
        System.out.println("0. Return to Main Menu");

        System.out.print("Select an option: ");
        String choice = text.nextLine();

        switch (choice) {
            case "1":
                viewMessages(); //  Shows all messages
                break;

            case "2":
                editMessage(messages, text); //  Edit selected message
                break;

            case "3":
                deleteMessage(messages, text); //  Delete by index or hash
                break;

            case "4":
                searchMessages(messages, text); //  ID, recipient, longest
                break;

            case "0":
                managing = false;
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid option. Try again.");
        }
    }
}
    


public static void viewMessages() {
    if (messages.isEmpty()) {
        System.out.println("No messages to show.");
    } else {
        System.out.println("\n--- Sent Messages ---");
        for (int i = 0; i < messages.size(); i++) {
            Map<String, String> msg = messages.get(i);
            System.out.println("Index: " + i);
            System.out.println("To: " + msg.get("recipient"));
            System.out.println("Cell: " + msg.getOrDefault("cell", "N/A"));  // cell might be missing
            System.out.println("Message: " + msg.get("message"));
            System.out.println("ID: " + msg.get("id"));
            System.out.println("Hash: " + msg.get("hash"));
            System.out.println("------------------------");
            msg.put("sender", "You");

            

        }
    }
}

public static void editMessage(List<Map<String, String>> messages, Scanner scanner) {
    if (messages.isEmpty()) {
        System.out.println("No messages to edit.");
        return;
    }

    // Show all messages with index
    System.out.println("Messages:");
    for (int i = 0; i < messages.size(); i++) {
        System.out.println(i + ": " + messages.get(i).get("message"));
    }

    // Ask user which message to edit
    System.out.print("Enter the index of the message to edit: ");
    int index = -1;

    try {
        index = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        return;
    }

    if (index < 0 || index >= messages.size()) {
        System.out.println("Invalid index.");
        return;
    }

    Map<String, String> messageToEdit = messages.get(index);
    System.out.println("Current message: " + messageToEdit.get("message"));
    System.out.print("Enter the new message: ");
    String newMessage = scanner.nextLine();

    // Update message and hash
    messageToEdit.put("message", newMessage);
    messageToEdit.put("hash", Integer.toString(newMessage.hashCode()));

    // Save updated list
    MessageStorage.saveMessagesToJson(messages);
    System.out.println("Message updated and saved successfully.");
}


public static void deleteMessage(List<Map<String, String>> messages, Scanner scanner) {
    if (messages.isEmpty()) {
        System.out.println("No messages to delete.");
        return;
    }

    System.out.println("Messages:");
    for (int i = 0; i < messages.size(); i++) {
        System.out.println(i + ": " + messages.get(i).get("message"));
    }

    System.out.print("Enter the index of the message to delete (or type 'hash' to delete by hash): ");
    String input = scanner.nextLine();

    if (input.equalsIgnoreCase("hash")) {
        System.out.print("Enter the hash of the message to delete: ");
        String hash = scanner.nextLine();

        boolean found = false;
        Iterator<Map<String, String>> iterator = messages.iterator();
        while (iterator.hasNext()) {
            Map<String, String> msg = iterator.next();
            if (msg.get("hash").equals(hash)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            MessageStorage.saveMessagesToJson(messages);
            System.out.println("Message deleted by hash.");
        } else {
            System.out.println("No message found with that hash.");
        }

    } else {
        try {
            int index = Integer.parseInt(input);
            if (index >= 0 && index < messages.size()) {
                messages.remove(index);
                MessageStorage.saveMessagesToJson(messages);
                System.out.println("Message deleted by index.");
            } else {
                System.out.println("Invalid index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}


public static void searchMessages(List<Map<String, String>> messages, Scanner scanner) {
    if (messages.isEmpty()) {
        System.out.println("No messages to search.");
        return;
    }

    System.out.println("\nSearch Options:");
    System.out.println("1. Search by Message ID");
    System.out.println("2. Search by Recipient");
    System.out.println("3. Show Longest Message");
    System.out.print("Enter your choice: ");
    String choice = scanner.nextLine();
//user must pick an option
    switch (choice) {
        case "1":
            //display messages by searching the ID of the recipient
            System.out.print("Enter Message ID: ");
            String id = scanner.nextLine();
            for (Map<String, String> msg : messages) {
                if (msg.get("id").equals(id)) {
                    System.out.println("Recipient: " + msg.get("recipient"));
                    System.out.println("Message: " + msg.get("message"));
                    return;
                }
            }
            System.out.println("No message found with ID: " + id);
            break;

        case "2":
            //display messages by searching recipients name
            System.out.print("Enter recipient name: ");
            String recipient = scanner.nextLine();
            boolean found = false;
            for (Map<String, String> msg : messages) {
                if (msg.get("recipient").equalsIgnoreCase(recipient)) {
                    System.out.println("Message to " + recipient + ": " + msg.get("message"));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No messages found for recipient: " + recipient);
            }
            break;

        case "3":
            //display the longest message
            Map<String, String> longest = messages.get(0);
            for (Map<String, String> msg : messages) {
                if (msg.get("message").length() > longest.get("message").length()) {
                    longest = msg;
                }
            }
            System.out.println("Longest Message:");
            System.out.println("To: " + longest.get("recipient"));
            System.out.println("Message: " + longest.get("message"));
            break;

        default:
            System.out.println("Invalid option.");
    }
}


    // Registering process
    // method to input username
    public static String captureUsername(Scanner text) {
        String username;
        boolean isUsernameValid;
        do {
            System.out.println("Please enter your username");
            // user must input their username
            System.out.println("Your username must be no longer than five characters and must contain an underscore");
            username = text.next();
            isUsernameValid = username.contains("_") && username.length() <= 5;
            // boolean method to check the username matches requirements
            if (!isUsernameValid) {
                System.out.println("Please ensure that your user name is no longer than five characters and contains an underscore");
            }
        } while (!isUsernameValid);
        System.out.println("Username successfully captured!");
        return username;
    }

    // method to input password
    public static String capturePassword(Scanner text) {
        String password;
        boolean isPasswordValid;

        do {
            System.out.println("Please enter your password");
            // user must input their password correctly
            System.out.println("Your password must be at least 8 characters and must have a CAPITAL LETTER, a NUMBER, and a SPECIAL CHARACTER");
            password = text.next();
            boolean isLongEnough = password.length() >= 8;
            boolean hasCapitalLetter = password.matches(".*[A-Z].*");
            boolean hasNumber = password.matches(".*[0-9].*");
            boolean hasSpecialCharacter = password.matches(".*[!@#$%^&*()_+=<>?/{}|~`].*");
            // boolean method to check if the password matches the requirements
            isPasswordValid = isLongEnough && hasNumber && hasCapitalLetter && hasSpecialCharacter;
            if (!isPasswordValid) {
                System.out.println("Please ensure that the password is at least eight letters and contains a CAPITAL LETTER, a NUMBER, and a SPECIAL CHARACTER");
            }
        } while (!isPasswordValid);
        System.out.println("Password successfully captured!");
        return password;
    }

    // method to input phone number
    public static String capturePhoneNumber(Scanner text) {
        String phoneNumber;
        boolean isPhoneValid;

        do {
            System.out.println("Please enter your phone number ");
            // user must input the correct country code and numbers
            System.out.println("must start with +27 and have 10 digits total");
            phoneNumber = text.next();
            isPhoneValid = phoneNumber.matches("\\+27[6-8][0-9]{8}");

            if (!isPhoneValid) {
                System.out.println("Phone number is not formatted correctly or does not contain international code");
            }
        } while (!isPhoneValid);
        System.out.println("Phone number successfully captured!");
        return phoneNumber;
    }

    // login process
    public static class Login {

        // Boolean method to check username validity
        public static boolean checkUsernameValidity(String username) {
            return username.contains("_") && username.length() <= 5;
        }

        // Boolean method to check password complexity
        public static boolean checkPasswordComplexity(String password) {
            return password.length() >= 8 &&
                   password.matches(".*[A-Z].*") &&
                   password.matches(".*[0-9].*") &&
                   password.matches(".*[!@#$%^&*()].*");
        }

        // Boolean method to check South African phone number validity
        public static boolean checkPhoneNumberValidity(String phoneNumber) {
            return phoneNumber.matches("\\+27[0-9]{9}");
        }

        // String method to "register" the user (just combines data in a message)
        public static String registerUser(String username, String password, String phoneNumber) {
            return "User registered with username: " + username + ", phone number: " + phoneNumber;
        }

        // Boolean method to log in the user
        public static boolean loginUser(String storedUsername, String storedPassword, String enteredUsername, String enteredPassword) {
            return storedUsername.equals(enteredUsername) && storedPassword.equals(enteredPassword);
        }

        // String method to return login status
        public static String returnLoginStatus(boolean loginSuccessful) {
            if (loginSuccessful) {
                return "Login successful. Welcome to ChatApp!";
            } else {
                return "Login failed, your username or password is incorrect";
            }
        }
    }
}

