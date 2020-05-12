package com.dupreincaperu.dupree.mh_adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_utilities.ProcessImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by Marwuin@gmail.com on 16/4/2017.
 */

public class ProfileViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;


    List<String> mResources;
    public ProfileViewPagerAdapter(Context context, List<String> mResources) {
        this.mContext = context;
        this.mResources = mResources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;//HACE QUE SE ELIMINANE TODAS LAS ANTERIORES Y SE AGREGAN NUEVAS
        //return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.adapter_viewpager_profile, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        //imageView.setImageResource(mResources.get(position).);

        img = ImageLoader.getInstance();
        img.init(ProcessImage.configurarImageLoader(mContext));
        img.displayImage(mResources.get(position), imageView);//perfil

        Log.e("cambio a: ", String.valueOf(position));
        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    private ImageLoader img;

}
