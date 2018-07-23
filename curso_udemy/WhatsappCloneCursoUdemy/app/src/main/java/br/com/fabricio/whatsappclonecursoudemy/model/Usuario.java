package br.com.fabricio.whatsappclonecursoudemy.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String foto;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebase = FirebaseHelper.getFirebaseDatabase();
        firebase.child("usuarios")
                .child(this.id)
                .setValue(this);
    }

    public void atualizar(){
        String identificadorUsuario = UsuarioFirebase.getUsuarioFirebase();
        DatabaseReference databaseReference = FirebaseHelper.getFirebaseDatabase();

        DatabaseReference usuarioRef = databaseReference.child("usuarios")
                .child(identificadorUsuario);

        Map<String, Object> valoresUsuario = converterToMap();

        usuarioRef.updateChildren(valoresUsuario);
    }

    @Exclude
    public Map<String, Object> converterToMap(){
        HashMap<String, Object> usuarioMap = new HashMap<>();

        usuarioMap.put("email", getEmail());
        usuarioMap.put("nome", getNome());
        usuarioMap.put("foto", getFoto());

        return usuarioMap;
    }

    @Exclude
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
