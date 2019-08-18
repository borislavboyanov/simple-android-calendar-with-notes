package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditNotes extends AppCompatActivity {

    TextView dateNotes;
    Button update;
    Button cancel;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        dateNotes = findViewById(R.id.dateNotes);
        update = findViewById(R.id.update);
        cancel = findViewById(R.id.cancel);

        db = new DB(this);

        dateNotes.setText(db.loadNotes(this, getIntent().getStringExtra("user"), getIntent().getStringExtra("date")));
        dateNotes.setEnabled(true);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateNotes(EditNotes.this, dateNotes.getText().toString(), getIntent().getStringExtra("user"), getIntent().getStringExtra("date"));

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
