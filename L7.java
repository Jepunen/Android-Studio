package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    TextView text;
    EditText input;
    EditText inputF;
    EditText fileName;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView);
        input = (EditText) findViewById(R.id.textInput);
        fileName = (EditText) findViewById(R.id.tiedostoNimi);
        inputF = (EditText) findViewById(R.id.multiLineText);

        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                text.setText(input.getText());
            }
        });

        context = MainActivity.this;
        System.out.println("Sijainti = " + context.getFilesDir());
    }

    public void readFile(View v) {
        try {
            InputStream ins = context.openFileInput(String.valueOf(fileName.getText()));
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s = "";

            inputF.setText("");

            while ( (s = br.readLine()) != null ) {
                inputF.append(s + "\n");
            }
            ins.close();
        } catch (IOException e) {
            Log.e("Error", "Virhe syötteessä");
        } finally {
            System.out.println("LUETTU");
        }
    }

    public void writeFile(View v) {
        try {

            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(String.valueOf(fileName.getText()), Context.MODE_PRIVATE));

            osw.write(String.valueOf(inputF.getText()));
            osw.close();

        } catch (IOException e) {
            Log.e("Error", "Virhe syötteessä");
        } finally {
            System.out.println("KIRJOITETTU");
        }
    }

    public void consoleHW(View v) {
        System.out.println("Hello world!");
    }
    public void textHW(View v) {
        text.setText("Hello World!");
    }
    public void setText(View v) {
        text.setText(input.getText());
    }
}
