package additionalTask;

public class PrimeNumbers {
    public static void main(String[] args) {

        int number = 37;

        System.out.println(PrimeNumberCalculator.primeNumberInformation(
                PrimeNumberCalculator.isPrimeNumber(number)));

    }
}

class PrimeNumberCalculator {

    public static boolean isPrimeNumber(int number) {
        if (number < 2)
            return false;
        for (int n = 2; n < number; n++) {
            if (number % n == 0) {
                return false;
            }
        }
        return true;
    }

    public static String primeNumberInformation(boolean isPrimeNumber) {
        if (isPrimeNumber) {
            return "Given number is prime number";
        } else {
            return "Given number is not prime number";
        }
    }
}
