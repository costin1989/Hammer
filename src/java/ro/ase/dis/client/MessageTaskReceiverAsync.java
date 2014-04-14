/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dis.client;

import java.util.Calendar;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author costin1989
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/taskQueue")
})
public class MessageTaskReceiverAsync implements MessageListener {

    @EJB
    MessageResponseSender responseSender;

    public MessageTaskReceiverAsync() {
    }

    @Override
    public void onMessage(Message m) {
        if (m != null) {
            if (m instanceof TextMessage) {
                TextMessage message = (TextMessage) m;
                try {
                     String passwords = message.getText();
                    String encrypted = Subject.subject;
                    
                    String[] task = passwords.split(",");
                        for (int i = 0; i < task.length; i++) {
                            String decrypted = DecryptFile.getDecryptFile(encrypted, task[i]);
                        
                            if (decrypted != null) {
                                String hostName = "client1";
                                responseSender.sendMessage(hostName + "," + Calendar.getInstance().getTime() + "," + task[i] + "," + decrypted);
                            }
                        }
                } catch (JMSException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
