package io.tanjundang.study.knowledge.viewpager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.tanjundang.study.R

class CustomFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_custom, container, false)
    }

    companion object {
        fun newInstance(): CustomFragment {
            return CustomFragment()
        }
    }

}
