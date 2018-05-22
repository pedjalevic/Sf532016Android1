package util;

import java.util.List;

import model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostService {
    @GET("posts")
    Call<List<Post>> getAll(

    );

    @GET("posts/{postId}")
    Call<Post> getOne(
            @Path("postId") int postId
    );
}
