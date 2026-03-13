package com.example.pph_reproductormp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Adapter del RecyclerView que gestiona la llista de cançons a continuació
public class CancoAdapter extends RecyclerView.Adapter<CancoAdapter.ViewHolder> {

    // Interfície per notificar MainActivity quan l'usuari clica una cançó de la llista
    public interface OnCancoClickListener {
        void onCancoClick(int index);
    }

    private final Context context;
    private final int[] covers;      // IDs de les portades (res/drawable)
    private final String[] titles;   // Títols de les cançons
    private final String[] artists;  // Noms dels artistes
    private final int[] durades;     // Durades en ms precalculades a MainActivity
    private int currentIndex;        // Índex de la cançó en reproducció (per ressaltar)
    private final OnCancoClickListener listener;

    public CancoAdapter(Context context, int[] covers, String[] titles,
                        String[] artists, int[] durades,
                        int currentIndex, OnCancoClickListener listener) {
        this.context      = context;
        this.covers       = covers;
        this.titles       = titles;
        this.artists      = artists;
        this.durades      = durades;
        this.currentIndex = currentIndex;
        this.listener     = listener;
    }

    // Actualitza quin índex és l'actiu i refresca només les files afectades
    public void setCurrentIndex(int index) {
        int anterior = currentIndex;
        currentIndex = index;
        notifyItemChanged(anterior); // Treu el ressaltat de la fila anterior
        notifyItemChanged(index);    // Ressalta la nova fila activa
    }

    // Crea la vista de cada fila inflant el layout item_canco.xml
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_canco, parent, false);
        return new ViewHolder(view);
    }

    // Omple les dades de cada fila amb la informació de la cançó corresponent
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cover.setImageResource(covers[position]);
        holder.title.setText(titles[position]);
        holder.artist.setText(artists[position]);

        // Mostra la durada precalculada (sense crear cap MediaPlayer aquí)
        holder.duration.setText(formatarTemps(durades[position]));

        // Ressalta la cançó activa amb fons vermell translúcid
        holder.itemView.setBackgroundColor(
                position == currentIndex ? 0x33FF0000 : 0x00000000
        );

        // Notifica MainActivity quan es clica una fila
        holder.itemView.setOnClickListener(v -> listener.onCancoClick(position));
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    // Converteix mil·lisegons a format MM:SS
    private String formatarTemps(int ms) {
        return String.format("%02d:%02d", (ms / 1000) / 60, (ms / 1000) % 60);
    }

    // Conté les referències a les vistes de cada fila de la llista
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title, artist, duration;

        ViewHolder(View itemView) {
            super(itemView);
            cover    = itemView.findViewById(R.id.itemCover);
            title    = itemView.findViewById(R.id.itemTitle);
            artist   = itemView.findViewById(R.id.itemArtist);
            duration = itemView.findViewById(R.id.itemDuration);
        }
    }
}