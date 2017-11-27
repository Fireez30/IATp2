
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
			System.out.println("Lecture de la prochaine regle");
			rule = readFile.readLine();
			if (rule == null || rule == "aa") 
				rule = null;
			else {
				r=new Rule(rule);
				rules.addRule(r);
			}
		}

		factssat = new FactBase(fact);
	}

	public void ForwardChaining(){
		ArrayList<Atom> atraiter = new ArrayList<Atom>(facts.getAtoms());
		factssat = facts;
		Hashtable compteur = new Hashtable();

		for (int i = 0; i < rules.size(); i++) 
			compteur.put(rules.getRule(i), rules.getRule(i).getHypothesis().size());

		while (!atraiter.isEmpty()) {
			Atom temp = atraiter.remove(0);

			for (int i = 0; i < rules.size(); i++) {

				for (int j = 0; j < rules.getRule(i).getHypothesis().size(); ++j)
					if (rules.getRule(i).getHypothesis().get(j).equalsA(temp)) {					
						compteur.replace(rules.getRule(i),(int) compteur.get(rules.getRule(i)) - 1);

						if ((int) compteur.get(rules.getRule(i)) == 0) {
							Atom c = rules.getRule(i).getConclusion();

							boolean flag_in = false;

							for (int k = 0; k < factssat.getAtoms().size(); ++k)
								for (int l = 0; l < atraiter.size(); ++l)
									if (!factssat.getAtoms().get(k).equalsA(c) && !atraiter.get(l).equalsA(c)) 
										continue;
									else
										flag_in = true;

							if (!flag_in) {
								factssat.addAtomWithoutCheck(c);
								atraiter.add(c);
							}
						}
					}
			}
		}
	}

	public boolean BackwardChaining(Atom q, ArrayList<Atom> l) {
		if (facts.belongsAtom(q)) return true;

		for(int i = 0; i < rules.size(); i++) {
			for(int j = 0; j < rules.getRule(i).getHypothesis().size(); j++) {

				boolean flag_none = true;

				for (int k = 0; k < l.size(); k++)					
					if (l.get(k).equalsA(rules.getRule(i).getHypothesis().get(j))) flag_none = false;

				if (flag_none) {
					int z = 1;

					ArrayList<Atom> lClone = new ArrayList<Atom>(l); 

					lClone.add(q);

					while (z <= rules.getRule(i).getHypothesis().size() && BackwardChaining(rules.getRule(i).getHypothesis().get(j), lClone)) z++;
					if (z > rules.getRule(i).getHypothesis().size()) return true;

				}
			}
		}
		return false;
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
