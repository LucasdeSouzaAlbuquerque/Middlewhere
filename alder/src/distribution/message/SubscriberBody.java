package distribution.message;

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

public class SubscriberBody extends Body implements Serializable {
	
	private String topicList;
	private String filterList;
	private String typeList;
	private static final long serialVersionUID = 1L;
	
	public SubscriberBody(String topicList, String filterList, String typeList) {
		super("Subscribing");
		this.topicList = topicList;
		this.filterList = filterList;
		this.typeList = typeList;
	}

	public String toString(){
		return "#"+super.getBody()+"#"+topicList+"#"+filterList+"#"+typeList+"#";
	}
	
}
