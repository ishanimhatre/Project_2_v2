package banking;

public class CollegeChecking extends Checking{

    private Campus campus; //campus code
    public CollegeChecking(Profile holder, double balance, Campus campus){
        super(holder, balance);
        this.campus = campus;
    }

    @Override
    public double monthlyFee() {
        return 0.0;
    }

    @Override
    public int compareTo(Account account) {
        if(account instanceof CollegeChecking){
            return super.compareTo(account);
        }
        else {
            return account.getClass().getName().compareTo(this.getClass().getName());
        }
    }
}

