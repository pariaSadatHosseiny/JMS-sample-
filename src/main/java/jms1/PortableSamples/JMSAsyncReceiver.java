package jms1.PortableSamples;/*
  Created by IntelliJ IDEA.
  User: p.hosseini
  Date: 6/28/2020
  Time: 4:22 PM
*/


import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSAsyncReceiver implements MessageListener {

    public JMSAsyncReceiver(){
        try {
            Context ctx = new InitialContext();
            Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");

            MessageConsumer receiver = session.createConsumer(queue);
            receiver.setMessageListener(this);
            System.out.println("waiting for message");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void onMessage(Message message){
        try {
            TextMessage msg=(TextMessage)message;
            String trader = msg.getStringProperty("TraderName");
            System.out.println(msg.getText() + ", trader = "+trader);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception{
        new Thread(){
            public void run(){
                new JMSAsyncReceiver();
            }}.start();
    }

}
