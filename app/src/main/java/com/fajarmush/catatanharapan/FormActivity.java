package com.fajarmush.catatanharapan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

public class FormActivity extends AppCompatActivity {

    EditText formTitle, formDesc;
    Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ActionBar actionBar = getSupportActionBar();
        formTitle = findViewById(R.id.formTitle);
        formDesc = findViewById(R.id.formDesc);
        btnDelete =findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);
        btnDelete.setText(getIntent().getStringExtra("button"));

        if (getIntent().getStringExtra("action").equals("edit")) {
            actionBar.setTitle("Edit Note");
            formTitle.setText(getIntent().getStringExtra("title"));
            formDesc.setText(getIntent().getStringExtra("description"));
        } else {
            actionBar.setTitle("New Note");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String currentDate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(date);

                DatabaseHelper database = new DatabaseHelper(FormActivity.this);
                if (getIntent().getStringExtra("action").equals("edit")) {
                    database.edit(getIntent().getStringExtra("id"),formTitle.getText().toString().trim(), formDesc.getText().toString().trim(), currentDate);
                } else {
                    database.insert(formTitle.getText().toString().trim(), formDesc.getText().toString().trim(), currentDate);
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("action").equals("edit")) {
                    modal();
                } else {
                    finish();
                }
            }
        });
    }
    void modal(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + getIntent().getStringExtra("title") + " ?");
        builder.setMessage("Are you sure want to delete " + getIntent().getStringExtra("title") + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper database = new DatabaseHelper(FormActivity.this);
                database.delete(getIntent().getStringExtra("id"));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Nothing
            }
        });
        builder.create().show();
    }
}