package sudokusolver;

/**
 * Yliluokka kaikille testeriluokille.
 * Tästä luokasta periytetään kayttajanGrid, i ja j testereille.
 * @author Aki
 *
 */
public class RegionTesters {
	private int[][] kayttajanGrid;
	protected int i;
	protected int j;
	/**
	 * Konstruktori, jossa asetetaan annetut parametrit
	 * alempien luokkien käytettäväksi.
	 * @param kayttajanGrid ratkaistava ongelma
	 * @param i x-koordinaatti
	 * @param j y-koordinaatti
	 */
	public RegionTesters(int[][] kayttajanGrid,int i, int j) {
		// TODO Auto-generated constructor stub
		this.kayttajanGrid = kayttajanGrid;
		this.i = i;
		this.j = j;
	}
	public int[][] getKayttajanGrid() {
		return kayttajanGrid;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}

}
