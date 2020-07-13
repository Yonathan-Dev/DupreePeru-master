package com.imageperu.lib_image.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.imageperu.lib_image.BuildConfig;
import com.imageperu.lib_image.view.dialogs.CropImageImage;
import com.isseiaoki.simplecropview.CropImageView;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class PermissionCamera {
    public static final int PICK_CAMERA_REQUEST = 123;
    public static final int PICK_IMAGE_REQUEST = 124;
    public static final int PICK_VIDEO_REQUEST = 125;
    public static final int PICK_AUDIO_REQUEST = 126;

    private static final String TAG = PermissionCamera.class.getName();
    private Activity activity;
    private Fragment fragment;

    private boolean resultToFragment = false;
    private Events events;
    public PermissionCamera(Activity activity, Events events) {
        resultToFragment = false;
        this.activity = activity;
        this.events = events;

    }

    public PermissionCamera(Fragment fragment, Events events) {
        resultToFragment = true;
        this.activity = fragment.getActivity();
        this.fragment = fragment;
        this.events = events;
    }

    public static final int CONST_PERMISSION_GALERY=123;
    @AfterPermissionGranted(CONST_PERMISSION_GALERY)
    public void checkPermissionGalery() {
        //private void connect(int mode, String roomId, String nameCall, String numberCall, String idDevice) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions((resultToFragment ? fragment.getContext() : activity), perms)) {
            galleryTake();
        } else {
            if(resultToFragment)
                EasyPermissions.requestPermissions(fragment, "Necesita habilitar permisos", CONST_PERMISSION_GALERY, perms);
            else
                EasyPermissions.requestPermissions(activity, "Necesita habilitar permisos", CONST_PERMISSION_GALERY, perms);
        }
    }

    public static final int CONST_PERMISSION_CAMERA=124;
    @AfterPermissionGranted(CONST_PERMISSION_CAMERA)
    public void checkPermissionCamera() {
        //private void connect(int mode, String roomId, String nameCall, String numberCall, String idDevice) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        assert (resultToFragment ? fragment.getContext() : activity) != null;
        if (EasyPermissions.hasPermissions((resultToFragment ? fragment.getContext() : activity ), perms)) {
            cameraTake();
        } else {
            if(resultToFragment)
                EasyPermissions.requestPermissions(fragment, "Necesita habilitar permisos", CONST_PERMISSION_CAMERA, perms);
            else
                EasyPermissions.requestPermissions(activity, "Necesita habilitar permisos", CONST_PERMISSION_CAMERA, perms);
        }
    }

    public static final int CONST_PERMISSION_CAMERA_VIDEO=125;
    @AfterPermissionGranted(CONST_PERMISSION_CAMERA_VIDEO)
    public void checkPermissionVideo() {
        //private void connect(int mode, String roomId, String nameCall, String numberCall, String idDevice) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        assert (resultToFragment ? fragment.getContext() : activity) != null;
        if (EasyPermissions.hasPermissions((resultToFragment ? fragment.getContext() : activity), perms)) {
            videoTake();
        } else {
            if(resultToFragment)
                EasyPermissions.requestPermissions(fragment, "Necesita habilitar permisos", CONST_PERMISSION_CAMERA_VIDEO, perms);
            else
                EasyPermissions.requestPermissions(activity, "Necesita habilitar permisos", CONST_PERMISSION_CAMERA_VIDEO, perms);
        }
    }

    public static final int CONST_PERMISSION_AUDIO=125;
    @AfterPermissionGranted(CONST_PERMISSION_AUDIO)
    public void checkPermissionAudio() {
        //private void connect(int mode, String roomId, String nameCall, String numberCall, String idDevice) {
        String[] perms = {Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        assert (resultToFragment ? fragment.getContext() : activity) != null;
        if (EasyPermissions.hasPermissions((resultToFragment ? fragment.getContext() : activity), perms)) {
            videoTake();
        } else {
            if(resultToFragment)
                EasyPermissions.requestPermissions(fragment, "Necesita habilitar permisos", CONST_PERMISSION_AUDIO, perms);
            else
                EasyPermissions.requestPermissions(activity, "Necesita habilitar permisos", CONST_PERMISSION_AUDIO, perms);
        }
    }

    private File imageFile;
    private Uri picUri;

    public File getImageFile() {
        return imageFile;
    }

    public Uri getPicUri() {
        return picUri;
    }

    public void cameraTake(){
        try {
            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            imageFile = new File(imageFilePath);

//            picUri = FileProvider.getUriForFile(
//                    resultToFragment ? fragment.getActivity() : activity,
//                    (resultToFragment ? fragment.getActivity() : activity ).getApplicationContext().getPackageName() + ".provider", imageFile);

            Log.e(TAG, BuildConfig.APPLICATION_ID + ".provider");
            picUri = FileProvider.getUriForFile(
                    resultToFragment ? fragment.getActivity() : activity,
                    BuildConfig.APPLICATION_ID + ".provider", imageFile);


            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /**/
            List<ResolveInfo> resolvedIntentActivities = (resultToFragment ? fragment.getActivity() : activity).getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                String packageName = resolvedIntentInfo.activityInfo.packageName;
                (resultToFragment ? fragment.getActivity() : activity).grantUriPermission(packageName, picUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            /**/

            takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  picUri );
            if(resultToFragment)
                fragment.startActivityForResult(takePictureIntent, PICK_CAMERA_REQUEST);
            else
                activity.startActivityForResult(takePictureIntent, PICK_CAMERA_REQUEST);

        } catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support capturing images!";
            Toast.makeText((resultToFragment ? fragment.getActivity() : activity), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }


    public void galleryTake(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(resultToFragment)
            fragment.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        else
            activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void recordAudio(){
        Intent audioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        audioIntent.putExtra( MediaStore.EXTRA_OUTPUT,true);
        if(resultToFragment)
            fragment.startActivityForResult(audioIntent, PICK_AUDIO_REQUEST);
        else
            activity.startActivityForResult(audioIntent, PICK_AUDIO_REQUEST);
    }

    private File videoFile;

    public File getVideoFile() {
        return videoFile;
    }

    public void videoTake(){
        try {
            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/video.mp4";
            videoFile = new File(imageFilePath);
            picUri = FileProvider.getUriForFile(
                    resultToFragment ? fragment.getActivity() : activity,
                    (resultToFragment ? fragment.getActivity() : activity).getApplicationContext().getPackageName() + ".provider", videoFile);

            Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  picUri );
            // En la siguiente línea podríamos utilizar cualquiera de las
            // dos constantes definidas anteriormente: ALTA_CALIDAD o BAJA_CALIDAD
            takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            //takePictureIntent.putExtra(MediaStore.EXTRA_MEDIA_RADIO_CHANNEL, 1);
            //takePictureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 27457600L);//25*1048*1048=25MB
            takePictureIntent.putExtra("android.intent.extra.durationLimit", 10);

            List<ResolveInfo> resolvedIntentActivities = (resultToFragment ? fragment.getActivity() : activity).getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                String packageName = resolvedIntentInfo.activityInfo.packageName;

                (resultToFragment ? fragment.getActivity() : activity).grantUriPermission(packageName, picUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            if(resultToFragment)
                fragment.startActivityForResult(takePictureIntent, PICK_VIDEO_REQUEST);
            else
                activity.startActivityForResult(takePictureIntent, PICK_VIDEO_REQUEST);

        } catch(ActivityNotFoundException anfe){
            String errorMessage = "Whoops - your device doesn't support capturing videos!";
            Toast.makeText((resultToFragment ? fragment.getActivity() : activity), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    /////////////////////PARA CORTRAR IMAGEN
    private void cropImage(Uri imageUri){

        CropImageImage imageImage = new CropImageImage();
        imageImage.setData(new CropImageImage.CutImageReady() {
            @Override
            public void croppeFile(File imageFile) {
                Log.e(TAG, "croppeFile()");
                if(events!=null)
                    events.imageFile(imageFile);
            }
        }, CropImageView.CropMode.FREE, imageUri);
        imageImage.show((fragment.getChildFragmentManager()), TAG);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, (resultToFragment ? fragment.getActivity() : activity));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, boolean crop) {
        Log.e(TAG, "onActivityResult() de PermissionCamera");

        Log.e("requestCode:",Integer.toString(requestCode));
        Log.e("resultCode:",Integer.toString(resultCode));

        Uri imageUri;
        //Galeria
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Log.e(TAG, "PICK_IMAGE_REQUEST");
            imageUri = data.getData();

            Bitmap imageRotada = ConvertUtil.rotateSamsung((resultToFragment ? fragment.getActivity() : activity), imageUri);
            Uri uriRotada = ConvertUtil.getImageUri((resultToFragment ? fragment.getActivity() : activity), imageRotada);
            imageUri = ((uriRotada != null) ? uriRotada : imageUri);
            processImage(imageUri, crop);
            //Camara
        } else if (requestCode == PICK_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Log.e(TAG, "PICK_CAMERA_REQUEST");
            imageUri = getPicUri();
            //cropImage(uriOriginal);
            //los sansumg generan problemad de retacion
            Bitmap imageRotada = ConvertUtil.rotateSamsung((resultToFragment ? fragment.getActivity() : activity), imageUri);
            Uri uriRotada = ConvertUtil.getImageUri((resultToFragment ? fragment.getActivity() : activity), imageRotada);

            imageUri = ((uriRotada != null) ? uriRotada : imageUri);
            processImage(imageUri, crop);
        }
    }

    private void processImage(Uri imageUri, boolean crop){
        if(crop) {
            cropImage(imageUri);
        } else {
            File file = ConvertUtil.getPath((resultToFragment ? fragment.getActivity() : activity), imageUri);
            Log.e(TAG, file.getAbsolutePath());
            if(events!=null){
                events.imageFile(file);
            }
        }
    }

    public interface Events {
        void imageFile(File imageFile);
    }
}