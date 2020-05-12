package com.dupreincaperu.dupree.mh_utilities;

import android.content.Context;

import com.dupreincaperu.dupree.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/**
 * Created by cloudemotion on 24/9/17.
 */

public class ProcessImage {
    public static ImageLoaderConfiguration configurarImageLoader(Context mContext) {
        DisplayImageOptions opcionesDefault = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)//@drawable/logocompany
                .showImageOnFail(R.drawable.user_empty)
                .showImageForEmptyUri(R.drawable.user_empty)
                .showImageOnLoading(R.drawable.loading)
                .resetViewBeforeLoading(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(opcionesDefault)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .build();
        return config;
    }
}
