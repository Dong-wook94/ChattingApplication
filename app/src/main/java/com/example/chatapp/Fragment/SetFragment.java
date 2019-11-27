package com.example.chatapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Activity.LoginActivity;
import com.example.chatapp.Data.AppDataInfo;
import com.example.chatapp.R;


public class SetFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button logoutBtn;
    private TextView useridTextView;
    private SharedPreferences auto;


    public SetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auto = getActivity().getSharedPreferences(AppDataInfo.Login.key, Activity.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        logoutBtn = view.findViewById(R.id.logout_btn);
        useridTextView = view.findViewById(R.id.userid_textview);

        useridTextView.setText(auto.getString(AppDataInfo.Login.userID,null));
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }
    private void logout() {
        startActivity(new Intent(getActivity(), LoginActivity.class));

        SharedPreferences.Editor editor = auto.edit();

        editor.clear();
        editor.commit();
        Toast.makeText(getActivity(), "로그아웃", Toast.LENGTH_SHORT).show();
        editor = auto.edit();
        editor.putBoolean("checkbox",false);
        editor.commit();
        getActivity().finish();
    }


}
