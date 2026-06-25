/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe2iprg5121;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Message class representing a chat message with all required attributes
 * 
 * @author lab_services_student
 */
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private String flag; // "sent", "stored", "discarded"

    // =========================
    // CONSTRUCTORS
    // =========================

    public Message() {
        // Default constructor
    }

    public Message(String recipient, String message, String flag) {
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.messageNumber = 0;
    }

    // =========================
    // ENTER MESSAGE DETAILS
    // =========================

    public void enterDetails(int num) {

        this.messageNumber = num;
        this.messageID = generateMessageID();

        // =========================
        // RECIPIENT VALIDATION
        // =========================

        recipient = JOptionPane.showInputDialog(
                null,
                "Enter recipient number:"
        );

        while (checkRecipientCell(recipient) == 0) {

            JOptionPane.showMessageDialog(
                    null,
                    """
                    Cell phone number is incorrectly formatted
                    or does not contain an international code.
                    Please correct the number and try again.
                    """
            );

            recipient = JOptionPane.showInputDialog(
                    null,
                    "Enter recipient number:"
            );
        }

        JOptionPane.showMessageDialog(
                null,
                "Cell phone number successfully captured."
        );

        // =========================
        // MESSAGE VALIDATION
        // =========================

        message = JOptionPane.showInputDialog(
                null,
                "Enter your message:"
        );

        while (message.length() > 250) {

            int excess = message.length() - 250;

            JOptionPane.showMessageDialog(
                    null,
                    "Message exceeds 250 characters by "
                    + excess
                    + ", please reduce size."
            );

            message = JOptionPane.showInputDialog(
                    null,
                    "Enter your message again:"
            );
        }

        JOptionPane.showMessageDialog(
                null,
                "Message ready to send."
        );

        // =========================
        // CREATE HASH
        // =========================

        messageHash = createMessageHash();
    }

    // =========================
    // GENERATE MESSAGE ID
    // =========================

    private String generateMessageID() {

        Random random = new Random();

        long number = 1000000000L
                + (long) (random.nextDouble() * 8999999999L);

        return String.valueOf(number);
    }

    // =========================
    // CHECK MESSAGE ID
    // =========================

    public boolean checkMessageID() {

        return messageID.length() <= 10;
    }

    // =========================
    // VALIDATE RECIPIENT NUMBER
    // =========================

    public int checkRecipientCell(String number) {

        if (number == null) {
            return 0;
        }

        if (number.matches("^\\+27\\d{9}$")) {
            return 1;
        }

        return 0;
    }

    // =========================
    // CREATE MESSAGE HASH
    // =========================

    public String createMessageHash() {
        if (message == null || message.trim().isEmpty()) {
            return "00:0:EMPTY";
        }
        
        String[] words = message.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (
                messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord
                ).toUpperCase();
    }

    // =========================
    // SEND / STORE / DISCARD
    // =========================

    public String sentMessage() {

        String[] options = {
            "Send Message",
            "Disregard Message",
            "Store Message"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option",
                "Message Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {

            case 0:
                this.flag = "sent";
                JOptionPane.showMessageDialog(
                        null,
                        "Message successfully sent."
                );
                return "send";

            case 1:
                this.flag = "discarded";
                JOptionPane.showMessageDialog(
                        null,
                        "Message discarded."
                );
                return "discard";

            case 2:
                this.flag = "stored";
                JOptionPane.showMessageDialog(
                        null,
                        "Message successfully stored."
                );
                return "store";

            default:
                this.flag = "discarded";
                return "discard";
        }
    }

    // =========================
    // DISPLAY MESSAGE DETAILS
    // =========================

    public String printMessages() {

        return """
               Message ID: %s
               Message Hash: %s
               Recipient: %s
               Message: %s
               Flag: %s
               """.formatted(
                messageID,
                messageHash,
                recipient,
                message,
                flag != null ? flag : "pending"
        );
    }

    // =========================
    // TOTAL MESSAGES
    // =========================

    public int returnTotalMessages() {

        return messageNumber + 1;
    }

    // =========================
    // GETTERS AND SETTERS
    // =========================

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.messageHash = createMessageHash();
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}