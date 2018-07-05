package br.com.fabricio.cursoudemyorganizze.enums;

public enum TipoMovimentacao {
    DESPESA("despesa"), RECEITA("receita");

    private String descricao;

    TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
