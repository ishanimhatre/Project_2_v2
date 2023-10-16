package inheritanceprojectfiles;

public class Checking extends Account{

    public class Checking extends Account{

    private static final double INTEREST_RATE = 1.0;
    private static final int MONTHLY_FEE = 12;

    public Checking(){

    }
    public Checking(Profile holder, double balance){
        super(holder, balance);
    }

    @Override
    public double monthlyFee() {
        if(balance>= 1000)
            return 0;
        else return MONTHLY_FEE;
    }

    @Override
    public double monthlyInterest() {
        return ((balance*(INTEREST_RATE/100))/12);
    }

    @Override
    public int compareTo(Account account) {
        if(account instanceof Checking){
            return super.compareTo(account);
        }
       else {
           return account.getClass().getName().compareTo(this.getClass().getName());
        }
    }
    @Override
    public String toString(){
        return "College Checking: : " +holder.toString() + ": : Balance $" + balance;
    }
}
