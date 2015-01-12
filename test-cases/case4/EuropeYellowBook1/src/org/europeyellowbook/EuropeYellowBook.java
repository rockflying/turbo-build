package org.europeyellowbook;
import org.mudam.Mudam;


public class EuropeYellowBook 
{
	public static void main(String[] args)
	{
		EuropeYellowBook eyb = new EuropeYellowBook();
		System.out.println(eyb.getMudamAddress());
	}
	
	public String getMudamAddress()
	{ 
		Mudam m = new Mudam();
		String rtStr = m.getAddress(true);
		
		return rtStr;
	}
}
