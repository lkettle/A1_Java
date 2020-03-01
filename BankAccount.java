package Assignment1_200380188;

/*  Asks for 1 file only, no accountRunner or user interface */

import java.text.NumberFormat;
import java.util.Locale;

public class BankAccount {

    // instance variables
    private String accountName = "";
    private int accountNumber = 000000;
    private double accountBalance = 0.0;
    public enum AccountType {CHECKING, DEFAULT, RSP, SAVING, TFSA} ;
    AccountType accountType = AccountType.DEFAULT;
    final static byte WITHDRAWAL_LIMIT = 3;
    final double DEPOSIT_LIMIT = 1000.00;
    private double depositTotal = 0;
    private int counter = 0;

    // to format display of account balance to currency
    Locale locale = new Locale("en", "CA");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    // GETTERS
    public String getAccountName() { return accountName; }
    public int getAccountNumber() { return accountNumber;}
    public double getAccountBalance() { return accountBalance; }
    public byte getWithdrawalLimit() {return WITHDRAWAL_LIMIT; }
    public double getDepositLimit() {return DEPOSIT_LIMIT - depositTotal; }
    public int getCounter() { return counter;}
    public AccountType getAccountType() {return accountType;}

    // SETTERS

    private boolean setAccountBalance(double value) {
            accountBalance += value;
            if (accountBalance >= 0) { return true;}
            else {return false;}
       }

    public boolean setAccountName(String accountName)
    {
        // account name must be at least 3 spaces, with minimum 1 space and maximum 1 hyphen
        int nameLength = accountName.length();
        int hyphenCount = accountName.length() - accountName.replace("-", "").length();
        boolean containsSpace = accountName.contains(" ");
        if ((nameLength > 2) && containsSpace && (hyphenCount < 2))
            {
            this.accountName = accountName;
            return true;
            }
        else
            {
                System.out.println("Incorrect name format.");
                return false;
            }
    }

    public boolean setAccountNumber(int accountNumber)
    {
        // account number must be a positive integer and six digits long
        if ((accountNumber > 99999) && (accountNumber < 1000000))
            {
                this.accountNumber = accountNumber;
                return true;
            }
        else
            {
                System.out.println("Incorrect account number format.");
                return false;
            }
    }

    // at creation of new account set initial balance at name length*10
    // when no name given in constructor
    public void setInitialBalance ()
    {
        // without a name, will return default, so accountBalance = 0
        int startNumber = getAccountName().length();
        this.accountBalance = startNumber * 10.0;
    }

    // at creation of new account set initial balance at name length*10
    // when a name is given in constructor
    public void setInitialBalance (String accountName)
    {
        // uses account name from client if acceptable
        if (setAccountName(accountName))
        {
            int startNumber = getAccountName().length();
            this.accountBalance = startNumber * 10.0;
        }
    }

    // CLASS METHODS
    public boolean withdrawal(double amount)
    {
        if ((amount > 0) && (counter < 3) && (getAccountBalance() - amount >=0) )
            {
                setAccountBalance(-amount);
                counter ++;
                System.out.println("Withdrawal Number: " + counter);
                System.out.println("You have withdrawn: " + currencyFormatter.format(amount));
                System.out.println("Your new balance is:  " + currencyFormatter.format(getAccountBalance()) + "\n");
                return true;
            }
        else if (getAccountBalance() - amount <0)
            {
                int withdrawalsRemaining = 3 - counter;
                System.out.println("We cannot process this withdrawal request as it exceeds your account balance of:  " + currencyFormatter.format(getAccountBalance()));
                System.out.println("Your remaining number of withdrawals this month is:   " + withdrawalsRemaining + "\n");
                return false;
            }
        else {
            if (counter > 2)
                {
                    System.out.println("Your withdrawal could not be processed as you have exceeded the monthly withdrawal limit.\n");
                }
            else
                {
                    System.out.println("A negative withdrawal cannot be processed.\n");
                }
            return false;
        }
    }

    public boolean deposit(double amount) {
        if (amount > 0)
            {
                depositTotal += amount;
            }
        else
            {
            System.out.println("Deposit must be > zero.\n");
            return false;
            }

        if((amount > 0) && (depositTotal <= DEPOSIT_LIMIT))
        {
            setAccountBalance(amount);
            System.out.println("Your deposit is: \t\t\t" + currencyFormatter.format(amount));
            System.out.println("Your new balance is:  \t" + currencyFormatter.format(getAccountBalance()) + "\n");
            return true;
        }
        else
        {
            double depositRemaining = DEPOSIT_LIMIT - getAccountBalance();
            System.out.println("You have exceeded your deposit limit of :\t" + currencyFormatter.format(DEPOSIT_LIMIT));
            System.out.println("Your deposit limit remaining is :\t\t\t\t\t" + currencyFormatter.format(depositRemaining));
            return false;
        }
    }

    @Override
    public String toString() {
        return "Account Type: \t\t" + accountType + "\n" +
                "Account Name:  \t\t" + accountName + "\n" +
                "Account Number:  \t" + accountNumber + "\n" +
                "Account Balance:  \t$" + accountBalance + "\n" ;
    }

    @Override
    public boolean equals(Object obj)
    {
        //if they are one and the same
        if(this == obj)
            return true;

        // if object is not null or is bank account class
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;

        // cast the object~ seems redundant but OK
        BankAccount BankAccount = (BankAccount) obj;

        // comparing the state of argument with 'this' Object.
        return ((BankAccount.accountName == this.accountName) &&
                (BankAccount.accountNumber == this.accountNumber) &&
                (BankAccount.accountType == this.accountType) &&
                (BankAccount.accountBalance == this.accountBalance));
    }


        // CONSTRUCTORS ~ TOTAL = 16 = 1 + 4 + 6 + 4  + 1
        // Default = 0 parameters 4C0 = 1

    public BankAccount()
        {
            setInitialBalance();
        }

        // 4 parameters 4C4 = 1

    public BankAccount(String accountName, int accountNumber, double accountBalance, AccountType accountType)
        {
            if (setAccountName(accountName) && setAccountNumber(accountNumber) && setAccountBalance(accountBalance)) {
                setAccountName(accountName);
                setInitialBalance(accountName);
                setAccountNumber(accountNumber);
                setAccountBalance(accountBalance);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

        // 3 parameters 4C3 = 4

    public BankAccount(String accountName, int accountNumber, double accountBalance)
        {
            if (setAccountName(accountName) && setAccountNumber(accountNumber) && setAccountBalance(accountBalance)) {
                setAccountName(accountName);
                setInitialBalance(accountName);
                setAccountNumber(accountNumber);
                setAccountBalance(accountBalance);
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount(String accountName, int accountNumber, AccountType accountType)
        {
            if (setAccountName(accountName) && setAccountNumber(accountNumber)) {
                setAccountName(accountName);
                setInitialBalance(accountName);
                setAccountNumber(accountNumber);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount(String accountName, double accountBalance, AccountType accountType)
        {
            if (setAccountName(accountName) && setAccountBalance(accountBalance)) {
                setAccountName(accountName);
                setInitialBalance();
                setAccountBalance(accountBalance);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount( int accountNumber, double accountBalance, AccountType accountType)
        {
            if (setAccountNumber(accountNumber) && setAccountBalance(accountBalance)) {
                setInitialBalance();
                setAccountNumber(accountNumber);
                setAccountBalance(accountBalance);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

        // 2 parameters 4C2 = 6

    public BankAccount(String accountName, int accountNumber)
        {
            if (setAccountName(accountName) && setAccountNumber(accountNumber)) {
                setAccountName(accountName);
                setInitialBalance(accountName);
                setAccountNumber(accountNumber);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount(String accountName, AccountType accountType)
        {
            if (setAccountName(accountName)) {
                setAccountName(accountName);
                setInitialBalance(accountName);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount(String accountName, double accountBalance)
        {
            if (setAccountName(accountName) && setAccountBalance(accountBalance)) {
                setAccountName(accountName);
                setInitialBalance(accountName);
                setAccountBalance(accountBalance);
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount( int accountNumber, AccountType accountType)
        {
            if (setAccountNumber(accountNumber)) {
                setInitialBalance();
                setAccountNumber(accountNumber);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount( int accountNumber, double accountBalance)
        {
            if (setAccountNumber(accountNumber) && setAccountBalance(accountBalance)) {
                setInitialBalance();
                setAccountNumber(accountNumber);
                setAccountBalance(accountBalance);
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount( double accountBalance, AccountType accountType)
        {
            if (setAccountBalance(accountBalance)) {
                setInitialBalance();
                setAccountBalance(accountBalance);
                this.accountType = accountType;
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

        // 1 parameter 4C1 = 4

    public BankAccount(String accountName)
        {
            if (setAccountName(accountName))
            {
                setAccountName(accountName);
                setInitialBalance(accountName);
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount( int accountNumber)
        {
            if (setAccountNumber(accountNumber)) {
                setInitialBalance();
                setAccountNumber(accountNumber);
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount( double accountBalance)
        {
            if (setAccountBalance(accountBalance)) {
                setInitialBalance();
                setAccountBalance(accountBalance);
            } else {
                System.out.println("We were unable to set up your account.  Please try again.");
            }
        }

    public BankAccount(AccountType accountType)
        {
            setInitialBalance();
            this.accountType = accountType;
        }
    }


