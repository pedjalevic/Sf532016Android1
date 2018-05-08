package com.example.asus.sf_53_2016_android;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import model.Post;

public class ReadPostFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myInflatedView = inflater.inflate(R.layout.fragment_read_post, container,false);
////////////
        Post post = (Post) getArguments().getSerializable("post");
////////////////
        TextView postTitle = (TextView) myInflatedView.findViewById(R.id.postTitle);
        postTitle.setText(post.getTitle());

        TextView postDescription = (TextView) myInflatedView.findViewById(R.id.postDescription);
        postDescription.setText(post.getDescription());

        TextView postDate = (TextView) myInflatedView.findViewById(R.id.postDate);
        String dateString = post.getDate().toString().substring(3, 16);
        postDate.setText(dateString);

        return myInflatedView;
    }


}