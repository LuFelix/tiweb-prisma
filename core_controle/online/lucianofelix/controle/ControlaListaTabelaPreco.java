package online.lucianofelix.controle;

import java.util.List;

import online.lucianofelix.beans.TabelaPreco;

public class ControlaListaTabelaPreco {
	private List<TabelaPreco> iterableList;
	private int currentPosition;
	private int collectionSize;

	public ControlaListaTabelaPreco(List<TabelaPreco> iterableList) {
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
	protected TabelaPreco getCollectionItemAt(int position) {
		return getIterableList().get(position);
	}

	// Recupera o primeiro item da minha cole��o.
	public TabelaPreco first() {
		return getCollectionItemAt(0);
	}

	// Recupera o último item da minha cole��o.
	public TabelaPreco last() {
		return getCollectionItemAt(this.collectionSize);
	}

	// Recupera o próximo item a partir da posi��o atual.
	public TabelaPreco next() {
		return getCollectionItemAt(getNextPosition());
	}

	// Recupera o item anterior a partir da posi��o atual.
	public TabelaPreco previous() {
		return getCollectionItemAt(getPreviousPosition());
	}

	// Recupera um item em qualquer posi��o.
	public TabelaPreco getAt(int position) {
		return getCollectionItemAt(position);

	}

	public List<TabelaPreco> getIterableList() {
		return iterableList;
	}

	public void setIterableList(List<TabelaPreco> iterableList) {
		this.iterableList = iterableList;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
}
