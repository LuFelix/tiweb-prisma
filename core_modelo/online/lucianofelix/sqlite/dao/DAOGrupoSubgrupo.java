package online.lucianofelix.sqlite.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import online.lucianofelix.beans.GrupoSubgrupo;
import online.lucianofelix.dao.ConfigS;
import online.lucianofelix.util.Conexao;
import online.lucianofelix.util.ConexaoSTM;

public class DAOGrupoSubgrupo {
	private Conexao c;
	private ConexaoSTM c2;
	private ResultSet result;
	private PreparedStatement prepStm;
	private List<GrupoSubgrupo> listGrupo;
	private GrupoSubgrupo grupo;

	public DAOGrupoSubgrupo() {
		super();
		System.out.println("DAOGrupoSubgrupo.construtor");
		c = new Conexao(ConfigS.getBdSQLT(), "siacecf");
		c2 = new ConexaoSTM(ConfigS.getBdSQLT(), "siacecf");

	}

	public void reservaCodigo(String codigo) throws SQLException {
		// TODO Reserva um c�digo livre na tabela de grupos para cadastrar
		String sql = "insert into grupos (codi_grupo) values (?)";
		c.conectar();
		prepStm = c.getCon().prepareStatement(sql);
		prepStm.setString(1, codigo);
		prepStm.executeUpdate();
		c.desconectar();
	}

	public boolean cadastrar(GrupoSubgrupo grupo) {
		System.out.println("DAOGruposSubgrupo.cadastrar");
		String sql = "insert into grupos (codi_grupo, nome_grupo, codi_no_ancora, isroot, seq_tipo_sistema) values (?,?,?,?,?);";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, grupo.getCodiGrupo());
			prepStm.setString(2, grupo.getNomeGrupo());
			prepStm.setString(3, grupo.getNoAncora());
			prepStm.setBoolean(4, grupo.getIsroot());
			prepStm.setInt(5, grupo.getNoRaiz());
			prepStm.execute();
			c.desconectar();
			System.out.println("Inseriu");
			return true;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	public int consultaUltimo() {
		System.out.println("DAOGrupoSubgrupo.consultarUltimo");
		c2.conectStm();
		result = c2.query("SELECT max(seq_grupo) FROM grupos;");
		c2.disconect();
		try {
			if (result.next()) {
				return result.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String pesquisarNomeCodigo(String str) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select nome_grupo from grupos where codi_grupo = ? ;";

		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			ResultSet res = prepStm.executeQuery();
			if (res.next()) {
				String nomeGrupo = res.getString("nome_grupo");
				c.desconectar();
				return nomeGrupo;
			} else {
				c.desconectar();
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

	public int pesquisarSequenciaNome(String str) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select seq_grupo from grupos where nome_grupo = ? ;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			ResultSet res = prepStm.executeQuery();
			if (res.next()) {
				int seqGrupo = res.getInt("seq_grupo");
				c.desconectar();
				return seqGrupo;
			} else {
				c.desconectar();
				return 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return e.getErrorCode();
		}
	}
	public int pesquisarSeqTipoSistemaNome(String str) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select seq_tipo_sistema from tipos_sistema where nome_tipo_sistema = ? ;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			ResultSet res = prepStm.executeQuery();
			if (res.next()) {
				int seqGrupo = res.getInt("seq_tipo_sistema");
				c.desconectar();
				return seqGrupo;
			} else {
				c.desconectar();
				return 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return e.getErrorCode();
		}
	}

	public String pesquisarCodigoNome(String str) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select codi_grupo from grupos where nome_grupo = ? ;";

		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			ResultSet res = prepStm.executeQuery();
			if (res.next()) {
				String codiGrupo = res.getString("codi_grupo");
				c.desconectar();
				return codiGrupo;
			} else {
				c.desconectar();
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}
	public List<GrupoSubgrupo> pesquisarString(String str) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select * from grupos where codi_grupo ~* ? or nome_grupo ~* ? or codi_no_ancora ~* ? order by codi_no_ancora;";
		listGrupo = new ArrayList<GrupoSubgrupo>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			prepStm.setString(2, str);
			prepStm.setString(3, str);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				grupo = new GrupoSubgrupo();
				grupo.setSeqGrupo(res.getInt("seq_grupo"));
				grupo.setCodiGrupo(res.getString("codi_grupo"));
				grupo.setNomeGrupo(res.getString("nome_grupo"));
				grupo.setNoAncora(res.getString("codi_no_ancora"));
				grupo.setIsroot(res.getBoolean("isroot"));
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
	public List<GrupoSubgrupo> listPorGrupoRaiz(int grupoRaiz) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select * from grupos where seq_tipo_sistema = ? order by codi_no_ancora;";
		listGrupo = new ArrayList<GrupoSubgrupo>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setInt(1, grupoRaiz);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				grupo = new GrupoSubgrupo();
				grupo.setSeqGrupo(res.getInt("seq_grupo"));
				grupo.setCodiGrupo(res.getString("codi_grupo"));
				grupo.setNomeGrupo(res.getString("nome_grupo"));
				grupo.setNoAncora(res.getString("codi_no_ancora"));
				grupo.setIsroot(res.getBoolean("isroot"));
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
	 * Listar por Grupo �ncora
	 * 
	 * @param grupoAncora
	 * @return List
	 */
	public List<GrupoSubgrupo> listPorGrupoAncora(int grupoAncora) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select * from grupos where codi_no_ancora = ? order by nome_grupo;";
		listGrupo = new ArrayList<GrupoSubgrupo>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setInt(1, grupoAncora);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				grupo = new GrupoSubgrupo();
				grupo.setSeqGrupo(res.getInt("seq_grupo"));
				grupo.setCodiGrupo(res.getString("codi_grupo"));
				grupo.setNomeGrupo(res.getString("nome_grupo"));
				grupo.setNoAncora(res.getString("codi_no_ancora"));
				grupo.setIsroot(res.getBoolean("isroot"));
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
	 * Listar por Tipo de Sistema �ncora
	 * 
	 * @param grupoAncora
	 * @return List
	 */
	public List<GrupoSubgrupo> listPorTipoSistema(int tipoS) {
		System.out.println("DAOGrupo.pesquisarString");
		String sql = "select * from grupos where seq_tipo_sistema = ? order by nome_grupo;";
		listGrupo = new ArrayList<GrupoSubgrupo>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setInt(1, tipoS);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				grupo = new GrupoSubgrupo();
				grupo.setSeqGrupo(res.getInt("seq_grupo"));
				grupo.setCodiGrupo(res.getString("codi_grupo"));
				grupo.setNomeGrupo(res.getString("nome_grupo"));
				grupo.setNoAncora(res.getString("codi_no_ancora"));
				grupo.setIsroot(res.getBoolean("isroot"));
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
	public List<GrupoSubgrupo> listRaizes() {
		System.out.println("DAOGrupo.listRaizes");
		String sql = "select * from grupos where isroot = true order by nome_grupo;";
		listGrupo = new ArrayList<GrupoSubgrupo>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				grupo = new GrupoSubgrupo();
				grupo.setSeqGrupo(res.getInt("seq_grupo"));
				grupo.setCodiGrupo(res.getString("codi_grupo"));
				grupo.setNomeGrupo(res.getString("nome_grupo"));
				grupo.setNoAncora(res.getString("codi_no_ancora"));
				grupo.setIsroot(res.getBoolean("isroot"));
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

	public boolean alterar(GrupoSubgrupo grupo) {
		System.out.println("DAOGrupoSubgrupo.alterar");
		System.out.println(grupo.getIsroot());
		String sql = "update grupos set  nome_grupo=?, codi_no_ancora=?, isroot=? where codi_grupo=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, grupo.getNomeGrupo());
			prepStm.setString(2, grupo.getNoAncora());
			prepStm.setBoolean(3, grupo.getIsroot());
			prepStm.setString(4, grupo.getCodiGrupo());
			prepStm.executeUpdate();
			c.desconectar();

			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(GrupoSubgrupo grupo) {
		System.out.println("DAOGrupoSubgrupo.excluir");
		String sql = "delete from grupos where codi_grupo=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, grupo.getCodiGrupo());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

}
