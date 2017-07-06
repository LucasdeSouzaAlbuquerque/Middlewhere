package application;

import java.util.ArrayList;
import java.util.Scanner;

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

public class Subscriber {
	
	QueueManagerProxy queueManagerProxy;	
	
	public Subscriber(String name, int port) throws Exception {
		this.queueManagerProxy = new QueueManagerProxy(name, port);
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
		System.out.print("Subscriber name: ");
		String name = in.nextLine();
		
		System.out.print("Type in the port: ");
		int port = in.nextInt();
		in.nextLine();
		
		Subscriber subscriber = new Subscriber(name, port);	
		
		boolean exists = subscriber.check();

		if(!exists){
			ArrayList<String> topicList = new ArrayList<String>();
			System.out.println("Type a topic you are interested on! [Enter] -add // [-1] -next");
			while(true){
				String topic = in.nextLine();
				if(topic.equals("-1")){
					break;
				}
				topicList.add(topic);			
			}
		
			ArrayList<String> filterList = new ArrayList<String>();
			System.out.println("Type any keywords you are interested on! [Enter] -add // [-1] -next");
			System.out.println("  [You will receive any message that has one of those keywords]");
			while(true){
				String filter = in.nextLine();
				if(filter.equals("-1")){
					break;
				}
				filterList.add(filter);			
			}
			
			ArrayList<String> typeList = new ArrayList<String>();
			System.out.println("Type a 'message type' you are interested on! [Enter] -add // [-1] -next");
			System.out.println(" [Examples of message types: 's-a']");
			while(true){
				String type = in.nextLine();
				if(type.equals("-1")){
					break;
				}
				typeList.add(type);			
			}
			subscriber.subscribe(topicList, filterList, typeList);	
		} else {
			System.out.println(" .:.:.:.:.:.:.:.: You are already a user! .:.:.:.:.:.:.:.:");
		}
		subscriber.receive();
		in.close();	
	}
}
