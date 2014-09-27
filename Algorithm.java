package sudokusolver;
/**
 * Tämä luokka on ohjelman 'sydän'.
 * Ratkaisuun liittyvät operaatiot tehdään tässä luokassa
 * search()-metodin avulla.
 * @author Aki
 *
 */
public class Algorithm {
	private int[][] kayttajanGrid;
	private int[][] listavapaista;
	private boolean ratkaistuvaiei;
	public Algorithm(int[][] kayttajanGrid) {
		this.setKayttajanGrid(kayttajanGrid);
		 //Tallennetaan listaan vapaana olevat sudokucellit
		this.listavapaista = vapaanaolevatpaikat();
		ratkaistuvaiei = search();
	}
	/**
	 * Search()-metodi on Ariadnen langan logiikkaa seuraava backtracking-algoritmi
	 * joka etsii annetulle gridille ratkaisun.
	 * @return
	 */
	public boolean search() {
		if (listavapaista.length == 0) {
			return true; //Sudoku on ratkaistu jo
		}
		int k = 0; //Alotetaan ensimmäiseistä ruudusta, eli toisinsanoen eka vapaa ruutu sudokussa
		while (true) {
			int i = listavapaista[k][0]; //x-koordinaatti
			int j = listavapaista[k][1]; //y-koordinaatti
			if (getKayttajanGrid()[i][j] == 0) { //Jos on 0, (mitä ekalla suorituksella pitäisikin olla)
				getKayttajanGrid()[i][j] =1; //niin tallennetaan ykkönen
			} if (Controller.syoteonValidi(i,j,getKayttajanGrid())) { //Jos on validi niin hyväksytään vastaus
				int temp = k;
				temp++;
				if (temp ==listavapaista.length) { //tarkastetaan vielä oliko viimeinen vapaa
					return true; //Tässä vaiheessa sudoku on ratkaistu
				}
				else { //Siirrytään seuraavaan vapaaseen tapaukseen. Tyhjiä on siis vielä jäljellä listalla
					k++;
				}
			} else if (getKayttajanGrid()[i][j] <9) { //Syöte ei ole validi, katsotaan onko se pienempi kuin 9, max arvo on 9
				//Lisätään seuraava arvo ja takas while-luuppiin tarkistamaan onko validi.
				int temp = getKayttajanGrid()[i][j];
				getKayttajanGrid()[i][j]= temp+1;
			} else { //Kaikki menee huonosti, syöte ei ole validi ja slotin arvo on 9, pitää backtrackata
				while (getKayttajanGrid()[i][j] == 9) {
					if (k == 0) { //On backtrackattu ensimmäiseen vapaaseen slottiin listalla ja siihenkin tulee arvoksi 9
					 return false;
					}
					else {
						getKayttajanGrid()[i][j]=0; //Vaihdetaan tämähetkinen nollaksi, (tilanne jossa tapaus on 9)
						k--;	//Backtrackataan takas edelliseen avoimeen slottiin
						i=listavapaista[k][0]; //Asetetaan edellisen slotin indeksit kohdalleen, x-koordinaatti
						j = listavapaista[k][1];//Y-koordinaatti
					}
				}
				getKayttajanGrid()[i][j] = getKayttajanGrid()[i][j] + 1; //Lisätään edellisen vapaan cellin arvoa yhdellä joka ei ole 9 ja palataan alkuperäiseen
				//while-looppiin.
			}
		}
	}
	/**
	 * Tällä metodilla etsitään annetusta gridistä ruudut joita ei
	 * ole etukäteen määritelty.
	 * @return
	 */
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