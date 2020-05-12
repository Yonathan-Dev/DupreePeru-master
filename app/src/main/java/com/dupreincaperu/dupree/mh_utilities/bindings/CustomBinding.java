package com.dupreincaperu.dupree.mh_utilities.bindings;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dupreincaperu.dupree.R;
import com.squareup.picasso.Picasso;

/**
 * Created by marwuinh@gmail.com on 2/20/19.
 */

public class CustomBinding {
    private static String TAG = CustomBinding.class.getName();

    @BindingAdapter({"bind:picassoUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if(imageUrl!=null && !imageUrl.isEmpty()) {
            //Picasso.with(view.getContext())
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ph_add_image2)
                    .centerInside()
                    .error(R.drawable.ph_add_image2)
                    .fit()
                    .into(view);
        } else {
            view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ph_add_image2));
        }
    }

    @BindingAdapter({"bind:picassoUrlFull"})
    public static void loadImage2(final ImageView view, String imageUrl) {

        //String urlFull = imageUrl;

        ///data/user/0/com.dupreinca.dupree/cachedupree_documents_temporary_file0.jpg
        if(!TextUtils.isEmpty(imageUrl) && !imageUrl.contains("android.resource://") && !imageUrl.contains("/storage/emulated/") && !imageUrl.contains("/user/0/com.dupreinca.dupree/")) {
            Log.e(TAG, "Picasso A(URL: "+imageUrl);
            //Picasso.with(view.getContext())
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ph_add_image2)
                    //.networkPolicy(NetworkPolicy.OFFLINE)
                    .centerCrop()
                    .error(R.drawable.ph_add_image2)
                    .fit()
                    .into(view);
        } else if(!TextUtils.isEmpty(imageUrl) && (imageUrl.contains("android.resource://"))){
            Log.e(TAG, "Picasso B(android.resource://): "+imageUrl);
            view.setImageURI(Uri.parse(imageUrl));
        } else if(!TextUtils.isEmpty(imageUrl) && (imageUrl.contains("/storage/emulated/") || imageUrl.contains("/user/0/com.dupreinca.dupree/"))){
            Log.e(TAG, "Picasso C(/storage/emulated/): "+imageUrl);
            view.setImageURI(Uri.parse(imageUrl));

        } else {
            Log.e(TAG, "Picasso D(else): "+imageUrl);
            view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ph_add_image2));
        }
    }

    @BindingAdapter({"bind:isVisible"})
    public static void setIsVisible(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"bind:isEnable"})
    public static void setIsEnable(View view, boolean isEnable) {
//        view.setBackgroundResource(isEnable ? R.color.transp_anaranjado_1 : R.color.grayLight_semitransparente);
        view.setEnabled(isEnable);
    }
}
