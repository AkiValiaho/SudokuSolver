package sudokusolver;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
/**
 * Luokkaa GUI käytetään käyttöliittymän käynnistämiseksi, sekä 
 * syötteiden vastaanottamiseksi ja näyttämiseksi
 * @author Aki
 * @version 17.04.2014
 *
 */
public class GUI extends JFrame {
	private JPanel contentPane;
	private JTable table;
	JToolBar toolBar;
	JComboBox<String> combobox;
	public GUI refguiGUiGui;
	/**
	 * Ohjelma käynnistetään mainin kautta.
	 * Asetetaan myös ikkunaan liittyvä ominaisuuksia.
	 * @param args
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setSize(373,352);
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 *  GUI-luokan konstruktori, joka rakentaa ikkunan.
	 *  Ikkuna koostuu neljästä erilaisesta paneelista:
	 *  contentpane-paneeliin lisätään työkalupalkki sekä JTable,
	 *  joka on omassa PanelTable-paneelissaan. Panel-nimiseen paneeliin
	 *  lisätään ohjelman toiminnallisuutta ohjaavat painikenapit.
	 *  Tässä määritellään myös painikkeille omat ActionListenerit.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(7, 250, 350, 33);
		contentPane.add(panel);
		Nollaabuttonkuuntelija nollaabuttonkuuntelija = new Nollaabuttonkuuntelija(this);
		Laskebuttonkuuntelija laskebuttonkuuntelija = new Laskebuttonkuuntelija(this);
		Generoibuttonkuuntelija generoibuttonkuuntelija = new Generoibuttonkuuntelija(this);
		JButton btnGeneroi = new JButton("Generoi");
		btnGeneroi.setToolTipText("Generoi uusi laskettava sudoku");
		btnGeneroi.addActionListener(generoibuttonkuuntelija);
		panel.add(btnGeneroi);
		JButton btnLaskeSudoku = new JButton("Laske Sudoku");
		panel.add(btnLaskeSudoku);
		btnLaskeSudoku.addActionListener(laskebuttonkuuntelija);
		JButton btnNollaaTaulukko = new JButton("Nollaa taulukko");
		panel.add(btnNollaaTaulukko);
		btnNollaaTaulukko.addActionListener(nollaabuttonkuuntelija);
		JPanel PanelTable = new JPanel();
		PanelTable.setBounds(94, 50,200, 200);
		HidastuAlgoritmi.kaynnissa = false;
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (HidastuAlgoritmi.kaynnissa == true) {
					return false;
				} else {
					return true;
				}
			}
		};
		refguiGUiGui = this;
		table.setBackground(new Color(255, 255, 255));
		table.setToolTipText("Klikkaa ja kirjoita ruutuun annettu numero ja liiku hiirellÄ tai nuolilla, vain numeroita hyväksytään!");
		table.setCellSelectionEnabled(true);
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null},
					},
					new String[] {
						"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
					}
				) {
					Class[] columnTypes = new Class[] {
						Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class
					};
					public Class getColumnClass(final int columnIndex) {
						return columnTypes[columnIndex];
					}
				};
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				int row = arg0.getFirstRow();
				int column = arg0.getColumn();
				DefaultTableModel defaultTableModel = (DefaultTableModel) refguiGUiGui.getTable().getModel();
				int[][] kayttajanGrid = {{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0}};
				for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
					for (int j = 0; j < defaultTableModel.getColumnCount(); j++) {
							if (defaultTableModel.getValueAt(i, j) == null) {
								kayttajanGrid[i][j] = 0;
							} else {
								kayttajanGrid[i][j] = (int) defaultTableModel.getValueAt(i, j);
							}
					}
				}
				String cellString = String.valueOf(table.getValueAt(row, column));
				if (HidastuAlgoritmi.kaynnissa == false) {
					try {
						if (Integer.parseInt(cellString) <10 && Integer.parseInt(cellString) > 0 && Controller.syoteonValidi(row, column, kayttajanGrid)) {
							return;
						}else {
							table.setValueAt(null, row, column);
							Toolkit.getDefaultToolkit().beep();
						}
					} catch (Exception e) {
						// TODO: handle exception
						Toolkit.getDefaultToolkit().beep();
					}
				} else {
					try {
						if (Integer.parseInt(cellString) <10 && Integer.parseInt(cellString) > 0) {
							return;
						}else {
							table.setValueAt(null, row, column);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		table.setModel(model);
			table.setRowHeight(20);
			for (int i = 0; i < 9; i++) {
				table.getColumnModel().getColumn(i).setPreferredWidth(20);
			}
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		PanelTable.add(table);
		contentPane.add(PanelTable);
		toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 368, 30);
		contentPane.add(toolBar);
		addButtons(toolBar);
	}
	/**Tässä metodissa lisätään työkalupalkkiin toiminnot
	 * ja niiden tarvisemat kuuntelijat
	 * @param toolBar
	 */
	protected final void addButtons(final JToolBar toolBar) {
		JButton button = null;
		String[] valuesforbox = {"Kyllä","Ei"};
		combobox = new JComboBox<String>(valuesforbox);
		JLabel combolabel = new JLabel("Hidastus: ");
		button= makeButton(null, "Tallenna tiedostoon", "Tallenna");
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseClicked(final MouseEvent e) {
				HidastuAlgoritmi.kaynnissa = false;
				String kayttajanOldString1 = null;
				PrintWriter outputstream = null;
				// TODO Auto-generated method stub
			    JFileChooser chooser = new JFileChooser("usr.dir");
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "txt-tiedosto", "txt");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showSaveDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			            kayttajanOldString1=chooser.getSelectedFile().getName();	
			    }
				File file = new File("src/sudokusolver/"+kayttajanOldString1);
				try {
					outputstream = new PrintWriter(new FileWriter(file)) ;
					StringBuilder line;
					for (int i = 0; i < 9; i++) {
						line = new StringBuilder();
						for (int j = 0; j < 9; j++) {
							if (table.getValueAt(i, j) == null) {
								line.append(String.format("0 "));
							}
							else {
								line.append(String.format("%s ", table.getValueAt(i, j)));
							}
						}
						outputstream.println(line);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				} finally {
					if (outputstream != null) {
		                outputstream.close();
		            }
				}
			}
		});
		toolBar.add(button);
		toolBar.add(combolabel);
		toolBar.add(combobox);
		JPanel glasspane = new GlassPanel();
		setGlassPane(glasspane);
		glasspane.setOpaque(false);
		glasspane.setVisible(true);
		glasspane.setSize(new Dimension(600,600));
	}
	/**
	 * Määrittelee addbuttonsin painikkeet
	 * @param actionCommand
	 * @param tooltipText
	 * @param altText
	 * @return JButton button
	 */
	protected final JButton makeButton(final String actionCommand, final String tooltipText, final String altText) {
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(tooltipText);
		button.setText(altText);
		return button;
	}
	/**
	 * Palauttaa luokassa käytetyn JTablen
	 * @return
	 */
	public final JTable getTable() {
		return table;
	}
	public final void setTable(final JTable table) {
		this.table = table;
	}
	/**
	 * Päivittää ikkunassa näytettävän JTablen arvot annetusta gridistä
	 * @param ratkaistuGrid
	 */
	public final void paivitaTable(final int[][] ratkaistuGrid) {
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				table.setValueAt(ratkaistuGrid[i][j], i, j);
		}
	}
}
}
/**
 * Luokka välittää käyttäjän syöttämän sudokuongelman
 * JTablesta Controllerille ja sitä kautta algoritmille
 * ratkaistavaksi
 * @author Aki
 *
 */
class Laskebuttonkuuntelija implements ActionListener {
	private GUI refGui;
	/**Laskebuttonkuuntelijalle määritelty
	 * konstruktori, joka luodessa ottaa
	 * parametrina GUI-luokan olion referenssin,
	 * jotta taulukon arvojen saaminen onnistuu
	 * @param refGUI
	 */
	public Laskebuttonkuuntelija(final GUI refGUI) {
		this.refGui = refGUI;
		}
	public void actionPerformed(final ActionEvent event) {
	try {
			if (!Controller.solver.interrupted()) {
			Controller.solver.interrupt();
		}
	} catch (NullPointerException e) {
	}
	HidastuAlgoritmi.kaynnissa = true;
		int[][] grid = {{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0}};
		DefaultTableModel defaultTableModel = (DefaultTableModel) refGui.getTable().getModel();
		for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
			for (int j = 0; j < defaultTableModel.getColumnCount(); j++) {
					if (defaultTableModel.getValueAt(i, j) == null) {
						grid[i][j] = 0;
					} else {
						grid[i][j] = (int) defaultTableModel.getValueAt(i, j);
					}
			}
		}
		Controller.Startup(grid,refGui,(String)refGui.combobox.getSelectedItem());
	}
}
/**
 * Luokka ohjaa Generoi-painikkeen käyttöä. Painettaessa painiketta
 * generoidaan satunnainen sudokuongelma.
 * @author Aki
 *
 */
class Generoibuttonkuuntelija implements ActionListener {
	private GUI refGui;
	private int[][] Generatedgrid;
	/**Konstruktori tarvitsee refGui:n, jotta ikkunassa
	 * näytettävän taulukon päivittäminen onnistuisi
	 * @param refGui
	 */
	public Generoibuttonkuuntelija(final GUI refGui) {
		this.refGui = refGui;
	}
	public void actionPerformed(final ActionEvent event) {
		try {
			if (!Controller.solver.interrupted()) {
				Controller.solver.interrupt();
				HidastuAlgoritmi.kaynnissa = false;
			}
		} catch (NullPointerException e) {
		}
		int[][] grid = {{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0}};
		Integer readynumbers = 0;
		readynumbers = 80;
		Generatedgrid = Controller.generoi(grid, readynumbers, refGui);
		for (int i = 0; i < refGui.getTable().getRowCount(); i++) {
			for (int j = 0; j < refGui.getTable().getColumnCount(); j++) {
				if (Generatedgrid[i][j] == 0) {
					refGui.getTable().setValueAt(null, i, j);
				} else {
					refGui.getTable().setValueAt(Generatedgrid[i][j], i, j);
				}
			}
		}
	}
}
/**
 * @author Aki
 * Piirretään GlassPanelilla sudokun viivat JTablen päälle, jotta
 * arvojen muokkaus ei vaikuttaisi niihin.
 */
class GlassPanel extends JPanel {
	public void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D)g;
		int width = 2;
		graphics2d.setColor(Color.BLACK);
		graphics2d.setStroke(new BasicStroke(width));
		graphics2d.drawLine(164, 55, 164, 233);
		graphics2d.drawLine(225, 55, 225, 233);
		graphics2d.drawLine(106, 173, 284, 173);
		graphics2d.drawLine(106, 115, 284, 115);
	}
}
/**
 * @author Aki
 * Lisää tablen tyhjentävän toiminnallisuuden Nollaa-näppäimeen.
 */
class Nollaabuttonkuuntelija implements ActionListener {
	private GUI refGui;
	/**
	 * Konstruktori
	 * @param refGui ottaa arvokseen viittauksen GUI-luokan olioon 
	 * siinä olevan JTablen muokkaamiseksi.
	 * 
	 */
	public Nollaabuttonkuuntelija(final GUI refGui) {
		this.refGui = refGui;
	}
	public void actionPerformed(final ActionEvent event) {
	try {
			if (!Controller.solver.interrupted()) {
			Controller.solver.interrupt();
			HidastuAlgoritmi.kaynnissa = false;
		}
	} catch (NullPointerException e) {
	}
		DefaultTableModel defaultTableModel = (DefaultTableModel) refGui.getTable().getModel();
		for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
			for (int j = 0; j < defaultTableModel.getColumnCount(); j++) {
				refGui.getTable().setValueAt(null, i, j);
			}
		}
	}
}
