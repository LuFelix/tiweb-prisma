package online.lucianofelix.beans;

public class GrupoSubgrupo {

	private boolean isroot;// Se selecionado o n� � raiz.
	private int noRaiz;// Tipo de sistema ao qual est� vinculado o n�
	private int seqGrupo;// C�digo sequencial
	private String codiGrupo; // C�digo composto para administra��o da tabela.
	private String nomeGrupo;// Nome para o grupo.
	private String noAncora;// Grupo pai para esse elemento, se existir.

	public int getSeqGrupo() {
		return seqGrupo;
	}

	public void setSeqGrupo(int seqGrupo) {
		this.seqGrupo = seqGrupo;
	}

	public String getCodiGrupo() {
		return codiGrupo;
	}

	public void setCodiGrupo(String codiGrupo) {
		this.codiGrupo = codiGrupo;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public String getNoAncora() {
		return noAncora;
	}

	public void setNoAncora(String noAncora) {
		this.noAncora = noAncora;
	}

	public Boolean getIsroot() {
		return isroot;
	}

	public void setIsroot(boolean isroot) {
		this.isroot = isroot;
	}

	public int getNoRaiz() {
		return noRaiz;
	}

	public void setNoRaiz(int noRaiz) {
		this.noRaiz = noRaiz;
	}

}
