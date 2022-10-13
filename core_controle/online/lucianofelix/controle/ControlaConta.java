package online.lucianofelix.controle;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import online.lucianofelix.beans.CentroCusto;
import online.lucianofelix.beans.Conta;
import online.lucianofelix.beans.Lancamento;
import online.lucianofelix.dao.DAOCentroCusto;
import online.lucianofelix.dao.DAOConta;
import online.lucianofelix.dao.DAOLancamento;
import online.lucianofelix.plcontas.PainelConta;
import online.lucianofelix.tableModels.commom.TableModelContas;
import online.lucianofelix.visao.FrameInicial;
import online.lucianofelix.visao.FrameInicial.ControlaBotoes;

public class ControlaConta {
	private JTable tbl01;
	private JTable tbl02;
	private JTable tbl03;
	private TableModelContas mdlTblContas;
	private DefaultTableModel mdlTblEntra;
	private DefaultTableModel mdlTblSai;
	private DAOConta daoConta;
	private DAOLancamento daoContaLanc;
	private DAOCentroCusto daoCentCust;
	private List<Lancamento> listLanc;
	private List<Conta> listContas;
	private String nome;
	private JComboBox<String> cmbContasCCusto;
	private CentroCusto cCust;
	private Conta c;

	public ControlaConta() {
		daoConta = new DAOConta();
		daoContaLanc = new DAOLancamento();
		daoCentCust = new DAOCentroCusto();
		cCust = new CentroCusto();

	}

	/**
	 * Criar c�digo para a nova conta
	 * 
	 * @return String codigo
	 */
	public String criaCodigo() {
		System.out.println("ControlaConta.criarCodigo");
		Calendar c = Calendar.getInstance();
		String codigo = String.valueOf(daoConta.consultaUltimo())
				+ String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH))
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codigo;
	}
	/**
	 * 
	 * @return
	 */
	public JTable tblctSaldoCentCusto() {
		tbl01 = new JTable();
		mdlTblContas = new TableModelContas(daoConta.listContaGroupByCCusto());
		tbl01.setModel(mdlTblContas);
		tbl01.setShowHorizontalLines(true);
		return tbl01;
	}

	/**
	 * Tabela pesquisa a por nome, detalhes e codigo Retorna uma tabela geral de
	 * contas agrupadas por Centro de Custo
	 * 
	 * @param str
	 * @return
	 */
	public JTable pesqNomeTabela(String str) {
		tbl01 = new JTable();
		mdlTblContas = new TableModelContas(daoConta.listContaGroupByCCusto());
		tbl01.setModel(mdlTblContas);
		tbl01.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTblContas.getConta(tbl01.getSelectedRow());
					PainelConta.carregarCampos(c);
					FrameInicial.atualizaTela();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tbl01.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTblContas.getConta(tbl01.getSelectedRow());
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					c = mdlTblContas.getConta(tbl01.getSelectedRow());
					PainelConta.carregarCampos(c);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelConta.getTxtFNomeConta().grabFocus();
				}
			}
		});
		tbl01.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				c = mdlTblContas.getConta(tbl01.getSelectedRow());
				PainelConta.carregarCampos(c);
				FrameInicial.atualizaTela();
			}
		});

		tbl01.setShowHorizontalLines(true);
		return tbl01;
	}

	/**
	 * Tabela de contas do centro de custo por nome, ou codigo. Retorna uma
	 * tabela completa caso o parametrop esteja em branco
	 * 
	 * @param String
	 * @return
	 */

	public JTable tblContasCentroCusto(String nomeCentro) {
		tbl01 = new JTable();
		mdlTblContas = new TableModelContas(
				daoConta.listContCCusto(nomeCentro));
		tbl01.setModel(mdlTblContas);
		tbl01.setShowGrid(true);
		tbl01.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTblContas.getConta(tbl01.getSelectedRow());
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tbl01.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelConta.carregarCampos(c);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelConta.getTxtFNomeConta().grabFocus();
				}
			}
		});
		tbl01.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				c = mdlTblContas.getConta(tbl01.getSelectedRow());
				PainelConta.carregarCampos(c);

			}
		});
		return tbl01;

	}

	/**
	 * Fun��o Salvar
	 */

	public void funcaoSalvar() {
		System.out.println("ControlaConta.funcaoSalvar");
		ControlaBotoes.limparBtnSalvar();
		ControlaBotoes.clicaNovoBotoes();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c = PainelConta.lerCampos();
				if (c != null) {
					PainelConta.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(c.getCodiConta()));
					PainelConta.carregarCampos(c);
					FrameInicial.atualizaTela();
					if (daoConta.cadastrar(c)) {
						JOptionPane.showMessageDialog(null, "Feito");
						iniciar(c.getCentroCusto());
					}
				} else {
					erro();
				}
			}
		});
	}

	/**
	 * Fun��o sobrescrever
	 */
	public void funcaoSobrescrever() {
		System.out.println("ControlaConta.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		ControlaBotoes.habilitaEdicaoBotoes();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c = PainelConta.lerCampos();
				if (!c.equals(null) & daoConta.alterar(c)) {

					JOptionPane.showMessageDialog(null, "Feito!");
					iniciar(c.getCentroCusto());
				} else {
					erro();
				}
			}
		});
	}

	/**
	 * TODO Funcao excluir
	 * 
	 * @return
	 */

	public boolean funcaoExcluir() {
		System.out.println("ControlaConta.excluir");
		c = PainelConta.lerCampos();
		if (daoConta.excluir(c)) {
			FrameInicial.setTabela(null);
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.atualizaTela();
			// JOptionPane.showMessageDialog(null, "Feito!");
			iniciar();
			return true;
		} else {
			funcaoCancelar();
			return false;
		}
	}

	/**
	 * Iniciar contas sem considerar o centro de custo
	 */
	public void iniciar() {
		System.out.println("ControlaConta.iniciar();");
		ControlaBotoes.limpaTodosBotoes();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getTxtfPesquisa().grabFocus();
		JScrollPane scrTbl = new JScrollPane();
		JPanel pnlLista = new JPanel();
		pnlLista.setBorder(
				BorderFactory.createTitledBorder("Contas do Centro de Custo"));
		scrTbl.setViewportView(tblContasCentroCusto(""));
		pnlLista.add(scrTbl);
		FrameInicial.setPnlLista(pnlLista);
		FrameInicial.setPainelVisualiza(new PainelConta());
		FrameInicial.atualizaTela();
		configuraBotes();
		configuraTxtPesquisa();

	}

	/**
	 * TODO Iniciar contas do centro de custo por nome ou codigo
	 * 
	 * @param codiCentro
	 */

	public void iniciar(String str) {
		System.out.println("ControlaConta.iniciar(String Codicentro);");
		ControlaBotoes.limpaTodosBotoes();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getTxtfPesquisa().grabFocus();
		JScrollPane scrTbl = new JScrollPane();
		JPanel pnlLista = new JPanel();
		pnlLista.setLayout(new BorderLayout());
		pnlLista.setBorder(
				BorderFactory.createTitledBorder("Contas do Centro de Custo"));
		scrTbl.setViewportView(tblContasCentroCusto(str));
		pnlLista.add(scrTbl, BorderLayout.CENTER);
		FrameInicial.setPainelVisualiza(new PainelConta());

		FrameInicial.atualizaTela();
		FrameInicial.getScrLista().setViewportView(pnlLista);
		configuraBotes();
		configuraTxtPesquisa();

	}

	/**
	 * TODO Configura TXT Pesquisa
	 */

	void configuraTxtPesquisa() {
		FrameInicial.limparTxtfPesquisa();
		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {// seta para baixo
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false,
							false);

				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					nome = FrameInicial.getTxtfPesquisa().getText();
					iniciar(nome);
					FrameInicial.atualizaTela();

				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {// seta para baixo
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false,
							false);

				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					nome = FrameInicial.getTxtfPesquisa().getText();
					iniciar(nome);
					FrameInicial.atualizaTela();

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * 
	 * TODO Configura Botoes
	 */

	void configuraBotes() {
		FrameInicial.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelConta.habilitaEdicao();
				funcaoSobrescrever();

			}
		});
		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelConta.habilitaNovo();
				funcaoSalvar();
			}
		});
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciar();
			}
		});
		FrameInicial.getBtnExcluir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoExcluir();
			}
		});
	}

	/**
	 * TODO Consultar Saldo
	 * 
	 * @param conta
	 * @return
	 */

	public BigDecimal consultaSaldo(Conta conta) {
		System.out.println("ControlaConta.consultaSaldo");
		BigDecimal total = new BigDecimal(0);
		listLanc = daoContaLanc.listCtasReceber(conta);
		for (Lancamento contaLancamento : listLanc) {
			total = total.add(contaLancamento.getValor());
		}
		return total;
	}

	/**
	 * TODO Lista de contas por tipo Receita/Despesa (Nao considera centro de
	 * custo)
	 * 
	 * @param tipoConta
	 * @return
	 */

	public List<Conta> listContTipo(String tipoConta) {
		listContas = new ArrayList<Conta>(daoConta.listContTipo(tipoConta));
		return listContas;
	}

	/**
	 * TODO Lista de contas por centro de Custo
	 * 
	 * @param codiCentroCusto
	 * @return
	 */

	public List<Conta> lisContCCusto(String codiCCusto) {
		listContas = new ArrayList<Conta>(daoConta.listContCCusto(codiCCusto));
		return listContas;
	}

	/**
	 * TODO ComboBox nome de contas do Centro de custo
	 * 
	 * @param codiCCusto
	 * @return
	 */
	public JComboBox<String> cmbContasCCusto(String codiCCusto) {
		JComboBox<String> jcbCCusto = new JComboBox<>();
		for (Conta ct : lisContCCusto(codiCCusto)) {
			jcbCCusto.addItem(ct.getNomeConta());
		}
		return jcbCCusto;
	}

	// TODO Cancelar
	public void funcaoCancelar() {
		System.out.println("ControlaConta.cancelar");
		iniciar();
	}

	// TODO Tabela de entradas da conta
	public JTable tblEntradas(Conta conta) {
		if (conta != null) {
			conta.setListEntradas(daoContaLanc.listCtasReceber(conta));
			tbl02 = new JTable();
			mdlTblEntra = new DefaultTableModel();
			mdlTblEntra = (DefaultTableModel) tbl02.getModel();
			Object colunas[] = {"Conta", "Pedido", "Valor"};
			mdlTblEntra.setColumnIdentifiers(colunas);
			tbl02.setModel(mdlTblEntra);
			tbl02.setShowHorizontalLines(true);
			for (int i = 0; i < conta.getListEntradas().size(); i++) {
				Object linha[] = {conta.getNomeConta(),
						conta.getListEntradas().get(i).getCodiPedido(),
						conta.getListEntradas().get(i).getValor()};
				mdlTblEntra.addRow(linha);
			}

		}
		return tbl02;
	}

	// TODO Tabela de sa�das da conta
	public JTable tblSaidas(Conta conta) {
		conta.setListEntradas(daoContaLanc.listCtasReceber(conta));
		tbl03 = new JTable();
		mdlTblSai = new DefaultTableModel();
		mdlTblSai = (DefaultTableModel) tbl03.getModel();
		Object colunas[] = {"Conta", "Pedido", "Valor"};
		mdlTblSai.setColumnIdentifiers(colunas);
		tbl03.setModel(mdlTblSai);
		tbl03.setShowHorizontalLines(true);
		for (int i = 0; i < c.getListEntradas().size(); i++) {
			Object linha[] = {c.getNomeConta(),
					c.getListEntradas().get(i).getCodiPedido(),
					c.getListEntradas().get(i).getValor()};
			mdlTblSai.addRow(linha);
		}

		return tbl03;
	}

	/**
	 * TODO Carrega as contas no combbox em execucao
	 * 
	 * @param cmb
	 */
	public void carregarContas(JComboBox<String> cmb, String cCusto) {
		cmb.setToolTipText("Selecione a conta");

		listContas = new ArrayList<Conta>(
				daoConta.pesqContasNomeCCusto(cCusto));
		cmb.removeAllItems();
		if (listContas.size() > 0) {
			for (Conta conta : listContas) {
				cmb.addItem(conta.getNomeConta());
			}
		}
	}

	// TODO Combo Box com as contas do centro de custo
	public JComboBox<String> cmbContas(String codiCCusto) {
		cmbContasCCusto = new JComboBox<String>();
		cmbContasCCusto.addItem("Conta");
		cmbContasCCusto.setToolTipText("Selecione a conta para o lan�amento.");
		listContas = daoConta.pesquisarString("");
		if (listContas.size() > 0) {
			for (Conta conta : listContas) {
				cmbContasCCusto.addItem(conta.getNomeConta());
			}
		}

		return cmbContasCCusto;

	}
	// Lista de Contas do Centros de Custo
	public List<Conta> listContCCusto(CentroCusto c) {
		listContas = new ArrayList<Conta>(
				daoConta.listContCCusto(c.getCodiCentroCusto()));
		return listContas;

	}
	// Nome do centro de custo por codigo do centro
	public String nomeCentCustCodi(String codiCentCust) {
		cCust = daoCentCust.buscaCodigo(codiCentCust);
		if (cCust != (null)) {
			return cCust.getNomeCentroCusto();
		} else {
			return "";
		}

	}
	// nome do centro de custo por codigo da conta
	public String nomeCentCustCodiConta(String codiConta) {
		String nomeCCusto = daoConta.nomeCustoCodiConta(codiConta);
		if (cCust != (null)) {
			return nomeCCusto;
		} else {
			return "";
		}

	}
	void erro() {

		JOptionPane.showMessageDialog(null,
				"Problemas: Erro de acesso ao banco", "Erro ao Salvar",
				JOptionPane.ERROR_MESSAGE);

	}

	public List<Conta> pesqNomeArray(String str) {
		return daoConta.pesquisarString(str);
	}

	public String nomeContaCodigo(String codigoConta) {
		String nomeConta = daoConta.nomeCtaCodigo(codigoConta);
		return nomeConta;
	}
	public String codigoContaNome(String nomeConta) {
		String codiConta = daoConta.codigoCtaNome(nomeConta);
		return codiConta;
	}

	public List<Conta> getListCont() {
		return listContas;
	}

	public void setListCont(List<Conta> listCont) {
		this.listContas = listCont;
	}

}