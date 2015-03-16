package web.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import sun.nio.ch.FileChannelImpl;
import web.Node;

public class FileClient extends Thread {
	String ip;
	Node need;
	Socket client;
	long part,length;
	public static long waitTime;
	
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
				length=need.getTo()-need.getFrom();
				if (!need.getCommand().equals("download")){
					File file=new File(need.getPath());
					RandomAccessFile raf=new RandomAccessFile(file, "r");
					FileChannel channel=raf.getChannel();
					MappedByteBuffer buffer=channel.map(FileChannel.MapMode.READ_ONLY, need.getFrom(), length);
					
					OutputStream os=client.getOutputStream();
					byte[] bf=new byte[Client.bufferLength*1000000];
					part=0;
					while (part!=length){
						int len;
						if (part+Client.bufferLength*1000000<=length){
							len=Client.bufferLength*1000000;
						}else{
							len=(int) (length-part);
						}
						part+=len;
						buffer.get(bf,0,len);
						os.write(bf,0,len);
					}
					releaseBuffer(buffer);
					channel.close();
					raf.close();
					os.close();
				}else{
					File file=new File(need.getSavePath());
					
					PrintWriter fos = new PrintWriter(file);
					fos.write("");
					fos.close();
					
					RandomAccessFile raf=new RandomAccessFile(file, "rw");
					FileChannel channel=raf.getChannel();
					MappedByteBuffer buffer=channel.map(FileChannel.MapMode.READ_WRITE, need.getFrom(), length);
					
					InputStream is=client.getInputStream();
					byte[] bf=new byte[Client.bufferLength*1000000];
					int len;
					while (true){
						len=is.read(bf);
						if (len==-1) break;
						part+=len;
						buffer.put(bf);
					}
					releaseBuffer(buffer);
					channel.close();
					raf.close();
					is.close();
				}
				client.close();
				flag=false;
			} catch (IOException e) {
				System.out.println(need.getPath()+" error: "+e.getMessage());
				t--;
				if (t==0){
					System.out.println(need.getPath()+" fail");
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
	
	public long getStatus(){
		if (length==0) return 100;
		return part*100/length;
	}
	
	private static void releaseBuffer(MappedByteBuffer buffer) {
		try {
			Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
			m.setAccessible(true);
			m.invoke(FileChannelImpl.class, buffer);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
}