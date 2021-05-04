package com.dupreincaperu.dupree.mh_slid_peru;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dupreincaperu.dupree.R;
import com.squareup.picasso.Picasso;

public class CustomSwipeAadapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    String url = "";
    String urlbanner1 = "https://alcor.dupree.pe/image/banner/450/1.jpg";
    String urlbanner2 = "https://alcor.dupree.pe/image/banner/450/2.jpg";
    String urlbanner3 = "https://alcor.dupree.pe/image/banner/450/3.jpg";

    /*int draw;
    int a = R.drawable.bannerazzorti1;
    int b = R.drawable.bannerazzorti2;
    int c = R.drawable.bannerazzorti3;*/

    private String[] image_resource = {urlbanner1,urlbanner2,urlbanner3};

    public CustomSwipeAadapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return image_resource.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return (view == (LinearLayout)o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);

        //imageView.setImageResource(image_resource[position]);
        container.addView(item_view);

        switch (position){
            case 0:
                url = urlbanner1;
                //draw = a;
                break;
            case 1:
                url = urlbanner2;
                //draw = b;
                break;
            case 2:
                url = urlbanner3;
                //draw = c;
                break;
        }

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ph_add_image2)
                .error(R.drawable.ph_add_image2)
                .fit()
                .into(imageView);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
