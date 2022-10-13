/**
 * - ** a quantidade de parcelas - **um índice pra calcular juros - **uma
 * senha pra uso exclusivo **- a respectiva condição de pagamento no ECF **-
 * o desconto máximo ou acréscimo **- a geração do contas a receber (**s:n,
 * o tipo de numeração a seguir: número da nf ou fatura)
 * 
 * Acredito sim no respeito, na dedicação e na admiração que podemos ter
 * umas pessoas por outras. Deveria eu escrever um livro, mas ao eleger esse editor de
 * texto como meu preferido, decidi deixar minha vida aqui justamente ao
 * perceber que o tenho como bloco de todas as anotações. Dia 28/09/2015
 * estava a buscar o que armazenar na tabela de condições de pagamentos e
 * abri um e-mail do meu irmão evandro que continha justamente essas
 * anotações as quais são o cabeçalho de comentário da classe. Fé e
 * perseverança o caminho ainda é longo.
 * 
 * @author Luciano de Oliveira Felix Silva
 * 
 */
package online.lucianofelix.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CondPagamento {
	private Timestamp data_cadastro;
	private Timestamp data_ultima_alteracao;
	private String senha;
	private String codiCondPag;
	private String nomeCondicao;
	private String geraContasReceber;
	private float indiceJuros;
	private float descontoAcrescimo;
	private int seq_condPagamento;
	private int quantParcelas;
	private int tipoNumeracao;
	private int condicaoECF;
	private BigDecimal valor; // representar��o de valor, semelhante ao estoque

	public String getCodiCondPag() {
		return codiCondPag;
	}

	public int getCondicaoECF() {
		return condicaoECF;
	}

	public float getDescontoAcrescimo() {
		return descontoAcrescimo;
	}

	public String getGeraContasReceber() {
		return geraContasReceber;
	}

	public float getIndiceJuros() {
		return indiceJuros;
	}

	public String getNomeCondicao() {
		return nomeCondicao;
	}

	public int getQuantParcelas() {
		return quantParcelas;
	}

	public String getSenha() {
		return senha;
	}

	public int getSeq_condPagamento() {
		return seq_condPagamento;
	}

	public int getTipoNumeracao() {
		return tipoNumeracao;
	}

	public void setCodiCondPag(String codiCondPag) {
		this.codiCondPag = codiCondPag;
	}

	public void setCondicaoECF(int condicaoECF) {
		this.condicaoECF = condicaoECF;
	}

	public void setDescontoAcrescimo(float descontoAcrescimo) {
		this.descontoAcrescimo = descontoAcrescimo;
	}

	public void setGeraContasReceber(String string) {
		this.geraContasReceber = string;
	}

	public void setIndiceJuros(float indiceJuros) {
		this.indiceJuros = indiceJuros;
	}

	public void setNomeCondicao(String nomeCondicao) {
		this.nomeCondicao = nomeCondicao;
	}

	public void setQuantParcelas(int quantParcelas) {
		this.quantParcelas = quantParcelas;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setSeq_condPagamento(int seq_condPagamento) {
		this.seq_condPagamento = seq_condPagamento;
	}

	public void setTipoNumeracao(int tipoNumeracao) {
		this.tipoNumeracao = tipoNumeracao;
	}

	public Timestamp getData_ultima_alteracao() {
		return data_ultima_alteracao;
	}

	public void setData_ultima_alteracao(Timestamp data_ultima_alteracao) {
		this.data_ultima_alteracao = data_ultima_alteracao;
	}

	public Timestamp getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Timestamp data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
