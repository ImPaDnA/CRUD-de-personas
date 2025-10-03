package com.matias.proyectocrud;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
 * Esta clase la hice porque el RecyclerView no tiene un método fácil como 'setOnItemClickListener'.
 * Con esta clase, puedo detectar los clics (cortos y largos) en los items de la lista
 * de una forma más limpia y reutilizable.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private ClickListener clickListener;
    private GestureDetector gestureDetector; // Este es el que de verdad detecta los gestos.
    /*
     * Esta es una interfaz que cree yo.
     * Sirve para que la MainActivity se encargue de lo que pasa
     * cuando se hace clic corto o clic largo.
     */
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;

        // Aquí configuro el detector de gestos de Android.
        // Le digo qué hacer cuando detecte un toque simple o uno largo.
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            // onSingleTapUp es para un clic corto y rápido.
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            // onLongPress es para cuando dejas el dedo apretado.
            public void onLongPress(MotionEvent e) {
                // Busco qué item de la lista está debajo de donde se tocó.
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    // Si encontré un item, llamo al método onLongClick de mi interfaz.
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    /*
     * Este método es como un espía. 'Intercepta' todos los toques en el RecyclerView.
     * Aquí es donde decido si el toque fue un clic simple.
     */
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // Busco el item que está debajo del toque.
        View child = rv.findChildViewUnder(e.getX(), e.getY());

        // Le paso el evento al detector de gestos. Si me dice que fue un toque simple...
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            // ...llamo al método onClick de mi interfaz.
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false; // Devuelvo false para que el scroll y otros gestos sigan funcionando.
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // No necesito poner nada aquí.
        // El 'onInterceptTouchEvent' ya hace todo lo que quiero con los clics.
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // Este tampoco lo necesito.
        // Es para cosas más complicadas que no uso en mi proyecto.
    }
}