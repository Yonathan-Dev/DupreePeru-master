package com.dupreincaperu.dupree.mh_adapters.base.TabViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dupreincaperu.dupree.model_view.TabItem;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 01/12/18.
 */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<TabItem> tabItemList;

    public BaseViewPagerAdapter(FragmentManager fm, List<TabItem> tabItemList) {
        super(fm);
        this.tabItemList = tabItemList;
    }

    @Override
    public Fragment getItem(int position) {
        return tabItemList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabItemList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabItemList.get(position).getTabTitle();
    }

    public List<TabItem> getTabItemList() {
        return tabItemList;
    }

    public Fragment getFragment(int pos) {
        return tabItemList.get(pos).getFragment();
    }
}
