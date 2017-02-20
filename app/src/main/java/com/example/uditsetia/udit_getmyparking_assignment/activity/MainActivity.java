package com.example.uditsetia.udit_getmyparking_assignment.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.uditsetia.udit_getmyparking_assignment.MyAdapter;
import com.example.uditsetia.udit_getmyparking_assignment.R;
import com.example.uditsetia.udit_getmyparking_assignment.Sms;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
  private static final String TAG = "MainActivity";
  private static final int READ_SMS_PERMISSION_REQ_CODE = 515;
  ListView listView;
  MyAdapter adapter;
  List<Sms> smsList;

  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    listView = (ListView) findViewById(R.id.lv_one);
    smsList = new ArrayList<>();
    adapter = new MyAdapter(this, smsList);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(this);
    findViewById(R.id.btn_delete).setOnClickListener(this);
    fetchAllSms();
  }

  private boolean makeThisAppDefault () {
    if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT)
      return true;
    final String myPackageName = getPackageName();
    if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {
      Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
      intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName);
      startActivityForResult(intent, 516);
      return false;
    }
    return true;
  }

  private void deleteMessage (Sms sms) {
    Uri message = Uri.parse("content://sms/");
    ContentResolver cr = this.getContentResolver();
    int d = cr.delete(message, "_id=?", new String[]{sms.getId()});
  }

  private void startRequiredActivity (String msg, String address) {
    Intent intent = new Intent(MainActivity.this, ShowMessageActivity.class);
    intent.putExtra("msg", msg);
    intent.putExtra("address", address);
    startActivity(intent);
  }

  private void fetchAllSms () {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSION_REQ_CODE);
      return;
    }
    new AsyncTask<Void, Void, Void>() {

      @Override
      protected Void doInBackground (Void... params) {
        Uri message = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(message, null, null, null, null);
        startManagingCursor(cursor);

        if (cursor != null && cursor.moveToFirst()) {
          int totalSMS = cursor.getCount();
          smsList.clear();
          for (int i = 0; i < totalSMS; i++) {
            Sms sms = new Sms();
            sms.setId(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
            sms.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            sms.setMsg(cursor.getString(cursor.getColumnIndexOrThrow("body")));
            sms.setReadState(cursor.getInt(cursor.getColumnIndex("read")));
            sms.setTime(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) {
              sms.setFolderName("inbox");
            }
            smsList.add(sms);
            cursor.moveToNext();
          }
        }
        return null;
      }

      @Override
      protected void onPostExecute (Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.notifyDataSetChanged();
      }
    }.execute();
  }

  @Override
  public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == READ_SMS_PERMISSION_REQ_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
      fetchAllSms();
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    if (requestCode == 516 && resultCode == RESULT_OK)
      deleteAllSpamAndReload();
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onClick (View v) {
    deleteAllSpamAndReload();
  }

  private void deleteAllSpamAndReload () {
    if (!makeThisAppDefault())
      return;
    for (int i = 0; i < smsList.size(); i++) {
      if (smsList.get(i).isSpam()) {
        deleteMessage(smsList.get(i));
      }
    }
    fetchAllSms();
  }

  @Override
  public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
    startRequiredActivity(adapter.getItem(position).getMsg(), adapter.getItem(position).getAddress());
  }
}
