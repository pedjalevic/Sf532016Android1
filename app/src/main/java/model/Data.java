package model;

import java.util.ArrayList;
import java.util.Date;

public abstract class Data {

    public static ArrayList<Post> posts = new ArrayList<>();

    static {

        Post post = new Post("Naslov 00", "Deskrip", new Date(2018 - 1900, 7, 13), 0, 12);
        Post post1 = new Post("Naslov 1", "Deskrip", new Date(2012 - 1900, 7, 21), 11, 4);
        Post post2 = new Post("Naslov 2", "Deskrip", new Date(2011 - 1900, 11, 3), 12, 1);
        Post post3 = new Post("Naslov 3", "Deskrip", new Date(2018 - 1900, 10, 22), 22, 1);
        Post post4 = new Post("Naslov 4", "Deskrip", new Date(2013 - 1900, 4, 25), 32, 89);
        Post post5 = new Post("Naslov 5", "Deskrip", new Date(2011 - 1900, 10, 21), 21, 13);
        Post post6 = new Post("Naslov 6", "Deskrip", new Date(2019 - 1900, 1, 3), 400, 5);
        Post post7 = new Post("Naslov 7", "Deskrip", new Date(2081 - 1900, 3, 2), 12, 6);

        post.addComment(new Comment("Prvi komentar", "podoapsdasoda dasd asdas da ", new Date(2018, 12 ,2), 12, 2));
        post.addComment(new Comment("Drugi komentar", "podoapsdasoda dasd asdas da ", new Date(2018, 12 ,3), 55, 7));
        post.addComment(new Comment("Treci komentar", "podoapsdasoda dasd asdas da ", new Date(2018, 12 ,4), 19, 1));

        posts.add(post);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);


    }
}
