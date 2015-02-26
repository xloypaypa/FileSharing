package client;

import web.client.Client;
import web.client.FileClient;

public class Main {
	public static void main(String[] args){
		Client.chance=5; Client.fileSize=1000000;
		Client.setMaxThread(4);
		FileClient.setMaxWriteThread(1);
		UI ui=new UI();
		ui.show();
	}
}
