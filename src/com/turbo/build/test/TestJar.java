package com.turbo.build.test;

import com.turbo.build.util.Jar;

public class TestJar {

	public static void main(String[] args) {
		Jar jar1 = new Jar("E:/Temp/calc-v1.jar");
		Jar jar2 = new Jar("E:/Temp/calc-v2.jar");
		Jar jar3 = new Jar("E:/Temp/coffi.jar");
		
		System.out.println(jar1.compareTo(jar2));
		System.out.println(jar2.compareTo(jar2));
		System.out.println(jar2.compareTo(jar3));
	}

}
