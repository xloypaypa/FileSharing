package client;

import web.client.Client;

public class Main {
	public static void main(String[] args){
		Client.chance=5; Client.fileSize=1000000;
		UI ui=new UI();
		ui.show();
	}
}
