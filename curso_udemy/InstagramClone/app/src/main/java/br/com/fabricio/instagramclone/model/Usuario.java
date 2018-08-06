package br.com.fabricio.instagramclone.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import br.com.fabricio.instagramclone.helper.FirebaseHelper;

/**
 * Created by Fabricio on 01/08/2018.
 */

public class Usuario {

    private String id;
    private String nome;
    private String nomeMaiusculo;
    private String email;
    private String senha;
    private String caminhoFoto;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference database = FirebaseHelper.getDatabaseReference();
        DatabaseReference usuarioRef = database.child("usuarios");
        usuarioRef.child(this.getId())
                .setValue(this);
    }

    public void atualizar() {
        DatabaseReference firebaseRef = FirebaseHelper.getDatabaseReference();
        DatabaseReference usuarioRef = firebaseRef.child("usuarios")
                .child(getId());

        usuarioRef.updateChildren(converteParaMap());
    }

    public Map<String, Object> converteParaMap(){
        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail());
        usuarioMap.put("nome", getNome());
        usuarioMap.put("nomeMaiusculo", getNome().toUpperCase());
        usuarioMap.put("id", getId());
        usuarioMap.put("caminhoFoto", getCaminhoFoto());

        return usuarioMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNomeMaiusculo() {
        return nomeMaiusculo;
    }

    public void setNomeMaiusculo(String nomeMaiusculo) {
        this.nomeMaiusculo = nomeMaiusculo;
    }
}
