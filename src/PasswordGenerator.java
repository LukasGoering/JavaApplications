import java.security.SecureRandom;		// Generation of random numbers
import javax.swing.*;					// GUI
import java.awt.*;						// Abstract Window Toolkit (e.g. GridLayout)
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {

	public static void main(String[] args) {
		
		// Create GUI on the Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater(() -> buildGUI());
	}
	
	
	public static void buildGUI() {
		
		// Optional: Use Dark Theme for GUI
		applyDarkTheme();
		

		// Create the main window
		JFrame frame = new JFrame("Password Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
	    
	    
		// Build GUI
		GUIComponents gui = buildMainPanel();			// Main panel built in separate method
		frame.add(gui.mainPanel);						// Add main panel to frame
		SwingUtilities.updateComponentTreeUI(frame);	// Update necessary for dark theme
		frame.setVisible(true);							// Show the GUI to the user
		
		
		// Configure actions of the buttons
		configureActions(frame, gui);
		
	}
	
	
	// Helper class to store the GUI components bundled
	private static class GUIComponents {
	    JPanel mainPanel;
	    JTextField pwLengthField;
	    JTextArea passwordBox;
	    JButton generateButton;
	    JButton copyButton;
	    JCheckBox[] checkBoxes;		// Array of JCheckBox components to hold all checkboxes
	}
	
	
	// Build the main panel of the GUI
	private static GUIComponents buildMainPanel() {
		GUIComponents gui = new GUIComponents();
		
		// Main panel with vertical layout
	    gui.mainPanel = new JPanel();
	    gui.mainPanel.setLayout(new BoxLayout(gui.mainPanel, BoxLayout.Y_AXIS));
	    gui.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
		
	    // Input: Password length
	    JPanel pwLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pwLengthPanel.add(new JLabel("Password length:"));
		gui.pwLengthField = new JTextField(5);
		pwLengthPanel.add(gui.pwLengthField);
		gui.mainPanel.add(pwLengthPanel);
		
		// Checkboxes for character sets
		// ATTENTION: Order must match the order of characterSets
		String[] checkboxLabels = {
		        "Upper case letters", "Lower case letters", "Digits", "Minus",
		        "Underline", "Blank", "Special Characters", "Brackets"
		    };
		
		JPanel checkBoxPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Character Sets"));
		
		gui.checkBoxes = new JCheckBox[checkboxLabels.length];
		for (int i = 0; i < checkboxLabels.length; i++) {
			gui.checkBoxes[i] = new JCheckBox(checkboxLabels[i]);
			checkBoxPanel.add(gui.checkBoxes[i]);
		}
		gui.mainPanel.add(checkBoxPanel);
		
		// Create button panel
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    // Create generate button and "copy to clipboard" button
	    gui.generateButton = new JButton("Generate Password");
	    gui.copyButton = new JButton("Copy Password");
	    gui.copyButton.setEnabled(false);	// Disabled at start
	    // Add buttons to button panel and button panel to main panel
	    buttonPanel.add(gui.generateButton);
	    buttonPanel.add(gui.copyButton);
	    gui.mainPanel.add(buttonPanel);
		
		// Create password box
	    gui.passwordBox = new JTextArea(2, 30);
	    gui.passwordBox.setEditable(false);			// User can't type into password box
	    gui.passwordBox.setLineWrap(true);			// Long passwords are displayed in multiple lines
	    gui.passwordBox.setBorder(BorderFactory.createTitledBorder("Generated Password"));
	    JScrollPane scrollPanel = new JScrollPane(gui.passwordBox);
	    gui.mainPanel.add(scrollPanel);
		
	    
		return gui;
		
	}

	
	// Password is generated from an arbitrary non-empty String with custom length
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
	
	
	// The character set for the password is created from the user input
	private static String createCharacterSet(List<Boolean> response) {
		// Define the character sets
		// ATTENTION: Order must match with the order of the string "checkboxLabels"
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
	
	
	// The actions of generate button and copy button are configured here
	// Uses the methods generatePassword and createCharacterSet
	private static void configureActions(JFrame frame, GUIComponents gui) {
		
		// Configure action of generate button
		gui.generateButton.addActionListener(_ -> {
			
			// Obtain desired number of characters
			String input = gui.pwLengthField.getText().trim();
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
			
			
			// Initialize an ArrayList containing the answers from user
			List<Boolean> boolResponse = new ArrayList<>(); // New every click
			
			// For every checkbox, the boolean value is added to boolVector
			for (JCheckBox cb : gui.checkBoxes) {
				boolResponse.add(cb.isSelected());
			}
			
			// If no character set is selected, stop and ask to select at least one	
		    if (!boolResponse.contains(true)) {
		        JOptionPane.showMessageDialog(frame, "Please select at least one character set.");
		        return;
		    }
			
			// Create the allowed characterset
			String pwCharacters = createCharacterSet(boolResponse);
			
			// Generate the password
			String pw = generatePassword(pwLength, pwCharacters);
			
			// Display the password
			gui.passwordBox.setText(pw);
			
			// Enable copy button
			gui.copyButton.setEnabled(true);
		});
		
		
		// Configure action of "copy to clipboard" button
		gui.copyButton.addActionListener(_ -> {
		    String pw = gui.passwordBox.getText();
		    if (pw.isEmpty()) {
		        JOptionPane.showMessageDialog(frame, "No password to copy.");
		    } else {
		        Toolkit.getDefaultToolkit()
		            .getSystemClipboard()
		            .setContents(new StringSelection(pw), null);
		        JOptionPane.showMessageDialog(frame, "Password copied to clipboard!");
		    }
		});
	}
	
	
	// Configurations for Dark Theme
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
	
}