package com.example.uditsetia.udit_getmyparking_assignment;

/**
 * Created by uditsetia on 15/02/17.
 */

public class Sms {
  private String id;
  private String address;
  private String msg;
  private int readState;
  private String time;
  private String folderName;
  private boolean spam;

  public String getId () {
    return id;
  }

  public String getAddress () {
    return address;
  }

  public String getMsg () {
    return msg;
  }

  public int getReadState () {
    return readState;
  }

  public String getTime () {
    return time;
  }

  public String getFolderName () {
    return folderName;
  }


  public void setId (String id) {
    this.id = id;
  }

  public void setAddress (String address) {
    this.address = address;
  }

  public void setMsg (String msg) {
    this.msg = msg;
  }

  public void setReadState (int readState) {
    this.readState = readState;
  }

  public void setTime (String time) {
    this.time = time;
  }

  public void setFolderName (String folderName) {
    this.folderName = folderName;
  }

  public boolean isSpam () {
    return spam;
  }

  public void setSpam (boolean spam) {
    this.spam = spam;
  }
}