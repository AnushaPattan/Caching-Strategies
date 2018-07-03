package action;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Date;

public class PaintPanel extends JPanel
{
	private int pointCount=0;	
	private Point points[]=new Point[1000];
	String name;
	int i=0;
	int hostport=5000;
	Date d=new Date();	
	public static HostContainer nt = new HostContainer();
	
	public PaintPanel()
	{	
		addMouseListener(
				new MouseAdapter()
				{
					public void mouseClicked(MouseEvent event)
					{
						if (i<50)
						{
							long host_id=new Date().getTime();
							Point point = event.getPoint();   //Get the Point Position
							name="Host";
							i++;
							name=name+String.valueOf(i);	
							hostport++;    					//Assign unique port for every Host
							Host node = new Host(point, name,hostport,host_id);  //Create Object and pass arguments through constructor
							nt.addHost(node);   //Add object of Host class to HostContainer Class
							repaint();
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Allowed only 50 Receiver Hosts");
						}
					}
				}		
		);
	}	
	public void paint(final Graphics g)
	{
		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.RED);
		gg.clearRect(0, 0, getWidth(), getHeight());
		Iterator treeTraverse = nt.getIterator();  
		Vector v = nt.getVector();		
		while (treeTraverse.hasNext())
		{	
			Host host = (Host) treeTraverse.next();			
			gg.setColor(Color.RED);
			gg.fillOval(host.position.x - 10, host.position.y - 10, 18, 18);
			gg.setColor(Color.BLACK);
			gg.drawString("[" +host.name +"]", host.position.x - 20,host.position.y + 20);
		}
	}
}