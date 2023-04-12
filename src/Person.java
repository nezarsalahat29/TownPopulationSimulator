public class Person {

    private final int deathYear;

    public Person(int birthYear, int age) {
        this.deathYear = birthYear +age;
    }

    public int getDeathYear() {
        return deathYear;
    }

}