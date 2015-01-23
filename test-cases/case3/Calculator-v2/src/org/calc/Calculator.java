package org.calc;

public class Calculator {
	private int a;
	private int b;
	
	String calc = "Calculator";
	
	public int getA() {
		return a;
	}
	
	public int getB() {
		return b;
	}
	
	public int add(int x, int y) {
		return x + y;
	}
	
	public int sub(int x, int y) {
		return x - y;
	}
	
	public int mul(int x, int y) {
		return x * y;
	}
	
	public int mul(int x, int y, int z) {
		return x * y + z;
	}

}
