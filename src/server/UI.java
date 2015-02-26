package server;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class UI {
	static String ip,port;
	
	public static void setIP(String ip){
		UI.ip=new String(ip);
	}
	
	public static void setPort(String port){
		UI.port=new String(port);
	}
	
	public static void show(){
		SystemTray tray=SystemTray.getSystemTray();
		PopupMenu pop=new PopupMenu();
		MenuItem mip=new MenuItem(ip);
		MenuItem mport=new MenuItem(port);
		MenuItem exit=new MenuItem("exit");
		pop.add(mip);
		pop.add(mport);
		pop.add(exit);
		URL fileURL=UI.class.getResource("/icon.png");
		TrayIcon trayIcon=new TrayIcon(Toolkit.getDefaultToolkit().getImage(fileURL), "server", pop);
		trayIcon.setImageAutoSize(true);
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