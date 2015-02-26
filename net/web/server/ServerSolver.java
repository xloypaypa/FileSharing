package web.server;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import database.HHD;
import web.Node;

public class ServerSolver extends Thread {
	Node node;
	Socket socket;
	
	public void setNode(Node node){
		this.node=new Node(node);
	}
	public void setSocket(Socket socket){
		this.socket=socket;
	}
	
	@Override
	public void run(){
		if (node.getCommand().equals("get file length")){
			node.setLength(HHD.getFileLength(node.getPath()));
		}else if (node.getCommand().equals("download")||node.getCommand().equals("upload")){
			FileServer fs=new FileServer();
			fs.setNeed(node);
			fs.buildServer();
			if (fs.beFail()){
				node.setCommand("change port");
			}else{
				node.setCommand("ok");
				fs.start();
			}
		}else if (node.getCommand().equals("delete")){
			if (HHD.fileExiste(node.getPath())||HHD.folderExiste(node.getPath())){
				HHD.deleteFolder(node.getPath());
				node.setCommand("ok");
			}else{
				node.setCommand("not exist");
			}
		}else if (node.getCommand().equals("open")){
			if (!Desktop.isDesktopSupported()){
				node.setCommand("not support");
			}
			else if (HHD.fileExiste(node.getPath())||HHD.folderExiste(node.getPath())){
				Desktop d=Desktop.getDesktop();
				try {
					d.open(new File(node.getPath()));
					node.setCommand("ok");
				} catch (IOException e) {
					node.setCommand("error");
				}
				
			}else{
				node.setCommand("not exist");
			}
		}else if (node.getCommand().equals("clean file")){
			if (HHD.fileExiste(node.getPath())||HHD.folderExiste(node.getPath())){
				HHD.cleanFile(node.getPath());
				node.setCommand("ok");
			}else{
				node.setCommand("not exist");
			}
		}
		
		try {
			OutputStream out=socket.getOutputStream();
			out.write(node.getAllMessage().getBytes());
			out.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
