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
import br.com.fabricio.whatsappclonecursoudemy.model.Conversa;
import br.com.fabricio.whatsappclonecursoudemy.model.Grupo;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class ConversasAdapter extends RecyclerView.Adapter<ConversasAdapter.MyViewHolder> {

    private List<Conversa> conversasList;
    private Context context;

    public ConversasAdapter(List<Conversa> lsConversas, Context context) {
        this.conversasList = lsConversas;
        this.context = context;
    }

    public List<Conversa> getConversas(){
        return this.conversasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_conversas, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Conversa conversa = conversasList.get(position);
        holder.ultimaMensagem.setText(conversa.getUltimaMensagem());
//<<<<<<< HEAD
//=======
//
//        holder.ultimaMensagem.setText(conversa.getUltimaMensagem());
//        holder.nome.setText(usuario.getNome());
//>>>>>>> a0ebb776b25596f1623390e98a5554b2b5f7f787

        if(conversa.getIsGroup().equals("true")){
            Grupo grupo = conversa.getGrupo();
            holder.nome.setText(grupo.getNome());

            if(grupo.getFoto() != null){
                Uri uri = Uri.parse(grupo.getFoto());
                Glide.with(context).load(uri).into(holder.imagem);
            }else {
                holder.imagem.setImageResource(R.drawable.padrao);
            }

        }else {
            Usuario usuario = conversa.getUsuarioExibicao();
            if(usuario != null){
                holder.nome.setText(usuario.getNome());

                if(usuario.getFoto() != null){
                    Uri uri = Uri.parse(usuario.getFoto());
                    Glide.with(context).load(uri).into(holder.imagem);
                }else {
                    holder.imagem.setImageResource(R.drawable.padrao);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return conversasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView imagem;
        private TextView nome;
        private TextView ultimaMensagem;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.list_item_conversas_circle_image);
            nome = itemView.findViewById(R.id.list_item_conversas_nome);
            ultimaMensagem = itemView.findViewById(R.id.list_item_conversas_mensagem);
        }
    }
}
