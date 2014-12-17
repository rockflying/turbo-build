package com.turbo.build.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class Md5Util {

	public static String getMd5ByFile(File file) {
		String value = null;		
		
		try {
			FileInputStream in = new FileInputStream(file);
			
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			
			md5.update(byteBuffer);
			
			BigInteger bi = new BigInteger(1, md5.digest());
			
			value = bi.toString(16);
			
			in.close();
		} catch (Exception e) {
			
			e.printStackTrace();			
		}
		
		return value;
	}
}