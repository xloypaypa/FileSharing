package net.type;

import java.util.Vector;

public class ExtraType {
	String title,message;
	public ExtraType(){
		this.title=new String();
		this.message=new String();
	}
	public ExtraType(String title, String message){
		this.title=new String(title);
		this.message=new String(message);
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public void setMessage(String message){
		this.message=message;
	}
	public String getTitle(){
		return this.title;
	}
	public String getMessage(){
		return this.message;
	}

	public String format() {
		String ans=new String();
		ans+="[extra title]\n";
		ans+=this.title+"\n";
		ans+="[extra message]\n";
		ans+=this.message+"\n";
		return ans;
	}

	public void solveTypeMessage(Vector<String> message) {
		for (int i=0;i<message.size();i+=2){
			if (message.get(i).equals("[extra title]")){
				this.title=message.get(i+1);
			}else if (message.get(i).equals("extra message")){
				this.message=message.get(i+1);
			}
		}
	}
	public String getAllMessage() {
		return this.format();
	}
	
}
