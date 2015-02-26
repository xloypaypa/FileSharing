package interfaceTool;

import javax.swing.*;

public interface ToolInterface {
	JPanel getPanel();
	void setBounds(int x,int y,int width,int height);
	void updateBounds();
}
