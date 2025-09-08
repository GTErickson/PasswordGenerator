import java.util.Scanner;
import java.util.Random;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";  

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Password Generator!");
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

        String password = generatePassword(length, characterPool);

        System.out.println("\nGenerated Password: " + password);
        System.out.println("Password Strength: " + characterPool.length() + " possible characters");

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

    private static String generatePassword(int length, String characterPool) {
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterPool.length());
            char randomChar = characterPool.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
}