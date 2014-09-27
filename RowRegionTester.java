package sudokusolver;
/**
 * Tämä testeri tarkistaa syötetystä ongelmasta x-akselin alueen eli yhden rivin.
 * @author Aki
 *
 */
public class RowRegionTester extends RegionTesters{
	private boolean RegionBoolean = true;
	/**
	 * Tallennetaan yliluokan kautta annetut parametrit talteen.
	 * @param kayttajanGrid
	 * @param i
	 * @param j
	 */
	public RowRegionTester(int[][] kayttajanGrid, int i, int j) {
		super(kayttajanGrid, i, j);
	// TODO Auto-generated constructor stub
		RegionTester();
	}
	/**
	 * Tässä metodissa itse tarkistus tapahtuu. Testeri muuttaa RegionsBoolean-arvoa
	 * riippuen siitä, onko ongelma tässä testattavassa alueessa validi.
	 */
	public void RegionTester() {
		//Tarkistetaan ensin onko rivillä samaa numeroa, ei saisi olla... eli mennään siis
		//alaspäin. Iffissä katsotaan ensin että ei vertailla annettua arvoa annettuun arvoon.
		for (int sarake = 0; sarake < 9; sarake++) {
 			if (sarake !=j && getKayttajanGrid()[i][sarake] == getKayttajanGrid()[i][j]) { //Tässä alaspäin
				setRegionBoolean(false);
				break;
				}
		}
	}
	public boolean isRegionBoolean() {
		return RegionBoolean;
	}
	public void setRegionBoolean(boolean regionBoolean) {
		RegionBoolean = regionBoolean;
	}
}
