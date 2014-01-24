import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    Context_Search con_search = new Context_Search();
	Scanner sn=new Scanner(System.in);
	System.out.println("Enter phrase to search...");
	String phrase = sn.nextLine();
	sn.close();
    con_search.search(phrase);
	}

}
