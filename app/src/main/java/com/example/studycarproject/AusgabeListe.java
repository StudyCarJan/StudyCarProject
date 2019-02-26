package com.example.studycarproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AusgabeListe extends AppCompatActivity {

    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ausgabe_liste);


        tl = (TableLayout)findViewById(R.id.myTableLayout);


        //createRow();
        Button bu = (Button)findViewById(R.id.my_table_button);
        bu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                createRow();
            }
        });
    }

    public void createRow(){
        /* Create a new row to be added. */
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /* Create a Button to be the row-content. */
        Button b = new Button(this);
        b.setText("Karte");

        Button c = new Button(this);
        c.setText("E-Mail");

        //DividerItemDecoration d = new DividerItemDecoration(this,5);
        //b.setLayoutParams(new LayoutParams(
        //      LayoutParams.FILL_PARENT,
        //      LayoutParams.WRAP_CONTENT));

        /* Add Button to row. */
        tr.addView(b);
        tr.addView(c);
        //tr.addView(d);

        /* Add row to TableLayout.
        tl.addView(tr,new TableLayout.LayoutParams(
                  LayoutParams.FILL_PARENT,
                  LayoutParams.WRAP_CONTENT));*/

        tl.addView(tr);
    }



}
