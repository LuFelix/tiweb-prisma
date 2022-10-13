package online.lucianofelix.fuse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.StyledDocument;

import online.lucianofelix.beans.fuse.Ativo;
import online.lucianofelix.beans.fuse.Operacao;
import online.lucianofelix.dao.DAOOperacao;
import online.lucianofelix.visao.FrameInicial;

public class PainelOperacao extends JPanel {

	private JPanel jPanelAtivoSelecionado;
	private JPanel jPanelTituloTela;
	private JPanel jPanelPrecoAtual;
	private JPanel jPanelGrid;
	private JPanel jPanelSuperior;
	private JPanel jPanelBoleta;
	private static JPanel jPanelInforma;
	private JSplitPane jSpPanelPrincipal;
	private JSplitPane jSpPanelInferior;
	private static JScrollPane jScrollPaneInforma;

	private JLabel lblTituloTela;
	private static JLabel lblAtivoSelecionado;
	private static JLabel lblPrecoAtual;
	private static JLabel lblVariacaoAtual;

	// Labels e text fields
	private JLabel lblSeqOperacao;
	private JLabel lblCodiOperacao;
	private JLabel lblCodiAtivo;
	private JLabel lblValorPapel;
	private JLabel lblCorretagem;
	private JLabel lblTipoOper;
	private JLabel lblCodiOrd;
	private JLabel lblQtdAcoes;
	private JLabel lblStopLoss;
	private JLabel lblDataHoraExec;
	private JLabel lblTotal;
	private JLabel lblTipoSetup;
	private JLabel lblFiltroIndicador;

	private static JTextField txtFCodiOperacao;
	private static JTextField txtFSeqOperacao;
	private static JTextField txtFCodiAtivo;
	private static JTextField txtFValorPapel;
	private static JTextField txtFCorretagem;
	private static JTextField txtFTipoOper;
	private static JTextField txtFCodiOrd;
	private static JTextField txtFQtdAcoes;
	private static JTextField txtFDataHoraExec;
	private static JTextField txtFTotal;
	// Adicionar o campo lucro desejado

	private JTextArea jTextAreaInforma;
	private static JTextPane jTextPaneInforma;
	private static StyledDocument doc;
	private static JButton btnProximo;
	private static JButton btnAnterior;
	private static JButton jBtnExecuta;

	private JRadioButton jrbEditarSim;
	private JRadioButton jrbEditarNao;
	private ButtonGroup grpRadio;
	private ActionListener acaoEditar;
	private ActionListener sobrescrever;

	// objetos de controle
	private static ControlaListaOperacoes controledaLista;
	private static ControlaPainelInformativo contInform;
	private static ControlaOperacao cOp;
	private static DAOOperacao daoOp;
	private static Operacao op;
	private List<Operacao> listOp;
	int tam;

	public PainelOperacao(String nome) {
		UIManager.put("TextField.font",
				new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font",
				new Font("Times New Roman", Font.BOLD, 12));

		cOp = new ControlaOperacao();
		contInform = new ControlaPainelInformativo();
		daoOp = new DAOOperacao();

		setLayout(new GridLayout(1, 1));

		lblTituloTela = new JLabel("ORDEM Swing Trade");
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblTituloTela.setForeground(Color.BLUE);
		lblTituloTela.setHorizontalAlignment(SwingConstants.CENTER);

		jPanelTituloTela = new JPanel(new BorderLayout());
		jPanelTituloTela.setBackground(Color.WHITE);
		jPanelTituloTela.add(lblTituloTela, BorderLayout.CENTER);

		lblPrecoAtual = new JLabel();
		lblPrecoAtual.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecoAtual.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblVariacaoAtual = new JLabel();
		lblVariacaoAtual.setHorizontalAlignment(SwingConstants.CENTER);
		lblVariacaoAtual.setFont(new Font("Times New Roman", Font.BOLD, 24));
		jPanelPrecoAtual = new JPanel(new GridLayout(1, 2));
		jPanelPrecoAtual.setBackground(Color.WHITE);
		jPanelPrecoAtual.add(lblPrecoAtual);
		jPanelPrecoAtual.add(lblVariacaoAtual);

		lblAtivoSelecionado = new JLabel();
		lblAtivoSelecionado.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtivoSelecionado.setFont(new Font("Arial Black", Font.BOLD, 24));
		lblAtivoSelecionado.setForeground(Color.BLUE);

		jPanelAtivoSelecionado = new JPanel(new GridLayout(1, 2));
		jPanelAtivoSelecionado.setBackground(Color.WHITE);
		jPanelAtivoSelecionado.add(lblAtivoSelecionado);
		jPanelAtivoSelecionado.add(jPanelPrecoAtual);

		jPanelSuperior = new JPanel(new BorderLayout());
		jPanelSuperior.add(jPanelTituloTela, BorderLayout.NORTH);
		jPanelSuperior.add(jPanelAtivoSelecionado, BorderLayout.CENTER);

		// TODO Configuração dos Labels e text fields

		lblSeqOperacao = new JLabel("Seq. da Operação:");
		lblSeqOperacao.setHorizontalAlignment(SwingConstants.LEADING);
		lblSeqOperacao.setFont(new Font("Arial", Font.BOLD, 16));
		txtFSeqOperacao = new JTextField();
		txtFSeqOperacao.setFont(new Font("Times New Roman", Font.BOLD, 24));

		lblCodiOperacao = new JLabel("Cod. Operacão:");
		lblCodiOperacao.setHorizontalAlignment(SwingConstants.LEADING);
		lblCodiOperacao.setFont(new Font("Arial", Font.BOLD, 16));
		txtFCodiOperacao = new JTextField();
		txtFCodiOperacao.setFont(new Font("Arial", Font.BOLD, 24));

		lblCodiAtivo = new JLabel("Ativo:");
		txtFCodiAtivo = new JTextField(0);

		lblValorPapel = new JLabel("Valor Papel: ");
		setTxtFValorPapel(new JTextField());

		lblCorretagem = new JLabel("Corretagem: ");
		setTxtFCorretagem(new JTextField());

		lblTipoOper = new JLabel("Tipo Operação ");
		lblTipoOper.setHorizontalAlignment(SwingConstants.LEADING);
		lblTipoOper.setFont(new Font("Arial", Font.BOLD, 18));
		txtFTipoOper = new JTextField();
		txtFTipoOper.setFont(new Font("Arial", Font.BOLD, 24));

		lblCodiOrd = new JLabel("Cod. Ordem ");
		lblCodiOrd.setHorizontalAlignment(SwingConstants.LEADING);
		lblCodiOrd.setFont(new Font("Arial", Font.BOLD, 18));
		txtFCodiOrd = new JTextField();
		txtFCodiOrd.setFont(new Font("Arial", Font.BOLD, 24));

		lblQtdAcoes = new JLabel("Qtd Ações: ");
		lblQtdAcoes.setHorizontalAlignment(SwingConstants.LEADING);
		lblQtdAcoes.setFont(new Font("Arial", Font.BOLD, 18));
		txtFQtdAcoes = new JTextField();
		txtFQtdAcoes.setFont(new Font("Arial", Font.BOLD, 24));

		lblDataHoraExec = new JLabel("Data / Hora:");
		lblDataHoraExec.setHorizontalAlignment(SwingConstants.LEADING);
		lblDataHoraExec.setFont(new Font("Arial", Font.BOLD, 18));
		txtFDataHoraExec = new JTextField();
		txtFDataHoraExec.setFont(new Font("Arial", Font.BOLD, 24));

		lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.LEADING);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
		txtFTotal = new JTextField();
		txtFTotal.setFont(new Font("Arial", Font.BOLD, 24));
		txtFTotal.setForeground(Color.RED);

		lblStopLoss = new JLabel("Stop Loss ");

		btnProximo = new JButton("Próximo");
		btnProximo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(
						"Estava em: " + controledaLista.getCurrentPosition());
				if (controledaLista.getCurrentPosition() < tam) {
					op = controledaLista.next();
					cOp.posicionarTabela(controledaLista.getCurrentPosition());
				} else {
					controledaLista.setCurrentPosition(0);
					op = controledaLista.getAt(0);
					cOp.posicionarTabela(controledaLista.getCurrentPosition());
				}
				carregarCampos(op);
			}
		});

		btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(
						"Estava em: " + controledaLista.getCurrentPosition());
				if (controledaLista.getCurrentPosition() == 0) {
					controledaLista.setCurrentPosition(tam);
					op = controledaLista.getAt(tam);
					cOp.posicionarTabela(controledaLista.getCurrentPosition());
				} else {
					op = controledaLista.previous();
					cOp.posicionarTabela(controledaLista.getCurrentPosition());
				}
				System.out.println(
						"Foi para: " + controledaLista.getCurrentPosition());
				carregarCampos(op);
				// FrameECF.atualizaTela(op);

			}
		});

		jPanelGrid = new JPanel(new GridLayout(8, 2));
		jPanelGrid.setBackground(Color.WHITE);
		jPanelGrid.add(lblSeqOperacao);
		jPanelGrid.add(txtFSeqOperacao);
		jPanelGrid.add(lblCodiOperacao);
		jPanelGrid.add(txtFCodiOperacao);
		jPanelGrid.add(lblCodiOrd);
		jPanelGrid.add(txtFCodiOrd);
		jPanelGrid.add(lblTipoOper);
		jPanelGrid.add(txtFTipoOper);
		jPanelGrid.add(lblQtdAcoes);
		jPanelGrid.add(txtFQtdAcoes);
		jPanelGrid.add(lblDataHoraExec);
		jPanelGrid.add(txtFDataHoraExec);
		jPanelGrid.add(lblTotal);
		jPanelGrid.add(txtFTotal);
		jPanelGrid.add(btnProximo);
		jPanelGrid.add(btnAnterior);

		jBtnExecuta = new JButton("Executa");
		jBtnExecuta.setFont(new Font("Arial", Font.BOLD, 18));
		jBtnExecuta.setForeground(Color.WHITE);
		jBtnExecuta.setBackground(Color.BLUE);
		jBtnExecuta.addMouseListener(new MouseListener() {

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
				jBtnExecuta.setBackground(Color.BLUE);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				jBtnExecuta.setBackground(Color.RED);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		jPanelBoleta = new JPanel(new BorderLayout());
		jPanelBoleta.add(jPanelGrid, BorderLayout.CENTER);
		jPanelBoleta.add(jBtnExecuta, BorderLayout.PAGE_END);

		// doc = jTextPaneInforma.getStyledDocument();
		// jTextPaneInforma.setDocument(doc);

		// StyledDocument doc = jTextPaneInforma.getStyledDocument();
		// SimpleAttributeSet center = new SimpleAttributeSet();
		// StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		// doc.setParagraphAttributes(0, doc.getLength(), center, false);

		jTextAreaInforma = new JTextArea();
		jTextAreaInforma.setText(
				"Aqui estará painel de informação com uma aba disponível para visualizar o gráfico");

		jScrollPaneInforma = new JScrollPane(jPanelInforma);
		jScrollPaneInforma.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jScrollPaneInforma.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPaneInforma.setPreferredSize(getMinimumSize());

		jSpPanelInferior = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
		jSpPanelInferior.setBackground(Color.WHITE);
		jSpPanelInferior.setDividerLocation(0.9);
		jSpPanelInferior.add(jPanelBoleta);
		jSpPanelInferior.add(jScrollPaneInforma);

		jSpPanelPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
		jSpPanelPrincipal.setDividerLocation(0.5);
		jSpPanelPrincipal.add(jPanelSuperior);
		jSpPanelPrincipal.add(jSpPanelInferior);

		// e finalmente...

		desHabilitaEdicao();
		listOp = daoOp.pesquisaString(nome);
		tam = listOp.size();
		tam--;
		if (tam < 0) {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.getScrVisualiza()
					.setViewportView(FrameInicial.getPainelVisualiza());
		} else {
			controledaLista = new ControlaListaOperacoes(listOp);
			System.out.println("PaineOperacao.fimConstrutor");
			op = controledaLista.first();
			System.out.println(op.getCodiAtivo());
			carregarCampos(op);
		}
		atualizarNoticias();
		setBackground(Color.WHITE);
		add(jSpPanelPrincipal);

	}
	// TODO Fim constr. inicio + Habilita/Desab./Carrega/Le/Limpa Campos

	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		op = controledaLista.getAt(posicao);
		carregarCampos(op);
	}

	// TODO Ler Campos.
	public static Operacao lerCampos() {
		op = new Operacao();
		op.setCodiOperacao(txtFCodiOperacao.getText());
		op.setCodiAtivo(txtFCodiAtivo.getText());
		op.setSeqOperacao(Integer.parseInt(txtFSeqOperacao.getText()));
		if (!getTxtFValorPapel().getText().equals(null)
				& !getTxtFValorPapel().getText().equals("")
				& !txtFQtdAcoes.getText().equals(null)
				& !txtFQtdAcoes.getText().equals("")) {
			op.setValorPapel(Float.parseFloat(getTxtFValorPapel().getText()));
			op.setQtdPapeis(Integer.parseInt(txtFQtdAcoes.getText()));
		} else {
			JOptionPane.showMessageDialog(null,
					"Problemas: Verifique as informações preenchidas",
					"Erro ao Salvar. Campos com * são necessários",
					JOptionPane.ERROR_MESSAGE);
		}
		op.setTipoOp(txtFTipoOper.getText());
		op.setCodiOrdem(txtFCodiOrd.getText());
		return op;
	}

	// TODO Carregar Campos
	public static void carregarAtivo(Operacao op) {

		lblAtivoSelecionado.setText(op.getCodiAtivo());
		lblPrecoAtual.setText(String.valueOf(op.getValorPapel()));
		lblVariacaoAtual.setText(op.getTipoOp());
	}
	public static void carregarCampos(Operacao op) {
		System.out.println("PainelOperação.carregarCampos");
		if (!op.equals(null)) {
			txtFSeqOperacao.setText(String.valueOf(op.getSeqOperacao()));
		}
		txtFCodiOperacao.setText(op.getCodiOrdem());
		txtFCodiAtivo.setText(op.getCodiAtivo());
		getTxtFValorPapel().setText(String.valueOf(op.getValorPapel()));
		getTxtFCorretagem().setText(String.valueOf(op.getCorretagem()));
		txtFTipoOper.setText(op.getTipoOp());
		txtFDataHoraExec.setText(String.valueOf(op.getDataHoraExec()));
		txtFCodiOrd.setText(op.getCodiOrdem());
		txtFQtdAcoes.setText(String.valueOf(op.getQtdPapeis()));
		txtFTotal.setText(String.valueOf(op.getTotal()));
		carregarAtivo(op);

	}
	public static void atualizarNoticias() {

		jPanelInforma = contInform.painelInforma();
		jScrollPaneInforma.setViewportView(jPanelInforma);

	};
	// TODO Habilita Edição
	public static void habilitaEdicao() {
		txtFCodiAtivo.setEditable(false);
		getTxtFValorPapel().setEditable(true);
		getTxtFCorretagem().setEditable(true);
		txtFTipoOper.setEditable(true);
		txtFCodiOrd.setEditable(true);
		txtFQtdAcoes.setEditable(true);

	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();
		String codigo = cOp.criaCodiOp();
		txtFCodiOperacao.setText(codigo);
		txtFCodiAtivo.setEditable(false);
		txtFCodiAtivo.setEnabled(true);
		getTxtFValorPapel().setEditable(true);
		getTxtFCorretagem().setEditable(true);
		txtFTipoOper.setEditable(true);
		txtFCodiOrd.setEditable(true);
		txtFQtdAcoes.setEditable(true);

	}

	public void refresh() {
		repaint();
	}

	// DesabilitaEdição
	public static void desHabilitaEdicao() {
		txtFSeqOperacao.setEditable(false);
		txtFCodiAtivo.setEditable(false);
		getTxtFValorPapel().setEditable(false);
		getTxtFCorretagem().setEditable(false);
		txtFTipoOper.setEditable(false);
		txtFCodiOrd.setEditable(false);
		txtFQtdAcoes.setEditable(false);

	}

	public static void limparCampos() {
		txtFCodiAtivo.setText(null);
		getTxtFValorPapel().setText(null);
		getTxtFCorretagem().setText(null);
		txtFTipoOper.setText(null);
		txtFCodiOrd.setText(null);
		txtFQtdAcoes.setText(null);
		txtFTotal.setText(null);

	}

	public static void adicionaAtvOp(Ativo ativo) {
		txtFCodiAtivo.setText(ativo.getIdNeg());

	}

	/*
	 * private JRadioButton jrbEditarSim; private JRadioButton jrbEditarNao;
	 * private ButtonGroup grpRadio;
	 */

	public static JTextField getTxtFNomeProd() {
		return getTxtFValorPapel();
	}

	public static void setTxtFNomeProd(JTextField txtFNomeProd) {
		PainelOperacao.setTxtFValorPapel(txtFNomeProd);
	}

	public static JTextField getTxtFStartCompra() {
		return getTxtFValorPapel();
	}

	public static void setTxtFStartCompra(JTextField txtFStartCompra) {
		PainelOperacao.setTxtFValorPapel(txtFStartCompra);
	}

	public static JTextField getTxtFValorPapel() {
		return txtFValorPapel;
	}

	public static void setTxtFValorPapel(JTextField txtFValorPapel) {
		PainelOperacao.txtFValorPapel = txtFValorPapel;
	}

	public static JTextField getTxtFCorretagem() {
		return txtFCorretagem;
	}

	public static void setTxtFCorretagem(JTextField txtFCorretagem) {
		PainelOperacao.txtFCorretagem = txtFCorretagem;
	}
}
