# Cash-Machine
A cash dispensing application for use in an ATM or similar device

## To run the project:
1. git clone https://github.com/SophieLi0720/Cash-Machine -- to your desired local folder
2. Navigate to /Cash-Machine/src
3. Open Controller.java file and run Controller.main()
4. You should be able to interact with the program by entering a valid int number
5. You can modify the first argument of the CashDispensingApp constructor (line 6) to give the application a different initial balance

## This app is designed to:
1. Emulate a cash dispensing device like an ATM
2. Suport various denominations and automatically update the balance after successful transactions
3. Process transaction requests, provide information and dispense legal combinations of notes

## Further Development(TODO):
1. Implement threshold notification and different error notifications for the different reasons for failures
2. Break down the WithdrawResult class to guarantee maintainability
