package lab05.bank.account;

public class SavingsAccount extends Account {

    private double minimumBalance;
    private double interestRate;

    public SavingsAccount(String holderName, double initialDeposit, double minimumBalance,
            double interestRate) {
        super(holderName, initialDeposit);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public boolean withdraw(double amount, String withdrawalMode) {
        if (!isValidAmount(amount)) {
            return false;
        }
        if (getBalance() - amount < minimumBalance) {
            System.out.println("Withdrawal denied: a minimum balance of Rs."
                    + formatAmount(minimumBalance) + " must be maintained. You can withdraw up to Rs."
                    + formatAmount(Math.max(0, getBalance() - minimumBalance)) + ".");
            return false;
        }
        debit(amount, withdrawalMode);
        return true;
    }

    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }

    public void displayInterest() {
        System.out.println("Interest rate        : " + interestRate + "% per year");
        System.out.println("Current balance      : Rs." + formatAmount(getBalance()));
        System.out.printf("Interest for 1 year  : Rs.%.2f%n", calculateInterest());
        System.out.printf("Interest for 1 month : Rs.%.2f%n", calculateInterest() / 12);
    }

    @Override
    public void displayAccount() {
        super.displayAccount();
        System.out.println("      Minimum balance: Rs." + formatAmount(minimumBalance)
                + " | Interest rate: " + interestRate + "%");
    }
}
