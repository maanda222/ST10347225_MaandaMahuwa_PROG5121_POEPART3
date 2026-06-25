/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poe2iprg5121;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Main application class for QuickChat.
 * 
 * @author lab_services_student
 */
public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Login login = new Login();
        MessageManager messageManager = new MessageManager();

        // =========================
        // REGISTRATION
        // =========================

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        // Username Validation
        boolean validUsername = false;

        while (!validUsername) {

            System.out.print("Enter username: ");
            String username = input.nextLine();

            login.setUsername(username);

            if (login.checkUserName()) {

                System.out.println("Username successfully captured.");
                validUsername = true;

            } else {

                System.out.println("""
                        Username is not correctly formatted;
                        please ensure that your username contains
                        an underscore and is no more than five
                        characters in length.
                        """);
            }
        }

        // Password Validation
        boolean validPassword = false;

        while (!validPassword) {

            System.out.print("Enter password: ");
            String password = input.nextLine();

            login.setPassword(password);

            if (login.checkPasswordComplexity()) {

                System.out.println("Password successfully captured.");
                validPassword = true;

            } else {

                System.out.println("""
                        Password is not correctly formatted;
                        please ensure that the password contains
                        at least eight characters, a capital letter,
                        a number, and a special character.
                        """);
            }
        }

        // Cellphone Validation
        boolean validPhone = false;

        while (!validPhone) {

            System.out.print("Enter cellphone number: ");
            String phone = input.nextLine();

            login.setCellPhone(phone);

            if (login.checkCellPhoneNumber()) {

                System.out.println("Cell phone number successfully added.");
                validPhone = true;

            } else {

                System.out.println("""
                        Cell phone number incorrectly formatted
                        or does not contain international code.
                        """);
            }
        }

        // Registration Status
        System.out.println("\n" + login.registerUser());

        // =========================
        // LOGIN
        // =========================

        boolean loggedIn = false;

        while (!loggedIn) {

            System.out.println("\n===== LOGIN =====");

            System.out.print("Enter username: ");
            String enteredUsername = input.nextLine();

            System.out.print("Enter password: ");
            String enteredPassword = input.nextLine();

            loggedIn = login.loginUser(
                    enteredUsername,
                    enteredPassword
            );

            System.out.println(
                    login.returnLoginStatus(
                            firstName,
                            lastName,
                            loggedIn
                    )
            );
        }

        // =========================
        // QUICKCHAT STARTS HERE
        // =========================

        JOptionPane.showMessageDialog(
                null,
                "Welcome to QuickChat."
        );

        int totalMessages = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                totalMessages = Integer.parseInt(
                        JOptionPane.showInputDialog(
                                "How many messages would you like to send?"
                        )
                );
                if (totalMessages > 0) {
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter a number greater than 0."
                    );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid number."
                );
            }
        }

        // =========================
        // MAIN MENU LOOP
        // =========================

        boolean running = true;

        while (running) {

            String menu = JOptionPane.showInputDialog("""
                    
                    ===== QUICKCHAT MENU =====
                    
                    1. Send Messages
                    2. Show recently sent messages
                    3. Stored Messages
                    4. Quit
                    
                    Enter your choice:
                    """);

            if (menu == null) {
                // User cancelled
                running = false;
                JOptionPane.showMessageDialog(
                        null,
                        "Goodbye."
                );
                continue;
            }

            switch (menu) {

                case "1":
                    // =========================
                    // SEND MESSAGES
                    // =========================
                    
                    int sentCount = 0;
                    
                    for (int i = 0; i < totalMessages; i++) {

                        Message msg = new Message();

                        msg.enterDetails(i);

                        String action = msg.sentMessage();

                        if (action.equals("send")) {
                            messageManager.addMessage(msg, "send");
                            JOptionPane.showMessageDialog(
                                    null,
                                    msg.printMessages()
                            );
                            sentCount++;

                        } else if (action.equals("store")) {
                            messageManager.addMessage(msg, "store");
                            MessageStore.storeMessage(msg);
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Message stored successfully."
                            );
                            sentCount++;

                        } else if (action.equals("discard")) {
                            messageManager.addMessage(msg, "discard");
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Message discarded."
                            );
                        }
                    }

                    JOptionPane.showMessageDialog(
                            null,
                            "Total messages sent: " + sentCount
                    );
                    break;

                case "2":
                    // =========================
                    // SHOW RECENTLY SENT
                    // =========================
                    
                    if (messageManager.getSentMessages().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "No messages have been sent yet."
                        );
                    } else {
                        StringBuilder recent = new StringBuilder("=== RECENTLY SENT MESSAGES ===\n\n");
                        for (Message msg : messageManager.getSentMessages()) {
                            recent.append(msg.printMessages()).append("\n");
                        }
                        JOptionPane.showMessageDialog(
                                null,
                                recent.toString()
                        );
                    }
                    break;

                case "3":
                    // =========================
                    // STORED MESSAGES
                    // =========================
                    
                    showStoredMessagesMenu(messageManager);
                    break;

                case "4":
                    running = false;
                    JOptionPane.showMessageDialog(
                            null,
                            "Goodbye."
                    );
                    break;

                default:
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid option. Please try again."
                    );
            }
        }
    }

    // =========================
    // STORED MESSAGES MENU
    // =========================

    private static void showStoredMessagesMenu(MessageManager messageManager) {

        // Load stored messages from JSON
        messageManager.loadStoredMessagesFromJSON();

        boolean back = false;

        while (!back) {

            String option = JOptionPane.showInputDialog("""
                    
                    ===== STORED MESSAGES =====
                    
                    a. Display sender and recipient of all stored messages
                    b. Display the longest stored message
                    c. Search for a message by ID
                    d. Search for all messages for a particular recipient
                    e. Delete a message using message hash
                    f. Display full report of all stored messages
                    g. Back to main menu
                    
                    Enter your choice:
                    """);

            if (option == null) {
                back = true;
                continue;
            }

            switch (option.toLowerCase()) {

                case "a":
                    JOptionPane.showMessageDialog(
                            null,
                            messageManager.displaySenderAndRecipient()
                    );
                    break;

                case "b":
                    JOptionPane.showMessageDialog(
                            null,
                            messageManager.displayLongestMessage()
                    );
                    break;

                case "c":
                    String searchID = JOptionPane.showInputDialog(
                            "Enter Message ID to search:"
                    );
                    if (searchID != null && !searchID.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                messageManager.searchMessageByID(searchID.trim())
                        );
                    }
                    break;

                case "d":
                    String recipient = JOptionPane.showInputDialog(
                            "Enter recipient phone number:"
                    );
                    if (recipient != null && !recipient.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                messageManager.searchMessagesByRecipient(recipient.trim())
                        );
                    }
                    break;

                case "e":
                    String hash = JOptionPane.showInputDialog(
                            "Enter Message Hash to delete:"
                    );
                    if (hash != null && !hash.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                messageManager.deleteMessageByHash(hash.trim())
                        );
                    }
                    break;

                case "f":
                    JOptionPane.showMessageDialog(
                            null,
                            messageManager.displayReport()
                    );
                    break;

                case "g":
                    back = true;
                    break;

                default:
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid option. Please try again."
                    );
            }
        }
    }
}