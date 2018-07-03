package action;
import java.io.*;
import java.net.*;
public class SubHostResponse 
{
	public SubHostResponse(int port,BufferTransfer bufferTransfer)
	{
		Socket socket;
		ObjectOutput dataout;
		try
		{	
 			System.out.println("Client Communicate with appropriate port");
			socket=new Socket("127.0.0.1",port);// Made socket connection on the port number 6789 in the host 127.0.01	
			dataout=new ObjectOutputStream(socket.getOutputStream());// Bind socket stream with Buffered stream
			dataout.writeObject(bufferTransfer);
			dataout.flush();
			socket.close();
		}
		catch(Exception error)
		{
			System.out.println("Error in Client"+error);
		}		
	}
}