package action;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
public class Simulation extends JFrame implements ActionListener,MenuListener 
{	
	private JMenuBar bar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem send;
	public static PaintPanel pp;	
	public Simulation()
	{
		bar=new JMenuBar();
		file=new JMenu("File");
		file.setMnemonic('f');
		file.addMenuListener(this);		
		send=new JMenuItem("Send");
		send.addActionListener(this);
		send.setActionCommand("send");
		send.setMnemonic('s');
		file.add(send);
		
		exit=new JMenuItem("Exit");  
		exit.addActionListener(this);
		exit.setActionCommand("Exit");
		exit.setMnemonic('x');
		file.add(exit);
		
		bar.add(file);
		setJMenuBar(bar);				
		pp=new PaintPanel();		
		setLayout(new BorderLayout());
		final JSplitPane spt = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
		spt.setDividerLocation(350);
		spt.setLeftComponent(pp);
		setTitle("MANET Simulator");
		add(spt);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setExtendedState (MAXIMIZED_BOTH);		
	}
	public static void main(String args[]) 
	{
		
		new Simulation();
	}
	public void menuSelected(MenuEvent e) 
	{		// TODO Auto-generated method stub		
	}
	public void menuDeselected(MenuEvent e) 
	{		// TODO Auto-generated method stub		
	}
	public void menuCanceled(MenuEvent e) 
	{		// TODO Auto-generated method stub	
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(exit))
		{
			System.exit(0);
		}
		if(e.getSource().equals(send))
		{
			new SenderForm();
		}
	}
}
