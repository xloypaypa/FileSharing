package web.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import web.Node;
import web.client.Client;
import database.HHD;
import encryptionAlgorithm.AES;

public class FileServer extends Thread {
	Node need;
	ServerSocket server;
	static ExecutorService pool = Executors.newFixedThreadPool(1);
	boolean fail=false;
	
	public static void setMaxWriteThread(int num){
		pool = Executors.newFixedThreadPool(num);
	}
	
	public void setNeed(Node node){
		this.need=new Node(node);
	}
	
	public void buildServer(){
		try{
			fail=false;
			server=new ServerSocket(0);
		}catch (IOException e){
			fail=true;
		}
	}
	
	public int getPort(){
		return server.getLocalPort();
	}
	
	@Override
	public void run(){
		try {
			fail=false;
			Socket socket=server.accept();
			if (need.getCommand().equals("download")){
				int len=(int) Math.min(Server.fileSize, need.getLength()-need.getPart()*Server.fileSize);
				
				byte[] file=HHD.readByte(need.getPath(), need.getPart()*Server.fileSize, len);
				if (need.extraExist("password")){
					file=AES.encode(file, need.getExtraMessage("password"));
				}
				
				OutputStream out=socket.getOutputStream();
				out.write(file);
				out.flush();
				out.close();
			}else if (need.getCommand().equals("upload")){
				InputStream in=socket.getInputStream();
				byte[] now=new byte[64];
				Vector <Byte> ans=new Vector<Byte>();
				while (true){
					int len=in.read(now);
					if (len==-1) break;
					for (int i=0;i<len;i++) ans.add(now[i]);
				}
				
				byte[] file=new byte[ans.size()];
				for (int i=0;i<file.length;i++) file[i]=ans.get(i);
				
				if (need.extraExist("password")){
					final byte[] ret=AES.decodeAsByte(file, need.getExtraMessage("password"));
					writeFile(ret);
				}else{
					writeFile(file);
				}
				
				in.close();
			}
			socket.close();
			server.close();
		} catch (IOException e) {
			fail=true;
		}
	}
	
	public boolean beFail(){
		return this.fail;
	}
	
	private void writeFile(final byte[] file) {
		pool.execute(new Thread(){
			@Override
			public void run(){
				HHD.writeByte(need.getSavePath(), file, need.getPart()*Client.fileSize, file.length);
			}
		});
	}
}