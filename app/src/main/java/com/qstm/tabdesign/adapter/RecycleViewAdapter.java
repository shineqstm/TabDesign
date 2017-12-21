package com.qstm.tabdesign.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qstm.tabdesign.R;

import java.util.List;


/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class RecycleViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RecycleViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text,item);
    }
}
