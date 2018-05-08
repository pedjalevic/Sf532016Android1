package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.sf_53_2016_android.R;
import com.example.asus.sf_53_2016_android.ReadPostActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Post;

public class PostAdapter  extends ArrayAdapter<Post>{

    private Context context;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvTitle);
        TextView tvHome = convertView.findViewById(R.id.tvDescription);
        TextView tvDate = convertView.findViewById(R.id.tvPostDate);
        TextView tvPopularity = convertView.findViewById(R.id.tvPopularity);
        // Populate the data into the template view using the data object
        tvName.setText(post.getTitle());
        tvHome.setText(post.getDescription());
        //tvDate.setText(post.getDate().toString().substring(0,10) + post.getDate().toString().substring(23,29) );
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(post.getDate());
        tvDate.setText(strDate);

        String popularity = Integer.toString(post.getPopularity());
        if (post.getPopularity()> 0){
            tvPopularity.setTextColor(Color.GREEN);
        } else if(post.getPopularity() < 0){
            tvPopularity.setTextColor(Color.RED);
        }
        tvPopularity.setText(popularity);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReadPostActivity.class);
                intent.putExtra("post", post);
                context.startActivity(intent);
            }
        });



        // Return the completed view to render on screen
        return convertView;
    }
}
