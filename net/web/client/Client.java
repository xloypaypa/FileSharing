package web.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import web.Node;

public class Client {
	public static int fileSize;
	public static int chance;
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
		String ans=new String();
		byte[] now=new byte[64];
		while (true){
			int len=in.read(now);
			if (len==-1) break;
			ans+=new String(now,0,len);
			if (ans.endsWith("$")) break;
		}
		node.solve(ans);
		
		client.close();
		
		return node;
	}
	
	public void startFileClient(Node node){
		fc=new FileClient();
		fc.ip=ip;
		fc.need=new Node(node);
		fc.start();
	}
	
	public boolean isAlive(){
		return fc.isAlive();
	}
	
	public static long getPart(long length){
		long ans=0;
		ans=length/fileSize;
		if (length%fileSize==0) ans--;
		if (ans==-1) ans=0;
		return ans;
	}
}
