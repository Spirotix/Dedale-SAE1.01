public class TestPiece {
	
	public static void main(String[] argv)
	{
		Piece p1,p2;
		
		p1 = new Piece(14+16);
		p2 = new Piece(5, "Hall");
		
		System.out.println(p1.getValOuvertures());
		System.out.println(p2.getValOuvertures());
		
		System.out.println(p1);
		System.out.println(p2);

		System.out.println("Piece 1 Ouest : " + p1.getOuvertures('O'));
		System.out.println("Piece 1 Nord  : " + p1.getOuvertures('N'));
		
		System.out.println("Piece 2 Est   : " + p2.getOuvertures('E'));
		System.out.println("Piece 2 Sud   : " + p2.getOuvertures('S'));
		
		System.out.println("Piece 1 test avec G : " + p1.getOuvertures('G')); //test d'erreur

	}
}






















