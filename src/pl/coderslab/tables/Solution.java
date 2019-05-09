package pl.coderslab.tables;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private int exercise_id;
    private int users_id;
    private int rating;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }


    @Override
    public String toString() {
        return "id=" + id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", exercise_id=" + exercise_id +
                ", users_id=" + users_id +
                ", rating ='" + rating + "'" +
                ", comment ='" + comment + "'";
    }

    public Solution(String created, String updated, String description, int exercise_id, int users_id) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;
        this.users_id = users_id;
    }

    public Solution() {
    }


}
