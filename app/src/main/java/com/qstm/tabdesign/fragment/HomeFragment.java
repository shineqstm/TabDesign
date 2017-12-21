package com.qstm.tabdesign.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qstm.tabdesign.R;
import com.qstm.tabdesign.adapter.RecycleViewGridAdapter;
import com.qstm.tabdesign.adapter.TabFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 首页
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.tab_viewpager)
    ViewPager tabViewpager;
    Unbinder unbinder;
    @BindView(R.id.iv_fenlei)
    ImageView mIvFenlei;


    private List<Fragment> mFragmentArrays = new ArrayList<>();
    private List<String> mTabs = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //解决点击“我的”回来方法二，首页空白的问题，推荐的方法
        if (view != null) {
            unbinder = ButterKnife.bind(this, view);//必须加，不然报ButterKnife的异常
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }

        view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);//这里也得有，不然报ButterKnife的异常

        initView(view);
        return view;
    }

    private void initView(View view) {
        mIvFenlei.setOnClickListener(this);
        tablayout.removeAllTabs();
        tabViewpager.removeAllViews();
        if (mFragmentArrays != null) {
            mFragmentArrays.clear();
            mTabs.clear();
        }
        //替换成从服务器接口请求数据就成动态了
        mTabs.add("特惠新品");
        mTabs.add("有机果蔬");
        mTabs.add("放养牲畜");
        mTabs.add("健康吧");
        mTabs.add("调味品");
        mTabs.add("素食者");
        mTabs.add("时令食品");
        mTabs.add("野生菌类");
        mTabs.add("放养家禽");
        mTabs.add("休闲吧");
        mTabs.add("粮油类");
        mTabs.add("素食类");
        mTabs.add("周边菜场");

        //动态添加Fragment
        for (int i = 0; i < mTabs.size(); i++) {

            Fragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragment.setArguments(bundle);
            mFragmentArrays.add(fragment);
        }

        tabViewpager.setAdapter(new TabFragmentAdapter(getFragmentManager(), mFragmentArrays, mTabs));
        tablayout.setupWithViewPager(tabViewpager);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fenlei:
                startPopuwindows(view);
                break;
        }
    }

    private void startPopuwindows(View view1) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.layout_main_popuwindows,null);
        RecyclerView recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        RecycleViewGridAdapter gridAdapter=new RecycleViewGridAdapter(R.layout.item_gride_fenlei,mTabs);
        recyclerView.setAdapter(gridAdapter);

        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.showAsDropDown(view1);

        gridAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(),"点击了"+mTabs.get(position),Toast.LENGTH_SHORT).show();
                tabViewpager.setCurrentItem(position);
                popupWindow.dismiss();
            }
        });
        gridAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                tabViewpager.setCurrentItem(position);
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
