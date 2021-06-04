package com.dupreincaperu.dupree.mh_bar_scann;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BarcodeCaptureActivity extends AppCompatActivity {
    private static final String TAG = "Barcode-reader";

    private static final int RC_HANDLE_GMS = 9001;

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String BarcodeObject = "Barcode";

    private int iterator;
    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barcode_capture);

        iterator = 0;

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay<BarcodeGraphic>) findViewById(R.id.graphicOverlay);

        // read parameters from the intent used to launch the activity.
        boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, true);
        boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash);
        } else {
            requestCameraPermission();
        }

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Snackbar.make(mGraphicOverlay, "Tap to capture. Pinch/Stretch to zoom",
                Snackbar.LENGTH_LONG)
                .show();
    }


    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = scaleGestureDetector.onTouchEvent(e);

        boolean c = gestureDetector.onTouchEvent(e);

        return b || c || super.onTouchEvent(e);
    }


    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            Log.w(TAG, "Detector dependencies are not yet available.");
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;
            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }

        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        iterator = 0;
        startCameraSource();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
            iterator = 1;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus,false);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            createCameraSource(autoFocus, useFlash);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private boolean onTap(float rawX, float rawY) {
        //clic en el overlay para obtener datos
        //TODO: use the tap position to select the barcode.
        BarcodeGraphic graphic = mGraphicOverlay.getFirstGraphic();
        Barcode barcode = null;
        /*if (graphic != null) {
            barcode = graphic.getBarcode();
            if (barcode != null) {
                Intent data = new Intent();
                data.putExtra(BarcodeObject, barcode);
                setResult(CommonStatusCodes.SUCCESS, data);
                finish();
            }
            else {
                Log.d(TAG, "barcode data is null");
            }
        }
        else {
            Log.d(TAG,"no barcode detected");
        }*/
        return barcode != null;
    }






    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }
    }



    private class AsyncTaskRunner extends AsyncTask<String, String, Barcode> {

        private String resp;

        @Override
        protected Barcode doInBackground(String... params) {
            Barcode barcode=null;
            while (iterator==0){
                try{
                    BarcodeGraphic graphic = mGraphicOverlay.getFirstGraphic();
                    barcode = graphic.getBarcode();
                    if(barcode.rawValue.contains("DSK_")) {
                        iterator = 1;
                    }
                }catch (Exception e){

                }
            }
            return barcode;
        }


        @Override
        protected void onPostExecute(Barcode barcode) {
            //////////////////////////////////////////
            try {
                List<String> lista = new ArrayList<String>();
                lista = verificarCedula(barcode.rawValue, "PDF417");
                String numero = lista.get(0);
                String nombre = lista.get(1);
                String apellido = lista.get(2);
                String prueba = lista.get(3);
                Intent resultData = new Intent();
                resultData.putExtra("number", numero);
                resultData.putExtra("name", nombre);
                resultData.putExtra("lastname", apellido);
                resultData.putExtra("method", "s");
                resultData.putExtra("prueba",prueba);
                setResult(1, resultData);
                finish();
            }catch (Exception e){
                Intent resultData = new Intent();
                resultData.putExtra("number", "Fallo");
                resultData.putExtra("name", "");
                resultData.putExtra("lastname", "");
                resultData.putExtra("method", "s");
                setResult(1, resultData);
                finish();
            }
        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    private List<String> verificarCedula(String resultado, String formato) {
        String resultado2 = null;
        String Nombre =null;
        String Apellido =null;
        resultado2 = resultado;

        String prueba ="1";
        if (formato.contains("PDF417")) {
            Log.e("LineaLeida",""+resultado2);

            int indexSDK_ = resultado.indexOf("DSK_");
            Log.e("indexSDKLimpio",""+indexSDK_);
            if(indexSDK_==-1){
                indexSDK_=558;
            }
            Log.e("indexSDK", String.valueOf(indexSDK_));
            //Busca si es hombre o mujer
            int indexDiv = resultado.indexOf("0M19");
            if(indexDiv==-1){
                indexDiv = resultado.indexOf("0F19");
                if(indexDiv==-1){
                    indexDiv = resultado.indexOf("0M20");
                }if(indexDiv==-1){
                    indexDiv = resultado.indexOf("0F20");
                }
            }
            Log.e("indexMoF", String.valueOf(indexDiv));
            prueba = resultado.substring(indexDiv+2,indexDiv+10);
            prueba = prueba.substring(0,4)+"/"+prueba.substring(4,6)+"/"+prueba.substring(6,8);
            resultado2 = resultado.substring(indexSDK_ + 21, indexDiv);
            Log.e("prueba",prueba);
            try {
                //buscar una letra
                int index = 0;
                for (int i = 1; i <= resultado2.length(); i++) {
                    try {
                        Integer.parseInt(String.valueOf(resultado2.charAt(i)));
                        index = i;
                    } catch (NumberFormatException e) {
                        break;
                    }
                }
                //revision de nombres y apellidos
                String resultado3 = resultado2.substring(index + 1, resultado2.length());
                Log.e("resultado3 sin num", resultado3);
                int contadorcambio = 0;
                int estadoactual = 0, estadoanterior = 1;
                ArrayList<Integer> listInicios = new ArrayList<>();
                ArrayList<Integer> listFinal = new ArrayList<>();
                listInicios.add(0);
                for (int j = 0; j < resultado3.length(); j++) {
                    if (Character.isLetter(resultado3.charAt(j))) {
                        estadoactual = 1;
                        Log.e("CARACTER", String.valueOf(resultado3.charAt(j)));
                    } else {
                        estadoactual = 0;
                        Log.e("CARACTER", String.valueOf(resultado3.charAt(j)));
                    }
                    if (estadoactual != estadoanterior) {
                        contadorcambio++;
                        if (contadorcambio == 0 || contadorcambio == 2 || contadorcambio == 4 || contadorcambio == 6) {
                            Log.e("CAMBIO", "ACA INICIA TEXTO   " + String.valueOf(contadorcambio));
                            listInicios.add(j);
                        } else {
                            Log.e("CAMBIO", "ACA TERMINA TEXTO");
                            listFinal.add(j);
                        }
                    }
                    estadoanterior = estadoactual;
                }
                switch (listInicios.size()) {
                    case 2:
                        Log.e("APELLIDOS", resultado3.substring(listInicios.get(0), listFinal.get(0)));
                        Apellido = resultado3.substring(listInicios.get(0), listFinal.get(0));
                        Log.e("NOMBRE", resultado3.substring(listInicios.get(1), listFinal.get(1)));
                        Nombre = resultado3.substring(listInicios.get(1), listFinal.get(1));
                        break;
                    case 4:
                        Log.e("APELLIDOS", resultado3.substring(listInicios.get(0), listFinal.get(0)) + resultado3.substring(listInicios.get(1), listFinal.get(1)));
                        Apellido = resultado3.substring(listInicios.get(0), listFinal.get(0)) + " " + resultado3.substring(listInicios.get(1), listFinal.get(1));
                        Log.e("NOMBRE", resultado3.substring(listInicios.get(2), listFinal.get(2)) + resultado3.substring(listInicios.get(3), listFinal.get(3)));
                        Nombre = resultado3.substring(listInicios.get(2), listFinal.get(2)) + " " + resultado3.substring(listInicios.get(3), listFinal.get(3));
                        break;
                    case 3:
                        Log.e("APELLIDOS", resultado3.substring(listInicios.get(0), listFinal.get(0)) + resultado3.substring(listInicios.get(1), listFinal.get(1)));
                        Apellido = resultado3.substring(listInicios.get(0), listFinal.get(0)) + " " + resultado3.substring(listInicios.get(1), listFinal.get(1));
                        Log.e("NOMBRE", resultado3.substring(listInicios.get(2), listFinal.get(2)));
                        Nombre = resultado3.substring(listInicios.get(2), listFinal.get(2));
                        break;
                }
                if (listInicios.size() > 4) {
                    for (int k = 0; k < listInicios.size(); k++) {
                        if (k == 0) {
                            Apellido = resultado3.substring(listInicios.get(k), listFinal.get(k));
                        }
                        if (k == 1) {
                            Apellido = Apellido + resultado3.substring(listInicios.get(k), listFinal.get(k));
                        }
                        if (k >= 2) {
                            Nombre = Nombre + resultado3.substring(listInicios.get(k), listFinal.get(k));
                        }
                    }
                }
                Log.e("resultado2","Numeros:  "+resultado2);
                resultado2 = resultado2.substring(0, index + 1);
                try {
                    resultado2 = String.valueOf(Integer.parseInt(resultado2));
                } catch (Exception e) {

                }
                Log.e("Nombres",Nombre);
                Log.e("Apellidos",Apellido);
            }catch (Exception e){
                Log.e("ERROR","ERROR EN EL TRY");
                resultado2="Fallo en la lectura, intente nuevamente";
                Nombre="";
                Apellido="";
            }

        }else{
            resultado2="Fallo en la lectura, intente nuevamente";
            Nombre="";
            Apellido="";

        }
        List<String> lista = new ArrayList<String>();
        lista.add(resultado2);
        lista.add(Nombre);
        lista.add(Apellido);
        lista.add(prueba);
        return lista;
    }

}
