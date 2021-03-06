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

public class PublisherHeader extends Header implements Serializable {

	private String topic;
	private String type;
	private static final long serialVersionUID = 1L;

	public PublisherHeader(String destination, String topic, String type){
		super(destination);
		this.topic = topic;
		this.type = type;
	}

	public String getDestination() {
		return super.getDestination();
	}

	public void setDestination(String destination) {
		super.setDestination(destination);		
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString(){
		return "#"+super.getDestination()+"#"+this.topic+"#"+this.type+"#";
	}
	
}
