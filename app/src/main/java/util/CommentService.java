package util;

import java.util.List;

import model.Comment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentService {

    @GET("comments")
    Call<List<Comment>> getAll(

    );

    @POST("comments")
    @Headers("Content-type: application/json")
    Call<Comment> createComment(
            @Body Comment comment
    );

    @PATCH("comments/{commentId}/like")
    Call<ResponseBody> likeComment(
            @Path("commentId") int commentId
    );

    @PATCH("comments/{commentId}/dislike")
    Call<ResponseBody> dislikeComment(
            @Path("commentId") int commentId
    );
}
