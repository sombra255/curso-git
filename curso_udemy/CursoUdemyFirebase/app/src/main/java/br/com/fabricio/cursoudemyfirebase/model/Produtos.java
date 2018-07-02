package br.com.fabricio.cursoudemyfirebase.model;

/**
 * Created by Fabricio on 24/06/2018.
 */

public class Produtos {

    private String descricao;
    private String marca;
    private Double preco;

    public Produtos() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
