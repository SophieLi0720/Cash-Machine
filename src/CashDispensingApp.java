import java.util.HashMap;
import java.util.Map;

public class CashDispensingApp {
    /* A hash map that stores how many of each type of bank note the device has */
    private static final Map<Integer, Integer> noteMap = new HashMap<>();
    private final int BALANCE_THRESHOLD;
    private int balance = 0;
    private boolean balanceAlert = false; // TODO if get extra time

    /* Constructor */
    public CashDispensingApp(int defaultNoteCount, int balanceThreshold) {
        // Initialisation with default value
        BALANCE_THRESHOLD = balanceThreshold;
        for (Denomination note : Denomination.values()) {
            noteMap.put(note.getNoteValue(), defaultNoteCount);
        }
        updateBalance();
    }

    private void updateNoteMap(Map<Integer, Integer> withdrawnResult) {
        for (Map.Entry<Integer, Integer> entry : withdrawnResult.entrySet()) {
            int currentNote = entry.getKey();
            int currentCount = noteMap.get(currentNote) - entry.getValue();
            noteMap.put(currentNote, currentCount);
        }
        updateBalance();
    }

    private void updateBalance() {
        balance = 0;
        noteMap.forEach((noteValue, noteCount) -> balance += noteValue * noteCount);
        balanceAlert = balance <= BALANCE_THRESHOLD;
    }

    public WithdrawResult withdrawMoney(int requestedAmount) {
        WithdrawResult withdrawResult = new WithdrawResult();
        if (requestedAmount <= balance) {
            withdrawResult.getCombinationMap(requestedAmount, noteMap);
            if (!withdrawResult.hasError()) {
                updateNoteMap(withdrawResult.getResultMap());
            }
        }
        return withdrawResult;
    }
}
