package lab06.bank.account;




public class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(String holderName, double initialDeposit, double overdraftLimit) {
        super(holderName, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    
    @Override
    public boolean withdraw(double amount, String withdrawalMode) {
        if (!isValidAmount(amount)) {
            return false;
        }
        if (getBalance() - amount < -overdraftLimit) {
            System.out.println("Withdrawal denied: overdraft limit of Rs." + formatAmount(overdraftLimit)
                    + " exceeded. You can withdraw up to Rs."
                    + formatAmount(getBalance() + overdraftLimit) + ".");
            return false;
        }
        debit(amount, withdrawalMode);
        if (getBalance() < 0) {
            System.out.println("Note: account is in overdraft by Rs." + formatAmount(-getBalance()) + ".");
        }
        return true;
    }

    @Override
    public void displayAccount() {
        super.displayAccount();
        System.out.println("      Overdraft limit: Rs." + formatAmount(overdraftLimit)
                + " | Available to withdraw: Rs." + formatAmount(getBalance() + overdraftLimit));
    }
}
