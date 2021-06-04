package com.dupreincaperu.dupree.mh_adapters.base.TabViewPager;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 01/12/18.
 */
public abstract class TabManagerFragment extends BaseFragment {
    private String TAG = TabManagerFragment.class.getName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViewPage().setAdapter(setAdapter());
        setTabs().setTabMode(TabLayout.MODE_FIXED);
        setTabs().setupWithViewPager(setViewPage());

        createTabIcons();

        onLoadedView();
    }

    protected abstract ViewPager setViewPage();
    protected abstract TabLayout setTabs();
    protected abstract FragmentStatePagerAdapter setAdapter();
    protected abstract  List<ModelList> setItems();

    protected int getPageCurrent(){
        return setViewPage().getCurrentItem();
    }

    protected void setPageCurrent(int position){
        setViewPage().setCurrentItem(position);
    }

    private void createTabIcons() {
        Log.e(TAG, "createTabIcons");
        for(int i = 0; i< setItems().size(); i++) {
            TextView view = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_item, null);
            view.setText(setItems().get(i).getName());
            view.setTextColor(getResources().getColor(R.color.azulDupree));

            Drawable icon = getResources().getDrawable(setItems().get(i).getId());
            if(icon != null) {
                icon.setColorFilter(new
                        PorterDuffColorFilter(getResources().getColor(R.color.azulDupree), PorterDuff.Mode.MULTIPLY));
                view.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            }
            setTabs().getTabAt(i).setCustomView(view);
        }
    }

    protected void setTabIcons(int i, boolean isVisible) {
        Log.e(TAG, "setTabIcons");
        TextView view = (TextView) setTabs().getTabAt(i).getCustomView();
        if(view!=null) {
            view.setText(setItems().get(i).getName());
            view.setTextColor(getResources().getColor(isVisible ? R.color.azulDupree : R.color.gray_4));

            Drawable icon = getResources().getDrawable(setItems().get(i).getId());
            if (icon != null) {
                icon.setColorFilter(new
                        PorterDuffColorFilter(getResources().getColor(isVisible ? R.color.azulDupree : R.color.gray_4), PorterDuff.Mode.MULTIPLY));
                view.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            }

            LinearLayout tabStrip = ((LinearLayout) setTabs().getChildAt(0));
            tabStrip.getChildAt(i).setClickable(isVisible);
        }
    }
}
