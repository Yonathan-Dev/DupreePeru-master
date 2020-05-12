package com.dupreincaperu.dupree;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by marwuinh@gmail.com on 5/9/17.
 */
//MultiDexApplication
public class BaseAPP extends Application {

    private static final String TAG = BaseAPP.class.getName();
    private static Context context;
    private static String campana;

    public String getCampana() {

        return campana;
    }

    public void setCampana(String campana) {
        campana = campana;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();

        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()// si hay un cambio en la estructura de la base de
                // datos, la elimina, tras una actualizacion de la app
                .name("myrealm2.realm")
                .build();


        Realm.setDefaultConfiguration(config);


    }
}
