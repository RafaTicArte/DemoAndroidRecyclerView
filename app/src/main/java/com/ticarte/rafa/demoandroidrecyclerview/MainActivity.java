package com.ticarte.rafa.demoandroidrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Hay que añadir al proyecto la siguiente
 * dependencia en el archivo /app/build.gradle
 * con la versión que estemos trabajando
 * 'com.android.support:recyclerview-v7:27.1.1'
 */

public class MainActivity extends AppCompatActivity {

    private ArrayList<AcontecimientoItem> items;
    private AcontecimientoAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crea una lista con los elementos a mostrar
        items = new ArrayList<AcontecimientoItem>();
        items.add(new AcontecimientoItem("1", "Primer acontecimiento"));
        items.add(new AcontecimientoItem("2", "Segundo acontecimiento"));
        items.add(new AcontecimientoItem("3", "Tercer acontecimiento"));
        items.add(new AcontecimientoItem("4", "Cuarto acontecimiento"));

        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Crea el Adaptador con los datos de la lista anterior
        adaptador = new AcontecimientoAdapter(items);

        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                Toast.makeText(MainActivity.this, "Posición: " + String.valueOf(position) + " Id: " + items.get(position).getId() + " Nombre: " + items.get(position).getNombre(), Toast.LENGTH_SHORT)
                     .show();
            }
        });

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Define la acción de desplazamiento a derecha y/o izquierda
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d("DemoAndroidRecyclerView", "Dirección de desplazamiento: " + direction);
                int position = viewHolder.getAdapterPosition();
                if(items.remove(position)!=null){
                    Toast.makeText(MainActivity.this, "Posición " + position + " " + getResources().getString(R.string.deleted_ok), Toast.LENGTH_SHORT).show();
                    adaptador.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "Posición " + position + " " + getResources().getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Asocia la acción de desplazamiento al RecyclerView
        helper.attachToRecyclerView(recyclerView);
    }
}