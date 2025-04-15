import java.security.SecureRandom;		// Generation of random numbers
import javax.swing.*;					// GUI
import java.awt.*;						// Abstract Window Toolkit (e.g. GridLayout)
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;				// For ArrayLists ??
import java.util.List;					// For ArrayLists ??

public class PasswordGenerator {

	public static void main(String[] args) {
		
		// Create GUI on the Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater(() -> buildGUI());
	}
	
	
	public static void buildGUI() {
		applyDarkTheme();

		// Create the main window
		JFrame frame = new JFrame("Passworffcd Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		// Main panel with vertical layout
	    JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
		
	    
		// Input: Password length
	    JPanel pwLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pwLengthPanel.add(new JLabel("Password length:"));
		JTextField pwLengthField = new JTextField(5);
		pwLengthPanel.add(pwLengthField);
		mainPanel.add(pwLengthPanel);
		
		
		// Checkboxes for character sets
		// ATTENTION: Order must match the order of characterSets
		JPanel checkBoxPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Character Sets"));
		
		JCheckBox checkBox1 = new JCheckBox("Upper case letters");
		JCheckBox checkBox2 = new JCheckBox("Lower case letters");
		JCheckBox checkBox3 = new JCheckBox("Digits");
		JCheckBox checkBox4 = new JCheckBox("Minus");
		JCheckBox checkBox5 = new JCheckBox("Underline");
		JCheckBox checkBox6 = new JCheckBox("Blank");
		JCheckBox checkBox7 = new JCheckBox("Special Characters");
		JCheckBox checkBox8 = new JCheckBox("Brackets");
		
		// Create submit button
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton submitButton = new JButton("Generate Password");
	    
	    // Create a "Copy to clipboard"-button
	    JButton copyButton = new JButton("Copy Password");
	    copyButton.setEnabled(false);	// Disabled at start
		
		// Create password box
	    JTextArea passwordBox = new JTextArea(2, 30);
	    passwordBox.setEditable(false);
	    passwordBox.setLineWrap(true);
	    passwordBox.setWrapStyleWord(true);
	    passwordBox.setBorder(BorderFactory.createTitledBorder("Generated Password"));
	    JScrollPane scrollPanel = new JScrollPane(passwordBox);
	    

		// Add components to Panel resp. to frame
		checkBoxPanel.add(checkBox1);
	    checkBoxPanel.add(checkBox2);
	    checkBoxPanel.add(checkBox3);
	    checkBoxPanel.add(checkBox4);
	    checkBoxPanel.add(checkBox5);
	    checkBoxPanel.add(checkBox6);
	    checkBoxPanel.add(checkBox7);
	    checkBoxPanel.add(checkBox8);
	    mainPanel.add(checkBoxPanel);
	    mainPanel.add(scrollPanel);
	    buttonPanel.add(submitButton);
	    buttonPanel.add(copyButton);
	    mainPanel.add(buttonPanel);
	    frame.add(mainPanel); // Add main panel to frame
	    
	    
		// Configure submit-button action
		submitButton.addActionListener(_ -> {
			// Obtain desired number of characters
			String input = pwLengthField.getText().trim();
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
			
			// Enable copy button
			copyButton.setEnabled(true);
		});
		
		
		// Implement "copy to clipboard" button
		copyButton.addActionListener(_ -> {
		    String pw = passwordBox.getText();
		    if (pw.isEmpty()) {
		        JOptionPane.showMessageDialog(frame, "No password to copy.");
		    } else {
		        Toolkit.getDefaultToolkit()
		            .getSystemClipboard()
		            .setContents(new StringSelection(pw), null);
		        JOptionPane.showMessageDialog(frame, "Password copied to clipboard!");
		    }
		});
		
		// Make the GUI visible
		frame.setVisible(true);
	}
	
	
	private static void applyDarkTheme() {
	    UIManager.put("Panel.background", new Color(45, 45, 45));
	    UIManager.put("Label.foreground", Color.WHITE);
	    UIManager.put("TextField.background", new Color(60, 60, 60));
	    UIManager.put("TextField.foreground", Color.WHITE);
	    UIManager.put("TextField.caretForeground", Color.WHITE);
	    UIManager.put("TextField.border", BorderFactory.createLineBorder(new Color(100, 100, 100)));
	    UIManager.put("TextArea.background", new Color(60, 60, 60));
	    UIManager.put("TextArea.foreground", Color.WHITE);
	    UIManager.put("TextArea.caretForeground", Color.WHITE);
	    UIManager.put("Button.background", new Color(70, 70, 70));
	    UIManager.put("Button.foreground", Color.WHITE);
	    UIManager.put("CheckBox.background", new Color(45, 45, 45));
	    UIManager.put("CheckBox.foreground", Color.WHITE);
	    UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);
	    UIManager.put("ScrollPane.background", new Color(45, 45, 45));
	    UIManager.put("OptionPane.messageForeground", Color.WHITE);
	    UIManager.put("OptionPane.background", new Color(45, 45, 45));
	    UIManager.put("OptionPane.messageBackground", new Color(45, 45, 45));
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