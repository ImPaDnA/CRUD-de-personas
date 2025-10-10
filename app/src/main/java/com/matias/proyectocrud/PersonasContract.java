package com.matias.proyectocrud;

import android.provider.BaseColumns;

/*
 * Esta es una clase Contract. La uso para tener los nombres de la tabla
 * y las columnas en un solo lugar. Así el código es más ordenado y no
 * me equivoco al escribir los nombres.
 */
public final class PersonasContract { /*que sea una clase FINAL significa que funciona como un CONST en JavaScript
    la variable final, o sea NO se puede MODIFICAR asi entendí yo la variable final */

    // Constructor privado para que nadie pueda crear un objeto de esta clase.
    private PersonasContract() {
    }

    /*
     * Clase interna que define los nombres de la tabla 'personas'.
     * Le puse 'implements BaseColumns' porque es una buena práctica que
     * recomienda Android para las tablas.
     */
    public static class PersonaEntry implements BaseColumns {
        public static final String NOMBRE_TABLA = "personas";

        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_EDAD = "edad";
    }
}