package server;

import web.server.Server;

public class Main {
	public static void main(String[] args){
		Server server;
		Server.fileSize=1000000;
		System.out.println("server open");
		server=new Server(8899);
		System.out.println("port: "+server.getPort());
		server.listen();
		System.out.println("end");
	}
}
