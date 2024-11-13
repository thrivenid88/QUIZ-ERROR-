package com.example.quizz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



public class AskGeminiActivity extends AppCompatActivity {
    private EditText editTextAsk;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askgemini);

        editTextAsk = findViewById(R.id.editextask);
        listView = findViewById(R.id.listView);

        // Set up the adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionList);
        listView.setAdapter(adapter);

        // Set listener for the "Done" key on the keyboard
        editTextAsk.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Get the question from EditText
                    String prompt = editTextAsk.getText().toString().trim();
                    if (!TextUtils.isEmpty(prompt)) {
                        generateQuizQuestion(prompt);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void generateQuizQuestion(String prompt) {
        // Replace with your actual API key stored in BuildConfig
        String apiKey = BuildConfig.apiKey;  // Ensure you've set your API key here
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        // Create content for the request
        Content content = new Content.Builder().addText(prompt).build();

        // Set up executor
        Executor executor = Executors.newSingleThreadExecutor();

        // Send the request to generate content
        ListenableFuture<GenerateContentResponse> responseFuture = model.generateContent(content);

        // Handle the response asynchronously
        Futures.addCallback(
                responseFuture,
                new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String question = result.getText();
                        runOnUiThread(() -> {
                            // Update ListView with the generated question
                            questionList.add(question);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        runOnUiThread(() -> {
                            // Show error message in ListView if API call fails
                            questionList.add("Error: " + t.getMessage());
                            adapter.notifyDataSetChanged();
                        });
                    }
                },
                executor
        );
    }
}
