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
    private String authorUsername;
    private Date date;
    private double longitude;
    private double latitude;
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

    public void like(){
        this.likes++;
    }

    public void dislike(){
        this.dislikes++;
    }

    /**
     * Metoda koja setuje location polje objekta koristeci polja longitude i latitude
     */
    public void generateLocation(){
        Location location = new Location("ja provajdovo");
        location.setLongitude(this.longitude);
        location.setLatitude(this.latitude);
        this.location = location;
    }

    @Override
    public int compareTo(Post other) {
        return date.compareTo(other.getDate());
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

    public double getLongitude(){
        return this.longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;

        // AKO SETUJEMO LOCATION SETUJE SE I LOGITUDE I NATLALTLTA
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public void makeLocation(double latitude, double longitude){

    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Filters comments by deleted attribute
     * @return returns a list of not-deleted comments
     */
    public ArrayList<Comment> getComments() {
        ArrayList<Comment> retval = new ArrayList<>();
        for(Comment comment : comments){
            if(!comment.isDeleted())
                retval.add(comment);
        }
        return retval;
    }

    /**
     * Adds a comment to the post
     * @param comment
     */
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