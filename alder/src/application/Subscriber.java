package application;

import java.io.*;

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
	
	public Subscriber() {
		this.queueManagerProxy = new QueueManagerProxy("Subscribe");
	}
	
	public void receive() throws Exception {
		//queueManagerProxy.receive();
	}
	
	public void subscribe(){
		
	}

	public static void main(String[] args) throws Exception {
		Subscriber subcriber = new Subscriber();
		subcriber.receive();
	}
	
}
