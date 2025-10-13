package com.matias.proyectocrud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull; //import para que nunca esté nulo.
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
 * Este es el Adaptador. Es el puente entre mi 'listaDePersonas' y el RecyclerView.
 * Su trabajo es agarrar los datos y meterlos en cada fila de la lista.
 */
public class AdaptadorPersonas extends RecyclerView.Adapter<AdaptadorPersonas.MyViewHolder> {

    // La lista de datos que va a mostrar.
    private List<Persona> listaDePersonas;

    // Constructor, aqui le paso la lista de personas cuando lo creo.
    public AdaptadorPersonas(List<Persona> listaDePersonas) {
        this.listaDePersonas = listaDePersonas;
    }

    // Un método para poder cambiar la lista desde MainActivity cuando se actualizan los datos.
    public void setListaDePersonas(List<Persona> listaDePersonas) {
        this.listaDePersonas = listaDePersonas;
    }

    /*
     * El ViewHolder. Es como un 'molde' para cada fila.
     * Guarda las referencias a los TextViews para no tener que buscarlos a cada rato.
     * Esto hace que la lista funcione mas rapido.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEdad;

        public MyViewHolder(View view) {
            super(view);
            // Conecto las variables con los IDs del layout de la fila (mi_fila.xml).
            tvNombre = view.findViewById(R.id.tvNombre);
            tvEdad = view.findViewById(R.id.tvEdad);
        }
    }

    /*
     * Este se llama cuando el RecyclerView necesita crear una fila nueva.
     * Pasa pocas veces, solo al principio.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 'Infla' el layout de la fila, o sea, lo convierte en un objeto View.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mi_fila, parent, false);
        // Devuelve un nuevo 'molde' (ViewHolder) con esa vista.
        return new MyViewHolder(itemView);
    }

    /*
     * Este es el más importante. Se llama para rellenar una fila con datos.
     * El RecyclerView es inteligente y 'recicla' las filas que ya no se ven.
     * Este método se encarga de ponerle la información nueva a una fila reciclada.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Busco la persona que toca mostrar segun su posición en la lista.
        Persona persona = listaDePersonas.get(position);
        // Uso el 'holder' (el molde) para ponerle el nombre y la edad.
        holder.tvNombre.setText(persona.getNombre());
        // Convierto la edad a texto para poder mostrarla (arreglada con un string añadido)
        String edadFormateada = holder.itemView.getContext().getString(R.string.formato_edad, persona.getEdad());
        holder.tvEdad.setText(edadFormateada);
    }

    /*
     * Este solo le dice al RecyclerView cuántos items hay en total en la lista.
     * Lo necesita para saber hasta donde puede hacer scroll.
     */
    @Override
    public int getItemCount() {
        return listaDePersonas.size();
    }
}