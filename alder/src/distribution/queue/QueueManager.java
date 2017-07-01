package distribution.queue;

import java.io.*;
import java.net.*;
import java.util.*;

import distribution.marshaller.Marshaller;
import distribution.message.Message;
import distribution.message.SubscriberBody;
import distribution.message.SubscriberHeader;
import distribution.reply.ReplyPacket;
import distribution.reply.ReplyPacketBody;
import distribution.reply.ReplyPacketHeader;
import infrastructure.ServerRequestHandler;
import distribution.request.*;

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

public class QueueManager {
	
	private String host;
	private int port;
	private Map<String, Queue> queues = new HashMap<String, Queue>();
	
	public QueueManager(int port) throws Exception{
		this.host = InetAddress.getLocalHost().getHostName();
		this.port = port;
	}
	
	public void run() throws Exception{
		//Packet from Server
		byte[] packetUnmarshalled = new byte[1024];
		
		RequestPacket requestPacketMarshalled = new RequestPacket();
		Marshaller marshaller = new Marshaller();
		ServerRequestHandler srh = new ServerRequestHandler(this.port);
		
		while(true){
			System.out.println("Running, running, running");

			packetUnmarshalled = srh.receive();
		
			requestPacketMarshalled = (RequestPacket) marshaller.unmarshall(packetUnmarshalled);
			
			switch (requestPacketMarshalled.getHeader().getOperation()) {
				case "ADD":
					System.out.println("Sent message");
					System.out.println(requestPacketMarshalled.getBody().getMessage().getHeader().toString());
					System.out.println(requestPacketMarshalled.getBody().getMessage().getBody().toString());
					break;
				case "SUBSCRIBE":
					System.out.println("Sent subscription");
					System.out.println(requestPacketMarshalled.getBody().getMessage().getHeader().toString());
					System.out.println(requestPacketMarshalled.getBody().getMessage().getBody().toString());
					break;
				case "CHECK":
					System.out.println("Sent check");
					System.out.println(requestPacketMarshalled.getBody().getMessage().getHeader().toString());
					String user = requestPacketMarshalled.getBody().getMessage().getHeader().getDestination();
					boolean returning = exists(user);
					
					ReplyPacket packet = new ReplyPacket();
					ReplyPacketBody packetBody = new ReplyPacketBody();
					ArrayList<Object> parameters = new ArrayList<Object>(0);
					packetBody.setParameters(parameters);
					packetBody.setMessage(returning);
					
					packet.setHeader(new ReplyPacketHeader("EXISTS"));
					packet.setBody(packetBody);
					
					srh.send(marshaller.marshall((Object)packet));
					
				default:
					break;
			}
		}
	}
	
	public boolean exists(String user){
		return false;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}	
	
}
