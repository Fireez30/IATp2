
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
	/*	// TODO Auto-generated method stub
		Atom at1 = new Atom(")");
		Atom at2 = new Atom(")");
		Atom at3 = new Atom(")");
		
		FactBase f= new FactBase("at1;at2");
		f.addAtomWithoutCheck(at3);
		
		System.out.println(f.toString());
		
		Rule r1 = new Rule("at1;at2;at3");
		RuleBase r = new RuleBase();
		r.addRule(r1);
		
		
		System.out.println(r1.toString());*/
		
		KnowledgeBase k = new KnowledgeBase("src/reunion.txt");
		
		Scanner sc = new Scanner(System.in);

		System.out.println("Atom :");

		String str = sc.nextLine();
		
		Atom at = new Atom(str);
				
		System.out.println(k.BackwardChaining(new Atom(str), new ArrayList<Atom>()));		
	}

}
