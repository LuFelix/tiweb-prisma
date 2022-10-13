package online.lucianofelix.util;

import java.util.List;

public abstract class ListaObjetos {
	
	    private List<Object> iterableList;
	    private int currentPosition;
	    private int collectionSize;
	    // gets e sets omitidos.

	    public ListaObjetos(List<Object> iterableList) {
	        this.iterableList = iterableList;
	        this.collectionSize = iterableList.size();
	        this.currentPosition = 0;
	    }

	    // Anda um item para a frente.
	    public int getNextPosition() { 
	        setCurrentPosition(getCurrentPosition() + 1);
	        return getCurrentPosition();
	    }

	    // Anda um item para tr�s.
	    public int getPreviousPosition() { 
	        setCurrentPosition(getCurrentPosition() - 1);
	        return getCurrentPosition();
	    }

	    // Pega um item em uma determinada posi��o.
	    protected Object getCollectionItemAt(int position) {
	        return getIterableList().get(position);
	    }

	    // Recupera o primeiro item da minha cole��o.
	    public Object first() {
	        return getCollectionItemAt(0);
	    }

	    // Recupera o �ltimo item da minha cole��o.
	    public Object last() {
	        return getCollectionItemAt(this.collectionSize);
	    }

	    // Recupera o pr�ximo item a partir da posi��o atual.
	    public Object next() {
	        return getCollectionItemAt(getNextPosition());
	    }

	    // Recupera o item anterior a partir da posi��o atual.
	    public Object previous() {
	        return getCollectionItemAt(getPreviousPosition());
	    }

	    // Recupera um item em qualquer posi��o.
	    public Object getAt(int position) {
	        return getCollectionItemAt(position);
	   
	    }
	    public List<Object> getIterableList() {
			return iterableList;
		}

		public void setIterableList(List<Object> iterableList) {
			this.iterableList = iterableList;
		}

		public int getCurrentPosition() {
			return currentPosition;
		}

		public void setCurrentPosition(int currentPosition) {
			this.currentPosition = currentPosition;
		}
		
}
	/*
	 * 
		Um exemplo b�sico de Uso:
	 

		List<TipoDoObjeto> listaDoTipoDoObjeto = new ArrayList<>();
		
		No DAO preenche a lista
	
		while (rs.next()) {
		    
		    TipoDoObjeto t = new TipoDoObjeto();
		    t.setId(rs.getInt("id"));
		    t.setNome(rs.getString("nome"));
		    continua setando as propriedades do objeto de acordo com o campo na tabela
		    e adicionando 
		    listaDoTipoDoObjeto.add(t)

	// Chama o m�todo que retorna uma cole��o do TipodeObjeto.
	
	List<TipoDoObjeto> listaDoTipoDoObjeto = getColecaoDeTipoDoObjetodoBancoDeDados(); //Carrega a lista(Array) na clase de controle

	// Cria uma cole��o IterableList tipada com a classe doTipoDoObjeto, onde o par�metro � a lista de objetos criada do banco de dados.
	
	IterableList<TipoDoObjeto> tipoDoObjetoIterableList = new IterableList<TipoDoObjeto>(listaDoTipoDoObjeto);

	//Pego o primeiro s�cio.
	TipoDoObjeto first = tipoDoObjetoIterableList.first();

	//Pego o �ltimo s�cio.
	TipoDoObjeto last = tipoDoObjetoIterableList.last();

	//Pego o pr�ximo s�cio.
	TipoDoObjeto next = tipoDoObjetoIterableList.next();
	
	//Pego o s�cio anterior.
	TipoDoObjeto previous = sociosIterableList.previous();

	//Pego um s�cio qualquer s�cio.
	TipoDoObjeto any = tipoDoObjetoIterableList.getCollectionItemAt(5);

*/