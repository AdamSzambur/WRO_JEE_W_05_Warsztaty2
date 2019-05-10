package pl.coderslab.tables;

public class Group {
    private int id;
    private String name;

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

    public Group(String name, int ratingAccess, int solutionAccess) {
        this.name = name;
    }

    public Group(String name) {
        this.name = name;
    }

    public Group() {
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + "\'";
    }
}
