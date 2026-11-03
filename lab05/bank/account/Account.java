package lab05.bank.account;

public class Account {

    public static final String TYPE_DEPOSIT = "DEPOSIT";
    public static final String TYPE_WITHDRAW = "WITHDRAW";
    public static final String MODE_CASH = "Cash";
    public static final String MODE_CHEQUE = "Cheque";
    public static final String MODE_ATM = "ATM";

    private static String bankName;
    private static int totalAccounts;
    private static long nextAccountNumber;

    static {
        bankName = "State Bank of India";
        totalAccounts = 0;
        nextAccountNumber = 40213453314L;
        System.out.println(">> ATM system initialized for " + bankName);
    }

    private final String accountNumber;
    private final String holderName;
    private double balance;
    private final StringBuilder transactionHistory = new StringBuilder();
    private int transactionCount;

    public Account(String holderName, double initialDeposit) {
        this.accountNumber = String.valueOf(nextAccountNumber);
        nextAccountNumber += 1111;
        this.holderName = holderName;
        this.balance = initialDeposit;
        totalAccounts++;
        recordTransaction("OPENING", initialDeposit, "Initial deposit");
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static String getBankName() {
        return bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getMaskedAccountNumber() {
        int visibleFrom = accountNumber.length() - 4;
        return "*".repeat(visibleFrom) + accountNumber.substring(visibleFrom);
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return "Basic";
    }

    public boolean matchesHolderName(String searchName) {
        String searchText = searchName.trim().toLowerCase();
        return !searchText.isEmpty() && holderName.toLowerCase().contains(searchText);
    }

    public boolean deposit(double amount) {
        return deposit(amount, MODE_CASH);
    }

    public boolean deposit(double amount, String depositMode) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return false;
        }
        balance += amount;
        recordTransaction(TYPE_DEPOSIT, amount, depositMode);
        return true;
    }

    public boolean withdraw(double amount) {
        return withdraw(amount, MODE_ATM);
    }

    public boolean withdraw(double amount, String withdrawalMode) {
        if (!isValidAmount(amount)) {
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        }
        debit(amount, withdrawalMode);
        return true;
    }

    protected boolean isValidAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return false;
        }
        return true;
    }

    protected void debit(double amount, String withdrawalMode) {
        balance -= amount;
        recordTransaction(TYPE_WITHDRAW, amount, withdrawalMode);
    }

    public boolean transferTo(Account destination, double amount) {
        if (destination == null || destination == this) {
            System.out.println("Invalid destination account.");
            return false;
        }
        if (!withdraw(amount, "Transfer to " + destination.getMaskedAccountNumber())) {
            System.out.println("Transfer failed.");
            return false;
        }
        destination.deposit(amount, "Transfer from " + getMaskedAccountNumber());
        return true;
    }

    private void recordTransaction(String type, double amount, String details) {
        transactionCount++;
        transactionHistory.append(String.format("%3d. %-8s Rs.%10.2f   Balance: Rs.%10.2f   (%s)%n",
                transactionCount, type, amount, balance, details));
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }

    public String searchTransactions(String searchText) {
        String searchWord = searchText.trim().toLowerCase();
        StringBuilder result = new StringBuilder();
        if (searchWord.isEmpty()) {
            return "";
        }
        for (String line : transactionHistory.toString().split("\\R")) {
            if (line.toLowerCase().contains(searchWord)) {
                result.append(line).append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    public void displayAccount() {
        System.out.println("Bank: " + bankName + " | " + getAccountType() + " Account "
                + getMaskedAccountNumber() + " | Holder: " + holderName
                + " | Balance: Rs." + formatAmount(balance));
    }

    public static String formatAmount(double amount) {
        if (amount == Math.floor(amount)) {
            return String.valueOf((long) amount);
        }
        return String.format("%.2f", amount);
    }
}
