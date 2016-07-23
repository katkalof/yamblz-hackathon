package ru.katkalov.yamblz.hackathon.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ru.katkalov.yamblz.hackathon.R;
import ru.katkalov.yamblz.hackathon.ui.activities.MainActivity;
import ru.katkalov.yamblz.hackathon.ui.logic.YaTranslator;

public class CollectWordFragment extends Fragment {
    List<String> collectTaskWords = new ArrayList<>();
    int collectTaskWordsAmount = 10;
    int currStep = 0;
    String direction = "en-ru";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_collect_word, container, false);


        ImageView close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("MainFragment");
                if (fragment == null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_frame_layout, new MainFragment(), "AboutFragment")
                            .commit();
                }
                else {
                    fragmentManager.beginTransaction().replace(R.id.main_frame_layout, fragment, "AboutFragment").commit();
                }

            }
        });

        generateWordsForTask();
        fillTheWord(inflater, container, view);
        ImageView skipNext = (ImageView) view.findViewById(R.id.skip_next);
        skipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clieckedView) {
                if (currStep < collectTaskWordsAmount - 1) {
                    currStep++;
                    //fillTheWord(view);
                }
            }
        });
        return  view;
    }

    private void fillTheWord(LayoutInflater inflater, ViewGroup container, View view) {
        TextView wordView = (TextView) view.findViewById(R.id.word);
        String currWord = collectTaskWords.get(currStep);
        wordView.setText(currWord);

        String wordTranslated = translateWord(direction, currWord);

        FlexboxLayout flexboxLayout = (FlexboxLayout) view.findViewById(R.id.letters_input);

        CardView cardView = (CardView) view.findViewById(R.id.letters_output);
        String output = "";

        char[] letters = wordTranslated.toCharArray();
        int i = 0;
        for (final char letter : letters) {
            View childLayout = inflater.inflate(R.layout.item_char, flexboxLayout);
            childLayout.setTag(i);

//            <TextView
//            android:id="@+id/letter"
//            android:layout_width="wrap_content"
//            android:layout_height="wrap_content"
//            android:layout_gravity="center"
//            android:text="A"
//            android:textSize="24sp" />

//            TextView textView = new TextView(this);
//            textView.setText(textArray[i]);
//            linearLayout.addView(textView);

//TODO здесь нужно добавить букву в кардвью и обработать

//            childLayout.addChildrenForAccessibility(new ArrayList<View>().add((textView)));
            childLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //output += letter;
                }
            });
            i++;
        }

        /*TextView attemptView = (Tex
        tView) view.findViewById(R.id.attempt);
        attemptView.setText(wordTranslated);
        char[] letters = wordTranslated.toCharArray();
        */
    }

    private boolean checkTheWord(String attempt, String translation) {
        if (attempt == null || translation == null) return false;
        return attempt.equals(translation);
    }

    private void generateWordsForTask() {
        List<String> wordsToLearn = MainActivity.wordsToLearn;
        int wordsAmount = wordsToLearn.size();
        if (wordsAmount < collectTaskWordsAmount) {
            return;
        }
        int i = 0;
        int random;
        while (i < collectTaskWordsAmount) {
            random = (int) (Math.random() * wordsAmount);
            if (!collectTaskWords.contains(wordsToLearn.get(random))) {
                collectTaskWords.add(wordsToLearn.get(random));
                i++;
            }
        }
        return;
    }

    private String translateWord(String direction, String word) {
        YaTranslator yaTranslator = new YaTranslator();
        String[] params = {getString(R.string.key_ya_dictionary), direction, word};
        yaTranslator.execute(params);

        String wordTranslated = "";
        try {
            Map<String, String> result = yaTranslator.get();
            wordTranslated = result.get("translated");
        } catch (InterruptedException ex) {

        } catch (ExecutionException ex) {

        }
        Log.e("HACATHON", wordTranslated);

        return wordTranslated;
    }
}
