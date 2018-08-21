package br.com.fabricio.youtubeclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.fabricio.youtubeclone.R;
import br.com.fabricio.youtubeclone.model.Items;
import br.com.fabricio.youtubeclone.model.Video;

public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.MyViewHolder> {

    private Context context;
    private List<Items> lsItems;

    public AdapterVideos(Context context, List<Items> lsItems) {
        this.context = context;
        this.lsItems = lsItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Items items = lsItems.get(position);
        holder.titulo.setText(items.getSnippet().getTitle());
        Uri uri = Uri.parse(items.getSnippet().getThumbnails().getHigh().getUrl());
        Picasso.get().load(uri).into(holder.imagem);
    }

    @Override
    public int getItemCount() {
        return lsItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagem;
        private TextView titulo;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.list_item_video_imagem);
            titulo = itemView.findViewById(R.id.list_item_video_texto);

        }
    }
}
