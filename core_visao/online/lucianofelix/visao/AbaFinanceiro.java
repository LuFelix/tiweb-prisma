package online.lucianofelix.visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import online.lucianofelix.beans.CentroCusto;
import online.lucianofelix.controle.ControlaCentroCusto;
import online.lucianofelix.controle.ControlaLancamento;
import online.lucianofelix.controle.ControlaTipoSistema;
import online.lucianofelix.treeModels.TreeModelCentroCusto;
import online.lucianofelix.util.ModeloListenerArvore;

public class AbaFinanceiro extends JPanel implements TreeSelectionListener {

	// Labels e text fields
	private JLabel lblTituloTela;
	private JLabel lblDataSistema;
	private JPanel pnlPrincipal;
	private JPanel pnlCalendar;

	// Arvore do Sistema
	private static JTree arvore;
	private static JTree arvoreContas;
	private static String nomeNo;
	static ControlaCentroCusto contCCusto;
	static ControlaTipoSistema contTipoS;
	static ControlaLancamento contLanc;

	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode nodPlContas;
	private static DefaultMutableTreeNode nodCentroCusto;
	private static DefaultMutableTreeNode leafCentroCusto;
	private static DefaultMutableTreeNode nodLancamentos;
	private static DefaultMutableTreeNode nodReceitas;
	private static DefaultMutableTreeNode nodDespesas;

	private static JScrollPane scrArvNegocios;
	private static DefaultTreeModel modArvore;
	private static TreeModelCentroCusto modArvCCusto;
	private static JSplitPane sppPrincipal;

	public AbaFinanceiro() {

		// TODO Configuração dos Labels e text fields e árvore de negócios
		lblTituloTela = new JLabel("Pessoas");
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));

		criaArvore();

		// TODO Posicionamento e ações botões
		scrArvNegocios = new JScrollPane(arvore);
		sppPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppPrincipal.setDividerSize(2);
		sppPrincipal.setDividerLocation(310);
		lblDataSistema = new LblDataSistema();
		pnlPrincipal = new JPanel(new BorderLayout());

		pnlCalendar = new JPanel(new BorderLayout());
		pnlCalendar.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				"Time"));
		pnlCalendar.setBackground(Color.WHITE);
		pnlCalendar.add(lblDataSistema, BorderLayout.CENTER);
		pnlPrincipal.add(pnlCalendar, BorderLayout.PAGE_START);
		pnlPrincipal.add(scrArvNegocios, BorderLayout.CENTER);

		sppPrincipal.add(pnlPrincipal);
		setLayout(new GridLayout());
		add(sppPrincipal);

	}

	// TODO Renderizar a árvore negócios
	private class RenderizarTreeNegocios extends DefaultTreeCellRenderer
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
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\archives32x32.png"));
			}
			if (node.toString().equals("Clientes")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\People32x32.png"));
			}
			if (node.toString().equals("Fornecedores")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\Industry32x32.png"));
			}
			if (node.toString().equals("Funcionários")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Func32x32.png"));
			}
			if (node.toString().equals("Pessoas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\personfolder_32x32.png"));
			}
			if (!node.isRoot() && node.isNodeAncestor(nodCentroCusto)) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\Currency16x16.png"));
			}
			if (node.toString().equals("Centros de Custo")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\flowblock32x32.png"));
			}
			if (node.toString().equals("Caixa")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\Cashregister32x32.png"));
			}
			if (node.toString().equals("Receitas")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Plus32x32.png"));
			}
			if (node.toString().equals("Despesas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\Minus32x32.png"));
			}
			if (node.toString().equals("Plano de Contas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\contas32x32.png"));
			}
			return this;
		}
	}

	// TODO Ao mudar o no;
	@Override
	public void valueChanged(TreeSelectionEvent e) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvore
				.getLastSelectedPathComponent();
		if (node != null) {
			Object nodeInfo = node.getUserObject();
			nomeNo = nodeInfo.toString();
			if (nomeNo.equals("Caixa")) {
				FrameInicial.getContLanc().iniciar();
			}
			/**
			 * Inicia os centros de Custo
			 */
			if (nomeNo.equals("Centros de Custo")) {
				// FrameInicial.getContPosiFin().iniciar();
				FrameInicial.getContCentroCusto().iniciar();
			}
			/**
			 * Inicia as contas do centro de custo
			 */
			if (node.isLeaf() && node.isNodeAncestor(nodCentroCusto)) {
				FrameInicial.getContConta().iniciar(nomeNo);
			}
			/**
			 * Inicia o controle de contas a pagar e receber
			 */
			if (nomeNo.equals("Plano de Contas")) {
				FrameInicial.getContPosiFin().iniciar();
			}
			if (nomeNo.equals("Grupos/Categorias")) {
				FrameInicial.getContTipS().iniciar();
			}

		} else {

			criaArvore();

		}

	}

	// TODO Carregar centros de Custo
	static void carregarNoCCusto() {
		nodCentroCusto = new DefaultMutableTreeNode("Centros de Custo");
		contCCusto = new ControlaCentroCusto();
		List<CentroCusto> listCCusto = new ArrayList<CentroCusto>(
				contCCusto.pesqNomeArray(""));;

		for (int i = 0; i < listCCusto.size(); i++) {
			CentroCusto c = listCCusto.get(i);
			leafCentroCusto = new DefaultMutableTreeNode(
					c.getNomeCentroCusto());

			// TODO Carrega as contas diretamente na folha do centro de custo
			// controlaCCusto.carregaContasCCusto(c);
			// for (int j = 0; j < c.getListContas().size(); j++) {
			// contas = new DefaultMutableTreeNode(
			// c.getListContas().get(j).getNomeConta());
			// leafCentroCusto.add(contas);
			// }
			nodCentroCusto.add(leafCentroCusto);
		}

	}

	public static void criaNos() {
		root = new DefaultMutableTreeNode("Cadastros e Tabelas");
		carregarNoCCusto();

		nodLancamentos = new DefaultMutableTreeNode("Caixa");
		nodReceitas = new DefaultMutableTreeNode("Receitas");
		nodDespesas = new DefaultMutableTreeNode("Despesas");
		nodLancamentos.add(nodReceitas);
		nodLancamentos.add(nodDespesas);

		nodPlContas = new DefaultMutableTreeNode("Plano de Contas");

		root.add(nodCentroCusto);
		root.add(nodLancamentos);
		root.add(nodPlContas);

	}

	void criaArvore() {
		criaNos();
		modArvore = new DefaultTreeModel(root);
		modArvore.addTreeModelListener(new ModeloListenerArvore());
		arvore = new JTree(modArvore);

		// Where the tree is initialized:
		arvore.getSelectionModel()
				.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvore.addTreeSelectionListener(this);
		arvore.setCellRenderer(new RenderizarTreeNegocios());
		arvore.setShowsRootHandles(true);
		arvore.setRootVisible(false);
		arvore.setRowHeight(40);
	}
	public static void recarregaArvore() {
		int pos = getArvoreNegocios().getMaxSelectionRow();
		System.out.println("posição da arvore   " + pos);
		modArvore.reload();
		// criaNos();
		getArvoreNegocios().setSelectionRow(pos);
		// getArvoreNegocios().expandRow(pos);
		scrArvNegocios.setViewportView(arvore);

	}
	public static void expandirArvore(JTree tree) {
		try {
			for (int row = 0; row <= tree.getRowCount(); row++) {
				tree.expandRow(row);
			}
		} catch (Exception e) {
			// tratar erro
		}
	}

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

	// TODO Eventos de Mouse
	// arvoreSistema.addMouseListener(new MouseListener() {
	//
	// @Override
	// public void mouseReleased(MouseEvent arg0) {
	// // TODO Ao soltar o botão
	// System.out.println("Mouse Released");
	//
	// }
	//
	// @Override
	// public void mousePressed(MouseEvent arg0) {
	// //TODO Ao pressionar o botão
	// System.out.println("Mouse Pressed");
	// }
	//
	// @Override
	// public void mouseExited(MouseEvent arg0) {
	// // TODO Ao sair de sobre o objeto
	// System.out.println("Mouse Exited");
	// }
	//
	// @Override
	// public void mouseEntered(MouseEvent arg0) {
	// // TODO Ao entrar sobre o objeto
	// System.out.println("Mouse Entered");
	//
	// }
	//
	// @Override
	// public void mouseClicked(MouseEvent arg0) {
	// // TODO Ao clicar
	// System.out.println("Mouse Clicked");
	//
	// }
	// });

	// public void criaArvContasCentCusto() {
	// DAOCentroCusto daocc = new DAOCentroCusto();
	// modArvCCusto = new TreeModelCentroCusto(daocc.pesquisarString(""));
	//
	// arvoreContas = new JTree(modArvCCusto);
	//
	// // Where the tree is initialized:
	// arvoreContas.getSelectionModel()
	// .setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	//
	// // Listen for when the selection changes.
	// arvoreContas.addTreeSelectionListener(this);
	// arvoreContas.setShowsRootHandles(true);
	// arvoreContas.setRootVisible(true);
	// arvoreContas.setRowHeight(40);
	//
	// ImageIcon openCloseIcon = new ImageIcon(
	// "C:\\SIMPRO\\img\\order\\flowblock32x32.png");
	// ImageIcon leafIcon = new ImageIcon(
	// "C:\\SIMPRO\\img\\order\\billing16x16.png");
	// if (leafIcon != null) {
	//
	// DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	//
	// renderer.setLeafIcon(leafIcon);
	// renderer.setClosedIcon(openCloseIcon);
	// renderer.setOpenIcon(openCloseIcon);
	//
	// arvoreContas.setCellRenderer(renderer);
	// }
	// }

	public static String getNomeNo() {

		return nomeNo;
	}

	public static void setNomeNo(String nomeNo) {
		AbaFinanceiro.nomeNo = nomeNo;
	}

	public static JTree getArvoreNegocios() {
		return arvore;
	}

	public static void setArvoreNegocios(JTree arvoreNegocios) {
		AbaFinanceiro.arvore = arvoreNegocios;
	}
}
