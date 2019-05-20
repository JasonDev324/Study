package io.tanjundang.study.knowledge.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.tanjundang.study.R

/**
 * @Author: TanJunDang
 * @Date: 2019/5/20
 * @Description:
 */

class PageTwoFragment : Fragment() {
    companion object {
        fun newInstance(): PageTwoFragment {
            return PageTwoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_page_two, container, false)
    }

}