package distribution.queue;

import java.util.*;

import distribution.message.Message;

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

public class Queue {
	
	private ArrayList<Message> queue = new ArrayList<Message>();
	
	public Queue(){
	}
	
	public void add(Message msg){
		queue.add(msg);
	}
	
	public Message get(){
		Message t = queue.get(0);
		return t;
	}
	
	public Message pop(){
		Message t = queue.get(0);
		queue.remove(0);
		return t;	
	}	
	
	public int size(){
		return queue.size();
	}
	
}
