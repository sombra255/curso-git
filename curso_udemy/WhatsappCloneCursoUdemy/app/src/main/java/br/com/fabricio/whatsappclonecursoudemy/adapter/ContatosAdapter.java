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
import java.util.zip.Inflater;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.model.Conversa;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder> {

    private List<Usuario> lsUsuarios;
    private Context context;

    public ContatosAdapter(List<Usuario>listaUsuario, Context ctx) {
        this.lsUsuarios = listaUsuario;
        this.context = ctx;
    }

    public List<Usuario> getContatos(){
        return this.lsUsuarios;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contatos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Usuario usuario = lsUsuarios.get(position);
        boolean cabecalho = usuario.getEmail().isEmpty();

        holder.txtNome.setText(usuario.getNome());
        holder.txtEmail.setText(usuario.getEmail());

        if(usuario.getFoto() != null){
            Uri uri = Uri.parse(usuario.getFoto());
            Glide.with(context).load(uri).into(holder.imagem);

        }else {
            if(cabecalho){
                holder.imagem.setImageResource(R.drawable.icone_grupo);
                holder.txtEmail.setVisibility(View.GONE);
            }else {
                holder.imagem.setImageResource(R.drawable.padrao);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lsUsuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView imagem;
        private TextView txtNome;
        private TextView txtEmail;

        public MyViewHolder(View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.list_item_contatos_circle_image);
            txtNome = itemView.findViewById(R.id.list_item_contatos_nome);
            txtEmail = itemView.findViewById(R.id.list_item_contatos_email);
        }
    }
}
