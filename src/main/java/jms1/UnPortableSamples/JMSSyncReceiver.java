package jms1.UnPortableSamples;/*
  Created by IntelliJ IDEA.
  User: p.hosseini
  Date: 6/28/2020
  Time: 3:33 PM
*/


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSSyncReceiver {
    public static void main(String[] args) {
        try {
            Connection connection= new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            connection.start();
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("EM_TRADE.Q");

            MessageConsumer receiver = session.createConsumer(queue);
            //instead of QueueReceiver we used generic Interface Instead

            TextMessage msg =(TextMessage)receiver.receive();
            //unfortunately here i should cast the received message.

            //by the way this is s Blocking wait : it is going
            //to seat on that Receive method and Block and do not anything else
            //until the message comes in and if message does not come it will wait for ever

            // if we use (TextMessage)receiver.receiveNoWait();
            // if there is a message it receives otherwise it immediately returns null

            //if we use this way (TextMessage)receiver.receive(1000);
            //you can specify the milliseconds to waite before you terminate this synchronous call
            //it returns null after finishing the wait timeout too

            System.out.println(msg.getText());
            connection.close();

            //this is a synchronous receiver

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
