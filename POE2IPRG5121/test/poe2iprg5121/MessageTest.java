/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package poe2iprg5121;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class MessageTest {

    private Message message;

    @Before
    public void setUp() {

        message = new Message();
    }

    @Test
    public void testRecipientSuccess() {

        assertEquals(1,
                message.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testRecipientFailure() {

        assertEquals(0,
                message.checkRecipientCell("08575975889"));
    }

    @Test
    public void testMessageHash() throws Exception {

        Field idField = Message.class.getDeclaredField("messageID");
        idField.setAccessible(true);
        idField.set(message, "0012345678");

        Field numField = Message.class.getDeclaredField("messageNumber");
        numField.setAccessible(true);
        numField.set(message, 0);

        Field msgField = Message.class.getDeclaredField("message");
        msgField.setAccessible(true);
        msgField.set(message,
                "Hi Mike can you join us for dinner tonight");

        String hash = message.createMessageHash();

        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    public void testMessageIDLength() throws Exception {

        Field idField = Message.class.getDeclaredField("messageID");

        idField.setAccessible(true);

        idField.set(message, "1234567890");

        assertTrue(message.checkMessageID());
    }
}