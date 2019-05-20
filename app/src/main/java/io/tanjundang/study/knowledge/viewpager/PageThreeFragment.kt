package io.tanjundang.study.knowledge.viewpager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.tanjundang.study.R


class PageThreeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_page_three, container, false)
    }

    companion object {
        fun newInstance(): PageThreeFragment {
            return PageThreeFragment()
        }
    }

}
