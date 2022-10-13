package online.lucianofelix.tableModels.fuse;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

import online.lucianofelix.dao.DAOConta;
import online.lucianofelix.dao.DAOPessoaPG;

public class TableModelStockUnit extends AbstractTableModel {
	private List<StockUnit> linhas;
	private String[] colunas = new String[]{"Abertura", "M�ximo", "M�nimo",
			"Volume", "Fechamento", "Data"};

	private static final int Abertura = 0;
	private static final int M�ximo = 1;
	private static final int M�nimo = 2;
	private static final int Volume = 3;
	private static final int Fechamento = 4;
	private static final int Data = 5;
	DAOPessoaPG daoPess;
	DAOConta daoConta;

	public TableModelStockUnit() {
		linhas = new ArrayList<StockUnit>();
	}

	public TableModelStockUnit(List<StockUnit> list) {
		linhas = new ArrayList<StockUnit>(list);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		StockUnit st = linhas.get(rowIndex);
		switch (columnIndex) {
			case Abertura :
				return st.getOpen();
			case M�ximo :
				return st.getHigh();
			case M�nimo :
				return st.getLow();
			case Fechamento :
				return st.getClose();
			case Volume :
				return st.getVolume();
			case Data :
				return st.getDate();
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	public StockUnit getStockUnit(int linha) {
		return linhas.get(linha);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case Abertura :
				return Double.class;
			case M�ximo :
				return Double.class;
			case M�nimo :
				return Double.class;
			case Volume :
				return Long.class;
			case Fechamento :
				return Double.class;
			case Data :
				return String.class;

			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		StockUnit st = linhas.get(rowIndex);
		switch (columnIndex) {
			case Abertura :
				// l.setCodiPedido((String) aValue);
				break;
			case M�ximo :
				// l.setCodiPessoa((String) aValue);
				break;
			case M�nimo :
				// l.setVencimentoHoraLancamento((Date) aValue);
				break;
			case Volume :
				// l.setValor((Float) aValue);
				break;
			case Fechamento :
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

			case Abertura :
				return columnIndex == Abertura;
			case M�ximo :
				return columnIndex == M�ximo;
			case M�nimo :
				return columnIndex == M�nimo;
			case Volume :
				return columnIndex == Volume;
			case Fechamento :
				return columnIndex == Fechamento;
			case Data :
				return columnIndex == Data;
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
