package io.tanjundang.study.knowledge.scrollconflict

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.tanjundang.study.R
import io.tanjundang.study.base.BaseActivity
import io.tanjundang.study.common.view.CustomLayoutManager
import io.tanjundang.study.common.view.ItemDivider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import java.util.concurrent.TimeUnit

/**
 * @Author: TanJunDang
 * @Date: 2019/5/20
 * @Description:
 */
class ScrollConflictActivity : BaseActivity() {

    var recyclerview: RecyclerView? = null
    var manager: LinearLayoutManager? = null
    var data = ArrayList<TestListInfo>()
    var mAdapter: TestAdapter? = null


    override fun initView() {
        setContentView(R.layout.activity_scroll_conflict)
        val btnTest = findViewById<View>(R.id.btnTest) as Button

        recyclerview = findViewById<View>(R.id.recyclerview) as RecyclerView
        mAdapter = TestAdapter()
        manager = CustomLayoutManager(this)
        manager!!.setOrientation(LinearLayoutManager.HORIZONTAL)

        recyclerview!!.addItemDecoration(ItemDivider(getColor(R.color.transparent), ItemDivider.HORIZONTAL, 35))
        recyclerview!!.setLayoutManager(manager)
        recyclerview!!.setAdapter(mAdapter)
        recyclerview!!.addOnScrollListener(StoreShowOnScrollListener())

    }

    override fun initData() {
        data.add(TestListInfo(""))
        data.add(TestListInfo(""))
        for (i in 0..9) {
            data.add(TestListInfo("DATA_$i"))
        }
        data.add(TestListInfo(""))
        data.add(TestListInfo(""))
        mAdapter!!.notifyDataSetChanged()
        //延迟加载解决偏移出错问题
        Observable.interval(1, TimeUnit.SECONDS).take(1).observeOn(AndroidSchedulers.mainThread()).subscribe {
            val scrollToPos = 2
            manager!!.scrollToPosition(scrollToPos)
        }
    }

    inner class StoreShowOnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val childCount = recyclerView!!.getChildCount()
            for (j in 0 until childCount) {
                val v = recyclerView.getChildAt(j)
                var offset = Math.abs(recyclerView.getWidth() / 2 - (v.left + v.right) / 2).toFloat() / recyclerView.getWidth()
                offset = Math.min(offset, 0.2f)
                v.scaleX = 1 - offset
                v.scaleY = 1 - offset
            }
        }
    }

    inner class TestAdapter : RecyclerView.Adapter<TestAdapter.TestHolder>() {
        var DATA_TAG = R.layout.list_item_scroll_conflict

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_scroll_conflict, parent, false)
            return TestHolder(view)
        }

        override fun onBindViewHolder(holder: TestHolder, position: Int) {
            val info = data[position]
            holder.rootview.visibility = if (info.title.isEmpty()) View.GONE else View.VISIBLE

            holder.tvTitle.text = info.title
            holder.rootview.setTag(DATA_TAG, position)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class TestHolder(var rootview: View) : RecyclerView.ViewHolder(rootview) {

            var tvTitle: TextView
            var ivImage: ImageView

            init {
                ivImage = rootview.findViewById<View>(R.id.ivImage) as ImageView
                tvTitle = rootview.findViewById<View>(R.id.tvTitle) as TextView
                rootview.setOnClickListener { v ->
                    val position = v.getTag(DATA_TAG) as Int
                    manager!!.scrollToPosition(position)
                }
            }
        }


    }

}