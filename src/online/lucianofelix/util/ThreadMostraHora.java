package online.lucianofelix.util;

import javax.swing.JLabel;

public class ThreadMostraHora extends Thread {

	ManipulaData manData = new ManipulaData();
	String strDataHora;
	JLabel lblDataHora;
	public ThreadMostraHora(JLabel dataHora) {
		this.lblDataHora = dataHora;
		// TODO Auto-generated constructor stub
	}

	public void run() {

		while (true) {
			strDataHora = manData.diaDaSemana()
					+ manData.dataAgora().substring(7, 10)
					+ manData.dataAgora().substring(3, 7)
					+ manData.dataAgora().substring(10, 19);
			lblDataHora.setText(strDataHora);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}

		}
	}

}
