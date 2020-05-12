package com.dupreincaperu.dupree.mh_fragments_distribucion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_pasa_prod.dato_gene;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;

public class toma_foto extends AppCompatActivity {

    private final String CARPETA_RAIZ="DupreeDistribucion/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misPedidos";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    Button botonCargar, btn_guar_imag;
    ImageView imagen;
    TextView nume_fact_foto;
    String path;
    String fact_sri="", pedido="";
    String nombreImagen="";
    Bitmap bitmap;
    boolean modo;
    boolean reachable;
    Context contexto;
    RequestQueue request;

    String URL_EMPRESA="";

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toma_foto);

        dato_gene URL = new dato_gene();
        URL_EMPRESA = URL.getURL_EMPRESA();

        contexto = this;
        request= Volley.newRequestQueue(getBaseContext());

        imagen          = (ImageView) findViewById(R.id.imagemId);
        botonCargar     = (Button) findViewById(R.id.btnCargarImg);
        btn_guar_imag   = (Button) findViewById(R.id.btn_guar_imag);
        nume_fact_foto  = (TextView) findViewById(R.id.txt_nume_fact);

        final Drawable imag_anti = imagen.getDrawable();

        isNetDisponible();

        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }

        botonCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFotografia();
            }
        });

        Bundle nume_fact = this.getIntent().getExtras();
        if(nume_fact !=null){
            fact_sri = nume_fact.getString("fact_sri");
            pedido   = nume_fact.getString("nume_fact");
            modo     = nume_fact.getBoolean("modo");
            nume_fact_foto.setText(pedido);
        }

        btn_guar_imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_imagen(imag_anti);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Salir");
        dialogo1.setMessage("¿ Esta seguro que desea salir sin capturar la fotografía?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
                //System.exit(0);
            }
        });
        dialogo1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean validaPermisos() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]== PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }
        }
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(toma_foto.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
                }
            }
        });
        dialogo.show();
    }

    private void tomarFotografia() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(isCreada==false){
            isCreada = fileImagen.mkdirs();
        }
        if(isCreada==true){
            nombreImagen = pedido+".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
            File imagen=new File(path);

            if (imagen.exists()){
                imagen.delete();
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N) {
                //String authorities=getApplicationContext().getPackageName()+".provider";
                String authorities="com.image.lib_image"+".provider";
                Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
            }
            startActivityForResult(intent,COD_FOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imagen.setImageURI(miPath);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(),miPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imagen.setImageBitmap(bitmap);

                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });
                    bitmap= BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);
                    break;
            }
            bitmap = redimensionar_imagen(bitmap, 500, 500);
            //Reduccion de imagen a 15KB
        }
    }


    private Bitmap redimensionar_imagen(Bitmap bitmap, float  anchoNuevo, float altoNuevo) {

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho>anchoNuevo || alto>altoNuevo){
            float escalaancho = anchoNuevo / ancho;
            float escalalto = altoNuevo / alto;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaancho, escalalto);

            return Bitmap.createBitmap(bitmap, 0, 0 , ancho, alto, matrix, false);
        }
        else{
            return bitmap;
        }
    }

    private void guardar_imagen(Drawable imag_anti) {

        if (imagen.getDrawable()==imag_anti){
            Toast.makeText(getBaseContext(),"No has capturado una fotografía !!.",Toast.LENGTH_SHORT).show();
        }
        else {

            if (isNetDisponible() && modo==true) {

                String url = URL_EMPRESA+"distribucion/imag?";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getBaseContext(),response.trim(),Toast.LENGTH_SHORT).show();

                                finish();
                                pdp.dismiss();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pdp.dismiss();
                                Toast.makeText(getBaseContext(),"No se puede conectar con el servidor."+error,Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String imag_captura = ConvertirStringImag (bitmap);
                        Map<String, String>  params = new HashMap<>();
                        params.put("fact_sri", fact_sri);
                        params.put("fac_imag", nombreImagen);
                        params.put("acti_hora_veri", "");
                        params.put("imagen", imag_captura);
                        return params;
                    }
                };

                pdp = new ProgressDialog(this);
                pdp.show();
                pdp.setContentView(R.layout.progress_dialog);
                pdp.setCancelable(false);
                pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_DEFAULT_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                request.add(postRequest);

            } else if(modo == false) {

                MyDbHelper dbHelper = new MyDbHelper(contexto);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor c = db.rawQuery("SELECT fact_sri FROM fac_conf WHERE fact_sri ='"+fact_sri+"' ", null);

                if (c.getCount()>0 && db!=null){
                    db.execSQL("UPDATE fac_conf set fac_imag = '"+path+"' WHERE fact_sri ='"+fact_sri+"'");
                    Toast.makeText(contexto, "La imagen fue almacenada en la memoria SD con exito.",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(contexto, "La imagen no fue almacenada en la memoria SD.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String ConvertirStringImag(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress (Bitmap.CompressFormat.JPEG,100,array);
        byte [] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString (imagenByte, Base64.DEFAULT);
        return imagenString;
    }

    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
}
