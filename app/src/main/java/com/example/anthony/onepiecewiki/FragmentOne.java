package com.example.anthony.onepiecewiki;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


//TODO: Get the data from Google API
public class FragmentOne extends Fragment {

    private TextView outputText;
    private Button pushButton;
    private boolean isPushButtonClear = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        outputText = view.findViewById(R.id.responseText);

        pushButton = view.findViewById(R.id.button);
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPushButtonClear) {
                    outputText.setText("Button was clicked");
                    isPushButtonClear = false;
                }
                else {
                    outputText.setText("Push the button");
                    isPushButtonClear = true;
                }

            }
        });
    }
}
