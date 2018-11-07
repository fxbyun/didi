package com.aicai.appmodel.internationalization;

import java.util.Locale;

public class RequestLocaleThreadLocalHolder {
	
	private static final ThreadLocal<Locale>  acceptLanguageLocaleHolder = new ThreadLocal<Locale>();
	
	public static void resetAcceptLanguageLocale(){
		acceptLanguageLocaleHolder.remove();
	}
	
	public static void setAcceptLanguageLocale(Locale locale){
		acceptLanguageLocaleHolder.set(locale);
	}
	
	public static Locale getAcceptLanguateLocale(){
		return acceptLanguageLocaleHolder.get();
	}

}
