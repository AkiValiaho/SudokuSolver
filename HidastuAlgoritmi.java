package sudokusolver;
import javax.swing.ListSelectionModel;
/**
 * Toimintaperiaatteeltaan samankaltainen luokka kuin Algorithm.
 * Ainoana erona on se, että tämä luokka implementoi Runnable-rajapinnan.
 * Tämä mahdollistaa luokan ajamisen erillisessä threadissa ja sitä kautta
 * tämän threadin 'nukuttamisen' irrallaan käyttöliittymän threadista.
 * @author Aki
 *
 */
public class HidastuAlgoritmi implements Runnable{
	private int[][] kayttajanGrid;
	private int[][] listavapaista;
	public volatile static boolean ratkaistuvaiei;
	private GUI refgui;
	public volatile static boolean kaynnissa = true;
	/**
	 * Tallennetaan konstruktorissa syötetyt parametrit.
	 * @param kayttajanGrid Syötetty ongelma
	 * @param refgui JTablen päivittämistä varten
	 */
	public HidastuAlgoritmi(int[][] kayttajanGrid,GUI refgui) {
		this.setKayttajanGrid(kayttajanGrid);
		 //Tallennetaan listaan vapaana olevat sudokucellit
		this.listavapaista = vapaanaolevatpaikat();
		this.refgui = refgui;
	}
	public void run() {
		int k = 0; //Alotetaan ensimmäiseistä ruudusta, eli toisinsanoen eka vapaa ruutu sudokussa
			while (true) {
				if (listavapaista.length == 0) {
					ratkaistuvaiei = true; //Sudoku on ratkaistu jo
					break;
				}
				Thread tarkkailija = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (kaynnissa== false) {
							Controller.solver.interrupt();
						}
					}
				});
				tarkkailija.start();
				int i = listavapaista[k][0]; //x-koordinaatti
				int j = listavapaista[k][1]; //y-koordinaatti
				if (getKayttajanGrid()[i][j] == 0) { //Jos on 0, (mitä ekalla suorituksella pitäisikin olla)
					getKayttajanGrid()[i][j] = 1; //niin tallennetaan ykkönen
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						break;
					}
					refgui.getTable().setValueAt(1, i, j);
				}
				if (Controller.syoteonValidi(i, j, getKayttajanGrid())) { //Jos on validi niin hyväksytään vastaus
					if (k + 1 == listavapaista.length) { //tarkastetaan vielä oliko viimeinen vapaa
						ratkaistuvaiei = true;
						break;//Tässä vaiheessa sudoku on ratkaistu
					} else { //Siirrytään seuraavaan vapaaseen tapaukseen. Tyhjiä on siis vielä jäljellä listalla
						k++;
					}
				} else if (getKayttajanGrid()[i][j] < 9) { //Syöte ei ole validi, katsotaan onko se pienempi kuin 9, max arvo on 9
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						break;
					}
					//Lisätään seuraava arvo ja takas while-luuppiin tarkistamaan onko validi.
					getKayttajanGrid()[i][j] = getKayttajanGrid()[i][j] + 1;
					refgui.getTable()
							.setValueAt(getKayttajanGrid()[i][j], i, j);
				} else { //Kaikki menee huonosti, syöte ei ole validi ja slotin arvo on 9, pitää backtrackata
					while (getKayttajanGrid()[i][j] == 9) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							break;
						}
						if (k == 0) { //On backtrackattu ensimmäiseen vapaaseen slottiin listalla ja siihenkin tulee arvoksi 9
							ratkaistuvaiei = false;
							break;
							//Ratkaisua ongelmaan ei ole olemassa...
						}
						getKayttajanGrid()[i][j] = 0; //Vaihdetaan tämähetkinen nollaksi, (tilanne jossa tapaus on 9)
						refgui.getTable().setValueAt(0, i, j);
						k--; //Backtrackataan takas edelliseen avoimeen slottiin
						i = listavapaista[k][0]; //Asetetaan edellisen slotin indeksit kohdalleen, x-koordinaatti
						j = listavapaista[k][1];//Y-koordinaatti
					}
					getKayttajanGrid()[i][j] = getKayttajanGrid()[i][j] + 1;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						break;
					}	
					refgui.getTable()
							.setValueAt(getKayttajanGrid()[i][j], i, j);
					//Lisätään edellisen vapaan cellin arvoa yhdellä joka ei ole 9 ja palataan alkuperäiseen
					//while-looppiin.
				}
			}
		}
	private int[][] vapaanaolevatpaikat() {
		//Etsitään gridistä kaikki nollat
		int vapaidenpaikkojenlkm = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (getKayttajanGrid()[i][j]==0) {
					vapaidenpaikkojenlkm++;
				}
			}
		}
		int[][] listavapaistapaikoistapaikoista = new int[vapaidenpaikkojenlkm][2];
		//vapaiden ruutujen määrän sarakkeita, riveillä tilaa x ja y:lle
		int lkm = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (getKayttajanGrid()[i][j]==0) {
					listavapaistapaikoistapaikoista[lkm][0] = i;
					listavapaistapaikoistapaikoista[lkm++][1]=j;
				}
			}	
		}
		return listavapaistapaikoistapaikoista;
	}
	public boolean isRatkaistuvaiei() {
		return ratkaistuvaiei;
	}
	public int[][] getKayttajanGrid() {
		return kayttajanGrid;
	}
	public void setKayttajanGrid(int[][] kayttajanGrid) {
		this.kayttajanGrid = kayttajanGrid;
	}
}