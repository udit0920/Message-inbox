package com.example.uditsetia.udit_getmyparking_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by uditsetia on 15/02/17.
 */

public class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
  private static final String TAG = "MyAdapter";
  private List<Sms> dataList;
  private LayoutInflater layoutInflater;

  public MyAdapter (Context context, List<Sms> dataList) {
    layoutInflater = LayoutInflater.from(context);
    this.dataList = dataList;
  }

  @Override
  public int getCount () {
    return dataList.size();
  }

  @Override
  public Sms getItem (int position) {
    if (position < dataList.size())
      return dataList.get(position);
    return null;
  }

  @Override
  public long getItemId (int position) {
    return position;
  }

  @Override
  public View getView (int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null)
      view = layoutInflater.inflate(R.layout.adapter_layout, parent, false);

    String number = getItem(position).getAddress();
    String time = getItem(position).getTime();
    String S = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Timestamp(Long.parseLong(time)));

    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
    checkBox.setTag(position);
    checkBox.setChecked(getItem(position).isSpam());
    checkBox.setOnCheckedChangeListener(this);

    if (getItem(position).isSpam())
      view.findViewById(R.id.type).setVisibility(View.VISIBLE);

    TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
    TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
    TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
    tv_number.setText(number);
    tv_time.setText(S);
    tvMessage.setText(getItem(position).getMsg());
    return view;
  }

  @Override
  public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
    int position = (int) buttonView.getTag();
    getItem(position).setSpam(isChecked);
    if (isChecked)
      ((ViewGroup) buttonView.getParent()).findViewById(R.id.type).setVisibility(View.VISIBLE);
    else
      ((ViewGroup) buttonView.getParent()).findViewById(R.id.type).setVisibility(View.INVISIBLE);
  }
}
