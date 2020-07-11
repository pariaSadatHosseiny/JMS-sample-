package jms1.PortableSamples;/*
  Created by IntelliJ IDEA.
  User: p.hosseini
  Date: 6/28/2020
  Time: 3:33 PM
*/


import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSSyncReceiver {
    public static void main(String[] args) throws Exception {
        Context ctx = new InitialContext();
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");

        MessageConsumer receiver = session.createConsumer(queue);
        TextMessage msg = (TextMessage) receiver.receive();
        String trader = msg.getStringProperty("TraderName");
        System.out.println(msg.getText() + ", trader = "+trader);
        connection.close();

    }
}
