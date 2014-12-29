package org.yellowbook;

import org.luxyellowbook.LuxYellowBook;

public class YellowBook 
{
	public static void main(String[] args)
	{
		LuxYellowBook lyb = new LuxYellowBook();
		System.out.println(lyb.getAddress("Mudam"));
		System.out.println(lyb.getAddress("Philharmonie"));
	}
}
