package ru.katkalov.yamblz.hackathon.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.katkalov.yamblz.hackathon.R;

public class MainFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        CardView b = (CardView) rootView.findViewById(R.id.to_word_comp);
        b.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (view.getId()) {
            case R.id.to_word_comp:
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_frame_layout, new CollectWordFragment())
                            .addToBackStack(null)
                            .commit();
                break;
        }
    }
}
