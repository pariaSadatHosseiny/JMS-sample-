package jms1.UnPortableSamples;/*
  Created by IntelliJ IDEA.
  User: p.hosseini
  Date: 6/28/2020
  Time: 4:22 PM
*/


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSAsyncReceiver implements MessageListener {
    //3-this is implementing javax.jms.MessageListener and we need to override the onMessage method

    public JMSAsyncReceiver(){
        try {
            Connection connection=new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            connection.start();
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //now we need a Queue
            Queue queue = session.createQueue("EM_TRADE.Q");
            MessageConsumer receiver = session.createConsumer(queue);
            //in this case we want to register a message Listener
            receiver.setMessageListener(this);
            //i set the MessageListener my own class , it doesn't have to be , but i do in this particular case
            System.out.println("waiting for message");
            // these all runs just one time
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //2-it is the constructor and is going to include all of the getting the connection ,session , Queue , ...
    }

    public void onMessage(Message message){
        //when a messsage is Received this message is going to fire and a message as Message Interface pass to it
        try {
            TextMessage msg=(TextMessage)message;
            System.out.println(msg.getText());

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //this is the method that is going to be invoked whenever the Listener suddenly Receives a message and we put all
    // logic of displaying a message here

    public static void main(String[] args) {
        new Thread(){
            public void run(){
                new JMSAsyncReceiver();
            }}.start();
        //1-this main method starts a new Thread , overrides the run and stantiate and runs the JMSAsyncReceiver method
    }

    //by running this sample you can get the message without blocking and many times
    //this class is no longer Blocked
}
