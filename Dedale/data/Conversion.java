public class Conversion
{
	public static String enChaine( boolean[] tab )
	{
		String sRet;


		sRet = "+";
		for ( int cpt = 0 ;cpt < tab.length; cpt++)
			sRet += "-----+";
		

		sRet += "\n|";
		for ( int cpt = 0 ;cpt < tab.length; cpt++)
			if ( tab[cpt] ){ sRet += "true |"; } else { sRet += "false|";}


		sRet += "\n+";
		for ( int cpt = 0 ;cpt < tab.length; cpt++)
			sRet += "-----+";
		
		sRet += String.format("%-5s", "\n");	
		for ( int cpt = 0 ;cpt < tab.length; cpt++)
			sRet += String.format("%-6s", cpt + "  ");

		return sRet ;
	}

	public static int tab2Entier(boolean[] tabBoolean)
	{
		int somme;
		
		somme = 0;
		for (int indCase = 0; indCase < tabBoolean.length; indCase++)
		{
			if (tabBoolean[indCase])
			{
				somme += Math.pow(2, indCase);
			}
		}
		
		return somme;
	}


	public static boolean[] entier2Tab( int valeur, int nbElt)
    {
        boolean[] tab;
        tab = new boolean[nbElt] ;

        for (int i = 32; i >= 0; i--)
        {
            if (valeur >= Math.pow(2,i))
            {
                if (i < tab.length)
				{
                    tab[i] = true;
				}

                valeur -= Math.pow(2,i);
            }
            else
                if (i < tab.length)
				{
                    tab[i] = false;
				}
        }
        
        return tab;
	}

	public static String detail( Dedale dedale )
	{
		String sRet;
		sRet = "";
		for ( int cpt = 0 ; cpt < dedale.getNbLigne() ; cpt++ )
		{
			sRet += "== Ligne " + cpt + "==========================================================================================\n";
			for ( int cpt2 = 0 ; cpt2 < dedale.getNbColonne() ; cpt2++ )
				sRet += dedale.getPiece( cpt, cpt2 ).toString() + "\n" ;
		}
		sRet += "======================================================================================================";

		return sRet;
	}

	public static String grille( Dedale dedale )
	{
		String sRet;
		sRet = "";




		for ( int cpt = 0 ; cpt < dedale.getNbLigne() ; cpt++ )
		{	
			
			sRet += "\n+";
			for (int cpt2 = 0; cpt2 < dedale.getNbColonne(); cpt2++)
				sRet += "----+";

			sRet += "\n|";
			for ( int cpt2 = 0 ; cpt2 < dedale.getNbColonne() ; cpt2++ )
			{
				sRet += String.format("%4d", dedale.getPiece( cpt, cpt2 ).getValOuvertures()) + "|";
			}

			
		}

		sRet += "\n+";
		for (int cpt2 = 0; cpt2 < dedale.getNbColonne(); cpt2++)
			sRet += "----+";
		
		
		return sRet;
	}
}