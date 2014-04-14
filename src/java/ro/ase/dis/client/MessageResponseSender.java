package ro.ase.dis.client;
 
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
 
@Stateless
public class MessageResponseSender {
 
    @Resource(mappedName = "jms/connectionFactory")
    private ConnectionFactory connectionFactory;
 
    @Resource(mappedName = "jms/responseQueue")
    private Queue queue;
 
    public void sendMessage(String message) {
        TextMessage textMessage;
        try {
            try (Connection connection = connectionFactory.createConnection(); 
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
                    MessageProducer messageProducer = session.createProducer(queue)) {
                
                    textMessage = session.createTextMessage();
                    textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
                    textMessage.setText(message);
                 //   messageProducer.setDeliveryDelay(100);
                    messageProducer.send(textMessage);
            }
            
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }
}