import java.util.Scanner;
import java.util.Random;
import java.io.*;

//Application Class
public class PasswordGenerator {

    // Hardcoded character pools
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";  

     // Main function
    public static void main(String[] args) {

        // Inpput Scanner creation
        Scanner scanner = new Scanner(System.in);

        // Console Connection
        Console cmd = System.console();
        if (cmd == null) {
             System.out.println("No Console Available.");
        }

        // While loop to allow for multiple password creations
        while (true) {
            cmd.flush();

            // Print header text
            System.out.println("\nWelcome to the Password Generator!");
            System.out.println("=================================");
            
            // Record desired length
            int length = -1;
            while(length < 4) {
                System.out.print("Enter desired password length (minimum 4): ");
                length = scanner.nextInt();
            }

            // Record if the user wants to include lowercase characters
            System.out.print("Include lowercase letters? (y/n): ");
            boolean includeLowercase = scanner.next().toLowerCase().startsWith("y");

            // Record if the user wants to include uppercase characters
            System.out.print("Include uppercase letters? (y/n): ");
            boolean includeUppercase = scanner.next().toLowerCase().startsWith("y");

            // Record if the user wants to include numbers
            System.out.print("Include numbers? (y/n): ");
            boolean includeNumbers = scanner.next().toLowerCase().startsWith("y");
            
            // Record if the user wants to include symbols
            System.out.print("Include symbols? (y/n): ");
            boolean includeSymbols= scanner.next().toLowerCase().startsWith("y");

            // Validate that the user has selected at least one character pool. If not include lowercase by default
            if(!includeLowercase && !includeUppercase && !includeNumbers && !includeSymbols) {
                System.out.println("You must select at least one character type!");
                System.out.println("Enabling lowercase letters by default.");
                includeLowercase = true;
            }

            // Create character pool
            String characterPool = buildCharacterPool(includeLowercase, includeUppercase, includeNumbers, includeSymbols);

            // Generate password
            String password = generatePassword(length, characterPool, includeLowercase, includeUppercase, includeNumbers, includeSymbols);

            // Present password and strength
            System.out.println("\nGenerated Password: " + password);
            System.out.println("Password Strength: " + getPasswordStrength(length, includeLowercase, includeUppercase, includeNumbers, includeSymbols));

            // Check if the user would like to generate another password
            System.out.print("Would you like to generate another password? (y/n): ");
            boolean done = scanner.next().toLowerCase().startsWith("n");

            if (done) break;
        }

        scanner.close();
    }

    // Function for building the character pool and returning it for the password generation
    private static String buildCharacterPool(boolean includeLowercase, boolean includeUppercase, boolean includeNumbers, boolean includeSymbols) {
        StringBuilder pool = new StringBuilder();
        
        // If a character type has been selected, add it to the character pool
        if (includeLowercase) {
            pool.append(LOWERCASE);
        }
        if (includeUppercase) {
            pool.append(UPPERCASE);
        }
        if (includeNumbers) {
            pool.append(NUMBERS);
        }
        if (includeSymbols) {
            pool.append(SYMBOLS);
        }

        // Return newly created character pool
        return pool.toString();
    }

    // Helper function used in the passwoird generation that shuffles characters in a string
    private static String shuffleString(String input) {
        Random random = new Random();

        // Convert the string into a character array
        char[] characters = input.toCharArray();

        // For each index in the character array, swap it's value with another index's value
        for (int i = characters.length - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }

        // Convert character array back to a string and return it
        return new String(characters);
    }

    // Function used to generate the password
    private static String generatePassword(int length, String characterPool, boolean hasLower, boolean hasUpper, boolean hasNumbers, boolean hasSymbols) {
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Garantee that a character from each selected character type makes it into the password
        if (hasLower) password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        if (hasUpper) password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        if (hasLower) password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        if (hasLower) password.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));

        // Add random characters to the password until the desired length is achieved
        while (password.length() < length) {
            int randomIndex = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(randomIndex));
        }

        // Return the shuffled password
        return shuffleString(password.toString());
    }

    // Function that calculates and returns the strength of the created password
    private static String getPasswordStrength(int length, boolean hasLower, boolean hasUpper, boolean hasNumbers, boolean hasSymbols) {
        int strength = 0;

        // Calculate strength score using password criteria
        if (length >= 8) strength++;
        if (length >= 12) strength++;
        if (hasLower) strength++;
        if (hasUpper) strength++;
        if (hasNumbers) strength++;
        if (hasSymbols) strength++;

        // Determine strength classification based on score
        if (strength <= 2) return "Weak";
        else if (strength <= 4) return "Medium";
        else return "Strong";
    }
}