import java.util.Map;
import java.util.Scanner;

public class Controller {
    public static void main(String[] args) {
        CashDispensingApp atm = new CashDispensingApp(10, 100);
        Scanner myScanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter desired amount: ");
            int requestedAmount = myScanner.nextInt();
            WithdrawResult withdrawResult = atm.withdrawMoney(requestedAmount);

            if (!withdrawResult.hasError()) {
                System.out.println("Transaction accepted...Please wait...");
                for (Map.Entry<Integer, Integer> entry : withdrawResult.getResultMap().entrySet()) {
                    System.out.println("$" + entry.getKey() + " provided: " + entry.getValue());
                }
            } else {
                System.out.println("Invalid amount requested...");
            }
        }
    }
}
