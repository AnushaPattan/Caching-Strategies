package action;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
/*
 * This class is used to store all the host
 * provide service the necessay place
 */
public class HostContainer 
{
	private Vector hostVector = null;

	public HostContainer()
	{
		hostVector = new Vector();
	}
	public void addHost(Host host)
	{	
		hostVector.add(host);
	}
	public Iterator getIterator() 	// to retrieve all the hosts under iterator
	{
		Vector vector = new Vector(hostVector);
		return vector.iterator(); 
	}
	public Vector getVector()  	// to retrieve the vector which content all the hosts
	{
		Vector vector = new Vector(hostVector);
		return vector;
	}
	public int size() 	// Get Vector Size.
	{	
		return hostVector.size();
	}
}
