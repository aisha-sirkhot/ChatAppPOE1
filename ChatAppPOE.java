/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapppoe;
import java.util.Scanner;
/**
 *
 * @author RC_Student_lab
 */
public class ChatAppPOE {

    public static void main(String[] args) {
        Scanner text = new Scanner(System.in);
        String username = captureUsername(text);
        String password = capturePassword(text);
        String phoneNumber = capturePhoneNumber(text);

        System.out.println("Registration complete!");
        System.out.println(Login.registerUser(username, password, phoneNumber));

        System.out.println("\nPlease log in now.");

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
