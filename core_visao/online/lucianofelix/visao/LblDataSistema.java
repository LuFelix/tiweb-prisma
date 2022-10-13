package online.lucianofelix.visao;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import online.lucianofelix.util.ThreadMostraHora;
public class LblDataSistema extends JLabel {

	public LblDataSistema() {
		setFont(new Font("Arial", Font.ITALIC, 12));
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		ThreadMostraHora t = new ThreadMostraHora(this);
		t.start();
	}

}
