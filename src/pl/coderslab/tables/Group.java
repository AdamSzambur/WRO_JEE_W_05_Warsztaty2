package pl.coderslab.tables;

public class Group {
    private int id;
    private String name;
    private int ratingAccess;
    private int solutionAccess;

    public int getRatingAccess() {
        return ratingAccess;
    }

    public void setRatingAccess(int ratingAccess) {
        this.ratingAccess = ratingAccess;
    }

    public int getSolutionAccess() {
        return solutionAccess;
    }

    public void setSolutionAccess(int solutionAccess) {
        this.solutionAccess = solutionAccess;
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

    public Group(String name, int ratingAccess, int solutionAccess) {
        this.name = name;
        this.ratingAccess = ratingAccess;
        this.solutionAccess = solutionAccess;
    }

    public Group(String name) {
        this.name = name;
    }

    public Group() {
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\''+
                ", rating_access=" + ratingAccess +
                ", solution_access=" + solutionAccess;
    }
}
