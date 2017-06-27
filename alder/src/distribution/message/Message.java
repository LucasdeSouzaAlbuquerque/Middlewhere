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

public class Message implements Serializable {
	
	private MessageHeader header;
	private MessageBody body;
	
	private static final long serialVersionUID = 1L;
	
	public MessageHeader getHeader() {
		return header;
	}
	public void setHeader(MessageHeader header) {
		this.header = header;
	}
	public MessageBody getBody() {
		return body;
	}
	public void setBody(MessageBody body) {
		this.body = body;
	}
	public String toString(){
		return this.header.toString() + " - " + this.body.toString();
	}
	
	
	
	
}
