package sudokusolver;

/**
 * Tässä luokassa tarkistetaan y-akselin suhteen eli yksi sarake.
 * @author Aki
 *
 */
public class ColumnRegionTester extends RegionTesters{
	private boolean RegionBoolean = true;
	/**
	 * Tallennetaan yliluokan kautta annetut parametrit talteen.
	 * @param grid
	 * @param i
	 * @param j
	 */
	public ColumnRegionTester(int[][] grid, int i, int j) {
		super(grid, i, j);
		// TODO Auto-generated constructor stub

		tester();
	}
	public boolean isRegionBoolean() {
		return RegionBoolean;
	}
	/**
	 * Testeri testaa yhden sarakkeen ja muokkaa RegionBoolean-muuttujan arvoa riippuen tuloksesta.
	 */
	private void tester() {
		for (int rivi = 0; rivi < 9; rivi++) {
			if (rivi !=i && getKayttajanGrid()[rivi][j]==getKayttajanGrid()[i][j]) { //Vaakasuoraan alaspäin! Eli sarake
				setRegionBoolean(false);
				break;
			}
		}
	}
	public void setRegionBoolean(boolean regionBoolean) {
		RegionBoolean = regionBoolean;
	}
}
