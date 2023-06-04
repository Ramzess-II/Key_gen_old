package com.example.keygen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public Button but;
    public CheckBox auto_manual;
    public EditText inpt_text;
    public TextView out_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but = findViewById(R.id.button);
        auto_manual = findViewById(R.id.checkBox2);
        inpt_text = findViewById(R.id.text_inpt);
        out_text = findViewById(R.id.textView);
        inpt_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {  // слушатель клавиатуры для ввода
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    transmit_password();
                    //return true;  //если раскоментировать то клавиатура не будет пропадать
                }
                return false;
            }
        });
    }

    public void generate(View view) {
        key_no_see();
        transmit_password();
    }

    public void check(View view) {
        if (auto_manual.isChecked()) {
            inpt_text.setText("");
            auto_manual.setText("Manual");
            key_no_see();
            //inpt_text.setEnabled(false);
            //inpt_text.setHint("");
            inpt_text.setVisibility(View.GONE);
        } else {
            inpt_text.setVisibility(View.VISIBLE);
            //inpt_text.setEnabled(true);
            //inpt_text.setHint("Data/mount");
            auto_manual.setText("Auto");
        }
    }

    public void transmit_password() {
        if (auto_manual.isChecked()) {
            Calendar calendar = Calendar.getInstance();
            int mount = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);
            int code = day * 100;
            code += (mount + 1);
            code ^= 0xFFF;
            String text = String.format("%04d", code);
            out_text.setText(text);
        } else {
            String str_value = inpt_text.getText().toString();
            if (!str_value.equals("") && (str_value.length() > 3)) {
                int data = Integer.parseInt(str_value);
                data ^= 0xFFF;
                str_value = String.format("%04d", data);
                out_text.setText(str_value);
            } else {
                out_text.setText("ERR");
            }
            inpt_text.setText("");
        }
    }

    public void key_no_see() {
        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }
}

