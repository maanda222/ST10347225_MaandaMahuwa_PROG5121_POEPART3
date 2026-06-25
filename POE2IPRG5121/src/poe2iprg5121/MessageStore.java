/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe2iprg5121;

import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author lab_services_student
 */
public class MessageStore {

    public static void storeMessage(Message msg) {

        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {

            String json = """
                    {
                        "MessageID": "%s",
                        "MessageHash": "%s",
                        "Recipient": "%s",
                        "Message": "%s"
                    }
                    """
                    .formatted(
                            msg.getMessageID(),
                            msg.getMessageHash(),
                            msg.getRecipient(),
                            msg.getMessage()
                    );

            writer.write(json);

            System.out.println("Message stored successfully.");

        } catch (IOException e) {

            System.out.println("Error storing message: " + e.getMessage());
        }
    }
}
