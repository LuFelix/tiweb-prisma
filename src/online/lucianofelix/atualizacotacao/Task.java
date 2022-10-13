package online.lucianofelix.atualizacotacao;

public class Task extends Thread {

	private final long valorInicial;
	private final long valorFinal;
	private long total = 0;

	// m�todo construtor que receber� os par�metros da tarefa
	public Task(int valorInicial, int valorFinal) {
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
	}

	// m�todo que retorna o total calculado
	public long getTotal() {
		return total;
	}

	/*
	 * Este m�todo se faz necess�rio para que possamos dar start() na Thread e
	 * iniciar a tarefa em paralelo
	 */
	@Override
	public void run() {
		for (long i = valorInicial; i <= valorFinal; i++) {

			total += i;

		}
	}
	/**
	 * Exemplo de uso
	 */

	/*
	 * Task t1 = new Task(0, 1000); t1.setName("Tarefa1"); Task t2 = new
	 * Task(1001, 2000); t2.setName("Tarefa2"); Task t3 = new Task(2001, 3000);
	 * t3.setName("Tarefa3");
	 * 
	 * // inicia a execu��o paralela das tr�s tarefas, iniciando tr�s novas //
	 * threads no programa t1.start(); t2.start(); t3.start();
	 * 
	 * // aguarda a finaliza��o das tarefas try { t1.join(); t2.join();
	 * t3.join(); } catch (InterruptedException ex) { ex.printStackTrace(); }
	 * 
	 * 
	 * // Exibimos o somat�rio dos totalizadores de cada Thread
	 * System.out.println( "Total: " + (t1.getTotal() + t2.getTotal() +
	 * t3.getTotal()));
	 */
}