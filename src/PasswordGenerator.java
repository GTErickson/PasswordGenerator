import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";  

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console cmd = System.console();

        if (cmd == null) {
             System.out.println("No Console Available.");
        }

        while (true) {
            cmd.flush();
            System.out.println("\nWelcome to the Password Generator!");
            System.out.println("=================================");
            
            int length = -1;
            while(length < 4) {
                System.out.print("Enter desired password length (minimum 4): ");
                length = scanner.nextInt();
            }

            System.out.print("Include lowercase letters? (y/n): ");
            boolean includeLowercase = scanner.next().toLowerCase().startsWith("y");

            System.out.print("Include uppercase letters? (y/n): ");
            boolean includeUppercase = scanner.next().toLowerCase().startsWith("y");

            System.out.print("Include numbers? (y/n): ");
            boolean includeNumbers = scanner.next().toLowerCase().startsWith("y");
            
            System.out.print("Include symbols? (y/n): ");
            boolean includeSymbols= scanner.next().toLowerCase().startsWith("y");

            if(!includeLowercase && !includeUppercase && !includeNumbers && !includeSymbols) {
                System.out.println("You must select at least one character type!");
                System.out.println("Enabling lowercase letters by default.");
                includeLowercase = true;
            }

            String characterPool = buildCharacterPool(includeLowercase, includeUppercase, includeNumbers, includeSymbols);

            String password = generatePassword(length, characterPool, includeLowercase, includeUppercase, includeNumbers, includeSymbols);

            System.out.println("\nGenerated Password: " + password);
            System.out.println("Password Strength: " + getPasswordStrength(length, includeLowercase, includeUppercase, includeNumbers, includeSymbols));

            System.out.print("Would you like to generate another password? (y/n): ");
            boolean done = scanner.next().toLowerCase().startsWith("n");

            if (done) break;
        }

        scanner.close();
    }

    private static String buildCharacterPool(boolean includeLowercase, boolean includeUppercase, boolean includeNumbers, boolean includeSymbols) {
        StringBuilder pool = new StringBuilder();
        
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

        return pool.toString();
    }

    private static String shuffleString(String input) {
        Random random = new Random();
        char[] characters = input.toCharArray();

        for (int i = characters.length - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }

        return new String(characters);
    }

    private static String generatePassword(int length, String characterPool, boolean hasLower, boolean hasUpper, boolean hasNumbers, boolean hasSymbols) {
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        if (hasLower) password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        if (hasUpper) password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        if (hasLower) password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        if (hasLower) password.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));

        while (password.length() < length) {
            int randomIndex = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(randomIndex));
        }

        return shuffleString(password.toString());
    }

    private static String getPasswordStrength(int length, boolean hasLower, boolean hasUpper, boolean hasNumbers, boolean hasSymbols) {
        int strength = 0;

        if (length >= 8) strength++;
        if (length >= 12) strength++;
        if (hasLower) strength++;
        if (hasUpper) strength++;
        if (hasNumbers) strength++;
        if (hasSymbols) strength++;

        if (strength <= 2) return "Weak";
        else if (strength <= 4) return "Medium";
        else return "Strong";
    }
}