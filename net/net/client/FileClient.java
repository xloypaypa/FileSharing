package net.client;

import java.io.IOException;
import java.net.Socket;

import net.tool.FileTP;
import net.type.Node;

public class FileClient extends Thread {
	String ip;
	Node need;
	Socket client;
	FileTP fTp;
	
	public static long waitTime;
	
	public void setNeed(Node node){
		this.need=node;
	}
	
	@Override
	public void run(){
		boolean flag=true;
		int t=Client.chance;
		while (true) {
			try {
				client=new Socket(ip, need.getPort());
				break;
			} catch (IOException e2) {
				t--;
				if (t==0) {
					flag=false;
					break;
				}
				e2.printStackTrace();
			}
		}
		if (!flag) return ;
		
		try {
			fTp = new FileTP();
			if (need.getCommand().equals("download")){
				fTp.get(client, need.getSavePath(), need.getFrom(), need.getTo());
			}else if (need.getCommand().equals("upload")){
				fTp.send(client, need.getPath(), need.getFrom(), need.getTo());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long getStatus(){
		if (fTp==null) return 0;
		return fTp.getStatus();
	}
}