package br.com.fabricio.instagramclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class PesquisaAdapter extends RecyclerView.Adapter<PesquisaAdapter.MyViewHolder> {

    private Context context;
    private List<Usuario> lsUsuarios;

    public PesquisaAdapter(Context context, List<Usuario> lsUsuarios) {
        this.context = context;
        this.lsUsuarios = lsUsuarios;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pesquisa, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Usuario usuario = lsUsuarios.get(position);

        if(usuario.getCaminhoFoto() != null){
            Uri uri = Uri.parse(usuario.getCaminhoFoto());
            Glide.with(context).load(uri).into(holder.imageView);
        }else {
            holder.imageView.setImageResource(R.drawable.avatar);
        }

        holder.txtNomeUsuario.setText(usuario.getNome());

    }

    @Override
    public int getItemCount() {
        return lsUsuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageView;
        private TextView txtNomeUsuario;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.list_item_pesquisa_imageViewFotoPesquisa);
            txtNomeUsuario = itemView.findViewById(R.id.list_item_pesquisa_txtNomePesquisa);
        }
    }
}
