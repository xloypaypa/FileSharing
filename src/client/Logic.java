package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import database.HHD;
import web.Node;
import web.client.Client;

public class Logic extends Thread {
	String ip;
	int port;
	String path,savePath;
	String command;
	
	void setMessage(String ip,int port, String path,String savePath,String command){
		this.ip=new String(ip);
		this.port=port;
		this.path=new String(path);
		this.savePath=new String(savePath);
		this.command=new String(command);
	}
	
	public void run(){
		if (command.equals("download")){
			try {
				download();
			}	catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}else if (command.equals("upload")){
			try {
				upload();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}else if (command.equals("open")){
			try{
				open();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}else if (command.equals("shut down")){
			try{
				shutDown();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}else if (command.equals("delete")){
			try{
				delete();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}else if (command.equals("update file lists")){
			try{
				update();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}else if (command.equals("update end")){
			try{
				updateOK();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}
	}
	
	private void updateOK() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node,ret;
		node=new Node();
		
		node.setCommand("update end");
		node.setPath(path);
		node.setPort(getFreePort());
		node.setSavePath(savePath);
		client.connect();
		ret=client.send(node);
		if (ret.getCommand().equals("yes")){
			JOptionPane.showMessageDialog(null, "ok", "message",JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "not", "message",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void update() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node;
		node=new Node();
		
		node.setCommand("update file lists");
		node.setPath(path);
		node.setPort(getFreePort());
		node.setSavePath(savePath);
		client.connect();
		client.send(node);
	}
	
	private void delete() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node;
		node=new Node();
		
		node.setCommand("delete");
		node.setPath(path);
		node.setPort(getFreePort());
		node.setSavePath(savePath);
		client.connect();
		client.send(node);
	}
	
	private void shutDown() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node;
		node=new Node();
		
		node.setCommand("shutDown");
		node.setPath(path);
		node.setPort(getFreePort());
		node.setSavePath(savePath);
		client.connect();
		client.send(node);
	}

	private void open() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node;
		node=new Node();
		
		node.setCommand("open");
		node.setPath(path);
		node.setPort(getFreePort());
		node.setSavePath(savePath);
		client.connect();
		client.send(node);
	}

	private void upload() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node,ret;
		node=new Node(); ret=new Node();
		
		node.setCommand("clean file");
		node.setPath(savePath);
		
		client.connect();
		client.send(node);
		
		node.setCommand("upload");
		node.setLength(HHD.getFileLength(path));
		node.setPath(path);
		node.setSavePath(savePath);
		
		long part=Client.getPart(node.getLength());
		for (long i=0;i<=part;i++){
			node.setPart(i);
			
			while (Client.getActiveCount()==Client.getMaxThread());
			
			client.connect();
			ret=client.send(node);
			
			node.setPort(ret.getPort());
			
			if (ret.getCommand().equals("ok")){
				client.startFileClient(node);
			}else{
				i--;
			}
		}
		
		while (Client.getActiveCount()!=0){
//			System.out.println(Client.getActiveCount());
		}
		JOptionPane.showMessageDialog(null, "upload ok", "message",JOptionPane.INFORMATION_MESSAGE);
	}

	private void download() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		Node node,ret;
		node=new Node(); ret=new Node();
		node.setCommand("get file length");
		node.setPath(path);
		node.setSavePath(savePath);
		node.setPort(getFreePort());
		
		client.connect();
		ret=client.send(node);
		
		HHD.cleanFile(node.getSavePath());
		
		long part=Client.getPart(ret.getLength());
		System.out.println("part: "+part);
		System.out.println(part);
		for (long i=0;i<=part;i++){
			
			node.setCommand("download");
			node.setLength(ret.getLength());
			node.setPath(path);
			node.setPart(i);
			node.setSavePath(savePath);
			
			while (Client.getActiveCount()==Client.getMaxThread());
			
			client.connect();
			ret=client.send(node);
			node.setPort(ret.getPort());
			
			if (ret.getCommand().equals("ok")){
				client.startFileClient(node);
			}else{
				i--;
			}
		}
		
		while (Client.getActiveCount()!=0);
		JOptionPane.showMessageDialog(null, "download ok", "message",JOptionPane.INFORMATION_MESSAGE);
	}
	
	private int getFreePort(){
		int ans;
		ServerSocket ss;
		try {
			ss = new ServerSocket(0);
			ans=ss.getLocalPort();
			ss.close();
		} catch (IOException e) {
			ans=8989;
		}
		
		return ans;
	}
}
