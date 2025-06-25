package com.example.reismiauapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reismiauapp.R;

import java.util.List;

public class FotoPreviewAdapter extends RecyclerView.Adapter<FotoPreviewAdapter.FotoViewHolder> {

    private final List<Uri> imagens;
    private final Context context;
    private final OnImageRemovedListener removeListener;

    public interface OnImageRemovedListener {
        void onImageRemoved(Uri uri);
    }

    public FotoPreviewAdapter(Context context, List<Uri> imagens, OnImageRemovedListener removeListener) {
        this.context = context;
        this.imagens = imagens;
        this.removeListener = removeListener;
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public FotoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageItem);
        }
    }

    @NonNull
    @Override
    public FotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_foto_preview, parent, false);
        return new FotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoViewHolder holder, int position) {
        Uri imageUri = imagens.get(position);
        Glide.with(context).load(imageUri).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Foto")
                    .setItems(new CharSequence[]{"Visualizar", "Excluir"}, (dialog, which) -> {
                        if (which == 0) {
                            // Visualizar
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(imageUri, "image/*");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            context.startActivity(intent);
                        } else if (which == 1) {
                            int adapterPosition = holder.getAdapterPosition();
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                Uri uriRemovida = imagens.remove(adapterPosition);
                                notifyItemRemoved(adapterPosition);

                                if (removeListener != null) {
                                    removeListener.onImageRemoved(uriRemovida);
                                }
                            }
                        }
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }
}