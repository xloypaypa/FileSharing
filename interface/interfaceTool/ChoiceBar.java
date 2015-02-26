package interfaceTool;

import java.util.*;
import javax.swing.*;

public class ChoiceBar implements ToolInterface {
	JPanel show;
	JComboBox<String> choice;
	JLabel name;
	
	public ChoiceBar(){
		show=new JPanel();
		this.show.setLayout(null);
		this.show.setBackground(null);
		
		name=new JLabel();
		name.setBackground(null);
		this.show.add(name);
		
		choice=new JComboBox<String>();
		this.show.add(choice);
	}
	
	public void setName(String name){
		this.name.setText(name);
	}
	public void addChoice(String item){
		choice.addItem(item);
		if (choice.getItemCount()==1){
			choice.setSelectedItem(item);
		}
	}
	public void removeChoice(String item){
		choice.removeItem(item);
		if (choice.getItemCount()==0){
			choice.setSelectedItem(null);
		}
	}
	public void setChoices(Vector <String> a){
		for (int i=0;i<a.size();i++){
			choice.addItem(a.get(i));
		}
		if (a.size()!=0) choice.setSelectedItem(a.get(0));
	}
	public String getChoosenItem(){
		String ans=new String();
		try{
			ans=choice.getSelectedItem().toString();
		}catch(Exception e){
			ans="null";
		}
		return ans;
	}
	
	@Override
	public JPanel getPanel(){
		return this.show;
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.show.setBounds(x, y, width, height);
		this.updateBounds();
	}
	@Override
	public void updateBounds() {
		name.setBounds(0, 0, show.getWidth(), show.getHeight()/2);
		choice.setBounds(0, show.getHeight()/2, show.getWidth(), show.getHeight()/2);
	}
}
