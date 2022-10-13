package online.lucianofelix.visao;

/**
 * 
 * @author Luciano de O. Felix
 * Esta classe é a base da visão desktop do sistema.
 *
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.wannawork.jcalendar.JCalendarComboBox;
import online.lucianofelix.bdi.AtualizaSistemaBDIBovespa;
import online.lucianofelix.beans.Pessoa;
import online.lucianofelix.controle.ControlaCentroCusto;
import online.lucianofelix.controle.ControlaCondPagamento;
import online.lucianofelix.controle.ControlaConta;
import online.lucianofelix.controle.ControlaGrupoSubgrupo;
import online.lucianofelix.controle.ControlaLancamento;
import online.lucianofelix.controle.ControlaPedido;
import online.lucianofelix.controle.ControlaPessoa;
import online.lucianofelix.controle.ControlaPosicaoFinanceira;
import online.lucianofelix.controle.ControlaProduto;
import online.lucianofelix.controle.ControlaServico;
import online.lucianofelix.controle.ControlaSistema;
import online.lucianofelix.controle.ControlaTabelaPreco;
import online.lucianofelix.controle.ControlaTipoSistema;
import online.lucianofelix.fuse.AbaFuse;
import online.lucianofelix.fuse.ControlaAtivo;
import online.lucianofelix.fuse.ControlaFuse;
import online.lucianofelix.fuse.ControlaOperacao;
import online.lucianofelix.fuse.PainelAtivo;
import online.lucianofelix.fuse.PainelFuse;
import online.lucianofelix.fuse.PainelOperacao;
import online.lucianofelix.plcontas.PainelCentroCusto;
import online.lucianofelix.plcontas.PainelConta;
import online.lucianofelix.util.AtualizaCotacaoAutHashSet;
import online.lucianofelix.util.ManipulaArquivoTxt;
import online.lucianofelix.util.SelecionaArquivo;

public class FrameInicial {
	// +++++Frames++++++++++++++++++++++++++++++++++
	private static JFrame frmPrincipal;
	// +++++Tabelas+++++++++++++++++++++++++++++++++
	private static JTable tblLista;
	private static JTable tblFluxo;
	static JTable tblMensagens;
	static String nome;
	static JTextArea atxtArquivoTxt;
	// +++++Objetos de visualiza��o ++++++++++++++++
	static JSplitPane jspPrincipal;
	static JScrollPane scrLista;
	public static JScrollPane scrVisualiza;
	private static JScrollPane scrFluxo;
	static JScrollPane scrMensagens;
	private JSplitPane painelPrincipal;
	private JSplitPane painelTabelas;
	private static JPanel painelVisualiza;
	private static JPanel pnlLista;
	private JPanel painelTab1;
	private JPanel painelTab2;
	private JPanel painelTab3;
	private JPanel painelTab4;
	private JPanel painelTab5;
	private JPanel painelFuncoes;
	private JPanel pnlMenu1;
	private JPanel pnlMenu2;
	private JPanel pnlMenu3;
	private JTabbedPane painelTabulado1;
	private JTabbedPane painelTabulado2;
	static JLabel lblStatus;
	static JLabel lblPesquisa;
	private static File arquivoAberto;
	// static int tipoTela;
	private static Pessoa usuarioLogado;
	private static JTextField txtfPesquisa;

	// +++++Objetos de Controle+++++++++++
	private static ControlaLancamento contLanc;
	private static ControlaPosicaoFinanceira contPosiFin;
	private static ControlaPessoa contPess;
	private static ControlaServico contServ;
	private static ControlaCondPagamento contCondPag;
	private static ControlaProduto contProd;
	private static ControlaConta contConta;
	private static ControlaPedido contPedi;
	private static ControlaAtivo contAtv;
	private static ControlaOperacao contOp;
	private static ControlaFuse contFuse;
	private static ControlaTabelaPreco contTabPreco;
	private static ControlaGrupoSubgrupo contGrupo;
	private static ControlaCentroCusto contCentroCusto;
	private static ControlaSistema contSist;
	private static ControlaTipoSistema contTipS;
	AtualizaCotacaoAutHashSet atu;
	static AtualizaSistemaBDIBovespa atuBdi;
	public static Thread t1;
	private static SelecionaArquivo selArq;
	private ManipulaArquivoTxt manTxt;
	private JCalendarComboBox cal;

	private JMenuBar menuBarPrincipal;
	private JMenuBar menuBar1;
	private JMenuBar menuBar2;
	private JMenuBar menuBar3;

	// Botoes
	private JButton btnLocalizar;
	private static JButton btnNovo;
	private static JButton btnImportar;
	private static JButton btnEditar;
	private static JButton btnCancelar;
	private static JButton btnSalvar;
	private static JButton btnExcluir;

	// +++++Menus Raiz++++++++++++++++++++++++++++++
	private JMenu mnPrincipal;

	private JMenu mnuExibir;
	private JMenu mnuPDV;
	private JMenu mnuTask;

	private JMenu mnHelp;

	// +++++ Submenus do menu principal++++++++++++
	private JMenu mnImportar;

	// +++++Itens dos menu Importar+++++++++++++++++
	private JMenuItem mnItmArquivo;
	private JMenuItem mnItmBdi;
	private JMenuItem mnItmFuse;
	private JMenuItem mnItmLists;
	private JMenuItem mnItmOrder;
	private JMenuItem mnItmWritter;

	public static JMenuItem mnuParamFuse;
	public static JMenuItem mnuConfigOrder;
	public static JMenuItem mnuParamLists;
	private JMenuItem mnuFuncoesBanco;
	// +++++Sub menu do menu help
	private JMenuItem mntmSobre;
	// +++++Sub menu do menu help

	// private ManipulaBdi manBdi;

	// TODO Construtor
	@SuppressWarnings("static-access")
	public FrameInicial() {
		super();
		// Configura��o do Frame
		frmPrincipal = new JFrame();
		// System Observer to a Rapid Trade Evolution
		frmPrincipal.setTitle(
				"-::- SIFRAO - Sistema Informa��es Financeiras Administrativas e Operacionais -::- Tend�ncias");
		frmPrincipal.setResizable(true);
		UIManager.put("Button.font",
				new Font("Times New Roman", Font.BOLD, 16));

		// frmPrincipal.setUndecorated(true);
		// frmPrincipal.getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
		try {
			// UIManager.setLookAndFeel(
			// new com.nilo.plaf.nimrod.NimRODLookAndFeel());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel(new Plastic3DLookAndFeel());

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				// System.out.println(info.getName());

				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

			// PlasticLookAndFeel.setHighContrastFocusColorsEnabled(true);
			UIManager.put("ScrollBar.is3DEnabled", Boolean.TRUE);
			// UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.jgoodies.looks.plaf.nimbus");
			// UIManager.setLookAndFeel("javax.swing.plaf.Macintosh.MacintoshLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			// Set NINROD Theme
			// NimRODTheme nt = new NimRODTheme(
			// "C:\\SIMPRO\\images\\NimRODThemeFileBlue.theme");
			// NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
			// NimRODLF.setCurrentTheme(nt);
			// UIManager.setLookAndFeel(NimRODLF);
			//
			//
			// frmPrincipal.setUndecorated(true);
			//
			// } catch (ClassNotFoundException e1) {
			// e1.printStackTrace();
			// } catch (InstantiationException e1) {
			// e1.printStackTrace();
			// } catch (IllegalAccessException e1) {
			// e1.printStackTrace();

			// } catch (
			//
			// UnsupportedLookAndFeelException e1)
			//
			// {
			// e1.printStackTrace();
		} catch (

		Exception e)

		{
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		// Configura��o dos menus

		mnPrincipal = new JMenu("Principal");
		mnImportar = new JMenu("Importar");
		mnuParamFuse = new JMenuItem("Configura��es do Fuse");
		mnuParamLists = new JMenuItem("Configura��es dos Cadastros");
		mnuConfigOrder = new JMenuItem("Configura��es do Operacional");
		mnuConfigOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FrameConfigOrder();

			}
		});
		mnuFuncoesBanco = new JMenuItem("Fun��es de Banco ");

		mnHelp = new JMenu("Help");

		// Subitens do menu importar
		mnItmArquivo = new JMenuItem("Arquivo");
		mnItmBdi = new JMenuItem("BDI");
		mnImportar.add(mnItmArquivo);
		mnImportar.add(mnItmBdi);

		// Subitens do menu exibir
		mnuExibir = new JMenu("Exibir");
		mnuPDV = new JMenu("PDV");
		mnuTask = new JMenu("Tarefas");
		mnuTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// new FrmPrincipal();

			}
		});
		mnItmFuse = new JMenuItem("Fuse");
		mnItmLists = new JMenuItem("Cadastros");
		mnItmOrder = new JMenuItem("Operacional");
		mnItmWritter = new JMenuItem("Relat�rios");
		mnuExibir.add(mnItmFuse);
		mnuExibir.add(mnItmLists);
		mnuExibir.add(mnItmOrder);
		mnuExibir.add(mnItmWritter);

		// Subitens do menu sobre
		mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"M�dulos: Fuse - Cadastros - Operacional - Relat�rios \nTechnology Projects - ME\n"
								+ "\nVers�o 0.3  12/2013\n" + "Desde 2010\n"
								+ "\nVers�o 0.4  12/2016\n" + "Desde 2010\n"
								+ "\nVers�o 0.5  12/2019\n" + "Desde 2010\n");
			}
		});
		mnHelp.add(mntmSobre);

		// Menu principal
		mnPrincipal.add(mnImportar);
		mnPrincipal.add(mnuParamLists);
		mnPrincipal.add(mnuParamFuse);
		mnPrincipal.add(mnuConfigOrder);
		mnPrincipal.add(mnuFuncoesBanco);

		// Objeto de data
		cal = new JCalendarComboBox();

		// TODO Objetos de Controle
		setContLanc(new ControlaLancamento());
		setContConta(new ControlaConta());
		contPess = new ControlaPessoa();
		contProd = new ControlaProduto();
		contPedi = new ControlaPedido();
		contFuse = new ControlaFuse();
		contAtv = new ControlaAtivo();
		contOp = new ControlaOperacao();
		contSist = new ControlaSistema();
		contTipS = new ControlaTipoSistema();
		setContServ(new ControlaServico());
		setContCondPag(new ControlaCondPagamento());
		setContTabPreco(new ControlaTabelaPreco());
		setContGrupo(new ControlaGrupoSubgrupo());
		setContCentroCusto(new ControlaCentroCusto());
		setContPosiFin(new ControlaPosicaoFinanceira());
		atuBdi = new AtualizaSistemaBDIBovespa();

		// TODO Menus
		mnItmArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// new TelaSeletoraTipoDeArquivo();
			}
		});

		mnuParamLists.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contTipS.iniciar();

			}
		});

		mnuParamFuse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				scrLista.setViewportView(atuBdi.datasSistemaFuse());
				scrVisualiza.setViewportView(null);

			}
		});

		mnuFuncoesBanco.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scrLista.setViewportView(contSist.listarTabelasSistema());
				scrVisualiza.setViewportView(null);
			}
		});

		// TODO Text Fields
		lblPesquisa = new JLabel(new ImageIcon(
				"//home//luciano//SIMPRO//SIMPRO//images//ic_search_24.png"));
		txtfPesquisa = new JTextField("Pesquisa");
		txtfPesquisa.setFont(new Font("times new roman", Font.BOLD, 16));
		txtfPesquisa.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (!txtfPesquisa.equals("Pesquisa")) {
					txtfPesquisa.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				txtfPesquisa.setText("Pesquisa");
			}
		});

		// TODO JButtons

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(60, 0, 90, 30);
		btnNovo.setBackground(new Color(0, 0, 0, 0));
		btnNovo.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				btnNovo.setBackground(new Color(0, 0, 0, 0));
			}

			@Override
			public void focusGained(FocusEvent e) {
				btnNovo.setBackground(null);
			}
		});
		btnNovo.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnNovo.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnNovo.setBackground(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(160, 0, 90, 30);
		btnEditar.setBackground(new Color(0, 0, 0, 0));
		btnEditar.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				btnEditar.setBackground(new Color(0, 0, 0, 0));
			}

			@Override
			public void focusGained(FocusEvent e) {
				btnEditar.setBackground(null);
			}
		});
		btnEditar.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnEditar.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnEditar.setBackground(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(250, 0, 90, 30);
		btnExcluir.setBackground(new Color(0, 0, 0, 0));
		btnExcluir.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				btnExcluir.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				btnExcluir.setBackground(null);

			}
		});
		btnExcluir.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExcluir.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnExcluir.setBackground(null);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(340, 0, 90, 30);
		btnCancelar.setBackground(new Color(0, 0, 0, 0));
		btnCancelar.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				btnCancelar.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				btnCancelar.setBackground(null);

			}
		});
		btnCancelar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCancelar.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnCancelar.setBackground(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(0, 0, 0, 0));
		btnSalvar.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				btnSalvar.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void focusGained(FocusEvent e) {
				btnSalvar.setBackground(null);

			}
		});
		btnSalvar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSalvar.setBackground(new Color(0, 0, 0, 0));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalvar.setBackground(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		btnImportar = new JButton("Importar");
		btnLocalizar = new JButton("Localizar");
		// TODO Planilha
		// btnNovo.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent arg0) throws
		// NullPointerException {
		// manTxt = new ManipulaArquivoTxt();
		// try {
		// if (manTxt.verificaArquivo(arquivoAberto)) {
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });

		// TODO Importar Arquivo
		btnImportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// new TelaSeletoraTipoDeArquivo();
			}
		});

		// TODO Localizar
		btnLocalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manTxt = new ManipulaArquivoTxt();
				if (manTxt.verificaArquivo(arquivoAberto)) {
					String busca = JOptionPane.showInputDialog(
							"Informe a sequ�ncia a ser encontrada no arquivo:");
					int quantidadeEncontrada = manTxt.buscarQuantString(busca,
							arquivoAberto);
					if (quantidadeEncontrada > 0) {
						JOptionPane.showMessageDialog(null, "A sequ�ncia ===>> "
								+ busca + " <<=== foi encontrada "
								+ quantidadeEncontrada + " vezes no arquivo.");
					} else {
						JOptionPane.showMessageDialog(null, "A sequ�ncia==>  "
								+ busca + "  <== n�o encontrada.");
					}
				}
			}
		});

		// +++++bot�o Atualizar+++++++++++++++++++++++++++++++++++++
		// btnAtualiza.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		//
		// AtualizaSistemaSimproBolsa atuSis = new AtualizaSistemaSimproBolsa();
		// String strData = JOptionPane.showInputDialog(null,
		// "Informe o dia (dd/MM) para atualiza��o!");
		// try {
		// if(atuSis.atualizarManual(strData)){
		// JOptionPane.showMessageDialog(null,
		// "Atualizado com sucesso para esta data");
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// JOptionPane.showMessageDialog(null,
		// "Erro na atualiza��o para esta data", "Arquivo n�o gravado",
		// JOptionPane.ERROR_MESSAGE);
		// }
		//
		//
		// }
		// });

		// TODO Salvar
		// btnSalvar.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// manTxt = new ManipulaArquivoTxt();
		// if(manTxt.verificaArquivo(arquivoAberto)){
		// manTxt.montaArquivotxt(atxtArquivoTxt);
		// }
		// }
		// });

		// TODO Paineis
		scrMensagens = new JScrollPane();
		scrMensagens.setBorder(null);

		scrFluxo = new JScrollPane();
		scrFluxo.setBorder(null);

		scrFluxo.setBackground(Color.WHITE);
		scrFluxo.setForeground(Color.WHITE);

		scrLista = new JScrollPane();
		scrLista.setBorder(null);
		scrLista.setBackground(Color.WHITE);

		scrVisualiza = new JScrollPane();
		scrVisualiza.setBorder(null);
		scrVisualiza.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getBtnEditar().doClick();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				int tipoTela = verificaPainelDetalhes();
				// JOptionPane.showMessageDialog(null, "Tipo tela: " +
				// tipoTela);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		// Barra de menus
		painelFuncoes = new JPanel(new GridLayout(0, 5, 0, 0));
		painelFuncoes.setBorder(null);
		painelFuncoes.setOpaque(false);
		// painelFuncoes.setBorder(BorderFactory.createEtchedBorder());
		painelFuncoes.add(btnNovo);
		painelFuncoes.add(btnExcluir);
		painelFuncoes.add(btnEditar);
		painelFuncoes.add(btnCancelar);
		painelFuncoes.add(btnSalvar);

		menuBarPrincipal = new JMenuBar();
		menuBarPrincipal.setLayout(new GridLayout(2, 0));

		menuBar1 = new JMenuBar();
		menuBar1.add(mnPrincipal);
		menuBar1.add(mnuExibir);
		menuBar1.add(mnuPDV);
		menuBar1.add(mnHelp);

		// JToolBar tbarFerramentas = new JToolBar();
		// tbarFerramentas.setLayout(new GridLayout(0, 1));
		// tbarFerramentas.add(txtfPesquisa);
		// tbarFerramentas.add(painelFuncoes);
		menuBar2 = new JMenuBar();
		menuBar2.setLayout(new GridLayout(0, 2));
		menuBar2.add(txtfPesquisa);
		menuBar2.add(painelFuncoes);
		menuBarPrincipal.add(menuBar1);
		menuBarPrincipal.add(menuBar2);

		// Configura��o das abas

		painelTab1 = new AbaFuse();
		painelTab2 = new AbaCadastros();
		painelTab3 = new AbaNegocios();
		painelTab4 = new AbaRelatorios();
		painelTab5 = new AbaFinanceiro();

		// painelTab4 = new AbaStatus();
		// painelTab4.setBounds(0, 0, 220, 380);
		// painelTab4.setLayout(null);

		painelTabulado1 = new JTabbedPane();
		painelTabulado1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		painelTabulado1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (painelTabulado1.getSelectedIndex() == 0) {
					FrameInicial.pesquisaAtivo();
				}
				if (painelTabulado1.getSelectedIndex() == 1) {
					contPess.iniciar("");
					AbaCadastros.getArvoreNegocios().setSelectionRow(0);
				}
				if (painelTabulado1.getSelectedIndex() == 2) {
					contPedi.iniciar("");
					AbaNegocios.getArvoreNegocios().setSelectionRow(0);
				}
				if (painelTabulado1.getSelectedIndex() == 3) {
					contConta.iniciar();
				}
				if (painelTabulado1.getSelectedIndex() == 4) {
					limpaTela();
				}
			}
		});
		painelTabulado1.add("Fuse", painelTab1);
		painelTabulado1.add("Cadastros", painelTab2);
		painelTabulado1.add("Operacional", painelTab3);
		painelTabulado1.add("Relat�rios", painelTab4);
		painelTabulado1.add("Financeiro", painelTab5);
		painelTabulado1.setSelectedIndex(1);

		// painelTabulado.add("Servi�os", painelTab3);
		// painelTabulado.add("Status", painelTab4);
		painelTabulado2 = new JTabbedPane(JTabbedPane.BOTTOM);
		painelTabulado2.setBackground(Color.WHITE);
		painelTabulado2.add("Fluxo", scrFluxo);
		painelTabulado2.addTab("Mensagens", scrMensagens);

		// Configura��o do Calend�rio
		cal = new JCalendarComboBox();
		cal.setBounds(560, 10, 120, 20);

		// Configura��o do painel principal
		jspPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jspPrincipal.setBorder(new EmptyBorder(0, 0, 0, 0));
		jspPrincipal.setEnabled(true);
		painelTabelas = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		painelTabelas.setDividerSize(3);
		painelTabelas.setDividerLocation(480);
		painelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		painelPrincipal.setBorder(new EmptyBorder(2, 2, 0, 2));
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setDividerSize(3);
		painelPrincipal.setDividerLocation(220);
		painelTabelas.add(scrLista);
		painelTabelas.add(scrVisualiza);
		painelPrincipal.add(painelTabulado1);
		painelPrincipal.add(painelTabelas);
		jspPrincipal.add(painelPrincipal);
		// jspPrincipal.add(painelTabulado2);
		jspPrincipal.setDividerSize(3);
		jspPrincipal.setDividerLocation(530);

		// painelPrincipal.add(painelFuncoes);

		// configura��o do frame principal
		frmPrincipal.setContentPane(jspPrincipal);
		frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPrincipal.setSize(1250, 720);
		frmPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmPrincipal.setJMenuBar(menuBarPrincipal);
		frmPrincipal.setLocationRelativeTo(null);
		frmPrincipal.setVisible(true);

		// atu = new AtualizaCotacaoAutHashSet();
		// atu.run();
		ControlaBotoes.desHabilitaEdicaoBotoes();
	} // Fim
		// Construtor

	public static ControlaPessoa getContPess() {
		return contPess;
	}

	public static void setContPess(ControlaPessoa contPess) {
		FrameInicial.contPess = contPess;
	}

	public static void disparaThread() {
		t1 = new Thread(atuBdi);
		t1.start();
	}

	public static ControlaGrupoSubgrupo getContGrupo() {
		return contGrupo;
	}

	public static void setContGrupo(ControlaGrupoSubgrupo contGrupo) {
		FrameInicial.contGrupo = contGrupo;
	}

	/**
	 * Atualiza tela
	 */
	public static void atualizaTela() {
		scrVisualiza.setViewportView(getPainelVisualiza());
		scrLista.setViewportView(getTabela());

	}

	/**
	 * Limpar a tela
	 */
	public static void limpaTela() {
		setPainelVisualiza(null);
		setTabela(null);
		scrVisualiza.setViewportView(null);
		scrLista.setViewportView(null);
	}

	/**
	 * Limpar txtFPesquisa
	 */
	public static void limparTxtfPesquisa() {
		txtfPesquisa.setText(null);
		for (ActionListener act : txtfPesquisa.getActionListeners()) {
			txtfPesquisa.removeActionListener(act);
		}
		for (KeyListener kl : txtfPesquisa.getKeyListeners()) {
			txtfPesquisa.removeKeyListener(kl);
		}
		// for(FocusListener act : txtfPesquisa.getFocusListeners()) {
		// txtfPesquisa.removeFocusListener(act);
		// }
	}

	public void atualizaMensagens() {
	}

	public void atualizaFluxo() {
	}

	// TODO Pesquisa Opera��o
	public static void pesquisaOperacao() {
		System.out.println("FrameInicial.pesquisaOperacao");
		ControlaBotoes.limpaTodosBotoes();
		limparTxtfPesquisa();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		txtfPesquisa.grabFocus();
		setTabela(contOp.pesqNomeTabela(""));
		setPainelVisualiza(new PainelOperacao(""));
		atualizaTela();
		ActionListener acaoEditar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelOperacao.habilitaEdicao();
				ControlaBotoes.habilitaEdicaoBotoes();
				contOp.funcaoSobrescrever();
			}
		};
		getBtnEditar().addActionListener(acaoEditar);
		getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.clicaNovoBotoes();
				PainelOperacao.habilitaNovo();
				contOp.funcaoSalvar();
			}
		});
		getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				contOp.funcaoCancelar();
			}
		});
		getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				contOp.funcaoSalvar();
			}
		});
		getBtnExcluir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				contOp.funcaoExcluir();
			}
		});

		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				System.out.println(tecla.getExtendedKeyCode());
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					// pergunta se quer mesmo encerrar a adi��o
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(contOp.pesqNomeTabela(nome));
					setPainelVisualiza(new PainelOperacao(nome));
					atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contOp.pesqNomeTabela(nome));
				setPainelVisualiza(new PainelOperacao(nome));
				atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Pesquisa Ativo
	 */
	public static void pesquisaAtivo() {
		limparTxtfPesquisa();
		txtfPesquisa.grabFocus();
		setTabela(contAtv.pesqAtivoTabela(""));
		setPainelVisualiza(new PainelAtivo(""));
		atualizaTela();
		// TODO A��es do txtfPesquisa
		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					// pergunta se quer mesmo encerrar a adi��o
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(contAtv.pesqAtivoTabela(nome));
					setPainelVisualiza(new PainelAtivo(nome));
					atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contAtv.pesqAtivoTabela(nome));
				setPainelVisualiza(new PainelAtivo(nome));
				atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Pesquisa ativo para realiza Operacao
	 */
	public static void pesquisaAtivoRealizaOp() {
		limparTxtfPesquisa();
		txtfPesquisa.grabFocus();
		setTabela(contAtv.pesqNomeTabelaAdicionaAtivoOp(""));
		scrLista.setViewportView(getTabela());
		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				// System.out.println(tecla.getExtendedKeyCode());
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					// pergunta se quer mesmo encerrar a adi��o
					contOp.funcaoCancelar();
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(contAtv.pesqNomeTabelaAdicionaAtivoOp(nome));
					scrLista.setViewportView(getTabela());
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contAtv.pesqNomeTabelaAdicionaAtivoOp(nome));
				scrLista.setViewportView(getTabela());
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Pesquisa cond. pagamento para realizar pedido
	 */
	public static void pesquisaCondPagamentoRealizaPedido() {
		limparTxtfPesquisa();
		txtfPesquisa.grabFocus();
		setTabela(contCondPag.pesqNomeTabelaAdicionacondPagamentoAopedido(""));
		scrLista.setViewportView(getTabela());
		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				System.out.println(tecla.getExtendedKeyCode());
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					// pergunta se quer mesmo encerrar a adi��o
					PainelPedidos.fechamentoPedido();
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(contCondPag
							.pesqNomeTabelaAdicionacondPagamentoAopedido(nome));
					scrLista.setViewportView(getTabela());
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contCondPag
						.pesqNomeTabelaAdicionacondPagamentoAopedido(nome));
				scrLista.setViewportView(getTabela());
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Pesquisa usuario para realizar um pedido
	 */
	public static void pesquisaUsuarioAdicionarAOPedido() {
		limparTxtfPesquisa();
		txtfPesquisa.grabFocus();
		setTabela(contPess.pesqNomeTabelaAdicionaUsuarioAopedido(""));
		scrLista.setViewportView(getTabela());
		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				System.out.println(tecla.getExtendedKeyCode());
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					// pergunta se quer mesmo encerrar a adi��o
					pesquisaCondPagamentoRealizaPedido();
					PainelPedidos.fechamentoPedido();
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(contPess
							.pesqNomeTabelaAdicionaUsuarioAopedido(nome));
					scrLista.setViewportView(getTabela());
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contPess.pesqNomeTabelaAdicionaUsuarioAopedido(nome));
				scrLista.setViewportView(getTabela());
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Pesquisar usu�rio para adi��o ao lancamento
	 */
	public static void pesqUsuarioAddPLanc() {
		limparTxtfPesquisa();
		txtfPesquisa.grabFocus();
		setTabela(contPess.pesqNomeTblAddUsuariLanc(""));
		scrLista.setViewportView(getTabela());
		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					JOptionPane.showMessageDialog(null, "Cancelar Lan�amento",
							"", JOptionPane.QUESTION_MESSAGE);
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(contPess.pesqNomeTblAddUsuariLanc(nome));
					scrLista.setViewportView(getTabela());
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contPess.pesqNomeTblAddUsuariLanc(nome));
				scrLista.setViewportView(getTabela());
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * 
	 * Pesquisar Cond Pag para add ao Lancamento
	 */
	public static void pesqCondPagAddPLanc() {
		limparTxtfPesquisa();
		txtfPesquisa.grabFocus();
		setTabela(contCondPag.pesqNomeTblAddCondPagLanc(""));
		scrLista.setViewportView(getTabela());
		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					JOptionPane.showMessageDialog(null, "Cancelar Lan�amento",
							"", JOptionPane.QUESTION_MESSAGE);
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(contPess.pesqNomeTblAddUsuariLanc(nome));
					scrLista.setViewportView(getTabela());
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contPess.pesqNomeTblAddUsuariLanc(nome));
				scrLista.setViewportView(getTabela());
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Pesquisa produtos para adicionar itens ao pedido
	 */
	public static void pesquisaProdutoAdicaoItem() {
		limparTxtfPesquisa();
		txtfPesquisa.grabFocus();
		if (PainelPedidos.getPnlTabAnexos().getSelectedIndex() == 0) {
			setTabela(contProd.pesqNomeTabelaAdicionaItemAopedido(""));
		} else if (PainelPedidos.getPnlTabAnexos().getSelectedIndex() == 1) {
			FrameInicial.pesquisaCondPagamentoRealizaPedido();
		}
		scrLista.setViewportView(getTabela());
		txtfPesquisa.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				System.out.println(tecla.getExtendedKeyCode());
				if (tecla.getExtendedKeyCode() == 27) {// ESC
					// pergunta se quer mesmo encerrar a adi��o
					// verificar se existe cliente carregado para a venda
					// se n�o h� cliente pergunta se quer vender como balcc�o
					// ou abrir pesquisa cliente
					pesquisaUsuarioAdicionarAOPedido();
					// PainelPedidos.fechamentoPedido();
				} else if (tecla.getExtendedKeyCode() == 10) {// enter
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 40 // seta para baixo
						| tecla.getExtendedKeyCode() == 38) { // seta cima
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else {
					nome = txtfPesquisa.getText();
					setTabela(
							contProd.pesqNomeTabelaAdicionaItemAopedido(nome));
					scrLista.setViewportView(getTabela());
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = txtfPesquisa.getText();
				setTabela(contProd.pesqNomeTabelaAdicionaItemAopedido(nome));
				scrLista.setViewportView(getTabela());
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	/**
	 * Verificar tipo de painel
	 * 
	 * @return
	 */
	static int verificaPainelDetalhes() {
		int tela = 0;
		if (!getPainelVisualiza().equals(null)) {

			if (getPainelVisualiza().getClass().equals(PainelProdutos.class)) {
				tela = 1;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelPessoa.class)) {
				tela = 2;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelPedidos.class)) {
				tela = 3;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelCentroCusto.class)) {
				tela = 4;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelServico.class)) {
				tela = 5;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelCondPagamento.class)) {
				tela = 6;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelServico.class)) {
				tela = 7;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelTabelaPreco.class)) {
				tela = 8;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelConta.class)) {
				tela = 9;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelGrupo.class)) {
				tela = 10;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelFuse.class)) {
				tela = 11;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelOperacao.class)) {
				tela = 12;
			} else if (getPainelVisualiza().getClass()
					.equals(PainelSubGrupo.class)) {
				tela = 13;
			}
		}
		return tela;
	}

	/**
	 * Controle de Bot�es
	 * 
	 * @author Luciano Felix
	 *
	 */
	public final static class ControlaBotoes {

		public static void limpaTodosBotoes() {
			limparBtnNovo();
			limparBtnEditar();
			limparBtnSalvar();
			limparBtnExcluir();
			limparBtnCancelar();
		}

		public static void limparBtnNovo() {
			for (ActionListener act : getBtnNovo().getActionListeners()) {
				getBtnNovo().removeActionListener(act);
			}
		}

		public static void limparBtnEditar() {
			for (ActionListener act : getBtnEditar().getActionListeners()) {
				getBtnEditar().removeActionListener(act);
			}
		}

		public static void limparBtnSalvar() {
			for (ActionListener act : btnSalvar.getActionListeners()) {
				getBtnSalvar().removeActionListener(act);
			}
		}

		public static void limparBtnExcluir() {
			for (ActionListener act : getBtnExcluir().getActionListeners()) {
				getBtnExcluir().removeActionListener(act);
			}
		}

		public static void limparBtnCancelar() {
			for (ActionListener act : getBtnCancelar().getActionListeners()) {
				getBtnCancelar().removeActionListener(act);
			}
		}

		public static void clicaNovoBotoes() {
			btnNovo.setEnabled(false);
			btnEditar.setEnabled(false);
			btnSalvar.setEnabled(true);
			btnExcluir.setEnabled(false);
			btnCancelar.setEnabled(true);

		}

		public static void descansaBotoes() {
			btnNovo.setEnabled(true);
			btnEditar.setEnabled(true);
			btnSalvar.setEnabled(false);
			btnExcluir.setEnabled(false);
			btnCancelar.setEnabled(true);

		}

		public static void habilitaSomenteNovoBotoes() {
			btnNovo.setEnabled(true);
			btnEditar.setEnabled(false);
			btnCancelar.setEnabled(false);
			btnSalvar.setEnabled(false);
			btnExcluir.setEnabled(false);

		}

		public static void desHabilitaEdicaoBotoes() {
			btnNovo.setEnabled(true);
			btnEditar.setEnabled(true);
			btnSalvar.setEnabled(false);
			btnExcluir.setEnabled(false);
			btnCancelar.setEnabled(true);

		}

		public static void habilitaEdicaoBotoes() {
			btnNovo.setEnabled(false);
			btnEditar.setEnabled(false);
			btnSalvar.setEnabled(true);
			btnExcluir.setEnabled(true);
			btnCancelar.setEnabled(true);
		}

	}

	/**
	 * Getters e Setters
	 * 
	 * @return
	 */
	public static JButton getBtnEditar() {
		return btnEditar;
	}

	public static void setBtnEditar(JButton btnEditar) {
		FrameInicial.btnEditar = btnEditar;
	}

	public static JButton getBtnNovo() {
		return btnNovo;
	}

	public static void setBtnNovo(JButton btnNovo) {
		FrameInicial.btnNovo = btnNovo;
	}

	public static JButton getBtnCancelar() {
		return btnCancelar;
	}

	public static void setBtnCancelar(JButton btnCancelar) {
		FrameInicial.btnCancelar = btnCancelar;
	}

	public static JButton getBtnSalvar() {
		return btnSalvar;
	}

	public static void setBtnSalvar(JButton btnSalvar) {
		FrameInicial.btnSalvar = btnSalvar;
	}

	public static JButton getBtnExcluir() {
		return btnExcluir;
	}

	public static void setBtnExcluir(JButton btnExcluir) {
		FrameInicial.btnExcluir = btnExcluir;
	}

	public static JLabel getLblGrafico() {
		return lblStatus;
	}

	public static JPanel getPainelVisualiza() {
		return painelVisualiza;
	}

	public static void setPainelVisualiza(JPanel painelVisualiza) {
		FrameInicial.painelVisualiza = painelVisualiza;
	}

	public static JScrollPane getScrLista() {
		return scrLista;
	}

	public static void setlblGrafico(JLabel lblStatus) {
		FrameInicial.lblStatus = lblStatus;
	}

	public static void setScrLista(JScrollPane scrLista) {
		FrameInicial.scrLista = scrLista;
	}

	public static void setScrVisualiza(JScrollPane scrVisualiza) {
		FrameInicial.scrVisualiza = scrVisualiza;
	}

	public static void setTabela(JTable tabela) {
		FrameInicial.setTblLista(tabela);
	}

	public static void setTxtfPesquisa(JTextField txtfPesquisa) {
		FrameInicial.txtfPesquisa = txtfPesquisa;
	}

	public static JScrollPane getScrVisualiza() {
		return scrVisualiza;
	}

	public static JTable getTabela() {
		return getTblLista();
	}

	public static JTextField getTxtfPesquisa() {
		return txtfPesquisa;
	}

	public static ControlaCentroCusto getContCentroCusto() {
		return contCentroCusto;
	}

	public static void setContCentroCusto(ControlaCentroCusto contCentroCusto) {
		FrameInicial.contCentroCusto = contCentroCusto;
	}

	public static ControlaTabelaPreco getContTabPreco() {
		return contTabPreco;
	}

	public static void setContTabPreco(ControlaTabelaPreco contTabPreco) {
		FrameInicial.contTabPreco = contTabPreco;
	}

	public static ControlaConta getContConta() {
		return contConta;
	}

	public static void setContConta(ControlaConta contConta) {
		FrameInicial.contConta = contConta;
	}

	public static ControlaServico getContServ() {
		return contServ;
	}

	public static void setContServ(ControlaServico contServ) {
		FrameInicial.contServ = contServ;
	}

	public static ControlaCondPagamento getContCondPag() {
		return contCondPag;
	}

	public static void setContCondPag(ControlaCondPagamento contCondPag) {
		FrameInicial.contCondPag = contCondPag;
	}

	public static ControlaPosicaoFinanceira getContPosiFin() {
		return contPosiFin;
	}

	public static void setContPosiFin(ControlaPosicaoFinanceira contPosiFin) {
		FrameInicial.contPosiFin = contPosiFin;
	}

	public static ControlaPedido getContPedi() {
		return contPedi;
	}

	public static void setContPedi(ControlaPedido contPedi) {
		FrameInicial.contPedi = contPedi;
	}

	public static ControlaFuse getContFuse() {
		return contFuse;
	}

	public static void setContFuse(ControlaFuse contFuse) {
		FrameInicial.contFuse = contFuse;
	}

	public static ControlaLancamento getContLanc() {
		return contLanc;
	}

	public static void setContLanc(ControlaLancamento contLanc) {
		FrameInicial.contLanc = contLanc;
	}

	public static JTable getTblLista() {
		return tblLista;
	}

	public static void setTblLista(JTable tblLista) {
		FrameInicial.tblLista = tblLista;
	}

	public static JTable getTblFluxo() {
		return tblFluxo;
	}

	public static void setTblFluxo(JTable tblFluxo) {
		FrameInicial.tblFluxo = tblFluxo;
	}

	public static JScrollPane getScrFluxo() {
		return scrFluxo;
	}

	public static void setScrFluxo(JScrollPane scrFluxo) {
		FrameInicial.scrFluxo = scrFluxo;
	}

	public static ControlaTipoSistema getContTipS() {
		return contTipS;
	}

	public static void setContTipS(ControlaTipoSistema contTipS) {
		FrameInicial.contTipS = contTipS;
	}

	public static ControlaProduto getContProd() {
		return contProd;
	}

	public static void setContProd(ControlaProduto contProd) {
		FrameInicial.contProd = contProd;
	}

	/**
	 * @return the pnlLista
	 */
	public static JPanel getPnlLista() {
		return pnlLista;
	}

	/**
	 * @param pnlLista
	 *            the pnlLista to set
	 */
	public static void setPnlLista(JPanel pnlLista) {
		FrameInicial.pnlLista = pnlLista;
	}

}
