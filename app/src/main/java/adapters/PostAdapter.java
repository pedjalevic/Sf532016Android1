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
import java.util.List;

import model.Post;

public class PostAdapter  extends ArrayAdapter<Post>{

    private Context context;

    public PostAdapter(Context context, List<Post> posts) {
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
        TextView tvAuthor = convertView.findViewById(R.id.tvPosterName);
        // Populate the data into the template view using the data object
        tvName.setText(post.getTitle());
        tvHome.setText(post.getDescription());
        //tvDate.setText(post.getDate().toString().substring(0,10) + post.getDate().toString().substring(23,29) );
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        String strDate = (post.getDate() != null) ? dateFormat.format(post.getDate()) : "nodate";
        tvDate.setText(strDate);

        // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
        tvPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);
        tvPopularity.setText(Integer.toString(post.getPopularity()));

        tvAuthor.setText(post.getAuthor().getUsername());

/*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadPostActivity.class);
                //Toast.makeText(context, )
                intent.putExtra("post", post);
                int i = 0;
                context.startActivity(intent);
            }
        });
*/


        // Return the completed view to render on screen
        return convertView;
    }
}
