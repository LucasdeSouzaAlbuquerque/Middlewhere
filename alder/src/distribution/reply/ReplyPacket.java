package distribution.reply;

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

public class ReplyPacket implements Serializable {
	private ReplyPacketHeader header;
	private ReplyPacketBody body;
	private static final long serialVersionUID = 1L;
	
	public ReplyPacket() {}

	public ReplyPacketHeader getHeader() {
		return header;
	}

	public void setHeader(ReplyPacketHeader header) {
		this.header = header;
	}

	public ReplyPacketBody getBody() {
		return body;
	}

	public void setBody(ReplyPacketBody body) {
		this.body = body;
	}
	
	
}
