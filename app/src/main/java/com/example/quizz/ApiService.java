package com.example.quizz;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/quiz-api/quizes")
    Call<List<Question>> getQuestions();

    @FormUrlEncoded
    @POST("quiz-api/post-quiz.php")
    Call<ApiResponse> postQuestion(
            @Field("question") String question,
            @Field("q_type") String questionType,
            @Field("options") String options,
            @Field("answer") String answer,
            @Field("points") String points,
            @Field("dif_level") String difficultyLevel,
            @Field("posted_by") String postedBy
    );
}

