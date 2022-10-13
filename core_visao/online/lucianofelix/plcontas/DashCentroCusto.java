package online.lucianofelix.plcontas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import online.lucianofelix.beans.CentroCusto;
import online.lucianofelix.controle.ControlaCentroCusto;
import online.lucianofelix.controle.ControlaConta;

public class DashCentroCusto extends JPanel {

	private JLabel lblImagem;
	private JLabel lbl01;
	private JLabel lbl02;
	private JLabel lbl03;
	private JLabel lbl04;
	private JLabel lbl05;

	private static JTextField txtF02;
	private static JTextField txtF03;
	private static JTextField txtF04;
	private static JTextField txtF05;
	private static JTextField txtF06;
	private static JTextField txtF07;
	private static JTextField txtF08;
	private static JTextField txtF09;

	private JSplitPane sppImagem;
	private JSplitPane sppPrincipal;
	private JSplitPane sppDetalhes;
	private JScrollPane scrImagem;
	private JPanel pnlInferior;
	private JPanel pnlSuperior;
	private JPanel painelGrid;
	private JPanel pnlPrincipal;
	private JTabbedPane tabSuperior;
	private JTabbedPane tabInferior;

	private JScrollPane scr1;
	private JScrollPane scrPContas;
	private JTable tblContas;

	private static CentroCusto centroCusto;
	private static ControlaCentroCusto contCentroCusto;

	public DashCentroCusto() {

		UIManager.put("TextField.font",
				new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font",
				new Font("Times New Roman", Font.BOLD, 12));
		setLayout(null);

		contCentroCusto = new ControlaCentroCusto();

		lbl01 = new JLabel("Centro de Custo");
		lbl01.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lbl02 = new JLabel("Sequencia:");
		txtF02 = new JTextField();
		lbl03 = new JLabel("Código :");
		txtF03 = new JTextField(0);
		lbl04 = new JLabel("Nome: ");
		txtF04 = new JTextField();
		lbl05 = new JLabel("Descrição : ");
		txtF05 = new JTextField();

		scrImagem = new JScrollPane(lblImagem);
		scrImagem.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrImagem.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrPContas = new JScrollPane();
		tblContas = new JTable();

		painelGrid = new JPanel();
		painelGrid.setBorder(BorderFactory.createEtchedBorder());
		painelGrid.setLayout(new GridLayout(8, 2));
		painelGrid.setBackground(Color.WHITE);
		painelGrid.add(lbl02);
		painelGrid.add(txtF02);
		painelGrid.add(lbl03);
		painelGrid.add(txtF03);
		painelGrid.add(lbl04);
		painelGrid.add(txtF04);
		painelGrid.add(lbl05);
		painelGrid.add(txtF05);

		sppImagem = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppImagem.add(lbl01);
		sppImagem.add(scrImagem);
		sppImagem.setDividerLocation(50);
		sppImagem.setEnabled(false);
		sppImagem.setBackground(Color.WHITE);
		sppImagem.setForeground(Color.WHITE);
		sppImagem.setDividerSize(3);

		sppDetalhes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppDetalhes.setDividerLocation(200);
		sppDetalhes.setDividerSize(3);
		sppDetalhes.setEnabled(false);
		sppDetalhes.add(sppImagem);
		sppDetalhes.add(painelGrid);

		tabSuperior = new JTabbedPane();
		tabSuperior.addTab("Dashboard", scr1);
		tabSuperior.addTab("Detalhes", sppDetalhes);

		tabInferior = new JTabbedPane();
		tabInferior.addTab("Dashboard", scr1);
		tabInferior.addTab("Contas", scrPContas);
		tabInferior.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabInferior.getSelectedIndex() == 0) {

				} else if (tabInferior.getSelectedIndex() == 1) {
					habilitaTblContas();
				}
			}
		});

		pnlInferior = new JPanel();
		pnlInferior.setBorder(BorderFactory.createEtchedBorder());
		pnlInferior.setLayout(new GridLayout());
		pnlInferior.setBackground(Color.WHITE);
		pnlInferior.add(tabInferior);

		pnlSuperior = new JPanel();
		pnlSuperior.setBorder(BorderFactory.createEtchedBorder());
		pnlSuperior.setLayout(new GridLayout());
		pnlSuperior.setBackground(Color.WHITE);
		pnlSuperior.add(tabSuperior);

		desHabilitaEdicao();

		sppPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppPrincipal.setDividerSize(3);
		sppPrincipal.setDividerLocation(250);
		sppPrincipal.setEnabled(false);
		sppPrincipal.setBackground(Color.WHITE);
		sppPrincipal.add(pnlSuperior);
		sppPrincipal.add(pnlInferior);
		setLayout(new GridLayout());
		setBackground(Color.WHITE);
		add(sppPrincipal);

	}

	public static CentroCusto lerCampos() {
		System.out.println("PanelCentroCusto.lerCampos");
		centroCusto = new CentroCusto();
		if (!txtF02.getText().equals("") & !txtF02.getText().equals(null)) {
			centroCusto.setSeqcentroCusto(Integer.parseInt(txtF02.getText()));
		}
		if (!txtF03.getText().equals("") & !txtF03.getText().equals(null)) {
			centroCusto.setCodiCentroCusto(txtF03.getText());
		} else {
			centroCusto.setCodiCentroCusto(contCentroCusto.criaCodigo());
		}
		if (!txtF04.getText().equals(null) & !txtF04.getText().equals("")) {
			centroCusto.setNomeCentroCusto(txtF04.getText());
		} else {
			JOptionPane.showMessageDialog(null,
					"Problemas: Verifique as informações preenchidas",
					"Erro ao Salvar. Campos com * são necessários",
					JOptionPane.ERROR_MESSAGE);
		}
		if (!txtF05.getText().equals(null) & !txtF05.getText().equals("")) {
			centroCusto.setDescCentroCusto(txtF05.getText());
		} else {
			centroCusto.setDescCentroCusto("");

		}
		return centroCusto;
	}

	// TODO carregar Campos
	public static void carregarCampos(CentroCusto centroCusto) {
		if (!centroCusto.equals(null)) {
			getTxtFSequencia()
					.setText(String.valueOf(centroCusto.getSeqcentroCusto()));
			getTxtFCodigo().setText(centroCusto.getCodiCentroCusto());
			getTxtFNome().setText(centroCusto.getNomeCentroCusto());
			getTxtFDescricao().setText(centroCusto.getDescCentroCusto());
		} else {
			limparCampos();
		}

	}

	// TODO Edição
	public static void habilitaEdicao() {
		txtF02.setEditable(false);
		txtF03.setEditable(false);
		txtF04.setEditable(true);
		txtF05.setEditable(true);

	}

	public static void desHabilitaEdicao() {
		txtF02.setEditable(false);
		txtF03.setEditable(false);
		txtF04.setEditable(false);
		txtF05.setEditable(false);
	}

	// TODO Limpar campos
	public static void limparCampos() {
		txtF02.setText(null);
		txtF03.setText(null);
		txtF04.setText(null);
		txtF05.setText(null);
	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();
		txtF02.setText("0");
		txtF03.setEditable(false);
		txtF03.setFocusable(false);
		txtF03.setText(contCentroCusto.criaCodigo());
		txtF04.setEditable(true);
		txtF04.grabFocus();
		txtF05.setEditable(true);

	}

	void habilitaTblContas() {
		ControlaConta contConta = new ControlaConta();
		tblContas = contConta
				.tblContasCentroCusto(lerCampos().getNomeCentroCusto());
		scrPContas.setViewportView(tblContas);
	}

	public JLabel getLblSequencia() {
		return lbl02;
	}

	public void setLblSequencia(JLabel lblSequencia) {
		this.lbl02 = lblSequencia;
	}

	public JLabel getLblCodigo() {
		return lbl03;
	}

	public void setLblCodigo(JLabel lblCodigo) {
		this.lbl03 = lblCodigo;
	}

	public JLabel getLblNomeGrupo() {
		return lbl04;
	}

	public void setLblNomeGrupo(JLabel lblNomeGrupo) {
		this.lbl04 = lblNomeGrupo;
	}

	public JLabel getLblDescricao() {
		return lbl05;
	}

	public void setLblDescricao(JLabel lblDescricao) {
		this.lbl05 = lblDescricao;
	}

	public static JTextField getTxtFCodigo() {
		return txtF03;
	}

	public static void setTxtFCodigo(JTextField txtFCodigo) {
		DashCentroCusto.txtF03 = txtFCodigo;
	}

	public static JTextField getTxtFNome() {
		return txtF04;
	}

	public static void setTxtFNome(JTextField txtFNome) {
		DashCentroCusto.txtF04 = txtFNome;
	}

	public static JTextField getTxtFDescricao() {
		return txtF05;
	}

	public static void setTxtFDescricao(JTextField txtFDescricao) {
		DashCentroCusto.txtF05 = txtFDescricao;
	}

	public static JTextField getTxtFSequencia() {
		return txtF02;
	}

	public static void setTxtFSequencia(JTextField txtFSequencia) {
		DashCentroCusto.txtF02 = txtFSequencia;
	}

}
