package distribution.queue;

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
	int port;
	
	public WelcomeThread(List<UserObject> users, List<NewsObject> news, int port){
		this.users = users;
		this.news = news;
		this.port = port;
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
				System.out.println("Running, running, running");

				packetUnmarshalled = srh.receive();
				requestPacketMarshalled = (RequestPacket) marshaller.unmarshall(packetUnmarshalled);

				switch (requestPacketMarshalled.getHeader().getOperation()) {
					case "ADD":
						System.out.println("Sent message");
						System.out.println(requestPacketMarshalled.getBody().getMessage().getHeader().toString());
						System.out.println(requestPacketMarshalled.getBody().getMessage().getBody().toString());
						System.out.println("###");
						PublisherHeader publishHeader = (PublisherHeader) requestPacketMarshalled.getBody().getMessage().getHeader();
						PublisherBody publishBody = (PublisherBody) requestPacketMarshalled.getBody().getMessage().getBody();
	
						String publisherName =  publishHeader.getDestination();
						String type = publishHeader.getType();
						String topic = publishHeader.getTopic();
						String content = publishBody.getMessage();
						
						NewsObject newsObj =  new NewsObject(publisherName, type, topic, content);
						checkRelevance(newsObj);
						
						news.add(newsObj);
						
						break;
						
					case "SUBSCRIBE":
						System.out.println("Sent subscription");
						System.out.println(((SubscriberBody) requestPacketMarshalled.getBody().getMessage().getBody()).getTopicList());
						System.out.println(((SubscriberBody) requestPacketMarshalled.getBody().getMessage().getBody()).getFilterList());
						System.out.println(((SubscriberBody) requestPacketMarshalled.getBody().getMessage().getBody()).getTypeList());
						System.out.println("###");
						
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
						String user = requestPacketMarshalled.getBody().getMessage().getHeader().getDestination();
						System.out.println(user + " - CHECK");
						String userHost = requestPacketMarshalled.getHeader().getHost();
						int userPort = requestPacketMarshalled.getHeader().getPort();
						
						boolean exists = exists(user);
	
						if(exists) {
							for(UserObject object: users){
								if(object.getName().equals(user)){
									object.setHost(userHost);
									object.setPort(userPort);
								}
							}
						}
	
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
			
			if(hasTopicOrType(news.getTopic(), topicList) ||
					hasTopicOrType(news.getType(), typeList) ||
					hasFilter(news.getContent(), filterList)) {
				news.getUserObjectList().add(user);
			}
			
		}
	}
	
	public boolean hasTopicOrType(String news, ArrayList<String> list){//TODO
		for(String l : list){
			if (news.equals(l)){
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
}
