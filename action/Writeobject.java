/*
 * The class is used to write information to log file 
 * about the Output eg(Source Name,Destinatin name, Traverse path)
 */
package action;
import java.io.FileOutputStream;
import java.util.Date;
public class Writeobject 
{
	private FileOutputStream fos;
	
	public void output(BufferTransfer bufferTransfer)
	{	
		try
		{
			fos = new FileOutputStream("Transfer_history.txt",true);
			String s_line="\n--------------------------------------------------------------------";
			String res_output="\n S.Name ==>"+bufferTransfer.getSource_name()+"D.Name==>"+bufferTransfer.getDestination_name();  //Assign Source and Destinatin name for write the log file   
			String date = "\n" +new Date().toString();
			fos.write(date.getBytes());
			fos.write(s_line.getBytes());
			fos.write(res_output.getBytes());
			fos.write(s_line.getBytes());
			fos.write(bufferTransfer.getCrossing_host_name().toString().getBytes());  //Display traversing Data Packer path info
			fos.write(s_line.getBytes());
			fos.close();
		}
		catch(Exception e)
		{
			System.out.println("File Write"+e.getMessage());
		}
	}
}