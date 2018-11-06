package com.aicai.dao.util;

public class HasLoadedAttachUtil {
	public static boolean hasAttachLoaded(long attachFlag, long bitForPart){
		return (attachFlag & bitForPart) > 0;
	}
}
