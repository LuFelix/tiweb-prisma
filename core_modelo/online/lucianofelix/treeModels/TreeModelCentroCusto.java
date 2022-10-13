package online.lucianofelix.treeModels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import online.lucianofelix.beans.CentroCusto;

public class TreeModelCentroCusto implements TreeModel {
	// Criamos um objeto para nossa raiz. O List n�o pode ser usado diretamente
	// pois
	// seu hash ir� mudar sempre que um novo livro for adicionado.
	// Fora que � mais bonitinho escrever "Livros" no topo.
	private String raiz = "Centros de Custo";
	// Escrevam depois AbstractTreeModel para lidar com os listeners.
	private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	// Raiz da nossa �rvore, vamos exibir uma lista de livros.
	private List<CentroCusto> listCCusto;

	public TreeModelCentroCusto(List<CentroCusto> listCCusto) {
		this.listCCusto = listCCusto;
	}
	/**
	 * Com esse m�todo, o Java quem � o objeto que est� num determinado �ndice
	 * do pai. Cada n� de uma �rvore pode ser encarado como uma lista. Sendo o
	 * pai a lista e o �ndice um dos filhos.
	 * 
	 * @param parent
	 *            � o pai, que tem os filhos. No caso do Livro, o pr�prio livro.
	 * @param index
	 *            �ndice do filho. No caso do livro, o �ndice corresponde aos
	 *            autores.
	 */
	public Object getChild(Object parent, int index) {
		if (parent == raiz) // � o n� principal?
			return listCCusto.get(index); // Pegamos da
											// lista de
											// livro
		if (parent instanceof CentroCusto) // O pai � um livro?
		{
			// Devolvemos um autor
			return ((CentroCusto) parent).getListContas().get(index);

		}
		// Se o pai n�o � nenhum desses. Melhor dar erro.
		throw new IllegalArgumentException(
				"Invalid parent class" + parent.getClass().getSimpleName());
	}
	/**
	 * Retornamos quantos filhos um pai tem. No caso de um livro, � a contagem
	 * de autores. No caso da lista de livros, � a quantidade de livros.
	 */
	public int getChildCount(Object parent) {
		// Mesma l�gica.
		if (parent == raiz) {

			return listCCusto.size();
		}
		if (parent instanceof CentroCusto) { // O pai � um Centroce Custo?

			return ((CentroCusto) parent).getListContas().size();
		}
		// Se o pai n�o � nenhum desses. Melhor dar erro.
		throw new IllegalArgumentException(
				"Invalid parent class " + parent.getClass().getSimpleName());

	}
	/**
	 * Dado um pai, indicamos qual � o �ndice do filho correspondente.
	 */
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == raiz)
			return listCCusto.indexOf(child);
		if (parent instanceof CentroCusto)
			return ((CentroCusto) parent).getListContas().indexOf(child);
		return 0;
	}
	/**
	 * Devemos retornar quem � o n� raiz da �rvore. Afinal, a �rvore tem que
	 * come�ar em algum lugar.
	 */
	public Object getRoot() {
		return raiz;
	}
	/**
	 * Indicamos se um n� � ou n�o uma folha. Isso �, se ele n�o tem filhos. No
	 * nosso caso, os autores s�o as folhas da �rvore.
	 */
	public boolean isLeaf(Object node) {
		return node instanceof CentroCusto;
	}
	public void valueForPathChanged(TreePath path, Object newValue) {
		// Com esse m�todo, a tree avisa que um objeto mudou.
		// Editem se quiserem que um n� seja edit�vel
	}
	// Esses dois m�todos abaixo poderiam ir para classe abstrata
	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}

}
