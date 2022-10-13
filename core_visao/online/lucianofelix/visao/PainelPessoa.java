package online.lucianofelix.visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import online.lucianofelix.beans.Pessoa;
import online.lucianofelix.controle.ControlaPessoa;

public class PainelPessoa extends JPanel {
	/**
	 * Objetos de Controle
	 */
	private static Pessoa p;
	private static ControlaPessoa contP;
	/**
	 * Arquitetura da tela
	 */
	JPanel painelPrincipal;
	private JSplitPane jspPrincipal;
	private JSplitPane sppImagem;
	private JSplitPane sppSuperior;
	private JLabel lblTituloTela;
	int result[];
	int result1[];
	/**
	 * Painel que recebe os subpaineis de endereco e dados pertinentes ao
	 * usuario
	 */
	private JPanel pnlContato;
	/**
	 * Painel com os dados pertinentes ao usuário
	 */
	private JPanel painelGrid;
	private static JLabel lblImagem;
	private JLabel lblNome;
	private JLabel lblEmail;
	private JLabel lblCodiPessoa;
	private JLabel lblCpf;
	private JLabel lblSeqPessoa;
	private JLabel lblFones;
	private static JTextField txtfNome;
	private static JTextField txtFSeqPessoa;
	private static JTextField txtFCodiPessoa;
	private static JTextField txtfCpf;
	private static JTextField txtfEmail;
	private static JComboBox<String> cmbTipoPessoa;
	private static JComboBox<String> cmbRelPessoa;
	private static JList<String> listRelPessoa;
	private static JButton btnAdd;
	private static JButton btnDel;
	/**
	 * Configuração do painel de enderecos
	 */
	private JPanel painelEnd;
	private JLabel lblCep;
	private JLabel lblRua;
	private JLabel lblNumero;
	private JLabel lblBairro;
	private JLabel lblCidade;
	private JLabel lblEstado;
	private static JTextField txtFCep;
	private static JTextField txtFRua;
	private static JTextField txtFNumero;
	private static JTextField txtFBairro;
	private static JTextField txtFCidade;
	private static JTextField txtFEstado;
	/**
	 * Painel que recebe as tabelas com resumos do usuario(usermeta)
	 */
	private JPanel painelMovimento;
	private static JTabbedPane tabVisualiza;
	private static JScrollPane scrPOcupacoes;
	private static JScrollPane scrPUltPedidos;
	private static JScrollPane scrPContratos;
	private static JScrollPane scrPRecebimentos;
	private static JScrollPane scrEndereco;
	private static JScrollPane scrRelacao;
	private static JTable tblOcupacoes;
	private static JTable tblUltPedidos;
	private static JTable tblContratos;
	private static JTable tblRecebimentos;
	private static JTable tblEndereco;

	/**
	 * Painel que recebe atalhos para interação com o contato
	 */
	private JPanel pnlTools;
	private JButton btnEnviaMenu;
	private JButton btnEnviaBoleto;

	/**
	 * Painel de controle de telefones e emails
	 *
	 */
	private JPanel pnlFuncContato;
	private JPanel pnlFone;
	private static JList<String> listFone;
	private JButton btnAddFone;
	private JPanel pnlEmail;
	private static JList<String> listEmail;
	private JButton btnAddEmail;
	/**
	 * Painel de dados anexos do usuario e ferramentas
	 */
	private JPanel pnlAnexoUser;
	/**
	 * Construtor de Pessoa
	 * 
	 * @param Pessoa
	 *            p
	 */
	public PainelPessoa(Pessoa p) {
		UIManager.put("TextField.font",
				new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font",
				new Font("Times New Roman", Font.BOLD, 12));
		result = new int[9];
		result1 = new int[10];
		/**
		 * Configuracao da tela
		 */
		lblTituloTela = new JLabel("Contato");
		lblTituloTela.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloTela.setBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 28));
		/**
		 * Painel Grid - Dados Pertinentes ao usuario
		 */
		lblSeqPessoa = new JLabel("Número:");
		txtFSeqPessoa = new JTextField();
		lblCodiPessoa = new JLabel("Código:");
		txtFCodiPessoa = new JTextField();
		contP = new ControlaPessoa();
		lblNome = new JLabel("Nome:");
		txtfNome = new JTextField();
		txtfNome.setCaretPosition(0);
		txtfNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpf = new JLabel("CPF/CNPJ:");
		txtfCpf = new JTextField();
		txtfCpf.setColumns(15);
		lblEmail = new JLabel("E-mail:");
		txtfEmail = new JTextField();
		txtfEmail.setHorizontalAlignment(SwingConstants.LEFT);
		cmbTipoPessoa = new JComboBox<String>();
		cmbTipoPessoa.addItem("Pessoa F/J");
		cmbTipoPessoa.addItem("Jurídica");
		cmbTipoPessoa.addItem("Física");
		cmbRelPessoa = contP.carregarGrupos();
		cmbRelPessoa.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				FrameInicial.getBtnSalvar().grabFocus();
			}
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		listRelPessoa = new JList<String>();
		listRelPessoa.add(lblSeqPessoa, "TESTE", 1);
		btnAdd = new JButton("Adiciona");
		btnDel = new JButton("Remove");
		lblImagem = new JLabel();
		lblImagem.setBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		painelGrid = new JPanel();
		painelGrid.setPreferredSize(getMinimumSize());
		painelGrid.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(Color.LIGHT_GRAY, 2), "Dados Pessoais",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP));
		painelGrid.setLayout(new GridLayout(7, 2));
		painelGrid.setBackground(Color.WHITE);
		painelGrid.add(lblSeqPessoa);
		painelGrid.add(txtFSeqPessoa);
		painelGrid.add(lblCodiPessoa);
		painelGrid.add(txtFCodiPessoa);
		painelGrid.add(lblNome);
		painelGrid.add(txtfNome);
		painelGrid.add(lblCpf);
		painelGrid.add(txtfCpf);
		painelGrid.add(lblEmail);
		painelGrid.add(txtfEmail);
		painelGrid.add(cmbTipoPessoa);
		painelGrid.add(cmbRelPessoa);
		painelGrid.add(listRelPessoa);
		painelGrid.add(btnAdd);
		// painelGrid.add(btnDel);

		/**
		 * Painel Endereco
		 */

		lblCep = new JLabel("CEP:");
		txtFCep = new JTextField();
		txtFCep.setCaretPosition(0);
		txtFCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblRua = new JLabel("Rua:");
		txtFRua = new JTextField();
		txtFRua.setCaretPosition(0);
		txtFRua.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumero = new JLabel("Número:");
		txtFNumero = new JTextField();
		txtFNumero.setCaretPosition(0);
		txtFNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblBairro = new JLabel("Bairro:");
		txtFBairro = new JTextField();
		txtFBairro.setCaretPosition(0);
		txtFBairro.setHorizontalAlignment(SwingConstants.LEFT);
		lblCidade = new JLabel("Cidade:");
		txtFCidade = new JTextField();
		txtFCidade.setCaretPosition(0);
		txtFCidade.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstado = new JLabel("Estado:");
		txtFEstado = new JTextField();
		txtFEstado.setCaretPosition(0);
		txtFEstado.setHorizontalAlignment(SwingConstants.LEFT);
		painelEnd = new JPanel();
		painelEnd.setPreferredSize(getMinimumSize());
		painelEnd.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(Color.LIGHT_GRAY, 2),
				"Endereço Padrão de Entrega", TitledBorder.LEFT,
				TitledBorder.ABOVE_TOP));
		painelEnd.setLayout(new GridLayout(6, 2));
		painelEnd.setBackground(Color.WHITE);
		painelEnd.add(lblCep);
		painelEnd.add(txtFCep);
		painelEnd.add(lblRua);
		painelEnd.add(txtFRua);
		painelEnd.add(lblNumero);
		painelEnd.add(txtFNumero);
		painelEnd.add(lblBairro);
		painelEnd.add(txtFBairro);
		painelEnd.add(lblCidade);
		painelEnd.add(txtFCidade);
		painelEnd.add(lblEstado);
		painelEnd.add(txtFEstado);

		/**
		 * Painel de telefones
		 */

		pnlFuncContato = new JPanel(new GridLayout(2, 1));
		pnlFuncContato.setBackground(Color.WHITE);
		pnlFone = new JPanel();
		pnlEmail = new JPanel();
		pnlFone.setSize(getMinimumSize());

		btnAddFone = new JButton("Add Telefone");
		btnAddFone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contP.addFone();

			}
		});
		btnAddEmail = new JButton("Add E-mail");
		btnAddEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contP.addEmail();

			}
		});

		pnlFone.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(Color.LIGHT_GRAY, 2), "Telefones",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP));
		pnlFone.setLayout(new BorderLayout());
		pnlFone.setBackground(Color.WHITE);
		String arrayFone[] = {"82 9 98217 9000", "82 9 9323 1684",
				"82 9 9323 1684"};
		String arrayEmails[] = {"correildolulu@gmail.com",
				"luciano@tiweb.app.br", "contato@tiweb.app.br"};

		pnlEmail.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(Color.LIGHT_GRAY, 2), "E-mails",
				TitledBorder.LEFT, TitledBorder.ABOVE_TOP));
		pnlEmail.setBackground(Color.WHITE);
		pnlEmail.setLayout(new BorderLayout());

		listFone = new JList<String>(arrayFone);
		listFone.setName("Telefones");
		listFone.setVisibleRowCount(3);
		listEmail = new JList<String>(arrayEmails);
		listEmail.setName("E-mails");
		listEmail.setVisibleRowCount(3);

		pnlFone.add(btnAddFone, BorderLayout.BEFORE_FIRST_LINE);
		pnlFone.add(listFone, BorderLayout.CENTER);
		pnlEmail.add(btnAddEmail, BorderLayout.BEFORE_FIRST_LINE);
		pnlEmail.add(listEmail, BorderLayout.CENTER);
		pnlFuncContato.add(pnlFone);
		pnlFuncContato.add(pnlEmail);

		/**
		 * Painel de ferramentas de acesso rapido
		 * 
		 */
		btnEnviaMenu = new JButton("Envia Menu");
		btnEnviaBoleto = new JButton("Envia Boleto");

		pnlTools = new JPanel(new GridLayout(1, 2));
		pnlTools.setBackground(Color.WHITE);
		pnlTools.setBorder(
				BorderFactory.createTitledBorder(getBorder(), "Acesso Rápido"));
		pnlTools.add(btnEnviaMenu);
		pnlTools.add(btnEnviaBoleto);

		JPanel pnlAcesso = new JPanel(new GridLayout(2, 1));
		pnlAcesso.add(lblImagem);
		pnlAcesso.add(pnlFuncContato);
		/**
		 * Painel Movimento
		 */
		tblUltPedidos = new JTable();
		scrPUltPedidos = new JScrollPane();
		setTblRecebimentos(new JTable());
		scrPRecebimentos = new JScrollPane();
		setTblEndereco(new JTable());
		scrEndereco = new JScrollPane();
		tblOcupacoes = new JTable();
		scrPOcupacoes = new JScrollPane();
		setTblContratos(new JTable());
		scrPContratos = new JScrollPane();
		tabVisualiza = new JTabbedPane();
		tabVisualiza.add("Últimos Pedidos", scrPUltPedidos);
		tabVisualiza.add("Recebimentos", scrPRecebimentos);
		tabVisualiza.add("Endereços", scrEndereco);
		tabVisualiza.add("Conta Fiado", scrPOcupacoes);
		// tabVisualiza.add("Contratos", scrPContratos);

		painelMovimento = new JPanel();
		painelMovimento.setBorder(BorderFactory.createEtchedBorder());
		painelMovimento.setLayout(new GridLayout());
		painelMovimento.setBackground(Color.WHITE);
		painelMovimento.add(tabVisualiza);

		sppImagem = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppImagem.add(lblTituloTela);
		// sppImagem.add(lblImagem);
		sppImagem.add(pnlAcesso);
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

		pnlContato = new JPanel(new GridLayout(2, 1));
		pnlAnexoUser = new JPanel(new BorderLayout());
		pnlAnexoUser.add(painelEnd, BorderLayout.CENTER);
		pnlAnexoUser.add(pnlTools, BorderLayout.SOUTH);
		pnlContato.add(painelGrid);
		pnlContato.add(pnlAnexoUser);

		sppSuperior.add(pnlContato);

		jspPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jspPrincipal.setDividerSize(3);
		jspPrincipal.setDividerLocation(500);
		jspPrincipal.setEnabled(false);
		jspPrincipal.setBackground(Color.WHITE);
		jspPrincipal.add(sppSuperior);
		jspPrincipal.add(painelMovimento);
		setLayout(new GridLayout());
		setBackground(Color.WHITE);
		add(jspPrincipal);
		carregarCampos(p);
		desHabilitaEdicao();
		FrameInicial.getTxtfPesquisa().grabFocus();
	}

	public void desHabilitaEdicao() {
		cmbRelPessoa.setEnabled(false);
		cmbTipoPessoa.setEnabled(false);
		txtFSeqPessoa.setEditable(false);
		txtFCodiPessoa.setEditable(false);
		txtfNome.setEditable(false);
		txtfCpf.setEditable(false);
		txtfEmail.setEditable(false);
		btnAdd.setEnabled(false);
		btnDel.setEnabled(false);

	}

	// TODO Habilita Edição
	public static void habilitaEdicao() {
		cmbRelPessoa.setEnabled(true);
		cmbTipoPessoa.setEnabled(true);
		txtfNome.setEditable(true);
		txtfNome.grabFocus();
		txtfCpf.setEditable(true);
		txtfEmail.setEditable(true);
		btnAdd.setEnabled(true);
		btnDel.setEnabled(true);
		// habilitaTbl(getTblOcupacoes());
	}
	static void habilitaTbl(JTable tabela) {
		// tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
		// tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		// tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
		// tabela.getColumnModel().getColumn(3).setPreferredWidth(30);
		tabela.setShowGrid(true);
		tabela.setRowSelectionAllowed(true);
		tabela.setCellSelectionEnabled(true);
		tabela.setColumnSelectionAllowed(true);
		tabela.setEnabled(true);

	}
	static void desabilitaTbl(JTable tabela) {
		// tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
		// tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		// tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
		// tabela.getColumnModel().getColumn(3).setPreferredWidth(30);
		tabela.setShowGrid(true);
		tabela.setRowSelectionAllowed(false);
		tabela.setCellSelectionEnabled(false);
		tabela.setColumnSelectionAllowed(false);
		tabela.setEnabled(false);
	}
	// TODO habilitar novo
	public static void habilitaNovo() {
		limparCampos();
		if (txtFCodiPessoa.getText().equals("")
				|| txtFCodiPessoa.getText().equals(null)) {
			txtFCodiPessoa.setText(contP.criaCodiUsuario());
		}
		txtfNome.setEditable(true);
		txtfNome.grabFocus();
		txtfCpf.setEditable(true);
		txtfEmail.setEditable(true);
		cmbTipoPessoa.setEnabled(true);
		cmbRelPessoa.setEnabled(true);
		btnAdd.setEnabled(true);
		btnDel.setEnabled(true);
	}

	// TODO ler campos
	public static Pessoa lerCampos() {
		p = new Pessoa();
		if (txtFSeqPessoa.getText().equals("")) {
			p.setSeqUsuario(0);
		} else {
			p.setSeqUsuario(Integer.parseInt(txtFSeqPessoa.getText()));
		}
		if (cmbTipoPessoa.getSelectedItem() != null) {
			p.setTipoPessoa(cmbTipoPessoa.getSelectedItem().toString());
		}
		if (cmbRelPessoa.getSelectedItem() != null) {
			p.setRelacao(contP.carregarCodigoGrupoNome(
					cmbRelPessoa.getSelectedItem().toString()));
		} else {
			p.setRelacao("Sem Categoria");
		}
		p.setCodiPessoa(txtFCodiPessoa.getText());
		p.setNome(txtfNome.getText());
		p.setCpf(txtfCpf.getText());
		p.setEmail(txtfEmail.getText());
		return p;
	}

	public static void limparCampos() {
		cmbRelPessoa.setSelectedIndex(0);
		cmbTipoPessoa.setSelectedIndex(0);
		txtFCodiPessoa.setText(null);
		txtfNome.setText(null);
		txtfCpf.setText(null);
		txtfEmail.setText(null);
		lblImagem.setIcon(null);
		setTblOcupacoes(null);
		setTblUltPedidos(null);
		setTblEndereco(null);
		setTblRecebimentos(null);
		setTblContratos(null);
		scrPOcupacoes.setViewportView(getTblOcupacoes());
		scrPUltPedidos.setViewportView(getTblUltPedidos());
		scrPContratos.setViewportView(getTblContratos());
		scrEndereco.setViewportView(getTblEndereco());
		scrPRecebimentos.setViewportView(getTblRecebimentos());
	}

	public static void carregarCampos(Pessoa p) {
		if (p != null) {
			cmbTipoPessoa.setSelectedItem(p.getTipoPessoa());
			if (p.getRelacao() != null) {
				cmbRelPessoa.setSelectedItem(
						contP.carregarNomeGrupoCodigo(p.getRelacao()));
			} else {
				cmbRelPessoa.setSelectedIndex(0);
			}
			txtFSeqPessoa.setText(String.valueOf(p.getSeqUsuario()));
			txtFCodiPessoa.setText(p.getCodiPessoa());
			txtfNome.setText(p.getNome());
			txtfCpf.setText(String.valueOf(p.getCpf()));
			txtfEmail.setText(p.getEmail());
			carregarImagem(p.getCodiPessoa());
			// setTblOcupacoes(contP.carregaProfissoes(p));
			// desabilitaTbl(getTblOcupacoes());
			// scrPOcupacoes.setViewportView(getTblOcupacoes());
			setTblUltPedidos(contP.carregaUltPed(p));
			desabilitaTbl(getTblUltPedidos());
			scrPUltPedidos.setViewportView(getTblUltPedidos());

		} else {

		}
	}
	void limpaBtnDet() {
		for (ActionListener act : btnAdd.getActionListeners()) {
			btnAdd.removeActionListener(act);
		}
		for (ActionListener act : btnDel.getActionListeners()) {
			btnDel.removeActionListener(act);
		}

	}

	void funcaoOcupacao() {
		limpaBtnDet();
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contP.adicionaOcup(lerCampos());
				habilitaTbl(getTblOcupacoes());

			}
		});

		btnDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contP.apagaOcup();

			}
		});

	}

	public static void carregarImagem(String codiPessoa) {
		lblImagem.setIcon(new ImageIcon(
				"C:\\SIMPRO\\img\\common\\" + "javinha4" + ".jpg "));
	}

	public static JTextField getTxtfNome() {
		return txtfNome;
	}

	public static void setTxtfNome(JTextField txtfNome) {
		PainelPessoa.txtfNome = txtfNome;
	}

	// TODO Validar CPF
	// private boolean validarcpf(String cpf) {
	// int soma = 0;
	// int soma1 = 0;
	// int resto = 0;
	// int resto1 = 0;
	// int dv = 0;
	// int dv1 = 0;
	// int mult[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	// for (int i = 0; i < 9; i++) {
	// result[i] = mult[i] * Integer.parseInt(cpf.substring(i, i + 1));
	// soma = soma + result[i];
	// System.out.println(result[i] + "soma:" + soma);
	//
	// }
	// // Primeiro digito verificador efetuado.
	// resto = soma % 11;
	//
	// if (resto < 2) {
	// dv = 0;
	// System.out.println(dv);
	// } else {
	// dv = 11 - resto;
	// System.out.println(dv);
	// }
	// // Segundo digito verificador
	// int mult1[] = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	// for (int i = 0; i < 10; i++) {
	// result1[i] = mult1[i] * Integer.parseInt(cpf.substring(i, i + 1));
	// soma1 = soma1 + result1[i];
	// System.out.println(result1[i] + "soma:" + soma1);
	//
	// }
	// resto1 = soma1 % 11;
	// if (resto1 < 2) {
	// dv1 = 0;
	// System.out.println(dv1);
	//
	// } else {
	// dv1 = 11 - resto1;
	// System.out.println(dv1);
	// }
	//
	// if (dv == Integer.parseInt(cpf.substring(9, 10)) & dv1 ==
	// Integer.parseInt(cpf.substring(10, 11))) {
	// return true;
	// } else {
	// JOptionPane.showMessageDialog(null, "Favor verificar CPF ", "CPF
	// Inválido!", JOptionPane.ERROR_MESSAGE);
	// return false;
	// }
	//
	// return true;
	// }

	/**
	 * @return the tblEndereco
	 */
	public static JTable getTblEndereco() {
		return tblEndereco;
	}
	/**
	 * @param tblEndereco
	 *            the tblEndereco to set
	 */
	public static void setTblEndereco(JTable tblEndereco) {
		PainelPessoa.tblEndereco = tblEndereco;
	}

	/**
	 * @return the tblOcupacoes
	 */
	public static JTable getTblOcupacoes() {
		return tblOcupacoes;
	}

	/**
	 * @param tblOcupacoes
	 *            the tblOcupacoes to set
	 */
	public static void setTblOcupacoes(JTable tblOcupacoes) {
		PainelPessoa.tblOcupacoes = tblOcupacoes;
	}

	/**
	 * @return the tblUltPedidos
	 */
	public static JTable getTblUltPedidos() {
		return tblUltPedidos;
	}

	/**
	 * @param tblUltPedidos
	 *            the tblUltPedidos to set
	 */
	public static void setTblUltPedidos(JTable tblUltPedidos) {
		PainelPessoa.tblUltPedidos = tblUltPedidos;
	}

	/**
	 * @return the tblContratos
	 */
	public static JTable getTblContratos() {
		return tblContratos;
	}

	/**
	 * @param tblContratos
	 *            the tblContratos to set
	 */
	public static void setTblContratos(JTable tblContratos) {
		PainelPessoa.tblContratos = tblContratos;
	}

	/**
	 * @return the tblRecebimentos
	 */
	public static JTable getTblRecebimentos() {
		return tblRecebimentos;
	}

	/**
	 * @param tblRecebimentos
	 *            the tblRecebimentos to set
	 */
	public static void setTblRecebimentos(JTable tblRecebimentos) {
		PainelPessoa.tblRecebimentos = tblRecebimentos;
	}

	/**
	 * @return the scrRelacao
	 */
	public static JScrollPane getScrRelacao() {
		return scrRelacao;
	}

	/**
	 * @param scrRelacao
	 *            the scrRelacao to set
	 */
	public static void setScrRelacao(JScrollPane scrRelacao) {
		PainelPessoa.scrRelacao = scrRelacao;
	}

	/**
	 * @return the listFone
	 */
	public static JList<String> getListFone() {
		return listFone;
	}

	/**
	 * @param listFone
	 *            the listFone to set
	 */
	public static void setListFone(JList<String> listFone) {
		PainelPessoa.listFone = listFone;
	}

	/**
	 * @return the listRelPessoa
	 */
	public static JList<String> getListRelPessoa() {
		return listRelPessoa;
	}

	/**
	 * @param listRelPessoa
	 *            the listRelPessoa to set
	 */
	public static void setListRelPessoa(JList<String> listRelPessoa) {
		PainelPessoa.listRelPessoa = listRelPessoa;
	}

	/**
	 * @return the lblFones
	 */
	public JLabel getLblFones() {
		return lblFones;
	}

	/**
	 * @param lblFones
	 *            the lblFones to set
	 */
	public void setLblFones(JLabel lblFones) {
		this.lblFones = lblFones;
	}

	/**
	 * @return the pnlTools
	 */
	public JPanel getPnlTools() {
		return pnlTools;
	}

	/**
	 * @param pnlTools
	 *            the pnlTools to set
	 */
	public void setPnlTools(JPanel pnlTools) {
		this.pnlTools = pnlTools;
	}
}
