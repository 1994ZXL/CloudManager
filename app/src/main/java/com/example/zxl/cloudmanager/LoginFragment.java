package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {
    private TextView usernameTextView, passwordTextView;
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.login_fragment, parent, false);

        usernameTextView = (TextView)v.findViewById(R.id.loginFragment_username_textview);
        passwordTextView = (TextView)v.findViewById(R.id.loginFragment_password_textview);
        usernameEditText = (EditText)v.findViewById(R.id.loginFragment_username_edittext);
        passwordEditText = (EditText)v.findViewById(R.id.loginFragment_password_edittext);

        loginButton = (Button)v.findViewById(R.id.loginFragment_loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }
}
