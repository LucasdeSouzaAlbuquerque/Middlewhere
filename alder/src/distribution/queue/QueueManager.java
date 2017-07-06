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
		this.port = port;
		this.host = InetAddress.getLocalHost().getHostName();
		this.users = Collections.synchronizedList(new ArrayList<UserObject>());
		this.news = Collections.synchronizedList(new ArrayList<NewsObject>());
	}

	public void run() {
		WelcomeThread welcomeThread = new WelcomeThread(users, news, port);
		PostmanThread postmanThread = new PostmanThread(news);
		new Thread (welcomeThread).start();
		new Thread (postmanThread).start();
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
