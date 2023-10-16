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

    public Profile getHolder() {
        return holder;
    }

    public void setHolder(Profile holder) {
        this.holder = holder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public abstract double monthlyInterest();
    public abstract double monthlyFee();

    @Override
    public int compareTo(Account account) { //comparing accounts by their holders
        return account.holder.compareTo(this.holder);
    }

    @Override
    public abstract String toString();
}
