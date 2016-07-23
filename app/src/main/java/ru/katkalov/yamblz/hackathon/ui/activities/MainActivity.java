package ru.katkalov.yamblz.hackathon.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.katkalov.yamblz.hackathon.R;
import ru.katkalov.yamblz.hackathon.ui.fragments.MainFragment;
import ru.katkalov.yamblz.hackathon.ui.logic.JSONExtractor;

public class MainActivity extends AppCompatActivity {
    public static List<String> wordsToLearn = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new MainFragment())
                    .commit();
        }
        try {
            JSONExtractor extractor = new JSONExtractor(getAssets().open("words.json"));
            wordsToLearn = extractor.getWordsFromJSon("en");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
