package database;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import encryptionAlgorithm.AES;

public class HHD {
	public static boolean fileExiste(String path){
		File f=new File(path);
		return f.exists();
	}
	public static boolean folderExiste(String path){
		File f=new File(path);
		return f.exists();
	}
	public static void createFolder(String Path,String Name){
		File path=new File(Path+Name);
		if (!path.exists()){
			path.mkdirs();
		}
	}
	public static void createFile(String Path,String Name){
		try{
			File path=new File(Path);
			if (!path.exists()){
				path.mkdirs();
			}
			File dir=new File(path,Name);
			if (!dir.exists()){
				dir.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void cleanFile(String path){
		try{
			FileOutputStream fos = new FileOutputStream(new File(path));
			fos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void writeFile(String path,String message, String passWord){
		message+="\r\n";
		writeByte(path, AES.encode(message, passWord));
	}
	public static void writeFile(String path,String message){
		try{
		PrintWriter fos = new PrintWriter(new File(path));
		fos.write(message+"\r\n");
		fos.close();
		}catch(IOException e){
		e.printStackTrace();
		}
		}
	public static void addWriteFile(String path,String message, String passWord){
		String past=AES.decode(readByte(path), passWord);
		past+=message+"\r\n";
		writeByte(path, AES.encode(past, passWord));
	}
	public static void addWriteFile(String path,String message){
		try{
		FileWriter fos=new FileWriter(new File(path),true);
		PrintWriter pw = new PrintWriter(fos);
		pw.println(message);
		pw.flush();
		pw.close();
		fos.close();
		}catch(IOException e){
		e.printStackTrace();
		}
		}
	public static Vector <String> readFile(String path, String passWord){
		return AES.decodeAsVector(readByte(path), passWord);
	}
	public static Vector <String> readFile(String path){
		/*
		* need fix
		*/
		if (!HHD.fileExiste(path)){
		String d=new String(),f=new String();
		int len=path.length(),pos=0;
		for (pos=len-1;pos>=0;pos--){
		if (path.toCharArray()[pos]=='/') break;
		}
		for (int i=0;i<=pos;i++) d+=path.toCharArray()[i];
		for (int i=pos+1;i<len;i++) f+=path.toCharArray()[i];
		HHD.createFile(d, f);
		}
		try {
		Vector <String > ans=new Vector <String>();
		BufferedReader fos=new BufferedReader(new FileReader(path));
		String data = null;
		while ((data = fos.readLine()) != null) {
		ans.add(data);
		}
		fos.close();
		return ans;
		}catch(IOException e){
		e.printStackTrace();
		}
		return new Vector <String>();
		}
	
	
	public static byte[] readByte(String path){
		return readByte(path, 0, (int) HHD.getFileLength(path));
	}
	
	public static byte[] readByte(String path, long from, int length){
		
		if (!HHD.fileExiste(path)){
			String d=new String(),f=new String();
			int len=path.length(),pos=0;
			for (pos=len-1;pos>=0;pos--){
				if (path.toCharArray()[pos]=='/') break;
			}
			
			for (int i=0;i<=pos;i++) d+=path.toCharArray()[i];
			for (int i=pos+1;i<len;i++) f+=path.toCharArray()[i];
			
			HHD.createFile(d, f);
		}
		
		File file=new File(path);
		try {
			RandomAccessFile  raf=new RandomAccessFile(file, "rw");
			MappedByteBuffer buffer=raf.getChannel().map(FileChannel.MapMode.READ_WRITE, from, length);
			byte[] ans=new byte[length];
			for (int i=0;i<length;i++) ans[i]=buffer.get(i);
			raf.close();
			return ans;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static void writeByte(String path, byte[] message){
		writeByte(path,message,0,message.length);
	}
	
	public static void writeByte(String path,byte[] message, long from, int length){
		if (!HHD.fileExiste(path)){
			String d=new String(),f=new String();
			int len=path.length(),pos=0;
			for (pos=len-1;pos>=0;pos--){
				if (path.toCharArray()[pos]=='/') break;
			}
			
			for (int i=0;i<=pos;i++) d+=path.toCharArray()[i];
			for (int i=pos+1;i<len;i++) f+=path.toCharArray()[i];
			
			HHD.createFile(d, f);
		}
		
		File file=new File(path);
		try {
			RandomAccessFile raf=new RandomAccessFile(file, "rw");
			MappedByteBuffer buffer=raf.getChannel().map(FileChannel.MapMode.READ_WRITE, from, length);
			for (int i=0;i<length;i++){
				buffer.put(message[i]);
			}
			raf.close();
		} catch (IOException e) {
		}
	}
	
	public static String getAppPath(){
		return System.getProperty("user.dir");
	}
	
	public static void copyFile(String from, String to){
		writeFile(to, "", "");
		
		File fileIn,fileOut;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fileIn = new File(from);
			fileOut =new File(to);
			fis=new FileInputStream(fileIn);
			fos=new FileOutputStream(fileOut);
			FileChannel fi=fis.getChannel();
			FileChannel fo=fos.getChannel();
			fi.transferTo(0, fi.size(), fo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addByte(String path,byte[] message){
		File file=new File(path);
		FileOutputStream fos;
		try {
			fos=new FileOutputStream(file,true);
			fos.write(message);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long getFileLength(String path){
		File file=new File(path);
		return file.length();
	}
	
	public static void deleteFile(String path){
		File file=new File(path);
		file.delete();
	}
	
	public static void deleteFolder(String path){
		File file=new File(path);
		if (file.isFile()){
			deleteFile(path);
		}else{
			File[] kid=file.listFiles();
			for (int i=0;i<kid.length;i++){
				if (kid[i].isFile()){
					deleteFile(kid[i].getPath());
				}else{
					deleteFolder(kid[i].getPath());
				}
			}
		}
	}
}