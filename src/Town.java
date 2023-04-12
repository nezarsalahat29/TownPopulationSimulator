
public class Town{
    private int citizens;
    public Town(int init){
        citizens=init;
    }
    public int getCitizens(){
        return citizens;
    }
    public void add(int numberOfBirth){
        citizens+=numberOfBirth;
    }
    public void remove(int numberOfDeath){
        citizens-=numberOfDeath;
    }
}