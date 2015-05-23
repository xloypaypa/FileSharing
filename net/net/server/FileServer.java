package net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.tool.FileTP;
import net.type.Node;

public class FileServer extends Thread {
	Node need;
	ServerSocket server;
	long part;
	
	public void setNeed(Node node){
		this.need=new Node(node);
	}
	
	public void buildServer(){
		try {
			server=new ServerSocket(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPort(){
		return server.getLocalPort();
	}
	
	@Override
	public void run(){
		try {
			Socket socket=server.accept();
			FileTP fTp = new FileTP();
			if (need.getCommand().equals("download")){
				fTp.send(socket, need.getPath(), need.getFrom(), need.getTo());
			}else if (need.getCommand().equals("upload")){
				fTp.get(socket, need.getSavePath(), need.getFrom(), need.getTo());
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}