package online.lucianofelix.beans;

import java.sql.Timestamp;

public class ProdutoCotacao {
	private int seqCotacaoProduto; // Sequencia de grava��o dos pre�os.
	private float valor; // O valor do produto nesta data;
	private String codiTabela;// Refer�ncia � tabela qual o pre�o comp�e.
	private Timestamp dataHoraMarcacao; // Data e hora da marca��o
	private String codiServico;// Refer�ncia para a tabela de servi�os.
	private String codiProduto; // Refer�ncia para a tabela de produtos.
	private String descPreco;// Descri��o para o pre�o.

	public int getSeqCotacaoProduto() {
		return seqCotacaoProduto;
	}

	public void setSeqCotacaoProduto(int seqCotacaoProduto) {
		this.seqCotacaoProduto = seqCotacaoProduto;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getCodiTabela() {
		return codiTabela;
	}

	public void setCodiTabela(String codiTabela) {
		this.codiTabela = codiTabela;
	}

	public Timestamp getDataHoraMarcacao() {
		return dataHoraMarcacao;
	}

	public void setDataHoraMarcacao(Timestamp dataHoraMarcacao) {
		this.dataHoraMarcacao = dataHoraMarcacao;
	}

	public String getCodiServico() {
		return codiServico;
	}

	public void setCodiServico(String codiServico) {
		this.codiServico = codiServico;
	}

	public String getCodiProduto() {
		return codiProduto;
	}

	public void setCodiProduto(String codiProduto) {
		this.codiProduto = codiProduto;
	}

	public String getDescPreco() {
		return descPreco;
	}

	public void setDescPreco(String descPreco) {
		this.descPreco = descPreco;
	}

}
