import java.util.Iterator;
import java.util.Set;

public class Priority {
    private boolean field;
    private boolean universityLocation;
    private int specialTies;
    private boolean workPlace;
    private int degree;
    private int connectionId;

    public Priority() {
        field = false;
        universityLocation = false;
        specialTies = 0;
        workPlace = false;
        degree = 0;
        connectionId = 0;
    }
    public void weight(User enter_1 , User enter_2){
        if (enter_1.getField().equals(enter_2.getField())){
            field = true;
        }
        if (enter_1.getUniversityLocation().equals(enter_2.getUniversityLocation())){
            universityLocation = true;
        }
        Set<String> temp_1 = enter_1.getSpecialties();
        Set<String> temp_2 = enter_2.getSpecialties();
        for (String temp : temp_1){
            if (temp_2.contains(temp)){
                specialTies++;
            }
        }
        Set<Integer> temp1 = enter_1.getConnectionId();
        Set<Integer> temp2 = enter_2.getConnectionId();
        for (int temp : temp1){
            if (temp2.contains(temp)){
                connectionId++;
            }
        }
    }

    public boolean isField() {
        return field;
    }

    public void setField(boolean field) {
        this.field = field;
    }

    public boolean isUniversityLocation() {
        return universityLocation;
    }

    public void setUniversityLocation(boolean universityLocation) {
        this.universityLocation = universityLocation;
    }

    public int getSpecialTies() {
        return specialTies;
    }

    public void setSpecialTies(int specialTies) {
        this.specialTies = specialTies;
    }

    public boolean isWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(boolean workPlace) {
        this.workPlace = workPlace;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public String toString() {
        return "Priority{" +
                "field=" + field +
                ", universityLocation=" + universityLocation +
                ", specialTies=" + specialTies +
                ", workPlace=" + workPlace +
                ", degree=" + degree +
                ", connectionId=" + connectionId +
                '}';
    }
    public int getScore(){
        // Field = 2000
        // UniversityLocation = 1000
        // SpecialTies = 100
        // WorkPlace = 50
        // Degree = -10
        // ConnectionId = 2
        int score = 0;
        if (field) score += 2000;
        if (universityLocation) score += 1000;
        score += specialTies*100;
        if (workPlace) score += 50;
        score -= degree*10;
        score += connectionId*2;
        return score;

    }

}
