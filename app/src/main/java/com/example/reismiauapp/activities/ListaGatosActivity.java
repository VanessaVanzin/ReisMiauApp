package com.example.reismiauapp.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reismiauapp.R;
import com.example.reismiauapp.adapters.GatoAdapter;
import com.example.reismiauapp.helpers.BottomNavBarHelper;
import com.example.reismiauapp.models.GatoModel;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ListaGatosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GatoAdapter adapter;
    private List<GatoModel> gatoLista;

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
        adapter = new GatoAdapter(gatoLista);
        recyclerView.setAdapter(adapter);

        // Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference petsRef = db.collection("pets");

        petsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    GatoModel gato = doc.toObject(GatoModel.class);
                    gatoLista.add(gato);
                }
                adapter.notifyDataSetChanged();
            } else {
                Log.e("FIREBASE", "Erro ao buscar pets", task.getException());
            }
        });

        BottomNavBarHelper.setup(this);
    }
}
