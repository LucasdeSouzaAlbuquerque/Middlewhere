package distribution.queue;

import java.io.*;
import java.net.*;
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

public class QueueManager {
	
	private String host;
	private int port;
	private Map<String, Queue> queues = new HashMap<String, Queue>();
	
	public QueueManager(int port) throws Exception{
		this.host = InetAddress.getLocalHost().getHostName();
		this.port = port;
	}
	
	public void run(){
		while(true){
			System.out.println("Running, running, running");
		}
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
	
}
