package com.matias.proyectocrud;

// Los imports de ContentValues y SQLiteDatabase no son necesarios aqui.
// Es mejor borrarlos para que el código esté más limpio. Esta clase solo sirve como molde para las personas.

import androidx.annotation.NonNull; //Importar este NonNUll porque me dio una alerta en ToString, persona no puede ser nulo.

import java.util.Locale; //Localizacion agregada.

public class Persona {
    // Los atributos de una persona.
    private String nombre;
    private int edad;
    private long id; // uso long por si la base de datos crece mucho. lo vi en un tutorial.
    //la variable long es como un int pero de 64bits, o sea es mucho más grande la cantidad de numeros que el INT.

    // Constructor para cuando creo una persona nueva desde la app.
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    // Constructor para cuando saco una persona de la base de datos, porque ya tiene id.
    public Persona(String nombre, int edad, long id) {
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
    }

    // Getters y Setters, para poder ver y cambiar los datos desde afuera.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    // cambié el return a un string format, esto es más legible, y NonNull para que no sea nulo.
    @NonNull
    @Override
    public String toString() {
        // Uso locale.US para un formato consistente que no dependa del idioma del teléfono.
        return String.format(Locale.US, "Persona{nombre='%s', edad=%d}", nombre, edad);
    }
}