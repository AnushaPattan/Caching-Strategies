package action;
/*
 * The class is used to maintain all the information 
 * about the individual host eg(position, name, and windowSize)
 */
import java.io.*;
import java.util.Vector;
public class BufferTransfer implements Serializable
{
	String source_name,destination_name,filename,destination_type,current_host,cross_host_name;
	long refuse_host_id; //Reject host id when 
	Vector crossing_host_name=new Vector();	//List for File traverse path
	Vector refuse_packet_container=new Vector(); //Refuse list for already file traversed
	Vector destination_not_reachable_list=new Vector();	//Refuse list for packet not reachable
	String destination_not_reach_host;
	
	byte[] buffer=new byte[1024];
	
	public Vector getCrossing_host_name() {     //Get Info for file traversing Host
		return crossing_host_name;
	}	
	public void setCross_host_name(String cross_host_name) {   //Add Cross host name to vector list
		crossing_host_name.add(cross_host_name);
	}	
	
	public void setRefuse_host_id(long refuse_host_id) {      //reject host for one host traverse 
		refuse_packet_container.add(refuse_host_id);
	}
	public Vector getRefuse_packet_container() {       			//get Reject host
		return refuse_packet_container;
	}		
	
	public String getCurrent_host() {							//get Current host
		return current_host;
	}
	public void setCurrent_host(String current_host) {			//set Current host
		this.current_host = current_host;
	}
	public String getDestination_name() {						//get Destinatin Host
		return destination_name;
	}
	public void setDestination_name(String destination_name) {  //set Destinatin Host
		this.destination_name = destination_name;
	}
	public String getDestination_type(){						// get Destination type wheather that host is Process/Destination
		return destination_type;
	}
	public void setDestination_type(String destination_type) {  // set Destination type wheather that host is Process/Destination
		this.destination_type = destination_type;
	}
	public String getFilename() {								// get Name for which file want to trasfer through network 
		return filename;
	}
	public void setFilename(String filename) { 					// get Name for which file want to trasfer through network
		this.filename = filename;
	}
	public String getSource_name() {							//get Source name select by user
		return source_name;
	} 
	public void setSource_name(String source_name) {			// set Source name select by user
		this.source_name = source_name;
	}
	public byte[] getBuffer() {
		return buffer;
	}
	public void setBuffer(byte[] buffer) {						//Set output message read from input file given by user
		this.buffer = buffer;
	}
	public Vector getDestination_not_reachable_list() {			//Get vector contains that not reachable host name
		return destination_not_reachable_list;
	}
	public void setDestination_not_reach_host(String destination_not_reach_host) {
		destination_not_reachable_list.add(destination_not_reach_host);
	}
}