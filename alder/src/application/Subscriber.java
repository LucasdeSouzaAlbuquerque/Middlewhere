package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import distribution.marshaller.Marshaller;
import distribution.queue.QueueManagerProxy;
import distribution.request.RequestPacket;
import infrastructure.ServerRequestHandler;

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

public class Subscriber {
	
	String subscriberName;
	QueueManagerProxy queueManagerProxy;
	
	
	public Subscriber(String name) {
		this.queueManagerProxy = new QueueManagerProxy(name);
		this.subscriberName = name;
	}
	
	public void receive() throws Exception {
		queueManagerProxy.listen();
	}
	
	public void subscribe(ArrayList<String> topicList, ArrayList<String> filterList, ArrayList<String> typeList) throws Exception{
		this.queueManagerProxy.subscribe(topicList, filterList, typeList);
	}
	
	public boolean check() throws Exception{
		return queueManagerProxy.check();
	}

	public static void main(String[] args) throws Exception {

		Scanner in = new Scanner(System.in);
		System.out.println("Type in the name of this Subscriber Legal: ");
		String name = in.nextLine();

		Subscriber subscriber = new Subscriber(name);	
		
		boolean exists = subscriber.check();
		System.out.println("Exists: "+exists);
		
		if(!exists){
			ArrayList<String> topicList = new ArrayList<String>();
			while(true){
				
				System.out.println("Type a topic you are interested on! (-1 to close)");
				String topic = in.nextLine();
				if(topic.equals("-1")){
					break;
				}
				topicList.add(topic);			
			}
		
			ArrayList<String> filterList = new ArrayList<String>();
			while(true){
				
				System.out.println("Type any keywords you are interested on! (-1 to close)");
				System.out.println("You will receive any message that has one of those keywords");
				String filter = in.nextLine();
				if(filter.equals("-1")){
					break;
				}
				filterList.add(filter);			
			}
			
			ArrayList<String> typeList = new ArrayList<String>();
			while(true){
				
				System.out.println("Type a 'message type' you are interested on! (-1 to close)");
				System.out.println("Examples of message types: 's-a'");
				String type = in.nextLine();
				if(type.equals("-1")){
					break;
				}
				typeList.add(type);			
			}
			
			subscriber.subscribe(topicList, filterList, typeList);
			//subscriber.receive();
			
		}
		in.close();
		
	}
	
}
