package org.mudam;

public class Mudam 
{
	public String getAddress(boolean withPhoneNo)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("3 Park Drai Eechelen, 1499 Luxembourg City, Luxembourg");
		
		if (withPhoneNo)
		{
			sb.append("\n" + "+352-453785-1");
		}
		
		return sb.toString();
	}
}