public class Position
{
	private int lig;
	private int col;

	public Position ( int lig, int col )
	{
		this.lig = lig;
		this.col = col;
	}

	public int  getLig ()        { return this.lig; }
	public int  getCol ()        { return this.col; }

	public void setLig (int lig) { this.lig=lig;    }
	public void setCol (int col) { this.col=col;    }
}