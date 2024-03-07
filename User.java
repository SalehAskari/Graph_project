import java.util.Set;

public class User {
    private int id;
    private String name;
    private String dateOfBirth;
    private String universityLocation;
    private String field;
    private String workplace;
    private Set<String> specialties;
    private Set<Integer> connectionId;

    public User() {
        id = 0;
        name = "";
        dateOfBirth = "";
        universityLocation = "";
        field = "";
        workplace = "";
        specialties = null;
        connectionId = null;
    }

    public User(int id, String name, String dateOfBirth, String universityLocation, String field, String workplace, Set<String> specialties, Set<Integer> connectionId) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.universityLocation = universityLocation;
        this.field = field;
        this.workplace = workplace;
        this.specialties = specialties;
        this.connectionId = connectionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUniversityLocation() {
        return universityLocation;
    }

    public void setUniversityLocation(String universityLocation) {
        this.universityLocation = universityLocation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public Set<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<String> specialties) {
        this.specialties = specialties;
    }

    public Set<Integer> getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Set<Integer> connectionId) {
        this.connectionId = connectionId;
    }
}
