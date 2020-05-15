public class Crypto {
    public static void main(String[] args) {
        String cyphertext = encryptString("Who will win the election?", 5, 3);
        String plaintext = decryptString(cyphertext, 5);
        System.out.println("Who will win the election?");
        System.out.print("Encrypted String: ");
        System.out.println(cyphertext);
        System.out.print("Decrypted String: ");
        System.out.println(plaintext);
    }

    public static String normalizeText(String text) {
        return text.replaceAll("[^a-zA-Z ]|\\s+", "").toUpperCase();
    }

    public static String obify(String s) {
        char[] vowels = { 'A', 'E', 'I', 'O', 'U', 'Y' };
        String n = "";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (compare(c, vowels)) {
                n += "OB" + c;
            } else {
                n += c;
            }
        }
        return n;
    }

    public static boolean compare(char c, char[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (c == array[i]) {
                return true;
            }
        }
        return false;
    }

    public static String unobify(String s) {
        String n = "";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c1 = s.charAt(i);
            if (c1 == 'O') {
                char c2 = s.charAt(i + 1);
                if (c2 == 'B') {
                    char c3 = s.charAt(i + 2);
                    n += c3;
                    i += 2;
                }
            } else {
                n += c1;
            }
        }
        return n;
    }

    public static String caesarify(String input, int key) {
        String result = "";
        String shiftedAlphabet = shiftAlphabet(key);
        for (int i = 0; i < input.length(); i++) {
            char currentLetter = input.charAt(i);
            int newLetterIndex = currentLetter - 'A';
            result += shiftedAlphabet.charAt(newLetterIndex);
        }
        return result;
    }

    public static String shiftAlphabet(int shift) {
        int start = 0;
        if (shift < 0) {
            start = (int) 'Z' + shift + 1;
        } else {
            start = 'A' + shift;
        }
        String result = "";
        char currChar = (char) start;
        for (; currChar <= 'Z'; ++currChar) {
            result = result + currChar;
        }
        if (result.length() < 26) {
            for (currChar = 'A'; result.length() < 26; ++currChar) {
                result = result + currChar;
            }
        }
        return result;
    }

    public static String groupify(String input, int key) {
        String result = "";
        if (input.length() < key) {
            result += input;
            for (int i = 0; i < (key - input.length()); i++) {
                result += "x";
            }
            return result;
        }
        result += input.substring(0, key) + " ";
        return result + groupify(input.substring(key), key);
    }

    public static String ungroupify(String input) {
        String result = "";
        String normalizeInput = input.replaceAll("\\s+", "");
        for (int i = 0; i < normalizeInput.length(); i++) {
            char letter = normalizeInput.charAt(i);
            if (letter != 'x') {
                result += letter;
            }
        }
        return result;
    }

    public static String encryptString(String input, int shift, int group) {
        String normalizeInput = normalizeText(input);
        String obifyInput = obify(normalizeInput);
        String caesarifyInput = caesarify(obifyInput, shift);
        String groupifyInput = groupify(caesarifyInput, group);

        return groupifyInput;
    }

    public static String decryptString(String s, int shift) {
        String d = ungroupify(s);
        d = caesarify(d, -1 * shift);
        d = unobify(d);
        return d;
    }
}