import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AffineCipher {

    // Function to calculate modular inverse
    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1; // Modular inverse doesn't exist
    }

    // Encryption function
    public static String encrypt(String plainText, int a, int b) {
        StringBuilder cipherText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) (((a * (c - base) + b) % 26) + base);
            }
            cipherText.append(c);
        }
        return cipherText.toString();
    }

    // Decryption function
    public static String decrypt(String cipherText, int a, int b) {
        StringBuilder plainText = new StringBuilder();
        int aInverse = modInverse(a, 26);
        for (char c : cipherText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) (((aInverse * ((c - base - b + 26)) % 26) + base));
            }
            plainText.append(c);
        }
        return plainText.toString();
    }

    // Main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plain text:");
        String text = scanner.nextLine();

        System.out.println("Enter values for 'a' and 'b' (a must be coprime with 26):");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (modInverse(a, 26) == 1) {
            System.out.println("Do you want to encrypt or decrypt the text? (e/d):");
            String choice = scanner.nextLine().toLowerCase();

            String result = "";
            if (choice.equals("e")) {
                result = encrypt(text, a, b);
                System.out.println("Encrypted text: " + result);
            } else if (choice.equals("d")) {
                result = decrypt(text, a, b);
                System.out.println("Decrypted text: " + result);
            } else {
                System.out.println("Invalid choice.");
                scanner.close();
                return;
            }

            // Write the input and result to an external file
            try (FileWriter writer = new FileWriter("AffineCipherOutput.txt")) {
                writer.write("Original text: " + text + "\n");
                writer.write((choice.equals("e") ? "Encrypted text: " : "Decrypted text: ") + result + "\n");
                System.out.println("Output written to AffineCipherOutput.txt");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("The value of 'a' must be coprime with 26. Please try again.");
        }

        scanner.close();
    }
}
