package br.com.fabricio.whatsappclonecursoudemy.adapter;

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

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class GrupoSelecionadoAdapter extends RecyclerView.Adapter<GrupoSelecionadoAdapter.MyViewHolder>{

    private List<Usuario> lsUsuarios;
    private Context context;

    public GrupoSelecionadoAdapter(List<Usuario> lsUsuarios, Context context) {
        this.lsUsuarios = lsUsuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_grupo_selecionado, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Usuario usuario = lsUsuarios.get(position);

        holder.nome.setText(usuario.getNome());
        if(usuario.getFoto() != null){
            Uri uri = Uri.parse(usuario.getFoto());
            Glide.with(context).load(uri).into(holder.imagem);
        }else {
            holder.imagem.setImageResource(R.drawable.padrao);
        }
    }

    @Override
    public int getItemCount() {
        return lsUsuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView imagem;
        private TextView nome;

        public MyViewHolder(View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.list_item_grupo_imagem);
            nome = itemView.findViewById(R.id.list_item_grupo_nome);
        }
    }
}
