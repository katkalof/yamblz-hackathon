package ru.katkalov.yamblz.hackathon.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.katkalov.yamblz.hackathon.R;

public class CollectWordFragment extends Fragment {
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


        return view;
    }
}
