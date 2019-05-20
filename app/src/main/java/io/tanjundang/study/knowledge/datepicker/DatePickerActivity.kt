package io.tanjundang.study.knowledge.datepicker

import android.app.DatePickerDialog
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.common.tools.DateFormatTool
import kotlinx.android.synthetic.main.activity_date_picker.view.*
import java.util.*

/**
 * @Author: TanJunDang
 * @Date: 2019/5/15
 * @Description:
 */
class DatePickerActivity : BaseActivity(), View.OnClickListener {


    private var btnDate: Button? = null
    private var selectedDate = Calendar.getInstance()
    private var currentDate = Calendar.getInstance()

    override fun initView() {
        setContentView(R.layout.activity_date_picker)
        btnDate = findViewById<View>(R.id.btnDate) as Button
        btnDate!!.setOnClickListener(this)
        btnDate!!.text = DateFormatTool.getDefaultDate(currentDate.timeInMillis) //默认显示当前日期
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        if (v!!.equals(btnDate)) {
            showDateDialog()
        }
    }

    fun showDateDialog() {
        var listener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//                修改日期
                selectedDate!!.set(year, month, dayOfMonth)
                btnDate!!.text = DateFormatTool.getDefaultDate(selectedDate.timeInMillis)

            }
        }
//       初始化Dialog日期,Dialog弹出时显示的日期
        val year = selectedDate.get(Calendar.YEAR)
        val month = selectedDate.get(Calendar.MONTH)
        val day = selectedDate.get(Calendar.DAY_OF_MONTH)

        var dialog = DatePickerDialog(this, listener, year, month, day)
        //设置Dialog的最大日期为当前日期
        dialog.datePicker.maxDate = currentDate.timeInMillis;
        //显示对话框
        dialog.show()
    }
}