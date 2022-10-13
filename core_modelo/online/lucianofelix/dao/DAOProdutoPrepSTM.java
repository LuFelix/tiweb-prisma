package online.lucianofelix.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import online.lucianofelix.beans.GrupoSubgrupo;
import online.lucianofelix.beans.Produto;
import online.lucianofelix.util.Conexao;
import online.lucianofelix.util.ConexaoSTM;

public class DAOProdutoPrepSTM {
	private Conexao c;
	private ConexaoSTM c2;
	private Produto prod;
	private GrupoSubgrupo grupo;
	private PreparedStatement prepStm;
	private ResultSet result;
	private List<Produto> listProd;
	private List<GrupoSubgrupo> listGrupo;
	private DAOGrupoSubgrupo daoGrupo;

	/**
	 * @category Classe de acessoa a dados do produto.
	 * @author Luciano de O. Felix
	 * 
	 *         nome_prod text NOT NULL, -- Nome do produto. desc_prod text, --
	 *         Descri��o do produto. aliq_prod text NOT NULL, -- Aliquota de
	 *         ICMS do produto. prec_custo real, -- Pre�o de custo produto.
	 *         seq_produto integer NOT NULL DEFAULT sequencia de inser��o
	 *         codi_prod_1 text NOT NULL, -- O principal código do produto.
	 *         prec_prod_1 real, -- Primeiro pre�o do produto. prec_prod_2 real,
	 *         -- Segundo preço de venda do produto. codi_prod_2 text, --
	 *         C�digo extra 2 para o produto.
	 * 
	 */

	public DAOProdutoPrepSTM() {
		System.out.println("DAOProduto.construtor");
		daoGrupo = new DAOGrupoSubgrupo();
		c = new Conexao(ConfigS.getBdPg(), "siacecf");
		c2 = new ConexaoSTM(ConfigS.getBdPg(), "siacecf");
	}

	/**
	 * Alterar um produto existente
	 * 
	 * @param prod
	 * @return boolean
	 * 
	 * 
	 * 
	 */
	public boolean alterar(Produto prod) {
		System.out.println(">>>>>>>>>>  DaoProduto.alterar");
		String sql = "update produtos set nome_prod=?, desc_prod=?, aliq_prod=?, prec_custo=?,"
				+ " prec_prod_1=?, prec_prod_2=?, codi_prod_2=?, codi_ean=?, detalhes_prod=?  where codi_prod_1=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, prod.getNome_prod());
			prepStm.setString(2, prod.getDesc_prod());
			prepStm.setString(3, prod.getAliq_prod());
			prepStm.setBigDecimal(4, prod.getPrec_custo());
			prepStm.setBigDecimal(5, prod.getPrec_prod_1());
			prepStm.setBigDecimal(6, prod.getPrec_prod_2());
			prepStm.setString(7, prod.getCodi_prod_2());
			prepStm.setString(8, prod.getcEAN());
			prepStm.setString(9, prod.getDetalhes_prod());
			prepStm.setString(10, prod.getCodi_prod_1());
			prepStm.execute();
			c.desconectar();
			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Cadastrar um novo produto
	 * 
	 * @param prod
	 * @return boolean
	 */
	public boolean cadastrar(Produto prod) {
		String sql = "insert into produtos (nome_prod, desc_prod, aliq_prod, prec_custo, prec_prod_1,"
				+ " prec_prod_2, codi_prod_1, codi_prod_2, codi_ean, detalhes_prod) values (?,?,?,?,?,?,?,?,?,?)";

		try {
			c.conectar();
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, prod.getNome_prod());
			prepStm.setString(2, prod.getDesc_prod());
			prepStm.setString(3, prod.getAliq_prod());
			prepStm.setBigDecimal(4, prod.getPrec_custo());
			prepStm.setBigDecimal(5, prod.getPrec_prod_1());
			// prepStm.setFloat(6, prod.getPrec_prod_2());
			prepStm.setString(7, prod.getCodi_prod_1());
			prepStm.setString(8, prod.getCodi_prod_2());
			prepStm.setString(9, prod.getcEAN());
			prepStm.setString(10, prod.getDetalhes_prod());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			c.desconectar();
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Remove um produto por c�digo
	 * 
	 * @param prod
	 * @return Boolean
	 * 
	 */
	public boolean excluir(Produto prod) {
		String sql = "delete from produtos where codi_prod_1=?; ";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, prod.getCodi_prod_1());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Consulta a sequencia do �ltimo produto inserido
	 * 
	 * @return Integer
	 * 
	 */
	public int consultaUltimo() {
		c2.conectStm();
		result = c2.query("SELECT max(seq_produto) FROM produtos;");
		c2.disconect();
		try {
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * Lista Todos os produtos por aproxima��o de codigo, nome ou descri��o
	 * 
	 * @param str
	 * @return
	 */
	public List<Produto> pesquisaString(String str) {
		String sql = "select seq_produto, codi_prod_1, nome_prod, desc_prod, aliq_prod, prec_custo, prec_prod_1, codi_ean,detalhes_prod "
				+ "from produtos "
				+ "where nome_prod ~* ?  or desc_prod ~* ? or codi_prod_1 ~* ? or codi_ean ~* ? order by nome_prod;";
		listProd = new ArrayList<Produto>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			prepStm.setString(2, str);
			prepStm.setString(3, str);
			prepStm.setString(4, str);
			result = prepStm.executeQuery();
			while (result.next()) {
				prod = new Produto();
				prod.setSeq_produto(result.getInt("seq_produto"));
				prod.setCodi_prod_1(result.getString("codi_prod_1"));
				prod.setNome_prod(result.getString("nome_prod"));
				prod.setDesc_prod(result.getString("desc_prod"));
				prod.setDetalhes_prod(result.getString("detalhes_prod"));
				prod.setAliq_prod(result.getString("aliq_prod"));
				prod.setPrec_custo(result.getBigDecimal("prec_custo"));
				prod.setPrec_prod_1(result.getBigDecimal("prec_prod_1"));
				prod.setcEAN(result.getString("codi_ean"));
				listProd.add(prod);
			}
			c.desconectar();
			return listProd;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}
	/**
	 * Lista Todos os produtos por aproxima��o de codigo, nome ou descri��o
	 * 
	 * @param str
	 * @return
	 */
	public List<Produto> pesquisaStringView(String str) {
		String sql = "select seq_produto, codi_prod_1, nome_prod, desc_prod, aliq_prod, prec_custo, prec_prod_1, codi_ean,detalhes_prod "
				+ "from view_produtos "
				+ "where nome_prod ~* ?  or desc_prod ~* ? or codi_prod_1 ~* ? or codi_ean ~* ? order by nome_prod;";
		listProd = new ArrayList<Produto>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			prepStm.setString(2, str);
			prepStm.setString(3, str);
			prepStm.setString(4, str);
			result = prepStm.executeQuery();
			while (result.next()) {
				prod = new Produto();
				prod.setSeq_produto(result.getInt("seq_produto"));
				prod.setCodi_prod_1(result.getString("codi_prod_1"));
				prod.setNome_prod(result.getString("nome_prod"));
				prod.setDesc_prod(result.getString("desc_prod"));
				prod.setDetalhes_prod(result.getString("detalhes_prod"));
				prod.setAliq_prod(result.getString("aliq_prod"));
				prod.setPrec_custo(result.getBigDecimal("prec_custo"));
				prod.setPrec_prod_1(result.getBigDecimal("prec_prod_1"));
				prod.setcEAN(result.getString("codi_ean"));
				listProd.add(prod);
			}
			c.desconectar();
			return listProd;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

	/**
	 * Lista de grupos ao qual pertence o produto
	 * 
	 * @param codiProduto
	 * @return List<GrupoSubGrupo>
	 * 
	 */
	public List<GrupoSubgrupo> pesquisaCategorias(String codiProduto) {
		String sql = "select codi_grupo from produtos_grupos where codi_produto = ? order by nome_prod;";
		listGrupo = new ArrayList<GrupoSubgrupo>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, codiProduto);
			result = prepStm.executeQuery();
			while (result.next()) {
				grupo = new GrupoSubgrupo();
				grupo.setCodiGrupo(result.getString("codi_grupo"));
				listGrupo.add(grupo);
			}
			c.desconectar();
			return listGrupo;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

	/**
	 * Lista de produtos perntencentes ao grupo de produtos
	 * 
	 * @param codiGrupo
	 * @return List<Produto>
	 * 
	 */
	public List<Produto> pesquisaProdutosCategoria(String codiGrupo) {

		String sql = "SELECT produtos.seq_produto, produtos.codi_prod_1, produtos.nome_prod, produtos.desc_prod, produtos.aliq_prod, produtos.prec_custo,produtos.prec_prod_1, produtos.codi_ean,produtos.detalhes_prod, produtos_grupos.codi_produto "
				+ "FROM produtos, produtos_grupos "
				+ "WHERE produtos_grupos.codi_grupo = ? and produtos.codi_prod_1 = produtos_grupos.codi_produto order by produtos.nome_prod;";

		listProd = new ArrayList<Produto>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, codiGrupo);
			result = prepStm.executeQuery();
			while (result.next()) {
				prod = new Produto();
				prod.setSeq_produto(result.getInt("seq_produto"));
				prod.setCodi_prod_1(result.getString("codi_prod_1"));
				prod.setNome_prod(result.getString("nome_prod"));
				prod.setDesc_prod(result.getString("desc_prod"));
				prod.setDetalhes_prod(result.getString("detalhes_prod"));
				prod.setAliq_prod(result.getString("aliq_prod"));
				prod.setPrec_custo(result.getBigDecimal("prec_custo"));
				prod.setPrec_prod_1(result.getBigDecimal("prec_prod_1"));
				prod.setcEAN(result.getString("codi_ean"));
				listProd.add(prod);
			}
			c.desconectar();
			return listProd;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

	/**
	 * Retorna um Produto por c�digo sem as cota��es
	 * 
	 * @param codiProduto
	 * @return Produto
	 * 
	 */
	public Produto procurar(String codiProduto) {
		prod = new Produto();
		c2.conectStm();
		result = c2.query("SELECT * FROM produtos WHERE codi_prod_1 ='"
				+ codiProduto + "';");
		c2.disconect();
		try {
			if (result.next()) {
				prod.setNome_prod(result.getString("nome_prod"));
				prod.setDesc_prod(result.getString("desc_prod"));
				prod.setDetalhes_prod(result.getString("detalhes_prod"));
				prod.setAliq_prod(result.getString("aliq_prod"));
				prod.setPrec_custo(result.getBigDecimal("prec_custo"));
				prod.setSeq_produto(result.getInt("seq_produto"));
				prod.setCodi_prod_1(result.getString("codi_prod_1"));
				prod.setPrec_prod_1(result.getBigDecimal("prec_prod_1"));
				prod.setcEAN(result.getString("codi_ean"));
				return prod;
			} else {
				return null;
			}
		} catch (SQLException e) {
			c2.disconect();
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Pesquisa se o produto existe
	 * 
	 * @param codiProduto
	 * @return Boolean
	 */
	public Boolean existe(String codiProduto) {
		prod = new Produto();
		c2.conectStm();
		result = c2.query("SELECT * FROM produtos WHERE codi_prod_1 ='"
				+ codiProduto + "';");
		c2.disconect();
		try {
			if (result.next()) {

				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			c2.disconect();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Reserva um c�digo para inserir o produto
	 * 
	 * @param codiProduto
	 * @throws SQLException
	 * 
	 */
	public void reservaCodigo(String codiProduto) throws SQLException {
		String sql = "insert into produtos (codi_prod_1) values (?)";
		c.conectar();
		prepStm = c.getCon().prepareStatement(sql);
		prepStm.setString(1, codiProduto);
		prepStm.executeUpdate();
		c.desconectar();
	}

	/**
	 * Busca o produto pelo codigo usando Statment
	 * 
	 * @param codiProd
	 * @return produto
	 * 
	 */
	public Produto procurarStmt(int codiProd) {
		prod = new Produto();
		c2.conectStm();
		result = c2
				.query("SELECT codiprod,nomeprod,descprod,aliqprod,quanprod,precprod,detalhes_prod FROM produtos WHERE codiprod='"
						+ codiProd + "';");
		c2.disconect();
		try {
			result.next();
			prod.setCodi_prod_1(result.getString("codi_prod"));
			prod.setNome_prod(result.getString("nome_prod"));
			prod.setDesc_prod(result.getString("desc_prod"));
			prod.setDetalhes_prod(result.getString("detalhes_prod"));
			prod.setAliq_prod(result.getString("aliq_prod"));
			prod.setPrec_prod_1(result.getBigDecimal("prec_prod_1"));
			prod.setSeq_produto(result.getInt("seq_produto"));
			prod.setcEAN(result.getString("codi_ean"));
			return prod;
		} catch (SQLException e) {
			c2.disconect();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lista todos os produtos da tabela
	 * 
	 * @return List<Produto>
	 * 
	 */
	public List<Produto> procurarTodos() {
		String sql = "select * from produtos order by nomeprod; ";
		listProd = new ArrayList<Produto>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				prod = new Produto();
				prod.setCodi_prod_1(res.getString("codi_prod"));
				prod.setNome_prod(res.getString("nome_prod"));
				prod.setDesc_prod(res.getString("desc_prod"));
				prod.setDetalhes_prod(result.getString("detalhes_prod"));
				prod.setAliq_prod(res.getString("aliq_prod"));
				prod.setPrec_prod_1(res.getBigDecimal("prec_prod_1"));
				prod.setSeq_produto(res.getInt("seq_produto"));
				prod.setcEAN(result.getString("codi_ean"));
				listProd.add(prod);
			}
			c.desconectar();
			return listProd;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lista todos os produtos da tabela utilizando Statement
	 * 
	 * @return List<Produto>
	 * 
	 */
	public List<Produto> procurarTodosStmt() {
		listProd = new ArrayList<Produto>();
		c2.conectSTMScroll();
		result = c2.queryScroll("SELECT * FROM produtos;");
		try {
			result.first();
			do {
				prod = new Produto();
				prod.setCodi_prod_1(result.getString("codi_prod_1"));
				prod.setNome_prod(result.getString("nome_prod"));
				prod.setDesc_prod(result.getString("desc_prod"));
				prod.setDetalhes_prod(result.getString("detalhes_prod"));
				prod.setAliq_prod(result.getString("aliq_prod"));
				// prod.setEstoque(result.getInt("quan_prod")); // resolver
				// estoque
				prod.setPrec_prod_1(result.getBigDecimal("prec_prod_1"));
				prod.setSeq_produto(result.getInt("seq_produto"));
				prod.setcEAN(result.getNString("codi_ean"));
				listProd.add(prod);
			} while (result.next());
			c2.disconect();
			return listProd;
		} catch (SQLException e) {
			c2.disconect();
			e.printStackTrace();
			return null;
		}
	}

	public Produto procurarAnteriorStmt(int codiProd) {
		prod = new Produto();
		c2.conectStm();
		result = c2
				.query("SELECT codiprod, nomeprod,descprod,aliqprod,quanprod,precprod,detalhes_prod FROM produtos WHERE codiprod="
						+ codiProd);
		c2.disconect();
		try {
			result.next();
			prod.setAliq_prod(result.getString("aliq_prod"));
			// prod.setEstoque(result.getInt("quan_prod"));
			prod.setPrec_prod_1(result.getBigDecimal("prec_prod"));
			prod.setCodi_prod_1(result.getString("codi_prod"));
			prod.setNome_prod(result.getString("nome_prod"));
			prod.setDesc_prod(result.getString("desc_prod"));
			prod.setDetalhes_prod(result.getString("detalhes_prod"));
			prod.setSeq_produto(result.getInt("seq_produto"));
			return prod;

		} catch (SQLException e) {
			c2.disconect();
			e.printStackTrace();
			return null;
		}
	}

	public Produto procurarProximoStmt(int codiProd) {
		prod = new Produto();
		c2.conectStm();
		result = c2.query("SELECT * FROM produtos WHERE codi_prod=" + codiProd);
		c2.disconect();
		try {
			result.next();
			prod.setCodi_prod_1(result.getString("codi_prod"));
			prod.setCodi_prod_2(result.getString("codi_prod_2"));
			prod.setNome_prod(result.getString("nome_prod"));
			prod.setDesc_prod(result.getString("desc_prod"));
			prod.setDetalhes_prod(result.getString("detalhes_prod"));
			prod.setAliq_prod(result.getString("aliq_prod"));
			prod.setPrec_custo(result.getBigDecimal("prec_custo real"));
			prod.setPrec_prod_1(result.getBigDecimal("prec_prod_1"));
			// prod.setPrec_prod_2(result.getBigDecimal("prec_prod_2"));
			prod.setSeq_produto(result.getInt("seq_produto"));
			return prod;
		} catch (SQLException e) {
			e.printStackTrace();
			c2.disconect();
			return null;
		}
	}

	/**
	 * M�todo retorna uma String com o c�digo nome da categoria
	 * 
	 * @param prod
	 * @return String
	 */
	public List<GrupoSubgrupo> carregarCategoriasProduto(Produto produto) {

		String sql = "SELECT produtos_grupos.codi_grupo "
				+ "FROM produtos_grupos "
				+ "WHERE produtos_grupos.codi_produto=?;";
		List<GrupoSubgrupo> listGrupo = new ArrayList<GrupoSubgrupo>();
		GrupoSubgrupo grupo;
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, produto.getCodi_prod_1());
			result = prepStm.executeQuery();
			while (result.next()) {
				grupo = new GrupoSubgrupo();
				grupo.setCodiGrupo(result.getString("codi_grupo"));
				grupo.setNomeGrupo(
						daoGrupo.pesquisarNomeCodigo(grupo.getCodiGrupo()));
				listGrupo.add(grupo);
			}
			c.desconectar();
			return listGrupo;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}

	}
	/**
	 * Adiciona uma tag para o produto recebendo os c�digos referentes
	 * 
	 * @param codi_prod_1
	 * @param codiCategoria
	 */
	public void cadastrarCategoria(String codi_prod_1, String codiCategoria) {
		String sql = "insert into produtos_grupos (codi_produto, codi_grupo) values (?,?)";
		try {
			c.conectar();
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, codi_prod_1);
			prepStm.setString(2, codiCategoria);
			prepStm.executeUpdate();
			c.desconectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			c.desconectar();
			e.printStackTrace();

		}

	}
	/**
	 * Remove uma tag para o produto recebendo o produto e o grupo a ser
	 * removido
	 * 
	 * @param Produto
	 * @param Grupo
	 */

	public void removerCategoria(Produto produto, GrupoSubgrupo grupo) {
		// TODO Auto-generated method stub
		String sql = "delete from produtos_grupos where codi_produto=? and codi_grupo=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, produto.getCodi_prod_1());
			prepStm.setString(2, grupo.getCodiGrupo());
			prepStm.executeUpdate();
			c.desconectar();

		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();

		}

	}
}
