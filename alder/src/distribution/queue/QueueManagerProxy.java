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
	
	public void send(String msg) throws Exception{
		
		ClientRequestHandler crh = new ClientRequestHandler("localhost", queuePort);
		Marshaller marshaller = new Marshaller();
		RequestPacket packet = new RequestPacket();
		Message message = new Message();
		
		message.setHeader(new MessageHeader(this.queueName, "TOPIC1"));
		message.setBody(new MessageBody(msg));
		
		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);
		
		packetBody.setParameters(parameters);
		packetBody.setMessage(message);
		packet.setHeader(new RequestPacketHeader("SEND"));;
		packet.setBody(packetBody);
		
		crh.send(marshaller.marshall((Object)packet));
		
		return;
	}
	
	public String receive() throws Exception{
		
		ClientRequestHandler crh = new ClientRequestHandler("localhost", 1313);
		Marshaller marshaller = new Marshaller();
		RequestPacket requestPacket = new RequestPacket();
		byte[] unmarshalledReplyPacket = new byte[1024];
		Message message = new Message();

		// configure message
		message.setHeader(new MessageHeader(this.queueName, "TOPIC1"));
		message.setBody(new MessageBody("messageBody"));

		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);
		packetBody.setParameters(parameters);
		packetBody.setMessage(message);
		requestPacket.setHeader(new RequestPacketHeader("RECEIVE"));
		requestPacket.setBody(packetBody);

		crh.send(marshaller.marshall((Object) requestPacket));

		unmarshalledReplyPacket = crh.receive();
		
		//TODO
		return "";
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
}
