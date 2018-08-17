package br.com.fabricio.instagramclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.model.Comentario;
import de.hdodenhof.circleimageview.CircleImageView;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.MyViewHolder> {

    private Context context;
    private List<Comentario> lsComentario;

    public ComentarioAdapter(Context context, List<Comentario> lsComentario) {
        this.context = context;
        this.lsComentario = lsComentario;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comentario, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comentario comentario = lsComentario.get(position);

        if(comentario.getCaminhoFoto() != null) {
            Uri uri = Uri.parse(comentario.getCaminhoFoto());
            Glide.with(context).load(uri).into(holder.imagem);
        }

        holder.txtNome.setText(comentario.getNomeUsuario());
        holder.txtComentario.setText(comentario.getComentario());
    }

    @Override
    public int getItemCount() {
        return lsComentario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView imagem;
        private TextView txtNome, txtComentario;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.list_item_comentario_imagem);
            txtNome = itemView.findViewById(R.id.list_item_comentario_nome);
            txtComentario = itemView.findViewById(R.id.list_item_comentario_textoComentario);
        }
    }
}
