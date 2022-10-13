package online.lucianofelix.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import online.lucianofelix.beans.Servico;
import online.lucianofelix.controle.ControlaListaServicos;
import online.lucianofelix.controle.ControlaServico;
import online.lucianofelix.dao.DAOServico;

public class PainelServico extends JPanel {

	;
	// Labels e text fields
	private static JLabel lblImagem;
	private JLabel lbl01;
	private JLabel lbl02;
	private JLabel lblCodiServ;
	private JLabel lblNomeServ;
	private JLabel lblDescServ;
	private JLabel lblDuracao;
	private JLabel lbl07;
	private JLabel lbl08;
	private JLabel lbl09;
	private JLabel lbl10;

	private static JTextField txtF01;
	private static JTextField txtF02;
	private static JTextField txtFCodiServ;
	private static JTextField txtFNomeServ;
	private static JTextField txtFDescServ;
	private static JTextField txtFDuracao;
	private static JTextField txtF07;
	private static JTextField txtF08;
	private static JTextField txtF09;
	private static JTextField txtF10;

	private static JScrollPane scrImagem;
	private static JScrollPane scrP01;
	private static JScrollPane scrP02;
	private JSplitPane sppImagem;
	private JSplitPane sppPrincipal;
	private JSplitPane sppSuperior;
	private JPanel pnlInferior;
	private JPanel pnlGrid;
	private JTabbedPane tabVisualiza;
	private static JTable tbl01;
	private static JTable tbl02;

	private ActionListener acaoEditar;
	private ActionListener sobrescrever;

	// objetos de controle
	private static ControlaListaServicos controledaLista;
	private static ControlaServico contServ;
	private static DAOServico daoServ;
	private static Servico serv;
	private List<Servico> listServ;
	int tam;
	// TODO Fim do 1 construtor e criação do 2 sobrecarregado

	public PainelServico(String nome) {
		UIManager.put("TextField.font",
				new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font",
				new Font("Times New Roman", Font.BOLD, 12));
		contServ = new ControlaServico();
		daoServ = new DAOServico();

		// TODO Configuração dos Labels e text fields
		lbl01 = new JLabel("Serviço");
		lbl01.setFont(new Font("Times New Roman", Font.BOLD, 28));

		lbl02 = new JLabel("Seq Serviço");
		txtF02 = new JTextField();
		lblCodiServ = new JLabel("Codigo Serviço");
		txtFCodiServ = new JTextField();
		lblNomeServ = new JLabel("Nome Serviço:");
		txtFNomeServ = new JTextField();
		lblDescServ = new JLabel("Desc. Serviço: ");
		txtFDescServ = new JTextField();
		lblDuracao = new JLabel("Duração: ");
		txtFDuracao = new JTextField();
		lbl07 = new JLabel("Preço Hora: ");
		txtF07 = new JTextField();
		lbl08 = new JLabel("Adicional: ");
		txtF08 = new JTextField();

		// Painel Superior

		pnlGrid = new JPanel();
		pnlGrid.setBorder(BorderFactory.createEtchedBorder());
		pnlGrid.setLayout(new GridLayout(8, 2));
		pnlGrid.setBackground(Color.WHITE);

		pnlGrid.add(lbl02);
		pnlGrid.add(txtF02);
		pnlGrid.add(lblCodiServ);
		pnlGrid.add(txtFCodiServ);
		pnlGrid.add(lblNomeServ);
		pnlGrid.add(txtFNomeServ);
		pnlGrid.add(lblDescServ);
		pnlGrid.add(txtFDescServ);
		pnlGrid.add(lblDuracao);
		pnlGrid.add(txtFDuracao);
		pnlGrid.add(lbl07);
		pnlGrid.add(txtF07);
		pnlGrid.add(lbl08);
		pnlGrid.add(txtF08);

		scrImagem = new JScrollPane(lblImagem);
		scrImagem.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrImagem.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sppImagem = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppImagem.add(lbl01);
		sppImagem.add(scrImagem);
		sppImagem.setDividerLocation(50);
		sppImagem.setEnabled(false);
		sppImagem.setBackground(Color.WHITE);
		sppImagem.setForeground(Color.WHITE);
		sppImagem.setDividerSize(3);

		sppSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppSuperior.setDividerLocation(200);
		sppSuperior.setDividerSize(3);
		sppSuperior.setEnabled(false);
		sppSuperior.add(sppImagem);
		sppSuperior.add(pnlGrid);

		// Painel Inferior

		tbl01 = new JTable();
		scrP01 = new JScrollPane();
		scrP01.setViewportView(tbl01);

		tbl02 = new JTable();
		scrP02 = new JScrollPane();
		scrP02.setViewportView(tbl02);

		tabVisualiza = new JTabbedPane();
		tabVisualiza.addTab("Entradas", scrP01);
		tabVisualiza.addTab("Saídas", scrP02);
		tabVisualiza.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				habilitaTabelaMovimentos();
			}
		});

		pnlInferior = new JPanel();
		pnlInferior.setBorder(BorderFactory.createEtchedBorder());
		pnlInferior.setLayout(new GridLayout());
		pnlInferior.setBackground(Color.WHITE);
		pnlInferior.add(tabVisualiza);

		// e finalmente...
		desHabilitaEdicao();
		listServ = daoServ.pesquisaString(nome);
		tam = listServ.size();
		tam--;
		if (tam < 0) {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.setTabela(null);
			FrameInicial.atualizaTela();
		} else {
			controledaLista = new ControlaListaServicos(listServ);
			serv = controledaLista.first();
			carregarCampos(serv);
		}
		sppPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppPrincipal.setDividerSize(3);
		sppPrincipal.setDividerLocation(250);
		sppPrincipal.setEnabled(false);
		sppPrincipal.setBackground(Color.WHITE);
		sppPrincipal.add(sppSuperior);
		sppPrincipal.add(pnlInferior);
		setLayout(new GridLayout());
		setBackground(Color.WHITE);
		add(sppPrincipal);

	} // TODO Fim Construtor + Habilita/Desab./Carrega/Le/Limpa Campos

	public static void habilitaTabelaMovimentos() {
		tbl01 = new JTable();
		tbl02 = new JTable();
		// modeloTabelaEntra = new DefaultTableModel();
		// modeloTabelaEntra = (DefaultTableModel) tbl01.getModel();
		// modeloTabelaSai = new DefaultTableModel();
		// modeloTabelaSai = (DefaultTableModel) tbl02.getModel();
		// contConta.carregarEntradas(conta);
		// Object colunas[] = { "Conta", "Pedido", "Valor" };
		// modeloTabelaEntra.setColumnIdentifiers(colunas);
		// modeloTabelaSai.setColumnIdentifiers(colunas);
		// tbl01.setModel(modeloTabelaEntra);
		// tbl01.setShowHorizontalLines(true);
		// tbl02.setModel(modeloTabelaSai);
		// tbl02.setShowHorizontalLines(true);
		// for (int i = 0; i < conta.getListEntradas().size(); i++) {
		// Object linha[] = { conta.getNomeConta(),
		// conta.getListEntradas().get(i).getCodiPedido(),
		// conta.getListEntradas().get(i).getValor() };
		// modeloTabelaEntra.addRow(linha);
		// }
		// getScrEntradas().setViewportView(tbl01);
		// getScrSaidas().setViewportView(tbl02);
	}

	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		serv = controledaLista.getAt(posicao);
		carregarCampos(serv);
	}

	// TODO Ler Campos.
	public static Servico lerCampos() {
		serv = new Servico();
		serv.setCodiServico(txtF02.getText());
		// serv.setSeqServico(Integer.parseInt(txtFSeqServico.getText()));
		if (txtFCodiServ.getText() != ("") & txtFCodiServ.getText() != null) {
			serv.setDescServico(txtFCodiServ.getText());
		}

		if (txtFNomeServ.getText() != ("") & txtFNomeServ.getText() != null) {
			serv.setNomeServico(txtFNomeServ.getText());
		}

		if (txtFDescServ.getText() != ("") & txtFDescServ.getText() != null) {
			serv.setDescServico(txtFDescServ.getText());
		}

		if (txtF07.getText() != ("") & txtF07.getText() != (null)) {
			serv.setPrecoHora(Float.parseFloat(txtF07.getText()));
		}

		return serv;
	}

	public static void carregarCampos(Servico s) {
		txtF02.setText(String.valueOf(s.getSeqServico()));
		txtFCodiServ.setText(serv.getCodiServico());
		txtFNomeServ.setText(String.valueOf(s.getNomeServico()));
		txtFDescServ.setText(String.valueOf(s.getDescServico()));
		txtFDuracao.setText(String.valueOf(s.getTempoResposta()));
		txtF07.setText(String.valueOf(s.getPrecoHora()));
		txtF08.setText(String.valueOf(s.getPrecoAdicional()));
		carregarImagem(s.getCodiServico());

	}

	public static void carregarImagem(String codiPessoa) {
		lblImagem = new JLabel(new ImageIcon(
				"C:\\SIMPRO\\img\\common\\" + "javinha2" + ".jpg "));
		scrImagem.setViewportView(lblImagem);
	}

	public static void habilitaEdicao() {

		txtFCodiServ.setEditable(true);
		txtFNomeServ.setEditable(true);
		txtFDescServ.setEditable(true);
		txtF07.setEditable(true);
		txtF07.setEditable(true);

	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();
		String codigo = contServ.criaCodigo();
		try {
			daoServ.reservaCodigo(codigo);
		} catch (SQLException e) {
			// TODO Erro ao conectar banco.
			e.printStackTrace();
		}
		txtF02.setFocusable(false);
		txtF02.setEditable(false);
		txtF02.setText(codigo);
		txtFCodiServ.setEditable(false);
		txtFCodiServ.setFocusable(false);
		txtFNomeServ.setEditable(true);
		txtFDescServ.setEditable(true);
		txtFDuracao.setEditable(true);
		txtF07.setEditable(true);
		txtF08.setEditable(true);
		contServ.funcaoCancelarNovo();

	}

	public static void desHabilitaEdicao() {
		txtF02.setEditable(false);
		txtFCodiServ.setEditable(false);
		txtFNomeServ.setEditable(false);
		txtFDescServ.setEditable(false);
		txtF07.setEditable(false);
		txtF07.setEditable(false);
	}

	public static void limparCampos() {
		txtFCodiServ.setText(null);
		txtFNomeServ.setText(null);
		txtFDescServ.setText(null);
		txtF07.setText(null);
		txtF07.setText(null);
	}

	/*
	 * private JRadioButton jrbEditarSim; private JRadioButton jrbEditarNao;
	 * private ButtonGroup grpRadio;
	 */

	public static JTextField getTxtFCodiServico() {
		return txtFCodiServ;
	}

	public static void setTxtFCodiServico(JTextField txtFCodiServico) {
		PainelServico.txtFCodiServ = txtFCodiServico;
	}

	public static JTextField getTxtFNomeServico() {
		return txtFCodiServ;
	}

	public static void setTxtFNomeServico(JTextField txtFNomeServico) {
		PainelServico.txtFNomeServ = txtFNomeServico;
	}

	public static JTextField getTxtFPrecoHora() {
		return txtF07;
	}

	public static void setTxtFPrecoHora(JTextField txtFPrecoHora) {
		PainelServico.txtF07 = txtFPrecoHora;
	}
}
