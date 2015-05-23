package net.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.type.Node;

public class Client {
	public static int chance=5;
	public static int bufferLength=64;
	Socket client;
	String ip;
	int port;
	FileClient fc;
	
	public Client(String ip, int port){
		this.ip=ip;
		this.port=port;
	}
	
	public void connect() throws UnknownHostException, IOException{
		client=new Socket(ip, port);
	}
	
	public Node send(Node node) throws IOException{
		OutputStream out;
		out = client.getOutputStream();
		out.write(node.getAllMessage().getBytes());
		out.flush();
		
		InputStream in=client.getInputStream();
		Node ret=new Node();
		String ans=new String();
		byte[] now=new byte[64];
		while (true){
			int len=in.read(now);
			if (len==-1) break;
			ans+=new String(now,0,len);
			if (ans.endsWith("$")) break;
		}
		ret.solve(ans);
		
		client.close();
		
		return ret;
	}
	
	public void startFileClient(Node node){
		fc=new FileClient();
		fc.ip=ip;
		fc.need=new Node(node);
		fc.start();
	}
	
	public void runFileClient(Node node){
		fc=new FileClient();
		fc.ip=ip;
		fc.need=new Node(node);
		fc.run();
	}
	
	public int getStatus(){
		return (int) fc.getStatus();
	}
	
	public boolean isEnd(){
		return !fc.isAlive();
	}
}
