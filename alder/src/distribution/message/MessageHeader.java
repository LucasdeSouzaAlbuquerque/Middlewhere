package distribution.message;

import java.io.Serializable;

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

public class MessageHeader implements Serializable {

	private String destination;
	private String topic;
	private static final long serialVersionUID = 1L;

	public MessageHeader(String destination, String topic){
		this.destination = destination;
		this.topic = topic;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;		
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String toString(){
		return "#"+this.destination+"#"+this.topic+"#";
	}
	
}
