package org.luxembourgyellowbook;
import org.mudam.Mudam;


public class LuxembourgYellowBook 
{
	public static void main(String[] args)
	{
		LuxembourgYellowBook lyb = new LuxembourgYellowBook();
		System.out.println(lyb.getAddress("Mudam"));
	}
	
	public String getAddress(String name)
	{
		String rtStr = "Not found.";
		
		if (name.equals("Mudam"))
		{
			Mudam m = new Mudam();
			rtStr = m.getAddress();
		}
		
		return rtStr;
	}
}
