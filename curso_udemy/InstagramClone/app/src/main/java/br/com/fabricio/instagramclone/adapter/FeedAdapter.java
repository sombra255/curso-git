package br.com.fabricio.instagramclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.activity.ComentariosActivity;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.helper.UsuarioFirebase;
import br.com.fabricio.instagramclone.model.Feed;
import br.com.fabricio.instagramclone.model.Postagem;
import br.com.fabricio.instagramclone.model.PostagemCurtida;
import br.com.fabricio.instagramclone.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private Context context;
    private List<Feed> lsFeed;

    public FeedAdapter(Context context, List<Feed> lsFeed) {
        this.context = context;
        this.lsFeed = lsFeed;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Feed feed = lsFeed.get(position);
        final Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        Uri uriFotoUsuario = Uri.parse(feed.getFotoUsuario());
        Uri uriFotoPostagem = Uri.parse(feed.getFotoPostagem());

        Glide.with(context).load(uriFotoUsuario).into(holder.imagem);
        Glide.with(context).load(uriFotoPostagem).into(holder.fotoPostagem);

        holder.txtDescricao.setText(feed.getDescricao());
        holder.txtNomeUsuario.setText(feed.getNomeUsuario());

        DatabaseReference curtidasRef = FirebaseHelper.getDatabaseReference()
                .child("postagens-curtidas")
                .child(feed.getId());

        curtidasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int qtdCurtidas = 0;

                if(dataSnapshot.hasChild("qtdCurtidas")){
                    PostagemCurtida postagemCurtida = dataSnapshot.getValue(PostagemCurtida.class);
                    qtdCurtidas = postagemCurtida.getQtdCurtidas();
                }

                if(dataSnapshot.hasChild(usuarioLogado.getId())){
                    holder.likeButton.setLiked(true);
                }else {
                    holder.likeButton.setLiked(false);
                }

                final PostagemCurtida curtida = new PostagemCurtida();
                curtida.setFeed(feed);
                curtida.setUsuario(usuarioLogado);
                curtida.setQtdCurtidas(qtdCurtidas);

                holder.imageComentarioFeed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ComentariosActivity.class);
                        intent.putExtra("idPostagem", feed.getId());
                        context.startActivity(intent);
                    }
                });

                holder.likeButton.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        curtida.salvar();
                        holder.txtCurtidas.setText(curtida.getQtdCurtidas() + " curtidas");
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        curtida.remover();
                        holder.txtCurtidas.setText(curtida.getQtdCurtidas() + " curtidas");
                    }
                });

                holder.txtCurtidas.setText(curtida.getQtdCurtidas() + " curtidas");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return lsFeed.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNomeUsuario, txtCurtidas, txtDescricao;
        private ImageView fotoPostagem, imageComentarioFeed;
        private CircleImageView imagem;
        private LikeButton likeButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtNomeUsuario = itemView.findViewById(R.id.visualiza_postagem_txtNome);
            imageComentarioFeed =     itemView.findViewById(R.id.imageComentarioFeed);
            txtCurtidas = itemView.findViewById(R.id.visualiza_postagem_txtCurtidas);
            txtDescricao = itemView.findViewById(R.id.visualiza_postagem_txtDescricao);
            fotoPostagem = itemView.findViewById(R.id.visualiza_postagem_fotoPostagem);
            imagem = itemView.findViewById(R.id.visualiza_postagem_imagem);
            likeButton = itemView.findViewById(R.id.likeButtonFeed);
        }
    }
}
