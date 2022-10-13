package online.lucianofelix.simpro.alphavantage;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import online.lucianofelix.tableModels.fuse.TableModelStockUnit;

public class ExibeQuotes {
	ControlaQuotes cq;
	TimeSeriesResponse response;
	JTextArea jTextArea;
	JTable jTable;
	TableModelStockUnit tableModelStockUnit;
	JScrollPane jScrollPane;
	JPanel jPanelTabela;
	JFrame jFrame;
	JEditorPane jEditorPane;
	JTextPane jTextPane;

	void exibeQuotes() {
		try {
			// UIManager.setLookAndFeel(
			// new com.nilo.plaf.nimrod.NimRODLookAndFeel());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel(new Plastic3DLookAndFeel());

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				// System.out.println(info.getName());

				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

			// PlasticLookAndFeel.setHighContrastFocusColorsEnabled(true);
			UIManager.put("ScrollBar.is3DEnabled", Boolean.TRUE);

		} catch (

		Exception e)

		{
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		jFrame = new JFrame();
		JPanel jPanelPrincipal = new JPanel(new BorderLayout());
		JTextField jTextPesquisa = new JTextField();
		jTextPesquisa.addKeyListener(new KeyListener() {

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
				// TODO Auto-generated method stub

			}
		});;

		JButton jButtonDiario = new JButton("Diário");
		jButtonDiario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ativo = JOptionPane.showInputDialog(
						"Digite o ativo para a exibição das cotações");
				response = cq.daily(ativo);
				atuTela();

			}
		});

		JButton jButtonIntraday5MIN = new JButton("Intraday 5 MIN");
		jButtonIntraday5MIN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ativo = JOptionPane.showInputDialog(
						"Digite o ativo para a exibição das cotações");
				response = cq.intraDay5MIN(ativo);
				atuTela();

			}
		});
		JButton jButtonIntraday1MN = new JButton("Intraday 1 MIN");
		jButtonDiario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ativo = JOptionPane.showInputDialog(
						"Digite o ativo para a exibição das cotações");
				response = cq.intraDay(ativo);
				atuTela();

			}
		});
		JPanel jPanelControle = new JPanel(new GridLayout(3, 1));
		jPanelControle.add(jButtonIntraday5MIN);
		jPanelControle.add(jButtonDiario);
		jPanelControle.add(jButtonIntraday1MN);

		jTextPane = new JTextPane();
		jTextPane.setText("Atualize o sistema para carregar os dados...");
		jScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jPanelTabela = new JPanel(new BorderLayout());
		jPanelTabela.add(jTextPane, BorderLayout.NORTH);
		jPanelTabela.add(jScrollPane, BorderLayout.CENTER);
		jPanelPrincipal.add(jPanelControle, BorderLayout.WEST);
		jPanelPrincipal.add(jPanelTabela, BorderLayout.CENTER);

		jFrame.add(jPanelPrincipal);
		jFrame.setSize(480, 680);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);

	}

	void atuTela() {

		StyledDocument doc = jTextPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		jTextPane.setDocument(doc);

		String resposta = "Resultado para: "
				+ response.getMetaData().getSymbol() + " \n";
		resposta += " " + response.getMetaData().getInformation() + "\n";
		resposta += "Última atualização:  "
				+ response.getMetaData().getLastRefreshed() + "\n";
		jTextPane.setText(resposta);
		jTextPane.setEditable(false);
		jTextPane.setFont(new Font("Times New Roman", Font.BOLD, 18));

		tableModelStockUnit = new TableModelStockUnit(response.getStockUnits());
		jTable = new JTable(tableModelStockUnit);
		jTable.setFillsViewportHeight(true);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jTable.setShowGrid(false);
		jScrollPane.setViewportView(jTable);
		jFrame.pack();

	}
}
