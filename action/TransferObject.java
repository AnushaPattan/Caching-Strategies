package action;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
public class TransferObject
{
	private BufferTransfer bufferTransfer;
	public static TransferObject transferObject; 
	byte[] buffer;
	FileInputStream fis;
	public TransferObject(String sname,String dname,String fname,BufferTransfer bufferTransfer) throws Exception
	{	
		this.bufferTransfer=bufferTransfer;
		transferObject=new TransferObject();
		this.sname=sname;
		this.dname=dname;
		this.fname=fname;
		bufferTransfer.setSource_name(sname);		//	Set Souce Name
		bufferTransfer.setDestination_name(dname);	//	Set Destination name
		bufferTransfer.setFilename(fname);			//	Set File Name
		File f=new File(fname);	
		try {
			fis =new FileInputStream(f);
			buffer=new byte[fis.available()];
			fis.read(buffer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferTransfer.setBuffer(buffer);
		transferObject.neighbour_node(sname,dname,fname,bufferTransfer);
	}
	public TransferObject()
	{}	
	int dest_avail_confirm=0,region_short_node=0,sdt=0,next_sdt=0,trans_port=0,trans_port_copy=0,dest_reach_confirm=0;
	String sname,dname,fname,trans_node_type="";
	Point s_point,reg_point; 	//For get source point
	long trans_port_id;  //Assign port id for transfer
	Vector current_reg_vector=new Vector();   //For set Current region Host(Find out host 120 Distance)
	Vector current_reg_vector_next=new Vector(); //For set Next Current region Host(include 120 Distance)
	String region_short_node_name="None";		// Assign Next Host for Tranverse
	
//	For declare value for next iteration loop

	String not_reach_alternate_host="None",not_reach_alternate_host_copy="None";
	Point not_reach_alternate_host_point = null;
	int not_reach_hostport = 0;
	int fdt=0;
	public void neighbour_node(String sname,String dname,String fname,BufferTransfer bufferTransfer) throws Exception
	{
		dest_reach_confirm=0;		
		Iterator treeTraverse = PaintPanel.nt.getIterator();   // Iteration for Host information
		
		while (treeTraverse.hasNext())   //Find out source and destination point
		{		
			Host host = (Host) treeTraverse.next();
			if (host.name.equalsIgnoreCase(sname))
				s_point=host.position;				//Assign Source Position
		}
		Iterator treeTraverse_dist = PaintPanel.nt.getIterator();  //Iterate all host from hostcontainer
		while (treeTraverse_dist.hasNext())
		{	
			Host host = (Host) treeTraverse_dist.next();  //Get Host list from HostContainer Class
			sdt = (int)Math.sqrt(Math.pow((s_point.x-host.position.x), 2) + Math.pow((s_point.y-host.position.y), 2)); //Find the Distance from Source and iterate host
			if ((sdt>0) && (sdt<=125)&& (!bufferTransfer.getRefuse_packet_container().contains(host.host_id)) && (!bufferTransfer.getDestination_not_reachable_list().contains(host.name))&& (!bufferTransfer.getCrossing_host_name().contains(host.name))) 			 //Check particular Region
			{
				current_reg_vector.add(host.name);
				bufferTransfer.setRefuse_host_id(host.host_id);
							//Code for Check Next iteration host are available(within next 125 Region)
				Iterator treeTraverse_dist_next = PaintPanel.nt.getIterator();
				while(treeTraverse_dist_next.hasNext())
				{	
					Host host_next = (Host) treeTraverse_dist_next.next();  //Get Host list from HostContainer Class
					next_sdt = (int)Math.sqrt(Math.pow((host.position.x-host_next.position.x), 2) + Math.pow((host.position.y-host_next.position.y), 2)); //Find the Distance from Source and iterate host
					if ((next_sdt>0)&&(next_sdt<=125)&& (!bufferTransfer.getRefuse_packet_container().contains(host_next.host_id)) && (!host_next.name.equals(sname)) && (!bufferTransfer.getCrossing_host_name().contains(host.name))) 			 //Check particular Region
					{
						current_reg_vector_next.add(host_next.name);
					}	
				}  //End for check Next iteration
				if ((region_short_node<=sdt) && ((current_reg_vector_next.size()>0)||(host.name.equals(bufferTransfer.getDestination_name()))) && (!bufferTransfer.getCrossing_host_name().contains(host.name))) 
				{					
					region_short_node=sdt;					
					region_short_node_name=host.name;  //Assign next host 
					dest_reach_confirm++;
					trans_port=host.hostPort;			//get Unique port for next host 
				}
			}
			current_reg_vector_next.removeAll(current_reg_vector_next);
		}		
		if (current_reg_vector.contains(dname))   //Check Destinatin host are avaiable in Region
		{
			JOptionPane.showMessageDialog(null,"Successfully sent");
			String fob="Hosts/"+dname;
			File f=new File(fob);
			f.mkdir();				//Make Directory if not exists
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(fob+"/Output.txt");   //Genrate the .txt for output
				fos.write(bufferTransfer.getBuffer());	//Generate output when File reach their Destination
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bufferTransfer.setDestination_type("Destination");
			new SubHostResponse(trans_port,bufferTransfer);
		}
		else if (!region_short_node_name.equals("None"))  //Assign for next Host to Traverse
		{
			current_reg_vector.removeAll(current_reg_vector);
			region_short_node=0;
			sdt=0;
			bufferTransfer.setCurrent_host(region_short_node_name);
			bufferTransfer.setCross_host_name(region_short_node_name);
			System.out.println("Next Host to Process-->"+bufferTransfer.getCurrent_host());
			bufferTransfer.setDestination_type("Process");
			new SubHostResponse(trans_port,bufferTransfer);  //Control transfer to client(Such as Sender)
		}		
		else			//Prevent Data Packet Not reachable Condition (Two step back) 
		{	
			if (bufferTransfer.getCrossing_host_name().size()>=2)
			{
				try
				{					
					if (bufferTransfer.getCrossing_host_name().size()>=2)
					{	
						bufferTransfer.setDestination_not_reach_host(bufferTransfer.getCrossing_host_name().lastElement().toString());  //Assign particular Host name into Not Reachable list
						trans_port=transferObject.freeup_Host(bufferTransfer.getCrossing_host_name().lastElement().toString(),bufferTransfer);  //Free up surrounding host 
						bufferTransfer.getCrossing_host_name
						().remove(bufferTransfer.getCrossing_host_name().lastElement());  //Remove From Travering Host List
						bufferTransfer.setCurrent_host(bufferTransfer.getCrossing_host_name().lastElement().toString());  //Set Current host name to traverse next Iteration	
						trans_port=transferObject.freeup_Host(bufferTransfer.getCrossing_host_name().lastElement().toString(),bufferTransfer);	////Free up surrounding host (120 Distance from not reachable host)
						bufferTransfer.getCrossing_host_name().remove(bufferTransfer.getCrossing_host_name().lastElement()); //Remove from Taversing Host list
						System.out.println("Next Host to Process(Recover)-->."+bufferTransfer.getCurrent_host());
						bufferTransfer.setDestination_type("Process"); //Set type for process another iteration
						new SubHostResponse(trans_port,bufferTransfer);  //Control transfer to client(Such as Sender)
					}
					
				}
				catch(Exception e)
				{
					System.out.println("Exception in not reachable"+e.getMessage());
				}
			}
			else if (bufferTransfer.getCrossing_host_name().size()>=1)
			{					
				JOptionPane.showMessageDialog(null,"No More Sub Host Receiver Available in Next Host Region "+bufferTransfer.getCurrent_host());
				bufferTransfer.setDestination_type("Failure");
			}
			else if (bufferTransfer.getCrossing_host_name().isEmpty())
			{	
				JOptionPane.showMessageDialog(null,"Put Receiver Host in 125 Region");
				bufferTransfer.setDestination_type("Failure");
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Destination Not Reachable");
				bufferTransfer.setDestination_type("Failure");
			}
		}
	}
	public int freeup_Host(String freeuphost_param,BufferTransfer bufferTransfer)
	{
		try
			{
				Iterator iterator_getpoint = PaintPanel.nt.getIterator();
				while(iterator_getpoint.hasNext())
				{
					Host host_next_alter_host = (Host) iterator_getpoint.next();  //Get Host list from HostContainer Class
					if (freeuphost_param.equals(host_next_alter_host.name))
					{	
						not_reach_alternate_host_point=host_next_alter_host.position;   //Get the Host point posistion
						not_reach_hostport=host_next_alter_host.hostPort;				//Get corresponding hostport
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception in Getting Point"+e.getMessage());
			}
			try
			{
							// 	Free up from refuse List host
				Iterator treeTraverse_dist_freeup_host = PaintPanel.nt.getIterator();
				while(treeTraverse_dist_freeup_host.hasNext())
				{	
					Host host_next_free = (Host) treeTraverse_dist_freeup_host.next();  //Get Host list from HostContainer Class
					fdt = (int)Math.sqrt(Math.pow((not_reach_alternate_host_point.x-host_next_free.position.x), 2) + Math.pow((not_reach_alternate_host_point.y-host_next_free.position.y), 2)); //Find the Distance from Source and iterate host
					if (fdt<=125)  //Check that particular host is avaiable in  125 Region Distance
					{
						bufferTransfer.getRefuse_packet_container().remove(host_next_free.host_id);					
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception in Free-up Host"+e.getMessage());
			}
		return not_reach_hostport;
	} //End function	
}