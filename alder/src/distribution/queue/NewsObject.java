package distribution.queue;

import java.util.ArrayList;
import java.util.List;

public class NewsObject {
	
	private String publisherName;
	private String type;
	private String topic;//TODO: List
	private String content;
	private long addTime;
	private List<UserObject> userObjectList;
	
	public NewsObject(String publisherName, String type, String topic, String content){
		this.publisherName = publisherName;
		this.type = type;
		this.topic = topic;
		this.content = content;
		this.userObjectList = new ArrayList<UserObject>();
		this.addTime = System.currentTimeMillis();
	}
	
	public List<UserObject> getUserObjectList() {
		return userObjectList;
	}

	public void setUserObjectList(List<UserObject> userObjectList) {
		this.userObjectList = userObjectList;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public long getAddTime() {
		return addTime;
	}
}
