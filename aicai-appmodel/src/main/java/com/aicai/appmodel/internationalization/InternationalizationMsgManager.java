package com.aicai.appmodel.internationalization;

import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternationalizationMsgManager {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public static final String baseName = "/i18n/message";

	public static String getMessage(String errCode, Locale requestLocal) {
		return ResourceBundle.getBundle(baseName, requestLocal).getString(errCode);
	}

}
