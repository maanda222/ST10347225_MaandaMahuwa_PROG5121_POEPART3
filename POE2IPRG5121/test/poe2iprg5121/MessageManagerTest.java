/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package poe2iprg5121;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for MessageManager class
 * 
 * @author lab_services_student
 */
public class MessageManagerTest {
    
    private MessageManager messageManager;
    private Message testMessage1;
    private Message testMessage2;
    private Message testMessage3;
    private Message testMessage4;
    private Message testMessage5;
    
    @Before
    public void setUp() {
        messageManager = new MessageManager();
        
        // Test Data Message 1 - Sent
        testMessage1 = new Message("+27834557896", "Did you get the cake?", "sent");
        testMessage1.setMessageID("1234567890");
        testMessage1.setMessageHash("12:0:DIDCAKE");
        
        // Test Data Message 2 - Stored
        testMessage2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "stored");
        testMessage2.setMessageID("2345678901");
        testMessage2.setMessageHash("23:1:WHEREON");
        
        // Test Data Message 3 - Discarded
        testMessage3 = new Message("+27834484567", "Yohoooo, I am at your gate.", "discarded");
        testMessage3.setMessageID("3456789012");
        testMessage3.setMessageHash("34:2:YOHOOGATE");
        
        // Test Data Message 4 - Sent
        testMessage4 = new Message("0838884567", "It is dinner time!", "sent");
        testMessage4.setMessageID("4567890123");
        testMessage4.setMessageHash("45:3:ITTIME");
        
        // Test Data Message 5 - Stored
        testMessage5 = new Message("+27838884567", "Ok, I am leaving without you.", "stored");
        testMessage5.setMessageID("5678901234");
        testMessage5.setMessageHash("56:4:OKYOU");
    }

    // =========================
    // TEST: SENT MESSAGES ARRAY CORRECTLY POPULATED
    // =========================
    
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // Add messages
        messageManager.addMessage(testMessage1, "send");
        messageManager.addMessage(testMessage4, "send");
        
        // Get sent messages
        var sentMessages = messageManager.getSentMessages();
        
        // Verify size
        assertEquals("Sent messages array should contain 2 messages", 2, sentMessages.size());
        
        // Verify content
        assertEquals("First message should be testMessage1", "Did you get the cake?", sentMessages.get(0).getMessage());
        assertEquals("Second message should be testMessage4", "It is dinner time!", sentMessages.get(1).getMessage());
    }
    
    // =========================
    // TEST: DISPLAY LONGEST MESSAGE
    // =========================
    
    @Test
    public void testDisplayLongestMessage() {
        // Add messages to stored messages
        messageManager.addMessage(testMessage1, "store");
        messageManager.addMessage(testMessage2, "store");
        messageManager.addMessage(testMessage3, "store");
        messageManager.addMessage(testMessage4, "store");
        
        String result = messageManager.displayLongestMessage();
        
        assertTrue("Longest message should be testMessage2", 
            result.contains("Where are you? You are late! I have asked you to be on time."));
    }
    
    // =========================
    // TEST: SEARCH FOR MESSAGE ID
    // =========================
    
    @Test
    public void testSearchMessageByID() {
        // Add messages
        messageManager.addMessage(testMessage1, "send");
        messageManager.addMessage(testMessage2, "store");
        messageManager.addMessage(testMessage3, "discard");
        messageManager.addMessage(testMessage4, "send");
        
        // Search for message 4
        String result = messageManager.searchMessageByID("4567890123");
        
        assertTrue("Should find message with ID 4567890123", 
            result.contains("It is dinner time!"));
    }
    
    // =========================
    // TEST: SEARCH ALL MESSAGES FOR RECIPIENT
    // =========================
    
    @Test
    public void testSearchMessagesByRecipient() {
        // Add messages
        messageManager.addMessage(testMessage1, "send");
        messageManager.addMessage(testMessage2, "store");
        messageManager.addMessage(testMessage5, "store");
        
        // Search for recipient +27838884567
        String result = messageManager.searchMessagesByRecipient("+27838884567");
        
        assertTrue("Should find messages for recipient +27838884567",
            result.contains("Where are you? You are late!"));
        assertTrue("Should find messages for recipient +27838884567",
            result.contains("Ok, I am leaving without you."));
    }
    
    // =========================
    // TEST: DELETE MESSAGE USING MESSAGE HASH
    // =========================
    
    @Test
    public void testDeleteMessageByHash() {
        // Add messages
        messageManager.addMessage(testMessage1, "send");
        messageManager.addMessage(testMessage2, "store");
        messageManager.addMessage(testMessage3, "discard");
        
        // Delete message 2 using hash
        String result = messageManager.deleteMessageByHash("23:1:WHEREON");
        
        assertTrue("Should confirm deletion", 
            result.contains("successfully deleted"));
        
        // Verify message was removed from stored messages
        assertEquals("Stored messages should have 0 messages", 
            0, messageManager.getStoredMessages().size());
    }
    
    // =========================
    // TEST: DELETE MESSAGE USING MESSAGE HASH - NOT FOUND
    // =========================
    
    @Test
    public void testDeleteMessageByHashNotFound() {
        String result = messageManager.deleteMessageByHash("99:9:NOTFOUND");
        
        assertTrue("Should indicate message not found", 
            result.contains("No message found with hash"));
    }
    
    // =========================
    // TEST: DISPLAY REPORT
    // =========================
    
    @Test
    public void testDisplayReport() {
        // Add messages
        messageManager.addMessage(testMessage1, "send");
        messageManager.addMessage(testMessage2, "store");
        messageManager.addMessage(testMessage3, "discard");
        messageManager.addMessage(testMessage4, "send");
        
        String report = messageManager.displayReport();
        
        // Check if report contains all message details
        assertTrue("Report should contain Message Hash", report.contains("Message Hash"));
        assertTrue("Report should contain Recipient", report.contains("Recipient"));
        assertTrue("Report should contain Message", report.contains("Message"));
        assertTrue("Report should contain testMessage1", report.contains("Did you get the cake?"));
        assertTrue("Report should contain testMessage2", report.contains("Where are you?"));
        assertTrue("Report should contain testMessage3", report.contains("Yohoooo"));
        assertTrue("Report should contain testMessage4", report.contains("It is dinner time!"));
    }
    
    // =========================
    // TEST: DISPLAY SENDER AND RECIPIENT
    // =========================
    
    @Test
    public void testDisplaySenderAndRecipient() {
        messageManager.addMessage(testMessage2, "store");
        messageManager.addMessage(testMessage5, "store");
        
        String result = messageManager.displaySenderAndRecipient();
        
        assertTrue("Should display stored messages", 
            result.contains("STORED MESSAGES"));
        assertTrue("Should display recipient for message 2", 
            result.contains("+27838884567"));
    }
    
    // =========================
    // TEST: DISPLAY SENDER AND RECIPIENT - EMPTY
    // =========================
    
    @Test
    public void testDisplaySenderAndRecipientEmpty() {
        String result = messageManager.displaySenderAndRecipient();
        
        assertEquals("Should indicate no stored messages", 
            "No stored messages found.", result);
    }
}