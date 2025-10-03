package com.matias.proyectocrud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/*
 * Pantalla para editar una persona que ya existe.
 * Es parecida a la de agregar, pero primero tiene que cargar los datos actuales.
 */
public class EditarPersonaActivity extends AppCompatActivity {

    private Button btnGuardarCambios;
    private EditText etNombre, etEdad;
    private ControladorPersonas controladorPersonas;
    private long idPersona; // Necesito guardar el ID para saber a quién editar.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);

        controladorPersonas = new ControladorPersonas(this);

        // 1. Recibo los datos que me mandó la MainActivity.
        Bundle extras = getIntent().getExtras();
        // Si por alguna razon no llegan los datos, cierro la pantalla para evitar un error.
        if (extras == null) {
            finish();
            return;
        }

        // 2. Saco los datos del 'paquete' (Bundle) y los guardo en variables.
        idPersona = extras.getLong("idPersona");
        String nombreActual = extras.getString("nombrePersona");
        int edadActual = extras.getInt("edadPersona");

        // 3. Conecto las vistas.
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        etNombre = findViewById(R.id.etNombreEditar);
        etEdad = findViewById(R.id.etEdadEditar);

        // 4. Pongo los datos actuales en los campos para que el usuario los vea.
        etNombre.setText(nombreActual);
        etEdad.setText(String.valueOf(edadActual)); // Convierto el int a String para el EditText.

        // 5. Listener para el boton de guardar.
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saco los datos nuevos (o los que no se cambiaron).
                String nuevoNombre = etNombre.getText().toString();
                String nuevaEdadTexto = etEdad.getText().toString();

                if (nuevoNombre.isEmpty() || nuevaEdadTexto.isEmpty()) {
                    Toast.makeText(EditarPersonaActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int nuevaEdad = Integer.parseInt(nuevaEdadTexto);

                // Creo el objeto Persona, pero esta vez le paso tambien el ID original.
                Persona personaConCambios = new Persona(nuevoNombre, nuevaEdad, idPersona);

                // Llamo al método de actualizar del controlador.
                int filasAfectadas = controladorPersonas.guardarCambios(personaConCambios);

                // El método de actualizar devuelve el número de filas cambiadas.
                // Si es mayor a 0, significa que funcionó.
                if (filasAfectadas > 0) {
                    Toast.makeText(EditarPersonaActivity.this, "Cambios guardados", Toast.LENGTH_LONG).show();
                    finish(); // Cierro y vuelvo a la lista.
                } else {
                    Toast.makeText(EditarPersonaActivity.this, "Error al guardar cambios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}