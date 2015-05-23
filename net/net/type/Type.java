package net.type;

import java.util.Vector;

public class Type {
	
	Vector <ExtraType> extra;
	
	public Type(){
		this.extra=new Vector<ExtraType>();
	}
	public Type(Type other){
		this.extra=new Vector<ExtraType>(other.extra);
	}
	
	public Vector <ExtraType> getExtra(){
		return extra;
	}
	
	public void addExtra(String title,String message){
		ExtraType et=new ExtraType(title,message);
		extra.addElement(et);
	}
	public boolean extraExist(String title){
		for (int i=0;i<extra.size();i++){
			if (extra.get(i).getTitle().equals(title)) return true;
		}
		return false;
	}
	public String getExtraMessage(String title){
		for (int i=0;i<extra.size();i++){
			if (extra.get(i).getTitle().equals(title)) return extra.get(i).getMessage();
		}
		return new String();
	}

	public String format() {
		String ans=new String();
		for (int i=0;i<extra.size();i++){
			ans+=extra.get(i).format();
		}
		return ans;
	}

	
	public void solveTypeMessage(Vector<String> message) {
		extra=new Vector<ExtraType>();
		ExtraType now;
		for (int i=0;i<message.size();i++){
			if(message.get(i).equals("[extra title]")){
				now=new ExtraType();
				now.setTitle(message.get(i+1));
				now.setMessage(message.get(i+3));
				extra.add(now);
			}
		}
	}

	public String getAllMessage() {
		return this.format()+"$";
	}
}
