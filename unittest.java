/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.chatapppoe.ChatAppPOE;
 import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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

}
