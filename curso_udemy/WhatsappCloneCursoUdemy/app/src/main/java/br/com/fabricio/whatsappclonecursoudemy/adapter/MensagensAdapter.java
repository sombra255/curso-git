package br.com.fabricio.whatsappclonecursoudemy.adapter;

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

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Mensagem;

public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.MyViewHolder> {

    private List<Mensagem> lsMensagens;
    private Context context;
    private static final int TIPO_REMETENTE = 0;
    private static final int TIPO_DESTINATARIO = 1;

    public MensagensAdapter(List<Mensagem> lsMensagens, Context context) {
        this.lsMensagens = lsMensagens;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == TIPO_REMETENTE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_remetente, parent, false);
        } else if (viewType == TIPO_DESTINATARIO){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_destinatario, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mensagem mensagem = lsMensagens.get(position);

        String msg = mensagem.getMensagem();
        String imagem = mensagem.getImagem();

        if(imagem != null){
            Uri uri = Uri.parse(imagem);
            Glide.with(context).load(uri).into(holder.imagem);

            String nome = mensagem.getNome();
            if(!nome.isEmpty()){
                holder.nome.setText(nome);
            }else {
                holder.nome.setVisibility(View.GONE);
            }

            holder.mensagem.setVisibility(View.GONE);
        }else {
            holder.mensagem.setText(msg);

            String nome = mensagem.getNome();
            if(!nome.isEmpty()){
                holder.nome.setText(nome);
            }else {
                holder.nome.setVisibility(View.GONE);
            }

            holder.imagem.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return lsMensagens.size();
    }

    @Override
    public int getItemViewType(int position) {
        Mensagem mensagem =lsMensagens.get(position);
        String idUsuario = UsuarioFirebase.getUsuarioFirebase();

        if(idUsuario.equals(mensagem.getIdUsuario())){
            return TIPO_REMETENTE;
        }
        return TIPO_DESTINATARIO;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mensagem;
        private TextView nome;
        private ImageView imagem;

        public MyViewHolder(View itemView) {
            super(itemView);

            mensagem = itemView.findViewById(R.id.list_item_remetente_mensagem);
            imagem = itemView.findViewById(R.id.list_item_remetente_imagem);
            nome = itemView.findViewById(R.id.list_item_remetente_nome);
        }
    }
}
