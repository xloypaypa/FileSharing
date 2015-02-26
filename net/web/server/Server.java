package web.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import web.Node;

public class Server {
	public static int fileSize;
	ServerSocket server;
	ServerSolver solver;
	int port;
	public Server(int port){
		this.port=port;
		buildServer();
	}
	private void buildServer() {
		try {
			server=new ServerSocket(this.port);
		} catch (IOException e) {
			try {
				server=new ServerSocket(0);
				this.port=server.getLocalPort();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public int getPort(){
		return port; 
	}
	
	public void listen(){
		boolean shutDown=false;
		while (!shutDown){
			try {
				Socket socket=server.accept();
				
				InputStream in=socket.getInputStream();
				String ans=new String();
				byte[] now=new byte[64];
				while (true){
					int len=in.read(now);
					if (len==-1) break;
					ans+=new String(now,0,len);
					if (ans.endsWith("$")) break;
				}
				Node node=new Node();
				node.solve(ans);
				
				if (node.getCommand().equals("shutDown")){
					socket.close();
					server.close();
					System.exit(0);
					break;
				}
				
				solver=new ServerSolver();
				solver.setSocket(socket);
				solver.setNode(node);
				solver.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
