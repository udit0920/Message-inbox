package com.example.uditsetia.udit_getmyparking_assignment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.uditsetia.udit_getmyparking_assignment.R;

/**
 * Created by uditsetia on 15/02/17.
 */

public class ShowMessageActivity extends AppCompatActivity {


  @Override
  protected void onCreate (@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.show_message);

    String msg = getIntent().getStringExtra("msg");
    String address = getIntent().getStringExtra("address");
    TextView tvMessage = (TextView) findViewById(R.id.tv_msg);
    TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
    tvMessage.setText(msg);
    tvAddress.setText(address);
  }
}
