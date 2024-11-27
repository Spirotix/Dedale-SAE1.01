import java.util.Scanner;
import java.io.FileInputStream;

import iut.algo.Decomposeur;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Dedale
{
	private static char[] tabDir = new char[] {'N','O','S','E'};

	private Piece[][] tabPiece;
	
	private Piece pieceHeros;
	
	private String imageAct;
	
	private int cptDpl;
	
	private boolean estArrive;
	
	private static int nbNiveau    = chercherNbNiveau();
	private static int nbNiveauMax = chercherNbNiveauMax();


	public Dedale()
	{
		int ligDep = 0;
		int colDep = 0;
		
		this.tabPiece   = this.initPiece();
		this.cptDpl     = 0;
		this.estArrive = false;
		
		for (int lig = 0 ; lig < tabPiece.length ; lig++)
		{
			for (int col = 0 ; col < tabPiece[lig].length ; col++)
			{
				if ( tabPiece[lig][col].getDepart() )
				{
					ligDep = lig;
					colDep = col;
				}
			}
		}
		
		this.pieceHeros = this.tabPiece[ligDep][colDep];
		this.imageAct   = "Sud";
	}

	private Piece getPieceAdj ( int lig, int col, char dir )
	{
		switch (dir)
		{
			case 'N' -> lig--;
			case 'O' -> col--;
			case 'S' -> lig++;
			case 'E' -> col++;
		}
		
		if (lig < 0 || lig >= tabPiece[0].length || col < 0 || col >= tabPiece[0].length)
			return null;
		
		return tabPiece[lig][col];
	}

	public String getCptDpl() { return ""+this.cptDpl;}
	
	public boolean estValide()
	{
		Piece autre;

		for (int cptLig=0; cptLig < this.tabPiece.length; cptLig++)
		{
			for (int cptCol=0; cptCol < this.tabPiece[cptLig].length; cptCol++)
			{
				for (int i=0; i < tabDir.length; i++ )
				{
					if (this.tabPiece[cptLig][cptCol].getOuvertures(tabDir[i]))
					{
						autre = getPieceAdj(cptLig,cptCol,tabDir[i]);

						if (autre != null)
							if (!autre.getOuvertures(tabDir[(i+2)%4])) return false;
					}
				}
			}
		}
		
		return true;
	}

	public int   getNbLigne  () { return this.tabPiece.length ; }
    public int   getNbColonne() { return this.tabPiece[0].length ; }
	

    public Boolean getEstArrive() { return this.estArrive; }

    public Piece getPiece (int lig, int col)
    {
		if (lig < 0 || lig >= tabPiece[0].length || col < 0 || col >= tabPiece[0].length)
			return null;
		
        return this.tabPiece[lig][col];
    }

	private Piece[][] initPiece()
	{
		int[][]     initTab      ;
		Scanner		sc           ;
		Decomposeur	dec          ;
		int		    nbLig, nbCol ;
		Piece[][]   tab          ;
		String      nomNiveau    ;
		
		nbLig = nbCol = 5;

		/*tab = new Piece[][] {{new Piece(0,"A"), new Piece(12,"B"),new Piece(14,"C"),new Piece(6,"D"),new Piece(0,"E")},
                             {new Piece(0,"F"), new Piece(1+16,"G"),new Piece(5,"H"),new Piece(1,"I"),new Piece(0,"J")},
                             {new Piece(8,"K"), new Piece(10,"L"),new Piece(-1,"M"),new Piece(10,"N"),new Piece(2,"O")},
                             {new Piece(0,"P"), new Piece(4+32,"Q"),new Piece(5,"R"),new Piece(4,"S"),new Piece(0,"T")},
                             {new Piece(0,"U"), new Piece(9,"V"),new Piece(11,"W"),new Piece(3,"X"),new Piece(0,"Y")}};*/
		
		/*tab = new Piece[][] {{new Piece(0,"A"), new Piece(0,"B"),new Piece(0,"C"),new Piece(0,"D"),new Piece(0,"E")},
                             {new Piece(8+32,"F"), new Piece(10,"G"),new Piece(10,"H"),new Piece(6,"I"),new Piece(0,"J")},
                             {new Piece(0,"K"), new Piece(12,"L"),new Piece(-1,"M"),new Piece(3,"N"),new Piece(0,"O")},
                             {new Piece(0,"P"), new Piece(9,"Q"),new Piece(10,"R"),new Piece(6+16,"S"),new Piece(0,"T")},
                             {new Piece(0,"U"), new Piece(0,"V"),new Piece(0,"W"),new Piece(0,"X"),new Piece(0,"Y")}};*/
		
		
		initTab = new int[nbLig][nbCol];		
		try
		{
			sc  = new Scanner ( new FileInputStream ( "../niveaux/niveau_" + String.format("%02d", nbNiveau) + ".data" ), "UTF8" );
			
			nomNiveau = sc.nextLine();
			
			int lig = 0;
			while ( sc.hasNextLine() )
			{
				dec = new Decomposeur ( sc.nextLine() );
				
				for ( int col = 0 ; col < initTab[lig].length ; col++ )
				{
					initTab[lig][col] = dec.getInt(col);
				}
				lig++;
			}
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
		
		tab = new Piece[nbLig][nbCol];
		int compteur = 0;
		for ( int lig = 0 ; lig < tab.length ; lig++ )
		{
			for ( int col = 0 ; col < tab[lig].length ; col++ )
			{
				tab[lig][col] = new Piece ( initTab[lig][col] , "A" + ('a' + compteur ) );
				compteur++;
			}
		}

		return tab;
	}

	public String validite()
	{
		Piece autre;
		boolean erreur;
		String sRet;
		sRet = "";

		for (int cptLig=0; cptLig < this.tabPiece.length; cptLig++)
		{
			for (int cptCol=0; cptCol < this.tabPiece[cptLig].length; cptCol++)
			{
				erreur = false;
				sRet += String.format("Piece[%2d][%2d] %2d",cptLig,cptCol, this.tabPiece[cptLig][cptCol].getValOuvertures());
				for (int i=0; i < tabDir.length; i++ )
				{
					if (this.tabPiece[cptLig][cptCol].getOuvertures(tabDir[i]))
					{
						autre = getPieceAdj(cptLig,cptCol,tabDir[i]);
						if (autre != null)
						{
							if (!autre.getOuvertures(tabDir[(i+2)%4]))
							{
								sRet += pbPiece(tabDir[i]);
								erreur = true;
							}
						}
					}
					else
					{
						autre = getPieceAdj(cptLig,cptCol,tabDir[i]);
						if (autre != null)
						{
							if (autre.getOuvertures(tabDir[(i+2)%4]))
							{
								sRet += pbPiece(tabDir[i]);
								erreur = true;
							}
						}
					}
				}
				if (!erreur)
						sRet += "  OK";
				sRet+="\n";
			}
			sRet += "\n";
		}
		return sRet;
	}

	private String pbPiece(char dir)
	{
		String sRet;
		sRet = "\n\tpb avec piece située ";
		sRet += switch(dir)
		{
			case 'N' -> "au Nord";
			case 'O' -> "à l'Ouest";
			case 'S' -> "au Sud";
			case 'E' -> "à l'Est";
			default  -> "";
		};
		return sRet;
	}
	
	public char getSymboleHero ( int lig , int col )
	{
		if (this.tabPiece[lig][col] == this.pieceHeros) return 's';
        return ' ';
	}
	
	private Position rechercherPosition (Piece p)
	{
		for(int cptLig = 0 ; cptLig < 5 ; cptLig++)
		{
			for(int cptCol = 0 ; cptCol < 5 ; cptCol++)
			{   
				if (this.tabPiece[cptLig][cptCol] == this.pieceHeros)
					return new Position(cptLig,cptCol);
			}
		}
		
		return null;
	}
	
	public void deplacer(char dir)
	{
		Piece autre;
		Position heros;
		String ancienneImage;
		heros = rechercherPosition(this.pieceHeros);
		char autreDir;

		autreDir = switch(dir)
		{
			case ('N') -> 'S';
			case ('S') -> 'N';
			case ('E') -> 'O';
			case ('O') -> 'E';
			default    -> ' ';
		};
		
		ancienneImage = this.imageAct;
		
		this.imageAct = switch(dir)
		{
			case ('N') -> "Nord";
			case ('S') -> "Sud";
			case ('E') -> "Est";
			case ('O') -> "Ouest";
			default    -> "";
		};
				
		if ( dir == ancienneImage.charAt(0))
		{
			if (this.tabPiece[heros.getLig()][heros.getCol()].getOuvertures(dir))
			{
				autre = getPieceAdj(heros.getLig(),heros.getCol(),dir);

				if (autre != null)
                    if (autre.getOuvertures(autreDir))
					{
						this.pieceHeros = autre; 
						this.cptDpl ++;
						
						if (this.pieceHeros.getArrivee()) { this.estArrive = true; if ( nbNiveauMax == nbNiveau ) nbNiveauMax++; }
					}
			}
		}
		//balise1
		
		if ( chercherNbNiveauMax() < nbNiveauMax  )
		{
		    sauvegarder();
		}
		
		
	}
	
	
	public String getImage ( int lig , int col )
	{
		return this.imageAct ;
	}
	
	public boolean deplacerPiece ( int ligne1, int colonne1, int ligne2, int colonne2 )
	{
		if ( tabPiece[ligne1][colonne1]                    == pieceHeros ) return false;
		if ( tabPiece[ligne1][colonne1].getValOuvertures() == 0          ) return false;
		if ( tabPiece[ligne2][colonne2].getValOuvertures() != -1         ) return false;
		if ( tabPiece[ligne1][colonne1].getArrivee()                     ) return false;
		if ( tabPiece[ligne1][colonne1].getValOuvertures() == -1         ) return false;
		
		boolean test;
		test = false;
		for ( int cpt = 0 ; cpt < tabDir.length ; cpt++ )
			if ( this.getPieceAdj( ligne1, colonne1, tabDir[cpt] ) == tabPiece[ligne2][colonne2] ) test = true;
		
		if ( ! test ) return false;
		
		
		Piece temp;
		
		temp                       = tabPiece[ligne1][colonne1];
		tabPiece[ligne1][colonne1] = tabPiece[ligne2][colonne2];
		tabPiece[ligne2][colonne2] = temp;
		
		this.cptDpl ++;
		
		return true;
	}
	
	public void nivPrecedent ()
	{
		nbNiveau--;
		int ligDep = 0;
		int colDep = 0;
		
		this.tabPiece   = this.initPiece();
		this.cptDpl     = 0;
		this.estArrive = true;
		
		for (int lig = 0 ; lig < tabPiece.length ; lig++)
		{
			for (int col = 0 ; col < tabPiece[lig].length ; col++)
			{
				if ( tabPiece[lig][col].getDepart() )
				{
					ligDep = lig;
					colDep = col;
				}
			}
		}
		
		this.pieceHeros = this.tabPiece[ligDep][colDep];
		this.imageAct   = "Sud";
	}
	public void nivSuivant ()
	{
		nbNiveau++;
		int ligDep = 0;
		int colDep = 0;
		
		this.tabPiece   = this.initPiece();
		this.cptDpl     = 0;
		this.estArrive = false;
		
		for (int lig = 0 ; lig < tabPiece.length ; lig++)
		{
			for (int col = 0 ; col < tabPiece[lig].length ; col++)
			{
				if ( tabPiece[lig][col].getDepart() )
				{
					ligDep = lig;
					colDep = col;
				}
			}
		}
		
		this.pieceHeros = this.tabPiece[ligDep][colDep];
		this.imageAct   = "Sud";
	}
	
	public void resetNiveau()
	{
		int ligDep = 0;
		int colDep = 0;
		
		this.tabPiece   = this.initPiece();
		this.cptDpl     = 0;
		this.estArrive = false;
		
		for (int lig = 0 ; lig < tabPiece.length ; lig++)
		{
			for (int col = 0 ; col < tabPiece[lig].length ; col++)
			{
				if ( tabPiece[lig][col].getDepart() )
				{
					ligDep = lig;
					colDep = col;
				}
			}
		}
		
		this.pieceHeros = this.tabPiece[ligDep][colDep];
		this.imageAct   = "Sud";
	}
	
	public int getNbNiveau()
	{
		return nbNiveau;
	}
	
	public int getNbNiveauMax()
	{
		return nbNiveauMax;
	}
	
	public static int chercherNbNiveauMax()
	{
	    Decomposeur dec;
	    Scanner sc;
	    int nbNiveauMax;
	    
	    nbNiveauMax = 0;
	    try
		{
			sc  = new Scanner ( new FileInputStream ( "../save.data" ), "UTF8" );

			dec = new Decomposeur ( sc.nextLine() );
				    
			nbNiveauMax = dec.getInt(0);
				
			
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
		
		return nbNiveauMax;
	}
	
	public static int chercherNbNiveau()
	{
	    Decomposeur dec;
	    Scanner sc;
	    int nbNiveau;
	    
	    nbNiveau = 0;
	    try
		{
			sc  = new Scanner ( new FileInputStream ( "../save.data" ), "UTF8" );

			dec = new Decomposeur ( sc.nextLine() );
				    
		    nbNiveau = dec.getInt(1);
				
			
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
		
		return nbNiveau;
	}
	
	public static void sauvegarder()
	{
		PrintWriter pw;
	     try
		    {
			    pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("../save.data")));
		        
		        pw.println(nbNiveauMax + "\t" + nbNiveau);
		    	pw.close();
		    }   
		    catch (Exception e) 
		    {
			    e.printStackTrace();
	        }
	}

	public void resetSauvegarde()
	{
		nbNiveau = 1;
		nbNiveauMax =1;

		int ligDep = 0;
		int colDep = 0;
		
		this.tabPiece   = this.initPiece();
		this.cptDpl     = 0;
		this.estArrive = false;
		
		for (int lig = 0 ; lig < tabPiece.length ; lig++)
		{
			for (int col = 0 ; col < tabPiece[lig].length ; col++)
			{
				if ( tabPiece[lig][col].getDepart() )
				{
					ligDep = lig;
					colDep = col;
				}
			}
		}
		
		this.pieceHeros = this.tabPiece[ligDep][colDep];
		this.imageAct   = "Sud";
	}
}
