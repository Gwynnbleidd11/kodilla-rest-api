package additionalTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SortingNumbers {
    public static void main(String[] args) {

        List<Integer> testList = new ArrayList<>();
        testList.add(3);
        testList.add(1);
        testList.add(1);
        testList.add(5);
        testList.add(6);
        testList.add(4);
        testList.add(-7);
        testList.add(11);
        testList.add(-2);

        System.out.println(SortingNumbersMethod.sortListOfNumbers(testList));
        System.out.println(SortingNumbersMethod.lambdaSortingMethod(testList));

    }
}
class SortingNumbersMethod {

    public static List<Integer> sortListOfNumbers(List<Integer> list) {
        List<Integer> temporaryList = new ArrayList<>(list);
        List<Integer> resultList = new ArrayList<>();

        for (int j = 0; j < temporaryList.size(); j++) {
            if (temporaryList.get(j) < 0) {
                temporaryList.remove(j);
            }
        }

        while (temporaryList.size() > 0) {
            int min = temporaryList.get(0);

            for (int n = 0; n < temporaryList.size(); n++) {
                if (temporaryList.get(n) < min) {
                    min = temporaryList.get(n);
                }
            }
            resultList.add(min);
            temporaryList.remove(Integer.valueOf(min));
        }
        return resultList;
    }

    public static List<Integer> lambdaSortingMethod(List<Integer> list) {
        return list.stream()
                .filter(n -> n >= 0)
                .sorted()
                .collect(Collectors.toList());
    }
}
