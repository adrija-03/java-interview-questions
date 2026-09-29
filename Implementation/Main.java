package Implementation;

public class Main {
    public static void main(String[] args) {
        //encapsulation
        // BankAccount myAccount = new BankAccount("Adrija", 1000);
        // System.out.println("Account Holder : " + myAccount.getAccountHolder());
        // System.out.println("Account Balance : " + myAccount.getBalance());

        // myAccount.deposit(500.0);
        // myAccount.withdraw(200.0);

        //abstraction
        Account myAccount = new BankAccount("Adrija", 280976);
        System.out.println("Balance : " + myAccount.getBalance());
        myAccount.deposit(500);

        System.out.println("Final Balance : " + myAccount.getBalance());
    }
}
