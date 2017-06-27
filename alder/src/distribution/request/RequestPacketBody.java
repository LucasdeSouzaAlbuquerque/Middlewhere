package distribution.request;

import distribution.message.*;
import java.io.*;
import java.util.*;

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

public class RequestPacketBody implements Serializable {
	
	private ArrayList<Object> parameters = new ArrayList<Object>();
	private Message message;
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Object> getParameters() {
		return parameters;
	}
	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
}
