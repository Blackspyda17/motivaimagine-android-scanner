package com.motivaimagine.snscanner.scanner_serial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.motivaimagine.snscanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main extends AppCompatActivity {

    @BindView(R.id.ln_left)
    LinearLayout _ln_left;
    @BindView(R.id.ln_right)
    LinearLayout _ln_right;
    @BindView(R.id.til_left_implant)
    TextInputLayout _til_left_SN;
    @BindView(R.id.til_right_implant)
    TextInputLayout _til_right_SN;
    @BindView(R.id.til_left_vcode)
    TextInputLayout _til_left_vcode;
    @BindView(R.id.til_right_vcode)
    TextInputLayout _til_right_vcode;
    @BindView(R.id.edt_left_serial)
    EditText _left_serial;
    @BindView(R.id.edt_left_vcode)
    EditText _left_vcode;
    @BindView(R.id.edt_right_serial)
    EditText _right_serial;
    @BindView(R.id.edt_right_vcode)
    EditText _right_vcode;

    @BindView(R.id.toolbar_b)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title_b)
    TextView _toolbar_title;



    private static final String SERIAL = "serial";
    private static final String VC = "vc";


    private static final int REQUEST_GET_SERIAL_L = 1;
    private static final int REQUEST_GET_SERIAL_R = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



         /*   getSupportActionBar().setTitle(null);*/

        }
        _toolbar_title.setText("Implant Registration");

        _left_serial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (_left_serial.getRight() - _left_serial.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        startScanner(REQUEST_GET_SERIAL_L);
                        return true;
                    }
                }
                return false;
            }
        });

        _right_serial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (_right_serial.getRight() - _right_serial.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        startScanner(REQUEST_GET_SERIAL_R);
                        return true;
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_SERIAL_L && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                _left_serial.setText(extras.getString(SERIAL));
                _left_vcode.setText(extras.getString(VC));
            }
            //get data from extras
        }

        if (requestCode == REQUEST_GET_SERIAL_R && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                _right_serial.setText(extras.getString(SERIAL));
                _right_vcode.setText(extras.getString(VC));
            }
            //get data from extras
        }


        if (requestCode == REQUEST_GET_SERIAL_L && resultCode == Activity.RESULT_FIRST_USER) {

            Intent intent = new Intent(Main.this, ScannerSerial.class);
            startActivityForResult(intent, REQUEST_GET_SERIAL_L);

            //get data from extras
        }

        if (requestCode == REQUEST_GET_SERIAL_R && resultCode == Activity.RESULT_FIRST_USER) {

            Intent intent = new Intent(Main.this, ScannerSerial.class);
            startActivityForResult(intent, REQUEST_GET_SERIAL_R);

            //get data from extras
        }
    }
    private void startScanner(int requestGetSerial) {
        Intent intent = new Intent(Main.this, ScannerSerial.class);
        startActivityForResult(intent, requestGetSerial);
    }

}
