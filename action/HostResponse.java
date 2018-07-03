package action;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;
public class HostResponse implements Runnable 
{
	int port;
	Thread thread;
	public HostResponse(int port)
	{
		this.port=port;				//Assign port
		thread=new Thread(this);
		thread.start();		
	}
	public HostResponse(){}
	public void run()
	{
		while(true)
		{	
		
			try
			{	
				ServerSocket socket;
				Socket connsocket;
				BufferTransfer bufferTransfer;
				TransferObject transferObject=new TransferObject();  
				ObjectInputStream objectInputStream;
				socket=new ServerSocket(port);// create server socket and bind with port 6789
				System.out.println("Server Connected");
				connsocket=socket.accept();	// waiting to accept data from socket
				objectInputStream = new ObjectInputStream(connsocket.getInputStream());
				bufferTransfer = (BufferTransfer) objectInputStream.readObject();// Read data from socket
				if (bufferTransfer.getDestination_type().equals("Process"))  //Proect Next Host
				{	
					transferObject.neighbour_node(bufferTransfer.getCurrent_host(),bufferTransfer.getDestination_name(),bufferTransfer.getFilename(),bufferTransfer);   //Change source and destination and then continue 
				}
				else if(bufferTransfer.getDestination_type().equals("Destination")) //Confirm Data Packet reach destinatin
				{
					Writeobject writeobject=new Writeobject();  //Generate Output when that file reach destination
					writeobject.output(bufferTransfer);
					bufferTransfer.getRefuse_packet_container().removeAll(bufferTransfer.getRefuse_packet_container());
					bufferTransfer.getCrossing_host_name().removeAll(bufferTransfer.getCrossing_host_name());
					bufferTransfer.getDestination_not_reachable_list().removeAll(bufferTransfer.getDestination_not_reachable_list());
				}
				else if(bufferTransfer.getDestination_type().equals("Failure"))  //Check status for Not Reachable
				{
					Writeobject writeobject=new Writeobject();
					writeobject.output(bufferTransfer);
				}
				connsocket.close();
				socket.close();	
			}
			catch(Exception error)
			{
				System.out.println("error in Server==>"+error);
			}
		}	
	}
}