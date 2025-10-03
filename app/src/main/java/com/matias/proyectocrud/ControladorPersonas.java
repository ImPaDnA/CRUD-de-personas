package com.matias.proyectocrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
// Importo la clase del contract para usar sus constantes
import com.matias.proyectocrud.PersonasContract.PersonaEntry;
import java.util.ArrayList;

/*
Esta clase es para no poner codigo de la base de datos en las pantallas.
Es mas ordenado, desde aqui manejo el CRUD (crear, leer, actualizar, borrar).
 */
public class ControladorPersonas {

    private AyudanteBaseDeDatos ayudanteBaseDeDatos;

    public ControladorPersonas(Context context) {
        // Necesito el ayudante para poder conectarme a la BD.
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(context);
    }

    // --- Operación CREATE (C) ---
    public long nuevaPersona(Persona persona) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        // Uso las constantes del Contract para poner los datos.
        valores.put(PersonaEntry.COLUMNA_NOMBRE, persona.getNombre());
        valores.put(PersonaEntry.COLUMNA_EDAD, persona.getEdad());

        // El .insert devuelve el id nuevo, o -1 si falla.
        long id = baseDeDatos.insert(PersonaEntry.NOMBRE_TABLA, null, valores);
        baseDeDatos.close();
        return id;
    }

    // --- Operación READ (R) ---
    public ArrayList<Persona> obtenerPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // Defino que columnas quiero usando las constantes del Contract.
        String[] columnas = {
                PersonaEntry.COLUMNA_ID,
                PersonaEntry.COLUMNA_NOMBRE,
                PersonaEntry.COLUMNA_EDAD
        };

        // El Cursor es como un puntero que apunta a los resultados de la consulta.
        // Pido que los ordene por nombre.
        Cursor cursor = baseDeDatos.query(
                PersonaEntry.NOMBRE_TABLA,
                columnas,
                null, null, null, null,
                PersonaEntry.COLUMNA_NOMBRE);

        // Si el cursor no encuentra nada, devuelvo la lista vacía para evitar errores.
        if (cursor == null || !cursor.moveToFirst()) {
            return personas;
        }

        // Recorro todas las filas que encontró el cursor.
        do {
            // Saco los datos de la fila usando las constantes del Contract.
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(PersonaEntry.COLUMNA_ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(PersonaEntry.COLUMNA_NOMBRE));
            int edad = cursor.getInt(cursor.getColumnIndexOrThrow(PersonaEntry.COLUMNA_EDAD));
            // Creo el objeto Persona y lo agrego a la lista que voy a devolver.
            personas.add(new Persona(nombre, edad, id));
        } while (cursor.moveToNext());

        // Cierro todo para no gastar memoria.
        cursor.close();
        baseDeDatos.close();
        return personas;
    }

    // --- Operación UPDATE (U) ---
    public int guardarCambios(Persona persona) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(PersonaEntry.COLUMNA_NOMBRE, persona.getNombre());
        valores.put(PersonaEntry.COLUMNA_EDAD, persona.getEdad());

        // El truco del update es el WHERE, tambien con las constantes del Contract.
        // Le digo que solo actualice la fila que tenga el ID que le paso.
        String campo = PersonaEntry.COLUMNA_ID + " = ?";
        String[] args = {String.valueOf(persona.getId())};

        // El .update devuelve el numero de filas cambiadas. Debería ser 1.
        int filasAfectadas = baseDeDatos.update(PersonaEntry.NOMBRE_TABLA, valores, campo, args);
        baseDeDatos.close();
        return filasAfectadas;
    }

    // --- Operación DELETE (D) ---
    public int eliminarPersona(Persona persona) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        // Para borrar, igual que en el update, uso el WHERE con el ID que me llega.
        String campo = PersonaEntry.COLUMNA_ID + " = ?";
        String[] args = {String.valueOf(persona.getId())};

        // .delete devuelve el número de filas borradas, que deberia ser 1.
        int filasEliminadas = baseDeDatos.delete(PersonaEntry.NOMBRE_TABLA, campo, args);
        baseDeDatos.close();
        return filasEliminadas;
    }
}
