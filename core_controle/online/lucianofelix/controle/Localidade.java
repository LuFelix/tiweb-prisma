package online.lucianofelix.controle;

import java.util.ArrayList;

public class Localidade {

	ArrayList <String> loc;	

	public ArrayList <String> localidade(){
		loc = new ArrayList<>();
		loc.add("Selecione");
		loc.add("Acre");
		loc.add("Alagoas");
		loc.add("Para�ba");
		loc.add("Bahia");
		loc.add("Pernambuco");
		loc.add("Cear�");
		loc.add("Maranh�o");
		return loc;
		
		
		
	}
}
