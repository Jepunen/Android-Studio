package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText console;
    EditText btlIndex;
    TextView textSeek;
    TextView moneyAmount;
    SeekBar seekAmount;
    Spinner sodaType;
    Spinner sodaSize;
    Context context = null;
    Bottle lastBought = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        console = (EditText) findViewById(R.id.bottleConsole);
        btlIndex = (EditText) findViewById(R.id.bottleIndex);
        textSeek = (TextView) findViewById(R.id.seekText);
        moneyAmount = (TextView) findViewById(R.id.moneyAmount);
        seekAmount = (SeekBar) findViewById(R.id.seekAmount);
        seekAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textSeek.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sodaType = (Spinner) findViewById(R.id.sodaType);
        sodaSize = (Spinner) findViewById(R.id.sodaSize);

        fillSpinners();

        context = MainActivity.this;

    }

    BD dispenser = BD.getInstance();
    private final ArrayList<Bottle> bottles = dispenser.getArray();

    public void addMoney(View v) {
        dispenser.addMoney(seekAmount.getProgress());
        seekAmount.setProgress(1);
        moneyAmount.setText(String.valueOf(dispenser.getMoney()));
    }

    @SuppressLint("SetTextI18n")
    public void buyBottle(View v) {

        if (dispenser.getBottles() <= 0) {
            console.setText("Machine out of bottles!");
            return;
        }

        int indexOfBottle = 1;

        if (TextUtils.isEmpty((btlIndex.getText().toString()))) {
            int index = 0;
            for (Bottle item : bottles) {
                String selectedSize = sodaSize.getSelectedItem().toString();
                if ( sodaType.getSelectedItem().toString() == item.getName() ) {
                    if ( Double.parseDouble(selectedSize) == item.getBottle_size() ) {

                        if ( dispenser.getMoney() < item.getBottle_price() ) {
                            console.setText("Add money first!");
                            return;
                        }

                        int objIndex = bottles.indexOf(item);
                        console.setText("KACHUNK, a wild " + dispenser.buyBottle(objIndex) + " appeared.");
                        dispenser.removeBottle(objIndex);
                        moneyAmount.setText(String.valueOf(dispenser.getMoney()));
                        lastBought = item;
                        return;
                    }
                }
                index++;
            }
            console.setText(sodaType.getSelectedItem().toString() + " - " + sodaSize.getSelectedItem().toString() + "\non valitettavasti loppu.");


        } else {

            String text = btlIndex.getText().toString();
            indexOfBottle = Integer.parseInt(text);

            if ((indexOfBottle > bottles.size()) || indexOfBottle < 1) {
                console.setText(indexOfBottle + " is an invalid bottle number.");
            } else if (dispenser.getMoney() < bottles.get(indexOfBottle - 1).getBottle_price()) {
                console.setText("Add money first!");
            } else {
                console.setText("KACHUNK, a wild " + dispenser.buyBottle(indexOfBottle - 1) + " appeared.");
                dispenser.removeBottle(indexOfBottle - 1);
                moneyAmount.setText(String.valueOf(dispenser.getMoney()));
                lastBought = bottles.get(indexOfBottle - 1);
            }
        }
    }


    @SuppressLint("SetTextI18n")
    public void withdraw(View v) {
        console.setText("Amount of money returned: " + dispenser.getMoney());
        dispenser.returnMoney();
        moneyAmount.setText("0");
    }

    public void listBottles(View v) {

        console.setText("");

        for ( int i = 1; i <= dispenser.getBottles(); i++ ) {
            console.append(i + ". Name: " + bottles.get(i - 1).getName());
            console.append("\nSize: " + bottles.get(i - 1).getBottle_size() + ", Price: " + bottles.get(i - 1).getBottle_price() + "\n");
            console.append("--------------------------------\n");
        }
    }

    private void fillSpinners() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> size = new ArrayList<String>();

        names.add("Pepsi Max");
        names.add("Coca-Cola Zero");
        names.add("Fanta Zero");

        size.add("0.5");
        size.add("1.5");

        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        ArrayAdapter<String> sizesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, size);

        sodaType.setAdapter(namesAdapter);
        sodaSize.setAdapter(sizesAdapter);
    }

    @SuppressLint("SetTextI18n")
    public void tulostaKuitti(View v) {

        String fileName = "kuitti.txt";

        try {

            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(String.valueOf(fileName), Context.MODE_PRIVATE));

            osw.write(String.valueOf(lastBought.getName() + " / " + lastBought.getBottle_size() + " / " + lastBought.getBottle_price()));
            osw.close();

        } catch (IOException e) {
            Log.e("Error", "Virhe syötteessä");
        } finally {
            console.setText("Kuitti tulostettu: " + context.getFilesDir());
        }

    }
}