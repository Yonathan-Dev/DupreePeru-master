package com.dupreincaperu.dupree.mh_adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.R.id;
import com.daimajia.slider.library.R.layout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

/**
 * Created by Epica on 9/3/2017.
 */

public class BannerSliderAdapter extends BaseSliderView{

    public BannerSliderAdapter(Context context) {
        super(context);
    }

    public View getView() {

        View v = LayoutInflater.from(this.getContext()).inflate(layout.render_type_text, null);
        ImageView target = (ImageView) v.findViewById(id.action_bar_root);
        //ImageView target = (ImageView) v.findViewById(id.daimajia_slider_image);
        LinearLayout frame = (LinearLayout) v.findViewById(id.description_layout);
        frame.setBackgroundColor(Color.TRANSPARENT);

//      if you need description
//      description.setText(this.getDescription());

        this.bindEventAndShow(v, target);
        //this.bindEventAndShow(null,target);
        return v;
    }


    private ViewPagerEx.OnPageChangeListener listenerSlider
            = new ViewPagerEx.SimpleOnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }
    };

    public ViewPagerEx.OnPageChangeListener getListenerSlider() {
        return listenerSlider;
    }
}
