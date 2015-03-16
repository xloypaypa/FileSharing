package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import web.server.Server;

public class Main {
	public static void main(String[] args){
		Server server;
		server=new Server(8899);
		try {
			InetAddress addr = InetAddress.getLocalHost();
			UI.setIP(addr.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		UI.setPort(server.getPort()+"");
		UI.show();
		server.listen();
	}
}
