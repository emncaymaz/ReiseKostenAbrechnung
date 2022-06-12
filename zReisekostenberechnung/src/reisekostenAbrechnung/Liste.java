package reisekostenAbrechnung;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Panel1 extends JPanel implements ActionListener {
	// Treiber für die Datenbak
	private String treiber = "com.mysql.cj.jdbc.Driver"; // JDBC= Java Database Connectivity
	private String url = "jdbc:mysql://127.0.0.1/kunden?user=root";
	
	private JButton schreiben   = new JButton("schreiben");
	private JButton anzeigen    = new JButton("anzeigen");
	private JButton loeschen    = new JButton("löschen");
	 
	
	private JLabel listeLabName = new JLabel("Name:");
	private JLabel listeLabOrt  = new JLabel("Ort:");
	private JLabel nameLab      = new JLabel("Name:");
	private JLabel ortLab       = new JLabel("Ort:");
	private JLabel strasseLab   = new JLabel("Straße:");
	private JLabel plzLab 	    = new JLabel("PLZ:");
	private JLabel eingabe      = new JLabel("ADRESSLISTE");
	private JLabel fehler       = new JLabel("kein fehler...");
	
	private JComboBox listeName = new JComboBox();
	private JComboBox listeOrt  = new JComboBox();
	
	private JTextField name     = new JTextField();
	private JTextField ort      = new JTextField(); 
	private JTextField strasse  = new JTextField();
	private JTextField plz      = new JTextField(); 

	public Panel1() {
		
		setLayout(null);
		add(nameLab);
        add(name);
        add(fehler);
        add(ortLab);
        add(ort);
        add(strasseLab);
        add(strasse);
        add(plzLab);
        add(plz);
        add(schreiben);
        add(anzeigen);
        add(loeschen);
        add(listeLabName);
        add(listeName);
        add(listeLabOrt);
        add(listeOrt);
        add(eingabe);
        
        eingabe.setBounds(120, -5, 150, 30);
        eingabe.setFont(new Font("Arial", 3,12));
        eingabe.setForeground(Color.BLUE);
        
        schreiben.addActionListener(this);
        anzeigen.addActionListener(this);
        loeschen.addActionListener(this);
        
        nameLab.setBounds   (20, 20,  100, 20);
        ortLab.setBounds    (20, 50,  100, 20);
        strasseLab.setBounds(20, 80,  100, 20);
        plzLab.setBounds    (20, 110, 100, 20);
        fehler.setBounds(250, 20,  91, 20);
        
        name.setBounds   (80, 20,  150, 20);
        ort.setBounds    (80, 50,  150, 20);
        strasse.setBounds(80, 80,  150, 20);
        plz.setBounds    (80, 110, 150, 20);
        
        schreiben.setBounds(250, 80,  91, 20);
        loeschen.setBounds (250, 110, 91, 20);
        
        listeLabName.setBounds(20, 150, 100, 20);
        listeLabOrt.setBounds (20, 180, 100, 20);
        
        anzeigen.setBounds   (180, 150, 91, 20);
        listeName.setBounds  (80,  150,  91, 20);
        listeOrt.setBounds   (80,  180,  91, 20);
        
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == anzeigen) {
			try {
				
				// externe Klasse laden
				Class.forName(treiber);

			
				// Verbindung zur datenbank herstellen
				Connection c = DriverManager.getConnection(url);
				
				// SQL-Komando erstellen und an die DB schicken
				String sql = String.format("SELECT name,ort FROM test2");
				System.out.println("sql: " +sql);
				Statement s = c.createStatement();
				ResultSet rs=  s.executeQuery(sql);
				
				// JComboBoxen leeren
				listeName.removeAllItems();
				listeOrt.removeAllItems();
				while(rs.next()) {
					String temp = rs.getString("name");
					String temp1 = rs.getString("ort");	
					listeName.addItem(temp);
					listeOrt.addItem(temp1);
				
				}
				
				//verbindung schliessen
				c.close();

			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		if(e.getSource() == loeschen)
		{
			try {
				//Aufgabe: Name, ort  in die Tabelle kunden in der Datenbank TEst2 schreiben
				// Name und ort werden aus 2 TExtboxen lesen
				// externe Klasse laden
				Class.forName(treiber);
				
				
				// Verbindung zur datenbank herstellen
				Connection c = DriverManager.getConnection(url);
				
				// SQL-Komando erstellen und an die DB schicken
				String sql =String.format("DELETE FROM test2 WHERE name = '%s'" , name.getText()) ;
				System.out.println("sql: " +sql);
				
				Statement s = c.createStatement();
				s.executeUpdate(sql);
				
				
				//verbindung schliessen
				c.close();
				
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
				System.err.println("fehler");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.err.println("fehler");
			}
			
		}
		
		if (e.getSource() == schreiben ) { 
			try {
				//Aufgabe: Name, ort  in die Tabelle kunden in der Datenbank TEst2 schreiben
				 // Name und ort werden aus 2 TExtboxen lesen
				// externe Klasse laden
				if( fehler(name.getText(), ort.getText(), strasse.getText(), plz.getText()) )
				{
					fehler.setText("fehler bei Eingabe");
					return;
				}
				Class.forName(treiber);

			
				// Verbindung zur datenbank herstellen
				Connection c = DriverManager.getConnection(url);
				
				// SQL-Komando erstellen und an die DB schicken
				String sql = String.format("INSERT INTO test2(name,ort,strasse, plz) VALUES('%s', '%s', '%s', '%s')", 
						                          name.getText(), 
						                          ort.getText(),
						                          strasse.getText(), 
						                          plz.getText());
				System.out.println("sql: " +sql);
				Statement s = c.createStatement();
				s.executeUpdate(sql);
				

				//verbindung schliessen
				c.close();

			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}


	private boolean fehler(String name, String ort, String strasse, String plz) {
		
		boolean fehler = false;
		
		if( name != null && name.equals("") )
			fehler = true;
		if( ort != null && ort.equals("") )
			fehler = true;
		if( strasse != null && strasse.equals("") )
			fehler = true;
		// auch strasse und ort sollen nicht leer sein
		
		// plz muß Zahl enthalten
		if( plz != null)
		{
			try {
			  Integer.parseInt(plz);
			  
			}
			catch( NumberFormatException nfe)
			{
				fehler = true;
			}
		}
		return fehler;
	}
}

public class Liste extends JFrame {

	public Liste(String titel) {
		super(titel);
		setBounds(200, 300, 400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Panel1());
		setVisible(true);

	}

	public static void main(String[] args) {
		new Liste("Datenbank");

	}

}

