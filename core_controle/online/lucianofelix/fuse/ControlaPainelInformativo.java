package online.lucianofelix.fuse;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class ControlaPainelInformativo {

	private JTextPane jTextPaneInforma;
	private JPanel jPanelInforma;
	private StyledDocument styledHtmlDoc;
	public ControlaPainelInformativo() {

	}

	public void carregaInformaçoes() {

	}

	public JPanel painelInforma() {

		String url = "https://www.google.com";

		String text = "<a href=\"abc\">https://www.google.com/finance/</a>";

		jTextPaneInforma = new JTextPane();
		jTextPaneInforma.setContentType("text/html");
		jTextPaneInforma.setEditable(false);
		jTextPaneInforma.setFont(new Font("Times New Roman", Font.BOLD, 18));
		jTextPaneInforma.setText(
				"Aqui estará painel de informação com uma aba disponível \npara visualizar o gráfico");
		styledHtmlDoc = jTextPaneInforma.getStyledDocument();
		HTMLDocument docHtml = (HTMLDocument) styledHtmlDoc;
		HTMLEditorKit editorKit = (HTMLEditorKit) jTextPaneInforma
				.getEditorKit();
		try {
			editorKit.insertHTML(docHtml, docHtml.getLength(), text, 0, 0,
					null);
			jTextPaneInforma.setPage(url);
		} catch (Exception e) {
			// TODO: handle exception
		}
		jPanelInforma = new JPanel(new BorderLayout());
		jPanelInforma.add(jTextPaneInforma, BorderLayout.CENTER);
		return jPanelInforma;
	}

	public JPanel painelSite(String url) {

		url = "<a href=\"abc\">https://www.google.com/finance/</a>";

		StyledDocument styledHtmlDoc = jTextPaneInforma.getStyledDocument();
		HTMLDocument docHtml = (HTMLDocument) styledHtmlDoc;
		HTMLEditorKit editorKit = (HTMLEditorKit) jTextPaneInforma
				.getEditorKit();

		try {
			editorKit.insertHTML(docHtml, docHtml.getLength(), url, 0, 0, null);
			jTextPaneInforma.setPage(url);
		} catch (Exception e) {
			// TODO: handle exception
		}

		jPanelInforma = new JPanel(new BorderLayout());
		jPanelInforma.add(jTextPaneInforma, BorderLayout.CENTER);

		return jPanelInforma;

	}

}
