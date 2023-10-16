package banking;

/**
 * Class to hold accounts of different types
 * @author Ishani Mhatre
 */
public class AccountDatabase {
    private Account[] accounts; // List of various types of accounts
    private int numAcct; // Number of accounts in the array

    private final int NOT_FOUND = -1;

    public AccountDatabase() {
        accounts = new Account[4]; // Initial capacity is 4
        numAcct = 0;
    }

    // Increase the capacity by 4
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + 4];
        for(int i =0; i<numAcct; i++){
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    // Find an account in the array
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (account.compareTo(accounts[i])==0) {
                return i;
            }
        }
        return NOT_FOUND; // Account not found
    }

    // Check if the account is already in the database
    public boolean contains(Account account) {
        return find(account) != NOT_FOUND;
    }


    // Add a new account to the database
    public boolean open(Account account) {
        if (contains(account)) {
            return false; // Account already exists
        }
        accounts[numAcct] = account;
        numAcct++;
        if (numAcct == accounts.length) {
            grow();
        }
        return true;
    }

    // Remove the given account from the database
    public boolean close(Account account) {
        int index = find(account);
        if (index == NOT_FOUND || accounts[index].getClass().getName()!=account.getClass().getName()) {
            return false; // Account not found or account details do not match
        }
        // Move the last account to the removed account's position
        accounts[index] = accounts[numAcct - 1];
        accounts[numAcct - 1] = null;
        numAcct--;
        return true;
    }


    // Withdraw an amount from the account (false if insufficient funds)
    public boolean withdraw(Account account, double amount) { //check if moneymarket withdrawls are less than three
        int index = find(account);
        if (index == NOT_FOUND) {
            return false; // Account not found
        }
        if (accounts[index].balance < amount) {
            return false; // Insufficient funds
        }
        double amountToWithdraw = amount;
        if(account instanceof MoneyMarket){
            MoneyMarket mm = ((MoneyMarket) accounts[index]);
            if(mm.getWithdrawal()>3){
                amountToWithdraw+=10;
            }
            if(mm.balance<amountToWithdraw){
                return false;
            }
            mm.setWithdrawal(mm.getWithdrawal()+1);
            if((mm.balance - amountToWithdraw) <2000){
                mm.setLoyal(false);
            }
        }
        accounts[index].balance -= amountToWithdraw;
        return true;
    }

    // Deposit an amount into the account
    public boolean deposit(Account account, double amount) {
        int index = find(account);
        if (index == NOT_FOUND || accounts[index].getClass().getName()!=account.getClass().getName()) {
            return false;
        }
        accounts[index].balance += amount;
        if(account instanceof MoneyMarket) {
            MoneyMarket mm = ((MoneyMarket) accounts[index]);
            if(mm.balance>=2000){
                mm.setLoyal(true);
            }
        }
        return true;
    }

    public String printSorted(String[] inputData) {
        if (numAcct == 0) {
            return "Account Database is empty!";
        } else {
            // Sort the accounts using bubble sort
            bubbleSort(accounts, numAcct);
            // Print the sorted accounts
            System.out.println("\n*Accounts sorted by account type and profile.*");
            for (Account account : accounts) {
                if (account != null) {
                    System.out.println(account.toString());
                }
            }
            return null;
        }
    }


    // Modified bubble sort algorithm to sort based on compareTo
    private void bubbleSort(Account[] accounts, int numAcct) {
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - i - 1; j++) {
                if (accounts[j].compareTo(accounts[j + 1]) > 0) { // compareTo compares account type and profile
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }


//    // Calculate interests and fees
//    public void printFeesAndInterests() {
//        if (numAcct == 0) {
//            System.out.println("Account Database is empty!");
//        } else {
//            for (int i = 0; i < numAcct; i++) {
//                Account account = accounts[i];
//                String accountType = account.getClass().getSimpleName();
//                String holder = account.getProfile().getFirstName() + " " + account.getProfile().getLastName();
//                String dob = account.getProfile().getDateOfBirth().toString();
//                double balance = account.getBalance();
//                double monthlyInterest = account.monthlyInterest();
//                double monthlyFee = account.monthlyFee();
//
//                // Check if the account is Money Market and if it's loyal
//                if (account instanceof MoneyMarket) {
//                    MoneyMarket mm = (MoneyMarket) account;
//                    if (mm.isLoyal()) {
//                        holder += " (Loyal)";
//                    }
//                }
//
//                // Print the account details, fee, and interest
//                System.out.printf("%s::%s %s::Balance $%.2f::fee $%.2f::monthly interest $%.2f%n", accountType, holder, dob, balance, monthlyFee, monthlyInterest);
//            }
//        }
//    }

       public void printFeesAndInterests() {
        System.out.println("*list of accounts with fee and monthly interest");
        if (numAcct == 0) {
            System.out.println("Account Database is empty!");
        } else {
            // Create arrays for each account type
            Account[] checkingAccounts = new Account[numAcct];
            Account[] collegeCheckingAccounts = new Account[numAcct];
            Account[] moneyMarketAccounts = new Account[numAcct];
            Account[] savingsAccounts = new Account[numAcct];

            // Initialize counters for each account type
            int numChecking = 0;
            int numCollegeChecking = 0;
            int numMoneyMarket = 0;
            int numSavings = 0;

            // Iterate through accounts and categorize them
            for (int i = 0; i < numAcct; i++) {
                Account account = accounts[i];
                String accountType = account.getClass().getSimpleName();

                // Determine the account type and store it in the appropriate array
                if (accountType.equals("Checking")) {
                    checkingAccounts[numChecking] = account;
                    numChecking++;
                } else if (accountType.equals("CollegeChecking")) {
                    collegeCheckingAccounts[numCollegeChecking] = account;
                    numCollegeChecking++;
                } else if (accountType.equals("MoneyMarket")) {
                    moneyMarketAccounts[numMoneyMarket] = account;
                    numMoneyMarket++;
                } else if (accountType.equals("Savings")) {
                    savingsAccounts[numSavings] = account;
                    numSavings++;
                }
            }

            // Print accounts in alphabetical order of account type
            printAccounts(checkingAccounts, numChecking);
            printAccounts(collegeCheckingAccounts, numCollegeChecking);
            printAccounts(moneyMarketAccounts, numMoneyMarket);
            printAccounts(savingsAccounts, numSavings);
        }
        System.out.println("*end of list.");
    }

    private void printAccounts(Account[] accounts, int numAccounts) {
        for (int i = 0; i < numAccounts; i++) {
            Account account = accounts[i];
            String accountType = account.getClass().getSimpleName();
            String holder = account.getProfile().getFirstName() + " " + account.getProfile().getLastName();
            String dob = account.getProfile().getDateOfBirth().toString();
            double balance = account.getBalance();
            double monthlyInterest = account.monthlyInterest();
            double monthlyFee = account.monthlyFee();

            // Add "is loyal" information for Money Market and Savings accounts when isLoyal is true
            String loyalInfo = "";
            if (accountType.equals("Savings") && account instanceof Savings) {
                Savings savingsAccount = (Savings) account;
                if (savingsAccount.isLoyal()) {
                    loyalInfo = "::is loyal";
                }
            }

            if (accountType.equals("MoneyMarket") && account instanceof MoneyMarket) {
                MoneyMarket moneyMarket = (MoneyMarket) account;
                if (moneyMarket.isLoyal()) {
                    loyalInfo = "::is loyal";
                }
            }


            System.out.printf("%s::%s %s::Balance $%.2f%s::fee $%.2f::monthly interest $%.2f%n", accountType, holder, dob, balance, loyalInfo, monthlyFee, monthlyInterest);
        }
    }


    // Apply interests and fees to update balances
    public void printUpdatedBalances() {

        if (numAcct == 0) {
            System.out.println("Account Database is empty!");
        } else {
            for (int i = 0; i < numAcct; i++) {
                Account account = accounts[i];
                double monthlyInterest = account.monthlyInterest();
                double monthlyFee = account.monthlyFee();

                // Update the account balance based on the interest and fees
                double updatedBalance = account.getBalance() + monthlyInterest - monthlyFee;
                account.setBalance(updatedBalance);

                String accountType = account.getClass().getSimpleName(); // Get the account type

                // Print the updated balance for each account
                System.out.printf("%s - Updated Balance: $%.2f%n", accountType, updatedBalance);
            }
        }
    }

    public double getBalance(Account account) {
        int index = find(account); // Find the index of the specified account
        if (index != NOT_FOUND) {
            return accounts[index].balance; // Return the balance of the specified account
        }
        return -1.0; // Return a negative value (or another suitable indicator) to indicate that the account was not found.
    }


//    / Calculate updated balances and return them in an array
//    public double[] calculateUpdatedBalances() {
//        double[] updatedBalances = new double[numAcct];
//
//        for (int i = 0; i < numAcct; i++) {
//            Account account = accounts[i];
//            double monthlyInterest = account.monthlyInterest();
//            double monthlyFee = account.monthlyFee();
//
//            // Calculate the updated balance based on the interest and fees
//            double updatedBalance = account.getBalance() + monthlyInterest - monthlyFee;
//            updatedBalances[i] = updatedBalance;
//        }
//
//        return updatedBalances;
//    }
//
//    // Print the updated balances
//    public void printUpdatedBalances(double[] updatedBalances) {
//        for (int i = 0; i < numAcct; i++) {
//            Account account = accounts[i];
//            String accountType = account.getClass().getSimpleName(); // Get the account type
//
//            // Print the updated balance for each account
//            System.out.printf("%s - Updated Balance: $%.2f%n", accountType, updatedBalances[i]);
//        }
//    }



}
