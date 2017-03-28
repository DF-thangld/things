package puzzles;

import java.util.ArrayList;
import java.util.Random;

public class Utils {
		// remove all duplicate characters in a given string
		public static String removeDuplicateCharacterInString(String input) {
			char[] chars = input.toCharArray();
			StringBuilder result = new StringBuilder();
			ArrayList<Character> tmp = new ArrayList<Character>();
			for (char character: chars) {
				if (!tmp.contains(character)) {
					tmp.add(character);
				}
			}
			
			for (Character character: tmp) {
				result.append(character);
			}
			return result.toString();
		}
		
		public static String generateRandomString(int length) {
			StringBuilder result = new StringBuilder();
			String seedCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
			Random random = new Random();
			
			for (int i=0; i<length; i++) {
				result.append(seedCharacters.charAt(random.nextInt(seedCharacters.length())));
			}
			return result.toString();
		}
}
