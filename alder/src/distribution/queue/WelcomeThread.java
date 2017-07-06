package distribution.queue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import distribution.marshaller.Marshaller;
import distribution.message.PublisherBody;
import distribution.message.PublisherHeader;
import distribution.message.SubscriberBody;
import distribution.reply.ReplyPacket;
import distribution.reply.ReplyPacketBody;
import distribution.reply.ReplyPacketHeader;
import distribution.request.RequestPacket;
import infrastructure.ServerRequestHandler;

public class WelcomeThread implements Runnable{
	
	private List<UserObject> users;
	private List<NewsObject> news;
	DateTimeFormatter dtf;
	int port;
	
	public WelcomeThread(List<UserObject> users, List<NewsObject> news, int port){
		this.users = users;
		this.news = news;
		this.port = port;
		this.dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	}
	
	@Override
	public void run() {
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
					case "ADD":
						PublisherHeader publishHeader = (PublisherHeader) requestPacketMarshalled.getBody().getMessage().getHeader();
						PublisherBody publishBody = (PublisherBody) requestPacketMarshalled.getBody().getMessage().getBody();
	
						String publisherName =  publishHeader.getDestination();
						String type = publishHeader.getType();
						List<String> topic = publishHeader.getTopic();
						String content = publishBody.getMessage();
						
						NewsObject newsObj =  new NewsObject(publisherName, type, topic, content);
						checkRelevance(newsObj);
						news.add(newsObj);
						
						System.out.println("[Add Message] " + dtf.format(LocalDateTime.now()));
						System.out.println("  Publisher Info .:.:.:.:.:.:.:.:.:.:.:.:.:.:");
						System.out.println("           Name: " + publisherName);
						System.out.println("           Type: " + type);
						System.out.println("           Topic: " + topic.toString());
						System.out.println("           Message: " + content);
						System.out.println();

						break;
						
					case "SUBSCRIBE":					
						SubscriberBody requestPacketBody = (SubscriberBody) requestPacketMarshalled.getBody().getMessage().getBody();
	
						int port = requestPacketMarshalled.getHeader().getPort();
						String host = requestPacketMarshalled.getHeader().getHost();
						String name = requestPacketMarshalled.getBody().getMessage().getHeader().getDestination();
						ArrayList<String> topicList = requestPacketBody.getTopicList();
						ArrayList<String> filterList = requestPacketBody.getFilterList();
						ArrayList<String> typeList = requestPacketBody.getTypeList();
	
						UserObject userObj = new UserObject(name, host, port, topicList, filterList, typeList);
						users.add(userObj);
						
						System.out.println("[Subscribe Message] " + dtf.format(LocalDateTime.now()));
						System.out.println("  Subscribe Info .:.:.:.:.:.:.:.:.:.:.:.:.:.:");
						System.out.println("           Name: " + name);
						System.out.println("           Type(s): " + typeList);
						System.out.println("           Topic(s): " + topicList);
						System.out.println("           Filter(s): " + filterList);
						System.out.println();
						
						break;
						
					case "CHECK":
						String user = requestPacketMarshalled.getBody().getMessage().getHeader().getDestination();
						String userHost = requestPacketMarshalled.getHeader().getHost();
						int userPort = requestPacketMarshalled.getHeader().getPort();
						boolean exists = exists(user);
	
						if(exists) {
							updateHostAndPortUser(user, userHost, userPort);
						}
	
						ReplyPacket packet = new ReplyPacket();
						ReplyPacketBody packetBody = new ReplyPacketBody();
						ArrayList<Object> parameters = new ArrayList<Object>(0);
						
						packetBody.setParameters(parameters);
						packetBody.setMessage(exists);
						packet.setHeader(new ReplyPacketHeader("EXISTS"));
						packet.setBody(packetBody);
	
						srh.send(marshaller.marshall((Object)packet));
						
						System.out.println("[Check User] " + dtf.format(LocalDateTime.now()));
						System.out.println("  .:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:");
						if(exists){
							System.out.println("           User " + user + " already added!");
						} else {
							System.out.println("           Adding new user ");
						}
						System.out.println();
						
						break;
	
					 default:
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized boolean exists(String userCheck){
		Iterator<UserObject> i = users.iterator(); 

		while (i.hasNext()){
			if(i.next().getName().equals(userCheck)){
				return true;
			}
		}
		return false;
	}
	
	public synchronized void checkRelevance(NewsObject news){
		Iterator<UserObject> i = users.iterator(); 
		UserObject user;
		
		while (i.hasNext()){
			user = i.next();
			ArrayList<String> topicList = user.getTopicList();
			ArrayList<String> typeList = user.getTypeList();
			ArrayList<String> filterList = user.getFilterList(); 
			
			if(hasTopic(news.getTopic(), topicList) ||
					hasType(news.getType(), typeList) ||
					hasFilter(news.getContent(), filterList)) {
				news.getUserObjectList().add(user);
			}
			
		}
	}
	
	public boolean hasTopic(List<String> topicNews, ArrayList<String> topicList){
		for (String topic : topicList){
			if(topicNews.contains(topic)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasType(String typeNews, ArrayList<String> typeList){
		for(String l : typeList){
			if (typeNews.equals(l)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasFilter(String content, ArrayList<String> filters) {
		for(String filter : filters) {
			if(content.contains(filter)) {
				return true;
			}
		}
		return false;
	}
	
	public void updateHostAndPortUser(String user, String userHost, int userPort){
		for(UserObject object: this.users){
			if(object.getName().equals(user)){
				object.setHost(userHost);
				object.setPort(userPort);
			}
		}
	}
}
