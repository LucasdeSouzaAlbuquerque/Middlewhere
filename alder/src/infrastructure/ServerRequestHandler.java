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

public class ServerRequestHandler {
	
	private int port;
	private ServerSocket welcomeSocket = null;
	
	Socket connectionSocket = null;
	
	int sentSize;
	int receiveSize;
	DataInputStream in = null;
	DataOutputStream out = null;
	
	public ServerRequestHandler(int port) throws Exception {
		this.port = port;
		this.welcomeSocket = new ServerSocket(this.port);
	}
	
	public byte[] receive() throws Exception {
		
		byte[] msg = null;
		
		connectionSocket = welcomeSocket.accept();
		in = new DataInputStream(connectionSocket.getInputStream());
		out = new DataOutputStream(connectionSocket.getOutputStream());
		
		receiveSize = in.readInt();
		msg = new byte[receiveSize];
		in.read(msg, 0, receiveSize);;
		
		return msg;
		
	}
	
	public void send(byte[] msg) throws Exception {
		
		sentSize = msg.length;
		out.writeInt(sentSize);
		out.write(msg, 0, sentSize);
		out.flush();
		
		connectionSocket.close();
		in.close();
		out.close();
		
	}
	
	
}
