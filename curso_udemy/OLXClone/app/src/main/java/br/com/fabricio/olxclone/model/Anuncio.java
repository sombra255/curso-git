package br.com.fabricio.olxclone.model;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.com.fabricio.olxclone.helper.FirebaseHelper;
import br.com.fabricio.olxclone.helper.UsuarioFirebase;

public class Anuncio {

    private String idAnuncio;
    private String estado;
    private String categoria;
    private String titulo;
    private String valor;
    private String telefone;
    private String descricao;
    private List<String> lsFotos;

    public Anuncio() {
        DatabaseReference reference = FirebaseHelper.getFirebaseDatabase()
                .child("meus_anuncios");
        setIdAnuncio(reference.push().getKey());
    }

    public void salvar(){
        DatabaseReference anuncioRef = FirebaseHelper.getFirebaseDatabase()
                .child("meus_anuncios");
        anuncioRef.child(UsuarioFirebase.getUsuarioFirebase().getUid())
                .child(getIdAnuncio())
                .setValue(this);

        salvarAnuncioPublico();
    }

    public void salvarAnuncioPublico(){
        DatabaseReference anuncioPublicoRef = FirebaseHelper.getFirebaseDatabase()
                .child("anuncios");
        anuncioPublicoRef.child(getEstado())
                .child(getCategoria())
                .child(getIdAnuncio())
                .setValue(this);
    }

    public void remover(){
        DatabaseReference anuncioRef = FirebaseHelper.getFirebaseDatabase()
                .child("meus_anuncios");
        anuncioRef.child(UsuarioFirebase.getUsuarioFirebase().getUid())
                .child(getIdAnuncio());
        anuncioRef.removeValue();
        removerAnuncioPublico();
    }

    public void removerAnuncioPublico(){
        DatabaseReference anuncioPublicoRef = FirebaseHelper.getFirebaseDatabase()
                .child("anuncios");
        anuncioPublicoRef.child(getEstado())
                .child(getCategoria())
                .child(getIdAnuncio());
        anuncioPublicoRef.removeValue();
    }

    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getLsFotos() {
        return lsFotos;
    }

    public void setLsFotos(List<String> lsFotos) {
        this.lsFotos = lsFotos;
    }
}
