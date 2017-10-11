package com.sp.interactive.interf;

import java.util.Random;

/**
 * Éú³ÉËæ»úsessionID
 * @author qilin
 * */

public class RandomString {
	public String getRandomString(int length)
	{
	String str="abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
	Random random=new Random();
	StringBuffer sf=new StringBuffer();
	for(int i=0;i<length;i++)
	{
	 int number=random.nextInt(62);
	 sf.append(str.charAt(number));
	 

	}
	return sf.toString();
	}


}
