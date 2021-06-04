package com.dupreincaperu.dupree;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.dupreincaperu.dupree.mh_fragments_azzortimaps.Fragmento_list_ases;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Oferta;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.databinding.ActivityMenuBinding;
import com.dupreincaperu.dupree.mh_adapters.IncorporacionPagerAdapter;
import com.dupreincaperu.dupree.mh_adapters.NuevasPagerAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_fragments_asesora.Fragmento_repo_ases_deud;
import com.dupreincaperu.dupree.mh_fragments_cobranza.Fragmento_ubic_ases;
import com.dupreincaperu.dupree.mh_fragments_main.CatalogosAdvanceFragment;
import com.dupreincaperu.dupree.mh_fragments_main.CatalogosFragment;
import com.dupreincaperu.dupree.mh_fragments_main.ContactFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.BandejaEntradaFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.CatalogoPremiosFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ConfigModPerfilFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.IncentivosConsultaPtosFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.IncentivosRedimirFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.IncentivosReferidoFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.LocalizacionHelper;
import com.dupreincaperu.dupree.mh_fragments_menu.PanelGerenteFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.PedidosEdoPedidoFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.PedidosFaltantesFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ReporteRetenidosFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.Servicios_PagosOnLine_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.UbicacionFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.Incorp_Nuevas_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.Incorp_Todos_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.panel_asesoras.PanelAsesoraFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.HacerPedidoFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.HacerPrePedidoFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.reportes.ReportesFragment;
import com.dupreincaperu.dupree.mh_fragments_ventas.Fragmento_indi_vent;
import com.dupreincaperu.dupree.mh_hardware.Camera;
import com.dupreincaperu.dupree.mh_http.Http;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;
import com.dupreincaperu.dupree.mh_utilities.KeyBoard;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.dupreincaperu.dupree.view.activity.BaseActivity;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MenuActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MenuListener {
    public static final String TAG = MenuActivity.class.getName();
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 507;
    private LocalizacionHelper location;

    private ActivityMenuBinding binding;

    public MenuActivity(){
    }

    View hview;
    TextView usua, corr;
    Camera camera;
    private Profile perfil;
    String codi_prog[];
    String nomb_comp, corr_empl, tipo_vinc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cargarpreferencias();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        perfil = getTypePerfil();
        //enable and disable
        guardarpreferencia();

        binding.navView.setNavigationItemSelectedListener(this);

        binding.appBarMenu.toolbar.setTitle("Panel");
        setSupportActionBar(binding.appBarMenu.toolbar);

        binding.appBarMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.appBarMenu.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                KeyBoard.hide(MenuActivity.this);
            }
        };
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //Para el uso de imagenes
        camera = new Camera(MenuActivity.this);
        showMenuByPerfil(binding.navView);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        hview = navigationView.getHeaderView(0);
        usua = (TextView) hview.findViewById(R.id.txt_prin_usua);
        corr = (TextView) hview.findViewById(R.id.txt_prin_corr);

        navigationView.setNavigationItemSelectedListener(this);
        usua.setText(nomb_comp);
        corr.setText(corr_empl);

        //MODULOS DESARROLLADOS EN PERU.
        navigationView.getMenu().findItem(R.id.repo_ases_deud).setVisible(false);
        navigationView.getMenu().findItem(R.id.indi_vent).setVisible(false);
        navigationView.getMenu().findItem(R.id.list_ases).setVisible(false);
        navigationView.getMenu().findItem(R.id.ubic_ases).setVisible(false);
        navigationView.getMenu().findItem(R.id.aula_virt).setVisible(false);

        //MODULOS DESARROLLADOS
        navigationView.getMenu().findItem(R.id.menu_lat_hacer_pedidos).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_posi_ase).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_list_posi_ase).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_hacer_prepedidos).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_register).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_list_pre).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_catalogo).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_cat_premios).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_faltantes).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_consulta_puntos).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_incent_por_referido).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_reportes).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_pedidos_ret).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_bandeja_entrada).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_reporte_ubicacion).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_pet_quej_rec_pqr).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_pagos_linea).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_edo_pedido).setVisible(false);
        navigationView.getMenu().findItem(R.id.menu_lat_modif_perfil).setVisible(false);

        cargar_modulos();

        if (codi_prog != null){
            int tamano = codi_prog.length;
            while (tamano>=1){
                switch (String.valueOf(codi_prog[tamano-1]).trim()){
                    case "REPO_ASES_DEUD":
                        navigationView.getMenu().findItem(R.id.repo_ases_deud).setVisible(true);
                        break;
                    case "INDI_VENT":
                        navigationView.getMenu().findItem(R.id.indi_vent).setVisible(true);
                        break;
                    case "LIST_ASES":
                        navigationView.getMenu().findItem(R.id.list_ases).setVisible(true);
                        break;
                    case "UBIC_ASES":
                        navigationView.getMenu().findItem(R.id.ubic_ases).setVisible(true);
                        break;
                    case "MENU_LAT_HACER_PEDIDOS":
                        navigationView.getMenu().findItem(R.id.menu_lat_hacer_pedidos).setVisible(true);
                        break;
                    case "MENU_LAT_POSI_ASE":
                        navigationView.getMenu().findItem(R.id.menu_lat_posi_ase).setVisible(true);
                        break;
                    case "LISTA_DE_POSIBLES_ASESORAS":
                        navigationView.getMenu().findItem(R.id.menu_lat_list_posi_ase).setVisible(true);
                        break;
                    case  "MENU_LAT_HACER_PREPEDIDOS":
                        navigationView.getMenu().findItem(R.id.menu_lat_hacer_prepedidos).setVisible(true);
                        break;
                    case  "MENU_LAT_REGISTER":
                        navigationView.getMenu().findItem(R.id.menu_lat_register).setVisible(true);
                        break;
                    case  "MENU_LAT_LIST_PRE":
                        navigationView.getMenu().findItem(R.id.menu_lat_list_pre).setVisible(true);
                        break;
                    case  "MENU_LAT_CATALOGO":
                        navigationView.getMenu().findItem(R.id.menu_lat_catalogo).setVisible(true);
                        break;
                    case  "MENU_LAT_CAT_PREMIOS":
                        navigationView.getMenu().findItem(R.id.menu_lat_cat_premios).setVisible(true);
                        break;
                    case  "MENU_LAT_FALTANTES":
                        navigationView.getMenu().findItem(R.id.menu_lat_faltantes).setVisible(true);
                        break;
                    case  "MENU_LAT_CONSULTA_PUNTOS":
                        navigationView.getMenu().findItem(R.id.menu_lat_consulta_puntos).setVisible(true);
                        break;
                    case  "MENU_LAT_INCENT_POR_REFERIDO":
                        navigationView.getMenu().findItem(R.id.menu_lat_incent_por_referido).setVisible(true);
                        break;
                    case  "MENU_LAT_REPORTES":
                        navigationView.getMenu().findItem(R.id.menu_lat_reportes).setVisible(true);
                        break;
                    case  "MENU_LAT_PEDIDOS_RET":
                        navigationView.getMenu().findItem(R.id.menu_lat_pedidos_ret).setVisible(true);
                        break;
                    case  "MENU_LAT_HOME_GERENTES":
                        navigationView.getMenu().findItem(R.id.menu_lat_home_gerentes).setVisible(true);
                        break;
                    case  "MENU_LAT_BANDEJA_ENTRADA":
                        navigationView.getMenu().findItem(R.id.menu_lat_bandeja_entrada).setVisible(true);
                        break;
                    case  "MENU_LAT_REPORTE_UBICACION":
                        navigationView.getMenu().findItem(R.id.menu_lat_reporte_ubicacion).setVisible(true);
                        break;
                    case  "MENU_LAT_HOME_ASESORAS":
                        navigationView.getMenu().findItem(R.id.menu_lat_home_asesoras).setVisible(true);
                        break;
                    case  "MENU_LAT_REDIMIR_INCENTIVOS":
                        navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(true);
                        break;
                    case  "MENU_LAT_PET_QUEJ_REC_PQR":
                        navigationView.getMenu().findItem(R.id.menu_lat_pet_quej_rec_pqr).setVisible(true);
                        break;
                    case  "MENU_LAT_PAGOS_LINEA":
                        navigationView.getMenu().findItem(R.id.menu_lat_pagos_linea).setVisible(true);
                        break;
                    case  "MENU_LAT_EDO_PEDIDO":
                        navigationView.getMenu().findItem(R.id.menu_lat_edo_pedido).setVisible(true);
                        break;
                    case "MENU_LAT_MODIF_PERFIL" :
                        navigationView.getMenu().findItem(R.id.menu_lat_modif_perfil).setVisible(true);
                        break;
                    case "AULA_VIRT" :
                        navigationView.getMenu().findItem(R.id.servicios).setVisible(true);
                        navigationView.getMenu().findItem(R.id.aula_virt).setVisible(true);
                        break;

                    case "CLIE":
                        navigationView.getMenu().findItem(R.id.menu_lat_bandeja_entrada).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_reporte_ubicacion).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_hacer_pedidos).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_hacer_prepedidos).setVisible(true);
                        navigationView.getMenu().findItem(R.id.menu_lat_catalogo).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_cat_premios).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_faltantes).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_consulta_puntos).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_incent_por_referido).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_reportes).setVisible(true);
                        //navigationView.getMenu().findItem(R.id.menu_lat_pet_quej_rec_pqr).setVisible(true);
                        navigationView.getMenu().findItem(R.id.menu_lat_modif_perfil).setVisible(true);
                        if (perfil.getPerfil().equalsIgnoreCase("L")){
                            /*navigationView.getMenu().findItem(R.id.menu_lat_pedidos_ret).setVisible(true);
                            navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(false);
                            navigationView.getMenu().findItem(R.id.menu_lat_posi_ase).setVisible(true);
                            navigationView.getMenu().findItem(R.id.menu_lat_list_posi_ase).setVisible(true);
                            navigationView.getMenu().findItem(R.id.servicios).setVisible(true);
                            navigationView.getMenu().findItem(R.id.aula_virt).setVisible(true);
                            navigationView.getMenu().findItem(R.id.menu_lat_hacer_pedidos).setVisible(true);*/
                            navigationView.getMenu().findItem(R.id.menu_lat_register).setVisible(true);
                            navigationView.getMenu().findItem(R.id.menu_lat_list_pre).setVisible(true);
                        }
                        break;
                }
                tamano--;
            }
        }


    }

    private void cargar_modulos(){

        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();

        int i=0;
        if (db!=null){
            Cursor c = db.rawQuery(" SELECT codi_prog FROM tab_prog_movi ", null);
            if (c.getCount()>0){
                codi_prog = new String[c.getCount()];
                if (c.moveToNext()){
                    do {
                        codi_prog[i] = c.getString(0);
                        i=i+1;
                    } while (c.moveToNext());
                }
            }
        }
    }


    private void showMenuByPerfil(NavigationView navigationView){
        //Toast.makeText(getBaseContext(), ""+perfil.getPerfil(), Toast.LENGTH_SHORT).show();

        switch (perfil.getPerfil()){
            case Profile.ADESORA:
                navigationView.getMenu().findItem(R.id.menu_lat_home_asesoras).setVisible(true);
                navigationView.getMenu().findItem(R.id.menu_lat_home_gerentes).setVisible(false);
                navigationView.getMenu().findItem(R.id.posibles_asesoras).setVisible(false);
                navigationView.getMenu().findItem(R.id.incorporaciones).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(true);
//                navigationView.getMenu().findItem(R.id.menu_lat_cupo_saldo_conf).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_pedidos_ret).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_edo_pedido).setVisible(false);

                navigationView.getMenu().findItem(R.id.servicios).setVisible(true);

               onNavigationItemSelected(navigationView.getMenu().findItem(R.id.menu_lat_home_asesoras));

                break;
            case Profile.TRASNPORTISTA:
                navigationView.getMenu().findItem(R.id.menu_lat_home_asesoras).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_home_gerentes).setVisible(false);
                navigationView.getMenu().findItem(R.id.posibles_asesoras).setVisible(false);
                navigationView.getMenu().findItem(R.id.incorporaciones).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(false);
//              navigationView.getMenu().findItem(R.id.menu_lat_cupo_saldo_conf).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_pedidos_ret).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_edo_pedido).setVisible(false);

                navigationView.getMenu().findItem(R.id.servicios).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_hacer_pedidos).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_hacer_prepedidos).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_catalogo).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_cat_premios).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_faltantes).setVisible(false);

                navigationView.getMenu().findItem(R.id.menu_lat_consulta_puntos).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_incent_por_referido).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_reportes).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_pedidos_ret).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_pet_quej_rec_pqr).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_pagos_linea).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_update).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_share).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_modif_perfil).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_bandeja_entrada).setVisible(false);


                navigationView.getMenu().findItem(R.id.menu_lat_logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.menu_lat_reporte_ubicacion).setVisible(true);


                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.menu_lat_reporte_ubicacion));
                break;
            case Profile.LIDER:
            case Profile.GERENTE_ZONA:
            case Profile.GERENTE_REGION:
                navigationView.getMenu().findItem(R.id.menu_lat_home_asesoras).setVisible(false);
                navigationView.getMenu().findItem(R.id.menu_lat_home_gerentes).setVisible(true);
                navigationView.getMenu().findItem(R.id.incorporaciones).setVisible(true);
                navigationView.getMenu().findItem(R.id.menu_lat_redimir_incentivos).setVisible(false);
//                navigationView.getMenu().findItem(R.id.menu_lat_cupo_saldo_conf).setVisible(true);
                navigationView.getMenu().findItem(R.id.menu_lat_pedidos_ret).setVisible(true);

                navigationView.getMenu().findItem(R.id.menu_lat_edo_pedido).setVisible(false);

                navigationView.getMenu().findItem(R.id.servicios).setVisible(false);

                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.menu_lat_home_gerentes));
                break;

        }
    }

    private Profile getTypePerfil(){
        String jsonPerfil = mPreferences.getJSON_TypePerfil(MenuActivity.this);
        if(jsonPerfil != null){
            return new Gson().fromJson(jsonPerfil, Profile.class);
        }
        msgToast("Hubo un problema desconocido, se recomienda cerrar sesión e iniciar nuevamente");
        return null;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    Fragment fragmentoGenerico;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
            item.setChecked(false);
            fragmentoGenerico = null;
            // Handle navigation view item clicks here.
            setFragment(item);

            binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setFragment(MenuItem menuItem) {
       FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItem.getItemId()) {
            case R.id.menu_lat_home_asesoras:
                fragmentoGenerico = new PanelAsesoraFragment();
                ((PanelAsesoraFragment) fragmentoGenerico).loadData(perfil);
                break;
            case R.id.menu_lat_home_gerentes:
                fragmentoGenerico = new PanelGerenteFragment();
                break;
            case R.id.menu_lat_bandeja_entrada:
                fragmentoGenerico = new BandejaEntradaFragment();
                break;
            //reporte ubicación asesora
            case R.id.menu_lat_reporte_ubicacion:
                requeridPermission();
                fragmentoGenerico = new UbicacionFragment();
                ((UbicacionFragment) fragmentoGenerico).loadData(perfil);
                break;
            case R.id.menu_lat_register:
                fragmentoGenerico = new Incorp_Todos_Fragment();//hay que validar para que caiga en la pagina correcta
                ((Incorp_Todos_Fragment) fragmentoGenerico).loadData(IncorporacionPagerAdapter.PAGE_PREINSCRIPCION, perfil);
                break;
            case R.id.menu_lat_list_pre:
                fragmentoGenerico = new Incorp_Todos_Fragment();//hay que validar para que caiga en la pagina correcta
                ((Incorp_Todos_Fragment) fragmentoGenerico).loadData(IncorporacionPagerAdapter.PAGE_LIST_PRE, perfil);
                break;
            case R.id.menu_lat_posi_ase:
                fragmentoGenerico = new Incorp_Nuevas_Fragment();//hay que validar para que caiga en la pagina correcta
                ((Incorp_Nuevas_Fragment) fragmentoGenerico).loadData(NuevasPagerAdapter.PAGE_POSI_ASES, perfil);
                break;
            case R.id.menu_lat_list_posi_ase:
                fragmentoGenerico = new Incorp_Nuevas_Fragment();//hay que validar para que caiga en la pagina correcta
                ((Incorp_Nuevas_Fragment) fragmentoGenerico).loadData(NuevasPagerAdapter.PAGE_LIST_POSI_ASES, perfil);
                break;
            case R.id.menu_lat_hacer_pedidos:
                fragmentoGenerico = new HacerPedidoFragment();
                ((HacerPedidoFragment) fragmentoGenerico).loadData(perfil);
                break;
            case R.id.menu_lat_hacer_prepedidos:
                 fragmentoGenerico = new HacerPrePedidoFragment();
                ((HacerPrePedidoFragment) fragmentoGenerico).loadData(perfil);
                 break;
            case R.id.menu_lat_catalogo:
                if (perfil.isCatalogo()) {
                    fragmentoGenerico = new CatalogosAdvanceFragment();
                } else {
                    fragmentoGenerico = new CatalogosFragment();
                }
                break;
            case R.id.menu_lat_cat_premios:
                fragmentoGenerico = new CatalogoPremiosFragment();
                break;
            //LISTO PARA PRUEBAS ( TODOS - INDEPENDIENTE )
            case R.id.menu_lat_faltantes:
                fragmentoGenerico = new PedidosFaltantesFragment();
                break;
            case R.id.menu_lat_edo_pedido:
                fragmentoGenerico = new PedidosEdoPedidoFragment();
                break;
            //LISTO PARA PRUEBAS ( TODOS - GERENTE BUSCAR ASESORA )
            case R.id.menu_lat_consulta_puntos:
                fragmentoGenerico = new IncentivosConsultaPtosFragment();
                ((IncentivosConsultaPtosFragment) fragmentoGenerico).loadData(perfil);
                break;
            //LISTO PARA PRUEBAS ( SOLO ASESORAS )
            case R.id.menu_lat_redimir_incentivos:
                fragmentoGenerico = new IncentivosRedimirFragment();
                break;
            //LISTO PARA PRUEBAS ( TODOS - GERENTE BUSCAR ASESORA )
            case R.id.menu_lat_incent_por_referido:
                fragmentoGenerico = new IncentivosReferidoFragment();
                ((IncentivosReferidoFragment) fragmentoGenerico).loadData(perfil);
                break;
            //LISTO PARA PRUEBAS ( TODOS - GERENTE BUSCAR ASESORA )
            case R.id.menu_lat_reportes:
                fragmentoGenerico = new ReportesFragment();
//                ((ReportesFragment)fragmentoGenerico).loadData(perfil);
                break;
            case R.id.menu_lat_pedidos_ret:
                //LISTO PARA PRUEBAS ( SOLO GERENTE )
//                enableSearch(!perfil.getPerfil().equals(Profile.ADESORA),false,false,getString(R.string.cedula_asesora));
                fragmentoGenerico = new ReporteRetenidosFragment();
                break;
            case R.id.menu_lat_pet_quej_rec_pqr:
                //LISTO PARA PRUEBAS ( PAGINA )
                //fragmentoGenerico = new Servicios_PetQueRec_Fragment();// es el mismo del menu ppal
                fragmentoGenerico = new ContactFragment();
                break;
            case R.id.menu_lat_pagos_linea:
                //LISTO PARA PRUEBAS ( NO ESTA DEFINIDO )
                fragmentoGenerico = new Servicios_PagosOnLine_Fragment();
                break;
            case R.id.menu_lat_share:
                //LISTO PARA PRUEBAS ( COMPARTIR APP )
                testShare();
                //break;
                return;
            case R.id.menu_lat_modif_perfil:
                //LISTO PARA PRUEBAS ( EDITAR DATOS DE USUARIO )
                fragmentoGenerico = new ConfigModPerfilFragment();
                break;
            case R.id.menu_lat_update:
                checkUpdate();
                //break;
                return;
            case R.id.menu_lat_logout:
                testLogOut();
                //break;
                return;
            //Agregado por Perú
            case  R.id.repo_ases_deud:
                fragmentoGenerico = new Fragmento_repo_ases_deud();
                break;
            case  R.id.indi_vent:
                fragmentoGenerico = new Fragmento_indi_vent();
                break;
            case  R.id.list_ases:
                fragmentoGenerico = new Fragmento_list_ases();
                break;
            case  R.id.ubic_ases:
                fragmentoGenerico = new Fragmento_ubic_ases();
                break;
            case R.id.aula_virt:
                Uri uri = Uri.parse("https://pluton.dupree.pe/login/index.php");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }

        if (fragmentoGenerico != null) {
            String title = menuItem.getTitle().toString();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, fragmentoGenerico, menuItem.getTitle().toString())// se reconoce al fragment por el String
                    .commit();

//            binding.appBarMenu.toolbar.setTitle(menuItem.getItemId()==R.id.menu_lat_hacer_pedidos ? "Ingrese código ->" : menuItem.getTitle());
              binding.appBarMenu.toolbar.setTitle(menuItem.getTitle());
        }
    }

    private void requeridPermission() {
        //Se realiza la petición de permisos para dispositivos con OS >= 6.0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Se tiene permiso
               return;
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // El usuario acepto los permisos.
                    Toast.makeText(this, R.string.gps_permission_granted, Toast.LENGTH_SHORT).show();
                    showMessageGPSNoEnabled("Tu servicio de ubicación está desactivado, debes habilitarlo para poder recibir servicios, ¿Desea activarlo?");
                }else{
                    // Permiso denegado.
                    Toast.makeText(this, R.string.no_gps_permissions, Toast.LENGTH_SHORT).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        try{
            camera.onRequestPermissionsResultHand(requestCode, permissions, grantResults);
        }catch (Exception ex){

        }
    }

    private void showMessageGPSNoEnabled(String message) {
        androidx.appcompat.app.AlertDialog alert = null;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_alert_tittle_gps_enable))
                .setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void actualizar(){
        mPreferences.setUpdateBannerAndPDF(MenuActivity.this, true);
        startActivity(new Intent(MenuActivity.this, FullscreenActivity.class));
        finish();
    }

    private void checkUpdate(){
        Http http = new Http(this);
        http.setGenericListener(new Http.GenericListener() {
            @Override
            public void onProcess(String version) {
                if(version.equals(mPreferences.getVersionCatalogo(MenuActivity.this))){
                    Toast.makeText(MenuActivity.this, getResources().getString(R.string.no_hay_actualizaciones), Toast.LENGTH_LONG).show();
                } else {
                    testUpdate();
                }
            }

            @Override
            public void onFailed() {
                Toast.makeText(MenuActivity.this, getResources().getString(R.string.http_error_desconocido), Toast.LENGTH_LONG).show();
            }
        });
        http.getVersion();
    }

    public void testLogOut(){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.salir), getString(R.string.desea_salir));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    logout();
            }
        });
        simpleDialog.show(getSupportFragmentManager(),"mDialog");
    }

    public void testShare(){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.compartir), getString(R.string.desea_compartir));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    shareApp();
            }
        });
        simpleDialog.show(getSupportFragmentManager(),"mDialog");
    }

    public void testUpdate(){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.salir), getString(R.string.desea_actualizar));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    actualizar();
            }
        });
        simpleDialog.show(getSupportFragmentManager(),"mDialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume()");
        if (location == null) {
            requeridPermission();
        } else {
            // Resuming the periodic location updates
            if (location != null && location.mGoogleApiClient.isConnected()) {
                location.startLocationUpdates();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause()");
    }

    private void logout(){
        mPreferences.cerrarSesion(MenuActivity.this);
        clearCartDB();
        deleteOferta();
        startActivity(new Intent(MenuActivity.this, MainActivity.class));
        finish();
    }

    public void clearCartDB(){
        RealmResults<Catalogo> querycat;
        List<Catalogo> catalogoList = new ArrayList<>();
         Log.v(TAG,"clearCartDB... ---------------clearCartDB--------------");
        realm.beginTransaction();
        try {
            catalogoList.clear();
            querycat = realm.where(Catalogo.class)
                    .beginGroup()
                    .greaterThan("cantidad",0)
                    .or()
                    .greaterThan("cantidadServer",0)
                    .endGroup()
                    .findAllSorted("time").sort("time", Sort.DESCENDING);
            catalogoList.addAll(querycat);

            for (int i = 0; i < catalogoList.size(); i++) {
                catalogoList.get(i).setCantidad(0);
                catalogoList.get(i).setCantidadServer(0);
            }

            realm.commitTransaction();
        } catch(Throwable e) {
            Log.v(TAG,"clearCartDB... ---------------error--------------");
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            throw e;
        }
    }

    //SE DEBERIA BORRAR TODAS LAS OFERTAS
    public void clearOffersDB(){
        RealmResults<Oferta> querycat;
        List<Oferta> ofertaList = new ArrayList<>();
        Log.v(TAG,"clearOffersDB... ---------------clearOffersDB--------------");
        realm.beginTransaction();
        try {
            ofertaList.clear();
            querycat = realm.where(Oferta.class)
                    .beginGroup()
                    .greaterThan("cantidad",0)
                    .or()
                    .greaterThan("cantidadServer",0)
                    .endGroup()
                    .findAllSorted("time").sort("time", Sort.DESCENDING);
            //.findAll();
            ofertaList.addAll(querycat);

            for (int i = 0; i < ofertaList.size(); i++) {
                ofertaList.get(i).setCantidad(0);
                ofertaList.get(i).setCantidadServer(0);
            }

            realm.commitTransaction();
        } catch(Throwable e) {
            Log.v(TAG,"clearOffersDB... ---------------error--------------");
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            throw e;
        }
    }

    public void deleteOferta(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.delete(Oferta.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v(TAG,"deleteOferta... ---------------ok--------------");
                //realm.close();
                //writeOfertas(listServer);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e(TAG,"deleteOferta... ---------------error--------------");
                Log.e(TAG,"deleteOferta... "+error.getMessage());
                //realm.close();

            }
        });
    }

    private void shareApp(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, " Hola! Encontré un app donde puedes interactuar con Dupreé by Azzorti, entra a https://play.google.com/store/apps/details?id=com.dupreincaperu.dupree");
        startActivity(Intent.createChooser(intent, "Compartir con"));
        Log.e(TAG,"Compartir");
    }

    private void msgToast(String msg){
        Log.e("Toast", msg);
        Toast.makeText(MenuActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * CAMARA
     *
     *
     */
    public AlertDialog takeImage(String tagFragment, String objectFragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        camera.setBroadcast(tagFragment, objectFragment);

        builder.setTitle("Cargar imagen")
                .setMessage("Seleccione el origen de su imagen")
                .setPositiveButton("Cámara",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("Image - Base64","11111111");
                                boolean result=camera.checkPermissionCamera(MenuActivity.this);
                                camera.setUserChoosenTask(Camera.TAKE_PHOTO);
                                if(result) {
                                    Log.e("Image - Base64","333333333");
                                    camera.cameraTake();
                                }
                            }
                        })
                .setNegativeButton("Galería",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result = camera.checkPermissionGalery(MenuActivity.this);
                                camera.setUserChoosenTask(Camera.CHOOSE_PHOTO);
                                if (result)
                                    camera.galleryTake();
                            }
                        })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e(TAG, "onActivityResult() de MenuActivity");

        Log.e("requestCode:",Integer.toString(requestCode));
        Log.e("resultCode:",Integer.toString(resultCode));


        super.onActivityResult(requestCode, resultCode, data);
        //SE ATRAPA SOLO EL ULTIMO CASO DE IMAGEN LISTA
        Uri uri=null;
        switch (requestCode){
            case Camera.PIC_CROP:
                if(data!=null) {
                    Bundle extras = data.getExtras();
                    if(extras!=null) {
                        Bitmap thePic = (Bitmap) extras.get("data");
                        publishResult(camera.getTagFragment(), camera.getObjectFragment(), data.getData().getPath());
                    }
                }
                break;
            case Camera.CAMERA_CAPTURE:
                uri = Camera.picUri;

                Log.i(TAG,"Camera.CAMERA_CAPTURE: uri=: "+uri.toString());
                Log.i(TAG,"Camera.CAMERA_CAPTURE: uri=: "+camera.getRealPathFromURI(MenuActivity.this, uri));

                Log.i(TAG,"Camera.CAMERA_CAPTURE: getPath=: "+Camera.imageFile.getPath());
                Log.i(TAG,"Camera.CAMERA_CAPTURE: getAbsolutePath=: "+Camera.imageFile.getAbsolutePath());

                publishResult(camera.getTagFragment(), camera.getObjectFragment(), Camera.imageFile.getPath());
                break;
            case Camera.PICK_IMAGE_REQUEST:
                uri = data.getData();
                Log.i(TAG,"Camera.PICK_IMAGE_REQUEST: uri=: "+uri.toString());
                Log.i(TAG,"Camera.PICK_IMAGE_REQUEST: uri=: "+camera.getRealPathFromURI(MenuActivity.this, uri));
                Log.i(TAG,"onActivityResult: data.getData().getPath()=: "+data.getData().getPath());

                publishResult(camera.getTagFragment(), camera.getObjectFragment(), camera.getRealPathFromURI(MenuActivity.this, uri));
                break;
        }
    }

    private void publishResult(String tagFrangment, String objectFragment, String data){
        Log.i(TAG,"publishResult: "+tagFrangment+" - "+objectFragment+" - "+data);
        LocalBroadcastManager.getInstance(MenuActivity.this).sendBroadcast(
                new Intent(tagFrangment)
                        .putExtra(tagFrangment, objectFragment)
                        .putExtra(Camera.BROACAST_DATA, data));
    }

    private void guardarpreferencia() {
        String tipo_usua = "";
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        if (!perfil.getPerfil().equalsIgnoreCase(null))
            tipo_usua    = perfil.getPerfil();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tipo_usua",  tipo_usua);

        editor.commit();
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String nomb_comp = preferences.getString("nomb_comp","");
        String corr_empl = preferences.getString("corr_empl","");
        this.nomb_comp = nomb_comp;
        this.tipo_vinc = preferences.getString("tipo_vinc","");;

        if (corr_empl.equalsIgnoreCase("null"))
            this.corr_empl = "";
        else
            this.corr_empl = corr_empl;

    }

    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    //MARK: MenuListener
    @Override
    public void gotoMenuPage(int idMenuItem) {
        onNavigationItemSelected(binding.navView.getMenu().findItem(idMenuItem));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (location != null && location.mGoogleApiClient.isConnected()) {
            location.mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected  void onStart() {
        super.onStart();
        if (location != null && location.mGoogleApiClient != null) {
            location.mGoogleApiClient.connect();
        }
    }

}
