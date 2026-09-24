package Implementation;

public class Main {
    public static void main(String[] args) {
        BankAccount myAccount = new BankAccount("Adrija", 1000);
        System.out.println("Account Holder : " + myAccount.getAccountHolder());
        System.out.println("Account Balance : " + myAccount.getBalance());

        myAccount.deposit(500.0);
        myAccount.withdraw(200.0);

        System.out.println("Final Balance : " + myAccount.getBalance());
    }
}
