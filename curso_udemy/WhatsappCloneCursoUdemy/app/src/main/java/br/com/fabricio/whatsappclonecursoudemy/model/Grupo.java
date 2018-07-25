package br.com.fabricio.whatsappclonecursoudemy.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.utils.Base64Custom;

public class Grupo implements Serializable {
    private String id;
    private String nome;
    private String foto;
    private List<Usuario> lsMembros;

    public Grupo() {
        DatabaseReference databaseReference = FirebaseHelper.getFirebaseDatabase();
        DatabaseReference gruposRef = databaseReference.child("grupos");

        String idGrupoFirebase = gruposRef.push().getKey();
        setId(idGrupoFirebase);
    }

    public void salvar(){
        DatabaseReference databaseReference = FirebaseHelper.getFirebaseDatabase();
        DatabaseReference gruposRef = databaseReference.child("grupos");

        gruposRef.child("grupos")
                .child(this.id)
                .setValue(this);

        //salvar conversa para membros do grupo
        for (Usuario u : getLsMembros()){

            String idRemetente = Base64Custom.encodeToString(u.getEmail());
            String idDestinatario = getId();

            Conversa conversa = new Conversa();
            conversa.setIdRemetente(idRemetente);
            conversa.setIdDestinatario(idDestinatario);
            conversa.setUltimaMensagem("");
            conversa.setIsGroup("true");
            conversa.setGrupo(this);

            conversa.salvar();
        }
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Usuario> getLsMembros() {
        return lsMembros;
    }

    public void setLsMembros(List<Usuario> lsMembros) {
        this.lsMembros = lsMembros;
    }
}
