package br.com.fabricio.appguanabaracasa.model;

import java.util.Objects;

/**
 * Created by Fabricio on 08/10/2016.
 */
public class Aluno {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private Double nota;

    public Aluno() {
    }

    public Aluno(Long id, String nome, String telefone, String email, String endereco, Double nota) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(nome, aluno.nome) &&
                Objects.equals(telefone, aluno.telefone) &&
                Objects.equals(email, aluno.email) &&
                Objects.equals(endereco, aluno.endereco) &&
                Objects.equals(nota, aluno.nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone, email, endereco, nota);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", nota=" + nota +
                '}';
    }
}
