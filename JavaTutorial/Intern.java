/*
Interns 
    - Summer Interns
    - Entry Level Employees 
*/

public class Intern {
    public String name;
    protected int gradYear;
    private boolean hasTakenSWE;

    //constructor

    public Intern() {
        this.name = "Anonymous";
        this.gradYear = 2022;
        this.hasTakenSWE = true;
    }
    public Intern(String name, boolean hasTakenSWE) {
        this.name = name;
        this.gradYear = 2022;
        this.hasTakenSWE = hasTakenSWE;
    }

    public Intern(String name, int year) {
        this.name = name;
        this.gradYear = year;
        this.hasTakenSWE = (year == 2021);
    }

    public int yearsTilGraduation(int currentYear) {
        return this.gradYear - currentYear;
    }

    public void greeting() {
        System.out.println("Hello! my name is " + name + "and I graduate in " + gradYear + "\n");
    }
}

