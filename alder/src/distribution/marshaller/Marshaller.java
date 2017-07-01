package distribution.marshaller;

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

public class Marshaller {
	
	public byte[] marshall(Object msg) throws Exception{	
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream outStream = new ObjectOutputStream(byteStream);

		outStream.writeObject(msg);
		byte[] marshalledMessage = byteStream.toByteArray();

		return marshalledMessage;
	}
	
	public Object unmarshall(byte[] msg) throws Exception{
		ByteArrayInputStream stream = new ByteArrayInputStream(msg);
		ObjectInputStream obj = new ObjectInputStream(stream);
		return obj.readObject();
	}
	
	
	
}
