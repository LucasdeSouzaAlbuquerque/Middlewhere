package distribution.queue;

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

public class QueueServer {
	
	static private final int port = 1313;
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Started server...");
		QueueManager queueManager = new QueueManager(port);
		queueManager.run();
		
	}
	
}
