package com.turbo.build.util;

import java.util.ArrayList;
import java.util.List;

/**
 * When building call graphs, skip these packages
 *
 */
public class SkippedPackages {
	public static List<String> packlist = new ArrayList<String> ();
	
	static {
		packlist.add("com.sun");
		
		packlist.add("java.applet");
		packlist.add("java.awt");
		packlist.add("java.beans");
		packlist.add("java.io");
		packlist.add("java.lang");
		packlist.add("java.math");
		packlist.add("java.net");
		packlist.add("java.nio");
		packlist.add("java.rmi");
		packlist.add("java.security");
		packlist.add("java.sql");
		packlist.add("java.text");
		packlist.add("java.util");

		packlist.add("javax.accessibility");
		packlist.add("javax.annotation");
		packlist.add("javax.imageio");
		packlist.add("javax.lang");
		packlist.add("javax.management");
		packlist.add("javax.naming");
		packlist.add("javax.print");
		packlist.add("javax.rmi");
		packlist.add("javax.script");
		packlist.add("javax.security");
		packlist.add("javax.sound");
		packlist.add("javax.sql");
		packlist.add("javax.swing");
		packlist.add("javax.tools");
		packlist.add("javax.xml");
		
		packlist.add("org.ietf");
		packlist.add("org.omg");
		packlist.add("org.w3c");
		packlist.add("org.xml");
		
		packlist.add("sunw.io");
		packlist.add("sunw.util");
	}
}
