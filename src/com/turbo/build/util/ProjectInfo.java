package com.turbo.build.util;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class ProjectInfo {
	public static String classpath = null;
	public static String projectLocation = null;
	public static String output = null;
	
	public static IProject project = null;

	public static void projectInfo(ISelection selection) {
		Object element = ((IStructuredSelection) selection).getFirstElement();

		if(null == element) {
			return;
		}
		
		project = ((IResource)element).getProject();
		
		projectLocation = project.getLocation().toString();
		
		classpath = projectLocation	+ File.separator + ".classpath";
		File file = new File(classpath);
		if (!file.exists()) {
			classpath = null;
		}
		
		if(classpath != null) {
			try {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(classpath);
				List<Element> list = doc.getRootElement().getChildren();
				for (Element e : list) {
					if (e.getAttributeValue("kind").equals("output")) {
						output = projectLocation + File.separator
								+ e.getAttributeValue("path");
						break;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
