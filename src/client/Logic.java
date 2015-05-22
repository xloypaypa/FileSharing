package client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import type.Node;

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
		}else if (command.equals("shut down")){
			try{
				shutDown();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "net error", "error",JOptionPane.ERROR_MESSAGE);
				return ;
			}
		}
	}
	
	private void shutDown() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node;
		node=new Node();
		
		node.setCommand("shutDown");
		node.setPath(path);
		node.setPort(0);
		node.setSavePath(savePath);
		client.connect();
		client.send(node);
	}

	private void upload() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node,ret;
		node=new Node(); ret=new Node();
		
		client.connect();
		ret=client.send(node);
		
		File file=new File(path);
		
		node.setCommand("upload");
		node.setFrom(0);
		node.setTo(file.length());
		node.setLength(file.length());
		node.setPath(path);
		node.setSavePath(savePath);
		node.setPort(0);
		
		client.connect();
		ret=client.send(node);
		
		node.setPort(ret.getPort());
		client.startFileClient(node);
		
		while (!client.isEnd()){
			UI.showState.setStringPainted(true);
			UI.showState.setValue(client.getStatus());
		}
		UI.show.remove(UI.showState);
		
		JOptionPane.showMessageDialog(null, "upload ok", "message",JOptionPane.INFORMATION_MESSAGE);
	}

	private void download() throws UnknownHostException, IOException {
		Client client;
		client=new Client(ip, port);
		
		Node node,ret;
		node=new Node(); ret=new Node();
		
		node.setCommand("get file length");
		node.setFrom(0);
		node.setTo(0);
		node.setPort(0);
		node.setLength(0);
		node.setSavePath(savePath);
		node.setPath(path);
		
		client.connect();
		ret=client.send(node);
		
		node.setCommand("download");
		node.setFrom(0);
		node.setTo(ret.getLength());
		node.setLength(ret.getLength());
		
		client.connect();
		ret=client.send(node);
		
		node.setPort(ret.getPort());
		client.startFileClient(node);
		
		while (!client.isEnd()){
			UI.showState.setStringPainted(true);
			UI.showState.setValue(client.getStatus());
		}
		UI.show.remove(UI.showState);
		
		JOptionPane.showMessageDialog(null, "download ok", "message",JOptionPane.INFORMATION_MESSAGE);
	}
}
