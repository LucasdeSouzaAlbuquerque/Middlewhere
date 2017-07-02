package distribution.queue;

import java.io.IOException;
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
	private String host;
	private int port;

	public PostmanThread(List<NewsObject> news, String host, int serverPort){
		this.news = news;
		this.host = host;
		this.port = serverPort;
	}

	@Override
	public void run() {

		try{
			byte[] packetUnmarshalled = new byte[1024];
			RequestPacket requestPacketMarshalled = new RequestPacket();
			Marshaller marshaller = new Marshaller();
			ClientRequestHandler crh;

			while(true){

				Thread.sleep(100);			

				for(NewsObject object: news){

					long currTime = System.currentTimeMillis();
					boolean allNull = true;
					
					for(UserObject userObject: object.getUserObjectList()){
						if(userObject != null){
							allNull = false;
							break;
						}
					}
					
					if(currTime - object.getAddTime() > 100000 || allNull){
						object = null;
					}else{
						UserObject userObject;
						for(int i = 0; i < object.getUserObjectList().size(); i++){
							
							userObject = object.getUserObjectList().get(i);
							if(userObject != null){
								crh = new ClientRequestHandler(userObject.getHost(), userObject.getPort());
	
								Message message = new Message();
								message.setHeader(new PublisherHeader(userObject.getName(),object.getTopic(),object.getType()));
								message.setBody(new PublisherBody(object.getContent()));
	
								RequestPacket packet = new RequestPacket();
								RequestPacketBody packetBody = new RequestPacketBody();
								ArrayList<Object> parameters = new ArrayList<Object>(0);
	
								packetBody.setParameters(parameters);
								packetBody.setMessage(message);
	
								packet.setHeader(new RequestPacketHeader("SEND", userObject.getHost(), userObject.getPort()));
								packet.setBody(packetBody);
	
								try{
									crh.send(marshaller.marshall((Object)packet));
									object.getUserObjectList().set(i, null);
								}catch(IOException e){
									
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

}
