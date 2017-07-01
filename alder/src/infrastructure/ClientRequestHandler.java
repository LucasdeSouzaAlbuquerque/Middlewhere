package infrastructure;

import java.io.*;
import java.net.*;

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

public class ClientRequestHandler {

	private String host;
	private int port;
	
	int sentSize;
	int receiveSize;
	
	Socket clientSocket = null;
	DataInputStream in = null;
	DataOutputStream out = null;
	
	public ClientRequestHandler(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void send(byte[] msg) throws Exception{
		
		clientSocket = new Socket(this.host, this.port);
		in = new DataInputStream(clientSocket.getInputStream());
		out = new DataOutputStream(clientSocket.getOutputStream());
		
		sentSize = msg.length;
		out.writeInt(sentSize);
		out.write(msg, 0, sentSize);
		out.flush();
		
	}
	
	public byte[] receive() throws Exception{
		
		byte[] msg = null;
		
		receiveSize = in.readInt();
		msg = new byte[receiveSize];
		in.read(msg, 0, receiveSize);
		
		clientSocket.close();
		out.close();
		in.close();
		
		return msg;
		
	}
	
}
