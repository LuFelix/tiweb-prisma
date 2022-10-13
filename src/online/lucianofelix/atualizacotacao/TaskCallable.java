package online.lucianofelix.atualizacotacao;
import java.util.concurrent.Callable;
public class TaskCallable implements Callable<String> {

	private final long tempoDeEspera;

	public TaskCallable(int time) {
		this.tempoDeEspera = time;
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(tempoDeEspera);
		return Thread.currentThread().getName();
	}
}
