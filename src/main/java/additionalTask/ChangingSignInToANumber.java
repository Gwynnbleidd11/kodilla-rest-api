package additionalTask;

public class ChangingSignInToANumber {
    public static void main(String[] args) {

        System.out.println(convertSignToInt("579"));
        System.out.println(convertSignToInt("46"));
        System.out.println(convertSignToInt("-89"));
        System.out.println(convertSignToInt("5tom79"));
        System.out.println(convertSignToInt("alpaka"));
    }

    public static int convertSignToInt(String string) {
        int result = 0;
        boolean isNegative = false;
        int startIndex = 0;

        if(string.charAt(0) == '-') {
            isNegative = true;
            startIndex = 1;
        }

        try {
        for (int i = startIndex; i < string.length(); i++) {
                char digit = string.charAt(i);
                if (Character.isDigit(digit)) {
                    int digitValue = digit - '0';
                    result = result * 10 + digitValue;
                } else {
                    throw new IllegalArgumentException("Invalid character in the String " + digit);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid character in the String. Only numbers are allowed with exception of '-' at the beginning");
            result = 0;
        }

        if (isNegative) {
            result = -result;
        }

        return result;
    }
}
