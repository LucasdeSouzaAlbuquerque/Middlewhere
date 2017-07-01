package distribution.reply;

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

public class ReplyPacketBody implements Serializable {
	
	private ArrayList<Object> parameters = new ArrayList<Object>();
	private boolean message;
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Object> getParameters() {
		return parameters;
	}
	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}
	public boolean getMessage() {
		return message;
	}
	public void setMessage(boolean message) {
		this.message = message;
	}
	
}
