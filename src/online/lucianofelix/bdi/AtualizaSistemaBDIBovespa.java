package online.lucianofelix.bdi;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import online.lucianofelix.beans.fuse.Ativo;
import online.lucianofelix.beans.fuse.CotacaoAtivo;
import online.lucianofelix.dao.DAOAtivo;
import online.lucianofelix.dao.DAOCotacaoAtivo;
import online.lucianofelix.fuse.ControlaAtivo;
import online.lucianofelix.util.BaixarArquivos;
import online.lucianofelix.util.ManipulaArquivoZip;
import online.lucianofelix.util.ManipulaData;
import online.lucianofelix.visao.FrameInicial;

public class AtualizaSistemaBDIBovespa extends Thread {
	private ControlaAtivo contAtiv;
	private ManipulaBdi manBdi;
	private DAOAtivo daoAtivo;
	private DAOCotacaoAtivo daoCotaBov;
	private ManipulaData manData;
	private String strData;
	private String stringUrl;
	private ArrayList<Ativo> arrayAtivo;
	private File input, output;
	private File bdi;
	private Ativo atv;
	private ArrayList<String> colunas;
	private JTable tabela1;
	private DefaultTableModel modelotabela;
	private List<String> listData;

	public AtualizaSistemaBDIBovespa() {
		manData = new ManipulaData();
		manBdi = new ManipulaBdi();
		daoCotaBov = new DAOCotacaoAtivo();
	}

	@Override
	public void run() {
		super.run();
		atualizaBancoManual();
	}

	/**
	 * Lê um arquivo bdi identificando os ativos nele contidos e carrega uma
	 * tabela no banco de dados contendo os ativos encontrados.
	 * 
	 * @throws Exception
	 */
	public void carregarBancoAtivos() throws Exception {
		output = new File("C:\\SIMPRO\\bdi");
		manBdi = new ManipulaBdi();
		daoAtivo = new DAOAtivo();
		arrayAtivo = new ArrayList<>();
		File[] arrayArq = output.listFiles();
		for (int i = 0; i < arrayArq[i].length(); i++) {
			if (daoAtivo.tabelaVazia()) {
				ManipulaArquivoZip.extract(arrayArq[i], output);
				bdi = new File("C:\\SIMPRO\\bdi\\BDIN");
				arrayAtivo = manBdi.bdiparaArrayAtivos(bdi);
				bdi.delete();
				for (int j = 0; j < arrayAtivo.size(); j++) {
					atv = new Ativo();
					atv = arrayAtivo.get(j);
					atv.setIdNeg(atv.getIdNeg().trim());

					System.out.println("Carregando:---" + atv.getIdNeg() + "--"
							+ atv.getNomeRes() + "--");

					if ((atv.getTpMerc() == 10) | (atv.getTpMerc() == 20)) {
						daoAtivo.inserir(atv);

					}
				}
			}
		}
	}

	/**
	 * Datas dos bdis disponíveis na pasta
	 * 
	 * @return JTable
	 */
	public JTable datasDisponiveisPasta() {
		colunas = new ArrayList<String>();
		colunas.add("Datas na Pasta");
		tabela1 = new JTable();
		modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela1.getModel();
		modelotabela.setColumnIdentifiers(colunas.toArray());
		File output = new File("C:\\SIMPRO\\bdi");
		ManipulaBdi manBdi = new ManipulaBdi();
		File[] arrayArq = output.listFiles();
		System.out.println("AtualizaSistemaBovespa.atualizaCotacao");
		System.out
				.println("Quantidade de Arquivos na pasta " + arrayArq.length);
		File input = null;
		File bdi = null;
		for (int i = 0; i < arrayArq.length; i++) {
			try {
				input = new File("C:\\SIMPRO\\bdi\\" + arrayArq[i].getName());
				ManipulaArquivoZip.extract(input, output);
				System.out.println("BDI: " + arrayArq[i].getName());
				bdi = new File("C:\\SIMPRO\\bdi\\BDIN");
				Object linha[] = {manBdi.datBdi(bdi)};
				modelotabela.addRow(linha);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Erro de acesso aos arquivos:\n" + e.getMessage(),
						"Erro!", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		bdi.delete();
		tabela1.setShowHorizontalLines(true);
		tabela1.setModel(modelotabela);
		return tabela1;

	}

	/**
	 * TODO Lista as datas de cotações disponíveis no banco
	 * 
	 * @return JTable
	 */
	public JTable datasDisponiveisDB() {
		colunas = new ArrayList<String>();
		tabela1 = new JTable();
		colunas.add("Datas no Postgres");
		modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela1.getModel();
		modelotabela.setColumnIdentifiers(colunas.toArray());
		listData = daoCotaBov.datasDisponiveisDB();
		for (int i = 0; i < listData.size(); i++) {
			Object linha[] = {listData.get(i).substring(0, 10)};
			modelotabela.addRow(linha);
		}
		tabela1.setShowHorizontalLines(true);
		tabela1.setModel(modelotabela);
		return tabela1;
	}

	/**
	 * Carrega as cotações dos bdis a partir de uma pasta com os arquivos para a
	 * tabela do banco de dados
	 * 
	 * @throws Exception
	 */
	public void carregarPastaCompletaDB() throws Exception {
		System.out.println("AtualizaSistemaBovespa.carregarPastaDB");
		arrayAtivo = new ArrayList<Ativo>();
		output = new File("C:\\SIMPRO\\bdi");
		Ativo atv;
		File[] arrayArq = output.listFiles();
		System.out
				.println("Quantidade de Arquivos na pasta " + arrayArq.length);
		for (int i = 0; i < arrayArq.length; i++) {
			input = new File("C:\\SIMPRO\\bdi\\" + arrayArq[i].getName());
			System.out.println("BDI: " + arrayArq[i].getName());
			ManipulaArquivoZip.extract(input, output);
			bdi = new File("C:\\SIMPRO\\bdi\\BDIN");
			bdi.setReadOnly();
			arrayAtivo = manBdi.bdiparaArrayAtivos(bdi);
			System.out.println();
			System.out.println(
					"+++++++++++++++++Atualizando cotações para a data: "
							+ manBdi.datBdi(bdi)
							+ "++++++++++++++++++++++++++++++++");
			System.out.println();
			for (int j = 0; j < arrayAtivo.size(); j++) {
				atv = new Ativo();
				atv = arrayAtivo.get(j);
				atv.setIdNeg(atv.getIdNeg().trim());
				if ((atv.getTpMerc() == 10) | (atv.getTpMerc() == 20)) {
					System.out
							.println("..." + atv.getIdNeg() + "--- Valores... "
									+ atv.getCotacao().getDataCotacao() + "--"
									+ atv.getCotacao().getPreAbe() + "--"
									+ atv.getCotacao().getPreFec() + "--"
									+ atv.getCotacao().getPreMax() + "--"
									+ atv.getCotacao().getPreMin() + "--"
									+ atv.getCotacao().getVolumeNeg() + "--"
									+ atv.getCotacao().getQuaToTit() + "--"
									+ atv.getCotacao().getTotNeg());
					daoCotaBov.inserir(new CotacaoAtivo(atv.getIdNeg(),
							atv.getCotacao().getDataCotacao(),
							atv.getCotacao().getPreAbe(),
							atv.getCotacao().getPreFec(),
							atv.getCotacao().getPreMax(),
							atv.getCotacao().getPreMin(),
							atv.getCotacao().getVolumeNeg(),
							atv.getCotacao().getQuaToTit(),
							atv.getCotacao().getTotNeg()));
				}
			}
		}
		System.out.println("...Atualizção encerrada...");
	}

	/**
	 * Recebe o dia e o mês para carregar as cotações no banco a partir da pasta
	 * com os bdis
	 * 
	 * @param mesDia
	 *            MMDD
	 * @return boolean
	 */
	public boolean atualizaBancoManual() {
		System.out.println("AtualizaSistemaBdiBovespa.atualizaBancoManual");
		String diaMes = JOptionPane
				.showInputDialog("Informe a data para atualização - DD/MM");
		output = new File("C:\\SIMPRO\\bdi");
		System.out.println("BDI==>  " + "bdi" + manData.inverteData9(diaMes));
		input = new File(
				"C:\\SIMPRO\\bdi\\bdi" + manData.inverteData9(diaMes) + ".zip");
		System.out.println(input.exists());
		try {
			ManipulaArquivoZip.extract(input, output);
			bdi = new File("C:\\SIMPRO\\bdi\\BDIN");
			bdi.setReadOnly();
			arrayAtivo = manBdi.bdiparaArrayAtivos(bdi);
			JTextArea areaAtu = new JTextArea();
			areaAtu.append("+++++++++++++++++Atualizando cotações para a data: "
					+ manBdi.datBdi(bdi)
					+ "++++++++++++++++++++++++++++++++\n\n");
			System.out.println();

			for (int j = 0; j < arrayAtivo.size(); j++) {
				FrameInicial.getScrFluxo().setViewportView(areaAtu);
				atv = new Ativo();
				atv = arrayAtivo.get(j);
				atv.setIdNeg(atv.getIdNeg().trim());

				Thread.sleep(15);
				if ((atv.getTpMerc() == 10) | (atv.getTpMerc() == 20)) {
					areaAtu.append("..." + atv.getIdNeg() + "--- Valores... "
							+ atv.getCotacao().getDataCotacao() + "--"
							+ atv.getCotacao().getPreAbe() + "--"
							+ atv.getCotacao().getPreFec() + "--"
							+ atv.getCotacao().getPreMax() + "--"
							+ atv.getCotacao().getPreMin() + "--"
							+ atv.getCotacao().getVolumeNeg() + "--"
							+ atv.getCotacao().getQuaToTit() + "--"
							+ atv.getCotacao().getTotNeg() + "\n");
					areaAtu.setCaretPosition(areaAtu.getDocument().getLength());

					daoCotaBov.inserir(new CotacaoAtivo(atv.getIdNeg(),
							atv.getCotacao().getDataCotacao(),
							atv.getCotacao().getPreAbe(),
							atv.getCotacao().getPreFec(),
							atv.getCotacao().getPreMax(),
							atv.getCotacao().getPreMin(),
							atv.getCotacao().getVolumeNeg(),
							atv.getCotacao().getQuaToTit(),
							atv.getCotacao().getTotNeg()));

				}
			}
			JOptionPane.showMessageDialog(null, "Atualização encerrada",
					"Atualizadopara a data: " + manData.inverteData9(diaMes),
					JOptionPane.INFORMATION_MESSAGE);
			FrameInicial.getScrLista().setViewportView(datasSistemaFuse());
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Não conseguiu acesso ao arquivo!", "Erro de leitura",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Retorna um jpanel contendo as datas do banco e da pasta do sistema para
	 * conferência visual.
	 * 
	 * @return JPanel
	 */
	public JPanel datasSistemaFuse() {
		JPanel pnlDatasCotacoes = new JPanel(new GridLayout(0, 3));
		Dimension d = new Dimension(150, 509);
		JScrollPane scr1 = new JScrollPane(datasDisponiveisPasta());
		JScrollPane scr2 = new JScrollPane(datasDisponiveisDB());
		pnlDatasCotacoes.setPreferredSize(d);
		pnlDatasCotacoes.add(scr1);
		pnlDatasCotacoes.add(scr2);
		return pnlDatasCotacoes;
	}

	/**
	 * Carregar a data do sistema e verificar para os 20 movimentos anteriores
	 * pulando os dias sem movimento.
	 * 
	 * @throws Exception
	 */

	/**
	 * verifica se as datas do banco estão sincronizadas com as da pasta do
	 * sistema
	 * 
	 * @return boolean
	 */
	public boolean verificaSicronismoPastaBancoBDI() {

		return false;

	}

	/**
	 * Desenvolver uma forma de capturar as datas
	 * 
	 * @throws Exception
	 */
	public void baixarBdiWebAut() throws Exception {
		manData = new ManipulaData();
		String pathLocal = "C:\\SIMPRO\\bdi\\bdi";
		manData.setStrData("");
		System.out.println("Data: " + getStrData());

		setStringUrl("http://www.bmfbovespa.com.br/fechamento-pregao/bdi/bdi"
				+ getStrData() + ".zip");

		BaixarArquivos baixar = new BaixarArquivos();
		baixar.gravaArquivoDeURL(getStringUrl(), pathLocal, "");
	}

	/**
	 * Classe que baixar bdi para a pasta do sistema inserindo a data
	 * manualmente.
	 * 
	 * link atualizado em 15-12-2016
	 * http://bvmf.bmfbovespa.com.br/fechamento-pregao/bdi/bdi1214.zip
	 * 
	 * 28-01-2017 link cotações
	 * anuais----------------------------------------AAAA
	 * http://bvmf.bmfbovespa.com.br/InstDados/SerHist/COTAHIST_A2015.ZIP
	 * 
	 * @param mesDia
	 * @return boolean
	 * @throws Exception
	 * 
	 */

	public boolean baixarBdiWebManual(String mesDia) throws Exception {
		System.out.println("AtualizaSistemaBdibovespa.baixarBdiWebManual");
		manData = new ManipulaData();
		String pathLocal = "C:\\SIMPRO\\bdi\\bdi";
		manData.setStrData(mesDia);
		setStringUrl("http://bvmf.bmfbovespa.com.br/fechamento-pregao/bdi/bdi"
				+ mesDia + ".zip");
		BaixarArquivos baixar = new BaixarArquivos();
		baixar.gravaArquivoDeURL(getStringUrl(), pathLocal,
				getStringUrl().substring(55, 59) + ".zip");
		return true;
	}
	/**
	 * 
	 * @param mesDiaAno
	 *            'MM-DD-AAAA'
	 * @return
	 * @throws Exception
	 *             JSON
	 *             https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?%40dataCotacao=%2702-18-2022%27&$format=json
	 *             CSV
	 *             https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?%40dataCotacao='02-18-2022'&%24format=text%2Fcsv
	 * 
	 */
	public boolean baixarDolarWebManualBACEM(String mesDiaAno)
			throws Exception {
		System.out
				.println("AtualizaSistemaBdibovespa.baixarDolarWebManualBACEM");
		String pathLocal = "C:\\SIMPRO\\dolar";
		setStringUrl(
				"https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?%40dataCotacao='"
						+ mesDiaAno + "'&%24format=text%2Fcsv");
		BaixarArquivos baixar = new BaixarArquivos();
		baixar.gravaArquivoDeURL(getStringUrl(), pathLocal,
				getStringUrl().substring(61, 77) + ".csv");

		return true;
	}

	public boolean baixarDolarWebManualBACEMPeriodo(String mesDiaAnoINICIAL,
			String mesDiaAnoFINAL) throws Exception {
		System.out
				.println("AtualizaSistemaBdibovespa.baixarDolarWebManualBACEM");
		String pathLocal = "C:\\SIMPRO\\dolar";
		setStringUrl(
				"https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)?%40dataInicial='"
						+ mesDiaAnoINICIAL + "'&%40dataFinalCotacao='"
						+ mesDiaAnoFINAL + "'&%24format=text%2Fcsv");
		BaixarArquivos baixar = new BaixarArquivos();
		baixar.gravaArquivoDeURL(getStringUrl(), pathLocal,
				getStringUrl().substring(61, 77) + ".csv");

		return true;
	}

	/**
	 * Verifica se o arquivo existe na url
	 * 
	 * @param URLName
	 * @return boolean
	 * 
	 *         http://bvmf.bmfbovespa.com.br/fechamento-pregao/bdi/bdiMMDD.zip
	 * 
	 *         https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/documentacao
	 *         https://dadosabertos.bcb.gov.br/dataset/dolar-americano-usd-todos-os-boletins-diarios
	 * 
	 *         olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/moedas
	 * 
	 */

	public static int exists(String URLName) {
		URLName = "http://bvmf.bmfbovespa.com.br/fechamento-pregao/bdi/bdi1210.zip";
		try {
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
					.openConnection();
			con.setRequestMethod("HEAD");
			int status = con.getResponseCode(); // == HttpURLConnection.HTTP_OK
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Criar uma maneira de manter o sistema atualizado.
	 */
	public void atualizacaoResidente() {

	}
	public boolean baixarBdiAnual(String ano) throws Exception {
		System.out.println("AtualizaSistemaBdibovespa.baixarBdiWebManual");

		String pathLocal = "C:\\SIMPRO\\anos\\";
		setStringUrl(
				"http://bvmf.bmfbovespa.com.br/InstDados/SerHist/COTAHIST_A"
						+ ano + ".zip");
		BaixarArquivos baixar = new BaixarArquivos();
		baixar.gravaArquivoDeURL(getStringUrl(), pathLocal, ano + ".zip");
		return true;
	}

	public String getStrData() {
		return strData;
	}

	public String getStringUrl() {
		return stringUrl;
	}

	public void setStringUrl(String stringUrl) {
		this.stringUrl = stringUrl;
	}

}
