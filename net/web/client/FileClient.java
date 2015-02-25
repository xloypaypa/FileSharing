package web.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import web.Node;
import database.HHD;
import encryptionAlgorithm.AES;

public class FileClient extends Thread {
	String ip;
	Node need;
	Socket client;
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
					
					byte[] file=new byte[ans.size()];
					for (int i=0;i<file.length;i++) file[i]=ans.get(i);
					
					if (need.extraExist("password")){
						file=AES.decodeAsByte(file, need.getExtraMessage("password"));
					}
					
					if (need.getPart()==0) HHD.cleanFile(need.getSavePath());
					HHD.addByte(need.getSavePath(), file);
					in.close();
				}
				client.close();
				flag=false;
			} catch (IOException e) {
				System.out.println("client file error: "+e.getMessage());
				t--;
				if (t==0) break;
			}
		}
	}
}
