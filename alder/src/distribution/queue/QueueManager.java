package distribution.queue;

import java.io.*;
import java.net.*;
import java.util.*;

import distribution.marshaller.Marshaller;
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
		byte[] packetMarshalled = new byte[1024];
		
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
				case "RECEIVE":
					System.out.println("Receive message");
					break;
				default:
					break;
			}
		}
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
