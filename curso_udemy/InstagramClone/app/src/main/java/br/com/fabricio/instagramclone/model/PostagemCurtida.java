package br.com.fabricio.instagramclone.model;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import br.com.fabricio.instagramclone.helper.FirebaseHelper;

public class PostagemCurtida {

    private Feed feed;
    private Usuario usuario;
    private int qtdCurtidas = 0;

    public PostagemCurtida() {
    }

    public void salvar(){
        DatabaseReference firebaseRef = FirebaseHelper.getDatabaseReference();

        HashMap<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("nomeUsuario", usuario.getNome());
        dadosUsuario.put("caminhoFoto", usuario.getCaminhoFoto());

        DatabaseReference postagemCurtidasRef = firebaseRef
                .child("postagens-curtidas")
                .child(feed.getId())
                .child(usuario.getId());
        postagemCurtidasRef.setValue(dadosUsuario);

        atualizaQtd(1);
    }

    public void atualizaQtd(int valor){
        DatabaseReference firebaseRef = FirebaseHelper.getDatabaseReference();

        DatabaseReference postagemCurtidasRef = firebaseRef
                .child("postagens-curtidas")
                .child(feed.getId())
                .child("qtdCurtidas");

        setQtdCurtidas(getQtdCurtidas() + valor);


        postagemCurtidasRef.setValue(getQtdCurtidas());
    }

    public void remover(){
        DatabaseReference firebaseRef = FirebaseHelper.getDatabaseReference();

        DatabaseReference postagemCurtidasRef = firebaseRef
                .child("postagens-curtidas")
                .child(feed.getId())
                .child(usuario.getId());
        postagemCurtidasRef.removeValue();

        atualizaQtd(-1);
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getQtdCurtidas() {
        return qtdCurtidas;
    }

    public void setQtdCurtidas(int qtdCurtidas) {
        this.qtdCurtidas = qtdCurtidas;
    }
}
