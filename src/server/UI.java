package server;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Clock;

public class UI {
	static String ip,port;
	
	public static void setIP(String ip){
		UI.ip=new String(ip);
	}
	
	public static void setPort(String port){
		UI.port=new String(port);
	}
	
	public static void show(){
		
		TrayIcon icon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(Clock.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		
		SystemTray tray=SystemTray.getSystemTray();
		PopupMenu pop=new PopupMenu();
		MenuItem mip=new MenuItem(ip);
		MenuItem mport=new MenuItem(port);
		MenuItem exit=new MenuItem("exit");
		pop.add(mip);
		pop.add(mport);
		pop.add(exit);
		TrayIcon trayIcon=new TrayIcon(icon.getImage(), "server", pop);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
}
