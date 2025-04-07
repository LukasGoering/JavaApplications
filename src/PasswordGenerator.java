import java.security.SecureRandom;		// Generation of random numbers
import java.util.Scanner;				// Read user input from console

public class PasswordGenerator {

	public static void main(String[] args) {
		
		// Initialize a scanner object for user input
		Scanner scanner = new Scanner(System.in);
		
		// Ask user for the desired length of the password
		System.out.println("Please enter the desired password length:");
		int pwLength = scanner.nextInt();
		
		// User decides which Characters are to be used
		String chars = CharacterSetSelection();
		
		// Generate the password using the method "generatePassword"
		String password = generatePassword(pwLength, chars);
		
		// Print out password
		System.out.println(password);
		
		// Close the scanner
		scanner.close();
	}
	
	
	private static String CharacterSetSelection() {
		
		// Initialize a scanner object for user input
		Scanner scanner = new Scanner(System.in);
		
		// Initialize the character sets
		final String[] characterSets = {
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ", // Uppercase letters
			"abcdefghijklmnopqrstuvwxyz", // Lowercase letters
			"0123456789", // Digits
			"-", // Minus
			"_", // Underline
			" ", // Blank
			"!\"§$%&/=?#'´`+*~|°^", // Special Characters
			"()[]{}<>" // Brackets
		};
		
		String Ask = "Do you want to use ";		// Just for abbreviation
		
		final String[] questions = {
			Ask + "upper case letters? (Yes/No)",
			Ask + "lower case letters? (Yes/No)",
			Ask + "Digits? (Yes/No)",
			Ask + "minus? (Yes/No)",
			Ask + "underline? (Yes/No)",
			Ask + "blank? (Yes/No)",
			Ask + "special characters? (Yes/No)",
			Ask + "brackets? (Yes/No)"
		};
		
		// Initialize allowed Characters
		StringBuilder Characters = new StringBuilder();
		
		// Ask the user for each set if it should be used
		for (int i = 0; i < characterSets.length; i++) {
			System.out.println(questions[i]);
			String answer = scanner.nextLine().toLowerCase();
			if (answer.contains("y")) {
				Characters.append(characterSets[i]);
			}
		}
		
		// Close the scanner
		scanner.close();
		
		// Return the allowed characters as string output
		return Characters.toString();
	}
	
	
	private static String generatePassword(int length, String Characters) {
		
		// Initialize an object of type "SecureRandom"
		SecureRandom secureRandom = new SecureRandom();
		
		// Initialize a string using StringBuilder
		// This will be the password later
		StringBuilder pw = new StringBuilder();
		
		// Number of allowed characters
		int numChars = Characters.length();
		
		for (int i = 0; i < length; i++) {
			int randomIndex = secureRandom.nextInt(numChars);	// Creates random index of a character
			pw.append(Characters.charAt(randomIndex));	// Appends the character associated to the random index to the password
		}
		
		// Convert the password to a string
		String password = pw.toString();
		
		// Return the password as output
		return password;
	}
}