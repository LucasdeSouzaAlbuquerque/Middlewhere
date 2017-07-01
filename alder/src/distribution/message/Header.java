package distribution.message;

import java.io.*;

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

public abstract class Header implements Serializable {

	private String destination;
	private static final long serialVersionUID = 1L;
	
	public Header(String destination){
		this.destination = destination;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;		
	}
	
	public String toString(){
		return "#"+this.destination+"#";
	}
	
}
