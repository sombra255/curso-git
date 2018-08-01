package br.com.fabricio.instagramclone.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import br.com.fabricio.instagramclone.helper.FirebaseHelper;

/**
 * Created by Fabricio on 01/08/2018.
 */

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String caminhoFoto;

    public Usuario() {
    }

    public void salvar(){
        FirebaseDatabase database = FirebaseHelper.getDatabaseReference().getDatabase();
        database.getReference()
                .child("usuario")
                .push()
                .setValue(this);
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
}
