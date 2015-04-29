package utility;

import java.util.ArrayList;

/**
 * This class mainly check a string and put an escape character
 * in front of a double quote symbol to avoid JSON parser error
 * @author Thai Kha Le
 *
 */
public class StringNeutraliser {

	public static String wipeQuotes(String text)
	{
		String refined = "";
		for(char character: text.toCharArray()) {
			if(character == 34) {
				refined += "\\" + character;
			}
			else {
				refined += character;
			}
		}
		return refined;
	}
	
	public static void main(String[] agrs)
	{
		
	}
}
