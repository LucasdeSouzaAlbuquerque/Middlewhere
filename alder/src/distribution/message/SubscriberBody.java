package distribution.message;

import java.io.*;
import java.util.ArrayList;

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
	
	private ArrayList<String> topicList;
	private ArrayList<String> filterList;
	private ArrayList<String> typeList;
	private static final long serialVersionUID = 1L;
	
	public SubscriberBody(ArrayList<String> topicList, ArrayList<String> filterList, ArrayList<String> typeList) {
		super("Subscribing");
		this.topicList = topicList;
		this.filterList = filterList;
		this.typeList = typeList;
	}
	
	public ArrayList<String> getTopicList() {
		return topicList;
	}



	public void setTopicList(ArrayList<String> topicList) {
		this.topicList = topicList;
	}



	public ArrayList<String> getFilterList() {
		return filterList;
	}



	public void setFilterList(ArrayList<String> filterList) {
		this.filterList = filterList;
	}



	public ArrayList<String> getTypeList() {
		return typeList;
	}



	public void setTypeList(ArrayList<String> typeList) {
		this.typeList = typeList;
	}



	public String toString(){
		return "#"+super.getBody()+"#"+topicList+"#"+filterList+"#"+typeList+"#";
	}
	
}
