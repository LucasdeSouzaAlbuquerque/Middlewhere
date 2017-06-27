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
		
		System.out.println("Criei o servidor de filas/t�picos sei l� buguei legal ahaiii mensagem de teste");
		QueueManager queueManager = new QueueManager(port);
		queueManager.run();
		
	}
	
}
