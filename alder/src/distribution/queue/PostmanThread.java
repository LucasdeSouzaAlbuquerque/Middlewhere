package distribution.queue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import distribution.marshaller.Marshaller;
import distribution.message.Message;
import distribution.message.PublisherBody;
import distribution.message.PublisherHeader;
import distribution.request.RequestPacket;
import distribution.request.RequestPacketBody;
import distribution.request.RequestPacketHeader;
import infrastructure.ClientRequestHandler;

public class PostmanThread implements Runnable{
	private List<NewsObject> news;
	DateTimeFormatter dtf;

	public PostmanThread(List<NewsObject> news){
		this.news = news;
		this.dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	}

	@Override
	public void run() {

		try{
			Marshaller marshaller = new Marshaller();
			ClientRequestHandler crh;

			while(true){

				Thread.sleep(100);			

				for(NewsObject newsItem: news){
					long currTime = System.currentTimeMillis();
					boolean existsUserToSendNews = existsUserToSendNews(newsItem.getUserObjectList());
					
					//Verify if message timeout is valid or if exists users to send news 
					if(currTime - newsItem.getAddTime() > 100000 || !existsUserToSendNews){
						newsItem = null;
					}else{
						UserObject userObject;
						for(int i = 0; i < newsItem.getUserObjectList().size(); i++){
							userObject = newsItem.getUserObjectList().get(i);
							
							if(userObject != null){
								crh = new ClientRequestHandler(userObject.getHost(), userObject.getPort());
	
								Message message = new Message();
								message.setHeader(new PublisherHeader(userObject.getName(), newsItem.getTopic(), newsItem.getType()));
								message.setBody(new PublisherBody(newsItem.getContent()));
	
								RequestPacket packet = new RequestPacket();
								RequestPacketBody packetBody = new RequestPacketBody();
								RequestPacketHeader packetHeader = new RequestPacketHeader("SEND", userObject.getHost(), userObject.getPort());
								ArrayList<Object> parameters = new ArrayList<Object>(0);
	
								packetBody.setParameters(parameters);
								packetBody.setMessage(message);
								packet.setHeader(packetHeader);
								packet.setBody(packetBody);
	
								try{
									crh.send(marshaller.marshall((Object)packet));
									newsItem.getUserObjectList().set(i, null);
									
									System.out.println("[Send Message] " + dtf.format(LocalDateTime.now()));
									System.out.println("  Sendig message to " + userObject.getName());
									System.out.println();
								}catch(IOException e){
									//TODO: Tratar exception quando subcriber tiver off!
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean existsUserToSendNews(List<UserObject> userList){
		boolean existsUserToSendNews = false;
		
		for(UserObject user: userList){
			if(user != null){
				existsUserToSendNews = true;
				break;
			}
		}	
		return existsUserToSendNews;
	}
}
