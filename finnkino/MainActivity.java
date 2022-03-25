package com.example.finnkino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner area;

    Button aikaMin;
    Button aikaMax;
    private String minHour = "00", minMinute = "00", maxHour = "23", maxMinute = "59";

    CalendarView kalenteri;
    private int year, month, day;

    ListView filmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        area = (Spinner) findViewById(R.id.theatre);
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillTitles();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        aikaMin = (Button) findViewById(R.id.timeMin);
        aikaMax = (Button) findViewById(R.id.timeMax);
        kalenteri = (CalendarView) findViewById(R.id.calender);
        filmit = (ListView) findViewById(R.id.filmit);

        Calendar today = Calendar.getInstance();

        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        kalenteri.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {

                year = y;
                month = m;
                day = d;

                fillTitles();
            }
        });
        fillArea();
        fillTitles();
    }

    Brains brains = Brains.getInstance();

    private void fillArea() {

        brains.downloadXML("https://www.finnkino.fi/xml/TheatreAreas/");
        ArrayList<Theatre> theatres = brains.getArray();

        ArrayList<String> names = new ArrayList<String>();

        for ( Theatre temp : theatres ) {
            names.add(temp.getName());
        }

        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, R.layout.my_selected_item, names);
        namesAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        area.setAdapter(namesAdapter);
        fillTitles();
    }

    private void fillTitles() {

        getMoviesToArray();
        ArrayList<Movie> movies = brains.getArrayMovies();

        clearTitles();

        ArrayList<String> names = new ArrayList<String>();

        for ( Movie temp : movies ) {
            if ( !names.contains(temp.getName()) ) {

                String time = temp.getStartTime();
                int intTime = Integer.parseInt(time.replace(":", ""));

                int min = Integer.parseInt(String.valueOf(minHour) + String.valueOf(minMinute));
                int max = Integer.parseInt(String.valueOf(maxHour) + String.valueOf(maxMinute));

                if ( (intTime > min) && (intTime < max) ) {
                    names.add(temp.getName());
                }
            }
        }

        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, R.layout.my_selected_item, names);
        namesAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        filmit.setAdapter(namesAdapter);
    }

    public void clearTitles () {

        ArrayList<String> names = new ArrayList<String>();

        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, R.layout.my_selected_item, names);
        namesAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        filmit.setAdapter(namesAdapter);
    }

    public void aikaMinPicker(View v) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                int intMinHour = hours;
                int intMinMinute = minutes;
                minMinute = String.format(Locale.getDefault(), "%02d", intMinMinute);
                minHour = String.format(Locale.getDefault(), "%02d", intMinHour);

                aikaMin.setText(String.format(Locale.getDefault(), "%02d:%02d", intMinHour, intMinMinute));
                fillTitles();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.themeOnverlay_timePicker, onTimeSetListener, Integer.parseInt(minHour), Integer.parseInt(minMinute), true);
        timePickerDialog.setTitle("Valitse aikaisin elokuvan alkamis aika");
        timePickerDialog.show();
    }

    public void aikaMaxPicker(View v) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

                int intMaxHour = hours;
                int intMaxMinute = minutes;
                maxMinute = String.format(Locale.getDefault(), "%02d", intMaxMinute);
                maxHour = String.format(Locale.getDefault(), "%02d", intMaxHour);

                int min = Integer.parseInt(String.valueOf(minHour) + String.valueOf(minMinute));
                int max = Integer.parseInt(String.valueOf(maxHour) + String.valueOf(maxMinute));

                if ( max < min ) {
                    intMaxHour = Integer.parseInt(String.valueOf(minHour));
                    intMaxMinute = Integer.parseInt(String.valueOf(minMinute));
                    maxHour = minHour;
                    maxMinute = minMinute;
                }

                aikaMax.setText(String.format(Locale.getDefault(), "%02d:%02d", intMaxHour, intMaxMinute));
                fillTitles();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.themeOnverlay_timePicker, onTimeSetListener, Integer.parseInt(maxHour), Integer.parseInt(maxMinute), true);
        timePickerDialog.setTitle("Valitse myöhäisin elokuvan alkamis aika");
        timePickerDialog.show();
    }

    public void getMoviesToArray() {
        String selected = area.getSelectedItem().toString();
        int id = brains.getID(selected);
        brains.getMovies(id, year, month, day);
    }
}