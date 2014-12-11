package com.buildpath.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jar {
	String name;
	String path;
	String version;
	String md5;
	long modifyTime;
	boolean absolute;
	List<String> clazzes = new ArrayList<String>();
	
	int order; //the order in the build path
	
	boolean valid = false;  //to denote whether the jar is valid
	
	public Jar(String path) {
		this.path  = path;
		initJar();
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

	public int compareTo(Jar jar) {
		return versionCompare(version, jar.getVersion());
	}
	
	private int versionCompare(String ver1, String ver2) {
		int res = Integer.MIN_VALUE;
		String[] v1 = ver1.split("\\.");
		String[] v2 = ver2.split("\\.");
		
		for (int i = 0, j = 0; i < v1.length && j < v2.length; ++i, ++j) {
			res = Integer.parseInt(v1[i])-Integer.parseInt(v2[j]);
			if(res != 0) {
				return res;
			}
		}

		return v1.length - v2.length; //in case the length of version is different
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

		// suppose that version contains at least one "."
		Pattern pattern = Pattern.compile("(\\d+(\\.\\d+){1,})");
		Matcher m = pattern.matcher(name);
		if (m.find()) {
			version = m.group(1);
		} else {
			version = "unkown";
		}
		parseJar();
	}
	
	private void parseJar() {
		
	}
	public static void main(String[] args) {
		System.out.println(new Jar("").versionCompare("4.1.1", "4.30.15"));
	}
}
