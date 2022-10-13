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
import online.lucianofelix.beans.GrupoSubgrupo;
import online.lucianofelix.beans.TipoSistema;
import online.lucianofelix.controle.ControlaCentroCusto;
import online.lucianofelix.controle.ControlaGrupoSubgrupo;
import online.lucianofelix.controle.ControlaTipoSistema;
import online.lucianofelix.treeModels.TreeModelCentroCusto;
import online.lucianofelix.util.ModeloListenerArvore;

public class AbaCadastros extends JPanel implements TreeSelectionListener {

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
	static ControlaGrupoSubgrupo contGrupo;
	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode clientes;
	private static DefaultMutableTreeNode fornecedores;
	private static DefaultMutableTreeNode funcionarios;
	private static DefaultMutableTreeNode pessoas;
	private static DefaultMutableTreeNode nodProdutos;
	private static DefaultMutableTreeNode nodPessoas;

	private static JScrollPane scrArvNegocios;
	private static DefaultTreeModel modArvore;
	private static TreeModelCentroCusto modArvCCusto;
	private static JSplitPane sppPrincipal;
	private static DefaultMutableTreeNode nodGrupos;
	private static DefaultMutableTreeNode leafGrupos;
	private static DefaultMutableTreeNode leafProdutos;
	private static DefaultMutableTreeNode leafPessoas;
	private static DefaultMutableTreeNode contas;
	private static DefaultMutableTreeNode nodCentroCusto;
	private static DefaultMutableTreeNode leafCentroCusto;
	private static DefaultMutableTreeNode tabelasPrecos;
	private static DefaultMutableTreeNode condPagamento;
	private static DefaultMutableTreeNode servicos;

	public AbaCadastros() {

		// TODO Configura��o dos Labels e text fields e �rvore de neg�cios
		lblTituloTela = new JLabel("Pessoas");
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));

		criaArvore();

		// TODO Posicionamento e a��es bot�es
		scrArvNegocios = new JScrollPane(arvore);
		pnlPrincipal = new JPanel(new BorderLayout());
		lblDataSistema = new LblDataSistema();
		pnlCalendar = new JPanel(new BorderLayout());
		pnlCalendar.add(lblDataSistema, BorderLayout.CENTER);
		pnlCalendar.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				"Time"));
		pnlCalendar.setBackground(Color.WHITE);

		pnlCalendar.add(lblDataSistema, BorderLayout.CENTER);
		pnlPrincipal.add(pnlCalendar, BorderLayout.PAGE_START);
		pnlPrincipal.add(scrArvNegocios, BorderLayout.CENTER);

		sppPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppPrincipal.setDividerSize(2);
		sppPrincipal.setDividerLocation(310);

		sppPrincipal.add(pnlPrincipal);
		setLayout(new GridLayout());
		add(sppPrincipal);

	}

	// TODO Renderizar a �rvore neg�cios
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
			if (!node.isRoot() && node.isNodeAncestor(nodPessoas)) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\usergroup24x24.png"));
			}
			if (node.toString().equals("Clientes")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\People32x32.png"));
			}
			if (node.toString().equals("Fornecedores")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\Industry32x32.png"));
			}
			if (node.toString().equals("Funcion�rios")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Func32x32.png"));
			}
			if (node.toString().equals("Pessoas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\personfolder_32x32.png"));
			}
			if (!node.isRoot() && node.isNodeAncestor(nodProdutos)) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\etiqueta24x24.png"));
			}
			if (node.toString().equals("Produtos")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\store.png"));
			}
			if (!node.isRoot() && node.isNodeAncestor(nodCentroCusto)) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\Currency16x16.png"));
			}
			if (node.toString().equals("Centros de Custo")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\flowblock32x32.png"));
			}
			if (node.toString().equals("Servi�os")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Equipment.png"));
			}
			if (node.toString().equals("Grupos/Categorias")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\workarea.png"));
			}
			if (node.toString().equals("Contas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\contas32x32.png"));
			}
			if (node.toString().equals("Condi��es de Pagamento")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\business32x32.png"));
			}
			if (node.toString().equals("Tabelas de Pre�os")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\billing32x32.png"));
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
			if (nomeNo.equals("Pessoas")) {
				FrameInicial.getContPess().iniciar();
			}
			if (node.isLeaf() && node.isNodeAncestor(nodPessoas)) {
				FrameInicial.getContPess().iniciar(nomeNo);
			}
			if (nomeNo.equals("Produtos")) {
				FrameInicial.getContProd().iniciar();
			}
			if (node.isLeaf() && node.isNodeAncestor(nodProdutos)) {
				FrameInicial.getContProd().iniciar(nomeNo);
			}
			if (nomeNo.equals("Servi�os")) {
				FrameInicial.getContServ().iniciar();
			}
			/**
			 * Inicia os centros de custo com a posi��o financeira no dashboard
			 */
			if (nomeNo.equals("Centros de Custo")) {
				// FrameInicial.getContPosiFin().iniciar();
				FrameInicial.getContCentroCusto().iniciar();
			}
			/**
			 * Inicia a cpnta do centro de custo
			 */
			if (node.isLeaf() && node.isNodeAncestor(nodCentroCusto)) {
				FrameInicial.getContConta().iniciar(nomeNo);
			}
			if (nomeNo.equals("Tabelas de Pre�os")) {
				FrameInicial.getContTabPreco().iniciar();
			}
			if (nomeNo.equals("Condi��es de Pagamento")) {
				FrameInicial.getContCondPag().iniciar();
			}
			if (nomeNo.equals("Grupos/Categorias")) {
				FrameInicial.getContTipS().iniciar();
			}
			if (node.isLeaf() && node.isNodeAncestor(nodGrupos)) {
				FrameInicial.getContGrupo().iniciar(nomeNo);
			}
		} else {

			criaArvore();

		}

	}

	// TODO Carregar centros de Custo
	static void carregarNoCCusto() {
		nodCentroCusto = new DefaultMutableTreeNode("Centros de Custo");
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

	static void carregarNoGrupos() {
		nodGrupos = new DefaultMutableTreeNode("Grupos/Categorias");
		List<TipoSistema> listTipoS = new ArrayList<TipoSistema>(
				contTipoS.listTipo());;
		for (int i = 0; i < listTipoS.size(); i++) {
			TipoSistema tipoS = listTipoS.get(i);
			leafGrupos = new DefaultMutableTreeNode(tipoS.getNomeTipoSistema());
			nodGrupos.add(leafGrupos);
		}
	}
	static void carregarNoProdutos() {
		nodProdutos = new DefaultMutableTreeNode("Produtos");
		List<GrupoSubgrupo> listGrupo = new ArrayList<GrupoSubgrupo>(contGrupo
				.pesquisarPorSubgrupoProdutos("Categorias de Produtos"));;
		for (int i = 0; i < listGrupo.size(); i++) {
			GrupoSubgrupo grupo = listGrupo.get(i);
			leafProdutos = new DefaultMutableTreeNode(grupo.getNomeGrupo());
			nodProdutos.add(leafProdutos);
		}
	}
	static void carregarNoPessoas() {
		nodPessoas = new DefaultMutableTreeNode("Pessoas");
		List<GrupoSubgrupo> listGrupo = new ArrayList<GrupoSubgrupo>(contGrupo
				.pesquisarPorSubgrupoPessoas("Categorias de Pessoas"));;
		for (int i = 0; i < listGrupo.size(); i++) {
			GrupoSubgrupo grupo = listGrupo.get(i);
			leafPessoas = new DefaultMutableTreeNode(grupo.getNomeGrupo());
			nodPessoas.add(leafPessoas);
		}
	}

	public static void criaNos() {
		root = new DefaultMutableTreeNode("Cadastros e Tabelas");
		contCCusto = new ControlaCentroCusto();
		contTipoS = new ControlaTipoSistema();
		contGrupo = new ControlaGrupoSubgrupo();

		carregarNoCCusto();
		carregarNoGrupos();
		carregarNoProdutos();
		carregarNoPessoas();

		condPagamento = new DefaultMutableTreeNode("Condi��es de Pagamento");
		servicos = new DefaultMutableTreeNode("Servi�os");
		tabelasPrecos = new DefaultMutableTreeNode("Tabelas de Pre�os");

		root.add(nodPessoas);
		root.add(nodProdutos);
		root.add(servicos);
		// root.add(nodCentroCusto);
		// root.add(contas);
		root.add(tabelasPrecos);
		root.add(condPagamento);
		root.add(nodGrupos);

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
		System.out.println("posi��o da arvore   " + pos);
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
	// // TODO Ao soltar o bot�o
	// System.out.println("Mouse Released");
	//
	// }
	//
	// @Override
	// public void mousePressed(MouseEvent arg0) {
	// //TODO Ao pressionar o bot�o
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
		AbaCadastros.nomeNo = nomeNo;
	}

	public static JTree getArvoreNegocios() {
		return arvore;
	}

	public static void setArvoreNegocios(JTree arvoreNegocios) {
		AbaCadastros.arvore = arvoreNegocios;
	}
}
