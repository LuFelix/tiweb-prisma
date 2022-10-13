package online.lucianofelix.tableModels.commom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import online.lucianofelix.beans.Lancamento;
import online.lucianofelix.dao.DAOLancamento;
import online.lucianofelix.dao.DAOPessoaPG;

public class TableModelBaixas extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7375078006660980560L;
	private List<Lancamento> linhas;
	private String[] colunas = new String[]{"Titular", "Documento", "Data",
			"Valor"};
	private static final int Titular = 0;
	private static final int Documento = 1;
	private static final int DataLancamento = 2;
	private static final int Valor = 3;
	DAOPessoaPG daoPess;
	DAOLancamento daoLanc;

	public TableModelBaixas() {
		daoPess = new DAOPessoaPG();
		linhas = new ArrayList<Lancamento>();
	}

	public TableModelBaixas(List<Lancamento> list) {
		daoPess = new DAOPessoaPG();
		linhas = new ArrayList<Lancamento>(list);
	}
	public TableModelBaixas(String codiCtaReceber) {
		// JOptionPane.showMessageDialog(null,
		// "TableModelBaixas (String codiCtaReceber); " + codiCtaReceber);
		daoLanc = new DAOLancamento();
		daoPess = new DAOPessoaPG();
		linhas = new ArrayList<Lancamento>(
				daoLanc.listBaixasNumCtaReceber(codiCtaReceber));
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Lancamento l = linhas.get(rowIndex);

		// CurrencyUnit real = Monetary.get("BRL");
		// Money money = Money.of((BigMoneyProvider) l.getValor());
		// System.out.println("TableModelBaixas" + money.toString());

		switch (columnIndex) {
			case Titular :
				// return daoPess.pessoaCodigo(l.getCodiPessoa()).getNome();
			case Documento :
				return l.getCodiPedido();
			case DataLancamento :
				return l.getDtHrLanc();
			case Valor :
				return l.getValorString().replace(".", ",");
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
			case Documento :
				return String.class;
			case DataLancamento :
				return Timestamp.class;
			case Valor :
				return String.class;

			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Lancamento l = linhas.get(rowIndex);
		switch (columnIndex) {
			case Titular :
				// l.setCodiPessoa((String) aValue);
				break;
			case Documento :
				// l.setCodiPedido((String) aValue);
				break;
			case DataLancamento :
				// l.setDataHoraLancamento((Date) aValue);
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

			case Titular :
				return columnIndex == Titular;
			case Documento :
				return columnIndex == Documento;
			case DataLancamento :
				return columnIndex == DataLancamento;
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
