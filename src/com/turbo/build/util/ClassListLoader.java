package com.turbo.build.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassListLoader {
	private List<String> clazzList = new ArrayList<String>();

	public List<String> getClazzList() {
		return clazzList;
	}
	
	public void loadClassList() {
		getClass(ProjectInfo.output);
	}
	
	private void getClass(String path) {
		File file = new File(path);
		
		if (file.isDirectory()) {
			File[] dirFile = file.listFiles();
			
			for (File f : dirFile) {
				
				if (f.isDirectory())
					getClass(f.getAbsolutePath());
				else {
					if (f.getName().endsWith(".class"))
						clazzList.add(f.getAbsolutePath());
				}
			}
		}
	}
}
