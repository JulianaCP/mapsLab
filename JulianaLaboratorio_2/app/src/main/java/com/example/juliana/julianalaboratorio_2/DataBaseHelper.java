package com.example.juliana.julianalaboratorio_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Juliana on 09/05/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MapsStorage.db";
    //TABLA MARKER
    public static final String TABLE_NAME_MARKER = "Marker";
    //COLUMNAS
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_LATITUD = "latitud";
    public static final String COLUMN_NAME_LONGITUD = "longitud";
    public static final String COLUMN_NAME_DESCRIPCION = "descripcion";
    public static final String COLUMN_NAME_NOMBRE = "nombre";

    //TIPOS DE DATOS
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_TEXT= " TEXT";
    private static final String TYPE_DOUBLE = " DOUBLE";
    private static final String COMMA_SEP = ",";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_MARKER + " (" +
                COLUMN_NAME_ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME_LATITUD + TYPE_DOUBLE + COMMA_SEP +
                COLUMN_NAME_LONGITUD + TYPE_DOUBLE + COMMA_SEP +
                COLUMN_NAME_DESCRIPCION + TYPE_TEXT + COMMA_SEP +
                COLUMN_NAME_NOMBRE + TYPE_TEXT + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ TABLE_NAME_MARKER);
        onCreate(sqLiteDatabase);
    }



    //manejar base

    public boolean insertarMarca(double latitud, double longitud, String descripcion, String nombre){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues registro = new ContentValues();
            //registro.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_ID, null);
            registro.put(COLUMN_NAME_LATITUD, latitud);
            registro.put(COLUMN_NAME_LONGITUD, longitud);
            registro.put(COLUMN_NAME_DESCRIPCION,descripcion);
            registro.put(COLUMN_NAME_NOMBRE,nombre);
            db.insert(TABLE_NAME_MARKER, null, registro);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public ArrayList<Mark> getDbInfo(){
        String query = "select * from "+TABLE_NAME_MARKER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<Mark> nuevaInfo = new ArrayList<Mark>();
        if(cursor.moveToFirst()){
            do{
                Mark elemento = new Mark(
                        Integer.parseInt(cursor.getString(0)),
                        Double.parseDouble(cursor.getString(1)),
                        Double.parseDouble(cursor.getString(2)),
                        cursor.getString(3),
                        cursor.getString(4)
                );



                System.out.println("elememto 0 : "+cursor.getString(0));

                System.out.println("elememto 1 : "+cursor.getString(1));

                System.out.println("elememto 2 : "+cursor.getString(2));

                System.out.println("objetos : "+ elemento);

//                System.out.println("elememto 3 : "+cursor.getString(3));
                nuevaInfo.add(elemento);

            }while (cursor.moveToNext());
        }
        return nuevaInfo;
    }
    public Integer eliminar(double latitude, double longitud){
        System.out.println("eliminar latitude; " + latitude);
        System.out.println("eliminar longitud; " + longitud);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_MARKER,
                COLUMN_NAME_LATITUD+"='"+latitude+"' and " +
                        COLUMN_NAME_LONGITUD +"='"+longitud+"'" ,null);
    }
}
