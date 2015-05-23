package net.type;

import java.util.Vector;

public class Node extends Type {
	private int port;
	
	private String command;
	
	private String path,savePath;
	private long length,from,to;
	
	public Node(){
		port=0;
		command=new String();
		path=new String();
		savePath=new String();
		length=0;
		from=0;
		to=0;
	}
	public Node(Node other){
		this.port=other.port;
		this.command=new String(other.command);
		this.path=new String(other.path);
		this.savePath=new String(other.savePath);
		this.length=other.length;
		this.from=other.from;
		this.to=other.to;
	}
	
	public void setPort(int port){
		this.port=port;
	}
	public void setCommand(String command){
		this.command=new String(command);
	}
	public void setPath(String path){
		this.path=new String(path);
	}
	public void setSavePath(String savePath){
		this.savePath=new String(savePath);
	}
	public void setLength(long length){
		this.length=length;
	}
	public void setFrom(long from){
		this.from=from;
	}
	public void setTo(long to){
		this.to=to;
	}
	
	public int getPort(){
		return this.port;
	}
	public String getCommand(){
		return this.command;
	}
	public String getPath(){
		return this.path;
	}
	public String getSavePath(){
		return this.savePath;
	}
	public long getLength(){
		return this.length;
	}
	public long getFrom(){
		return this.from;
	}
	public long getTo(){
		return this.to;
	}
	
	public String format(){
		String ans=new String();
		ans+="[port]\n"+port+"\n";
		ans+="[path]\n"+path+"\n";
		ans+="[savePath]\n"+savePath+"\n";
		ans+="[length]\n"+length+"\n";
		ans+="[from]\n"+from+"\n";
		ans+="[to]\n"+to+"\n";
		ans+="[command]\n"+command+"\n";
		ans+=super.format();
		return ans;
	}
	
	public void solve(String message){
		char[] a=message.toCharArray();
		Vector <String> ans=new Vector<String>();
		String now=new String();
		for (int i=0;i<a.length;i++){
			if (a[i]=='$'){
				break;
			}
			else if (a[i]=='\n'){
				ans.add(now);
				now=new String();
			}else{
				now+=a[i];
			}
		}
		solveTypeMessage(ans);
	}
	
	public void solveTypeMessage(Vector <String> message){
		for (int i=0;i<message.size()-1;i++){
			if(message.get(i).equals("[port]")){
				port=Integer.valueOf(message.get(i+1));
			}else if (message.get(i).equals("[path]")){
				path=new String(message.get(i+1));
			}else if (message.get(i).equals("[savePath]")){
				savePath=new String(message.get(i+1));
			}else if (message.get(i).equals("[length]")){
				length=Long.valueOf(message.get(i+1));
			}else if (message.get(i).equals("[from]")){
				from=Long.valueOf(message.get(i+1));
			}else if (message.get(i).equals("[to]")){
				to=Long.valueOf(message.get(i+1));
			}else if (message.get(i).equals("[command]")){
				command=new String(message.get(i+1));
			}
		}
		super.solveTypeMessage(message);
	}
}
