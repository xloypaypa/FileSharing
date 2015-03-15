package web.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import web.Node;
import database.HHD;
import encryptionAlgorithm.AES;

public class FileClient extends Thread {
	String ip;
	Node need;
	Socket client;
	static ExecutorService pool = Executors.newFixedThreadPool(1);
	public static long waitTime;
	
	public static void setMaxWriteThread(int num){
		pool = Executors.newFixedThreadPool(num);
	}
	
	public void setNeed(Node node){
		this.need=node;
	}
	
	@Override
	public void run(){
		boolean flag=true;
		int t=Client.chance;
		while (flag){
			try {
				client=new Socket(ip, need.getPort());
				if (!need.getCommand().equals("download")){
					int len=(int) Math.min(Client.fileSize, need.getLength()-need.getPart()*Client.fileSize);
					byte[] file=HHD.readByte(need.getPath(), need.getPart()*Client.fileSize, len);
					
					if (need.extraExist("password")){
						file=AES.encode(file, need.getExtraMessage("password"));
					}
					
					OutputStream out=client.getOutputStream();
					out.write(file);
					out.flush();
					out.close();
				}else{
					InputStream in=client.getInputStream();
					byte[] now=new byte[64];
					Vector <Byte> ans=new Vector<Byte>();
					while (true){
						int len=in.read(now);
						if (len==-1) break;
						for (int i=0;i<len;i++) ans.add(now[i]);
					}
					
					final byte[] file=new byte[ans.size()];
					for (int i=0;i<file.length;i++) file[i]=ans.get(i);
					
					if (need.extraExist("password")){
						final byte[] ret=AES.decodeAsByte(file, need.getExtraMessage("password"));
						writeFile(ret);
					}else{
						writeFile(file);
					}
					
					in.close();
				}
				client.close();
				flag=false;
			} catch (IOException e) {
				System.out.println("client file error: "+e.getMessage());
				t--;
				if (t==0){
					System.out.println("error");
					break;
				}
				
				try {
					sleep(waitTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
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