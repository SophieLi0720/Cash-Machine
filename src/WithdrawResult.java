import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WithdrawResult {
    private static Map<Integer, Integer> resultMap = new HashMap<>();
    private boolean error = true;

    public boolean hasError() {
        return error;
    }

    public Map<Integer, Integer> getResultMap() {
        return resultMap;
    }

    public boolean getCombinationMap(int requestAmount, Map<Integer, Integer> balanceMap) {
        // An ArrayList that contains denominations as items
        // The length of the ArrayList will be the sum of the number of banknotes of various denominations
        ArrayList<Integer> notes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : balanceMap.entrySet()) {
            Integer noteValue = entry.getKey();
            Integer noteCount = entry.getValue();
            int j;
            for (j = 0; j < noteCount; j++) {
                notes.add(noteValue);
            }
        }

        // Sort the ArrayList to descending order to give preference to the larger denominations in the Dynamic Programming algorithm
        notes.sort(Collections.reverseOrder());
        int noteCount = notes.size();

        // Build up a DP table
        int[][] combinationTable = new int[noteCount + 1][requestAmount + 1];
        int itemNum;
        int combinationVal;

        // combinationTable[itemNum][combinationVal] denote the maximum total value
        // achievable within the combinationVal using items 0, 1, 2, ... , itemNum
        for (itemNum = 0; itemNum <= noteCount; itemNum++) {
            for (combinationVal = 0; combinationVal <= requestAmount; combinationVal++) {
                if (itemNum == 0 || combinationVal == 0) {
                   // If i = 0, we have no items, therefore total value = 0
                    combinationTable[itemNum][combinationVal] = 0;
                } else if (notes.get(itemNum - 1) <= combinationVal) {
                    // If the value of ith note <= combinationVal, try with and without the ith note and see
                    //which gives the best outcome
                    combinationTable[itemNum][combinationVal] = Math.max(notes.get(itemNum - 1) + combinationTable[itemNum - 1][combinationVal - notes.get(itemNum - 1)],
                            combinationTable[itemNum - 1][combinationVal]);
                } else {
                    // If the value of ith note > combinationVal, we can’t use it.
                    combinationTable[itemNum][combinationVal] = combinationTable[itemNum - 1][combinationVal];
                }
            }
        }

        // back tracking to get the solution
        resultMap.clear();
        itemNum = noteCount;
        combinationVal = requestAmount;
        while (itemNum > 0 && combinationVal > 0) {
            if (combinationTable[itemNum][combinationVal] != combinationTable[itemNum - 1][combinationVal]) {
                // If the maximum total value achieved changed, the ith note was used
                int currentNote = notes.get(itemNum - 1);
                // Add the ith note to the resultMap
                if (resultMap.containsKey(currentNote)) {
                    int currentCount = resultMap.get(currentNote) + 1;
                    resultMap.put(currentNote, currentCount);
                } else {
                    resultMap.put(currentNote, 1);
                }
                combinationVal = combinationVal - notes.get(itemNum - 1);
            }
            itemNum--;
        }

        // The final optimal solution should be equal to requestAmount to solve the problem
        error = combinationTable[noteCount][requestAmount] != requestAmount;
        return error;
    }
}
