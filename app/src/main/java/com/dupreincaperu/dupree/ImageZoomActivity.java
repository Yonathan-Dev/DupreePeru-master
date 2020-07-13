package com.dupreincaperu.dupree;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dupreincaperu.dupree.mh_utilities.PinchZoomImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.FileDescriptor;
import java.io.IOException;

public class ImageZoomActivity extends AppCompatActivity {
    public static String URL_IMAGE="";
    ImageView mImageView;
    PinchZoomImageView mPinchZoomImageView;
    private Uri mImageUri;
    private Animator mCurrentAnimator;
    private int mLongAnimationDuration;
    ImageLoader img;
    String url_image="";

    private static final int REQUEST_OPEN_RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        url_image = getIntent().getStringExtra(URL_IMAGE);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mPinchZoomImageView = (PinchZoomImageView) findViewById(R.id.pinchZoomImageView);
        /*mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Toast.makeText(getApplicationContext(), "ImageView long pressed!", Toast.LENGTH_SHORT).show();
                // zoomImageFromThumb();
                pinchZoomPan();
                return true;
            }
        });*/

        mLongAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);

        /*
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("imageperu/*");
        startActivityForResult(intent, REQUEST_OPEN_RESULT_CODE);
        */

        //pinchZoomPan();

        img = ImageLoader.getInstance();
        img.init(PinchZoomImageView.configurarImageLoader(ImageZoomActivity.this));
        img.displayImage(url_image, mImageView);//cuadrar la imagen del grupo
        //img.displayImage(getIntent().getStringExtra(URL_IMAGE), mPinchZoomImageView);//cuadrar la imagen del grupo


        pinchZoomPan();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View decorView = getWindow().getDecorView();
        if(hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if(requestCode == REQUEST_OPEN_RESULT_CODE && resultCode == RESULT_OK) {
            if(resultData != null) {
                mImageUri = resultData.getData();

                //try {
                //    Bitmap bitmap = getBitmapFromUri(uri);
                //    mImageView.setImageBitmap(bitmap);
                //} catch (IOException e) {
                //    e.printStackTrace();
                //}

                Glide.with(this)
                        .load(mImageUri)
                        .into(mImageView);
            }
        }
    }
    */

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return bitmap;
    }

    /*
    private void zoomImageFromThumb() {
        if(mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        Glide.with(this)
                .load(mImageUri)
                .into(mPinchZoomImageView);

        Rect startBounds = new Rect();
        Rect finalBounds = new Rect();
        Point globalOffset = new Point();
        mImageView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if((float) finalBounds.width() /finalBounds.height() >
                (float) startBounds.width() / startBounds.height()) {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = (float) startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = (float) startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        mImageView.setAlpha(0f);
        mPinchZoomImageView.setVisibility(View.VISIBLE);

        mPinchZoomImageView.setPivotX(0f);
        mPinchZoomImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(mPinchZoomImageView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(mPinchZoomImageView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(mPinchZoomImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(mPinchZoomImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mLongAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;
    }
    */

    private void pinchZoomPan() {
        //mPinchZoomImageView.setImageUri(mImageUri);

        mPinchZoomImageView.setImageUrl(url_image, ImageZoomActivity.this);
        mImageView.setAlpha(0.f);
        mPinchZoomImageView.setVisibility(View.VISIBLE);
        //
        //zoomImageFromThumb();
    }


}
