package lab06.bank.app;

import java.util.Scanner;

import lab06.bank.account.Account;
import lab06.bank.account.CurrentAccount;
import lab06.bank.account.SavingsAccount;





public class ATMApp {

    private static final int TOTAL_ACCOUNTS = 5;
    private static final double INITIAL_DEPOSIT = 1000;
    private static final double MINIMUM_BALANCE = 500;
    private static final double INTEREST_RATE = 4.0;
    private static final double OVERDRAFT_LIMIT = 5000;

    
    private static final Account[] ACCOUNTS = new Account[TOTAL_ACCOUNTS];
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        ACCOUNTS[0] = new SavingsAccount("Rahul Sharma", INITIAL_DEPOSIT, MINIMUM_BALANCE, INTEREST_RATE);
        ACCOUNTS[1] = new CurrentAccount("Priya Verma", INITIAL_DEPOSIT, OVERDRAFT_LIMIT);
        ACCOUNTS[2] = new SavingsAccount("Amit Kumar", INITIAL_DEPOSIT, MINIMUM_BALANCE, INTEREST_RATE);
        ACCOUNTS[3] = new CurrentAccount("Sneha Gupta", INITIAL_DEPOSIT, OVERDRAFT_LIMIT);
        ACCOUNTS[4] = new SavingsAccount("Rohan Sharma", INITIAL_DEPOSIT, MINIMUM_BALANCE, INTEREST_RATE);

        System.out.println("Total accounts created: " + Account.getTotalAccounts());
        displayAllAccounts();
        System.out.println("(Demo account numbers: " + ACCOUNTS[0].getAccountNumber() + " ... "
                + ACCOUNTS[TOTAL_ACCOUNTS - 1].getAccountNumber() + ", step 1111)");

        boolean running = true;
        while (running) {
            System.out.println("\n===== " + Account.getBankName() + " ATM =====");
            System.out.println("1. Enter account number");
            System.out.println("2. Search account by holder name");
            System.out.println("3. Display all accounts");
            System.out.println("4. Exit");
            System.out.print("Select Option number: ");
            switch (readLine()) {
                case "1":
                    System.out.print("Enter Account number: ");
                    Account account = findAccount(readLine());
                    if (account == null) {
                        System.out.println("No such account exists.");
                    } else {
                        running = runAccountMenu(account);
                    }
                    break;
                case "2":
                    System.out.print("Enter holder name (full or partial): ");
                    searchByHolderName(readLine());
                    break;
                case "3":
                    displayAllAccounts();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        System.out.println("Thank you for using " + Account.getBankName() + " ATM.");
    }

    
    private static boolean runAccountMenu(Account account) {
        System.out.println("Welcome, " + account.getHolderName() + " [" + account.getAccountType()
                + " account " + account.getMaskedAccountNumber() + "]");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("   1. Deposit (amount only)");
            System.out.println("   2. Deposit (amount and mode)");
            System.out.println("   3. Withdraw");
            System.out.println("   4. Check Balance");
            System.out.println("   5. Transaction History");
            System.out.println("   6. Search History (DEPOSIT / WITHDRAW / Cheque / Transfer ...)");
            System.out.println("   7. Transfer money");
            System.out.println("   8. Show interest (savings accounts)");
            System.out.println("   9. Enter another account");
            System.out.println("  10. Exit");
            System.out.print("Select Option number: ");
            switch (readLine()) {
                case "1":
                    System.out.print("Enter Amount to deposit: ");
                    if (account.deposit(readAmount())) {
                        System.out.println("Deposit successful.");
                    }
                    break;
                case "2":
                    System.out.print("Enter Amount to deposit: ");
                    double amount = readAmount();
                    System.out.print("Deposit mode (1 = Cash, 2 = Cheque): ");
                    String mode = readLine().equals("2") ? Account.MODE_CHEQUE : Account.MODE_CASH;
                    if (account.deposit(amount, mode)) {
                        System.out.println(mode + " deposit successful.");
                    }
                    break;
                case "3":
                    System.out.print("Enter Amount to withdraw: ");
                    if (account.withdraw(readAmount())) { 
                        System.out.println("Please collect your cash.");
                    }
                    break;
                case "4":
                    System.out.println("Balance: Rs." + Account.formatAmount(account.getBalance()));
                    break;
                case "5":
                    System.out.println("Transaction history of " + account.getMaskedAccountNumber() + ":");
                    System.out.print(account.getTransactionHistory());
                    break;
                case "6":
                    System.out.print("Enter type or word to search: ");
                    String result = account.searchTransactions(readLine());
                    System.out.print(result.isEmpty() ? "No matching transactions.\n" : result);
                    break;
                case "7":
                    transferMoney(account);
                    break;
                case "8":
                    if (account instanceof SavingsAccount) {
                        ((SavingsAccount) account).displayInterest();
                    } else {
                        System.out.println("Interest is available only for savings accounts.");
                    }
                    break;
                case "9":
                    return true;
                case "10":
                    return false;
                default:
                    System.out.println("Invalid option. Choose 1-10.");
            }
        }
    }

    private static void transferMoney(Account source) {
        System.out.print("Enter destination Account number: ");
        Account destination = findAccount(readLine());
        if (destination == null) {
            System.out.println("Destination account not found.");
            return;
        }
        System.out.print("Enter Amount to transfer: ");
        double amount = readAmount();
        if (source.transferTo(destination, amount)) {
            System.out.println("Transferred Rs." + Account.formatAmount(amount) + " to "
                    + destination.getHolderName() + " [" + destination.getMaskedAccountNumber() + "].");
            System.out.println("Your balance: Rs." + Account.formatAmount(source.getBalance()));
        }
    }

    private static void displayAllAccounts() {
        for (Account account : ACCOUNTS) {
            account.displayAccount(); 
        }
    }

    private static void searchByHolderName(String searchName) {
        boolean found = false;
        for (Account account : ACCOUNTS) {
            if (account.matchesHolderName(searchName)) {
                account.displayAccount();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No account found for \"" + searchName.trim() + "\".");
        }
    }

    private static Account findAccount(String accountNumber) {
        for (Account account : ACCOUNTS) {
            if (account.getAccountNumber().equals(accountNumber.trim())) {
                return account;
            }
        }
        return null;
    }

    private static String readLine() {
        if (!SCANNER.hasNextLine()) {
            System.out.println("\nInput ended.");
            System.exit(0);
        }
        return SCANNER.nextLine().trim();
    }

    private static double readAmount() {
        while (true) {
            try {
                return Double.parseDouble(readLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid amount: ");
            }
        }
    }
}
