package org.yellowbook;

import org.europeyellowbook.EuropeYellowBook;
import org.luxembourgyellowbook.LuxembourgYellowBook;

public class YellowBook 
{
	public static void main(String[] args)
	{
		LuxembourgYellowBook lyb = new LuxembourgYellowBook();
		System.out.println(lyb.getAddress("Mudam"));
		
		EuropeYellowBook eyb = new EuropeYellowBook();
		System.out.println(eyb.getMudamAddress());
	}
}
