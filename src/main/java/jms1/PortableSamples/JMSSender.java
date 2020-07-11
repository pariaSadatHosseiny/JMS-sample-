package jms1.PortableSamples;
/*
  Created by IntelliJ IDEA.
  User: p.hosseini
  Date: 6/28/2020
  Time: 2:23 PM
*/

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSSender {
    public static void main(String[] args) throws Exception {

        //in this case we are going to grab an initial context and we need to specify where that JNDI reference is
        //so in resources we make a jndi.properties file
        Context ctx = new InitialContext();

        //now i can grab a connection ,i need a jndi connection
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        //now i need to create a Queue from JNDI
        Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");
        MessageProducer sender = session.createProducer(queue);

        TextMessage msg = session.createTextMessage("SELL AAPL 2000 SHARES");
        msg.setStringProperty("TraderName","Paria");
        sender.send(msg);
        System.out.println("message Sent Guys");
        connection.close();

    }
}
