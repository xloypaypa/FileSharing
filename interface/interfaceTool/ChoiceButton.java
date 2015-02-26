package interfaceTool;

import javax.swing.*;

import java.util.*;

public class ChoiceButton implements ToolInterface {
	JPanel show;
	ButtonGroup group;
	Vector <String> name;
	Vector <JRadioButton> buttons;
	JLabel title;
	int titleHeight;
	public ChoiceButton() {
		this.show=new JPanel();
		this.group=new ButtonGroup();
		this.name=new Vector<String>();
		this.buttons=new Vector<JRadioButton>();
		
		show.setBackground(null);
		show.setLayout(null);
		
		title=new JLabel();
		title.setBackground(null);
		
		show.add(title);
		titleHeight=20;
	}
	public JPanel getPanel(){
		return this.show;
	}
	public void setBounds(int x,int y,int width,int height){
		show.setBounds(x, y, width, height);
		updateBounds();
	}
	
	public void setTitleHeight(int height){
		this.titleHeight=height;
		updateBounds();
	}
	
	public void setName(String s){
		title.setText(s);
	}
	public JRadioButton addChoose(String name,int width,int height){
		JRadioButton now=new JRadioButton(name);
		if (this.buttons.size()==0){
			now.setBounds(0, titleHeight, width, height);
			now.setSelected(true);
		}else{
			JRadioButton last=this.buttons.get(this.buttons.size()-1);
			int need=last.getX()+last.getWidth()+width;
			if (need<=show.getWidth()){
				now.setBounds(last.getX()+last.getWidth(), last.getY(), width, height);
			}else{
				now.setBounds(0, last.getY()+last.getHeight(), width, height);
			}
		}
		this.name.addElement(name);
		this.buttons.addElement(now);
		this.show.add(now);
		this.group.add(now);
		return now;
	}
	public void removeChoose(String name){
		for (int i=0;i<this.name.size();i++){
			if (this.name.get(i).equals(name)){
				this.show.remove(this.buttons.get(i));
				this.group.remove(this.buttons.get(i));
				this.buttons.remove(i);
				this.name.remove(i); i--;
			}
		}
	}
	public void removeChoose(JRadioButton b){
		for (int i=0;i<this.buttons.size();i++){
			if (this.buttons.get(i).equals(b)){
				this.show.remove(this.buttons.get(i));
				this.group.remove(this.buttons.get(i));
				this.name.remove(i);
				this.buttons.remove(i);
				i--;
			}
		}
	}
	
	public String getChoosen(){
		Enumeration<AbstractButton> choose=group.getElements();
		while (choose.hasMoreElements()){
			AbstractButton nowButton = choose.nextElement();
			if (nowButton.isSelected()) return nowButton.getText();
		}
		return "";
	}
	
	public void updateBounds(){
		title.setBounds(0, 0, show.getWidth(), titleHeight);
		if (this.buttons.size()==0) return ;
		
		this.buttons.get(0).setBounds(0, titleHeight, buttons.get(0).getWidth(), buttons.get(0).getHeight());
		for (int i=1;i<this.buttons.size();i++){
			JRadioButton last=this.buttons.get(i-1);
			JRadioButton now=this.buttons.get(i);
			int width=this.buttons.get(i).getWidth();
			int height=this.buttons.get(i).getHeight();
			int need=last.getX()+last.getWidth()+width;
			if (need<=show.getWidth()){
				now.setBounds(last.getX()+last.getWidth(), last.getY(), width, height);
			}else{
				now.setBounds(0, last.getY()+last.getHeight(), width, height);
			}
		}
	}
}
