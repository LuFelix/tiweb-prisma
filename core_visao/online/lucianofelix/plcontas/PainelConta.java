package online.lucianofelix.plcontas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import online.lucianofelix.beans.CentroCusto;
import online.lucianofelix.beans.Conta;
import online.lucianofelix.controle.ControlaCentroCusto;
import online.lucianofelix.controle.ControlaConta;
import online.lucianofelix.controle.ControlaListaConta;
import online.lucianofelix.visao.FrameInicial;
import online.lucianofelix.visao.FrameInicial.ControlaBotoes;

public class PainelConta extends JPanel {

	// Labels e text fields
	private JLabel lblImagem;
	private JLabel lbl01;
	private JLabel lblSeqConta;
	private JLabel lblCodiConta;
	private JLabel lblNomeConta;
	private JLabel lblDescricao;
	private JLabel lblTitular;
	private JLabel lblAgencia;
	private JLabel lblNumConta;
	private JLabel lbl09;
	private JLabel lbl10;
	private JLabel lbl11;

	private static JTextField txtFSeqConta;
	private static JTextField txtFCodiConta;
	private static JTextField txtFNomeConta;
	private static JTextField txtFDescricao;
	private static JTextField txtFTitular;
	private static JTextField txtFAgencia;
	private static JTextField txtFNumConta;
	private static JTextField txtF09;
	private static JTextField txtF10;
	private JScrollPane scrImagem;
	private static JScrollPane scrP01;
	private static JScrollPane scrP02;
	private static JScrollPane scrP03;
	private JSplitPane sppImagem;
	private JSplitPane sppPrincipal;
	private JSplitPane sppSuperior;
	private JPanel pnlInferior;
	private JPanel pnlGrid;
	private JTabbedPane tabVisualiza;

	private static JComboBox<String> cmbTipoConta;
	private static JComboBox<String> cmbCentroCusto;

	private static JTable tbl01;
	private static JTable tbl02;
	private static JTable tbl03;
	private static DefaultTableModel modeloTabelaEntra;
	private static DefaultTableModel modeloTabelaSai;

	// objetos de controle

	private static ControlaListaConta controledaLista;
	private static ControlaConta contConta;
	private static ControlaCentroCusto contCentroCusto;
	private static Conta conta;
	private List<Conta> listConta;
	private static List<CentroCusto> listCentroCusto;
	private static CentroCusto centroCusto;
	// TODO Construtor

	public PainelConta() {
		UIManager.put("TextField.font",
				new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font",
				new Font("Times New Roman", Font.BOLD, 12));
		// Controle
		contConta = new ControlaConta();
		contCentroCusto = new ControlaCentroCusto();
		// Dados

		inciaComponentes();
		desHabilitaEdicao();

	}

	void inciaComponentes() {

		lblImagem = new JLabel();
		lblImagem.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		lblImagem.setAlignmentY(CENTER_ALIGNMENT);
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon("C:\\SIMPRO\\img\\plcontas\\centro_custo.jpg")
						.getImage().getScaledInstance(200, 250,
								Image.SCALE_AREA_AVERAGING));
		lblImagem.setIcon(imageIcon);
		// lblImagem = new JLabel(new ImageIcon(
		// "C:\\SIMPRO\\img\\plcontas\\centro-de-custos-780x450.jpg"));

		scrImagem = new JScrollPane(lblImagem,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		lbl01 = new JLabel("Conta");
		lbl01.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblSeqConta = new JLabel("Seq.:");
		txtFSeqConta = new JTextField();
		lblCodiConta = new JLabel("Código :");
		txtFCodiConta = new JTextField(0);
		lblNomeConta = new JLabel("Nome da Conta: ");
		txtFNomeConta = new JTextField();
		lblDescricao = new JLabel("Descrição: ");
		txtFDescricao = new JTextField();
		lblTitular = new JLabel("Titular: ");
		txtFTitular = new JTextField();
		lblAgencia = new JLabel("Agência: ");
		txtFAgencia = new JTextField();
		lblNumConta = new JLabel("Número da Conta:");
		txtFNumConta = new JTextField();
		lbl09 = new JLabel("Banco:");
		txtF09 = new JTextField();
		lbl10 = new JLabel("Saldo:");
		lbl10.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtF10 = new JTextField();
		txtF10.setHorizontalAlignment(JTextField.RIGHT);
		txtF10.setFont(new Font("Times New Roman", Font.BOLD, 18));

		cmbCentroCusto = contCentroCusto.cmbCentrosCusto();

		cmbTipoConta = new JComboBox<String>();
		cmbTipoConta.addItem("Plano de Contas");
		cmbTipoConta.addItem("Despesa");
		cmbTipoConta.addItem("Receita");
		cmbTipoConta.setSelectedIndex(0);
		cmbTipoConta.setToolTipText("Selecione o plano de contas para a conta");
		cmbTipoConta.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				FrameInicial.getBtnSalvar().grabFocus();

			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		tbl01 = new JTable();
		scrP01 = new JScrollPane();
		scrP01.setViewportView(tbl01);

		tbl02 = new JTable();
		scrP02 = new JScrollPane();
		scrP02.setViewportView(tbl02);

		tbl03 = new JTable();
		scrP03 = new JScrollPane();
		scrP03.setViewportView(tbl03);

		tabVisualiza = new JTabbedPane();
		tabVisualiza.addTab("Dashboard", scrP01);
		tabVisualiza.addTab("Receitas", scrP02);
		tabVisualiza.addTab("Despesas", scrP03);

		tabVisualiza.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				habilitaTabelaMovimentos(lerCampos());
			}
		});
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

		pnlGrid = new JPanel();
		pnlGrid.setBorder(BorderFactory.createEtchedBorder());
		pnlGrid.setLayout(new GridLayout(10, 2));
		pnlGrid.setBackground(Color.WHITE);

		pnlGrid.add(lblSeqConta);
		pnlGrid.add(txtFSeqConta);
		pnlGrid.add(lblCodiConta);
		pnlGrid.add(txtFCodiConta);
		pnlGrid.add(lblNomeConta);
		pnlGrid.add(txtFNomeConta);
		pnlGrid.add(lblDescricao);
		pnlGrid.add(txtFDescricao);
		pnlGrid.add(lblTitular);
		pnlGrid.add(txtFTitular);
		pnlGrid.add(lblAgencia);
		pnlGrid.add(txtFAgencia);
		pnlGrid.add(lblNumConta);
		pnlGrid.add(txtFNumConta);
		pnlGrid.add(lbl09);
		pnlGrid.add(txtF09);
		pnlGrid.add(lbl10);
		pnlGrid.add(txtF10);

		pnlGrid.add(cmbCentroCusto);
		pnlGrid.add(cmbTipoConta);

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
		add(sppPrincipal);

	}

	// TODO Ler Campos.

	public static Conta lerCampos() {
		conta = new Conta();
		if (!txtFSeqConta.getText().equals("")
				& !txtFSeqConta.getText().equals(null)) {
			conta.setSeqConta(Integer.parseInt(txtFSeqConta.getText()));
		}
		if (!txtFCodiConta.getText().equals("")
				& !txtFCodiConta.getText().equals(null)) {
			conta.setCodiConta(txtFCodiConta.getText());
		} else {
			conta.setCodiConta(contConta.criaCodigo());
		}
		if (!txtFNomeConta.getText().equals(null)
				& !txtFNomeConta.getText().equals("")) {
			conta.setNomeConta(txtFNomeConta.getText());
		} else {
			erroLeitura();
			return null;
		}
		conta.setDescConta(txtFDescricao.getText());
		if (!txtFTitular.getText().equals(null)
				& !txtFTitular.getText().equals("")) {
			conta.setTiular(txtFTitular.getText());
		} else {
			System.out.println("Erro titular");
			erroLeitura();
			return null;
		}
		conta.setAgencia(txtFAgencia.getText());
		conta.setConta(txtFNumConta.getText());
		conta.setBanco(txtF09.getText());
		if (!cmbTipoConta.getSelectedItem().toString()
				.equals("Plano de Contas")) {
			conta.setTipoConta(cmbTipoConta.getSelectedItem().toString());
		} else {
			System.out.println("Erro tipo");
			erroLeitura();
			return null;
		}
		if (!cmbCentroCusto.getSelectedItem().toString()
				.equals("Centro de Custo")) {
			centroCusto = contCentroCusto
					.buscaNome(cmbCentroCusto.getSelectedItem().toString());
			conta.setCentroCusto(centroCusto.getCodiCentroCusto());
		} else {
			erroLeitura();
			return null;
		}
		return conta;
	}

	// TODO Carregar campos
	public static void carregarCampos(Conta c) {
		limparCampos();
		desHabilitaEdicao();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		if (!c.equals(null)) {
			txtFSeqConta.setText(String.valueOf(c.getSeqConta()));
			txtFCodiConta.setText(c.getCodiConta());
			txtFNomeConta.setText(c.getNomeConta());
			txtFDescricao.setText(c.getDescConta());
			txtFTitular.setText(c.getTiular());
			txtFAgencia.setText(c.getAgencia());
			txtFNumConta.setText(c.getConta());
			txtF09.setText(c.getBanco());
			txtF10.setText(String.valueOf(contConta.consultaSaldo(c)));
			cmbTipoConta.setSelectedItem(c.getTipoConta());
			cmbCentroCusto.setSelectedItem(
					contConta.nomeCentCustCodi(c.getCentroCusto()));
			habilitaTabelaMovimentos(c);

		}

	}

	private void desabilitaTabela() {
		tbl02 = null;
		tbl03 = null;

		scrP02.setViewportView(tbl02);
		scrP03.setViewportView(tbl03);
	}

	// TODO Habilitar tabela de movimentos
	public static void habilitaTabelaMovimentos(Conta c) {
		tbl02 = contConta.tblEntradas(c);
		tbl03 = new JTable();
		scrP02.setViewportView(tbl02);
		scrP03.setViewportView(tbl03);
	}

	// TODO Habilitar Dashboard
	public static void habilitaDashboard(Conta c) {

	}

	// TODO Desabilita Edição
	public static void desHabilitaEdicao() {

		txtFSeqConta.setEditable(false);
		txtFCodiConta.setEditable(false);
		txtFNomeConta.setEditable(false);
		txtFDescricao.setEditable(false);
		txtFTitular.setEditable(false);
		txtFAgencia.setEditable(false);
		txtFNumConta.setEditable(false);

		cmbTipoConta.setEnabled(false);
		cmbCentroCusto.setEnabled(false);

	}

	// TODO Habilitar Edição
	public static void habilitaEdicao() {

		txtFCodiConta.setEditable(false);
		txtFCodiConta.setFocusable(false);
		txtFNomeConta.setEditable(true);
		txtFNomeConta.grabFocus();
		txtFDescricao.setEditable(true);
		txtFTitular.setEditable(true);
		txtFAgencia.setEditable(true);
		txtFNumConta.setEditable(true);

		cmbTipoConta.setEnabled(true);
		cmbCentroCusto.setEnabled(true);

	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();

		txtFSeqConta.setText("0");
		txtFCodiConta.setEditable(false);
		txtFCodiConta.setFocusable(false);
		txtFCodiConta.setText(contConta.criaCodigo());
		txtFNomeConta.setEditable(true);
		txtFNomeConta.grabFocus();
		txtFDescricao.setEditable(true);
		txtFTitular.setEditable(true);
		txtFAgencia.setEditable(true);
		txtFNumConta.setEditable(true);

		cmbTipoConta.setEnabled(true);
		cmbCentroCusto.setEnabled(true);

	}

	// TODO Limpar campos
	public static void limparCampos() {

		txtFSeqConta.setText(null);
		txtFCodiConta.setText(null);
		txtFNomeConta.setText(null);
		txtFDescricao.setText(null);
		txtFTitular.setText(null);
		txtFAgencia.setText(null);
		txtFNumConta.setText(null);
		cmbTipoConta.setSelectedIndex(0);
		cmbCentroCusto.setSelectedItem(0);
	}
	private static void erroLeitura() {
		JOptionPane.showMessageDialog(null,
				"Problemas: Verifique as informações preenchidas",
				"Erro ao Salvar. Campos com * são necessários",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @return the txtFSeqConta
	 */
	public static JTextField getTxtFSeqConta() {
		return txtFSeqConta;
	}

	/**
	 * @param txtFSeqConta
	 *            the txtFSeqConta to set
	 */
	public static void setTxtFSeqConta(JTextField txtFSeqConta) {
		PainelConta.txtFSeqConta = txtFSeqConta;
	}

	/**
	 * @return the txtFCodiConta
	 */
	public static JTextField getTxtFCodiConta() {
		return txtFCodiConta;
	}

	/**
	 * @param txtFCodiConta
	 *            the txtFCodiConta to set
	 */
	public static void setTxtFCodiConta(JTextField txtFCodiConta) {
		PainelConta.txtFCodiConta = txtFCodiConta;
	}

	/**
	 * @return the txtFNomeConta
	 */
	public static JTextField getTxtFNomeConta() {
		return txtFNomeConta;
	}

	/**
	 * @param txtFNomeConta
	 *            the txtFNomeConta to set
	 */
	public static void setTxtFNomeConta(JTextField txtFNomeConta) {
		PainelConta.txtFNomeConta = txtFNomeConta;
	}

}
