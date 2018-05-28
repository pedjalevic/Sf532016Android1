package model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
    private int id;
    private boolean deleted;

    private String title;
    private String description;
    private User author;
    private Date date;
    private Post post;
    private int likes;
    private int dislikes;

    public Comment(){

    }

    public Comment(String title, String description, Date date, int likes, int dislikes){
        this.title = title;
        this.description = description;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public void like(){
        this.likes++;
    }

    public void dislike(){
        this.dislikes++;
    }

    /**
     * Metoda kojom dobijamo 'popularnost' odredjenog posta.
     * Posto u projektnoj specifikaciji popularnost kao takva nije definisana, definisem je kao razlika lajkova i dislajkova
     *
     * @return integer - razlika lajkova i dislajkova
     */
    public int getPopularity(){
        return likes - dislikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
