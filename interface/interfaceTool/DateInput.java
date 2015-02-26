package interfaceTool;

import java.util.Calendar;

import javax.swing.*;

public class DateInput implements ToolInterface {
	JPanel show;
	JLabel name,bk1,bk2;
	JTextField year,month,day;
	int nameWidth;
	
	public DateInput() {
		show=new JPanel();
		show.setLayout(null);
		show.setBackground(null);
		
		nameWidth=60;
		
		name=new JLabel();
		name.setBounds(0, 0, nameWidth, 20);
		name.setBackground(null);
		
		year=new JTextField();
		year.setBounds(nameWidth, 0, 40, 20);
		
		bk1=new JLabel();
		bk1.setBounds(nameWidth+40, 0, 10, 20);
		bk1.setText(":");
		bk1.setBackground(null);
		
		month=new JTextField();
		month.setBounds(nameWidth+50, 0, 30, 20);
		
		bk2=new JLabel();
		bk2.setBounds(nameWidth+80, 0, 10, 20);
		bk2.setText(":");
		bk2.setBackground(null);
		
		day=new JTextField();
		day.setBounds(nameWidth+90, 0, 30, 20);
		
		show.add(name);
		show.add(bk1);
		show.add(bk2);
		show.add(year);
		show.add(month);
		show.add(day);
	}
	
	public void setName(String name){
		this.name.setText(name);
	}
	public void setNameWidth(int width){
		this.nameWidth=width;
		updateBounds();
	}
	
	public void setDate(Calendar time){
		year.setText(time.get(Calendar.YEAR)+"");
		month.setText(time.get(Calendar.MAY)+"");
		day.setText(time.get(Calendar.DAY_OF_MONTH)+"");
	}
	public Calendar getDate(){
		Calendar ans=Calendar.getInstance();
		int y,m,d;
		y=Integer.valueOf(year.getText());
		m=Integer.valueOf(month.getText());
		d=Integer.valueOf(day.getText());
		ans.set(y, m, d);
		return ans;
	}
	
	public void clear(){
		year.setText("");
		month.setText("");
		day.setText("");
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
		name.setBounds(0, 0, nameWidth, 20);
		year.setBounds(nameWidth, 0, 40, 20);
		bk1.setBounds(nameWidth+40, 0, 10, 20);
		month.setBounds(nameWidth+50, 0, 30, 20);
		bk2.setBounds(nameWidth+80, 0, 10, 20);
		day.setBounds(nameWidth+90, 0, 30, 20);
	}

}
