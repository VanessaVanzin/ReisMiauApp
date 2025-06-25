package com.example.reismiauapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reismiauapp.activities.DetalhesGatoActivity;
import com.example.reismiauapp.R;
import com.example.reismiauapp.models.GatoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GatoAdapter extends RecyclerView.Adapter<GatoAdapter.GatoViewHolder>{

    private List<GatoModel> gatoLista;

    public GatoAdapter(List<GatoModel> gatoLista) {
        this.gatoLista = gatoLista;
    }

    public static class GatoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView descView;
        TextView statusView;

        public GatoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.catImage);
            nameView = itemView.findViewById(R.id.catName);
            descView = itemView.findViewById(R.id.catDescription);
            statusView = itemView.findViewById(R.id.catStatus);
        }

        public void bind(GatoModel gato) {
            nameView.setText(gato.name);
            descView.setText(gato.description);

            String statusTexto = (gato.status == 0) ? "Disponível" : "Adotado";
            statusView.setText(statusTexto);

            if (gato.photos != null && !gato.photos.isEmpty()) {
                String url = gato.photos.get(0).get("url");
                Glide.with(imageView.getContext())
                        .load(url)
                        .into(imageView);
            } else {
                imageView.setImageDrawable(null);
            }
        }

    }

    @NonNull
    @Override
    public GatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gato, parent, false);
        return new GatoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GatoViewHolder holder, int position) {
        GatoModel gato = gatoLista.get(position);
        holder.bind(gato);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetalhesGatoActivity.class);
            intent.putExtra("nome", gato.name);
            intent.putExtra("descricao", gato.description);
            intent.putExtra("genero", gato.gender);
            intent.putExtra("idade", gato.age);
            intent.putExtra("raca", gato.race);
            intent.putExtra("tamanho", gato.size);
            intent.putExtra("status", gato.status);
            intent.putExtra("petId", gato.petId);

            ArrayList<String> fotos = new ArrayList<>();
            for (Map<String, String> foto : gato.photos) {
                fotos.add(foto.get("url"));
            }
            intent.putStringArrayListExtra("fotos", fotos);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gatoLista.size();
    }
}
