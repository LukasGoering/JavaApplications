import java.security.SecureRandom;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		// Initialize a scanner object for user input
		Scanner scanner = new Scanner(System.in);
		
		// Ask for the desired length of the password
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
		final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String abc = "abcdefghijklmnopqrstuvwxyz";
		final String digits = "0123456789";
		final String specialChars = "-_ .,:;!\"§$%&/()=?";
		
		// Initialize allowed Characters
		StringBuilder Characters = new StringBuilder();
		
		// Asking for allowed character sets
		System.out.println("Do you want to use upper case letters? (Yes/No)");
		String useUppercase = scanner.nextLine().toLowerCase();
		if (useUppercase.contains("y")) {
			Characters.append(ABC);
		}
		
		System.out.println("Do you want to use lower case letters? (Yes/No)");
		String useLowercase = scanner.nextLine().toLowerCase();
		if (useLowercase.contains("y")) {
			Characters.append(abc);
		}
		
		System.out.println("Do you want to use digits? (Yes/No)");
		String useDigits = scanner.nextLine().toLowerCase();
		if (useDigits.contains("y")) {
			Characters.append(digits);
		}
		
		System.out.println("Do you want to use digits? (Yes/No)");
		String useSpecialchars = scanner.nextLine().toLowerCase();
		if (useSpecialchars.contains("y")) {
			Characters.append(specialChars);
		}
		
		
		// Convert Characters to a string
		String AllowedChars = Characters.toString();
		
		// Close the scanner
		scanner.close();

		// Return the allowed characters as output
		return AllowedChars;
	}
	
	
	private static String generatePassword(int length, String Characters) {
		
		// Initialize an object of type "SecureRandom"
		SecureRandom secureRandom = new SecureRandom();
				
		// Create a random integer in [0; 9]
		int randomInt = secureRandom.nextInt(10);
		
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
