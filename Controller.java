package sudokusolver;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
/**
 * @author Aki
 * Controller-luokka toimii kommunikaatioväylänä Algoritmin ja GUI:n välillä
 * välittäen tietoa.
 */
public class Controller {
	public static volatile boolean isfinished = false;
	public static Thread solver;
	/**
	 * Startup-metodi käynnistää algoritmin Gui:n välityksellä
	 * ottaen huomioon Gui:sta annetut parametrit.
	 * 
	 * @param Grid Laskettava taulukko
	 * @param ref Viittaus gui-olioon
	 * @param hidastus Valitaanko hidastettu suoritus
	 */
	public static void Startup(int[][] Grid,GUI ref,String hidastus) {
		int[][] kayttajanGrid = Grid;
		if (!syoteonvalidi(kayttajanGrid,ref)) {
			JOptionPane.showMessageDialog(null, "Jokin ei nyt täsmää syötteenannossa, Virheen antava luku on merkitty 9999");
		} else {
			if (hidastus == "Kyllä") {
				HidastuAlgoritmi uusi = new HidastuAlgoritmi(kayttajanGrid, ref);
				solver = new Thread(uusi);
				solver.start();
			} else {
				Algorithm uusi = new Algorithm(kayttajanGrid);
				if (uusi.isRatkaistuvaiei()) {
					ref.paivitaTable(kayttajanGrid);
				} else {
					ref.paivitaTable(kayttajanGrid);
					JOptionPane.showMessageDialog(null, "Ratkaisu ei ole mahdollinen");
				}
			}
			}
		}
	/**
	 * @param grid
	 * Mukana debuggaussyistä, tulostaa konsoliin Gridin.
	 */
	public static void printGrid(int[][] grid) { 
		 for (int i = 0; i < 9; i++) { 
 			for (int j = 0; j < 9; j++) 
				 System.out.print(grid[i][j] + " "); 
				 System.out.println(); 
			 } 
			 } 
	/**
	 * Tätä metodia käytetään satunnaisen sudokuongelman luomiseksi.
	 * Ensin generoidaan yksinkertaisella seedillä sudokuongelma käyttäen hyväksi
	 * satunnaisuutta, tämä ratkaistaan ja sitten ratkaisusta ongelmasta poistetaan satunnaisesti
	 * arvoja uuden ongelman luomiseksi.
	 * @param grid muokattava kaksiulotteinen array
	 * @param readynumbers Kuinka monta numeroa generoidaan, tulee GUI:lta.
	 * @param ref referenssi GUI:hin
	 * @return
	 * 
	 */
	public static int[][] generoi(int[][] grid,int readynumbers,GUI ref) {
		boolean isvalidi = false;
		boolean backtrackisvalid = false;
		int forbacktracking = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < readynumbers; i++) {
			Random ran = new Random();
			/*    Pseudosatunnainen integer rangella [min,max],
			    ekslusiivinen topvaluen suhteen joten lis�t��n 1
			    ett� oikea max saadaan, esim. (max = 6, viimeinen
			      saatava luku = 5). Pelkk� nextInt antaa siis rangen
			    0-(arg-1)*/
			int luku = ran.nextInt((9 - 1) + 1) + 1;
			if (list.contains(luku)) {
				int numberofhits = 0;
				for (Integer integer : list) {
					if (integer == luku) {
						numberofhits++;
					}
				}
				if (numberofhits ==9) {
					luku =  ran.nextInt((9 - 1) + 1) + 1;					
					list.add(luku);
				} else {
					list.add(luku);
				}
			} else {
				list.add(luku);
			}
		}
		//Satunnainen paikka outputille
				for (int i = 0; i < grid.length; i++) {
					if (list.isEmpty()) {
						break;
					}
					for (int j = 0; j < grid.length; j++) {
					Random ran = new Random();
					/*    Pseudosatunnainen integer rangella [min,max],
					    ekslusiivinen topvaluen suhteen joten lisätään 1
					    että oikea max saadaan, esim. (max = 6, viimeinen
					      saatava luku = 5). Pelkkä nextInt antaa siis rangen
					    0-(arg-1)*/
					int todari = ran.nextInt((9 - 1) + 1) + 1;
					if (todari < 2) {
						if (list.isEmpty()) {
							break;
						}
						grid[i][j] = list.get(0);
						if (syoteonValidi(i, j, grid)) {
							list.remove(0);
						} else {
							grid[i][j] = 0;
						}
					}
					}
				}
				Algorithm SolvedGrid = new Algorithm(grid);
				//Poistetaan satunnaisia variableja
				Random ran = new Random();
				/*    Pseudosatunnainen integer rangella [min,max],
				    ekslusiivinen topvaluen suhteen joten lisätään 1
				    että oikea max saadaan, esim. (max = 6, viimeinen
				      saatava luku = 5). Pelkkä nextInt antaa siis rangen
				    0-(arg-1)*/
				int luku = ran.nextInt((70 - 50) + 1) + 50;
				int i = 0;
				while (i < luku) {
					grid[ran.nextInt(((8-0)+1)+0)][ran.nextInt(((8-0)+1)+0)] = 0;
					i++;
					}
				return grid;
}
	/**
	 * Metodi palauttaa booleanin false jos
	 * syöte ei ole sudokun sääntöjen mukainen paikassa i,j
	 * arrayssa kayttajanGrid. Muuten true.
	 * @param i x-koordinaatti arrayssa
	 * @param j y-koordinaatti arrayssa
	 * @param kayttajanGrid tarkistettava array
	 * @return boolean
	 */
	public static boolean syoteonValidi(int i, int j,int[][] kayttajanGrid) {
		//Ensin tarkastetaan, onko celli� vastaavalla rivill� samaa numeroa kuin celliss�
 		RowRegionTester checkRowRegion = new RowRegionTester(kayttajanGrid, i, j);
		if (checkRowRegion.isRegionBoolean() == false) {
			return false;
		}
		//Sitten tarkastetaan, onko sarakkeella samaa numeroa.
			ColumnRegionTester checkColumnRegion = new ColumnRegionTester(kayttajanGrid, i, j);
			if (checkColumnRegion.isRegionBoolean() == false) {
				return false;
			}
		//3 by 3 boksi
		SquareRegionTester CheckSquareRegion = new SquareRegionTester(kayttajanGrid, i, j); //Luodaan SquareRegion-olio
		if (CheckSquareRegion.isRegionsBoolean() == false) {
			return false;
		}
		return true;
	}
	/**
	 * Tällä metodilla tarkistetaan, että syötetty taulukko on oikeellinen.
	 * Tätä ei välttämättä tarvittaisi reaaliaikaisen tarkistuksen vuoksi, mutta
	 * metodi toimii eräänlaisena suojaverkkona jos reaaliaikaiseen tarkistukseen tulee
	 * ongelma. 
	 * @param kayttajanGrid
	 * @param ref
	 * @return
	 */
	public static boolean syoteonvalidi(int[][] kayttajanGrid,GUI ref) {
		//Alkutilanteentarkastaja
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (kayttajanGrid[i][j]<0 || kayttajanGrid[i][j] > 9 || (kayttajanGrid[i][j]!= 0 && !syoteonValidi(i,j,kayttajanGrid))) {
/*					Tutkitaan sisältääkö vääränlaisia numeroita ja onko niin että jossakin koordinaatissa on tilanne, että
					fixattuja arvoja joita kuuluu boksiin, vaakasuoralle ja pystysuoralle viivalle on enemmän kuin 3 (kun numero ei ole 0
					joita on varmasti enemmän kuin 3 alkutilanteessa).*/
					ref.getTable().setValueAt(99999, i, j);
					return false;
				}
			}
		}
		return true;
	}
}
