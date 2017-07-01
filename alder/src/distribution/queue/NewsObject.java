package distribution.queue;

public class NewsObject {
	
	private String publisherName;
	private String type;
	private String topic;
	private String content;
	
	public NewsObject(String publisherName, String type, String topic, String content){
		this.publisherName = publisherName;
		this.type = type;
		this.topic = topic;
		this.content = content;
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
}
