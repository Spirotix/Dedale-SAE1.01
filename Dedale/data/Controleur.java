import ihmgui.FrameGrille;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import ihmgui.Controle;

public class Controleur extends Controle
{
	private Dedale      metier   ;
	private FrameGrille frame    ;
	private String[]    tabSprite = {"dw", "eevee"};
	private int         sprite   = 0;

	public Controleur()
	{
		this.metier  = new Dedale();
		this.frame   = new FrameGrille ( this );

		frame.setSize     (900, 700 );
		frame.setLocation ( 0 ,  0  );
		frame.setTitle    ("Dedale" );
		frame.setVisible  (true     );
	}
	
	public String setBouton (int numBtn)
	{
		String lib;
		
		switch(numBtn)
		{
			case 0 -> lib = "niveau précédent";
			case 1 -> lib = "niveau suivant";
			case 2 -> lib = "reset";
			case 3 -> lib = "changer thème";
			case 4 -> lib = "/!\\ Reset save /!\\";
			default  -> lib = null;
		};
		
		return lib;
	}
	
	public void bouton ( int action )
	{
		if ( action == 0 && metier.getNbNiveau() > 1 ) 
		{ 
		    metier.nivPrecedent (); 
		    Dedale.sauvegarder();
		}
		
		if ( action == 1 && metier.getNbNiveauMax() > metier.getNbNiveau() )
		{
			metier.nivSuivant();
			System.out.println( metier.getNbNiveauMax() + " " + metier.getNbNiveau() );
			Dedale.sauvegarder();
		}
		
		if ( action == 2 ) metier.resetNiveau();

		if ( action == 3 ) sprite = (sprite + 1) % tabSprite.length;

		if ( action == 4 ) 
		{
			PrintWriter pw;
	     try
		    {
			    pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("../save.data")));
		        
		        pw.println(1 + "\t" + 1);
		    	pw.close();
		    }   
		    catch (Exception e) 
		    {
			    e.printStackTrace();
	        }
			
			metier.resetSauvegarde();

		}
		
		frame.majIHM();
	}
	
	public int    setNbLigne    () { return 5   ; }
	public int    setNbColonne  () { return 5   ; }
	public int    setLargeurImg () { return 100 ; }
	public String setFondGrille () { return "../images/"  + tabSprite[sprite] + "/fond.png"; }
	
	public String getTheme()
	{
		return switch(this.sprite)
		{
			case 0 -> "Dr Who";
			case 1 -> "Pokemon";
			default -> "";
		};
	}

	public String setLabel(int numLbl)
    {
        String lib;

        switch (numLbl)
        {
        case 0:
            lib = "Nb Dep:";
            break;
		case 1:
			lib = "N° Actuel :";
			break;
		case 2:
			lib = "N° Max :";
			break;
		case 3:
			lib = "Thème :";
			break;
        default:
            lib = null; // cette derni�re ligne est importante, elle met fin �
                        // la contruction des labels
        }

        return lib;
    }

	public int setLargeurLabel()
	{
		return 150;
	}
    public String setTextLabel(int numLbl)
    {
        return switch (numLbl)
		{
		case 0 -> metier.getCptDpl();
		case 1 -> "" + metier.getNbNiveau();
		case 2 -> "" + metier.getNbNiveauMax();
		case 3 -> getTheme();
		default -> null;
		};

    }
	
	public String setImage ( int ligne, int colonne, int couche)
	{
		String rep    = "../images/" + tabSprite[sprite] + "/";
		String sImage = null;

		if ( couche == 0 )
		{
			if ( metier.getPiece( ligne, colonne ).getValOuvertures() == -1 )
				sImage =  rep + "lave.png";
			else
				sImage =  rep + "P" + String.format("%02d", metier.getPiece( ligne, colonne ).getValOuvertures() ) + ".png";
		}
		

		if ( (  couche == 1 || couche == 2 ) && metier.getSymboleHero(ligne , colonne) == 's' )
		{
			sImage = rep + switch(metier.getImage(ligne,colonne))
						{
							case "Nord"  -> "n.png";
							case "Ouest" -> "o.png";
							case "Sud"   -> "s.png";
							case "Est"   -> "e.png";
							default      -> "";
						};
		}

		
		if (couche == 1)
		{
			if ( metier.getPiece(ligne, colonne).getArrivee() )
			{
				sImage = rep + "arrivee.png";
			}
			
			if ( metier.getPiece(ligne, colonne).getDepart() )
			{
				sImage = rep + "depart.png";
			}
		}

		return sImage;
	}

	public static void main(String[] a)
	{
		new Controleur();
	}
	
	public void jouer (String touche)
	{
		if ( touche.equals ( "FL-H" ) ) metier.deplacer ( 'N' );
		if ( touche.equals ( "FL-G" ) ) metier.deplacer ( 'O' );
		if ( touche.equals ( "FL-B" ) ) metier.deplacer ( 'S' );
		if ( touche.equals ( "FL-D" ) ) metier.deplacer ( 'E' );

		frame.majIHM();
	}
	
	public void glisser (int ligne1, int colonne1, int ligne2, int colonne2)
	{
		if ( metier.deplacerPiece( ligne1, colonne1, ligne2, colonne2 ) )
			System.out.println ( "glisser de (" + ligne1 +":" + colonne1  + ")  vers  (" + ligne2 + ":" + colonne2 + ")" );
		
		frame.majIHM();
	}

	
}
