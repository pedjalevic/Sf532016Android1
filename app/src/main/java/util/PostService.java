package util;

import java.util.List;

import model.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostService {
    @GET("posts")
    Call<List<Post>> getAll (

    );

    @GET("posts/{postId}")
    Call<Post> getOne(
            @Path("postId") int postId
    );

    @POST("posts")
    @Headers("Content-type: application/json")
    Call<Post> createPost(
            @Body Post post
    );

    @DELETE("posts/{postId}")
    Call<ResponseBody> deletePost(
            @Path("postId") int postId
    );

    @PATCH("posts/{postId}/like")
    Call<ResponseBody> likePost(
            @Path("postId") int postId
    );

    @PATCH("posts/{postId}/dislike")
    Call<ResponseBody> dislikePost(
            @Path("postId") int postId
    );

}

