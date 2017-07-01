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

public class SubscriberHeader extends Header implements Serializable {

	private static final long serialVersionUID = 1L;

	public SubscriberHeader(String destination){
		super(destination);
	}

	public String getDestination() {
		return super.getDestination();
	}

	public void setDestination(String destination) {
		super.setDestination(destination);
	}

	public String toString(){
		return "#"+super.getDestination()+"#";
	}
	
}
