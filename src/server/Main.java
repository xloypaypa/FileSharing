package server;

import web.server.Server;

public class Main {
	public static void main(String[] args){
		Server server;
		Server.fileSize=1000000;
		server=new Server(8899);
		UI.setIP("127.0.0.1");
		UI.setPort("8899");
		UI.show();
		server.listen();
	}
}
