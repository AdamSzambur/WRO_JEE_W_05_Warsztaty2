package pl.coderslab.tables;

public class GroupPrivileges {
    int id;
    int groupId;
    int solution;
    int rating;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public GroupPrivileges() {
    }

    public GroupPrivileges(int groupId, int solution, int rating) {
        this.groupId = groupId;
        this.solution = solution;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return  ", solution=" + solution +
                ", rating=" + rating;
    }
}
