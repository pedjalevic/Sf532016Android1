package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.asus.sf_53_2016_android.Gadgets;
import com.example.asus.sf_53_2016_android.R;

import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.CommentService;
import util.UtilService;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context context;

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comments, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvTitle);
        TextView tvHome = convertView.findViewById(R.id.tvDescription);
        TextView tvDate = convertView.findViewById(R.id.tvPostDate);
        final TextView tvPopularity = convertView.findViewById(R.id.tvPopularity);
        TextView tvAuthor = convertView.findViewById(R.id.tvPosterName);
        // Populate the data into the template view using the data object
        tvName.setText(comment.getTitle());
        tvHome.setText(comment.getDescription());
        tvDate.setText(comment.getDate().toString().substring(3,16));
        tvAuthor.setText((comment.getAuthor() != null) ? comment.getAuthor().getUsername() : "noatuhor");

        // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
        tvPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);
        tvPopularity.setText(Integer.toString(comment.getPopularity()));


        final ImageButton btnLike = convertView.findViewById(R.id.btnCommentLike);
        final ImageButton btnDislike = convertView.findViewById(R.id.btnCommentDislike);

        btnLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int userId = getContext().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
                if(userId == comment.getAuthor().getId()){
                    Gadgets.showSimpleOkDialog(getContext(), "Ne mozete lajkovati svoj komentar!!!");
                    return;
                }

                if(btnLike.getBackground().getConstantState() == getContext().getResources().getDrawable(R.drawable.like).getConstantState()){
                    CommentService commentService = UtilService.retrofit.create(CommentService.class);
                    Call<ResponseBody> call = commentService.likeComment(comment.getId());
                    // ProgressDialog
                    //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            response.body().close();

                            comment.like();
                            tvPopularity.setText(Integer.toString(comment.getPopularity()));
                            // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                            tvPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);
                            btnLike.setBackground(getContext().getResources().getDrawable(R.drawable.like_pressed));

                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = getContext().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
                if(userId == comment.getAuthor().getId()){
                    Gadgets.showSimpleOkDialog(getContext(), "Ne mozete dislajkovati svoj komentar!!!");
                    return;
                }

                if(btnDislike.getBackground().getConstantState() == getContext().getResources().getDrawable(R.drawable.dislike).getConstantState()){
                    CommentService commentService = UtilService.retrofit.create(CommentService.class);
                    Call<ResponseBody> call = commentService.dislikeComment(comment.getId());
                    // ProgressDialog
                    //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            response.body().close();

                            comment.dislike();
                            tvPopularity.setText(Integer.toString(comment.getPopularity()));
                            // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                            tvPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);
                            btnDislike.setBackground(getContext().getResources().getDrawable(R.drawable.dislike_pressed));
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }



    private void btnLikePressed() {

    }

    private void btnDislikePressed() {
    }
}
