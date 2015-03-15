package client;

import web.client.Client;

public class Main {
	public static void main(String[] args){
		Client.chance=100; Client.fileSize=1000000;
		Client.setMaxThread(4);
		UI ui=new UI();
		ui.show();
	}
}
