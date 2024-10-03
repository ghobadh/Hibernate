package ca.gforcesoftware.util;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class NumberProcess {
	
	public static Predicate<String> isZeroLead = x -> {
		char[] y = x.toCharArray();
		if (y[0] =='0')
			return true;
		else
			return false;
	};
	
	public static Function<String,String> removeLastChar = (x) -> {
		char[] z = x.toCharArray();
		if (Character.isDigit(z[z.length-1]) ){
			return x;
		}else {
			return x.substring(0, z.length-1);
		}
	};
	
	public static BiFunction<String, Integer, String> collectSubString = (x,y) -> {
		x = x.replace(".","");
		if(x.charAt(0)=='0' && x.length() > y)// && x.charAt(1)=='0') && !x.contains("000."))
		{
			x = x.replaceFirst("0", "");
		}
		if (x.length() > y) {
			return x.substring(0,y);
		
		}else if (x.length() == y) {
			if (removeLastChar.apply(x.substring(0,y)).length() == y-1) {
				return removeLastChar.apply(x.substring(0,y))+"0";
				
			} else {
				return x.substring(0,y);
			}
		}else {

			return x;
			
		}
	};

}
