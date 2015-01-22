package com.turbo.build.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.bcel.classfile.ClassParser;
import org.jdom2.Element;

public class Jar {
	String name;
	String fullname;
	String path;
	String version;
	String md5;
	long modifyTime;
	boolean absolute;
//	List<Clazz> clazzes = new ArrayList<Clazz>();
//	List<String> clazzNames = new ArrayList<String>();
	Map<String, Clazz> clazzes = new HashMap<String, Clazz>();
	
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

	/**
	 * Compare two jars
	 * @param jar
	 * objective jar
	 * @return
	 * -1: this --> jar, jar contains this;
	 *  0: this == jar , jar is same as this.jar;
	 *  1: this <-- jar, this contains jar;
	 *  2: this and jar contain certain same parts
	 *  4: this and jar are completely different
	 */
	public int compareTo(Jar jar) {

		if (md5.equals(jar.md5)) {
			return 0;
		}
		
		Map<String, Clazz> srcClazzes = clazzes;
		Map<String, Clazz> objClazzes = jar.getClazzes();
		
		Iterator<String> iter = null;
		
		List<String> srcMethods = new ArrayList<String>();
		List<String> objMethods = new ArrayList<String>();
		
		List<String> srcFields = new ArrayList<String>();
		List<String> objFields = new ArrayList<String>();
		
		iter = srcClazzes.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Clazz srcClazz = srcClazzes.get(key);
			srcMethods.addAll(srcClazz.getMethodList());
			srcFields.addAll(srcClazz.getFieldList());
		}
		
		iter = objClazzes.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Clazz objClazz = objClazzes.get(key);
			objMethods.addAll(objClazz.getMethodList());
			objFields.addAll(objClazz.getFieldList());
		}
		
		boolean srcContainsObj = srcMethods.containsAll(objMethods)
				&& srcFields.containsAll(objFields);
		
		boolean objContainsSrc = objMethods.containsAll(srcMethods)
				&& objFields.containsAll(srcFields);
		
		if(srcContainsObj && objContainsSrc) {
			return 0;
		} else if(srcContainsObj) {
			return 1;
		} else if(objContainsSrc) {
			return -1;
		}
		
		for(String method : objMethods) {
			if(srcMethods.contains(method)) {
				return 2;
			}
		}
		
		for(String field : objFields) {
			if(srcFields.contains(field)) {
				return 2;
			}
		}
		
		return 4;
		
//		no longer depends on version of jars
//		return versionCompare(jar.getVersion(), version);
	}

	
	@Override
	public String toString() {
		return "name: " + name + "\n" +
			   "path: " + path + "\n" +
		       "version: " + version + "\n" +
			   "md5: " + md5 + "\n" +
		       "modifyTime: " + modifyTime + "\n" +
			   "valid: " + valid;
	}

	@SuppressWarnings("unused")
	// TODO remove this method if not needed in the end
	private int versionCompare(String ver1, String ver2) {
		int res = Integer.MIN_VALUE;
		
		if (ver1.toLowerCase().startsWith("v")) {
			ver1 = ver1.substring(1);
		}

		if (ver2.toLowerCase().startsWith("v")) {
			ver2 = ver2.substring(1);
		}
		
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
		fullname = name;
		modifyTime = file.lastModified()/1000;  //seconds

		// suppose that version contains at least one "."
		Pattern pattern = Pattern.compile("(\\d+(\\.\\d+){1,})");
		Matcher m = pattern.matcher(name);
		if (m.find()) {
			version = m.group(1);
		} else {
			version = "unknown";
		}
		
		if(version.equals("unknown")) {   // deal with jar name like  xxx-v1.jar
			pattern = Pattern.compile("(v(\\d+){1,})");
			m = pattern.matcher(name);
			if (m.find()) {
				version = m.group(1);
			}
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
//			JarClassLoader loader = new JarClassLoader(new URL[]{});

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
			
//			loader.addJar(file.toURI().toURL());
			Enumeration<JarEntry> files = jar.entries();
			ClassParser cp;
			while (files.hasMoreElements()) {
				JarEntry entry = files.nextElement();
				
				if (entry.toString().endsWith(".class")) {
//					System.out.println(entry);
					String clazzName = entry.getName().replaceAll("/", ".")
							.replace(".class", "");
					cp = new ClassParser(file.getAbsolutePath(), entry.getName());
					clazzes.put(clazzName, new Clazz(cp.parse()));
				}
			}
			
			jar.close();
//			loader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Clazz> getClazzes() {
		return clazzes;
	}

	public String getFullname() {
		return fullname;
	}

//	public static void main(String[] args) {
//		//dexlib2-2.0.3-dev.jar
//		//jsoup-1.8.1.jar
//		//java_cup.jar
//		//org.hamcrest.core_1.3.0.jar
//		Jar jar = new Jar("F:/Temp/mudam-lib-v2.jar");
////		System.out.println(jar.toString());
//		
//		for(Clazz clazz : jar.getClazzes()) {
//			System.out.println(clazz.getName());
//			
//			for(Method m : clazz.getMethods()) {
//				System.out.println(m.getDeclaringClass()+"."+m.getName());
//				System.out.println(m);
//			}
//			
//			for(Field field : clazz.getFields()) {
//				System.out.println(field);
//			}
//		}
//	}
}
