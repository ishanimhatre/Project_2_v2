package banking;

public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    public Account(){

    }

    public Account(Profile holder, double balance){
        this.holder = holder;
        this.balance = balance;
    }
    public abstract double monthlyInterest();
    public abstract double monthlyFee();

    @Override
    public int compareTo(Account account) { //comparing accounts by their holders
        return account.holder.compareTo(this.holder);
    }
}

