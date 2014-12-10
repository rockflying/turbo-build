package com.buildpath.util;

public class Jar {
	String version;
	String md5;
	
	
	public int compareTo(Jar jar) {
		return versionCompare(version, jar.version);
	}
	
	private int versionCompare(String ver1, String ver2) {
		int res = Integer.MIN_VALUE;
		String[] v1 = ver1.split("\\.");
		String[] v2 = ver2.split("\\.");
		
		for (int i = 0, j = 0; i < v1.length && j < v2.length; ++i, ++j) {
			System.out.println(v1[i]);
			System.out.println(v2[j]);
			res = v1[i].compareTo(v2[j]);
			if(res != 0) {
				return res;
			}
		}

		return res;
	}
	
	public static void main(String[] args) {
		new Jar().versionCompare("2.3.8", "2.3");
	}
}
