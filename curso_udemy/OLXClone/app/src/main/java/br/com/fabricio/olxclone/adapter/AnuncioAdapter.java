package br.com.fabricio.olxclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.fabricio.olxclone.R;
import br.com.fabricio.olxclone.model.Anuncio;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.MyViewHolder> {

    private Context context;
    private List<Anuncio> lsAnuncio;

    public AnuncioAdapter(Context context, List<Anuncio> lsAnuncio) {
        this.context = context;
        this.lsAnuncio = lsAnuncio;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_anuncio, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Anuncio anuncio = lsAnuncio.get(position);
        holder.txtTitulo.setText(anuncio.getTitulo());
        holder.txtValor.setText(anuncio.getValor());

        List<String> lsFotos = anuncio.getLsFotos();
        Uri uri = Uri.parse(lsFotos.get(0));
        Glide.with(context).load(uri).into(holder.imagem);
    }

    @Override
    public int getItemCount() {
        return lsAnuncio.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtTitulo, txtValor;
        private ImageView imagem;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.list_item_anuncio_txtTitulo);
            txtValor = itemView.findViewById(R.id.list_item_anuncio_txtValor);
            imagem = itemView.findViewById(R.id.list_item_anuncio_imageViewAnuncio);
        }
    }
}
