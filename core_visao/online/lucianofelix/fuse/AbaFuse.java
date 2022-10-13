package online.lucianofelix.fuse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import online.lucianofelix.bdi.AtualizaSistemaBDIBovespa;
import online.lucianofelix.beans.fuse.AtivoYahoo;
import online.lucianofelix.beans.fuse.Indicadores;
import online.lucianofelix.dao.DAOAtvYahoo;
import online.lucianofelix.mineradores.Minerador;
import online.lucianofelix.util.AtualizaCotacaoAutHashSet;
import online.lucianofelix.util.ModeloListenerArvore;
import online.lucianofelix.util.MontaGrid;
import online.lucianofelix.visao.FrameInicial;
import online.lucianofelix.visao.LblDataSistema;
import online.lucianofelix.zebra.ProgressBarFrame;

class RenderizarTreeNegocios extends DefaultTreeCellRenderer
		implements
			TreeCellRenderer {
	private Font plainFont, italicFont;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		if (node.isRoot()) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\archives32x32.png"));
		}
		if (node.toString().equals("Ferramentas")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\config32x32.png"));

		}
		if (node.toString().equals("Indicadores")) {
			setIcon(new ImageIcon(
					"C:\\SIMPRO\\img\\order\\3dbarchart32x32.png"));
		}
		if (node.toString().equals("Osciladores")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\chartxy.png"));
		}
		// if (!node.isRoot() && node.isNodeAncestor(ferramentas)) {
		// setIcon(new ImageIcon(
		// "C:\\SIMPRO\\img\\order\\Currency16x16.png"));
		if (node.toString().equals("Trading")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Industry32x32.png"));
		}
		if (node.toString().equals("Minerações")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Currency16x16.png"));
		}
		if (node.toString().equals("Últimas Cotações")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\communication.png"));
		}
		if (node.toString().equals("Ativos Merc Vista")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\archives32x32.png"));
		}
		if (node.toString().equals("Dolar Diário")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\business32x32.png"));
		}
		if (node.toString().equals("Receitas")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Plus32x32.png"));
		}
		if (node.toString().equals("Despesas")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Minus32x32.png"));
		}
		if (node.toString().equals("Plano de Contas")) {
			setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\contas32x32.png"));
		}
		return this;
	}
}

public class AbaFuse extends JPanel implements TreeSelectionListener {
	public static JPanel painelPrincipal;
	JPanel painelObjetos;

	// Labels, text fields, e objetos de visualização
	public static JProgressBar pgbMineracao;
	private static JLabel lblMineracao;
	private JLabel lblTituloTela;
	private JLabel dataSistema;
	private JTextField txtfSeqOperacao;
	private JTextField txtfIdAtivo;
	private JTextField txtfStartCompra;
	private JTextField txtfOrdCompra;
	private JTextField txtfStartVenda;
	private JTextField txtfOrdVenda;
	private JTextField txtFTipoSetup;
	// Arvore do Sistema
	private static JTree arvoreSistema;
	private static DefaultMutableTreeNode sistema;
	private DefaultMutableTreeNode ferramentas;
	private DefaultMutableTreeNode trading;
	private DefaultMutableTreeNode fuse;
	private DefaultMutableTreeNode ordensBovespa;
	private DefaultMutableTreeNode operacoes;
	private DefaultMutableTreeNode indicadores;
	private DefaultMutableTreeNode osciladores;
	private static DefaultMutableTreeNode mineracoes;
	private static DefaultMutableTreeNode nodLucroAtivo;
	private static DefaultMutableTreeNode nodLucroGeral;
	private static DefaultMutableTreeNode nodLucroGeralAtivo;
	private DefaultMutableTreeNode ativosMercVista;
	private DefaultMutableTreeNode ativosUltimas;
	private DefaultMutableTreeNode dolarDiario;
	DefaultMutableTreeNode node;
	private static String nomeNo;
	private static JSplitPane sppPrincipal;
	private static JPanel pnlButtons;
	private static JPanel pnlPrincipal;
	private static JPanel pnlCalendar;
	private static JScrollPane scrMineracao;

	// botões
	private JButton btnProximo;
	private JButton btnAnterior;
	private JButton btnBaixaBdi;
	private JButton btnAtuCot;
	private JButton btnAtuAnual;
	private JButton btnMinerar;
	// Objetos de Controle
	private Indicadores indicador;

	private static DAOAtvYahoo daoAtvYahoo;
	private static List<AtivoYahoo> listAtvSis;
	private MontaGrid montaGrid;
	private static DefaultTreeModel modArvore;
	private static List<AtivoYahoo> listAtvYahoo;

	private AtualizaSistemaBDIBovespa atuBov;

	// TODO Construtor
	public AbaFuse() {
		UIManager.put("TextField.font",
				new Font("times new roman", Font.BOLD, 13));
		UIManager.put("Label.font", new Font("times new roman", Font.BOLD, 11));
		UIManager.put("Button.font",
				new Font("times new roman", Font.BOLD, 12));

		// objetos de controle

		daoAtvYahoo = new DAOAtvYahoo();

		// Configurações da Arvore do sistema;
		sistema = new DefaultMutableTreeNode("Simpro");

		// Ferramentas
		ferramentas = new DefaultMutableTreeNode("Ferramentas");
		indicadores = new DefaultMutableTreeNode("Indicadores");
		indicadores.add(new DefaultMutableTreeNode("Média Móvel"));
		osciladores = new DefaultMutableTreeNode("Osciladores");
		osciladores.add(new DefaultMutableTreeNode("Estocástico"));
		ferramentas.add(indicadores);
		ferramentas.add(osciladores);
		// Trading
		trading = new DefaultMutableTreeNode("Trading");
		fuse = new DefaultMutableTreeNode("Output Fuses");
		ordensBovespa = new DefaultMutableTreeNode("Ordens");
		operacoes = new DefaultMutableTreeNode("Operações");
		trading.add(fuse);
		trading.add(ordensBovespa);
		trading.add(operacoes);
		// Ativos Minerados

		nodLucroAtivo = new DefaultMutableTreeNode("L/P por Ativo");
		nodLucroGeralAtivo = new DefaultMutableTreeNode("L/P Geral por Ativo");
		nodLucroGeral = new DefaultMutableTreeNode("L/P Geral");
		mineracoes = new DefaultMutableTreeNode("Minerações");
		mineracoes.add(nodLucroAtivo);
		mineracoes.add(nodLucroGeralAtivo);
		mineracoes.add(nodLucroGeral);

		// Ativos em análise
		ativosUltimas = new DefaultMutableTreeNode("Últimas Cotações");
		// Configuração ativos do sistema
		ativosMercVista = new DefaultMutableTreeNode("Ativos Merc Vista");
		dolarDiario = new DefaultMutableTreeNode("Dolar Diário");

		// montagem da árvore
		sistema.add(ferramentas);
		sistema.add(trading);
		sistema.add(mineracoes);
		sistema.add(ativosUltimas);
		sistema.add(ativosMercVista);
		sistema.add(dolarDiario);

		modArvore = new DefaultTreeModel(sistema);
		modArvore.addTreeModelListener(new ModeloListenerArvore());
		arvoreSistema = new JTree(modArvore);

		// Where the tree is initialized:
		arvoreSistema.getSelectionModel().setSelectionMode(
				TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

		// Listen for when the selection changes.
		arvoreSistema.addTreeSelectionListener(this);
		arvoreSistema.setShowsRootHandles(true);
		arvoreSistema.setRootVisible(false);
		// arvoreSistema.setSelectionRow(4);
		arvoreSistema.setRowHeight(40);
		arvoreSistema.setCellRenderer(new RenderizarTreeNegocios());
		scrMineracao = new JScrollPane(arvoreSistema);

		// TODO Posicionamento dos Botões

		// Ação Radio Buttons

		// Jbuttons
		btnAnterior = new JButton("<<<<");
		btnProximo = new JButton(">>>>");
		btnBaixaBdi = new JButton("Baixa Bdi");
		btnAtuCot = new JButton("Atu. Cotações");
		btnAtuAnual = new JButton("Atu Anual");
		btnAtuAnual.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				atuBov = new AtualizaSistemaBDIBovespa();
				try {
					String ano = JOptionPane.showInputDialog(
							"Digite o ano para a atualização:");
					if (atuBov.baixarBdiAnual(ano)) {
						FrameInicial.getScrLista()
								.setViewportView(atuBov.datasSistemaFuse());
						JOptionPane.showMessageDialog(null,
								"Baixado com sucesso para o ano: " + ano);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro na atualização!",
							e1.getMessage(), JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnMinerar = new JButton("Minerar");
		btnMinerar.setToolTipText(
				"Busca dentro da série histórica por pontos de compra e venda revelando a lucratividade com o indicador selecionado.");
		// TODO TESTANDO
		btnAtuCot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FrameInicial.disparaThread();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// TODO Botao Minerar
		btnMinerar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int dias = Integer.parseInt(JOptionPane
						.showInputDialog("Deseja a média de quantos dias?"));
				String idNeg = JOptionPane.showInputDialog(
						"Digite o Cod Bovespa: \nEX.: PETR4, CIEL3, BRKM5... ");
				Minerador min = new Minerador();
				try {
					min.recomendaVenda(idNeg, dias);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// node = (DefaultMutableTreeNode)
				// arvoreSistema.getLastSelectedPathComponent();
				//
				// if (node == null) {
				// JOptionPane.showMessageDialog(null, "Nada selecionado para
				// minerar!");
				// }
				// if (node.isLeaf()) {
				// if (node.isNodeAncestor(mineracoes) & node.getChildCount() !=
				// 0) {
				// System.out.println("Ativos Minerados");
				// // mineraAtivo(node);
				// }
				//
				// if (node.isNodeAncestor(ativosUltimas)) {
				// mineraAtivo(node);
				// }
				// }
				// if (node.isLeaf() & node.isNodeSibling(ativosMercVista)) {
				// JOptionPane.showConfirmDialog(null, "Minerar todos do Mercado
				// a vista?");
				// minerarQtdNegMercVista();
				//
				// }
				//
			}

		});

		btnBaixaBdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				atuBov = new AtualizaSistemaBDIBovespa();
				try {
					String mesDia = JOptionPane.showInputDialog(
							"Digite a data para a atualização:");
					if (atuBov.baixarBdiWebManual(mesDia)) {
						FrameInicial.getScrLista()
								.setViewportView(atuBov.datasSistemaFuse());
						JOptionPane.showMessageDialog(null,
								"Atualizado com sucesso para a data: "
										+ mesDia);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro na atualização!",
							e1.getMessage(), JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnProximo.setEnabled(true);
		btnAnterior.setEnabled(true);
		btnBaixaBdi.setEnabled(true);
		btnAtuCot.setEnabled(true);
		btnAtuAnual.setEnabled(true);

		dataSistema = new LblDataSistema();

		pnlCalendar = new JPanel(new BorderLayout());
		pnlCalendar.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				"Time"));
		pnlCalendar.setBackground(Color.WHITE);
		pnlCalendar.add(dataSistema, BorderLayout.CENTER);
		pnlPrincipal = new JPanel(new BorderLayout());
		pnlPrincipal.add(pnlCalendar, BorderLayout.PAGE_START);
		pnlPrincipal.add(scrMineracao, BorderLayout.CENTER);

		pnlButtons = new JPanel(new GridLayout(4, 0));
		pnlButtons.add(btnBaixaBdi);
		pnlButtons.add(btnAtuCot);
		pnlButtons.add(btnAtuAnual);
		pnlButtons.add(btnMinerar);

		sppPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppPrincipal.setDividerSize(2);
		sppPrincipal.setDividerLocation(310);

		sppPrincipal.add(pnlPrincipal);
		sppPrincipal.add(pnlButtons);
		setLayout(new GridLayout());
		add(sppPrincipal);

	}// TODO Fim do construtor

	// TODO Carregar Ativos do Sistema
	//
	// public void carregarListaAtivosCot() {
	// ativosMercVista = new DefaultMutableTreeNode("Ativos DisponÃ­veis");
	// listAtvSis = new ArrayList<Ativo>();
	// listAtvSis = daoAtv.todosMercadoVista();
	// for (int i = 0; i < listAtvSis.size(); i++) {
	// ativosMercVista.add(new DefaultMutableTreeNode(listAtvSis.get(i)
	// .getIdYahoo()));
	// }
	// painelPrincipal.repaint();
	// }

	// .addObject("New Node " + newNodeSuffix++);
	//
	// public DefaultMutableTreeNode addObject(Object child) {
	// DefaultMutableTreeNode parentNode = null;
	// TreePath parentPath = tree.getSelectionPath();
	//
	// if (parentPath == null) {
	// //There is no selection. Default to the root node.
	// parentNode = rootNode;
	// } else {
	// parentNode = (DefaultMutableTreeNode)
	// (parentPath.getLastPathComponent());
	// }
	//
	// return addObject(parentNode, child, true);
	// }
	//
	// public DefaultMutableTreeNode
	// addObject(DefaultMutableTreeNode parent,
	// Object child,
	// boolean shouldBeVisible) {
	// DefaultMutableTreeNode childNode =
	// new DefaultMutableTreeNode(child);
	// ...
	// treeModel.insertNodeInto(childNode, parent,
	// parent.getChildCount());
	//
	// //Make sure the user can see the lovely new node.
	// if (shouldBeVisible) {
	// tree.scrollPathToVisible(new TreePath(childNode.getPath()));
	// }
	// return childNode;
	// }

	public static void minerarQtdNegMercVista() {
		listAtvYahoo = daoAtvYahoo.todosMercadoVistaCompl();
		for (int i = 0; i < listAtvYahoo.size(); i++) {
			if (listAtvYahoo.get(i).getListCotComp().get(0).getTotNeg() > 100) {
				mineracoes.add(new DefaultMutableTreeNode(
						listAtvYahoo.get(i).getIdYahoo()));
			}
		}
	}

	public void mineraAtivo(DefaultMutableTreeNode noSelecionado) {

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf() & node.isNodeAncestor(ativosMercVista)) {
			if (node.getAllowsChildren()) {
				String idNeg = nodeInfo.toString();
				JOptionPane.showConfirmDialog(null,
						"Mineração avançada para:" + idNeg);

			}

		}
		if (node.isLeaf() & node.isNodeAncestor(mineracoes)) {
			if (node.getAllowsChildren()) {
				String idNeg = nodeInfo.toString();
				JOptionPane.showConfirmDialog(null,
						"Mineração avançada para:" + idNeg);

			}

		}

	}

	public static void minerarQtdNeg(List<AtivoYahoo> listAtvYahoo) {
		ProgressBarFrame frameCarregaCotacao = new ProgressBarFrame();
		frameCarregaCotacao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameCarregaCotacao.setVisible(true);
		for (int i = 0; i < listAtvYahoo.size(); i++) {
			if (listAtvYahoo.get(i).getListCotComp().get(0).getTotNeg() > 100) {
				mineracoes.add(new DefaultMutableTreeNode(
						listAtvYahoo.get(i).getIdYahoo()));
			}

		}
		arvoreSistema = criaArvore();
		scrMineracao.setViewportView(arvoreSistema);
		pgbMineracao.setVisible(false);

	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.
		node = (DefaultMutableTreeNode) arvoreSistema
				.getLastSelectedPathComponent();
		Object nodeInfo = node.getUserObject();
		nomeNo = nodeInfo.toString();
		if (node == null) {
			// Nothing is selected.
		}
		// System.out.println("isNodeChild " +
		// node.isNodeChild(ativosMercVista));
		// System.out.println("isNodeDescendant " +
		// node.isNodeDescendant(ativosMercVista));
		// System.out.println("isNodeAncestor " +
		// node.isNodeAncestor(ativosMercVista));
		// System.out.println("isRoot " + node.isRoot());
		// System.out.println("isLeaf " + node.isLeaf());
		if (node.isLeaf() & node.isNodeAncestor(fuse)) {
			FrameInicial.getContFuse().iniciar();
		}
		if (node.isLeaf() & node.isNodeAncestor(ordensBovespa)) {
			FrameInicial.pesquisaOperacao();
		}
		if (node.isLeaf() & node.isNodeAncestor(operacoes)) {
			FrameInicial.pesquisaOperacao();
		}
		if (node.isLeaf() & node.isNodeAncestor(ativosUltimas)) {
			AtualizaCotacaoAutHashSet atu = new AtualizaCotacaoAutHashSet();
			// atu.run();
		}

		if (node.isLeaf() & node.isNodeAncestor(ativosMercVista)) {
			FrameInicial.pesquisaAtivo();
			// }else {
			// displayURL(helpURL);
			// }
		}
		if (nomeNo == "L/P por Ativo") {
			FrameInicial.getContFuse().relLucAtivo();

		}
		if (nomeNo == "L/P Geral por Ativo") {
			FrameInicial.getContFuse().relLucGeralDetalh();

		}
		if (nomeNo == "L/P Geral") {
			FrameInicial.getContFuse().relLucGeral();

		}

	}

	// Evento de Mouse
	// arvoreSistema.addMouseListener(new MouseListener() {
	//
	// @Override
	// public void mouseReleased(MouseEvent arg0) {
	// // TODO Auto-generated method stub
	// System.out.println("Mouse Released");
	//
	// }
	//
	// @Override
	// public void mousePressed(MouseEvent arg0) {
	// // TODO Auto-generated method stub
	// System.out.println("Mouse Pressed");
	// }
	//
	// @Override
	// public void mouseExited(MouseEvent arg0) {
	// // TODO Auto-generated method stub
	// System.out.println("Mouse Exited");
	// }
	//
	// @Override
	// public void mouseEntered(MouseEvent arg0) {
	// // TODO Auto-generated method stub
	// System.out.println("Mouse Entered");
	//
	// }
	//
	// @Override
	// public void mouseClicked(MouseEvent arg0) {
	// // TODO Auto-generated method stub
	// System.out.println("Mouse Clicked");
	//
	// }
	// });
	public static JTree criaArvore() {
		modArvore = new DefaultTreeModel(sistema);
		modArvore.addTreeModelListener(new ModeloListenerArvore());

		arvoreSistema = new JTree(modArvore);
		arvoreSistema.setBounds(5, 5, 225, 440);
		// Where the tree is initialized:
		arvoreSistema.getSelectionModel().setSelectionMode(
				TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

		// Listen for when the selection changes.
		arvoreSistema.addTreeSelectionListener(
				(TreeSelectionListener) AbaFuse.arvoreSistema);
		arvoreSistema.setShowsRootHandles(true);

		return arvoreSistema;
	}

	public static String getNomeNo() {
		return nomeNo;
	}

	public static void atualizarTela(String str) {
		lblMineracao.setText(str);
		lblMineracao.repaint();
		// painelPrincipal.repaint();
	}

	public static JLabel getLblMineracao() {
		return lblMineracao;
	}

	public static void setLblMineracao(JLabel lblMineracao) {
		AbaFuse.lblMineracao = lblMineracao;
	}

	public static void setListAtvSis(List<AtivoYahoo> listAtvYahoo) {
		AbaFuse.listAtvSis = listAtvYahoo;
	}

}
