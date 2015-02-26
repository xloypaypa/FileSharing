package web.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import web.Node;

public class Client {
	public static int fileSize=1000000;
	public static int chance=5;
	Socket client;
	String ip;
	int port;
	FileClient fc;
	static ExecutorService pool = Executors.newFixedThreadPool(2);
	
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
		pool.execute(fc);
	}
	
	public void runFileClient(Node node){
		fc=new FileClient();
		fc.ip=ip;
		fc.need=new Node(node);
		fc.run();
	}
	
	public static void setMaxThread(int num){
		pool = Executors.newFixedThreadPool(num);
	}
	
	public static long getPart(long length){
		long ans=0;
		ans=length/fileSize;
		if (length%fileSize==0) ans--;
		if (ans==-1) ans=0;
		return ans;
	}
}
