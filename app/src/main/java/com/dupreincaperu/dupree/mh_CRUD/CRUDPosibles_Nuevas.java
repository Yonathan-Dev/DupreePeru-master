package com.dupreincaperu.dupree.mh_CRUD;

import android.util.Log;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Posibles_Nuevas;
import com.dupreeinca.lib_api_rest.model.view.Profile;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static java.lang.Boolean.TRUE;

public class CRUDPosibles_Nuevas {

    private final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(Posibles_Nuevas.class).max("id");
        int nextId;
        if(currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }

    public static int getUltimoId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(Posibles_Nuevas.class).max("id");
        return number == null ? 0 : number.intValue() + 1;
    }

    public final static void adicionarNueva(final Posibles_Nuevas posibles_nuevas, Profile perfil){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                //Incrementa ID
                int index = getUltimoId();
                Posibles_Nuevas realmPosibles_nuevas = realm.createObject(Posibles_Nuevas.class,index);
                realmPosibles_nuevas.setTipo_docu(posibles_nuevas.getTipo_docu());
                realmPosibles_nuevas.setCedula(posibles_nuevas.getCedula());
                realmPosibles_nuevas.setNombre(posibles_nuevas.getNombre());
                realmPosibles_nuevas.setApellido(posibles_nuevas.getApellido());
                realmPosibles_nuevas.setMovil1(posibles_nuevas.getMovil1());
                realmPosibles_nuevas.setMovil2(posibles_nuevas.getMovil2());
                realmPosibles_nuevas.setEstado("ACT");
                realmPosibles_nuevas.setDireccion(posibles_nuevas.getDireccion());
                realmPosibles_nuevas.setBarrio(posibles_nuevas.getBarrio());
            }
        });
    }




    public  final static boolean existeRegistro(String Cedula)
    {
        boolean hay = false;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Posibles_Nuevas> result = realm.where(Posibles_Nuevas.class).equalTo("cedula",Cedula).findAll();
        if(result.size()!=0)
        {
            hay = true;
        }
        return hay;
    }

    public final static List<Posibles_Nuevas> traeTodas(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Posibles_Nuevas> posibles_nuevas = realm.where(Posibles_Nuevas.class).findAll();
        for(Posibles_Nuevas posible_nueva: posibles_nuevas){
            Log.d("Listado de Nuevas:", "id: "
                    + posible_nueva.getId()
                    +" Cedula:"+posible_nueva.getCedula()
                    +" Nombre:"+posible_nueva.getNombre()
                    +" Apellido:"+posible_nueva.getApellido()
                    +" Movil1:"+posible_nueva.getMovil1()
                    +" Movil2:"+posible_nueva.getMovil2()
                    +" Estado:"+posible_nueva.getEstado()
            );
        }
        return posibles_nuevas;
    }

    public final static List<Posibles_Nuevas> traeTodas(boolean band){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Posibles_Nuevas> posibles_nuevas = realm.where(Posibles_Nuevas.class).equalTo("estado","ACT").findAll();
        return posibles_nuevas;
    }

    public final static List<Posibles_Nuevas> traeTodas(Integer pagina){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Posibles_Nuevas> posibles_nuevas = realm.where(Posibles_Nuevas.class).equalTo("pagina",pagina).findAll();
        for(Posibles_Nuevas posible_nueva: posibles_nuevas){
            Log.d("ListadoNuevas Paginas:", "id: "
                    + posible_nueva.getId()
                    +" Cedula:"+posible_nueva.getCedula()
                    +" Pagina:"+posible_nueva.getPagina()
            );
        }
        return posibles_nuevas;
    }

    public  final static void borrariD(int id)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Posibles_Nuevas> result = realm.where(Posibles_Nuevas.class).equalTo("id",id).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public  final static void borrarNueva(String Cedula)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Posibles_Nuevas> result = realm.where(Posibles_Nuevas.class).equalTo("cedula",Cedula).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public  final static void actualizarEstado()
    {
        Realm realm = Realm.getDefaultInstance();
        List<Posibles_Nuevas> result_nuevas = CRUDPosibles_Nuevas.traeTodas(TRUE);

        realm.beginTransaction();
        for(Posibles_Nuevas fila : result_nuevas)
        {
            fila.setEstado("ENV");
            realm.copyToRealmOrUpdate(fila);
        }
        realm.commitTransaction();
    }

    public  final static void asignePagina()
    {
        Realm realm = Realm.getDefaultInstance();
        List<Posibles_Nuevas> result_nuevas = realm.where(Posibles_Nuevas.class).findAll();

        //paginado de cuatro registros.
        int pagina= 1;
        int cont0= 1;
        realm.beginTransaction();
        for(Posibles_Nuevas fila : result_nuevas)
        {
            fila.setPagina(pagina);
            realm.copyToRealmOrUpdate(fila);
            Log.d("asignePagina()","Comienza Actualización de paginas:");
            Log.d("asignePagina()","Actualiza: "+fila.getCedula()+"-->"+pagina);
            if(cont0 % 4 == 0)
                pagina++;
            cont0++;
        }
        realm.commitTransaction();
    }



}
