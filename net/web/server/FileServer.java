package web.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import sun.nio.ch.FileChannelImpl;
import web.Node;
import web.client.Client;

public class FileServer extends Thread {
	Node need;
	ServerSocket server;
	long part;
	
	public void setNeed(Node node){
		this.need=new Node(node);
	}
	
	public void buildServer(){
		try {
			server=new ServerSocket(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPort(){
		return server.getLocalPort();
	}
	
	@Override
	public void run(){
		try {
			Socket socket=server.accept();
			long length=need.getTo()-need.getFrom();
			if (need.getCommand().equals("download")){
				File file=new File(need.getPath());
				RandomAccessFile raf=new RandomAccessFile(file, "r");
				FileChannel channel=raf.getChannel();
				MappedByteBuffer buffer=channel.map(FileChannel.MapMode.READ_ONLY, need.getFrom(), length);
				
				OutputStream os=socket.getOutputStream();
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
			}else if (need.getCommand().equals("upload")){
				File file=new File(need.getSavePath());
				
				PrintWriter fos = new PrintWriter(file);
				fos.write("");
				fos.close();
				
				RandomAccessFile raf=new RandomAccessFile(file, "rw");
				FileChannel channel=raf.getChannel();
				MappedByteBuffer buffer=channel.map(FileChannel.MapMode.READ_WRITE, need.getFrom(), length);
				
				InputStream is=socket.getInputStream();
				byte[] bf=new byte[Client.bufferLength*1000000];
				int len;
				part=0;
				while (part!=length){
					len=is.read(bf);
					if (len==-1) break;
					part+=len;
					buffer.put(bf, 0, len);
				}
				releaseBuffer(buffer);
				channel.close();
				raf.close();
				is.close();
				System.out.println("ok");
			}
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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