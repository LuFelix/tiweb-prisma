package online.lucianofelix.visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import online.lucianofelix.util.ModeloListenerArvore;

public class AbaRelatorios extends JPanel implements TreeSelectionListener {
	JSplitPane sppPrincipal;
	JPanel painelObjetos;

	private JLabel lblTituloTela;
	private JLabel lblDataSistema;
	private JPanel pnlPrincipal;
	private JPanel pnlCalendar;
	private JButton btnGerRelat;
	// Arvore do Sistema

	public static JTree arvoreNegocios;
	public static JTree arvoreStatus;
	private DefaultMutableTreeNode negocios;
	private DefaultMutableTreeNode financas;
	private DefaultMutableTreeNode contas;

	private DefaultMutableTreeNode lancamentos;
	private DefaultMutableTreeNode anotacoes;
	private DefaultMutableTreeNode contasReceber;
	private DefaultMutableTreeNode contasPagar;
	private DefaultMutableTreeNode pedidos;
	private DefaultMutableTreeNode fuse;
	private DefaultMutableTreeNode ativo;
	private DefaultMutableTreeNode geralPorAtivo;
	private DefaultMutableTreeNode venda;
	private DefaultMutableTreeNode vista;
	private DefaultMutableTreeNode prazo;
	private DefaultMutableTreeNode compra;
	private DefaultMutableTreeNode ordServicos;
	private DefaultMutableTreeNode contatos;
	private DefaultMutableTreeNode produtos;
	private DefaultMutableTreeNode estoque;
	private DefaultMutableTreeNode tabelasPrecos;
	private DefaultMutableTreeNode condPagamento;
	private DefaultMutableTreeNode servicos;
	private DefaultMutableTreeNode grupo;

	private JScrollPane scrArvNegocios;

	// Objetos de Controle
	private DefaultTreeModel modArvoreNegocios;

	public AbaRelatorios() {

		painelObjetos = new JPanel();
		painelObjetos.setLayout(null);
		painelObjetos.setSize(225, 440);

		// TODO Configura��o dos Labels e text fields e �rvore de neg�cios
		lblTituloTela = new JLabel("Relat�rios");
		lblTituloTela.setBounds(10, 5, 120, 20);
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));
		anotacoes = new DefaultMutableTreeNode("Anota��es");
		pedidos = new DefaultMutableTreeNode("Relat�rios de Pedidos");
		fuse = new DefaultMutableTreeNode("Relat�rios de Fuses");
		ativo = new DefaultMutableTreeNode("Relat�rios por Ativo");
		geralPorAtivo = new DefaultMutableTreeNode("Geral por Ativo");
		fuse.add(ativo);
		fuse.add(geralPorAtivo);

		venda = new DefaultMutableTreeNode("Venda");
		compra = new DefaultMutableTreeNode("Compra");
		vista = new DefaultMutableTreeNode("A Vista");
		prazo = new DefaultMutableTreeNode("A Prazo");
		venda.add(vista);
		venda.add(prazo);
		pedidos.add(compra);
		pedidos.add(venda);
		ordServicos = new DefaultMutableTreeNode("Relat�rios de Ord. Servi�os");
		contatos = new DefaultMutableTreeNode("Relat�rios de Contatos");
		produtos = new DefaultMutableTreeNode("Relat�rios de Produtos");

		financas = new DefaultMutableTreeNode("Relat�rios de Finan�as");
		contasPagar = new DefaultMutableTreeNode("Contas a Pagar");
		contasReceber = new DefaultMutableTreeNode("Contas a Receber");
		financas.add(contasPagar);
		financas.add(contasReceber);
		estoque = new DefaultMutableTreeNode("Relat�rios de Estoque");
		tabelasPrecos = new DefaultMutableTreeNode("Tabelas de Pre�os");
		condPagamento = new DefaultMutableTreeNode("Cond. Pagamento");
		contas = new DefaultMutableTreeNode("Contas");
		servicos = new DefaultMutableTreeNode("Servi�os");
		grupo = new DefaultMutableTreeNode("Grupos");

		estoque.add(condPagamento);
		estoque.add(contas);
		estoque.add(grupo);
		estoque.add(servicos);
		estoque.add(tabelasPrecos);

		negocios = new DefaultMutableTreeNode("Simpro");
		negocios.add(pedidos);
		negocios.add(fuse);
		negocios.add(ordServicos);
		negocios.add(contatos);
		negocios.add(produtos);
		negocios.add(financas);
		negocios.add(estoque);
		negocios.add(anotacoes);

		btnGerRelat = new JButton("Gerar Relat�rio");
		btnGerRelat.setBounds(30, 430, 180, 35);
		btnGerRelat.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 14));
		btnGerRelat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameInicial.getContFuse().relLucGeralDetalh();

			}
		});

		modArvoreNegocios = new DefaultTreeModel(negocios);
		modArvoreNegocios.addTreeModelListener(new ModeloListenerArvore());
		arvoreNegocios = new JTree(modArvoreNegocios);
		arvoreNegocios.setBounds(5, 28, 225, 200);
		scrArvNegocios = new JScrollPane(arvoreNegocios);
		scrArvNegocios.setBounds(0, 28, 250, 400);
		// Where the tree is initialized:
		arvoreNegocios.getSelectionModel()
				.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvoreNegocios.addTreeSelectionListener(this);
		arvoreNegocios.setShowsRootHandles(true);
		arvoreNegocios.setRootVisible(false);
		arvoreNegocios.setSelectionRow(2);
		arvoreNegocios.setRowHeight(30);

		lblDataSistema = new LblDataSistema();
		pnlPrincipal = new JPanel(new BorderLayout());
		pnlCalendar = new JPanel(new BorderLayout());
		pnlCalendar.add(lblDataSistema, BorderLayout.CENTER);
		pnlCalendar.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				"Time"));
		pnlCalendar.setBackground(Color.WHITE);

		pnlCalendar.add(lblDataSistema, BorderLayout.CENTER);
		pnlPrincipal.add(pnlCalendar, BorderLayout.PAGE_START);
		pnlPrincipal.add(scrArvNegocios, BorderLayout.CENTER);

		// TODO Posicionamento e a��es bot�es
		sppPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppPrincipal.setDividerSize(2);
		sppPrincipal.setDividerLocation(410);
		sppPrincipal.add(pnlPrincipal);
		sppPrincipal.add(btnGerRelat);
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

	// TODO Capturar sele��o de folha
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreNegocios
				.getLastSelectedPathComponent();
		Object nodeInfo = node.getUserObject();
		String nomeNo = nodeInfo.toString();
		if (node != null) {
			if (node.isLeaf() & node.isNodeAncestor(pedidos)) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContPedi().iniciar(nomeNo);
				}
			}
			if (node.isLeaf() & node.isNodeAncestor(fuse)) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContFuse().relLucAtivo();
				}
			}
			if (node.isLeaf() & node.isNodeAncestor(fuse)
					& node.toString().equals("Geral por Ativo")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContFuse().relLucGeralDetalh();
				}
			}
			if (node.isLeaf() & node.isNodeAncestor(ordServicos)) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContServ().iniciar();
				}
			}
			if (node.isLeaf() & node.isNodeAncestor(produtos)) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContProd()
							.iniciar(AbaCadastros.getNomeNo());
				}
			}
			if (node.isLeaf() & node.isNodeAncestor(contatos)) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContPess()
							.iniciar(AbaCadastros.getNomeNo());
				}
			}
			if (node.isLeaf() & nomeNo.equals("Cond. Pagamento")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContCondPag().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Servi�os")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContServ().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Contas")) {
				if (node.getAllowsChildren()) {

				}
			}
			if (node.isLeaf() & nomeNo.equals("Posi��o Financeira")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContPosiFin().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Tabelas de Pre�os")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContTabPreco().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Grupos")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContGrupo().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Centros de Custo")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContCentroCusto().iniciar();
				}
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
}
