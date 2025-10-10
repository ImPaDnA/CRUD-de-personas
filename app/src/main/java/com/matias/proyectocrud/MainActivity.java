package com.matias.proyectocrud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog; //algunos de estos imports aparecen automáticamente cuando le hago click a algo con error, para que se valide, es algo de android studio al parecer.
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Declaro las variables que voy a usar en esta pantalla.
    private List<Persona> listaDePersonas; // La lista de datos.
    private RecyclerView recyclerView; // La vista que muestra la lista.
    private AdaptadorPersonas adaptadorPersonas; // El que conecta la lista con el RecyclerView.
    private FloatingActionButton fabAgregarPersona; // El boton flotante.
    private ControladorPersonas controladorPersonas; // El que maneja la BD.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Conecto las variables con la vista y la lógica.
        // Instancio el controlador para poder usar sus metodos (CRUD).
        controladorPersonas = new ControladorPersonas(MainActivity.this);

        // Conecto las variables de la vista con los IDs del XML.
        recyclerView = findViewById(R.id.recyclerView);
        fabAgregarPersona = findViewById(R.id.fabAgregarPersona);


        // 2. Configuro el RecyclerView para que sepa como mostrar las cosas.
        listaDePersonas = new ArrayList<>();
        adaptadorPersonas = new AdaptadorPersonas(listaDePersonas);

        // Le digo que muestre los items en una lista vertical, uno debajo del otro.
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()); // El layout manager.
        recyclerView.setLayoutManager(mLayoutManager); // Le pongo el layout manager.
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // una animacion simple al agregar o borrar.
        recyclerView.setAdapter(adaptadorPersonas); // Finalmente, le pongo el adaptador.


        // 3. Pongo los 'escuchadores' para los botones y la lista.
        // Listener para el botón flotante de agregar.
        fabAgregarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando le hacen clic, abro la pantalla para agregar una persona nueva.
                Intent intent = new Intent(MainActivity.this, AgregarPersonaActivity.class);
                startActivity(intent); // pues se inicia el intent
            }
        });

        // Listener para los toques en la lista (clic y clic largo).
        // Uso la clase RecyclerTouchListener que cree para esto.
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            // Clic corto para EDITAR.
            @Override
            public void onClick(View view, int position) {
                Persona personaSeleccionada = listaDePersonas.get(position);
                // Abro la pantalla de edición y le paso los datos de la persona
                // para que el formulario ya aparesca lleno.
                Intent intent = new Intent(MainActivity.this, EditarPersonaActivity.class);
                intent.putExtra("idPersona", personaSeleccionada.getId());
                intent.putExtra("nombrePersona", personaSeleccionada.getNombre());
                intent.putExtra("edadPersona", personaSeleccionada.getEdad());
                startActivity(intent);
            }

            // Clic largo para BORRAR.
            @Override
            public void onLongClick(View view, int position) {
                final Persona personaParaEliminar = listaDePersonas.get(position);

                // Muestro una alerta para confirmar, por si se equivocó.
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmar")
                        .setMessage("¿Seguro quieres eliminar a " + personaParaEliminar.getNombre() + "?")
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Si dice que si, llamo al método para borrar de la BD.
                                controladorPersonas.eliminarPersona(personaParaEliminar);
                                // Y actualizo la lista para que desaparezca de la pantalla.
                                refrescarLista();
                            }
                        })
                        .setNegativeButton("Cancelar", null) // El botón de cancelar no hace nada, solo cierra la alerta.
                        .show();
            }
        }));

        // La primera vez que se abre la app, cargo los datos que ya existen.
        refrescarLista();
    }

    // El onResume se ejecuta siempre que la pantalla vuelve a estar visible.
    // Lo uso para actualizar la lista despues de agregar o editar una persona.
    // Asi los cambios se ven al instante.
    @Override
    protected void onResume() {
        super.onResume();
        refrescarLista();
    }

    // Mi método para actualizar la lista.
    public void refrescarLista() {
        // Pido la lista de personas actualizada desde el controlador.
        listaDePersonas = controladorPersonas.obtenerPersonas();
        // Le paso la nueva lista al adaptador.
        adaptadorPersonas.setListaDePersonas(listaDePersonas);
        // Le aviso al adaptador que los datos cambiaron para que redibuje la lista.
        adaptadorPersonas.notifyDataSetChanged();
    }
}
