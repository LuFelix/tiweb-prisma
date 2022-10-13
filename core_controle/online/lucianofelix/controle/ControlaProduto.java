package online.lucianofelix.controle;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import online.lucianofelix.beans.GrupoSubgrupo;
import online.lucianofelix.beans.Produto;
import online.lucianofelix.beans.ProdutoCotacao;
import online.lucianofelix.beans.ProdutoEstoque;
import online.lucianofelix.dao.DAOGrupoSubgrupo;
import online.lucianofelix.dao.DAOProdutoPrepSTM;
import online.lucianofelix.dao.DAOProdutosCotacao;
import online.lucianofelix.dao.DAOProdutosEstoque;
import online.lucianofelix.dao.DAOTabelaPreco;
import online.lucianofelix.dao.DAOTiposSistema;
import online.lucianofelix.tableModels.commom.TableModelProdutos;
import online.lucianofelix.visao.AbaCadastros;
import online.lucianofelix.visao.FrameInicial;
import online.lucianofelix.visao.FrameInicial.ControlaBotoes;
import online.lucianofelix.visao.PainelPedidos;
import online.lucianofelix.visao.PainelProdutos;

public class ControlaProduto {
	String resposta;
	Produto prod;
	DAOProdutoPrepSTM daoProd;

	private ControlaListaProdutos controledaLista;
	private DAOProdutosCotacao daoProdCota;
	private DAOProdutosEstoque daoProdEstoque;
	private DAOTiposSistema daoTipoS;
	private DAOGrupoSubgrupo daoGrupo;
	private DAOTabelaPreco daoTabPreco;
	private ControlaTabelaPreco contTPreco;
	private JTable tbl01;
	TableModelProdutos mdlTabela;
	List<Produto> listProd;

	/**
	 * Construtor
	 * 
	 */
	public ControlaProduto() {
		daoProd = new DAOProdutoPrepSTM();
		daoProdCota = new DAOProdutosCotacao();
		daoProdEstoque = new DAOProdutosEstoque();
		daoTipoS = new DAOTiposSistema();
		daoGrupo = new DAOGrupoSubgrupo();
		daoTabPreco = new DAOTabelaPreco();
		contTPreco = new ControlaTabelaPreco();
	}

	/**
	 * Consultar o ultimo preco por tabela
	 * 
	 * @param produto
	 * @param nomeTabela
	 * @return
	 */
	public boolean consultaUltimoPreco(Produto produto, String nomeTabela) {
		ProdutoCotacao pc = daoProdCota.consultaUltCotVend(produto, nomeTabela);
		if (pc != null) {
			produto.setPrec_prod_1(new BigDecimal(pc.getValor()));
			return true;
		} else {
			JOptionPane.showMessageDialog(null,
					"Produto sem preço para essa tabela", "Erro",
					JOptionPane.ERROR_MESSAGE);

			return false;
		}
	}

	/**
	 * Novo Movimento de estoque
	 * 
	 * @param codiEstoque
	 * @param dataHoraMovimento
	 * @param codiProduto
	 * @param quantidade
	 * @param codiPedido
	 * @param tipoMovimento
	 */
	void novoMovimentoProduto(String codiEstoque, Date dataHoraMovimento,
			String codiProduto, int quantidade, String codiPedido,
			String tipoMovimento) {
		System.out.println("ControlaProduto.novoMovimentoProduto");
		daoProdEstoque.novoMovProdEstoque(codiEstoque, dataHoraMovimento,
				codiProduto, quantidade, codiPedido, tipoMovimento);
	}

	/**
	 * Inicia o controle de produtos da cateria recebida no parametro
	 * 
	 * @param categoria
	 */
	public void iniciar(String categoria) {
		System.out.println(
				">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> controlaProdutosIniciar(String categoria)");
		configuraBotoes();
		configuraTxtPesquisa();
		FrameInicial.setTabela(tabelaProdutosGrupo(categoria));
		if (FrameInicial.getTabela().getRowCount() <= 0) {
			FrameInicial.setTabela(null);
			FrameInicial.setPainelVisualiza(new PainelProdutos(null));
			FrameInicial.atualizaTela();
			JOptionPane.showMessageDialog(null, "Sem Produtos nessa categoria");
			ControlaBotoes.limpaTodosBotoes();
			ControlaBotoes.habilitaSomenteNovoBotoes();
			funcaoSalvar();

		} else {
			FrameInicial.getTabela().setRowSelectionInterval(0, 0);
			prod = mdlTabela
					.getProduto(FrameInicial.getTabela().getSelectedRow());
			FrameInicial.setPainelVisualiza(new PainelProdutos(prod));
			carregaDetalhes(prod);
		}

	}

	/**
	 * Inicia o controle de Produtos
	 * 
	 */
	public void iniciar() {
		System.out.println(
				">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   controlaProdutosIniciar");
		configuraBotoes();
		configuraTxtPesquisa();
		FrameInicial.setTabela(tabelaProdutos(""));
		FrameInicial.getTabela().setRowSelectionInterval(0, 0);
		prod = mdlTabela.getProduto(FrameInicial.getTabela().getSelectedRow());
		FrameInicial.setPainelVisualiza(new PainelProdutos(prod));
		carregaDetalhes(prod);
	}

	/**
	 * Configura Actions dos botoes
	 * 
	 */
	void configuraBotoes() {
		ControlaBotoes.limpaTodosBotoes();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.habilitaEdicaoBotoes();
				funcaoSobrescrever();
			}
		});
		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameInicial.setTabela(new JTable());
				ControlaBotoes.clicaNovoBotoes();
				PainelProdutos.habilitaNovo();
				FrameInicial.atualizaTela();
				funcaoSalvar();
			}
		});
		FrameInicial.getBtnNovo().addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getExtendedKeyCode() == 38) {
					iniciar();
				} else if (e.getExtendedKeyCode() == 27) {
					FrameInicial.getTxtfPesquisa().grabFocus();
				}

			}
		});
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoCancelar();
			}
		});
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoSalvar();
			}
		});
		FrameInicial.getBtnExcluir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoExcluir();
			}
		});
	}

	/**
	 * Habilita a tela para preenchimento de um novo produto e grava no banco
	 */
	public void funcaoSalvar() {
		System.out.println(
				">>>>>>>>>>>>>>>>>>>>>>>>      ControlaProduto.funcaoSalvar");
		funcaoCancelarNovo();
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prod = PainelProdutos.lerCampos();
				if (!daoProd.existe(prod.getCodi_prod_1())) {
					if (prod != null & daoProd.cadastrar(prod)) {
						PainelProdutos.limparCampos();
						FrameInicial.setTabela(
								tabelaProdutos(prod.getCodi_prod_1()));
						FrameInicial.getTabela().setRowSelectionInterval(0, 0);
						FrameInicial
								.setPainelVisualiza(new PainelProdutos(prod));
						// AbaCadastros.recarregaArvore();
						FrameInicial.atualizaTela();
						// if (AbaCadastros.getNomeNo().equals("Produtos")) {
						// iniciar();
						// } else {
						// iniciar(AbaCadastros.getNomeNo());
						// }
						JOptionPane.showMessageDialog(null, "Feito!");
						configuraTxtPesquisa();
						FrameInicial.getBtnNovo().grabFocus();
					} else {
						JOptionPane.showMessageDialog(null,
								"Problemas: Erro de acesso ao banco",
								"Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					PainelProdutos.limparCampos();
					FrameInicial
							.setTabela(tabelaProdutos(prod.getCodi_prod_1()));
					FrameInicial.getTabela().setRowSelectionInterval(0, 0);
					FrameInicial.setPainelVisualiza(new PainelProdutos(prod));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito!");
					configuraTxtPesquisa();
					ControlaBotoes.descansaBotoes();
					FrameInicial.atualizaTela();
					FrameInicial.getBtnNovo().grabFocus();
				}
			}
		});
	}

	/**
	 * 
	 * Função sobrescrever
	 * 
	 */
	public void funcaoSobrescrever() {
		System.out.println(
				">>>>>>>>>>>>>>>>>>>>>>           ControlaProduto.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		PainelProdutos.habilitaEdicao();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prod = PainelProdutos.lerCampos();
				System.out.println("codigo ean" + prod.getcEAN());
				if (prod != null & daoProd.alterar(prod)) {
					PainelProdutos.limparCampos();
					FrameInicial
							.setTabela(tabelaProdutos(prod.getCodi_prod_1()));
					FrameInicial.getTabela().setRowSelectionInterval(0, 0);
					FrameInicial.setPainelVisualiza(new PainelProdutos(prod));
					FrameInicial.atualizaTela();
					// AbaCadastros.recarregaArvore();
					ControlaBotoes.descansaBotoes();
					FrameInicial.atualizaTela();
					FrameInicial.getTxtfPesquisa().setText(prod.getNome_prod());
					JOptionPane.showMessageDialog(null, "Feito!");
					FrameInicial.getTxtfPesquisa().setText("");
					FrameInicial.getBtnNovo().grabFocus();

				} else {
					JOptionPane.showMessageDialog(null,
							"Favor verificar os campos informados. ",
							"Não foi possivel alterar o produto!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Cancela a rotina de criacao de um novo prduto
	 * 
	 */
	public void funcaoCancelarNovo() {

		ControlaBotoes.limparBtnCancelar();
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PainelProdutos.desHabilitaEdicao();
				prod = PainelProdutos.lerCampos();
				if (daoProd.excluir(prod)) {
					FrameInicial.getContProd()
							.iniciar(AbaCadastros.getNomeNo());
					funcaoCancelar();
				} else {
					JOptionPane.showMessageDialog(null,
							"Problemas: Erro ao apagar a reserva de código",
							"Erro ao Excluir", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void funcaoCancelar() {
		System.out.println(
				">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>     ControlaProduto.funcaoCancelar");
		ControlaBotoes.limparBtnCancelar();
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}

		});

	}

	private void cancelar() {
		if (AbaCadastros.getNomeNo().equals("Produtos")) {
			iniciar();
			System.out.println("AbaCadastros.getNomeNo().equals(Produtos");
		} else {
			iniciar(AbaCadastros.getNomeNo());

		}
	}

	/**
	 * Criar um novo código e retorna em um String
	 * 
	 * @return codiProd
	 */
	public String criaCodiProd() {
		System.out.println("ControlaProduto.criarCodigo");
		Calendar c = Calendar.getInstance();
		String codiProd = String.valueOf(daoProd.consultaUltimo())
				+ String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH))
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codiProd;
	}

	/**
	 * Reserva o codigo recebido na base de dados para um novo produto
	 * 
	 * @param codigo
	 */
	public void reservaCodigo(String codigo) {

		try {
			daoProd.reservaCodigo(codigo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro de acesso ao banco",
					"Erro", JOptionPane.ERROR_MESSAGE);
			FrameInicial.getContProd().iniciar(AbaCadastros.getNomeNo());
			e.printStackTrace();
		}
	}

	/**
	 * Excluir um produto recebido por parametro
	 * 
	 * @param produto
	 * @return boolean
	 */
	public boolean funcaoExcluir(Produto produto) {
		System.out.println(
				">>>>>>>>>>>>>>>>>>   ControlaProduto.excluir(Produto prod)");
		if (daoProd.excluir(produto)) {
			FrameInicial.limpaTela();
			JOptionPane.showMessageDialog(null, "Feito!");
			iniciar(AbaCadastros.getNomeNo());
			return true;
		} else {
			JOptionPane.showMessageDialog(null,
					"Favor verificar os campos informados. ",
					"Não foi possivel excluir!", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	/**
	 * Excluir um produto lendo a tela
	 * 
	 * @return
	 */
	public boolean funcaoExcluir() {
		System.out.println("ControlaProduto.excluir");
		prod = PainelProdutos.lerCampos();
		if (daoProd.excluir(prod)) {
			FrameInicial.limpaTela();
			JOptionPane.showMessageDialog(null, "Feito!");
			iniciar(AbaCadastros.getNomeNo());
			return true;
		} else {
			JOptionPane.showMessageDialog(null,
					"Favor verificar os campos informados. ",
					"Não foi possivel excluir!", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	/**
	 * Consultar Estoque do produto recebido no parametro
	 * 
	 * @return int
	 */
	public int consultaEstoque(Produto produto) {
		System.out.println("ControlaProduto.consultaEstoque");
		int totalEntra = 0;
		int totalSai = 0;
		int totalAtual = 0;
		List<ProdutoEstoque> listMov = new ArrayList<ProdutoEstoque>();
		DAOProdutosEstoque daoestoque = new DAOProdutosEstoque();
		listMov = daoestoque.conEntrSaiProdOrdSeqAscend(produto, "Sai");
		for (ProdutoEstoque produtoEstoque : listMov) {
			totalSai = totalSai + produtoEstoque.getQuantidade();
		}
		listMov = daoestoque.conEntrSaiProdOrdSeqAscend(produto, "Entra");
		for (ProdutoEstoque produtoEstoque : listMov) {
			totalEntra = totalEntra + produtoEstoque.getQuantidade();
		}
		totalAtual = totalEntra - totalSai;
		return totalAtual;
	}

	public void cadastrar(Produto prod) {
		System.out.println("ControlaProduto.cadastrar");
		daoProd.cadastrar(prod);
	}

	public void posicionarTabela(int linha) {
		FrameInicial.getTabela().changeSelection(linha, 0, false, false);

	}

	public List<Produto> procurarTodos() {
		return daoProd.procurarTodos();
	}

	/**
	 * Prepara o produto recebido no parâmetro para receber um novo preco
	 * 
	 * @param produto
	 */
	public void editarPreco(Produto produto) {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		String entrada = JOptionPane.showInputDialog("Informe o novo valor:");
		BigDecimal valor = new BigDecimal(entrada.replace(",", "."));
		String nomeTabela = contTPreco.selecionaNomeTabelaPreco();
		try {

			novoPreco(nomeTabela, data, produto.getCodi_prod_1(), valor);
			// carregaDetalhes(prod);
			// PainelProdutos.habilitaTabelaPrecos(prod);
			// funcaoSobrescrever();
			// FrameInicial.getBtnSalvar().doClick();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problemas: ",
					" Erro ao Cadastrar: " + e1.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Efetua o registro de um novo preco para o produto
	 * 
	 * @param nomeTabela
	 * @param dataHoraMarcaca
	 * @param codiProduto
	 * @param valor
	 * @throws SQLException
	 */
	public void novoPreco(String nomeTabela, Timestamp dataHoraMarcaca,
			String codiProduto, BigDecimal valor) throws SQLException {
		System.out.println("ControlaProduto.novoPreço");
		daoProdCota.novoPrecoProduto(daoTabPreco.pesquisaCodigoNome(nomeTabela),
				dataHoraMarcaca, codiProduto, valor);
	}

	/**
	 * Retorna o nome da tabela de preco atraves do codigo recebido no parametro
	 * 
	 * @param codiTabela
	 * @return
	 */
	public String nomeTabelaPreco(String codiTabela) {

		String nomeTabela = daoTabPreco.pesquisaNomeCodigo(codiTabela);

		return nomeTabela;

	}

	/**
	 * Ajustar tamanho das colunas
	 */
	private void ajusta_tamanho_coluna() {
		// tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbl01.getColumnModel().getColumn(0).setPreferredWidth(60);
		tbl01.getColumnModel().getColumn(1).setPreferredWidth(220);
		tbl01.getColumnModel().getColumn(2).setPreferredWidth(20);
		// tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
		// tabela.getColumnModel().getColumn(4).setPreferredWidth(300);
		// tabela.getColumnModel().getColumn(5).setPreferredWidth(80);
		// tabela.getColumnModel().getColumn(6).setPreferredWidth(80);
	}

	/**
	 * 
	 * Tabela ligada ao painel de produtos; Tabela geral de produtos
	 * 
	 * @param str
	 * @return
	 */
	public JTable tabelaProdutos(String str) {
		mdlTabela = new TableModelProdutos(daoProd.pesquisaString(str));
		tbl01 = new JTable(mdlTabela);
		tbl01.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					carregaDetalhes(prod);
					PainelProdutos.carregarCampos(prod);
					tbl01.grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tbl01.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					PainelProdutos.carregarCampos(prod);
					tbl01.grabFocus();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					carregaDetalhes(prod);
					FrameInicial.getBtnEditar().doClick();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelProdutos.getTxtFNomeProd().grabFocus();
				}
			}
		});
		tbl01.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				//
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				//

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				//

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}
		});

		ajusta_tamanho_coluna();
		tbl01.setShowGrid(true);
		return tbl01;
	}

	/**
	 * TODO Tabela ligada ao painel de produtos; Tabela geral de produtos
	 * 
	 * @param produto
	 * @return
	 */
	public JTable tabelaProdutos(Produto produto) {
		mdlTabela = new TableModelProdutos(
				daoProd.pesquisaString(produto.getCodi_prod_1()));
		tbl01 = new JTable(mdlTabela);
		tbl01.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					carregaDetalhes(prod);
					PainelProdutos.carregarCampos(prod);
					tbl01.grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tbl01.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					PainelProdutos.carregarCampos(prod);
					tbl01.grabFocus();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					carregaDetalhes(prod);
					FrameInicial.getBtnEditar().doClick();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelProdutos.getTxtFNomeProd().grabFocus();
				}
			}
		});
		tbl01.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				//
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				//

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				//

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}
		});

		ajusta_tamanho_coluna();
		tbl01.setShowGrid(true);
		return tbl01;
	}

	public JTable tabelaProdutosGrupo(String grupo) {

		String codiGrupo = daoGrupo.pesquisarCodigoNome(grupo);
		System.out.println("               ");
		System.out.println(codiGrupo);
		System.out.println("               ");
		listProd = daoProd.pesquisaProdutosCategoria(codiGrupo);

		mdlTabela = new TableModelProdutos(listProd);
		tbl01 = new JTable(mdlTabela);
		tbl01.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					carregaDetalhes(prod);
					PainelProdutos.carregarCampos(prod);
					tbl01.grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tbl01.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					PainelProdutos.carregarCampos(prod);
					tbl01.grabFocus();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					prod = mdlTabela.getProduto(tbl01.getSelectedRow());
					carregaDetalhes(prod);
					FrameInicial.getBtnEditar().doClick();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					// PainelProdutos.getTxtFNomeProd().grabFocus();
				}
			}
		});
		tbl01.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				//
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				//

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				//

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				prod = mdlTabela.getProduto(tbl01.getSelectedRow());
				carregaDetalhes(prod);
				tbl01.grabFocus();

			}
		});

		ajusta_tamanho_coluna();
		tbl01.setShowGrid(true);
		return tbl01;
	}

	/**
	 * TODO Tabela ligada ao painel de pedidos
	 * 
	 * @param nome
	 * @return
	 */
	public JTable pesqNomeTabelaAdicionaItemAopedido(String nome) {
		ArrayList<String> colunas = new ArrayList<>();
		mdlTabela = new TableModelProdutos(daoProd.pesquisaString(nome));
		tbl01 = new JTable(mdlTabela);
		tbl01.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent tecla) {
				int linha = tbl01.getSelectedRow();
				System.out.println(tecla.getExtendedKeyCode());
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelPedidos.adicionaItem(mdlTabela.getProduto(linha),
							PainelPedidos.getLblTabPreco().getText());
				} else if (tecla.getExtendedKeyCode() == 9) {
					PainelPedidos.getTxtfCliente().grabFocus();
				}
			}
		});
		tbl01.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				int linha = tbl01.getSelectedRow();
				if (PainelPedidos.getLblTabPreco().getText().equals(null)) {
					JOptionPane.showMessageDialog(null,
							"Por favor selecione a Tabela de Preços ", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} else {
					PainelPedidos.adicionaItem(mdlTabela.getProduto(linha),
							PainelPedidos.getLblTabPreco().getText());
					System.out.println("preço para a tabela "
							+ PainelPedidos.getLblTabPreco().getText());

				}
			}
		});
		colunas.add("Código");
		colunas.add("Nome");
		// colunas.add("Preço");

		tbl01.setShowGrid(true);
		return tbl01;
	}

	/**
	 * 
	 * Configuração do campo de texto de pesquisa principal
	 * 
	 */
	void configuraTxtPesquisa() {
		FrameInicial.limparTxtfPesquisa();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {
					if (FrameInicial.getTabela().getRowCount() > 0) {
						FrameInicial.getTabela().grabFocus();
						FrameInicial.getScrFluxo().setViewportView(null);
					}
				} else if (tecla.getExtendedKeyCode() == 27) {
					cancelar();
				} else {
					String str = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(tabelaProdutos(str));
					if (tbl01.getRowCount() > 0) {
						FrameInicial.getTabela().setRowSelectionInterval(0, 0);
						prod = mdlTabela.getProduto(tbl01.getSelectedRow());
						carregaDetalhes(prod);
						FrameInicial.getScrFluxo().setViewportView(null);
					} else {
						FrameInicial.setTabela(new JTable());
						prod = new Produto();
						carregaDetalhes(prod);
						JLabel label = new JLabel("Nada encontrado!!");
						label.setFont(
								new Font("Times New Roman", Font.BOLD, 48));
						label.setForeground(Color.RED);
						FrameInicial.getScrFluxo().setViewportView(label);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {
					if (tbl01.getRowCount() > 0) {
						FrameInicial.getTabela().setRowSelectionInterval(0, 0);
						prod = mdlTabela.getProduto(tbl01.getSelectedRow());
						carregaDetalhes(prod);
						FrameInicial.getScrFluxo().setViewportView(null);
					}

				} else if (tecla.getExtendedKeyCode() == 27) {
					cancelar();
				} else {
					String str = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(tabelaProdutos(str));
					if (FrameInicial.getTabela().getRowCount() > 0) {
						FrameInicial.getTabela().setRowSelectionInterval(0, 0);
						prod = mdlTabela.getProduto(tbl01.getSelectedRow());
						carregaDetalhes(prod);

						FrameInicial.getScrFluxo().setViewportView(null);
					} else {
						FrameInicial.setTabela(new JTable());
						prod = new Produto();
						carregaDetalhes(prod);
						JLabel label = new JLabel("Nada encontrado!!");
						label.setFont(
								new Font("Times New Roman", Font.BOLD, 48));
						label.setForeground(Color.RED);
						FrameInicial.getScrFluxo().setViewportView(label);
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

	}

	/**
	 * Função para atualizar apenas a categoria do produto
	 * 
	 * @param prod
	 */
	public void editarCatProduto(Produto prod, List<GrupoSubgrupo> listGrupo) {

	}

	/**
	 * Retorna uma lista de Grupos apenas com o código
	 * 
	 * @param prod
	 * @return
	 */
	public Produto carregarCategorias(Produto prod) {
		prod.setListGrupo(daoProd.carregarCategoriasProduto(prod));
		if (prod.getListGrupo().size() > 0) {

		}

		return prod;
	}

	public void carregaDetalhes(Produto prod) {
		carregarCotacoes(prod);
		carregarCategorias(prod);
		PainelProdutos.carregarCampos(prod);
		FrameInicial.atualizaTela();
	}

	public Produto carregarCotacoes(Produto prod) {
		System.out.println("codigo cotações" + prod.getCodi_prod_1());
		prod.setListCotacaoProduto(daoProdCota.conCotProdOrdSeqDesc(prod));
		return prod;

	}

	public List<Produto> pesqNomeArray(String nome) {
		daoProd = new DAOProdutoPrepSTM();
		return daoProd.pesquisaString(nome);
	}

	public Produto procurar(String codiProduto) {
		return daoProd.procurar(codiProduto);
	}

	public int consultaUltimo() {
		return daoProd.consultaUltimo();
	}

	public void adicionarCategoria(Produto prod, String nomeCategoria) {
		String codiCategoria = daoGrupo.pesquisarCodigoNome(nomeCategoria);
		daoProd.cadastrarCategoria(prod.getCodi_prod_1(), codiCategoria);

	}

}
