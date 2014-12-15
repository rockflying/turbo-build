package com.buildpath.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Element;

public class Jar {
	String name;
	String path;
	String version;
	String md5;
	long modifyTime;
	boolean absolute;
	List<String> clazzes = new ArrayList<String>();
	
	Element element;

	int order; // the order in the build path

	boolean valid = false; // to denote whether the jar is valid

	public Jar(String path) {
		this.path = path;
		initJar();
	}
	
	public Jar(ClasspathEntry entry) {
		this(entry.path);
		this.element = entry.element;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getVersion() {
		return version;
	}

	public String getMd5() {
		return md5;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public int getOrder() {
		return order;
	}

	public boolean isValid() {
		return valid;
	}
	
	public Element getElement() {
		return element;
	}

	public int compareTo(Jar jar) {

		if (md5.equals(jar.md5)) {
			return 0;
		}

		if (version.equals("unknown") || jar.getVersion().equals("unknown")) {
			return (int) (jar.modifyTime - modifyTime);
		}
		return versionCompare(jar.getVersion(), version);
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "name: " + name + "\n" +
			   "path: " + path + "\n" +
		       "version: " + version + "\n" +
			   "md5: " + md5 + "\n" +
		       "modifyTime: " + modifyTime + "\n" +
			   "valid: " + valid;
	}

	private int versionCompare(String ver1, String ver2) {
		int res = Integer.MIN_VALUE;
		String[] v1 = ver1.split("\\.");
		String[] v2 = ver2.split("\\.");

		for (int i = 0, j = 0; i < v1.length && j < v2.length; ++i, ++j) {
			res = Integer.parseInt(v1[i]) - Integer.parseInt(v2[j]);
			if (res != 0) {
				return res;
			}
		}

		return v1.length - v2.length; // in case the length of version is
										// different
	}

	/**
	 * initialize the jar with certain info
	 */
	private void initJar() {
		// check whether is valid
		File file = new File(path);
		if (!file.exists()) {
			valid = false;
			return;
		}

		if (file.isAbsolute()) {
			absolute = true;
		} else {
			absolute = false;
		}

		valid = true;
		md5 = Md5Util.getMd5ByFile(file); // get the md5
		name = file.getName();
		modifyTime = file.lastModified()/1000;  //seconds

		// suppose that version contains at least one "."
		Pattern pattern = Pattern.compile("(\\d+(\\.\\d+){1,})");
		Matcher m = pattern.matcher(name);
		if (m.find()) {
			version = m.group(1);
		} else {
			version = "unknown";
		}
		parseJar();
		
		//modify jar file name to trim version info
		name = name.substring(0, name.indexOf(".jar"));
		
		if (name.contains(version)) {
			if (name.endsWith(version)) {
				name = name.substring(0, name.indexOf(version) - 1);
			} else {
				int index = name.indexOf(version);

				name = name.substring(0, index - 1)
						+ name.substring(index + version.length());
			}
		}
		name = name.toLowerCase();
	}

	/**
	 * open the jar file to get extra info
	 */
	private void parseJar() {
		final String[] vers = { "Implementation-Version", "Bundle-Version" };
		File file = new File(path);

		try {
			JarFile jar = new JarFile(file);

			if (version == "unknown") {
				Manifest manifest = jar.getManifest();
				Attributes mainAttr = manifest.getMainAttributes();
				for (String key : vers) {
					String ver = mainAttr.getValue(key);
					if (ver != null) {
						version = ver;
						break;
					}
				}
				
				// the version is still unknown
				if (version == "unknown") {
					Map<String, Attributes> entries = manifest.getEntries();
					for (Map.Entry<String, Attributes> entry : entries.entrySet()) {
						Attributes values = entry.getValue();
						if(values.containsKey(vers[0])) {
							version = values.getValue(vers[0]);
							break;
						}
					}
				}
			}
			
			Enumeration<JarEntry> files = jar.entries();
			while (files.hasMoreElements()) {
				JarEntry entry = files.nextElement();
				
				if (entry.toString().endsWith(".class")) {
//					System.out.println(entry);
					clazzes.add(entry.toString());
				}		
			}
			
			jar.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		//dexlib2-2.0.3-dev.jar
//		//jsoup-1.8.1.jar
//		//java_cup.jar
//		//org.hamcrest.core_1.3.0.jar
//		Jar jar = new Jar("dexlib2-2.0.3-dev.jar");
//		System.out.println(jar.toString());
//	}
}
