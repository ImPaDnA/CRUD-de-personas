package com.matias.proyectocrud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Esta es la pantalla para agregar una persona nueva.
 * Es un formulario simple con dos campos y un botón.
 */
public class AgregarPersonaActivity extends AppCompatActivity {
    public Button btnGuardar;
    private EditText etNombre, etEdad;
    private ControladorPersonas controladorPersonas; // Lo necesito para usar el método de insertar.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_persona);

        // Inicializo el controlador.
        controladorPersonas = new ControladorPersonas(this);

        // Conecto las variables con las vistas del XML.
        btnGuardar = findViewById(R.id.btnGuardar);
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);

        // Listener para el botón de guardar.
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Saco el texto de los campos.
                String nombre = etNombre.getText().toString();
                String edadTexto = etEdad.getText().toString();

                // 2. Reviso que no esten vacíos, una validación simple.
                if (nombre.isEmpty() || edadTexto.isEmpty()) {
                    Toast.makeText(AgregarPersonaActivity.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                    return; // Si faltan datos, no sigo.
                }

                // 3. Convierto la edad (que es texto) a número.
                int edad = Integer.parseInt(edadTexto);

                // 4. Creo el objeto Persona con los datos.
                Persona nuevaPersona = new Persona(nombre, edad);

                // 5. Llamo al método del controlador para que la guarde en la BD.
                long idInsertado = controladorPersonas.nuevaPersona(nuevaPersona);

                // 6. Muestro un mensaje para saber si se guardó bien o no.
                if (idInsertado == -1) {
                    Toast.makeText(AgregarPersonaActivity.this, "Error al guardar", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AgregarPersonaActivity.this, "Guardado con éxito", Toast.LENGTH_LONG).show();
                    // Cierro esta pantalla para volver a la lista principal.
                    finish();
                }
            }
        });
    }
}
