package com.imageperu.lib_image.view.dialogs;

import android.app.Dialog;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imageperu.lib_image.R;
import com.imageperu.lib_image.databinding.FragmentEditCutImageBinding;
import com.imageperu.lib_image.util.ConvertUtil;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;


public class CropImageImage extends DialogFragment implements View.OnClickListener {

    private static String TAG = CropImageImage.class.getSimpleName();

    private CutImageReady cutImageReady;
    private CropImageView.CropMode cropMode;

    public void setCropMode(CropImageView.CropMode cropMode) {
        this.cropMode = cropMode;
    }

    public void setCutImageReady(CutImageReady cutImageReady) {
        this.cutImageReady = cutImageReady;
    }

    private Uri imageUri;
    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialogStyle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

    }

    FragmentEditCutImageBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_cut_image, container, false);
        binding.setCallback(this);
        instanceObjects(binding.getRoot());
        return binding.getRoot();
    }

    public void setData(CutImageReady cutImageReady, CropImageView.CropMode cropMode, Uri imageUri) {
        this.cutImageReady = cutImageReady;
        this.cropMode = cropMode;
        this.imageUri = imageUri;
    }

    private void instanceObjects(View v) {
        //cutImage = new CutImageUtil(binding.cropImageView, getContext(), this::successSave);
        binding.cropImageView.load(imageUri).execute(mLoadCallback);
        binding.cropImageView.setCropMode(cropMode);
    }

    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {
            Log.e(TAG, "LoadCallback.onSuccess()");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "LoadCallback.onError(): "+e.getLocalizedMessage());
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        setCutImageReady(null);
    }

    public Uri createSaveUri() {
        return ConvertUtil.createNewUri(getActivity(), mCompressFormat);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_crop){
            showProgress();
            binding.cropImageView.crop(imageUri).execute(cropCallback);
        } else if(view.getId() == R.id.btn_cancel){
            dismiss();
        }
    }

    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
    private final CropCallback cropCallback = new CropCallback() {
        @Override
        public void onSuccess(Bitmap cropped) {
            Log.e(TAG, "CropCallback.onSuccess()");
            binding.cropImageView.save(cropped)
                    .compressFormat(mCompressFormat)
                    .execute(createSaveUri(), mSaveCallback);
        }

        @Override
        public void onError(Throwable e) {
            hidePorgress();
            Log.e(TAG, "CropCallback.onError(): "+e.getLocalizedMessage());
        }
    };

    private final SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSuccess(final Uri outputUri) {
            hidePorgress();
            Log.e(TAG, "SaveCallback.onSuccess()");
            Bitmap bitmap = ConvertUtil.resizeAndCompressImageBeforeSend(getActivity(), outputUri, mCompressFormat);
            successSave(bitmap);
        }
        @Override
        public void onError(Throwable e) {
            hidePorgress();
            Log.e(TAG, "SaveCallback.onError(): "+e.getLocalizedMessage());
        }
    };

    private void successSave(final Uri outputUri) {
        //try {
            //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), outputUri);
            if(cutImageReady != null){
                cutImageReady.croppeFile(/*"data:imageperu/png;base64," +*/ ConvertUtil.getPath(getContext(), outputUri));
            }
        //} catch (IOException | NullPointerException e) {
        //    e.printStackTrace();
        //} finally {
            dismiss();
        //}


    }

    private void successSave(final Bitmap bitmap) {
        try {
            //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), outputUri);
            if(cutImageReady != null){
                cutImageReady.croppeFile(ConvertUtil.getPath(getContext(), ConvertUtil.getImageUri(getContext(), bitmap)));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            dismiss();
        }
    }

    public String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    private void showProgress(){
//        ProgressDialog.getInstance().show(getFragmentManager(), TAG);
    }

    private void hidePorgress(){
//        ProgressDialog.getInstance().dismiss();

    }

    public interface CutImageReady{
        void croppeFile(File imageFile);
    }


    //https://stackoverflow.com/questions/12105064/actions-in-onactivityresult-and-error-can-not-perform-this-action-after-onsavei
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            Field mDismissed = DialogFragment.class.getDeclaredField("mDismissed");
            Field mShownByMe = DialogFragment.class.getDeclaredField("mShownByMe");
            mDismissed.setAccessible(true);
            mShownByMe.setAccessible(true);
            mDismissed.setBoolean(this, false);
            mShownByMe.setBoolean(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }
}
