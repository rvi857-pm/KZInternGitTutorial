public class EntryLevel extends Intern {
    private int salary;

    public EntryLevel(String name, int year) {
        super(name, false);
        super.gradYear = year;
    }

    public EntryLevel(String name) {
        super(name, false);
        super.gradYear = 2022;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    } 

    public int getSalary() {
        return this.salary;
    }


    
}