package com.example.asus.sf_53_2016_android;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import adapters.CommentAdapter;
import model.Comment;
import model.Post;

public class ReadCommentsFragment extends Fragment {

    private Post post;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_read_comments, container,false);

        post = (Post) getArguments().getSerializable("post");

        ArrayList<Comment> comments = post.getComments();

        sortCommentsByPreference(comments);

        CommentAdapter adapter = new CommentAdapter(getActivity(), comments);
        listView = myInflatedView.findViewById(R.id.listViewComments);
        listView.setAdapter(adapter);

        return myInflatedView;
    }

    @Override
    public void onResume() {
        super.onResume();

        post = (Post) getArguments().getSerializable("post");

        ArrayList<Comment> comments = post.getComments();

        sortCommentsByPreference(comments);

        CommentAdapter adapter = new CommentAdapter(getActivity(), comments);
        listView.setAdapter(adapter);
    }

    public void sortCommentsByPreference(ArrayList<Comment> comments){
        // PO cemu da sortiramo ???  ?? ? ?
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortPostsBy = sp.getString("lpSortCommentsBy", "default123");

        // Sortiramo
        if(sortPostsBy.equals("Date")) {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment comment2, Comment comment1) {
                    return comment1.getDate().compareTo(comment2.getDate());
                }
            });
        } else if (sortPostsBy.equals("Popularity")) {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment comment2, Comment comment1) {
                    if(comment1.getPopularity() > comment2.getPopularity()){
                        return 1;
                    } else if (comment1.getPopularity() < comment2.getPopularity()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Sorting went wrong, posts unsorted!\n" + sortPostsBy, Toast.LENGTH_LONG).show();
        }
    }
}