package interfaceTool;

import javax.swing.*;

public class Input implements ToolInterface {
	JPanel show;
	JLabel name;
	JTextField inputer;
	
	public Input() {
		show=new JPanel();
		show.setLayout(null);
		show.setBackground(null);
		
		name=new JLabel();
		name.setBounds(0, 0, 700, 20);
		name.setBackground(null);
		
		inputer=new JTextField();
		inputer.setBounds(0, 20, 0, 20);
		
		show.add(name);
		show.add(inputer);
	}
	
	public void setName(String s){
		name.setText(s);
	}
	public void setWord(String s){
		inputer.setText(s);
	}
	public String getName(){
		return name.getText();
	}
	public String getWord(){
		return inputer.getText();
	}
	
	public void clear(){
		setWord("");
	}

	@Override
	public JPanel getPanel() {
		return show;
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		show.setBounds(x, y, width, height);
		updateBounds();
	}

	@Override
	public void updateBounds() {
		name.setBounds(0, 0, show.getWidth(), show.getHeight()/2);
		inputer.setBounds(0, show.getHeight()/2, show.getWidth(), show.getHeight()/2);
	}

}
