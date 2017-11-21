package tp2.src.structure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class KnowledgeBase {
	protected FactBase facts;
	protected RuleBase rules;
	protected FactBase factssat;


	public KnowledgeBase() {
		facts = new FactBase();
		rules = new RuleBase();
		factssat = new FactBase();
	}

	public KnowledgeBase(String fic) throws IOException {

		System.out.println("Chargement du fichier : " + fic);
		BufferedReader readFile;
		System.out.println("Debut de la lecture du fichier ! ");
		readFile = new BufferedReader(new FileReader (fic));
		String fact;
		String rule = "aa";
		Rule r;
		System.out.println("Lecture de la 1ere ligne : les faits");
		fact = readFile.readLine();
		facts= new FactBase(fact);
		rules = new RuleBase();
		while (rule != null) {
			System.out.println("Lecture de la prochaine règle");
			rule = readFile.readLine();
			if (rule == null || rule == "aa") {
				rule = null;}
			else {
				r=new Rule(rule);
				rules.addRule(r);}
		}

		factssat = new FactBase();
	}

	public void ForwardChaining(){
		ArrayList<Atom> atraiter = new ArrayList<Atom>(facts.getAtoms());
		Hashtable compteur = new Hashtable();
		for (int i=0; i<rules.size();i++) {
			compteur.put(rules.getRule(i), rules.getRule(i).getHypothesis().size());
		}

		while (!atraiter.isEmpty()) {
			Atom temp = atraiter.remove(0);
			for (int i=0; i<rules.size();i++) {
				if (rules.getRule(i).getHypothesis().contains(temp))
				{
					compteur.replace(rules.getRule(i),(int)compteur.get(rules.getRule(i)) - 1);
					if ((int)compteur.get(rules.getRule(i)) == 0) {
						Atom c=rules.getRule(i).getConclusion();
							if (!facts.getAtoms().contains(c) && !atraiter.contains(c)) {
								factssat.addAtomWithoutCheck(c);
								atraiter.add(c);
							}
					}
				}
			}
		}
	}


	public FactBase getFacts()
	{
		return facts;
	}

	public FactBase getFactsSat()
	{
		return factssat;
	}

	public RuleBase getRules()
	{
		return rules;
	}


	public String toString() {
		return "Knowledge Base :\n"+facts.toString()+"\n"+rules.toString()+"\n"+factssat.toString();
	}
}
