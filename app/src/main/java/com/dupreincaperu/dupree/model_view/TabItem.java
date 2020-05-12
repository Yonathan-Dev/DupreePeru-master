package com.dupreincaperu.dupree.model_view;

import android.support.v4.app.Fragment;

/**
 * Created by ezequiel on 06/06/16.
 */
public class TabItem {

    private Fragment fragment;
    private String tabTitle;
    private int icon;

    public TabItem(Fragment fragment, String tabTitle) {
        this.fragment = fragment;
        this.tabTitle = tabTitle;
    }

    public TabItem(Fragment fragment, String tabTitle, int icon) {
        this.fragment = fragment;
        this.tabTitle = tabTitle;
        this.icon = icon;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
