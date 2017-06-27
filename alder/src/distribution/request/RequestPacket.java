package distribution.request;

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

public class RequestPacket implements Serializable {
	private RequestPacketHeader header;
	private RequestPacketBody body;
	private static final long serialVersionUID = 1L;
	
	public RequestPacket() {}

	public RequestPacketHeader getHeader() {
		return header;
	}

	public void setHeader(RequestPacketHeader header) {
		this.header = header;
	}

	public RequestPacketBody getBody() {
		return body;
	}

	public void setBody(RequestPacketBody body) {
		this.body = body;
	}
	
	
}
