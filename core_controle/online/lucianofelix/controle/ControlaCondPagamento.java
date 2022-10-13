package online.lucianofelix.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import online.lucianofelix.beans.CondPagamento;
import online.lucianofelix.dao.DAOCondPagamento;
import online.lucianofelix.plcontas.PainelLancamento;
import online.lucianofelix.tableModels.commom.TableModelCondPag;
import online.lucianofelix.visao.FrameInicial;
import online.lucianofelix.visao.FrameInicial.ControlaBotoes;
import online.lucianofelix.visao.PainelCondPagamento;
import online.lucianofelix.visao.PainelPedidos;

public class ControlaCondPagamento {

	private DAOCondPagamento daoCondPag;
	private JTable tabela;
	private List<CondPagamento> arrayCondPag;
	private CondPagamento condPag;

	public ControlaCondPagamento() {
		daoCondPag = new DAOCondPagamento();
	}

	public JTable pesqNomeTabela(String nome) {

		ArrayList<String> colunas = new ArrayList<>();
		tabela = new JTable();
		DefaultTableModel modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela.getModel();
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					PainelCondPagamento.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelCondPagamento.irParaPoicao(posicao);
					PainelCondPagamento.getBtnEditar().doClick();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelCondPagamento.getTxtFNomeProd().grabFocus();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
					PainelCondPagamento.irParaPoicao(posicao);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int posicao = tabela.getSelectedRow();
				PainelCondPagamento.irParaPoicao(posicao);

			}
		});
		colunas.add("C�digo");
		colunas.add("Descri��o");
		colunas.add("Parcelas");
		arrayCondPag = new ArrayList<CondPagamento>();
		arrayCondPag = daoCondPag.pesquisaString(nome);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < arrayCondPag.size(); i++) {
			Object linha[] = {arrayCondPag.get(i).getCodiCondPag(),
					arrayCondPag.get(i).getNomeCondicao(),
					arrayCondPag.get(i).getQuantParcelas(),};
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		// tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
		// tabela.getColumnModel().getColumn(1).setPreferredWidth(240);
		// tabela.getColumnModel().getColumn(2).setPreferredWidth(60);
		// tabela.setShowVerticalLines(true);
		// tabela.setShowHorizontalLines(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	public void posicionarTabela(int linha) {
		System.out.println("Ir para a linha: " + linha);
		FrameInicial.getTabela().changeSelection(linha, 0, false, false);
	}

	public List<CondPagamento> pesqNomeArray(String nome) {
		return daoCondPag.pesquisaString(nome);
	}

	// TODO Tabela que adiciona cond. de pag ao pedido.
	public JTable pesqNomeTabelaAdicionacondPagamentoAopedido(String nome) {
		ArrayList<String> colunas = new ArrayList<String>();
		tabela = new JTable();
		TableModelCondPag mdlTablCondPag = new TableModelCondPag(
				daoCondPag.pesquisaString(nome));
		DefaultTableModel modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela.getModel();
		tabela.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent tecla) {
				// TODO Ao Soltar a tecla
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				// TODO Ao Pressionar Tecla
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelPedidos

							.adicionaBaixaTitReceber(mdlTablCondPag
									.getCondPag(tabela.getSelectedRow()));
				} else if (tecla.getExtendedKeyCode() == 9) {
					FrameInicial.getBtnSalvar().grabFocus();

				}
			}
		});
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Ao Clicar
				int posicao = tabela.getSelectedRow();
				PainelPedidos
						.adicionaBaixaTitReceber(arrayCondPag.get(posicao));
				System.out.println("posicao condpag " + posicao);
			}
		});
		colunas.add("C�digo");
		colunas.add("Descri��o");
		colunas.add("Parcelas");
		arrayCondPag = new ArrayList<>();
		arrayCondPag = daoCondPag.pesquisaString(nome);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < arrayCondPag.size(); i++) {
			Object linha[] = {arrayCondPag.get(i).getCodiCondPag(),
					arrayCondPag.get(i).getNomeCondicao(),
					arrayCondPag.get(i).getQuantParcelas()};
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}
	// TODO Tabela que adiciona cond. de pag ao pedido.
	public JTable pesqNomeTblAddCondPagLanc(String nome) {
		TableModelCondPag mdlTbl = new TableModelCondPag(
				daoCondPag.pesquisaString(nome));
		tabela = new JTable(mdlTbl);
		tabela.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent tecla) {
				// TODO Ao Soltar a tecla
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					CondPagamento cp = mdlTbl
							.getCondPag(tabela.getSelectedRow());
					PainelLancamento.adicionaCondPag(cp);
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				// TODO Ao Pressionar Tecla
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelLancamento.adicionaCondPag(
							mdlTbl.getCondPag(tabela.getSelectedRow()));
				} else if (tecla.getExtendedKeyCode() == 9) {
					FrameInicial.getBtnSalvar().grabFocus();

				}
			}
		});
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Ao Clicar
				PainelLancamento.adicionaCondPag(
						mdlTbl.getCondPag(tabela.getSelectedRow()));
			}
		});

		tabela.setShowGrid(true);
		return tabela;
	}

	public String buscaNomeCodigo(String codiCP) {
		String nomeCP = daoCondPag.pesquisaNomeCodigo(codiCP);
		return nomeCP;
	}
	public String buscaCodigoNome(String nomeCP) {
		String codiCP = daoCondPag.pesquisaCodigoNome(nomeCP);
		return codiCP;
	}

	public void funcaoSalvar() {
		ControlaBotoes.limparBtnSalvar();
		ControlaBotoes.clicaNovoBotoes();
		PainelCondPagamento.habilitaNovo();
		funcaoCancelarNovo();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!PainelCondPagamento.getTxtFCodigoCondPag().getText()
						.equals("")
						& !PainelCondPagamento.getTxtFCodigoCondPag()
								.equals(null)) {
					condPag = PainelCondPagamento.lerCampos();
					try {
						daoCondPag.alterar(condPag);
						FrameInicial.setTabela(
								pesqNomeTabela(condPag.getCodiCondPag()));
						FrameInicial.setPainelVisualiza(new PainelCondPagamento(
								condPag.getCodiCondPag()));
						FrameInicial.atualizaTela();
						ControlaBotoes.desHabilitaEdicaoBotoes();
						PainelCondPagamento.desHabilitaEdicao();
						JOptionPane.showMessageDialog(null, "Feito!");
						iniciar();
						FrameInicial.getBtnNovo().grabFocus();
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Problemas: C�digo Nulo ou Duplicado",
								"Erro ao Cadastrar", JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Favor verificar os campos informados. ",
							"N�o foi possivel gravar!",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
	}

	public void funcaoSobrescrever() {
		System.out.println("ControlsCondicaoPagamento.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		ControlaBotoes.habilitaEdicaoBotoes();
		PainelCondPagamento.habilitaEdicao();
		funcaoCancelar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!PainelCondPagamento.getTxtFCodigoCondPag().getText()
						.equals("")
						& !PainelCondPagamento.getTxtFCodigoCondPag()
								.equals(null)) {
					condPag = PainelCondPagamento.lerCampos();
					try {
						daoCondPag.alterar(condPag);
						FrameInicial.setTabela(
								pesqNomeTabela(condPag.getCodiCondPag()));
						FrameInicial.setPainelVisualiza(new PainelCondPagamento(
								condPag.getCodiCondPag()));
						FrameInicial.atualizaTela();
						PainelCondPagamento.desHabilitaEdicao();
						ControlaBotoes.desHabilitaEdicaoBotoes();
						JOptionPane.showMessageDialog(null, "Feito!");
						iniciar();
						FrameInicial.getTxtfPesquisa().grabFocus();
					} catch (Exception e2) {
						PainelCondPagamento.desHabilitaEdicao();
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Erro ao gravar\n" + e2.getMessage(), "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					ControlaBotoes.desHabilitaEdicaoBotoes();
					PainelCondPagamento.desHabilitaEdicao();
					JOptionPane.showMessageDialog(null,
							"Favor verificar os campos informados. ",
							"N�o foi possivel concluir!",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
	}

	public String criaCodigo() {
		Calendar c = Calendar.getInstance();
		String codiCondPag = String.valueOf(daoCondPag.consultaUltimo())
				+ String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH))
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH));

		return codiCondPag;

	}

	public void funcaoCancelarNovo() {
		ControlaBotoes.limparBtnCancelar();
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PainelCondPagamento.desHabilitaEdicao();
				condPag = PainelCondPagamento.lerCampos();
				if (daoCondPag.remover(condPag)) {
					iniciar();
				} else {
					JOptionPane.showMessageDialog(null,
							"Problemas: Erro ao apagar a reserva de c�digo",
							"Erro ao Excluir", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void funcaoCancelar() {
		ControlaBotoes.limparBtnCancelar();
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciar();
			}
		});
	}

	public void funcaoExcluir() {
		condPag = PainelCondPagamento.lerCampos();
		if (daoCondPag.remover(condPag)) {
			FrameInicial.setTabela(null);
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.atualizaTela();
			JOptionPane.showMessageDialog(null, "Feito!");
			iniciar();
		}
	}

	public void iniciar() {
		ControlaBotoes.limpaTodosBotoes();
		funcaoCancelar();
		FrameInicial.limparTxtfPesquisa();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.setTabela(pesqNomeTabela(""));
		FrameInicial.setPainelVisualiza(new PainelCondPagamento(""));
		FrameInicial.atualizaTela();
		PainelCondPagamento.desHabilitaEdicao();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				funcaoSobrescrever();
			}
		});
		// Novo
		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				funcaoSalvar();
			}
		});
		// TODO Excluir
		FrameInicial.getBtnExcluir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				funcaoExcluir();
			}
		});

		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false,
							false);
				} else {
					String nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(pesqNomeTabela(nome));
					FrameInicial
							.setPainelVisualiza(new PainelCondPagamento(nome));
					FrameInicial.atualizaTela();
				}

			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = FrameInicial.getTxtfPesquisa().getText();
				FrameInicial.setTabela(pesqNomeTabela(nome));
				FrameInicial.setPainelVisualiza(new PainelCondPagamento(nome));
				FrameInicial.atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

}
