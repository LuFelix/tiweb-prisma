package online.lucianofelix.beans;

public class Servico {

	private int seqServico; // Sequencia de inser��o no banco.
	private String codiServico; // C�digo extra 2 para o produto.
	private String nomeServico; // Nome do servi�o.
	private String descServico; // Descri��o do �tem.
	private String codiCategoria;// Chave para a tabela de categoria.
	private float tempoResposta;// Tempo m�ximo para o �nicio do servi�o.
	private float tempoExecucao;// Tempo de execu��o do servi�o.
	private float duracao; // Quant. de horas necess�rias para o servi�o.
	private float precoHora; // O pre�o da hora trabalhada.
	private float precoAdicional; // Alguma taxa extra

	public String getCodiCategoria() {
		return codiCategoria;
	}

	public void setCodiCategoria(String codiCategoria) {
		this.codiCategoria = codiCategoria;
	}

	public float getTempoResposta() {
		return tempoResposta;
	}

	public void setTempoResposta(float tempoResposta) {
		this.tempoResposta = tempoResposta;
	}

	public float getTempoExecucao() {
		return tempoExecucao;
	}

	public void setTempoExecucao(float tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}

	public float getDuracao() {
		return duracao;
	}

	public void setDuracao(float duracao) {
		this.duracao = duracao;
	}

	public int getSeqServico() {
		return seqServico;
	}

	public void setSeqServico(int seqServico) {
		this.seqServico = seqServico;
	}

	public String getCodiServico() {
		return codiServico;
	}

	public void setCodiServico(String codiServico) {
		this.codiServico = codiServico;
	}

	public String getNomeServico() {
		return nomeServico;
	}

	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public String getDescServico() {
		return descServico;
	}

	public void setDescServico(String descServico) {
		this.descServico = descServico;
	}

	public float getPrecoHora() {
		return precoHora;
	}

	public void setPrecoHora(float precoHora) {
		this.precoHora = precoHora;
	}

	public float getPrecoAdicional() {
		return precoAdicional;
	}

	public void setPrecoAdicional(float precoAdicional) {
		this.precoAdicional = precoAdicional;
	}
}
