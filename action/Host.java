package action;
/*
 * The class is used to maintain all the information 
 * about the individual host eg(position, name, and windowSize)
 */

import java.awt.Point;
import java.io.Serializable;

public class Host implements Serializable
{	
	private static final long serialVersionUID = 1654566548251023645L;
	public Point position;// x,y position of the pixel
	public String name; // name of the host
	public int distance; // distance from router
	public int hostPort; // retrieve port no
	public long host_id; //set unique host id to each host
	public Host(Point pos,String nam,int port,long id)
	{
		position = pos; 
		name = nam;
		hostPort = port;
		host_id=id;
		new HostResponse(hostPort);
	}
}