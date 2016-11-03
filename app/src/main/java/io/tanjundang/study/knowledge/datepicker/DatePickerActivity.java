package io.tanjundang.study.knowledge.datepicker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.DateFormatTool;

public class DatePickerActivity extends BaseActivity implements View.OnClickListener {

    Button btnDate;
    Calendar selectedDate = Calendar.getInstance();
    Calendar currentDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_date_picker);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(this);
        btnDate.setText(DateFormatTool.getDefaultDateStrFormat(currentDate.getTimeInMillis())); //默认显示当前日期
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        if (v.equals(btnDate)) {
            showDateDialog();
        }
    }

    public void showDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                修改日期
                selectedDate.set(year, monthOfYear, dayOfMonth);
                btnDate.setText(DateFormatTool.getDefaultDateStrFormat(selectedDate.getTimeInMillis()));
            }
        };

//       初始化Dialog日期,Dialog弹出时显示的日期
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, listener, year, month, day);
        //设置Dialog的最大日期为当前日期
        dialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());
        //显示对话框
        dialog.show();
    }
}
