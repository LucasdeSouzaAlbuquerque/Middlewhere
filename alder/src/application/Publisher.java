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
	
	QueueManagerProxy queueManagerProxy;
	
	public Publisher(){
		this.queueManagerProxy = new QueueManagerProxy("publisherQueue");
	}
	
	public void send(String message) throws Exception{
		this.queueManagerProxy.send(message);
		
	}
	
	public static void main(String[] args) throws Exception{
		Publisher publisher = new Publisher();	
		publisher.send("message-01");
	}
	
}
