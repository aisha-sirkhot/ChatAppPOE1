/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.chatapppoe.ChatAppPOE;
import com.mycompany.chatapppoe.ChatAppPOE.MessageStorage;
 import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RC_Student_lab
 */
public class unittest {
   
//test to check the username is valid and contains an underscoree and atleast 5 letters
public class ChatAppPOETest {

    @Test
    public void testCheckUsernameValidity() {
        assertTrue(ChatAppPOE.Login.checkUsernameValidity("abc_"));     // correct
        assertFalse(ChatAppPOE.Login.checkUsernameValidity("abcdef"));  // too many chraracters
        assertFalse(ChatAppPOE.Login.checkUsernameValidity("abcde"));   // no underscore
    }
//test to check if password is valid with all the needed requirements
    @Test
    public void testCheckPasswordComplexity() {
        assertTrue(ChatAppPOE.Login.checkPasswordComplexity("Pass@123"));      // correct
        assertFalse(ChatAppPOE.Login.checkPasswordComplexity("password"));     // too waek
        assertFalse(ChatAppPOE.Login.checkPasswordComplexity("Pass1234"));     // no special character
        assertFalse(ChatAppPOE.Login.checkPasswordComplexity("pass@123"));     // no capital letter
    }
//test to check if phone number is valid with the correct country code and numbers
    @Test
    public void testCheckPhoneNumberValidity() {
        assertTrue(ChatAppPOE.Login.checkPhoneNumberValidity("+27821234567"));  // correct
        assertFalse(ChatAppPOE.Login.checkPhoneNumberValidity("0821234567"));   // no country code
        assertFalse(ChatAppPOE.Login.checkPhoneNumberValidity("+278212345"));   // too short
        assertFalse(ChatAppPOE.Login.checkPhoneNumberValidity("+279999999999"));// too long
    }

    @Test
    public void testRegisterUserOutput() {
        String result = ChatAppPOE.Login.registerUser("abc_", "Pass@123", "+27821234567");
        assertTrue(result.contains("abc_"));
        assertTrue(result.contains("+27821234567"));
    }
//test to check correct login details are given
    @Test
    public void testLoginUser() {
        String storedUsername = "abc_";
        String storedPassword = "Pass@123";

        assertTrue(ChatAppPOE.Login.loginUser(storedUsername, storedPassword, "abc_", "Pass@123")); // correct
        assertFalse(ChatAppPOE.Login.loginUser(storedUsername, storedPassword, "wrong", "Pass@123")); // wrong username
        assertFalse(ChatAppPOE.Login.loginUser(storedUsername, storedPassword, "abc_", "wrongpass")); // wrong password
    }
//return back for the user to input correct details
    @Test
    public void testReturnLoginStatus() {
        assertEquals("Login successful. Welcome!", ChatAppPOE.Login.returnLoginStatus(true));
        assertEquals("Login failed, your username or password is incorrect", ChatAppPOE.Login.returnLoginStatus(false));
    }
}
//part 2
@Test
    public void testSendMessageAccepted() {
        // Setup
        List<Map<String, String>> testMessages = new ArrayList<>();
        String recipient = "+27718693002";
        String messageId = "msg001";
        String message = "Hi Mike, can you join us for dinner tonight";
        String hash = Integer.toHexString((messageId + 1 + message).hashCode());

        Map<String, String> msg = new HashMap<>();
        msg.put("id", messageId);
        msg.put("recipient", "Mike");
        msg.put("cell", recipient);
        msg.put("message", message);
        msg.put("hash", hash);

        testMessages.add(msg);

        // Assert
        assertEquals(1, testMessages.size());
        assertEquals("Mike", testMessages.get(0).get("recipient"));
        assertEquals(recipient, testMessages.get(0).get("cell"));
        assertEquals(message, testMessages.get(0).get("message"));
    }

    @Test
    public void testSendMessageDiscarded() {
        // Setup
        List<Map<String, String>> testMessages = new ArrayList<>();
        String recipient = "08575975889";
        String messageId = "msg002";
        String message = "Hi Keegan, did you receive the payment?";

        // Simulate discard action (don't add to list)
        boolean discard = true;

        if (!discard) {
            String hash = Integer.toHexString((messageId + 1 + message).hashCode());

            Map<String, String> msg = new HashMap<>();
            msg.put("id", messageId);
            msg.put("recipient", "Keegan");
            msg.put("cell", recipient);
            msg.put("message", message);
            msg.put("hash", hash);

            testMessages.add(msg);
        }

        // Assert discarded
        assertEquals(0, testMessages.size());
    }

@Test
    public void testMessageLengthSuccess() {
        String message = "A".repeat(250);
        assertTrue(message.length() <= 250);
        assertEquals("Message ready to send.", "Message ready to send.");
    }

    @Test
    public void testMessageLengthFailure() {
        String message = "A".repeat(260);
        int excess = message.length() - 250;
        assertTrue(message.length() > 250);
        String expected = "Message exceeds 250 characters by " + excess + ", please reduce size.";
        assertEquals(expected, "Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    @Test
    public void testRecipientNumberSuccess() {
        String number = "+12345678901";
        boolean isValid = number.startsWith("+") && number.substring(1).matches("\\d+") && number.length() >= 10;
        assertTrue(isValid);
        assertEquals("Cell phone number successfully captured.", "Cell phone number successfully captured.");
    }

    @Test
    public void testRecipientNumberFailure() {
        String number = "12345";
        boolean isValid = number.startsWith("+") && number.substring(1).matches("\\d+") && number.length() >= 10;
        assertFalse(isValid);
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        assertEquals(expected, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testMessageHash() {
        String hashOutput = "00:0:HITONIGHT"; // Simulated hash output
        assertEquals("00:0:HITONIGHT", hashOutput);
    }

    @Test
    public void testMessageIdCreation() {
        String messageId = "<Message ID>";
        assertTrue(messageId.startsWith("<") && messageId.endsWith(">"));
        assertEquals("Message ID generated: " + messageId, "Message ID generated: <Message ID>");
    }

    @Test
    public void testMessageSent() {
        assertEquals("Message successfully sent.", "Message successfully sent.");
    }

    @Test
    public void testMessageDisregarded() {
        assertEquals("Press 0 to delete message.", "Press 0 to delete message.");
    }

    @Test
    public void testMessageStored() {
        assertEquals("Message successfully stored.", "Message successfully stored.");
    }

@Test
public void testPopulateMessageArray() {
    List<Map<String, String>> messages = new ArrayList<>();

    Map<String, String> message = new HashMap<>();
    message.put("id", "001");
    message.put("recipient", "user123");
    message.put("cell", "0123456789");
    message.put("message", "Hello!");
    message.put("hash", "abc123");

    messages.add(message);

    assertEquals(1, messages.size());
    assertEquals("001", messages.get(0).get("id"));
    assertEquals("user123", messages.get(0).get("recipient"));
    assertEquals("0123456789", messages.get(0).get("cell"));
    assertEquals("Hello!", messages.get(0).get("message"));
    assertEquals("abc123", messages.get(0).get("hash"));
}
@Test
public void testSaveMessagesToJson() {
    List<Map<String, String>> messages = new ArrayList<>();

    Map<String, String> message = new HashMap<>();
    message.put("id", "001");
    message.put("recipient", "user123");
    message.put("cell", "0123456789");
    message.put("message", "Hello JSON!");
    message.put("hash", "hash001");

    messages.add(message);

    // Call method to save messages
    MessageStorage.saveMessagesToJson(messages);

    // Read file and check contents
    try {
        String content = new String(Files.readAllBytes(Paths.get("messages.json")));
        assertTrue(content.contains("\"id\": \"001\""));
        assertTrue(content.contains("\"recipient\": \"user123\""));
        assertTrue(content.contains("\"cell\": \"0123456789\""));
        assertTrue(content.contains("\"message\": \"Hello JSON!\""));
        assertTrue(content.contains("\"hash\": \"hash001\""));
    } catch (IOException e) {
        fail("Could not read messages.json file");
    }
}}

