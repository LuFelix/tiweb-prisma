package online.lucianofelix.fuse;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import online.lucianofelix.beans.Pessoa;
import online.lucianofelix.beans.fuse.Fuse;
import online.lucianofelix.beans.fuse.Indicadores;
import online.lucianofelix.beans.fuse.ItensIndicadores;
import online.lucianofelix.beans.fuse.Operacao;
import online.lucianofelix.beans.fuse.OrdemBovespa;
import online.lucianofelix.dao.DAOFuse;
import online.lucianofelix.visao.FrameInicial;

public class PainelFuse extends JPanel {

	/**
	 * @return the listOrd
	 */
	public static List<OrdemBovespa> getListOrd() {
		return listOrd;
	}

	/**
	 * @param listOrd
	 *            the listOrd to set
	 */
	public static void setListOrd(List<OrdemBovespa> listOrd) {
		PainelFuse.listOrd = listOrd;
	}

	public static JTable getTabelaOperacoes() {
		return tabelaOperacoes;
	}

	public static void setTabelaOperacoes(JTable tabelaOperacoes) {
		PainelFuse.tabelaOperacoes = tabelaOperacoes;
	}

	public static JTable getTabelaOrdens() {
		return tabelaOrdens;
	}

	public static void setTabelaOrdens(JTable tabelaOrdens) {
		PainelFuse.tabelaOrdens = tabelaOrdens;
	}

	// JFrame telaPedido;
	JPanel painelPrincipal;
	private JPanel pnlGrid;
	private JScrollPane scrImagem;
	private JSplitPane sppImagem;
	private JSplitPane sppSuperior;
	private JSplitPane sppPrincipal;
	private JPanel pnlInferior;

	private JLabel lblTituloTela;
	private JLabel lblImagem;
	// Labels e text fields
	private JLabel lblCodigoFuse;
	private static JTextField txtFCodigoFuse;
	private JLabel lblTipoFuse;
	private static JTextField txtFTipoFuse;
	private JLabel lblCodiAtivo;
	private static JTextField txtFCodiAtivo;
	private JLabel lblLucroPrejuizo;
	private static JTextField txtFLucroPrejuizo;
	private JLabel lblStartCompra;
	private static JTextField txtFStartCompra;
	private JLabel lblOrdemCompra;
	private static JTextField txtFOrdemCompra;
	private JLabel lblStartVenda;
	private static JTextField txtFStartVenda;
	private JLabel lblOrdemVenda;
	private static JTextField txtFOrdemVenda;
	private JLabel lblQuant;
	private static JTextField txtFQuantItens;
	private JLabel lblPrecoTotal;
	private static JTextField txtFTotalFuse;
	private JLabel lblDataInicio;
	private JLabel lblPermiteEditar;

	private static JScrollPane scrOperacoes;
	private static JScrollPane scrIndicadores;
	private static JScrollPane scrOrdens;
	private JScrollPane scrObsPedido;
	private JTabbedPane tabVisualiza;

	private JRadioButton jrbEditarSim;
	private JRadioButton jrbEditarNao;
	private ButtonGroup grpRadio;
	private ActionListener acaoEditar;
	private ActionListener sobrescrever;

	// TODO objetos de controle

	private static ControlaListaFuse controledaLista;
	private static ControlaFuse contFuse;
	private static Fuse fuse;
	private static DAOFuse daoFuse;
	private List<Fuse> listfuse;
	int tam;

	private static JTextArea txtAreaObsPedido;
	private static JTable tabelaItensIndicadores;
	private static JTable tabelaOperacoes;
	private static JTable tabelaOrdens;
	private static DefaultTableModel modeloTabela;
	private static List<ItensIndicadores> arrayItensIndi;
	private static List<Operacao> arrayOperacao;
	private static List<OrdemBovespa> listOrd;
	private static float total;
	private static int movimentos;
	private static int quantTotItens;
	private static ItensIndicadores itemIndi;

	public PainelFuse(String nome) {
		UIManager.put("TextField.font",
				new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font",
				new Font("Times New Roman", Font.PLAIN, 12));
		UIManager.put("Button.font",
				new Font("Times New Roman", Font.BOLD, 12));

		contFuse = new ControlaFuse();
		daoFuse = new DAOFuse();
		controledaLista = new ControlaListaFuse(listfuse);
		arrayItensIndi = new ArrayList<ItensIndicadores>();

		painelPrincipal = new JPanel();
		painelPrincipal.setBorder(BorderFactory.createEtchedBorder());

		painelPrincipal.setBackground(Color.WHITE);

		scrOrdens = new JScrollPane();
		scrOrdens.setSize(420, 320);

		scrOperacoes = new JScrollPane();
		scrOperacoes.setSize(420, 320);

		scrIndicadores = new JScrollPane();
		scrIndicadores.setSize(420, 320);

		txtAreaObsPedido = new JTextArea();
		scrObsPedido = new JScrollPane();
		scrObsPedido.setSize(420, 320);
		scrObsPedido.setViewportView(txtAreaObsPedido);

		tabVisualiza = new JTabbedPane();
		tabVisualiza.setBounds(0, 360, 525, 210);
		tabVisualiza.setBorder(new EmptyBorder(0, 0, 0, 0));

		tabVisualiza.add("Operações", scrOperacoes);
		tabVisualiza.add("Ordens", scrOrdens);
		tabVisualiza.add("Indicadores", scrIndicadores);
		tabVisualiza.addTab("Observações", scrObsPedido);

		// TODO Labels e Text fields

		lblTituloTela = new JLabel("FUSE");
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 28));

		lblTipoFuse = new JLabel("Tipo Fuse");
		txtFTipoFuse = new JTextField();
		lblCodigoFuse = new JLabel("Código:");
		txtFCodigoFuse = new JTextField();
		txtFCodigoFuse.setHorizontalAlignment(JTextField.LEFT);
		txtFCodigoFuse.setFocusable(false);

		lblDataInicio = new JLabel(
				String.valueOf(Calendar.getInstance().getTime()));
		lblCodiAtivo = new JLabel("Ativo: ");
		txtFCodiAtivo = new JTextField();
		txtFCodiAtivo.setHorizontalAlignment(JTextField.LEFT);
		txtFCodiAtivo.setEditable(false);
		txtFCodiAtivo.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Cond. pagamento ao perder foco

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Cond. pagamento ao ganhar foco
				FrameInicial.pesquisaAtivoRealizaOp();

			}
		});
		lblQuant = new JLabel("Quantidade: ");
		txtFQuantItens = new JTextField();
		txtFQuantItens.setHorizontalAlignment(JTextField.LEFT);
		txtFQuantItens.setFont(new Font("TimesNew Roman", Font.BOLD, 16));
		txtFQuantItens.setEditable(false);

		lblStartCompra = new JLabel("Start Compra: ");
		txtFStartCompra = new JTextField();
		txtFStartCompra.setHorizontalAlignment(JTextField.LEFT);
		txtFStartCompra.setFont(new Font("TimesNew Roman", Font.BOLD, 16));
		txtFStartCompra.setEditable(false);
		txtFStartCompra.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO txtfCliente ao perder foco
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO txtfCliente ao receber foco
				FrameInicial.pesquisaUsuarioAdicionarAOPedido();
			}
		});

		lblOrdemCompra = new JLabel("Ordem Compra: ");
		txtFOrdemCompra = new JTextField();
		txtFOrdemCompra.setHorizontalAlignment(JTextField.LEFT);
		txtFOrdemCompra.setFont(new Font("TimesNew Roman", Font.BOLD, 16));

		lblStartVenda = new JLabel("Start Venda: ");
		txtFStartVenda = new JTextField();
		txtFStartVenda.setHorizontalAlignment(JTextField.LEFT);
		txtFStartVenda.setFont(new Font("TimesNew Roman", Font.BOLD, 16));

		lblOrdemVenda = new JLabel("Ordem Venda: ");
		txtFOrdemVenda = new JTextField();
		txtFOrdemVenda.setHorizontalAlignment(JTextField.LEFT);
		txtFOrdemVenda.setFont(new Font("TimesNew Roman", Font.BOLD, 16));

		lblLucroPrejuizo = new JLabel("L|P/Ação:");
		txtFLucroPrejuizo = new JTextField();
		txtFLucroPrejuizo.setHorizontalAlignment(JTextField.LEFT);
		txtFLucroPrejuizo.setFont(new Font("TimesNew Roman", Font.BOLD, 16));
		txtFLucroPrejuizo.setEditable(false);

		lblPrecoTotal = new JLabel("TOTAL: ");
		lblPrecoTotal.setFont(new Font("Times New Roman", Font.BOLD, 18));
		txtFTotalFuse = new JTextField();
		txtFTotalFuse.setFont(new Font("Times New Roman", Font.BOLD, 28));
		// txtFTotalFuse.setBackground(Color.BLACK);
		txtFTotalFuse.setHorizontalAlignment(JTextField.LEFT);
		txtFTotalFuse.setEditable(false);

		lblPermiteEditar = new JLabel("Permite Editar ");
		lblPermiteEditar.setBounds(105, 590, 100, 25);

		scrImagem = new JScrollPane(lblImagem);
		scrImagem.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrImagem.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sppImagem = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppImagem.add(lblTituloTela);
		sppImagem.add(scrImagem);
		sppImagem.setDividerLocation(50);
		sppImagem.setEnabled(false);
		sppImagem.setBackground(Color.WHITE);
		sppImagem.setForeground(Color.WHITE);
		sppImagem.setDividerSize(3);

		pnlGrid = new JPanel();
		pnlGrid.setBorder(BorderFactory.createEtchedBorder());
		pnlGrid.setLayout(new GridLayout(10, 2));
		pnlGrid.setBackground(Color.WHITE);

		pnlGrid.add(lblTipoFuse);
		pnlGrid.add(txtFTipoFuse);
		pnlGrid.add(lblCodigoFuse);
		pnlGrid.add(txtFCodigoFuse);
		pnlGrid.add(lblCodiAtivo);
		pnlGrid.add(txtFCodiAtivo);
		pnlGrid.add(lblLucroPrejuizo);
		pnlGrid.add(txtFLucroPrejuizo);
		pnlGrid.add(lblStartCompra);
		pnlGrid.add(txtFStartCompra);
		pnlGrid.add(lblStartVenda);
		pnlGrid.add(txtFStartVenda);
		pnlGrid.add(lblOrdemCompra);
		pnlGrid.add(txtFOrdemCompra);
		pnlGrid.add(lblOrdemVenda);
		pnlGrid.add(txtFOrdemVenda);
		pnlGrid.add(lblQuant);
		pnlGrid.add(txtFQuantItens);

		pnlGrid.add(lblPrecoTotal);
		pnlGrid.add(txtFTotalFuse);
		// pnlGrid.add(lblDataInicio);

		sppSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppSuperior.setDividerLocation(200);
		sppSuperior.setDividerSize(3);
		sppSuperior.setEnabled(false);
		sppSuperior.add(sppImagem);
		sppSuperior.add(pnlGrid);

		pnlInferior = new JPanel();
		pnlInferior.setBorder(BorderFactory.createEtchedBorder());
		pnlInferior.setLayout(new GridLayout());
		pnlInferior.setBackground(Color.WHITE);
		pnlInferior.add(tabVisualiza);

		sppPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppPrincipal.setDividerSize(3);
		sppPrincipal.setDividerLocation(320);
		sppPrincipal.setEnabled(false);
		sppPrincipal.setBackground(Color.WHITE);
		sppPrincipal.add(sppSuperior);
		sppPrincipal.add(pnlInferior);
		setLayout(new GridLayout());
		setBackground(Color.WHITE);

		desHabilitaEdicao();
		listfuse = contFuse.pesqNomeArray(nome);
		tam = listfuse.size();
		tam--;
		if (tam < 0) {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.setTabela(null);
			FrameInicial.atualizaTela();
		} else {
			controledaLista = new ControlaListaFuse(listfuse);
			fuse = controledaLista.first();
			carregarCampos(fuse);
		}

		add(sppPrincipal);

	}

	// TODO Vai para uma posição específica
	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		fuse = controledaLista.getAt(posicao);
		carregarCampos(fuse);
	}

	private boolean listavazia() {
		if (tam == -1) {
			return true;
		}
		return false;
	}

	// Colocar o indice correto para cada lista tam é o indice da lista de fuses
	List<ItensIndicadores> removeItemInd() {
		arrayItensIndi.remove(tam);
		atualizaTabelaIndi();
		return arrayItensIndi;
	}

	List<OrdemBovespa> removeOrdem() {
		listOrd.remove(tam);
		atualizaTabelaIndi();
		return listOrd;
	}

	// TODO Adicionar Indicador à Fuse
	public static List<ItensIndicadores> adicionaItem(Indicadores indi) {
		movimentos = Integer.parseInt(
				JOptionPane.showInputDialog(null, "Quantidade de Movimentos"));
		itemIndi = new ItensIndicadores();
		itemIndi.setItemIndi(indi);
		itemIndi.setPeriodo(movimentos);
		arrayItensIndi.add(itemIndi);
		if (arrayItensIndi.contains(itemIndi)) {
		} else {
		}
		atualizaTabelaIndi();
		return arrayItensIndi;
	}

	public static List<OrdemBovespa> adicionaOrdem(Indicadores indi) {
		OrdemBovespa ord = new OrdemBovespa();
		// deve ler os campos referentes à ordem
		listOrd.add(ord);
		if (listOrd.contains(ord)) {
			// Deve perguntar se quer alterar essa ordem ou deixar duas ordens
			// identicas
		} else {
		}
		atualizaTabelaOrdens();
		return listOrd;
	}

	// TODO Atualizar as tabelas da Fuse
	public static JTable atualizaTabelaIndi() {
		tabelaItensIndicadores = new JTable();
		modeloTabela = new DefaultTableModel();
		modeloTabela = (DefaultTableModel) tabelaItensIndicadores.getModel();
		Object colunas[] = {"Nome", "Movimentos"};
		modeloTabela.setColumnIdentifiers(colunas);
		tabelaItensIndicadores = new JTable(modeloTabela);
		for (int i = 0; i < arrayItensIndi.size(); i++) {
			Object linha[] = {arrayItensIndi.get(i).getNomeIndi(),
					arrayItensIndi.get(i).getPeriodo()};
			modeloTabela.addRow(linha);
		}
		scrOperacoes.setViewportView(tabelaItensIndicadores);
		return tabelaItensIndicadores;
	}

	public static JTable atualizaTabelaOrdens() {
		tabelaOrdens = new JTable();
		modeloTabela = new DefaultTableModel();
		modeloTabela = (DefaultTableModel) tabelaOrdens.getModel();
		Object colunas[] = {"Ativo", "Valor", "Tipo"};
		modeloTabela.setColumnIdentifiers(colunas);
		tabelaItensIndicadores = new JTable(modeloTabela);
		// for (int i = 0; i < arrayItensIndi.size(); i++) {
		// Object linha[] = { listOrd.get(i).getCodiAtivo(),
		// listOrd.get(i).getValorOrdem(),
		// listOrd.get(i).getTipoOrdem() };
		// modeloTabela.addRow(linha);
		// }
		scrOrdens.setViewportView(tabelaOrdens);
		return tabelaOrdens;
	}

	public static JTable atualizaTabelaOpViculadas(final Fuse fuse) {
		System.out.println("PainelPedidos.atualizaTabela");
		modeloTabela = new DefaultTableModel();
		tabelaOperacoes = new JTable(modeloTabela);
		tabelaOperacoes.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int posicao = tabelaOperacoes.getSelectedRow();
				carregarCampos(fuse);

			}
		});
		Object colunas[] = {"Código", "Quantidade", "Valor Unit.", "Valor Op",
				"Tipo Op", "Data"};
		modeloTabela.setColumnIdentifiers(colunas);
		getTabelaOperacoes().setRowSelectionAllowed(false);
		getTabelaOperacoes().setCellSelectionEnabled(false);
		getTabelaOperacoes().setColumnSelectionAllowed(false);

		contFuse.carregarOperVinculadas(fuse);
		for (int i = 0; i < fuse.getListOperVinc().size(); i++) {
			Object linha[] = {fuse.getListOperVinc().get(i).getCodiAtivo(),
					fuse.getListOperVinc().get(i).getQtdPapeis(),
					fuse.getListOperVinc().get(i).getValorPapel(),
					fuse.getListOperVinc().get(i).getTotal(),
					fuse.getListOperVinc().get(i).getTipoOp(),
					fuse.getListOperVinc().get(i).getDataHoraExec()};
			modeloTabela.addRow(linha);
		}
		tabelaOperacoes.setShowGrid(true);
		scrOperacoes.setViewportView(tabelaOperacoes);
		return tabelaOperacoes;
	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();
		arrayItensIndi = new ArrayList<ItensIndicadores>();
		fuse = new Fuse();
		atualizaTabelaIndi();
		atualizaTabelaOrdens();
		atualizaTabelaOpViculadas(fuse);
		String codigo = criaCodiPedi();
		try {
			daoFuse.reservaCodigo(codigo);
		} catch (SQLException e) {
			// TODO Erro ao conectar banco.
			JOptionPane.showMessageDialog(null,
					"Erro ao reservar código para o pedido/n" + e.getMessage());
			e.printStackTrace();
		}

		txtFTipoFuse.setEditable(true);
		txtFCodigoFuse.setText(codigo);
		txtFCodigoFuse.setEditable(false);
		txtFCodiAtivo.setEditable(true);
		txtFCodiAtivo.setFocusable(true);
		txtFLucroPrejuizo.setEditable(true);
		txtFQuantItens.setEditable(true);
		txtFStartCompra.setEditable(true);
		txtFStartCompra.setFocusable(true);
		txtAreaObsPedido.setEditable(true);
		txtAreaObsPedido.setEnabled(true);

	}

	// TODO Habilita edição
	public static void habilitaEdicao() {
		txtFTipoFuse.setEditable(true);
		txtFCodigoFuse.setEditable(false);
		txtFCodiAtivo.setEditable(true);
		txtFCodiAtivo.setFocusable(true);
		txtFLucroPrejuizo.setEditable(true);
		txtFQuantItens.setEditable(true);
		txtFStartCompra.setEditable(true);
		txtFStartCompra.setFocusable(true);
		txtFTotalFuse.setEditable(true);
		txtAreaObsPedido.setEditable(true);
		txtAreaObsPedido.setEnabled(true);

	}

	// TODO desabilita edição
	public void desHabilitaEdicao() {
		txtFTipoFuse.setEditable(false);
		txtFCodigoFuse.setEditable(false);
		txtFCodiAtivo.setEditable(false);
		txtFCodiAtivo.setFocusable(false);
		txtFLucroPrejuizo.setEditable(false);
		txtFQuantItens.setEditable(false);
		txtFStartCompra.setEditable(false);
		txtFStartCompra.setFocusable(false);
		txtFTotalFuse.setEditable(false);
		txtFTotalFuse.setText(null);
		txtAreaObsPedido.setEditable(false);
		txtAreaObsPedido.setEnabled(false);
	}

	// TODO Limpa Campos
	public static void limparCampos() {
		txtFTipoFuse.setText(null);
		txtFCodigoFuse.setText(null);
		txtFCodiAtivo.setText(null);
		txtFLucroPrejuizo.setText(null);
		txtFQuantItens.setText(null);
		txtFStartCompra.setText(null);
		txtFTotalFuse.setText(null);
		txtFQuantItens.setText(null);
		txtAreaObsPedido.setText(null);
		total = 0;
		movimentos = 0;
		quantTotItens = 0;
		arrayItensIndi = null;
		setTabelaItensIndicadores(null);
		// atualizaTabela();
	}

	// TODO Carregar a tela com um FUSE
	public static void carregarCampos(Fuse fuse) {
		if (!fuse.equals(null)) {
			txtFTipoFuse.setText(fuse.getTipoFuse());
			txtFCodigoFuse.setText(fuse.getCodiFuse());
			txtFQuantItens.setText(String.valueOf(fuse.getQuant()));

			if (fuse.getTotal() >= 0) {
				txtFTotalFuse.setForeground(Color.BLUE);
				txtFTotalFuse.setText(String.valueOf(fuse.getTotal()));
			} else {
				txtFTotalFuse.setForeground(Color.RED);
				txtFTotalFuse.setText(String.valueOf(fuse.getTotal()));
			}

			txtFCodiAtivo.setText(fuse.getCodiAtivo());
			if (fuse.getTipoFuse().equals("Vendida")) {
				txtFStartCompra
						.setText(String.valueOf(fuse.getPrecStartVenda()));
				txtFOrdemCompra.setText(String.valueOf(fuse.getPrecOrdVenda()));
				txtFStartVenda
						.setText(String.valueOf(fuse.getPrecStartCompra()));
				txtFOrdemVenda.setText(String.valueOf(fuse.getPrecOrdCompra()));
				txtFLucroPrejuizo
						.setText(String.valueOf(fuse.getLucroPrejuizo()));
				txtFTotalFuse.setText(String.valueOf(fuse.getTotal()));
			} else {
				txtFStartCompra
						.setText(String.valueOf(fuse.getPrecStartCompra()));
				txtFOrdemCompra
						.setText(String.valueOf(fuse.getPrecOrdCompra()));
				txtFStartVenda
						.setText(String.valueOf(fuse.getPrecStartVenda()));
				txtFOrdemVenda.setText(String.valueOf(fuse.getPrecOrdVenda()));
			}
			atualizaTabelaOpViculadas(fuse);
			setArrayItensIndi(fuse.getListIndiUtilizados());
			setListOrd(fuse.getListOrdGeradas());
			// atualizaTabelaIndi();
			atualizaTabelaOrdens();
		}
	}

	// TODO Ler os campos e retornar um pedido
	public static Fuse lerCampos() {
		fuse = new Fuse();
		fuse.setCodiFuse(txtFCodigoFuse.getText());
		if (!txtFCodiAtivo.getText().equals("") & !txtFCodiAtivo.equals(null)) {
			// fuse.setCodiCondPag(txtfCondPag.getText());
		}
		if (!txtFStartCompra.getText().equals("")
				& !txtFStartCompra.equals(null)) {
			// fuse.setxNome(txtfCliente.getText());
		}
		fuse.setListIndiUtilizados(arrayItensIndi);
		if (!txtFQuantItens.getText().equals(null)
				& !txtFQuantItens.getText().equals("")) {
			// fuse.setQuantItens(Integer.parseInt(txtFQuantItens.getText()));
		}
		if (!txtFTotalFuse.getText().equals(null)
				& !txtFTotalFuse.getText().equals("")) {
			// fuse.setTotalPedi(Float.parseFloat(txtFPrecopedi.getText()));
		}
		// fuse.setCodiCondPag(txtfCondPag.getText());
		// ItensProduto[] arrteste = pedi.getItensProdutoArray(arrayItemProd);
		// System.out.println("Tamanho " + arrteste.length);
		// System.out.println(arrteste[0].getNome_prod());
		return fuse;
	}

	// TODO Adicionar um cliente
	public static void adicionaUsuario(Pessoa usua) {
		txtFStartCompra.setText(usua.getNome());
	}

	// TODO Setar o codigo da FUSE
	public static String criaCodiPedi() {
		Calendar c = Calendar.getInstance();
		daoFuse.consultaUltimo();
		String codiPedi = String.valueOf(daoFuse.consultaUltimo())
				+ String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH))
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codiPedi;
	}

	public static JTextField getTxtFNomepedi() {
		return txtFCodiAtivo;
	}

	public static void setTxtFNomepedi(JTextField txtFNomepedi) {
		PainelFuse.txtFCodiAtivo = txtFNomepedi;
	}

	public static JScrollPane getScrOperacoes() {
		return scrOperacoes;
	}

	public static void setScrOpesracoes(JScrollPane scrOperacoes) {
		PainelFuse.scrOperacoes = scrOperacoes;
	}

	public static JTable getTabelaItensIndicadores() {
		return tabelaItensIndicadores;
	}

	public static void setTabelaItensIndicadores(JTable tabelaItensIndi) {
		PainelFuse.tabelaItensIndicadores = tabelaItensIndi;
	}

	public JLabel getLblQuantItens() {
		return lblQuant;
	}

	public void setLblQuantItens(JLabel lblQuantItens) {
		this.lblQuant = lblQuantItens;
	}

	public static List<ItensIndicadores> getArrayItensIndi() {
		return arrayItensIndi;
	}

	public static void setArrayItensIndi(
			List<ItensIndicadores> arrayItensIndi) {
		PainelFuse.arrayItensIndi = arrayItensIndi;
	}

	public static JTextField getTxtfCondPag() {
		return txtFCodiAtivo;
	}

	public static void setTxtfCondPag(JTextField txtfCondPag) {
		PainelFuse.txtFCodiAtivo = txtfCondPag;
	}

	public static JTextField getTxtfCliente() {
		return txtFStartCompra;
	}

	public static void setTxtfCliente(JTextField txtfCliente) {
		PainelFuse.txtFStartCompra = txtfCliente;
	}

	/**
	 * @return the arrayOperacao
	 */
	public static List<Operacao> getArrayOperacao() {
		return arrayOperacao;
	}

	/**
	 * @param arrayOperacao
	 *            the arrayOperacao to set
	 */
	public static void setArrayOperacao(List<Operacao> arrayOperacao) {
		PainelFuse.arrayOperacao = arrayOperacao;
	}

}
