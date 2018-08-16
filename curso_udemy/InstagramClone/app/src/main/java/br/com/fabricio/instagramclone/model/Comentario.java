package br.com.fabricio.instagramclone.model;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;

import br.com.fabricio.instagramclone.helper.FirebaseHelper;

public class Comentario {

    private String idComentario;
    private String idPostagem;
    private String idUsuario;
    private String caminhoFoto;
    private String nomeUsuario;
    private String comentario;

    public Comentario() {
    }

    public boolean salvar(){
        DatabaseReference firebaseRef = FirebaseHelper.getDatabaseReference();
        DatabaseReference comentariosRef = firebaseRef
                .child("comentarios")
                .child(getIdPostagem());

        String chaveComentario = comentariosRef.push().getKey();
        setIdComentario(chaveComentario);

        comentariosRef.child(getIdComentario()).setValue(this);

        return true;
    }

    public String getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(String idComentario) {
        this.idComentario = idComentario;
    }

    public String getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(String idPostagem) {
        this.idPostagem = idPostagem;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
