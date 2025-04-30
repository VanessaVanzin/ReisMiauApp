package com.example.reismiauapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reismiauapp.adapters.GatoAdapter;
import com.example.reismiauapp.helpers.BottomNavBarHelper;
import com.example.reismiauapp.models.Gato;

import java.util.ArrayList;
import java.util.List;

public class ListaGatosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GatoAdapter adapter;
    private List<Gato> gatoLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_gatos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listaGatos), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerGatos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gatoLista = new ArrayList<>();
        gatoLista.add(new Gato("William", "William é um gatinho de seis meses, sem raça definida, e positivo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Melinda", "Melinda é uma gatinha de seis meses, sem raça definida, negativa para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Charlotte", "Charlotte é uma gatinha de seis meses, sem raça definida, castrada e negativa para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));
        gatoLista.add(new Gato("Theo", "Theo é um gatinho de seis meses, sem raça definida, e negativo para FIV/FELV.", R.drawable.gatoteste));

        adapter = new GatoAdapter(gatoLista);
        recyclerView.setAdapter(adapter);

        BottomNavBarHelper.setup(this);
    }
}
