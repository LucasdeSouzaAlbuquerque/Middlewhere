package distribution.queue;

import java.io.*;
import java.net.*;
import java.util.*;

import distribution.marshaller.Marshaller;
import distribution.message.Message;
import distribution.message.PublisherBody;
import distribution.message.PublisherHeader;
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
	private List<UserObject> users;
	private List<NewsObject> news;	

	public QueueManager(int port) throws Exception{
		this.host = InetAddress.getLocalHost().getHostName();
		this.port = port;
		this.users = Collections.synchronizedList(new ArrayList<UserObject>());
		this.news = Collections.synchronizedList(new ArrayList<NewsObject>());
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
				PublisherHeader publishHeader = (PublisherHeader) requestPacketMarshalled.getBody().getMessage().getHeader();
				PublisherBody publishBody = (PublisherBody) requestPacketMarshalled.getBody().getMessage().getBody();

				String publisherName =  publishHeader.getDestination();
				String type = publishHeader.getType();
				String topic = publishHeader.getTopic();
				String content = publishBody.getMessage();
				
				NewsObject newsObj =  new NewsObject(publisherName, type, topic, content);
				news.add(newsObj);
				
				
				break;
			case "SUBSCRIBE":
				System.out.println("Sent subscription");
				System.out.println(requestPacketMarshalled.getBody().getMessage().getHeader().toString());
				System.out.println(requestPacketMarshalled.getBody().getMessage().getBody().toString());

				SubscriberBody requestPacketBody = (SubscriberBody) requestPacketMarshalled.getBody().getMessage().getBody();

				int port = requestPacketMarshalled.getHeader().getPort();
				String host = requestPacketMarshalled.getHeader().getHost();
				String name = requestPacketMarshalled.getBody().getMessage().getHeader().getDestination();
				ArrayList<String> topicList = requestPacketBody.getTopicList();
				ArrayList<String> filterList = requestPacketBody.getFilterList();
				ArrayList<String> typeList = requestPacketBody.getTypeList();

				UserObject userObj = new UserObject(name, host, port, topicList, filterList, typeList);
				users.add(userObj);

				break;
			case "CHECK":
				System.out.println("Sent check");
				System.out.println(requestPacketMarshalled.getBody().getMessage().getHeader().toString());
				String user = requestPacketMarshalled.getBody().getMessage().getHeader().getDestination();

				boolean exists = exists(user);

				//TODO: if exists update host and port user

				ReplyPacket packet = new ReplyPacket();
				ReplyPacketBody packetBody = new ReplyPacketBody();
				ArrayList<Object> parameters = new ArrayList<Object>(0);
				packetBody.setParameters(parameters);
				packetBody.setMessage(exists);

				packet.setHeader(new ReplyPacketHeader("EXISTS"));
				packet.setBody(packetBody);

				srh.send(marshaller.marshall((Object)packet));
				break;

			default:
				break;
			}
		}
	}

	public synchronized boolean exists(String userCheck){

		Iterator<UserObject> i = users.iterator(); // Must be in synchronized block

		while (i.hasNext()){
			if(i.next().getName().equals(userCheck)){
				return true;
			}
		}
		return false;
	}

	public void updateHostAndPort(String host, int port){
		//TODO
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

	public List<UserObject> getUsers() {
		return users;
	}

	public void setUsers(List<UserObject> users) {
		this.users = users;
	}

	public List<NewsObject> getNews() {
		return news;
	}

	public void setNews(List<NewsObject> news) {
		this.news = news;
	}	
}
