package online.lucianofelix.controle;

import java.util.List;

import online.lucianofelix.beans.Servico;

public class ControlaListaServicos {

	private List<Servico> iterableList;
	private int currentPosition;
	private int collectionSize;

	public ControlaListaServicos(List<Servico> iterableList) {
		super();
		this.iterableList = iterableList;
		this.currentPosition = currentPosition;
		this.collectionSize = collectionSize;
	}

	// Anda um item para a frente.
	public int getNextPosition() {
		setCurrentPosition(getCurrentPosition() + 1);
		return getCurrentPosition();
	}

	// Anda um �tem para tr�s.
	public int getPreviousPosition() {
		setCurrentPosition(getCurrentPosition() - 1);
		return getCurrentPosition();
	}

	// Pega um item em uma determinada posi��o.
	protected Servico getCollectionItemAt(int position) {
		return getIterableList().get(position);
	}

	// Recupera o primeiro item da minha cole��o.
	public Servico first() {
		return getCollectionItemAt(0);
	}

	// Recupera o último item da minha cole��o.
	public Servico last() {
		return getCollectionItemAt(this.collectionSize);
	}

	// Recupera o próximo item a partir da posi��o atual.
	public Servico next() {
		return getCollectionItemAt(getNextPosition());
	}

	// Recupera o item anterior a partir da posi��o atual.
	public Servico previous() {
		return getCollectionItemAt(getPreviousPosition());
	}

	// Recupera um item em qualquer posi��o.
	public Servico getAt(int position) {
		return getCollectionItemAt(position);

	}

	public List<Servico> getIterableList() {
		return iterableList;
	}

	public void setIterableList(List<Servico> iterableList) {
		this.iterableList = iterableList;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
}
