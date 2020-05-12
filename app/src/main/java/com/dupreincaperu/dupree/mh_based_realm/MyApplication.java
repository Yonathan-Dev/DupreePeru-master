package com.dupreincaperu.dupree.mh_based_realm;

import android.app.Application;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    /*public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        Log.i(TAG, "onCreate: Realm Object");

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("myrealm.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    private class MyMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            Log.w(TAG, "migrate() called with: " + "realm = [" + realm + "]," +
                    "oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
        }
    }*/
}