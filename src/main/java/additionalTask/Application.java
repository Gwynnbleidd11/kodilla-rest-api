package additionalTask;

public class Application {
    public static void main(String[] args) {

        double[] grades = {3, 1, 1, 5, 6, 4};
        int[] gradeWeights = {4, 6, 8, 4, 4, 10};
        double[] grades2 = {};
        int[] weights = {};

        double result = MediumWeight.calculateMediumWeight(grades, gradeWeights);
        System.out.println("Weighted average is: " + result);

    }
}
