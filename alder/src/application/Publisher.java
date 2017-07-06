package application;

import java.io.*;
import java.util.*;

import distribution.message.Message;
import distribution.queue.QueueManagerProxy;

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

public class Publisher {
	
	String publisherName;
	QueueManagerProxy queueManagerProxy;
	

	public Publisher(String publisherName) throws Exception{
		this.publisherName = publisherName; 
		this.queueManagerProxy = new QueueManagerProxy(publisherName, 8081);
	}
	
	public void send(String message, List<String> topic, String type) throws Exception{
		this.queueManagerProxy.add(message, topic, type);
	}
	
	public static void main(String[] args) throws Exception{

		Scanner in = new Scanner(System.in);
		System.out.print("Publisher Name: ");
		String name = in.nextLine();

		Publisher publisher = new Publisher(name);	
		
		while(true){
			System.out.println(".:.:.:.:.:.:.:.: Let's create a message .:.:.:.:.:.:.:.:");
			
			System.out.print("Type in your message (-1 to close): ");
			String message = in.nextLine();
			
			if(message.equals("-1")){
				break;
			}
			
			System.out.print("Type in your topic: ");
			String topic_item = in.nextLine();
			List<String> topic = new ArrayList<>();
			topic.add(topic_item);
			
			System.out.print("Type in the type: ");
			String type = in.nextLine();
			
			publisher.send(message,topic,type);
		}
		in.close();
	}
	
}
