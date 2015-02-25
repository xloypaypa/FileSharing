package client;

import java.io.IOException;

import web.Node;
import web.client.Client;

public class Main {
	public static void main(String[] args){
		Client client;
		Client.fileSize=1000000; Client.chance=5;
		client=new Client("127.0.0.1", 8899);
		Node node=new Node();
		Node ret;
		
		try {
			client.connect();
			node.setCommand("get file length");
			node.setPath("D:/aim.bmp");
			ret=client.send(node);
			
			long part=Client.getPart(node.getLength());
			long len=node.getLength();
			
			for (long i=0;i<=part;i++){
				client.connect();
				
				node.setPart(i);
				node.setCommand("download");
				node.setPort(8899);
				node.setSavePath("D:/ans.bmp");
				node.setLength(len);
				
				ret=client.send(node);
				System.out.println(ret.getCommand());
				
				node.setPart(i);
				node.setCommand("download");
				node.setPort(8899);
				node.setSavePath("D:/ans.bmp");
				node.setLength(len);
				
				client.startFileClient(node);
				while (client.isAlive());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
