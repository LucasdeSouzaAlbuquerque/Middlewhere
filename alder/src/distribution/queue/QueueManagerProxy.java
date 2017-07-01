package distribution.queue;

import infrastructure.*;
import distribution.marshaller.*;
import distribution.request.*;
import distribution.message.*;
import distribution.reply.ReplyPacket;

import java.io.*;
import java.net.InetAddress;
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
	private String host;
	private static int port;
	
	public QueueManagerProxy(String queueName, int port) throws Exception{
		this.queueName = queueName;
		this.host = InetAddress.getLocalHost().getHostName();
		this.port = port;
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
		packet.setHeader(new RequestPacketHeader("ADD", this.host, this.port));;
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
		packet.setHeader(new RequestPacketHeader("SUBSCRIBE", this.host, this.port));;
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
		packet.setHeader(new RequestPacketHeader("CHECK", this.host, this.port));;
		packet.setBody(packetBody);

		crh.send(marshaller.marshall((Object)packet));

		byte[] packetUnmarshalled = new byte[1024];
		ReplyPacket replyPacketMarshalled = new ReplyPacket();

		packetUnmarshalled = crh.receive();
		replyPacketMarshalled = (ReplyPacket) marshaller.unmarshall(packetUnmarshalled);

		return false;

	}

	@Override
	public void listen() throws Exception {

		byte[] packetUnmarshalled = new byte[1024];
		RequestPacket requestPacketMarshalled = new RequestPacket();
		Marshaller marshaller = new Marshaller();
		ServerRequestHandler srh;
		
		try {
			srh = new ServerRequestHandler(this.port);
			while(true){
				packetUnmarshalled = srh.receive();
				requestPacketMarshalled = (RequestPacket) marshaller.unmarshall(packetUnmarshalled);
				
				switch (requestPacketMarshalled.getHeader().getOperation()) {
					case "SEND":
						System.out.println("Sent message");
						System.out.println(requestPacketMarshalled.getBody().getMessage().getHeader().toString());
						System.out.println(requestPacketMarshalled.getBody().getMessage().getBody().toString());
						System.out.println(((PublisherBody) requestPacketMarshalled.getBody().getMessage().getBody()).getMessage());
						break;
					default:
						break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}	
}
