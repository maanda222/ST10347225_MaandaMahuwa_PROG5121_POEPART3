/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe2iprg5121;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * MessageManager class handles all array operations for message management
 * 
 * @author lab_services_student
 */
public class MessageManager {

    private ArrayList<Message> sentMessages;
    private ArrayList<Message> discardedMessages;
    private ArrayList<Message> storedMessages;
    private ArrayList<String> messageHashes;
    private ArrayList<String> messageIDs;

    // =========================
    // CONSTRUCTOR
    // =========================

    public MessageManager() {
        sentMessages = new ArrayList<>();
        discardedMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        messageHashes = new ArrayList<>();
        messageIDs = new ArrayList<>();
    }

    // =========================
    // ADD MESSAGE TO APPROPRIATE ARRAY
    // =========================

    public void addMessage(Message msg, String action) {

        switch (action.toLowerCase()) {
            case "send":
                sentMessages.add(msg);
                break;
            case "discard":
                discardedMessages.add(msg);
                break;
            case "store":
                storedMessages.add(msg);
                break;
            default:
                System.out.println("Unknown action");
        }

        // Add to tracking arrays
        messageIDs.add(msg.getMessageID());
        messageHashes.add(msg.getMessageHash());
    }

    // =========================
    // POPULATE FROM JSON FILE
    // =========================

    public void loadStoredMessagesFromJSON() {

        try (BufferedReader reader = new BufferedReader(new FileReader("stored_messages.json"))) {

            String line;
            String messageID = "";
            String messageHash = "";
            String recipient = "";
            String message = "";
            
            while ((line = reader.readLine()) != null) {
                
                line = line.trim();
                
                if (line.startsWith("\"MessageID\":")) {
                    messageID = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                } else if (line.startsWith("\"MessageHash\":")) {
                    messageHash = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                } else if (line.startsWith("\"Recipient\":")) {
                    recipient = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                } else if (line.startsWith("\"Message\":")) {
                    message = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                } else if (line.equals("}")) {
                    
                    // Create and add the message
                    Message storedMsg = new Message();
                    storedMsg.setMessageID(messageID);
                    storedMsg.setMessageHash(messageHash);
                    storedMsg.setRecipient(recipient);
                    storedMsg.setMessage(message);
                    storedMsg.setFlag("stored");
                    
                    // Add to stored messages if not already present
                    boolean exists = false;
                    for (Message m : storedMessages) {
                        if (m.getMessageID().equals(messageID)) {
                            exists = true;
                            break;
                        }
                    }
                    
                    if (!exists) {
                        storedMessages.add(storedMsg);
                        messageIDs.add(messageID);
                        messageHashes.add(messageHash);
                    }
                }
            }
            
            JOptionPane.showMessageDialog(
                null,
                "Stored messages loaded successfully. Total: " + storedMessages.size()
            );

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null,
                "No stored messages found or error reading file."
            );
        }
    }

    // =========================
    // DISPLAY SENDER AND RECIPIENT OF ALL STORED MESSAGES
    // =========================

    public String displaySenderAndRecipient() {
        
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }
        
        StringBuilder result = new StringBuilder("=== STORED MESSAGES - SENDER & RECIPIENT ===\n\n");
        
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            result.append("Message ").append(i + 1).append(":\n");
            result.append("  Sender: User\n"); // Since we don't track sender, use generic
            result.append("  Recipient: ").append(msg.getRecipient()).append("\n\n");
        }
        
        return result.toString();
    }

    // =========================
    // DISPLAY LONGEST STORED MESSAGE
    // =========================

    public String displayLongestMessage() {
        
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }
        
        Message longest = storedMessages.get(0);
        
        for (Message msg : storedMessages) {
            if (msg.getMessage().length() > longest.getMessage().length()) {
                longest = msg;
            }
        }
        
        return "=== LONGEST STORED MESSAGE ===\n\n" +
               "Message: " + longest.getMessage() + "\n" +
               "Recipient: " + longest.getRecipient() + "\n" +
               "Message ID: " + longest.getMessageID() + "\n" +
               "Message Hash: " + longest.getMessageHash();
    }

    // =========================
    // SEARCH FOR MESSAGE BY ID
    // =========================

    public String searchMessageByID(String messageID) {
        
        // Search in all message collections
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return "Message found in Sent Messages:\n" + msg.printMessages();
            }
        }
        
        for (Message msg : storedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return "Message found in Stored Messages:\n" + msg.printMessages();
            }
        }
        
        for (Message msg : discardedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return "Message found in Discarded Messages:\n" + msg.printMessages();
            }
        }
        
        return "No message found with ID: " + messageID;
    }

    // =========================
    // SEARCH ALL MESSAGES FOR RECIPIENT
    // =========================

    public String searchMessagesByRecipient(String recipient) {
        
        StringBuilder result = new StringBuilder("=== MESSAGES FOR RECIPIENT: " + recipient + " ===\n\n");
        boolean found = false;
        
        // Search in all message collections
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                result.append("[SENT] ").append(msg.getMessage()).append("\n");
                found = true;
            }
        }
        
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                result.append("[STORED] ").append(msg.getMessage()).append("\n");
                found = true;
            }
        }
        
        for (Message msg : discardedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                result.append("[DISCARDED] ").append(msg.getMessage()).append("\n");
                found = true;
            }
        }
        
        if (!found) {
            return "No messages found for recipient: " + recipient;
        }
        
        return result.toString();
    }

    // =========================
    // DELETE MESSAGE BY HASH
    // =========================

    public String deleteMessageByHash(String messageHash) {
        
        // Search in stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(messageHash)) {
                String messageText = storedMessages.get(i).getMessage();
                storedMessages.remove(i);
                return "Message: \"" + messageText + "\" successfully deleted.";
            }
        }
        
        // Search in sent messages
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(messageHash)) {
                String messageText = sentMessages.get(i).getMessage();
                sentMessages.remove(i);
                return "Message: \"" + messageText + "\" successfully deleted.";
            }
        }
        
        // Search in discarded messages
        for (int i = 0; i < discardedMessages.size(); i++) {
            if (discardedMessages.get(i).getMessageHash().equals(messageHash)) {
                String messageText = discardedMessages.get(i).getMessage();
                discardedMessages.remove(i);
                return "Message: \"" + messageText + "\" successfully deleted.";
            }
        }
        
        return "No message found with hash: " + messageHash;
    }

    // =========================
    // DISPLAY FULL REPORT
    // =========================

    public String displayReport() {
        
        StringBuilder report = new StringBuilder();
        
        report.append("========================================\n");
        report.append("        MESSAGE REPORT\n");
        report.append("========================================\n\n");
        
        report.append("SENT MESSAGES (").append(sentMessages.size()).append("):\n");
        report.append("----------------------------------------\n");
        
        if (sentMessages.isEmpty()) {
            report.append("  No sent messages.\n\n");
        } else {
            for (Message msg : sentMessages) {
                report.append("Message Hash: ").append(msg.getMessageHash()).append("\n");
                report.append("Recipient: ").append(msg.getRecipient()).append("\n");
                report.append("Message: ").append(msg.getMessage()).append("\n");
                report.append("----------------------------------------\n");
            }
            report.append("\n");
        }
        
        report.append("STORED MESSAGES (").append(storedMessages.size()).append("):\n");
        report.append("----------------------------------------\n");
        
        if (storedMessages.isEmpty()) {
            report.append("  No stored messages.\n\n");
        } else {
            for (Message msg : storedMessages) {
                report.append("Message Hash: ").append(msg.getMessageHash()).append("\n");
                report.append("Recipient: ").append(msg.getRecipient()).append("\n");
                report.append("Message: ").append(msg.getMessage()).append("\n");
                report.append("----------------------------------------\n");
            }
            report.append("\n");
        }
        
        report.append("DISCARDED MESSAGES (").append(discardedMessages.size()).append("):\n");
        report.append("----------------------------------------\n");
        
        if (discardedMessages.isEmpty()) {
            report.append("  No discarded messages.\n\n");
        } else {
            for (Message msg : discardedMessages) {
                report.append("Message Hash: ").append(msg.getMessageHash()).append("\n");
                report.append("Recipient: ").append(msg.getRecipient()).append("\n");
                report.append("Message: ").append(msg.getMessage()).append("\n");
                report.append("----------------------------------------\n");
            }
            report.append("\n");
        }
        
        report.append("========================================\n");
        report.append("TOTAL MESSAGES: ").append(sentMessages.size() + storedMessages.size() + discardedMessages.size()).append("\n");
        report.append("========================================\n");
        
        return report.toString();
    }

    // =========================
    // GETTERS
    // =========================

    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<Message> getDiscardedMessages() {
        return discardedMessages;
    }

    public ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public ArrayList<String> getMessageIDs() {
        return messageIDs;
    }
}