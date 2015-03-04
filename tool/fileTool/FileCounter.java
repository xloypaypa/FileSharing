package fileTool;

import java.io.File;

import database.HHD;

public class FileCounter extends Thread {
	String path=HHD.getAppPath()+"/allFiles.txt";
	
	@Override
	public void run(){
		HHD.writeFile(path,"");
		
		File[] roots = File.listRoots();
		for (int i=0;i<roots.length;i++){
			solve(roots[i]);
		}
	}

	private void solve(File file) {
		if (file.isFile()){
			HHD.addWriteFile(path, file.getPath());
			return ;
		}else{
			File[] kid=file.listFiles();
			if (kid==null) return ;
			for (int i=0;i<kid.length;i++){
				solve(kid[i]);
			}
		}
	}
}
