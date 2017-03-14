

public class ArraysAndStrings {
	
	
	public Boolean isUnique(String value) {
		
		/*
		 * Implement an algorithm to determine if a string has all unique characters.
		 * What if you cannot use additional data structures?
		 * => Only use Array
		 */
		
		/*
		 * 1. 	Loop through each character of the string, comparing it with the rest of string, 
		 * 		if there are equal character then return false, otherwise return true
		 * 		=> O(n*n)
		 * 2.	Loop through each character, save that character as a key and value as 1
		 * 		Before we save, we check if that key exists, if it exists then return false
		 * 		After the loop, return true
		 * 		=> O(n)
		 */
		
		int length = value.length();
		int[] characterCount = new int[256];
		for (int i=0; i<length; i++) {
			char currentCharacter = value.charAt(i);
			
			if (characterCount[currentCharacter] != 0) {
				return false;
			}
			else {
				characterCount[currentCharacter] = 1;
			}
		}
		
		return true;
		
	}
	
	public Boolean isPermutation(String str1, String str2) {
		/*
		 * Given 2 strings, write a method to decide if one is a permutation of other
		 */
		
		/*
		 * First calculate length of both strings, if they are not equal then return false
		 * Calculate the times each character appear on string 1
		 * Loop through each character in string 2, with each character, substract 1 in the character count array
		 * If value of the subtraction less than 0, then return false
		 * At the end of that loop, return true
		 */
		int length1 = str1.length(), length2 = str2.length();
		if (length1 != length2) {
			return false;
		}
		
		int[] characterCountString1 = new int[256];
		for (int i=0; i<length1; i++) {
			char currentCharacter = str1.charAt(i);
			characterCountString1[currentCharacter] += 1;
		}
		
		for (int i=0; i<length1; i++) {
			char currentCharacter = str2.charAt(i);
			characterCountString1[currentCharacter] -= 1;
			if (characterCountString1[currentCharacter] < 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(ArraysAndStrings.isUnique("abxggrsqrwfegr"));
		System.out.println(ArraysAndStrings.isUnique("abcdefghijk"));
	}
}
