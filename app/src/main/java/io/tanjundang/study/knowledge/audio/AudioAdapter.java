package io.tanjundang.study.knowledge.audio;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.tanjundang.study.R;
import io.tanjundang.study.databinding.ListItemAudioBinding;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/16
 * @Description:
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioHolder> {

    ArrayList<AudioInfo> list = new ArrayList<>();

    public AudioAdapter(ArrayList<AudioInfo> list) {
        this.list = list;
    }

    @Override
    public AudioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemAudioBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_audio, parent, false);
        return new AudioHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(AudioHolder holder, int position) {
        AudioInfo info = list.get(position);
        AudioInfo lastInfo;
        try {
            lastInfo = list.get(position - 1);
            if (info.getDate() - lastInfo.getDate() < 60 * 1000) {
                holder.getmBinding().tvDate.setVisibility(View.GONE);
            } else {
                holder.getmBinding().tvDate.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }

        holder.getmBinding().setInfo(info);
        holder.getmBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AudioHolder extends RecyclerView.ViewHolder {
        ListItemAudioBinding mBinding;

        public AudioHolder(ListItemAudioBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;

        }

        public ListItemAudioBinding getmBinding() {
            return mBinding;
        }
    }

}
