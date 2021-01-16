
public class Main {
    public static void main(String[] args) {
        Intern srikar = new Intern("Srikar", 2021);
        Intern intern1 = new SummerIntern("Sameer");
        System.out.println("Srikar graduates in " + srikar.yearsTilGraduation(2021) + "years."); 
        System.out.println("Sameer graduates in " + intern1.yearsTilGraduation(2021) + "years."); 
        intern1 = new EntryLevel("Drisya");
        ((EntryLevel) intern1).setSalary(2400);
        EntryLevel x = (EntryLevel) srikar;
        x.setSalary(2500);
        System.out.println("Srikar's salary is " + x.getSalary());
        int[] y = new int[5];
        for (int i = 0; i < 5; i++) {
            y[i] = 4;
        }
        Intern[] interns = new Intern[3];
        for (int i = 0; i < 3; i++) {
            interns[i] = new Intern();
        }

        int[] newarr = {8, 10, 5, 12, 14};
        Intern[] thedudes = {srikar, intern1, x};

    }

    public static int addition(int arg1, int arg2) {
        return arg1 + arg2;
    }
}
/*
static = this method doesn't belong to any class
this = referring to that specific object's field
extends = this class is a subclass of another class
super = refers to parent class
    - super() calls constructor of parent class
    - super.X refers to field in parent class

primitives:

Integer Data Types:
Takes little storage, but has limited range
byte - 1 byte
short - 2 bytes
int - 4 bytes
long - 8 bytes
Has huge range, but storage suffers

Decimal Data Types:
float - 4 bytes
double - 8 bytes

Character Data Types:
char - 1 byte: static
String - object that stores multiple chars: static


BigInteger()
BigDecimal()


boolean = true/false




method = function
access modifier:
public - that field inside that object can be accessed by anyone
protected - that field can be accessed by objects of child classes
private - that field cannot by accessed by anyone except other
functions in that class
*/