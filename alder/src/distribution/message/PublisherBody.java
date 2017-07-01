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

public class PublisherBody extends Body implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public PublisherBody(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getBody();
	}

	public void setMessage(String message) {
		super.setBody(message);
	}
	
	public String toString(){
		return "#"+super.getBody()+"#";
	}
	
}
