package distribution.queue;

import infrastructure.*;
import distribution.marshaller.*;
import distribution.request.*;
import distribution.message.*;

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
		packet.setHeader(new RequestPacketHeader("ADD"));;
		packet.setBody(packetBody);
		
		crh.send(marshaller.marshall((Object)packet));
		
		return;
	}

	@Override
	public void subscribe(String topicList, String filterList, String typeList) throws Exception{

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
		packet.setHeader(new RequestPacketHeader("SUBSCRIBE"));;
		packet.setBody(packetBody);
		
		crh.send(marshaller.marshall((Object)packet));
		
		return;
		
	}

	public void check() throws Exception{

		ClientRequestHandler crh = new ClientRequestHandler("localhost", queuePort);
		Marshaller marshaller = new Marshaller();
		RequestPacket packet = new RequestPacket();
		Message message = new Message();
		
		message.setHeader(new SubscriberHeader(this.queueName));
		message.setBody(new SubscriberBody("Empty","Empty","Empty"));
		
		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);
		
		packetBody.setParameters(parameters);
		packetBody.setMessage(message);
		packet.setHeader(new RequestPacketHeader("CHECK"));;
		packet.setBody(packetBody);
		
		crh.send(marshaller.marshall((Object)packet));
		
		return;
		
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	
	
}
