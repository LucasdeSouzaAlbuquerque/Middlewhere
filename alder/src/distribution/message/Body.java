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

public abstract class Body implements Serializable {
	
	private String body;	
	private static final long serialVersionUID = 1L;

	public Body(String message){
		this.body = message;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String message) {
		this.body = message;
	}
	
	public String toString(){
		return "#"+this.body+"#";
	}

}
