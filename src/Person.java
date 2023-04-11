public class Person {
    private final int birthYear;

    private final int age;
    private final int deathYear;

    public Person(int birthYear, int age) {
        this.birthYear = birthYear;
        this.age=age;
        this.deathYear = birthYear +age;
    }


    public int getDeathYear() {
        return deathYear;
    }


    @Override
    public String toString() {
        return "birth: "+birthYear+", age: "+age+", dead at: "+deathYear+" done";
    }
}