package com.example.xiaoyuantong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

public class TextUtil {
	// private KXApplication mKxApplication;
	//
	// public TextUtil(KXApplication application) {
	// mKxApplication = application;
	// }

	/**
	 * 根据输入流转换成字符�?
	 * 
	 * @param inputStream
	 *            文字输入�?
	 * @return 文字字符�?String 类型)
	 */
	public String readTextFile(InputStream inputStream) {
		String readedStr = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				readedStr += tmp;
			}
			br.close();
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return readedStr;
	}

	/**
	 * 表情的正则表达式
	 * 
	 * @return
	 */
	private Pattern buildPattern() {
		return null;
		// StringBuilder patternString = new StringBuilder(
		// mKxApplication.mFacesText.size() * 3);
		// patternString.append('(');
		// for (int i = 0; i < mKxApplication.mFacesText.size(); i++) {
		// String s = mKxApplication.mFacesText.get(i);
		// patternString.append(Pattern.quote(s));
		// patternString.append('|');
		// }
		// patternString.replace(patternString.length() - 1,
		// patternString.length(), ")");
		// return Pattern.compile(patternString.toString());
	}

	/**
	 * 将文本中含有表情字符的内容换成带有表情图片的文本
	 * 
	 * @param text
	 *            带有表情字符的文�?
	 * @return 带有表情图片的文�?
	 */
	public CharSequence replace(CharSequence text) {
		return text;
		// try {
		// SpannableStringBuilder builder = new SpannableStringBuilder(text);
		// Pattern pattern = buildPattern();
		// Matcher matcher = pattern.matcher(text);
		// while (matcher.find()) {
		// Bitmap bitmap = mKxApplication
		// .getFaceBitmap(mKxApplication.mFacesText
		// .indexOf(matcher.group()));
		// if (bitmap != null) {
		// ImageSpan span = new ImageSpan(bitmap);
		// builder.setSpan(span, matcher.start(), matcher.end(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// }
		// }
		// return builder;
		// } catch (Exception e) {
		// return text;
		// }
	}

	public String getCharacterPinYin(char c) {
		return null;
		// HanyuPinyinOutputFormat format = null;
		// format = new HanyuPinyinOutputFormat();
		// format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// String[] pinyin = null;
		// try {
		// pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
		// } catch (BadHanyuPinyinOutputFormatCombination e) {
		// e.printStackTrace();
		// }
		// // 如果c不是汉字，toHanyuPinyinStringArray会返回null
		// if (pinyin == null)
		// return null;
		// // 只取�?��发音，如果是多音字，仅取第一个发�?
		// return pinyin[0];
	}

	/**
	 * 获取字符的拼�?
	 * 
	 * @param str
	 * @return
	 */
	public String getStringPinYin(String str) {
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i) {
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin == null) {
				// 如果str.charAt(i)非汉字，则保持原�?
				sb.append(str.charAt(i));
			} else {
				sb.append(tempPinyin);
			}
		}
		return sb.toString();
	}
}
