package distribution.queue;

import infrastructure.*;
import distribution.marshaller.*;
import distribution.request.*;
import distribution.message.*;
import distribution.reply.ReplyPacket;

import java.io.*;
import java.util.*;

/**
 * CIn - Centro de Informática
 * IF711 - Programação Concorrente e Distribuída
 * Professor: Nelson Souto Rosa
 *
 * @author Ana França
 * @author Lucas Albuquerque
 * @author Dicksson Oliveira
 * @author Edipo Andersen
 * @author Rodrigo Figueiredo
 */

public class QueueManagerProxy implements IQueueManager {
	
	private String queueName = null;
	private static final int queuePort = 1313;
	
	public QueueManagerProxy(String queueName){
		this.queueName = queueName;
	}
	
	public void add(String msg, String topic, String type) throws Exception{
		
		//TO-DO: Implement Invocation/Requestor
		
		ClientRequestHandler crh = new ClientRequestHandler("localhost", queuePort);
		Marshaller marshaller = new Marshaller();
		RequestPacket packet = new RequestPacket();
		Message message = new Message();
		
		message.setHeader(new PublisherHeader(this.queueName, topic, type));
		message.setBody(new PublisherBody(msg));
		
		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);
		
		packetBody.setParameters(parameters);
		packetBody.setMessage(message);
		packet.setHeader(new RequestPacketHeader("ADD", "localhost", queuePort));;
		packet.setBody(packetBody);
		
		crh.send(marshaller.marshall((Object)packet));
		
		return;
	}

	@Override
	public void subscribe(ArrayList<String> topicList, ArrayList<String> filterList, ArrayList<String> typeList) 
			throws Exception{

		ClientRequestHandler crh = new ClientRequestHandler("localhost", queuePort);
		Marshaller marshaller = new Marshaller();
		RequestPacket packet = new RequestPacket();
		Message message = new Message();

		message.setHeader(new SubscriberHeader(this.queueName));
		message.setBody(new SubscriberBody(topicList, filterList, typeList));
		
		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);
		
		packetBody.setParameters(parameters);
		packetBody.setMessage(message);
		packet.setHeader(new RequestPacketHeader("SUBSCRIBE", "localhost", queuePort));;
		packet.setBody(packetBody);
		
		crh.send(marshaller.marshall((Object)packet));
		
		return;
		
	}

	public boolean check() throws Exception{

		ClientRequestHandler crh = new ClientRequestHandler("localhost", queuePort);
		Marshaller marshaller = new Marshaller();
		RequestPacket packet = new RequestPacket();
		Message message = new Message();
		
		message.setHeader(new SubscriberHeader(this.queueName));
		message.setBody(new SubscriberBody(null,null,null));
		
		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);
		
		packetBody.setParameters(parameters);
		packetBody.setMessage(message);
		packet.setHeader(new RequestPacketHeader("CHECK", "localhost", queuePort));;
		packet.setBody(packetBody);
		
		crh.send(marshaller.marshall((Object)packet));

		byte[] packetUnmarshalled = new byte[1024];
		ReplyPacket replyPacketMarshalled = new ReplyPacket();
		
		packetUnmarshalled = crh.receive();
		replyPacketMarshalled = (ReplyPacket) marshaller.unmarshall(packetUnmarshalled);
		
		return false;
		
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
	public void listen() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
}
