package online.lucianofelix.tableModels.commom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import online.lucianofelix.beans.Lancamento;
import online.lucianofelix.dao.DAOConta;
import online.lucianofelix.dao.DAOPessoaPG;

public class TableModelLancamento extends AbstractTableModel {
	private List<Lancamento> linhas;
	private String[] colunas = new String[]{"Centro de Custo", "Titular",
			"Vencimento", "Valor"};

	private static final int CentroCusto = 0;
	private static final int Titular = 1;
	private static final int Vencimento = 2;
	private static final int Valor = 3;
	DAOPessoaPG daoPess;
	DAOConta daoConta;

	public TableModelLancamento() {
		daoPess = new DAOPessoaPG();
		daoConta = new DAOConta();
		linhas = new ArrayList<Lancamento>();
	}

	public TableModelLancamento(List<Lancamento> list) {
		daoPess = new DAOPessoaPG();
		daoConta = new DAOConta();
		linhas = new ArrayList<Lancamento>(list);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Lancamento l = linhas.get(rowIndex);
		String titular = "";
		switch (columnIndex) {
			case CentroCusto :
				return daoConta.nomeCustoCodiConta(l.getCodiConta());
			case Titular :
				if (l.getCodiPessoa() != null & l.getCodiPessoa() != "") {
					titular = daoPess.pessoaCodigo(l.getCodiPessoa()).getNome();
				}
				return titular;
			case Vencimento :
				return l.getDtHrVenc();
			case Valor :
				return l.getValor();

			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	public boolean contemNomeLista(Lancamento lanc) {
		for (Lancamento l : linhas) {
			if (l.equals(lanc)) {
				return true;
			}
		}
		return false;
	}

	public Lancamento getLancamento(int linha) {
		return linhas.get(linha);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case Titular :
				return String.class;
			case CentroCusto :
				return String.class;
			case Vencimento :
				return Timestamp.class;
			case Valor :
				return Float.class;

			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Lancamento l = linhas.get(rowIndex);
		switch (columnIndex) {
			case CentroCusto :
				// l.setCodiPedido((String) aValue);
				break;
			case Titular :
				// l.setCodiPessoa((String) aValue);
				break;
			case Vencimento :
				// l.setVencimentoHoraLancamento((Date) aValue);
				break;
			case Valor :
				// l.setValor((Float) aValue);
				break;

			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}

		fireTableCellUpdated(rowIndex, columnIndex);
		// daoPess.atualizar(l);

	}

	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {

			case CentroCusto :
				return columnIndex == CentroCusto;
			case Titular :
				return columnIndex == Titular;
			case Vencimento :
				return columnIndex == Vencimento;
			case Valor :
				return columnIndex == Valor;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}

	}
	// public void adicionaOcup(Pessoa p, int rowIndex) {
	// if (linhas.isEmpty()) {
	// PessoaProfissional pp = new PessoaProfissional();
	// pp.setCodiPess(p.getCodiPessoa());
	// linhas.add(pp);
	// daoPess.criarFuncao(pp);
	// }
	//
	// }
	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return linhas.size();
	}

}
