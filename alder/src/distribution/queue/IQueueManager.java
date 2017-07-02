package distribution.queue;

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

public interface IQueueManager {
	
	public void add(String msg, List<String> topic, String type) throws Exception;
	public void subscribe(ArrayList<String> topic, ArrayList<String> filter, ArrayList<String> type) throws Exception;
	public boolean check() throws Exception;
	public void listen() throws Exception;
	//public void signal(boolean listening);
	
}
