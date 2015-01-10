package tests;

import java.io.File;

import com.turbo.build.util.Md5Util;

public class TestMd5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Md5Util.getMd5ByFile(new File(
							"E:/Study/Github/turbo-build/test-cases/case2/App/libs/coffi.jar")));
		System.out.println(Md5Util.getMd5ByFile(new File("E:/Temp/coffi.jar")));
	}

}
