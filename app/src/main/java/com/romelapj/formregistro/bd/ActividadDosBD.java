package com.romelapj.formregistro.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by romelapj on 7/05/15.
 */
public class ActividadDosBD extends SQLiteOpenHelper {

    private static final int VERSION_BASEDATOS = 1;

    private static final String NOMBRE_BASEDATOS = "actividaddos.db";

    private static final String TABLA_USUARIOS = "CREATE TABLE usuarios" +
            "(usuario TEXT PRIMARY KEY,   password TEXT)";

    public ActividadDosBD(Context context){
        super(context,NOMBRE_BASEDATOS,null,VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }
}
