package com.education.system.common.util;

import com.sun.corba.se.impl.io.TypeMismatchException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 日期格式化
 */
@SuppressWarnings("restriction")
public class DateUtilEx extends PropertyEditorSupport {

	private static Logger LOGGER = Logger.getLogger(GlobalUtil.class);

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Date date = null;
		SimpleDateFormat sdf = null;

		try {
			if (Pattern.compile("([GMT]|[gmt])").matcher(text).find()) { // Wed Nov 21 2018 08:00:00 GMT+0800(中国标准时间)
				sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
				try {
					text = text.replace("GMT", "").replaceAll("\\(.*\\)", "");
					date = sdf.parse(text);
					setValue(date);
					return;
				} catch (ParseException e) {
					LOGGER.error(e, e);
				}
			}
			// 防止空数据出错
			if (StringUtils.isNotBlank(text)) {
				sdf = getSimpleDateFormat(text);
				date = sdf.parse(text);
			}
		} catch (ParseException e) {
			LOGGER.error(e, e);
		}
		setValue(date);
	}

	/**
	 * 使用正在表达式匹配正确的格式
	 */
	private SimpleDateFormat getSimpleDateFormat(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", source)) { // yyyy-MM-dd
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else if (Pattern.matches("^\\d{4}-\\d{2}$", source)) { // yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		} else if (Pattern.matches("^\\d{4}$", source)) { // yyyy
			sdf = new SimpleDateFormat("yyyy");
		} else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}$", source)) { // yyyy-MM-dd HH-mm-ss
			sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		} else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", source)) { // yyyy-MM-dd HH:mm:ss
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2}$", source)) { // yyyy/MM/dd
			sdf = new SimpleDateFormat("yyyy/MM/dd");
		} else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}/\\d{2}$", source)) { // yyyy/MM/dd HH/mm/ss
			sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
		} else if (Pattern.matches("^\\d{4}\\d{2}\\d{2}$", source)) { // yyyyMMdd
			sdf = new SimpleDateFormat("yyyyMMdd");
		} else if (Pattern.matches("^\\d{4}\\d{2}\\d{2} \\d{2}\\d{2}\\d{2}$", source)) { // yyyyMMdd HHmmss
			sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		} else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2}$", source)) { // yyyy.MM.dd
			sdf = new SimpleDateFormat("yyyy.MM.dd");
		} else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2} \\d{2}\\.\\d{2}\\.\\d{2}$", source)) { // yyyy.MM.dd HH.mm.ss
			sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
		} else {
			System.out.println("TypeMismatchException");
			throw new TypeMismatchException();
		}
		return sdf;
	}

}
