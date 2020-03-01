package Assignment1_200380188;

public class BankAccountRunner {

    public static void main(String[] args)
    {
    BankAccount b1 = new BankAccount("linda kettle", 123456,0, BankAccount.AccountType.TFSA);
    //b1.deposit(2000);
    b1.deposit(10);
    b1.deposit(100);
    b1.deposit(2000);

    //System.out.println(b1);
    }
}
