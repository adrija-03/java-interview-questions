package Implementation;

public class BankAccount implements Account{
    private String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        if(initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
        }
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    @Override 
    public double getBalance() {
        return balance;
    }

    @Override 
    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            System.out.println("Successfully deposited : " + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    @Override 
    public void withdraw(double amount) {
        if(amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.println("Successfully withdrawn : " + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount!");
        }
    }
}
