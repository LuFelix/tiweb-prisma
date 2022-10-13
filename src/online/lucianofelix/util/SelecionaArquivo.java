package online.lucianofelix.util;
import java.io.File;

import javax.swing.JFileChooser;
/**
 * 
 * @author Luciano de Oliveira Felix Silva
 * Esta classe tem o objetivo de trabalhar com as janelas de sele��o da classe JFileChooser retornando o arquivo
 * selecionado atrav�s da escolha. Lembrando que um arquivo ainda aqui � um endere�o do arquivo e n�o o arquivo 
 * propriamente. O arquivo ser� apontado para que as classes manipuladoras de arquivo, seja txt, xml, zip ou outro
 * tipo qualquer. Esta classe � uma interface entre a camada de Vis�o e Controle efetuando o di�logo com o usu�rio.
 *
 */


public class SelecionaArquivo {

	private JFileChooser  escolheLocal;
	private JFileChooser escolherArquivo;
	private String pasta = null; 
	private String nomArquivo = null;
	
	public String getPasta() {
		return pasta;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}

	
	public String getNomArquivo() {
		return nomArquivo;
	}

	public void setNomArquivo(String nomArquivo) {
		this.nomArquivo = nomArquivo;
	}
	
	
	// TODO +++++Seleciona o arquivo que deseja abrir manipulando as caixas de di�logo retornado o arquivo selecionado++++++++++++
	
	public File escolheArquivoAbrir(File arquivo){
			
			escolherArquivo = new JFileChooser();
			escolherArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
			escolherArquivo.showOpenDialog(escolherArquivo);
			arquivo = escolherArquivo.getSelectedFile();
			
			return arquivo;
			
	}
	
		

//-----------------------------Este m�todo retorna verdadeiro para a sele��o de aprovado o nome do arquivo setando
//								as vari�veis pasta e nomArquivo com os respectivos endere�os 
//
	
	boolean escolheLocalSalvar(SelecionaArquivo local){
		
		escolheLocal =  new JFileChooser();
		
		int opcao = escolheLocal.showSaveDialog(null);
		
		if (opcao == JFileChooser.APPROVE_OPTION) {
			pasta = escolheLocal.getSelectedFile().getParent();
			nomArquivo = escolheLocal.getSelectedFile().getName();
			return true;
			
		}
				
		return false;
	}
	
}
