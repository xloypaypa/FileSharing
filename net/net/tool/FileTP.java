package net.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class FileTP {
	
	int fileWriteBufferLength = 2000;
	int fileReadBufferLength = 2000;
	long part, length;
	
	public void send(Socket socket, String path, long from, long to)
			throws IOException {
		File file=new File(path);
		
		if (!file.exists()) file.createNewFile();
		
		OutputStream os = socket.getOutputStream();
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		raf.seek(from);
		length = to - from;
		byte[] buffer= new byte[fileReadBufferLength];
		int now;
		for (part = 0; part < length; part+=now) {
			now=raf.read(buffer);
			os.write(buffer, 0, now);
        }
		raf.close();
		os.close();
		socket.close();
	}
	
	public void get(Socket socket, String path, long from, long to)
			throws IOException {
		File file=new File(path);
		
		if (!file.exists()) file.createNewFile();
		
		InputStream is;
		is = socket.getInputStream();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(from);
		length = to - from;
		byte[] buffer= new byte[fileWriteBufferLength];
		int now;
		for (part = 0; part < length; part+=now) {
			now = is.read(buffer);
			raf.write(buffer, 0, now);
        }
		
		raf.close();
		is.close();
		socket.close();
	}
	
	public long getStatus(){
		if (length==0) return 100;
		return part*100/length;
	}

}
