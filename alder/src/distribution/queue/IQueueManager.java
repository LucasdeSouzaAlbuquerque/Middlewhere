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
	
	public void add(String msg, String topic, String type) throws Exception;
	public void subscribe(String topic, String filter, String type) throws Exception;
	public void check() throws Exception;
	//public void signal(boolean listening);
	
}
