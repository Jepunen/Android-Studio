package com.example.finnkino;

public class Movie {

    private final String name;
    private final String startTime;

    public Movie (String n, String t) {
        name = n;
        startTime = t;
    }

    public String getName() { return name; }

    public String getStartTime() {

        String time = "";

        if ( startTime.length() > 5 ) {
            time = startTime.substring(startTime.length() - 8);
            return time.substring(0, time.length() - 3);
        } else {
            return startTime;
        }
    }
}
