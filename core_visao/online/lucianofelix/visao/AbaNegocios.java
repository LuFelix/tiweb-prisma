package online.lucianofelix.visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

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

import online.lucianofelix.util.ModeloListenerArvore;

public class AbaNegocios extends JPanel implements TreeSelectionListener {

	// Labels e text fields
	private JLabel lblTituloTela;
	private JLabel lblDataSistema;
	private JPanel pnlPrincipal;
	private JPanel pnlCalendar;

	// Arvore do Sistema
	private static JTree arvoreNegocios;
	private static JTree arvoreStatus;
	private static String nomeNo;
	private DefaultMutableTreeNode negocios;
	private DefaultMutableTreeNode lancamentos;
	private DefaultMutableTreeNode plContas;
	private DefaultMutableTreeNode pedidos;
	private DefaultMutableTreeNode venda;
	private DefaultMutableTreeNode compra;
	private DefaultMutableTreeNode ordServicos;
	private DefaultMutableTreeNode despesas;
	private DefaultMutableTreeNode receitas;
	private DefaultMutableTreeNode tabelasPrecos;
	private DefaultMutableTreeNode condPagamento;
	private DefaultMutableTreeNode servicos;
	private DefaultMutableTreeNode grupo;

	private JScrollPane scrArvNegocios;
	private DefaultTreeModel modArvoreNegocios;
	private JSplitPane sppPrincipal;

	public AbaNegocios() {

		// TODO Configura��o dos Labels e text fields e �rvore de neg�cios
		lblTituloTela = new JLabel("Neg�cios");
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));

		pedidos = new DefaultMutableTreeNode("Pedidos");
		compra = new DefaultMutableTreeNode("Compra");
		venda = new DefaultMutableTreeNode("Venda");

		pedidos.add(compra);
		pedidos.add(venda);
		ordServicos = new DefaultMutableTreeNode("Ordens de Servi�os");

		lancamentos = new DefaultMutableTreeNode("Caixa");
		receitas = new DefaultMutableTreeNode("Receitas");
		despesas = new DefaultMutableTreeNode("Despesas");
		lancamentos.add(receitas);
		lancamentos.add(despesas);
		plContas = new DefaultMutableTreeNode("Resumos e Provisionamentos");
		negocios = new DefaultMutableTreeNode("Simpro");

		negocios.add(pedidos);
		negocios.add(ordServicos);
		// negocios.add(lancamentos);
		negocios.add(plContas);

		modArvoreNegocios = new DefaultTreeModel(negocios);
		modArvoreNegocios.addTreeModelListener(new ModeloListenerArvore());
		arvoreNegocios = new JTree(modArvoreNegocios);

		// Where the tree is initialized:
		arvoreNegocios.getSelectionModel()
				.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvoreNegocios.addTreeSelectionListener(this);
		arvoreNegocios.setCellRenderer(new RenderizarTreeNegocios());
		arvoreNegocios.setShowsRootHandles(true);
		arvoreNegocios.setRootVisible(false);
		arvoreNegocios.setRowHeight(50);

		scrArvNegocios = new JScrollPane(arvoreNegocios);
		sppPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppPrincipal.setDividerSize(2);
		sppPrincipal.setDividerLocation(310);

		lblDataSistema = new LblDataSistema();
		pnlPrincipal = new JPanel(new BorderLayout());
		pnlCalendar = new JPanel(new BorderLayout());
		pnlCalendar.add(lblDataSistema, BorderLayout.CENTER);
		pnlCalendar.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				"Time"));
		pnlCalendar.setBackground(Color.WHITE);

		pnlPrincipal.add(pnlCalendar, BorderLayout.PAGE_START);
		pnlPrincipal.add(scrArvNegocios, BorderLayout.CENTER);
		sppPrincipal.add(pnlPrincipal);
		setLayout(new GridLayout());
		add(sppPrincipal);
	}

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
			if (node.toString().equals("Pedidos")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\order32x32.png"));
			}
			if (node.toString().equals("Compra")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\down24x24.png"));
			}
			if (node.toString().equals("Venda")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\up24x24.png"));
			}
			if (node.toString().equals("Ordens de Servi�os")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\config32x32.png"));
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
			if (node.toString().equals("Resumos e Provisionamentos")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\3dbarchart32x32.png"));
			}
			return this;
		}
	}

	// TODO Capturar sele��o de folha
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreNegocios
				.getLastSelectedPathComponent();
		Object nodeInfo = node.getUserObject();
		nomeNo = nodeInfo.toString();

		if (node != null) {
			if (nomeNo.equals("Pedidos")) {
				FrameInicial.getContPedi().iniciar(nomeNo);
			}
			if (nomeNo.equals("Compra")) {
				FrameInicial.getContPedi().iniciar(nomeNo);
			}
			if (nomeNo.equals("Venda")) {
				FrameInicial.getContPedi().iniciar(nomeNo);
			}
			if (nomeNo.equals("Ordens de Servi�os")) {
				FrameInicial.getContServ().iniciar();
			}
			if (nomeNo.equals("Caixa")) {
				FrameInicial.getContLanc().iniciar();
			}
			if (nomeNo.equals("Resumos e Provisionamentos")) {
				FrameInicial.getContPosiFin().iniciar();
			}
		}
	}

	public static void expandirrArvore(JTree tree) {
		try {
			for (int row = 0; row <= tree.getRowCount(); row++) {
				tree.expandRow(row);
			}
		} catch (Exception e) {
			// tratar erro
		}
	}

	public static String getNomeNo() {
		return nomeNo;
	}

	public static void setNomeNo(String nomeNo) {
		AbaNegocios.nomeNo = nomeNo;
	}

	public static JTree getArvoreNegocios() {
		return arvoreNegocios;
	}

	public static void setArvoreNegocios(JTree arvoreNegocios) {
		AbaNegocios.arvoreNegocios = arvoreNegocios;
	}
}
