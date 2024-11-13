package com.example.quizz;

import static android.graphics.Color.GREEN;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import kong.unirest.HttpResponse;
//import kong.unirest.Unirest;


public class PostActivity extends AppCompatActivity {
    private TextView text1, text2, text3, text4;
    private EditText edtQuest, optionA, optionB, optionC, optionD; // Renamed for clarity
    private Button btnmcq,btnoneword,btndescribe,btncancel,btnpost,btnAsk;
    private List<Question> questionList;
//    private long lastTapTime = 0;
    private boolean isPressed=false;
    private static final long DOUBLE_TAP_DELAY = 500; // Milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        text1 = findViewById(R.id.textV1);
        text2 = findViewById(R.id.textV2);
        text3 = findViewById(R.id.textV3);
        text4 = findViewById(R.id.textV4);
        btnmcq=findViewById(R.id.btnMCQ);
        btnoneword=findViewById(R.id.btnOneWord);
        btndescribe=findViewById(R.id.btnDescribe);
        btnAsk=findViewById(R.id.btnGemini);
        edtQuest = findViewById(R.id.etQuestion);
        optionA = findViewById(R.id.btnOptnA); // Use proper IDs for options
        optionB = findViewById(R.id.btnOptnB);
        optionC = findViewById(R.id.btnOptnC);
        optionD = findViewById(R.id.btnOptnD);
        btnpost = findViewById(R.id.btnPost);

        // Set initial background color to purple and input type to prevent accidental editing
        optionA.setBackgroundColor(getResources().getColor(R.color.purple));
        optionB.setBackgroundColor(getResources().getColor(R.color.purple));
        optionC.setBackgroundColor(getResources().getColor(R.color.purple));
        optionD.setBackgroundColor(getResources().getColor(R.color.purple));

        optionA.setInputType(InputType.TYPE_NULL);
        optionB.setInputType(InputType.TYPE_NULL);
        optionC.setInputType(InputType.TYPE_NULL);
        optionD.setInputType(InputType.TYPE_NULL);

        // Apply single and double tap listeners to options
        applyTouchListener(optionA);
        applyTouchListener(optionB);
        applyTouchListener(optionC);
        applyTouchListener(optionD);

        btnmcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed == false) {
                    btnmcq.setBackgroundColor(getResources().getColor(R.color.grey));
                    isPressed = true;
                } else {
                    btnmcq.setBackgroundColor(getResources().getColor(R.color.purple));
                    isPressed = false;
                }
            }
        });
        btnoneword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed == false) {
                    btnoneword.setBackgroundColor(getResources().getColor(R.color.grey));
                    isPressed = true;
                } else {
                    btnoneword.setBackgroundColor(getResources().getColor(R.color.purple));
                    isPressed = false;
                }
            }
        });
        btndescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed == false) {
                    btndescribe.setBackgroundColor(getResources().getColor(R.color.grey));
                    isPressed = true;
                } else {
                    btndescribe.setBackgroundColor(getResources().getColor(R.color.purple));
                    isPressed = false;
                }
            }
        });

        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showDialog();
            }
        });



        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postQuestionToApi();
//                fetchQuestionsFromApi();
            }
        });

    }
    private void postQuestionToApi() {
        String questionText = edtQuest.getText().toString().trim();
        String optionsText = optionA.getText().toString().trim() + "," +
                optionB.getText().toString().trim() + "," +
                optionC.getText().toString().trim() + "," +
                optionD.getText().toString().trim();

        ApiService apiService = ApiClient.getApiService();
        Call<ApiResponse> call = apiService.postQuestion(
                questionText,
                "mcq", // Example type
                optionsText,
                "resistive forces", // Example answer
                "5", // Example points
                "expert", // Example difficulty level
                "admin" // Example user
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(PostActivity.this, "Question posted successfully", Toast.LENGTH_SHORT).show();
                        // Optionally, navigate to another activity
                    } else {
                        Toast.makeText(PostActivity.this, "Failed to post question: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PostActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void fetchQuestionsFromApi() {
//        ApiService apiService = ApiClient.getApiService();
//        Call<List<Question>> call = apiService.getQuestions();
//
//        call.enqueue(new Callback<List<Question>>() {
//            @Override
//            public void onResponse(Call<List<Question>> call,Response<List<Question>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Question>questionList = response.body();
//
//                    // Create a new Question object
//                    Question question = new Question();
//                    question.setQuestion(edtQuest.getText().toString().trim());
//
//                    List<String> options = new ArrayList<>();
//                    options.add(optionA.getText().toString().trim());
//                    options.add(optionB.getText().toString().trim());
//                    options.add(optionC.getText().toString().trim());
//                    options.add(optionD.getText().toString().trim());
//                    question.setOptions(options);
//
//                    // Pass the question object to MainActivity
//                    Intent intent = new Intent(PostActivity.this, MainActivity.class);
//                    Gson gson = new Gson();
//                    intent.putExtra("question", gson.toJson(question));
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(PostActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Question>> call, Throwable t) {
//                Toast.makeText(PostActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    private void applyTouchListener(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            private long lastTapTime = 0; // Declare lastTapTime locally

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    long currentTime = System.currentTimeMillis();

                    if (currentTime - lastTapTime < DOUBLE_TAP_DELAY) {
                        // Double tap detected
                        // Enable editing without changing the background color
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        editText.requestFocus();
                        editText.setSelection(editText.length());
                        editText.setBackgroundColor(getResources().getColor(R.color.purple));
                    } else {
                        // Single tap detected
                        // Change background color to green
                        editText.setBackgroundColor(getResources().getColor(R.color.green));
                        // Set input type to prevent accidental editing on single tap
                        editText.setInputType(InputType.TYPE_NULL);
                    }
                    lastTapTime = currentTime; // Update lastTapTime for next comparison
                    return true;
                }
                return false;
            }
        });
    }
    private void showDialog()

    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_askgemini);
        dialog.show();

    }




}