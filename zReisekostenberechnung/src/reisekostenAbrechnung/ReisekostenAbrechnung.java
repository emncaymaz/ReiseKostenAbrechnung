package reisekostenAbrechnung;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

class Panel extends JPanel implements ActionListener {
	// Treiber für die Datenbak
	private String treiber = "com.mysql.cj.jdbc.Driver"; // 
	private String url     = "jdbc:mysql://127.0.0.1/kostenabrechnung?user=root"; 
	//Felder
	private double tage = 0;
	private double uebernachtung = 0;
	private double fahrkosten = 0;
	private double sonstiges = 0;		
	private final int TAGE =30;
	private final int UEBERNACHTUNG =100;
	private final int FAHRKOSTEN =1;
	private final int SONSTIGES =10;
	private String positiv = "Bitte nur positive Zahl eingeben";

	private JButton speichern = new JButton("Speichern");
	private JLabel fehler     = new JLabel();
	private JLabel positivFehler     = new JLabel();

	private JLabel mitarbeiterJL     = new JLabel("Mitarbeiter auswählen:");
	private JLabel rechnungsnummerJL = new JLabel("Rechnungsnummer:");
	private JLabel nameJL 			 = new JLabel("Name:");
	private JLabel vornameJL		 = new JLabel("Vorname:");
	private JLabel strasseJL		 = new JLabel("Straße:");
	private JLabel hausNummerJL		 = new JLabel("HausNr:");
	private JLabel plzJL 			 = new JLabel("PLZ:");
	private JLabel datumJL			 = new JLabel("Datum:");
	private JLabel ortJL			 = new JLabel("Ort:");
	
	private JLabel rechnungAusgabeJL = new JLabel();
	private JLabel nameAusgabeJL	 = new JLabel();
	private JLabel vornameAusgabeJL	 = new JLabel();
	private JLabel strasseAusgabeJL	 = new JLabel();
	private JLabel hausNummerAusgabeJL= new JLabel();
	private JLabel plzAusgabeJL		 = new JLabel();
	private JLabel ortAusgabeJL		 = new JLabel();

	private JLabel kostenartJL			  = new JLabel("KOSTENART");
	private JLabel anzahlJL				  = new JLabel("ANZAHL");
	private JLabel einzelverguetungJL	  = new JLabel("EINZELVERGÜTUNG");
	private JLabel summeJL				  = new JLabel();
	private JLabel summeverguetungJL	  = new JLabel();
	private JLabel tagegeldrechnungJL	  = new JLabel("30,00 Euro");
	private JLabel uebernachtungrechnungJL= new JLabel("100,00 Euro");
	private JLabel fahrkostenrechnungJL   = new JLabel("1,00 Euro");
	private JLabel sonstigesrechnungJL    = new JLabel("10,00 Euro");
	
	private JLabel gesamtverguetungJL		 = new JLabel("GESAMTVERGÜTUNG");
	private JLabel tagegeldausgabeJL		 = new JLabel();
	private JLabel uebernachtungausgabeJL	 = new JLabel();
	private JLabel fahrkostenausgabeJL		 = new JLabel();
	private JLabel sonstigesausgabeJL		 = new JLabel();

	private JLabel tagegeldJL		 = new JLabel("Tagegeld");
	private JLabel uebernachtungJL 	 = new JLabel("Übernachtung");
	private JLabel fahrkostenJL		 = new JLabel("Fahrkosten");
	private JLabel sonstigesJL		 = new JLabel("Sonstiges");
	
	private JTextField tagegeldJT		 = new JTextField();
	private JTextField uebernachtungJT	 = new JTextField();
	private JTextField fahrkostenJT		 = new JTextField();
	private JTextField sonstigesJT		 = new JTextField();
	private JTextField datumAusgabeJT	 = new JTextField();
	
	private JComboBox mitarbeiterJC = new JComboBox();

	public Panel() {

		setLayout(null);

		add(speichern);
		speichern.addActionListener(this);
		mitarbeiterJC.addActionListener(this);
		speichern.setBounds(250, 400, 100, 20);
		add(fehler);
		add(positivFehler);
		fehler.setBounds(200, 380, 300, 20);
		positivFehler.setBounds(200, 360, 300, 20);
		fehler.setForeground(Color.RED);
		positivFehler.setForeground(Color.RED);

		add(mitarbeiterJL);
		add(rechnungsnummerJL);
		add(nameJL);
		add(vornameJL);
		add(strasseJL);
		add(hausNummerJL);
		add(plzJL);
		add(datumJL);
		add(ortJL);
		add(kostenartJL);
		add(anzahlJL);

		add(gesamtverguetungJL);
		add(tagegeldausgabeJL);
		add(uebernachtungausgabeJL);
		add(fahrkostenausgabeJL);
		add(sonstigesausgabeJL);
		add(summeverguetungJL);
		add(einzelverguetungJL);
		add(summeJL);
		add(tagegeldrechnungJL);
		add(uebernachtungrechnungJL);
		add(fahrkostenrechnungJL);
		add(sonstigesrechnungJL);

		add(rechnungAusgabeJL);
		add(nameAusgabeJL);
		add(vornameAusgabeJL);
		add(strasseAusgabeJL);
		add(hausNummerAusgabeJL);
		add(plzAusgabeJL);
		add(ortAusgabeJL);

		add(tagegeldJL);
		add(uebernachtungJL);
		add(fahrkostenJL);
		add(sonstigesJL);

		add(tagegeldJT);
		add(uebernachtungJT);
		add(fahrkostenJT);
		add(sonstigesJT);
		add(datumAusgabeJT);

		add(mitarbeiterJC);

		mitarbeiterJL.setBounds		(10, 20, 150, 20);
		mitarbeiterJC.setBounds		(150, 20, 100, 20);
		rechnungsnummerJL.setBounds	(10, 50, 150, 20);
		nameJL.setBounds			(10, 80, 150, 20);
		strasseJL.setBounds			(10, 100, 150, 20);
		
		plzJL.setBounds				(10, 120, 150, 20);
		datumJL.setBounds			(300, 50, 150, 20);
		datumAusgabeJT.setBounds	(350, 50, 80, 20);
		vornameJL.setBounds			(300, 80, 150, 20);
		hausNummerJL.setBounds		(300, 100, 150, 20);
		ortJL.setBounds				(300, 120, 150, 20);

		rechnungAusgabeJL.setBounds	(130, 50, 200, 20);
		nameAusgabeJL.setBounds		(80, 80, 200, 20);
		strasseAusgabeJL.setBounds	(80, 100, 200, 20);
		vornameAusgabeJL.setBounds	(370, 80, 200, 20);
		hausNummerAusgabeJL.setBounds(370, 100, 200, 20);
		plzAusgabeJL.setBounds		(80, 120, 200, 20);
		ortAusgabeJL.setBounds		(370, 120, 200, 20);

		kostenartJL.setBounds	(10, 170, 100, 30);
		anzahlJL.setBounds		(130, 170, 150, 30);

		gesamtverguetungJL.setBounds      (400, 170, 210, 30);
		tagegeldausgabeJL.setBounds       (465 , 190, 100, 30);
		uebernachtungausgabeJL.setBounds  (460, 210, 100, 30);
		fahrkostenausgabeJL.setBounds     (472, 230, 100, 30);
		sonstigesausgabeJL.setBounds      (465, 250, 100, 30);

		einzelverguetungJL.setBounds     (230, 170, 210, 30);
		tagegeldrechnungJL.setBounds     (281, 190, 100, 30);
		uebernachtungrechnungJL.setBounds(275, 210, 100, 30);
		fahrkostenrechnungJL.setBounds   (288, 230, 100, 30);
		sonstigesrechnungJL.setBounds    (281, 250, 100, 30);
		summeverguetungJL.setBounds      (470, 280, 350, 30);
		summeJL.setBounds                (230, 280, 150, 30);

		tagegeldJL.setBounds		(10, 200, 100, 30);
		uebernachtungJL.setBounds	(10, 220, 100, 30);
		fahrkostenJL.setBounds		(10, 240, 100, 30);
		sonstigesJL.setBounds		(10, 260, 100, 30);

		tagegeldJT.setBounds		(130, 200, 50, 20);
		uebernachtungJT.setBounds	(130, 220, 50, 20);
		fahrkostenJT.setBounds		(130, 240, 50, 20);
		sonstigesJT.setBounds		(130, 260, 50, 20);
		//mitarbeiterJC.addItem("");
		try {
			//Externe Klasse laden
			Class.forName(treiber);
			//Verbindung zur Datenbak erstellen
			Connection c = DriverManager.getConnection(url);
			//Sql Komando erstellen und an die Datenbank schicken
			String sql = "SELECT DISTINCT nachname FROM mitarbeiter";
			
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			mitarbeiterJC.addItem("");
			while (rs.next()) {
				mitarbeiterJC.addItem(rs.getString("nachname"));
			}
			//Verbindung schließen
			c.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == mitarbeiterJC) {
			try {
				// externe Klasse laden
				Class.forName(treiber);
				// Verbindung zur datenbank herstellen
				Connection c = DriverManager.getConnection(url);
				// SQL-Komando erstellen und an die DB schicken
				String sql = String.format("SELECT rechnungsnummer, vorname, nachname, strasse, hausNr, plz, ort " +
											"FROM mitarbeiter WHERE nachname = '%s' ", 
											mitarbeiterJC.getSelectedItem());
				System.out.println(sql);
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);
				

				if( rs.next()) {
					//Aufruf der pesönlichen Daten in Panel 
					String rechnung = rs.getString(1); // 0 id,  1 rechnung, 2 name,3 vname 4 strasse, 5 plz, 6 ort, 7 datum 
					String vorname  = rs.getString(2);
					String nachname = rs.getString(3);
					String strasse  = rs.getString(4);
					String hausNr   = rs.getString(5);
					String plz      = rs.getString(6);
					String ort      = rs.getString(7);
					
					//Aufgerufene Daten in die gültigen Bereichen bringen
					rechnungAusgabeJL.setText(rechnung);
					nameAusgabeJL.setText(vorname);
					vornameAusgabeJL.setText(nachname);
					strasseAusgabeJL.setText(strasse);
					hausNummerAusgabeJL.setText(hausNr);
					plzAusgabeJL.setText(plz);
					ortAusgabeJL.setText(ort);
				}

				
					
					
					//Datum für aktuellen Tag
					Date date = Calendar.getInstance().getTime();
					SimpleDateFormat datum = new SimpleDateFormat("dd.MM.yyyy");
					String heute = datum.format(date);
					datumAusgabeJT.setText(heute);
					
					//Datenbank schliessen
					c.close();
					//Fehlermeldung für Treiber	
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				//Fehlermeldung für Datebase
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		//Wenn ich "speichern klicke, passiert folgenden "
		if (e.getSource() == speichern) {
			if (fehler(tagegeldJT.getText(),  uebernachtungJT.getText(),
					   fahrkostenJT.getText(),  sonstigesJT.getText())) {
				
				fehler.setText("Achtung!!! Nur gültige Zahl eingeben");
				return;
			}
				
			else {
				fehler.setText("");
			}
			
			
			//Umwandlung der Eingabe, Eingabe vom Textfield überprüfen, ob es außer Zahlen was eingegeben wurde
			if(!tagegeldJT.getText().equals("")) 
				tage = Double.parseDouble(tagegeldJT.getText());
			if(!uebernachtungJT.getText().equals("")) 
				uebernachtung = Double.parseDouble(uebernachtungJT.getText());
			if(!fahrkostenJT.getText().equals("")) 
				fahrkosten    = Double.parseDouble(fahrkostenJT.getText());
			if(!sonstigesJT.getText().equals("") )
				sonstiges     = Double.parseDouble(sonstigesJT.getText());
				
			//Eingabe vom Textfield überprüfen, ob es negative Zahl eingegeben wurde
			positivFehler.setText("");
			if(tage < 0  ) { 
				positivFehler.setText(positiv);
				tage = 0;
			}
				
			if(uebernachtung < 0  ) { 
				positivFehler.setText(positiv);
				uebernachtung = 0;
			}
			if(fahrkosten < 0  ) {
				positivFehler.setText(positiv);
				fahrkosten = 0;
			}
			if(sonstiges < 0  ) { 
				positivFehler.setText(positiv);
				sonstiges = 0;
			}
				
			//Ausgabe formatieren und anzeigen
			tagegeldausgabeJL.setText     (String.format("%.2f", tage*TAGE)                     + " Euro");
			uebernachtungausgabeJL.setText(String.format("%.2f", uebernachtung * UEBERNACHTUNG) + " Euro");
			fahrkostenausgabeJL.setText   (String.format("%.2f", fahrkosten * FAHRKOSTEN)       + " Euro");
			sonstigesausgabeJL.setText    (String.format("%.2f", sonstiges * SONSTIGES)         + " Euro");
			summeJL.setText("Summe der Vergütungen");
			summeverguetungJL.setText(String.format("%.2f",(tage*TAGE                    +
															uebernachtung*UEBERNACHTUNG  +
												            fahrkosten * FAHRKOSTEN      +
												            sonstiges*SONSTIGES) )       + " Euro"  );
			
			try {
				// externe Klasse laden
				Class.forName(treiber);

				// Verbindung zur datenbank herstellen
				Connection c = DriverManager.getConnection(url);

				// SQL-Komando erstellen und an die DB schicken
				String sql = String.format("INSERT INTO rechnung(tagegeld, uebernachtung, fahrtkosten, sonstiges, summeverguetung) " + 
						                   "VALUES('%s','%s', '%s', '%s', '%s')",
											tagegeldausgabeJL.getText(),  
											uebernachtungausgabeJL.getText(), 
											fahrkostenausgabeJL.getText(), 
											sonstigesausgabeJL.getText(), 
											summeverguetungJL.getText());

				
				Statement	s = c.createStatement();
				s.executeUpdate(sql);
				//Mitarbeiter Bereiche erstellen und an der Datenbank schicken
				sql = String.format("INSERT INTO mitarbeiter(rechnungsnummer, vorname, nachname, strasse, hausNr, plz, ort, datum ) " +
						 "VALUES('%s', '%s', '%s','%s', '%s', '%s','%s', '%s' )",
						 rechnungAusgabeJL.getText(),
						 nameAusgabeJL.getText(),
						 vornameAusgabeJL.getText(),
						 strasseAusgabeJL.getText(),
						 hausNummerAusgabeJL.getText(),
						 plzAusgabeJL.getText(),
						 ortAusgabeJL.getText(),
						 datumAusgabeJT.getText());
				
				
				s = c.createStatement();
				s.executeUpdate(sql);
				//Anzahl Bereiche an der Datenbank schicken
				sql = String.format("INSERT INTO anzahl(tagegeld, uebernachtung, fahrkosten, sonstiges, summeanzahl) " +
									"VALUES('%s', '%s', '%s', '%s', '%s' )",
									tagegeldJT.getText(),
									uebernachtungJT.getText(),
									fahrkostenJT.getText(),
									sonstigesJT.getText(),
									(int)(tage+ uebernachtung + fahrkosten + sonstiges)
									);
				
				s = c.createStatement();
				s.executeUpdate(sql);
				//DB-Verbindung schliessen
				c.close();
		
				
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
	}


	private boolean fehler(String tagegeld, String uebernachtung, String fahrkosten, String sonstiges) {

		boolean fehler = false;
		
			try {
			
				if(!tagegeld.equals("") )
					Double.parseDouble(tagegeld);
				if(!uebernachtung.equals(""))
					Double.parseDouble(uebernachtung);
				if(!fahrkosten.equals(""))
					Double.parseDouble(fahrkosten);
				if(!sonstiges.equals(""))
					Double.parseDouble(sonstiges);

			} catch (NumberFormatException nfe) {
				fehler = true;
			}
		
		return fehler;
	}
}

public class ReisekostenAbrechnung extends JFrame {

	public ReisekostenAbrechnung(String titel) {
		super(titel);
		setBounds(600,400, 570, 470);
		setLocation(900, 70);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Panel());
		setVisible(true);

	}

	public static void main(String[] args) {
		new ReisekostenAbrechnung("Reisekostenabrechnung");
		
		
		 
		

	}

}
