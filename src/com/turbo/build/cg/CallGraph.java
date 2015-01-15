package com.turbo.build.cg;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;

import com.turbo.build.util.Jar;

public class CallGraph {

	private List<Jar> jars = null;

	public CallGraph(List<Jar> jars) {
		this.jars = jars;
	}

	public void buildCallGraph() {
		ClassParser cp;
		try {
			for (Jar jarfile : jars) {

				File f = new File(jarfile.getPath());

				if (!f.exists()) {
					System.err.println("Jar file " + jarfile.getPath()
							+ " does not exist");
				}
				System.out.println("building jar: " + jarfile.getFullname());
				JarFile jar = new JarFile(f);

				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					if (entry.isDirectory())
						continue;

					if (!entry.getName().endsWith(".class"))
						continue;

					cp = new ClassParser(jarfile.getPath(), entry.getName());
					ClassVisitor visitor = new ClassVisitor(cp.parse());
					visitor.start();
				}

				jar.close();
			}
		} catch (IOException e) {
			System.err.println("Error while processing jar: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
