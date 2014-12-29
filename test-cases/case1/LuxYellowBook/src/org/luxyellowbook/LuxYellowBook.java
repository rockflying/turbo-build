package org.luxyellowbook;
import org.mudam.Mudam;


public class LuxYellowBook 
{
	public static void main(String[] args)
	{
		LuxYellowBook lyb = new LuxYellowBook();
		System.out.println(lyb.getAddress("Mudam"));
		System.out.println(lyb.getAddress("Philharmonie"));
	}
	
	public String getAddress(String name)
	{
		String rtStr = "Not found.";
		
		if (name.equals("Mudam"))
		{
			Mudam m = new Mudam();
			rtStr = m.getAddress();
		}
		else if (name.equals("Philharmonie"))
		{
			rtStr = "1, place de l'Europe, 1499 Luxembourg City, Luxembourg" + "\n" + "+352-260227-1";
		}
		
		return rtStr;
	}
}
