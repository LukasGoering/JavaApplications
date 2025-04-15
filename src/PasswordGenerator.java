import java.security.SecureRandom;		// Generation of random numbers
import javax.swing.*;					// GUI
import java.awt.*;						// Abstract Window Toolkit (e.g. GridLayout)
import java.util.ArrayList;				// For ArrayLists ??
import java.util.List;					// For ArrayLists ??

public class PasswordGenerator {

	public static void main(String[] args) {
		
		// Create GUI on the Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater(() -> buildGUI());
	}
	
	
	public static void buildGUI() {

		// Create the main window
		JFrame frame = new JFrame("Passworffcd Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLayout(new GridLayout(5, 1)); // 5 rows, 1 column
		
		// Create number-field for length of password
		JTextField numberField = new JTextField();
		
		// Checkboxes for character sets
		// ATTENTION: Order must match the order of characterSets
		JCheckBox checkBox1 = new JCheckBox("Upper case letters");
		JCheckBox checkBox2 = new JCheckBox("Lower case letters");
		JCheckBox checkBox3 = new JCheckBox("Digits");
		JCheckBox checkBox4 = new JCheckBox("Minus");
		JCheckBox checkBox5 = new JCheckBox("Underline");
		JCheckBox checkBox6 = new JCheckBox("Blank");
		JCheckBox checkBox7 = new JCheckBox("Special Characters");
		JCheckBox checkBox8 = new JCheckBox("Brackets");
		
		// Create submit button
		JButton submitButton = new JButton("Generate Password");
		
		// Create output field
		JTextArea passwordBox = new JTextArea();
		passwordBox.setEditable(false); // The user can't type into it
		passwordBox.setLineWrap(true);	// Allows multiple lines
		passwordBox.setBorder(BorderFactory.createTitledBorder("Generated Password"));	// Creates frame with title
		passwordBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        // Add components to the frame
		frame.add(new JLabel("Password length"));
		frame.add(numberField);	
		frame.add(checkBox1);
		frame.add(checkBox2);
		frame.add(checkBox3);
		frame.add(checkBox4);
		frame.add(checkBox5);
		frame.add(checkBox6);
		frame.add(checkBox7);
		frame.add(checkBox8);
		frame.add(passwordBox);
		frame.add(submitButton);
		
		// Configure button action
		submitButton.addActionListener(e -> {
			// Obtain desired number of characters
			String input = numberField.getText().trim();
			int pwLength;
			
			try {
			    pwLength = Integer.parseInt(input);
			    if (pwLength <= 0) {
			        JOptionPane.showMessageDialog(frame, "Please enter a positive number.");
			        return;
			    }
			} catch (NumberFormatException ex) {
			    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
			    return;
			}
			
			
			// Initialize boolVector containing the answers from user
			List<Boolean> boolVector = new ArrayList<>(); // New every click
			
			// For every checkbox, the boolean value is added to boolVector
			boolVector.add(checkBox1.isSelected());
			boolVector.add(checkBox2.isSelected());
			boolVector.add(checkBox3.isSelected());
			boolVector.add(checkBox4.isSelected());
			boolVector.add(checkBox5.isSelected());
			boolVector.add(checkBox6.isSelected());
			boolVector.add(checkBox7.isSelected());
			boolVector.add(checkBox8.isSelected());
			
		    if (!boolVector.contains(true)) {
		        JOptionPane.showMessageDialog(frame, "Please select at least one character set.");
		        return;
		    }
			
			// Create the allowed characterset
			String pwCharacters = createCharacterSet(boolVector);
			
			// Generate the password
			String pw = generatePassword(pwLength, pwCharacters);
			
			// Display the password
			passwordBox.setText(pw);
		});
		
		// Make the GUI visible
		frame.setVisible(true);
	}
	
	
	private static String createCharacterSet(List<Boolean> response) {
		// Define the character sets
		// ATTENTION: Order must match with the checkboxes in the GUI!
		final String[] characterSets = {
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",	// Uppercase letters
			"abcdefghijklmnopqrstuvwxyz",	// Lowercase letters
			"0123456789", 					// Digits
			"-",							// Minus
			"_",							// Underline
			" ",							// Blank
			"!\"§$%&/=?#'´`+*~|°^",			// Special Characters
			"()[]{}<>"						// Brackets
		};
		
		// Create empty stringbuilder with allowed characters
        StringBuilder chars = new StringBuilder();
		
        // For each checked Checkbox, the corresponding characterset is added
		for (int i = 0; i < response.size(); i++) {
			if (Boolean.TRUE.equals(response.get(i))) {		// NullPointerException
				chars.append(characterSets[i]);
			}
		}
		
		// Convert stringbuilder to string
		String pwCharacters = chars.toString();
		
		return pwCharacters;
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