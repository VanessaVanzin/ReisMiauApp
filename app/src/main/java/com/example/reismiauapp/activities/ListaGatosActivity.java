package com.example.reismiauapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

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
    private CheckBox filtroFilhote, filtroAdulto, filtroSenior, filtroMacho, filtroFemea;

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

        filtroFilhote = findViewById(R.id.filter_filhote);
        filtroAdulto = findViewById(R.id.filter_adulto);
        filtroSenior = findViewById(R.id.filter_senior);
        filtroMacho = findViewById(R.id.filter_macho);
        filtroFemea = findViewById(R.id.filter_femea);

        filtroFilhote.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarFiltros());
        filtroAdulto.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarFiltros());
        filtroSenior.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarFiltros());
        filtroMacho.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarFiltros());
        filtroFemea.setOnCheckedChangeListener((buttonView, isChecked) -> aplicarFiltros());

        recyclerView = findViewById(R.id.recyclerGatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gatoLista = new ArrayList<>();
        adapter = new GatoAdapter(gatoLista);
        recyclerView.setAdapter(adapter);

        // Firestore
        carregarPets();

        BottomNavBarHelper.setup(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarPets();
    }

    private List<GatoModel> gatoListaCompleta = new ArrayList<>();

    private void carregarPets() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference petsRef = db.collection("pets");

        petsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                gatoLista.clear();
                gatoListaCompleta.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    GatoModel gato = doc.toObject(GatoModel.class);
                    gato.petId = doc.getId();
                    gatoLista.add(gato);
                    gatoListaCompleta.add(gato);
                }
                aplicarFiltros();
            } else {
                Log.e("FIREBASE", "Erro ao buscar pets", task.getException());
            }
        });
    }

    private void aplicarFiltros() {
        List<GatoModel> gatosFiltrados = new ArrayList<>();

        for (GatoModel gato : gatoListaCompleta) {
            boolean idadeOk = (!filtroFilhote.isChecked() && !filtroAdulto.isChecked() && !filtroSenior.isChecked())
                    || (filtroFilhote.isChecked() && gato.age.equalsIgnoreCase("Filhote"))
                    || (filtroAdulto.isChecked() && gato.age.equalsIgnoreCase("Adulto"))
                    || (filtroSenior.isChecked() && gato.age.equalsIgnoreCase("Sênior"));

            boolean generoOk = (!filtroMacho.isChecked() && !filtroFemea.isChecked())
                    || (filtroMacho.isChecked() && gato.gender.equalsIgnoreCase("M"))
                    || (filtroFemea.isChecked() && gato.gender.equalsIgnoreCase("F"));

            if (idadeOk && generoOk) {
                gatosFiltrados.add(gato);
            }
        }

        adapter = new GatoAdapter(gatosFiltrados);
        recyclerView.setAdapter(adapter);
    }

}
