public class SummerIntern extends Intern {
    
    public SummerIntern(String name, int year) {
        super(name, false);
        super.gradYear = year;
    }

    public SummerIntern(String name) {
        super(name, false);
        super.gradYear = 2022;
    }
}