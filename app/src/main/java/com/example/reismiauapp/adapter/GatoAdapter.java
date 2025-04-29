package com.example.reismiauapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reismiauapp.R;
import com.example.reismiauapp.model.Gato;

import java.util.List;

public class GatoAdapter extends RecyclerView.Adapter<GatoAdapter.GatoViewHolder>{

    private List<Gato> gatoLista;

    public GatoAdapter(List<Gato> gatoLista) {
        this.gatoLista = gatoLista;
    }

    public static class GatoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView descView;

        public GatoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.catImage);
            nameView = itemView.findViewById(R.id.catName);
            descView = itemView.findViewById(R.id.catDescription);
        }

        public void bind(Gato gato) {
            nameView.setText(gato.nome);
            descView.setText(gato.descricao);
            imageView.setImageResource(gato.imagemId);
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
        holder.bind(gatoLista.get(position));
    }

    @Override
    public int getItemCount() {
        return gatoLista.size();
    }
}
