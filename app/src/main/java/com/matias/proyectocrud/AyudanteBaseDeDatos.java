package com.matias.proyectocrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
// Importo la clase del contract que contiene los nombres
import com.matias.proyectocrud.PersonasContract.PersonaEntry;

/*
Esta clase me ayuda a crear la base de datos y la tabla.
Es como el plano de la BD.
 */
public class AyudanteBaseDeDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DE_DATOS = "personas_db";
    private static final int VERSION_BASE_DE_DATOS = 1;

    /*
    El constructor, necesita el 'contexto' de la app para saber donde
    crear el archivo de la base de datos.
     */
    public AyudanteBaseDeDatos(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    /*
    Este método se llama solo la primera vez para crear la tabla.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Un comando SQL para crear la tabla 'personas' con su id, nombre y edad.
        // Uso las constantes de la clase Contract, es más ordenado y seguro.
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s integer primary key autoincrement, %s text, %s int)",
                PersonaEntry.NOMBRE_TABLA,
                PersonaEntry.COLUMNA_ID,
                PersonaEntry.COLUMNA_NOMBRE,
                PersonaEntry.COLUMNA_EDAD);
        db.execSQL(sql);
    }

    /*
    Este es para cuando actualice la app en el futuro.
    Como no voy a cambiar la tabla, lo dejo vacio, pero tiene que estar.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No lo necesito para este proyecto.
    }
}
