package com.example.uditsetia.udit_getmyparking_assignment.defaultSms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by uditsetia on 16/02/17.
 */
public class HeadlessSmsSendService extends Service {

  @Override
  public IBinder onBind (Intent intent) {
    return null;
  }
}
