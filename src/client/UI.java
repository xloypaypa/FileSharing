package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import interfaceTool.Input;
import interfaceTool.LineButtons;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UI {
	JFrame window;
	JPanel show;
	LineButtons lbs;
	Input ip,port,path,savePath;
	
	public UI(){
		window=new JFrame("Client");
		window.setBounds(200, 100, 250, 680);
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		show=new JPanel();
		show.setBounds(0, 0, 250, 680);
		show.setLayout(null);
		
		addItem();
		
		window.add(show);
	}
	
	public void show(){
		window.setVisible(true);
	}

	private void addItem() {
		int width=200;
		
		ip=new Input();
		ip.setBounds(10, 10, width, 40);
		ip.setName("ip:");
		show.add(ip.getPanel());
		
		port=new Input();
		port.setBounds(10, 60, width, 40);
		port.setName("port:");
		show.add(port.getPanel());
		
		path=new Input();
		path.setBounds(10, 110, width, 40);
		path.setName("path:");
		show.add(path.getPanel());
		
		savePath=new Input();
		savePath.setBounds(10, 160, width, 40);
		savePath.setName("save path:");
		show.add(savePath.getPanel());
		
		lbs=new LineButtons();
		lbs.setBounds(10, 210, width, 400);
		lbs.addButton("download", 40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIP(ip.getWord())){
					JOptionPane.showMessageDialog(window, "please input ip right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int pt;
				try{
					pt=Integer.valueOf(port.getWord());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(window, "please input port right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				Logic logic=new Logic();
				logic.setMessage(ip.getWord(), pt, path.getWord(), savePath.getWord(), "download");
				logic.start();
			}
		});
		
		lbs.addBreak();
		
		lbs.addButton("upload", 40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIP(ip.getWord())){
					JOptionPane.showMessageDialog(window, "please input ip right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int pt;
				try{
					pt=Integer.valueOf(port.getWord());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(window, "please input port right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				Logic logic=new Logic();
				logic.setMessage(ip.getWord(), pt, path.getWord(), savePath.getWord(), "upload");
				logic.start();
			}
		});
		
		lbs.addBreak();
		
		lbs.addButton("open", 40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIP(ip.getWord())){
					JOptionPane.showMessageDialog(window, "please input ip right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int pt;
				try{
					pt=Integer.valueOf(port.getWord());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(window, "please input port right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				Logic logic=new Logic();
				logic.setMessage(ip.getWord(), pt, path.getWord(), savePath.getWord(), "open");
				logic.start();
			}
		});
		
		lbs.addBreak();
		
		lbs.addButton("delete", 40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIP(ip.getWord())){
					JOptionPane.showMessageDialog(window, "please input ip right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int pt;
				try{
					pt=Integer.valueOf(port.getWord());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(window, "please input port right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				Logic logic=new Logic();
				logic.setMessage(ip.getWord(), pt, path.getWord(), savePath.getWord(), "delete");
				logic.start();
			}
		});
		
		lbs.addBreak();
		
		lbs.addButton("shut down", 40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIP(ip.getWord())){
					JOptionPane.showMessageDialog(window, "please input ip right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int pt;
				try{
					pt=Integer.valueOf(port.getWord());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(window, "please input port right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				Logic logic=new Logic();
				logic.setMessage(ip.getWord(), pt, path.getWord(), savePath.getWord(), "shut down");
				logic.start();
			}
		});
		
		lbs.addBreak();
		
		lbs.addButton("update file", 40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIP(ip.getWord())){
					JOptionPane.showMessageDialog(window, "please input ip right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int pt;
				try{
					pt=Integer.valueOf(port.getWord());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(window, "please input port right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				Logic logic=new Logic();
				logic.setMessage(ip.getWord(), pt, path.getWord(), savePath.getWord(), "update file lists");
				logic.start();
			}
		});
		
		lbs.addBreak();
		
		lbs.addButton("update end", 40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIP(ip.getWord())){
					JOptionPane.showMessageDialog(window, "please input ip right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int pt;
				try{
					pt=Integer.valueOf(port.getWord());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(window, "please input port right", "error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				Logic logic=new Logic();
				logic.setMessage(ip.getWord(), pt, path.getWord(), savePath.getWord(), "update end");
				logic.start();
			}
		});
		
		show.add(lbs.getPanel());
	}
	
	private boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }
}
