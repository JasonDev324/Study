package io.tanjundang.study.knowledge.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import io.tanjundang.study.R
import io.tanjundang.study.common.tools.Functions
import kotlinx.android.synthetic.main.activity_custom_view.view.*

/**
 * @Author: TanJunDang
 * @Date: 2019/5/31
 * @Description:
 */
class KotlinActivity : AppCompatActivity() {

    private var btnShow: Button? = null
    private var count: Int = 0
    var person = Person("TJD", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        btnShow = findViewById(R.id.btnShow) as Button;
        btnShow!!.setOnClickListener(View.OnClickListener {
            Functions.toast(person.name + person.age)
//            count++
//            cases(count)
//            if (count == 3) {
//                count = 0
//            }
        })


    }

    /**
     * when语句类似switch
     */
    fun cases(obj: Any) {
        when (obj) {
            1 -> {
                Functions.toast("1" + count)
            }
            2 -> {
                Functions.toast("2" + count)
            }
            3 -> {
                Functions.toast("3" + count)
            }
        }


    }


    class Person(var name: String) {

        var age: Int = 0

        init {
            name = "Kwan"
        }

        constructor(name: String, age: Int) : this(name) {
            this.age = age
        }
    }

}