package com.matias.proyectocrud;

// Los imports de ContentValues y SQLiteDatabase no son necesarios aqui.// Es mejor borrarlos para que el código esté más limpio.

public class Persona {
    // Los atributos de una persona.
    private String nombre;
    private int edad;
    private long id; // uso long por si la base de datos crece mucho. lo vi en un tutorial.

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

    // Aquí está el error de tipeo que hablamos, lo dejo como está.
    public void setId(long Id) {
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

    // El toString, mas que nada para pruebas.
    @Override
    public String toString() {
        return "Persona{" +
                "nombre=" + nombre + '\'' +
                ", edad=" + edad +
                '}';
    }
}
