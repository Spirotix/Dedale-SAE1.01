public class Piece {
    private static String[] tabDir = new String[] {"Nord","Ouest","Sud","Est"};

    private String nom;
    private boolean[] ouvertures;

    public Piece(int valeur)
    {
        this(valeur,"");
    }

    public Piece(int valeur, String nom)
    {
		
        this.nom = nom;
        if (valeur >= 0 && valeur <= 63)
            this.ouvertures = Conversion.entier2Tab(valeur,7);
        else
            if (valeur == -1) 
				this.ouvertures = Conversion.entier2Tab(64,7);
			else
				this.ouvertures = Conversion.entier2Tab(0,7);
    }

    public int getValOuvertures()
    {
        int nb;
        nb = Conversion.tab2Entier(this.ouvertures);

		if (nb == 64) return -1;
        if (nb >= 32) nb -= 32;
        if (nb >= 16) nb -= 16;

        return nb;
    }

    public boolean getDepart()
    {
        return ouvertures[4];
    }

    public boolean getArrivee()
    {
        return ouvertures[5];
    }

    public boolean getOuvertures(char dir)
    {
        if (indiceDir(dir) == -1) return false;
        if (this.ouvertures[indiceDir(dir)])
            return true;
        
        return false;
        
    }

    private static int indiceDir(char dir)
    {
        return switch (dir)
            {
                case 'N' ->  0;
                case 'O' ->  1;
                case 'S' ->  2;
                case 'E' ->  3;
                default  -> -1;
            };
    }

    public String toString()
    {
        String sRet;
        sRet ="";

        sRet += String.format("%2d", this.getValOuvertures());
        if ( this.nom != "" )
        { 
            sRet += String.format("%-12s", " (" + this.nom + ")" );
        }
        else
        { 
            sRet += String.format("%-12s", "" ); 
        }   
        
        sRet += "==> " + "Nord(0) :" + String.format("%-5s", this.ouvertures[0] ) + " Ouest(1) :" + String.format("%-5s", this.ouvertures[3] ) + " Sud (2) :" +
        String.format("%-5s", this.ouvertures[2] )  + " Est (3) :" + String.format("%-5s", this.ouvertures[3] ) ;
        
        if (getDepart()) sRet += " départ";
        if (getArrivee()) sRet += " arrivée";
        return sRet ;
    }
}

