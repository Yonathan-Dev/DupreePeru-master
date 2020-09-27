package com.dupreincaperu.dupree.mh_fragments_distribucion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_dial_peru.dialogoCanjes;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;

public class Canjesdevoluciones extends AppCompatActivity {
    String nume_iden="";
    int cont_inic = 0;
    int cont_maxi = 0;
    SearchView sv_codi_prod;
    EditText edt_nomb_prod, edt_codi_prod;
    ImageButton img_remo_cont, img_agre_cont;
    TextView txt_cant_movi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canjesdevoluciones);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            nume_iden = parametros.getString("nume_iden");
            Toast.makeText(getBaseContext(),""+nume_iden, Toast.LENGTH_SHORT).show();
        }
        cantidadMaxima(nume_iden);

        sv_codi_prod  = (SearchView)findViewById(R.id.sv_codi_prod);
        img_agre_cont = (ImageButton)findViewById(R.id.img_agre_cont);
        img_remo_cont = (ImageButton)findViewById(R.id.img_remo_cont);
        txt_cant_movi = (TextView)findViewById(R.id.txt_cant_movi);
        txt_cant_movi.setText(String.valueOf(cont_maxi));
        cont_inic     = Integer.parseInt(txt_cant_movi.getText().toString());

        img_agre_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont_inic < cont_maxi){
                    cont_inic   =   cont_inic + 1;
                    txt_cant_movi.setText(String.valueOf(cont_inic));
                }
            }
        });

        img_remo_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont_inic > 1){
                    cont_inic   =   cont_inic - 1;
                    txt_cant_movi.setText(String.valueOf(cont_inic));
                }
            }
        });

    }

    private void cantidadMaxima(String nume_iden) {
        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT nume_iden FROM canjesdevoluciones WHERE nume_iden = '"+nume_iden+"' ", null);

        if (c.getCount() > 0 && db != null) {
            Cursor d = db.rawQuery("SELECT sum(cant_movi) cant_movi FROM canjesdevoluciones WHERE nume_iden = '"+nume_iden+"' ", null);
            if (d.moveToNext()){
                cont_maxi = Integer.parseInt(String.valueOf(d.getString(0)));
            }
            d.close();
        }
        c.close();
        db.close();
    }


}