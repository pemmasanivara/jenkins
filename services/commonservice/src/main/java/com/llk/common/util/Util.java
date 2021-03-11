package com.llk.common.util;

import java.util.Random;

public class Util {
 public static String getImageName(String name) {
	 Random rd=new Random();	
	 return rd.nextInt(1000000000)+"_"+name;
 }
}
