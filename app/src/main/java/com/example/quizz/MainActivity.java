package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button postButton;
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postButton = findViewById(R.id.postButton);
        recyclerView = findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize question list
        questionList = new ArrayList<>();

        // Check if there's a question list from PostActivity
        String jsonQuestionList = getIntent().getStringExtra("questionList");
        if (jsonQuestionList != null) {
            Gson gson = new Gson();
            Type questionListType = new TypeToken<ArrayList<Question>>() {}.getType();
            questionList = gson.fromJson(jsonQuestionList, questionListType);
        }

        // Set up adapter for RecyclerView
        questionAdapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(questionAdapter);

        // Button to open PostActivity for posting a new question
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
    }
}
