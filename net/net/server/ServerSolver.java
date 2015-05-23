package net.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import net.type.Node;

public class ServerSolver extends Thread {
	Node node;
	Socket socket;
	
	public void setNode(Node node){
		this.node=new Node(node);
	}
	public void setSocket(Socket socket){
		this.socket=socket;
	}
	
	@Override
	public void run(){
		if (node.getCommand().equals("get file length")){
			File file=new File(node.getPath());
			node.setLength(file.length());
		}else if (node.getCommand().equals("download")||node.getCommand().equals("upload")){
			FileServer fs=new FileServer();
			fs.setNeed(node);
			fs.buildServer();
			node.setCommand("ok");
			System.out.println(fs.getPort());
			node.setPort(fs.getPort());
			Server.pool.execute(fs);
		}
		
		try {
			OutputStream out=socket.getOutputStream();
			out.write(node.getAllMessage().getBytes());
			out.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
