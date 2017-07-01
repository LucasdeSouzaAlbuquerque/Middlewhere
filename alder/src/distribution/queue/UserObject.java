package distribution.queue;

import java.util.ArrayList;

public class UserObject {
	
	private String name;
	private String host;
	private int port;
	private ArrayList<String> topicList;
	private ArrayList<String> filterList; 
	private ArrayList<String> typeList;
	
	public UserObject(String name, String host, int port, 
			ArrayList<String> topicList, ArrayList<String> filterList, ArrayList<String> typeList){
		this.name = name;
		this.host = host;
		this.port = port;
		this.topicList = topicList;
		this.filterList = filterList;
		this.typeList = typeList;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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
}
