package com.dupreincaperu.dupree.mh_utilities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_adapters.ProfileViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ImageZoomActivity_Scroll extends AppCompatActivity {

    private String TAG = "ZoomActivity_Scroll";
    public static String ARRAY_IMAGES = "path_images";
    public static String POSITION_IMAGES = "position_images";


    ViewPager viewpager_zoom;
    LinearLayout layoutDotsZ;

    List<String> image;
    ProfileViewPagerAdapter mCustomPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom__scroll);

        String[] array_images = getIntent().getStringArrayExtra(ARRAY_IMAGES);
        int position_images = getIntent().getIntExtra(POSITION_IMAGES, -1);

        viewpager_zoom = (ViewPager) findViewById(R.id.viewpager_zoom);
        layoutDotsZ = (LinearLayout) findViewById(R.id.layoutDotsZ);

        image=new ArrayList<>();
        for(int i = 0; i<array_images.length; i++){
            image.add(array_images[i]);
        }
        dots = new TextView[array_images.length];
        addBottomDots(0);

        //image.add(Data.mh_nearby_users.get(position).getImage());//primera imagen antes de actualizar
        mCustomPagerAdapter = new ProfileViewPagerAdapter(ImageZoomActivity_Scroll.this, image);
        viewpager_zoom.setAdapter(mCustomPagerAdapter);
        viewpager_zoom.addOnPageChangeListener(mOnPageChangeListener);

        viewpager_zoom.setCurrentItem(position_images==-1 ? 0 : position_images);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View decorView = getWindow().getDecorView();
        if(hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            /*| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION*///oculta barra de navegacion (inferior)
            );
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.i(TAG,"onPageSelected Page: "+position);
            addBottomDots(position);
            switch (position){
                case 0:
                    break;
                case 1:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private TextView[] dots;
    private void addBottomDots(int currentPage) {
        layoutDotsZ.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(getResources().getColor(R.color.gray_1));
            layoutDotsZ.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.gray_6));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
