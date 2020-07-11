package jms1.UnPortableSamples;/*
  Created by IntelliJ IDEA.
  User: p.hosseini
  Date: 6/28/2020
  Time: 2:23 PM
*/

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSSender {
    public static void main(String[] args) {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection= cf.createConnection();
            connection.start();
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            // first parameter says it is a transacted session or not
            //second parameter is the Acknowledgement Mode
            // if first element is true second one is completely ignored
            Queue queue = session.createQueue("EM_TRADE.Q");
            //if the Queue does not exists it creates the Queue
            MessageProducer sender = session.createProducer(queue);

            TextMessage msg=session.createTextMessage("BUY AAPL 1000 SHARES");
            //now I have message and simply should send it

            sender.send(msg);
            System.out.println("message Sent Guys");

            connection.close();
            //if i close connection it will close the session too
            // if you are coding and sending more messages you can just close the session
            //but here we are at the end of the program so we close the connection

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
