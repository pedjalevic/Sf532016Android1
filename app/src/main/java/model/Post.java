package model;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post implements Serializable, Comparable<Post>{
    private int id;
    private boolean deleted;

    private String title;
    private String description;
    private Bitmap photo;
    private User author;
    private Date date = new Date();
    private Location location;
    private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private int likes;
    private int dislikes;

    public Post(){

    }

    public Post(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Post(String title, String description, Date date){
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Post(String title, String description, Date date, int likes, int dislikes){
        this.title = title;
        this.description = description;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    @Override
    public int compareTo(Post other) {
        return date.compareTo(other.getDate());
    }
    public int getPopularity(){
        return likes - dislikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Comment> getComments() {
        ArrayList<Comment> retval = new ArrayList<>();
        for(Comment comment : comments){
            if(!comment.isDeleted())
                retval.add(comment);
        }
        return retval;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}