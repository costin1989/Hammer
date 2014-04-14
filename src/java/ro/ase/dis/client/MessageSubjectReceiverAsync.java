/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dis.client;

import javax.ejb.ActivationConfigProperty;
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
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/subjectQueue")
})
public class MessageSubjectReceiverAsync implements MessageListener {

    @Override
    public void onMessage(Message m) {
        if (m != null) {
            if (m instanceof TextMessage) {
                TextMessage message = (TextMessage) m;
                try {
                    Subject.subject = message.getText();
                    System.out.println("Subject received and seted:"+message.getText());
                } catch (JMSException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
