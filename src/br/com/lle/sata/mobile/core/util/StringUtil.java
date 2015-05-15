package br.com.lle.sata.mobile.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String removeExcessoEspacos(String str) {
		String padrao = "\\s{2,}";
	    Pattern regPat = Pattern.compile(padrao);
	    Matcher matcher = regPat.matcher(str);
	    String res = matcher.replaceAll(" ").trim();
	    return res;
	}
}
