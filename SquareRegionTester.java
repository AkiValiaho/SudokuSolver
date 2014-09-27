package sudokusolver;
/**
 * Tämä testeriluokka tarkistaa yhden 3x3-kokoisen laatikon alueen validiuden.
 * @author Aki
 *
 */
public class SquareRegionTester extends RegionTesters{
	private boolean RegionsBoolean = true;
	/**
	 * Tallennetaan yliluokan kautta annetut parametrit talteen.
	 * @param grid
	 * @param i
	 * @param j
	 */
	public SquareRegionTester(int[][] grid,int i, int j) {
		super(grid,i,j);
		TestRegion();
	}
	/**
	 * Metodi, joka suorittaa testauksen ja muokkaa RegionsBoolean-arvoa riippuen lopputuloksesta.
	 */
	public void TestRegion() {
		//Tässä täytyy havaita hieman Integeriin liittyviä ominaisuuksia. Integer ei nimittäin
		//pyöristä ylöspäin. Lasku 1/3 tuottaisi rivi-muuttujaan arvon 0! (Rivi 2 sarakkeessa 1-3 on 
		//osa vasemman yläkulman laatikkoa!)
		//Näin saadaan jokaisen laatikon alue rajattua ja testattua.
		for (int rivi = (i / 3) * 3; rivi < (i / 3) * 3 + 3; rivi++) {
			//Määritellään boksien rajat!
			 for (int sarake1 = (j / 3) * 3; sarake1 < (j / 3) * 3 + 3; sarake1++) {
				 if (rivi != i && sarake1 != j && getKayttajanGrid()[rivi][sarake1] == getKayttajanGrid()[i][j]) {
					 setRegionsBoolean(false);
				 		break; 
				 }
			 }
		}
		}
	public boolean isRegionsBoolean() {
		return RegionsBoolean;
	}
	public void setRegionsBoolean(boolean regionsBoolean) {
		RegionsBoolean = regionsBoolean;
	}
	}
