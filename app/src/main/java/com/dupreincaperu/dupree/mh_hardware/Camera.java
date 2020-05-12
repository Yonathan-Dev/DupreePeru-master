package com.dupreincaperu.dupree.mh_hardware;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Camera {

    //keep track of camera capture intent
    public static final int CAMERA_CAPTURE = 1;
    //keep track of cropping intent
    public static final int PIC_CROP = 3;
    //keep track of gallery intent
    public static final int PICK_IMAGE_REQUEST = 2;
    //captured picture uri
    public static Uri picUri;
    //image later permissions
    public static final String TAKE_PHOTO="Take Photo";
    public static final String CHOOSE_PHOTO="Choose from Library";
    public static  final String BROACAST_DATA="broacast_data";


    private String userChoosenTask;
    private ImageView imageViewPic;

    private AppCompatActivity mContex;

    String tagFragment;
    String objectFragment;

    public void setBroadcast(String tagFragment, String objectFragment){
        this.tagFragment=tagFragment;
        this.objectFragment=objectFragment;
    }

    public String getTagFragment() {
        return tagFragment;
    }

    public void setTagFragment(String tagFragment) {
        this.tagFragment = tagFragment;
    }

    public String getObjectFragment() {
        return objectFragment;
    }

    public void setObjectFragment(String objectFragment) {
        this.objectFragment = objectFragment;
    }

    public Camera(AppCompatActivity mContex) {
        this.mContex = mContex;
    }

    public void setUserChoosenTask(String userChoosenTask) {
        this.userChoosenTask = userChoosenTask;
    }

    public void setImageViewPic(ImageView imageViewPic) {
        this.imageViewPic = imageViewPic;
    }


    /*private File createImageFile() throws IOException {
        File dir = new File(SuFileInfo.getInstance().getImagePath());
        if (!dir.exists()){
            dir.mkdirs();
        }
        // 以上为了规避用户删除了._Image目录，报错java.io.IOException: open failed: ENOENT (No such file or directory)
        File file = new File(dir, TempImageMame);
        if (!file.exists()){
            file.createNewFile();
        }
        return file;
    }*/

    public static File imageFile;
    public void cameraTake(){
        try {



            // String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            // File imageFile = new File(imageFilePath);

            imageFile = createNewFile("Dupree_");//crea una carpeta especifica para la imagen (NUEVO)
            Log.e("cameraTake()","imageFilePath: "+imageFile.getPath());//(NUEVO)



            picUri = FileProvider.getUriForFile(mContex, mContex.getApplicationContext().getPackageName() + ".provider", imageFile);
            //picUri = FileProvider.getUriForFile(mContex, ".provider", imageFile);

            //picUri

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /**/
            List<ResolveInfo> resolvedIntentActivities = mContex.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                String packageName = resolvedIntentInfo.activityInfo.packageName;

                mContex.grantUriPermission(packageName, picUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            /**/




            Uri.fromFile(imageFile);
            //takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  picUri );
            takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  picUri );

            mContex.startActivityForResult(takePictureIntent, CAMERA_CAPTURE);

        } catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support capturing images!";
            Toast.makeText(mContex, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public void galleryTake(){
        Log.e("galleryTake","A");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        Log.e("galleryTake","B");
        mContex.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        Log.e("galleryTake","C");
    }

    public void onActivityResultHand(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //user is returning from capturing an image using the camera
            if(requestCode == CAMERA_CAPTURE){
                //get the Uri for the captured image
                Uri uri = picUri;
                //carry out the crop operation
                performCrop();
                Log.e("performCrop()","................................A");
                Log.d("picUri", uri.getPath());
                Log.e("performCrop()","................................B");
            } else if(requestCode == PICK_IMAGE_REQUEST){
                //Uri uri=Uri.parse("http://www.american.edu/uploads/profiles/large/chris_palmer_profile_11.jpg");
                //picUri = uri;

                picUri = data.getData();
                Log.d("uriGallery", picUri.toString());
                performCrop();
            } else if(requestCode == PIC_CROP){//user is returning from cropping the image
                //get the returned data
                Log.e("performCrop()","................................X");
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = (Bitmap) extras.get("data");
                //display the returned cropped image
                //ENVIAR LA IMAGEN
                imageViewPic.setImageBitmap(thePic);
                //convertImageToBase64(thePic);
            }
        }
    }


    //aparentemente revisa que en algunos dispositivos no funciona getData en result
    public boolean hasImageCaptureBug() {
        // list of known devices that have the bug
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("android-devphone1/dream_devphone/dream");
        devices.add("generic/sdk/generic");
        devices.add("vodafone/vfpioneer/sapphire");
        devices.add("tmobile/kila/dream");
        devices.add("verizon/voles/sholes");
        devices.add("google_ion/google_ion/sapphire");

        return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
                + android.os.Build.DEVICE);
    }

    //PARA CONVERTIR LA RUTA DE LA IMAGEN EN UNA RUTA ABSOLUTA, solo funciona de la galeria
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);

        }catch (IllegalArgumentException e){
            Log.e("getRealPathFromURI()","IllegalArgumentException");
            return null;
        }catch (NullPointerException e){
            Log.e("getRealPathFromURI()","IllegalArgumentException");
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Bitmap getImageFromURI(Context context, Uri contentUri){

        Bitmap bitmap;
        try {
            InputStream is = context.getContentResolver().openInputStream(contentUri);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }catch (FileNotFoundException e){
            return null;
        }catch (IOException e){
            return  null;
        }

        return bitmap;
    }

    private byte[] convertImageBitmanToByte(Bitmap bitmap){
        //si viene d euna ruta
        //Bitmap bm = BitmapFactory.decodeFile("/path/to/image.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        return b;
    }
    private void convertImageToBase64(Bitmap bitmap){
        String encodedImage = Base64.encodeToString(convertImageBitmanToByte(bitmap), Base64.DEFAULT);


        Log.e("Image - Base64",encodedImage);
    }

    public ImageView getImageViewPic() {
        return imageViewPic;
    }

    private void performCrop(){
        Log.e("performCrop()","................................1");
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            Log.e("performCrop()","................................2");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");

            ///// PERMISOS PARA ANDROID >6

            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            List<ResolveInfo> resolvedIntentActivities = mContex.getPackageManager()
                    .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                String packageName = resolvedIntentInfo.activityInfo.packageName;

                mContex.grantUriPermission(packageName, picUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }*/

            //ESTA ULTIMA SE AGREGO POR PROBLEMAS DE PERMISOS ()/EN TEORIA ESTOS PERMISSOS DE DEBEN SOLICITRA Y REVOKAR AL TERMINAR DE USARLOS
            /*List<ResolveInfo> resInfoList =  mContex.getPackageManager().queryIntentActivities(cropIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                mContex.grantUriPermission(packageName, picUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }*/
            //ESTA ULTIMA SE AGREGO POR PROBLEMAS DE PERMISOS ()/EN TEORIA ESTOS PERMISSOS DE DEBEN SOLICITRA Y REVOKAR AL TERMINAR DE USARLOS
            /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                List<ResolveInfo> resInfoList = mContex.getPackageManager().queryIntentActivities(cropIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    mContex.grantUriPermission(packageName, picUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }*/

            //ESTA ULTIMA SE AGREGO POR PROBLEMAS DE PERMISOS ()/EN TEORIA ESTOS PERMISSOS DE DEBEN SOLICITRA Y REVOKAR AL TERMINAR DE USARLOS
            //mContex.grantUriPermission(mContex.getPackageName(), picUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ///// PERMISOS PARA ANDROID >6

            Log.e("performCrop()","................................3");
            //set crop properties
            cropIntent.putExtra("crop", "true");

            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            Log.e("performCrop()","................................4");
            //start the activity - we handle returning in onActivityResult



            ////////////////////// NUEVO PARA ACCEDER AL PHTH DE LA IMAGEN REDIMENSIONADA
            File f = createNewFile("CROP_");
            /*try {
                f.createNewFile();
            } catch (IOException ex) {
                Log.e("io", ex.getMessage());
            }*/

            mCropImagedUri = Uri.fromFile(f);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImagedUri);
            /////////////////////////////

            mContex.startActivityForResult(cropIntent, PIC_CROP);
            Log.e("performCrop()","................................5"+": URI "+mCropImagedUri);
        } catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(mContex, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private Uri mCropImagedUri;

    ///PARA ACCEDER A LA UBICACION DE LA IMAGEN EN MEMORIA
    private File createNewFile(String prefix){
        if(prefix==null || "".equalsIgnoreCase(prefix)){
            prefix="IMG_";
        }
        File newDirectory = new File(Environment.getExternalStorageDirectory()+"/mypics/");
        if(!newDirectory.exists()){
            if(newDirectory.mkdir()){
                Log.d(mContex.getClass().getName(), newDirectory.getAbsolutePath()+" directory created");
            }
        }
        File file = new File(newDirectory,(prefix+ System.currentTimeMillis()+".jpg"));
        if(file.exists()){
            //this wont be executed
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
    /*public static void revokeFileReadPermission(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            String dirpath = context.getFilesDir() + File.separator + "directory";
            File file = new File(dirpath + File.separator + "file.txt");
            Uri uri = FileProvider.getUriForFile(context, "com.package.name.fileprovider", file);
            context.revokeUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }*/

    public void onRequestPermissionsResultHand(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*if(userChoosenTask.equals(TAKE_PHOTO))
                        cameraTake();
                    else */if(userChoosenTask.equals(CHOOSE_PHOTO))
                        galleryTake();
                }
                break;
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals(TAKE_PHOTO))
                        cameraTake();
                    /*else if(userChoosenTask.equals(CHOOSE_PHOTO))
                        galleryTake();*/
                }
                break;
        }
    }

    /*Metodo para verificar permiso de uso de la camara*/
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermissionGalery(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /*Metodo para verificar permiso de uso de la camara*/
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 124;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermissionCamera(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}

/*
me: FATAL EXCEPTION: main
        Process: ismcenter.app.com.gaverapp, PID: 13202
        java.lang.RuntimeException: Failure delivering result ResultInfo{who=null, request=2, result=-1, data=Intent { dat=content://com.google.android.apps.photos.contentprovider/-1/1/content://media/external/images/media/20353/ORIGINAL/NONE/1330521436 flg=0x1 clip={text/uri-list U:content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F20353/ORIGINAL/NONE/1330521436} }} to activity {ismcenter.app.com.gaverapp/ismcenter.app.com.gaverapp.MH_Activity_FilesDetail}: java.lang.SecurityException: Uid 10353 does not have permission to uri 0 @ content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F20353/ORIGINAL/NONE/1330521436
        at android.app.ActivityThread.deliverResults(ActivityThread.java:3778)
        at android.app.ActivityThread.handleSendResult(ActivityThread.java:3821)
        at android.app.ActivityThread.access$1400(ActivityThread.java:168)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1428)
        at android.os.Handler.dispatchMessage(Handler.java:102)
        at android.os.Looper.loop(Looper.java:150)
        at android.app.ActivityThread.main(ActivityThread.java:5665)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:822)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:712)
        Caused by: java.lang.SecurityException: Uid 10353 does not have permission to uri 0 @ content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F20353/ORIGINAL/NONE/1330521436
        at android.os.Parcel.readException(Parcel.java:1602)
        at android.os.Parcel.readException(Parcel.java:1555)
        at android.app.ActivityManagerProxy.startActivity(ActivityManagerNative.java:2659)
        at android.app.Instrumentation.execStartActivity(Instrumentation.java:1518)
        at android.app.Activity.startActivityForResult(Activity.java:4032)


   java.lang.SecurityException: Permission Denial: opening provider android.support.v4.content.FileProvider from ProcessRecord{424e9528 27419:com.sec.android.app.camera/u0a62} (pid=27419, uid=10062) that is not exported from uid 10108
       at android.os.Parcel.readException(Parcel.java:1425)
       at android.os.Parcel.readException(Parcel.java:1379)
       at android.app.ActivityManagerProxy.getContentProvider(ActivityManagerNative.java:2593)
       at android.app.ActivityThread.acquireProvider(ActivityThread.java:4342)
       at android.app.ContextImpl$ApplicationContentResolver.acquireUnstableProvider(ContextImpl.java:1990)
       at android.content.ContentResolver.acquireUnstableProvider(ContentResolver.java:1101)
       at android.content.ContentResolver.openAssetFileDescriptor(ContentResolver.java:616)
       at android.content.ContentResolver.openOutputStream(ContentResolver.java:489)
       at android.content.ContentResolver.openOutputStream(ContentResolver.java:465)
       at com.sec.android.app.camera.Camera$LastContentUriCallback.onCompleted(Camera.java:5729)
       at com.sec.android.app.camera.Camera.onLaunchGallery(Camera.java:5620)
       at com.sec.android.app.camera.Camera.onImageStoringCompleted(Camera.java:5118)
       at com.sec.android.app.camera.CameraEngine.imageStoringCompleted(CameraEngine.java:2280)
       at com.sec.android.app.camera.CeStateInitialized.handleMessage(CeStateInitialized.java:47)
       at com.sec.android.app.camera.CameraEngine$StateMessageHandler.handleMessage(CameraEngine.java:248)
       at android.os.Handler.dispatchMessage(Handler.java:99)
       at android.os.Looper.loop(Looper.java:137)
       at android.app.ActivityThread.main(ActivityThread.java:4944)
       at java.lang.reflect.Method.invokeNative(Native Method)
       at java.lang.reflect.Method.invoke(Method.java:511)
       at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1038)
       at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:805)
       at dalvik.system.NativeStart.main(Native Method)
*/
