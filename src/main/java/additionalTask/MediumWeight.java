package additionalTask;

public class MediumWeight {

    public static double calculateMediumWeight(double[] grades, int[] weights) {
        double sum = 0;
        int weightSum = 0;
        double result = 0;
        checkGradeBounds(grades, 1, 6);
        checkWeightBounds(weights, 1, 10);
        verifyArraysLengths(grades, weights);
        verifyIfGradeArrayIsNotEmpty(grades);
        verifyIfWeightArrayIsNotEmpty(weights);
        for (int n = 0; n < grades.length; n++) {
            sum += grades[n] * weights[n];
            weightSum += weights[n];
        }
        result = sum / weightSum;
        return result;
    }

    private static void checkGradeBounds(double[] array, int lowerBound, int upperBound) {
        for (double value: array) {
            if (value < lowerBound || value > upperBound) {
                throw new IllegalArgumentException("Grade out of bounds 1 - 6: " + value);
            }
        }
    }

    private static void checkWeightBounds(int[] array, int lowerBound, int upperBound) {
        for (int value: array) {
            if (value < lowerBound || value > upperBound) {
                throw new IllegalArgumentException("Weight out of bounds 1 - 10: " + value);
            }
        }
    }

    private static void verifyArraysLengths(double[] grades, int[] weights) {
        if (grades.length != weights.length) {
            throw new ArrayIndexOutOfBoundsException("Arrays are not the same length!");
        }
    }

    private static void verifyIfGradeArrayIsNotEmpty(double[] grades) {
        if (grades.length == 0) {
            throw new ArrayIndexOutOfBoundsException("One of the arrays has no records!");
        }
    }

    private static void verifyIfWeightArrayIsNotEmpty(int[] weights) {
        if (weights.length == 0) {
            throw new ArrayIndexOutOfBoundsException("One of the arrays has no records!");
        }
    }
}
