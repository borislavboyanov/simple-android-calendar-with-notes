package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

public class CalendarEvents extends AppCompatActivity {

    Spinner month;
    Spinner year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_events);

        month = findViewById(R.id.month);
        year = findViewById(R.id.year);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_dropdown_item);

        month.setAdapter(monthAdapter);
        year.setAdapter(yearAdapter);

        month.setSelection(Calendar.getInstance().get(Calendar.MONTH));
        year.setSelection(Calendar.getInstance().get(Calendar.YEAR) - 1900);

        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch(month.getSelectedItem().toString()) {
                    case "January": RedrawCalendar(Calendar.JANUARY, year.getSelectedItem().toString()); break;
                    case "March": RedrawCalendar(Calendar.MARCH, year.getSelectedItem().toString()); break;
                    case "May": RedrawCalendar(Calendar.MAY, year.getSelectedItem().toString()); break;
                    case "July": RedrawCalendar(Calendar.JULY, year.getSelectedItem().toString()); break;
                    case "August": RedrawCalendar(Calendar.AUGUST, year.getSelectedItem().toString()); break;
                    case "October": RedrawCalendar(Calendar.OCTOBER, year.getSelectedItem().toString()); break;
                    case "December": RedrawCalendar(Calendar.DECEMBER, year.getSelectedItem().toString()); break;

                    case "April": RedrawCalendar(Calendar.APRIL, year.getSelectedItem().toString()); break;
                    case "June": RedrawCalendar(Calendar.JUNE, year.getSelectedItem().toString()); break;
                    case "September": RedrawCalendar(Calendar.SEPTEMBER, year.getSelectedItem().toString()); break;
                    case "November": RedrawCalendar(Calendar.NOVEMBER, year.getSelectedItem().toString()); break;

                    case "February": RedrawCalendar(Calendar.FEBRUARY, year.getSelectedItem().toString()); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch(month.getSelectedItem().toString()) {
                    case "January": RedrawCalendar(Calendar.JANUARY, year.getSelectedItem().toString()); break;
                    case "March": RedrawCalendar(Calendar.MARCH, year.getSelectedItem().toString()); break;
                    case "May": RedrawCalendar(Calendar.MAY, year.getSelectedItem().toString()); break;
                    case "July": RedrawCalendar(Calendar.JULY, year.getSelectedItem().toString()); break;
                    case "August": RedrawCalendar(Calendar.AUGUST, year.getSelectedItem().toString()); break;
                    case "October": RedrawCalendar(Calendar.OCTOBER, year.getSelectedItem().toString()); break;
                    case "December": RedrawCalendar(Calendar.DECEMBER, year.getSelectedItem().toString()); break;

                    case "April": RedrawCalendar(Calendar.APRIL, year.getSelectedItem().toString()); break;
                    case "June": RedrawCalendar(Calendar.JUNE, year.getSelectedItem().toString()); break;
                    case "September": RedrawCalendar(Calendar.SEPTEMBER, year.getSelectedItem().toString()); break;
                    case "November": RedrawCalendar(Calendar.NOVEMBER, year.getSelectedItem().toString()); break;

                    case "February": RedrawCalendar(Calendar.FEBRUARY, year.getSelectedItem().toString()); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void RedrawCalendar(final int changedMonth, final String changedYear) throws DateTimeParseException {
        short numberOfDaysInMonth = 31;
        LinearLayout calendarLayout = findViewById(R.id.calendar);

        for(int i = 0; i < calendarLayout.getChildCount(); i++) {
            LinearLayout childLayout = (LinearLayout) calendarLayout.getChildAt(i);

            for (int j = 0; j < childLayout.getChildCount(); j++) {
                Button tempButton = (Button) childLayout.getChildAt(j);

                tempButton.setVisibility(View.VISIBLE);
            }
        }

        switch(changedMonth) {
            case Calendar.JANUARY: numberOfDaysInMonth = 31; break;
            case Calendar.MARCH: numberOfDaysInMonth = 31; break;
            case Calendar.MAY: numberOfDaysInMonth = 31; break;
            case Calendar.JULY: numberOfDaysInMonth = 31; break;
            case Calendar.AUGUST: numberOfDaysInMonth = 31; break;
            case Calendar.OCTOBER: numberOfDaysInMonth = 31; break;
            case Calendar.DECEMBER: numberOfDaysInMonth = 31; break;

            case Calendar.APRIL: numberOfDaysInMonth = 30; break;
            case Calendar.JUNE: numberOfDaysInMonth = 30; break;
            case Calendar.SEPTEMBER: numberOfDaysInMonth = 30; break;
            case Calendar.NOVEMBER: numberOfDaysInMonth = 30; break;

            case Calendar.FEBRUARY:
                int leapYear = Integer.parseInt(changedYear);
                if (leapYear % 400 == 0) numberOfDaysInMonth = 29;
                else if (leapYear % 100 == 0) numberOfDaysInMonth = 28;
                else if (leapYear % 4 == 0) numberOfDaysInMonth = 29;
                else numberOfDaysInMonth = 28;
                break;
        }

        Calendar checkDayOfWeek = Calendar.getInstance();
        checkDayOfWeek.set(Integer.parseInt(changedYear), changedMonth, 1);
        int dayOfWeek = checkDayOfWeek.get(Calendar.DAY_OF_WEEK) - 1;
        if(dayOfWeek == 0) {
            dayOfWeek = 6;
        } else {
            dayOfWeek--;
        }

        LinearLayout firstWeek = findViewById(R.id.firstWeek);

        for(int i = 0; i < 7; i++) {
            Button tempButton = (Button) firstWeek.getChildAt(i);
            if (i < dayOfWeek) {
                tempButton.setVisibility(View.INVISIBLE);
            } else {
                tempButton.setText(String.valueOf(i - dayOfWeek + 1));
                tempButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tempButton2 = (Button) view;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Integer.parseInt(changedYear), changedMonth, Integer.parseInt(tempButton2.getText().toString()));

                        Intent intent = new Intent(CalendarEvents.this, EditNotes.class);
                        intent.putExtra("user", getIntent().getStringExtra("user"));
                        Toast.makeText(CalendarEvents.this, "Passed date:  " + new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()) + "!", Toast.LENGTH_SHORT).show();
                        intent.putExtra("date", new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
                        startActivity(intent);
                    }
                });
            }
        }

        int currentDayCursor = 7 - dayOfWeek + 1;

        for(int i = 1; i < calendarLayout.getChildCount(); i++) {
            LinearLayout childLayout = (LinearLayout) calendarLayout.getChildAt(i);

            for (int j = 0; j < childLayout.getChildCount(); j++) {
                Button tempButton = (Button) childLayout.getChildAt(j);

                if (currentDayCursor <= numberOfDaysInMonth) {
                    tempButton.setText(String.valueOf(currentDayCursor));
                    tempButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button tempButton2 = (Button) view;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Integer.parseInt(changedYear), changedMonth, Integer.parseInt(tempButton2.getText().toString()));

                            Intent intent = new Intent(CalendarEvents.this, EditNotes.class);
                            intent.putExtra("user", getIntent().getStringExtra("user"));
                            Toast.makeText(CalendarEvents.this, "Passed date:  " + new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()) + "!", Toast.LENGTH_SHORT).show();
                            intent.putExtra("date", new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
                            startActivity(intent);
                        }
                    });
                } else {
                    tempButton.setVisibility(View.INVISIBLE);
                }
                currentDayCursor++;
            }
        }
    }

}
