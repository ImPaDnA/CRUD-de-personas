package com.matias.proyectocrud;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull; //import para que nunca esté nulo.
import androidx.recyclerview.widget.RecyclerView;

/*
 * Esta clase la hice porque el RecyclerView no tiene un método fácil como 'setOnItemClickListener'.
 * Con esta clase, puedo detectar los clics (cortos y largos) en los items de la lista
 * de una forma más limpia y reutilizable.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    //agregué un NonNull porque clicklistener podría ser nulo.
    private final @NonNull ClickListener clickListener;
    //Agregando la varaible final al click y al gesture (ya que estaban en alertas).
    private final GestureDetector gestureDetector; // Esta clase analiza los eventos del toque y decide si forman un gesto.
    /*
     * Esta es una interfaz.
     * Sirve para que la MainActivity se encargue de lo que pasa
     * cuando se hace clic corto o clic largo.
     */
    public interface ClickListener {
        void onClick(View view, int position); // Le paso la posición para saber que item toqué.
        void onLongClick(View view, int position); // Lo mismo que arriba, pero para clics largos.
    }

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final @NonNull ClickListener clickListener) {
        this.clickListener = clickListener; // Guardo la interfaz.


        // Aquí configuro el detector de gestos de Android.
        // Le digo qué hacer cuando detecte un toque simple o uno largo.
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            // onSingleTapUp es para un clic corto y rápido.
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                return true;
            }

            @Override
            // onLongPress es para cuando dejas el dedo apretado.
            public void onLongPress(@NonNull MotionEvent e) { //e es el evento del toque.
                // Busco qué item de la lista está debajo de donde se tocó.
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY()); //e.getX() y e.getY() son las coordenadas del toque.
                // Eliminando redundancias de la condicion nula.
                if (child != null) {
                    // Si encontré un item, llamo al método onLongClick de mi interfaz.
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child)); // Le paso la posición.
                }
            }
        });
    }

    /*
     * Este método es como un espía. 'Intercepta' todos los toques en el RecyclerView.
     * Aquí es donde decido si el toque fue un clic simple.
     */
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) { //onInterceptTouchEvent es para cuando se toca.

        // Busco el item que está debajo del toque.
        View child = rv.findChildViewUnder(e.getX(), e.getY());

        // Le paso el evento al detector de gestos. Si me dice que fue un toque simple...
        //eliminando la condicion de nulo de clicklistener por ser redundante ahora con el @NonNull
        if (child != null && gestureDetector.onTouchEvent(e)) {
            // ...llamo al método onClick de mi interfaz.
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false; // Devuelvo false para que el scroll y otros gestos sigan funcionando.
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // No necesito poner nada aquí.
        // El 'onInterceptTouchEvent' ya hace todo lo que quiero con los clics.
        // Es porque solo necesito saber si el toque fue un clic simple o largo.
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // Este tampoco lo necesito.
        // Es para cosas más complicadas que no uso en mi proyecto.
        // Lo mismo que en onTouchEvent.
    }
}