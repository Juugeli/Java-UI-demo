package muistilistaaja;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GuideFrame extends JFrame{
    
    JTextArea guideTextArea = new JTextArea();
    
    // Ohje-ikkuna
    public GuideFrame() {
        
        // Luodaan frame
        JFrame guideFrame = new JFrame("Ohje");
        guideFrame.setVisible(true);
        guideFrame.setResizable(false);
        guideFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        guideFrame.setLocationRelativeTo(null);
        guideFrame.setSize(400,450);
        
        // Paneeli, johon asetetaan sisältö ja Layout manager
        JPanel guidePanel = new JPanel();
        guidePanel.setLayout(new GridBagLayout());
        
        // Rullattava tekstikenttä
        guideTextArea.setLineWrap(true); // Teksti siirtyy uudelle riville automaattisesti.
        guideTextArea.setText(getInstructions());
        guideTextArea.setCaretPosition(0);
        final JScrollPane guideScrollableTextArea = new JScrollPane(guideTextArea);
        Dimension size = new Dimension(25,50);
        guideScrollableTextArea.setPreferredSize(size);
        
        guideScrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        guideScrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Nappi
        JButton guideCloseBtn = new JButton("Sulje");
        
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0;
        gc.weighty = 0;
        
        gc.ipadx = 300;
        gc.ipady = 300;
        
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 0;
        //gc.gridwidth = 2; TOIMIVA
        gc.gridwidth = 4;
        
        //gc.weighty = 1;
        //gc.weightx = 1;
        guidePanel.add(guideScrollableTextArea, gc);
        
        // Nollataan nappia varten
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.weighty = 0;
        gc.weightx = 0;
        gc.ipadx = 0;
        gc.ipady = 0;
        
        gc.gridx = 0;
        gc.gridy = 1;
        
        guidePanel.add(guideCloseBtn, gc);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Lisätään paneeli frameen
        guideFrame.add(guidePanel);
        
        // Sulje-painikkeen toiminta
        guideCloseBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                guideFrame.dispose();
            }
        });
        
    }
    
    String getInstructions(){
        
        String text = 
"Muistilistaajan käyttöohje\n" +
"\n" +
"Sisällysluettelo:\n" +
"\n" +
"1.Yläpainikkeet\n" +
"1.1 Uusi muistilistaaja\n" +
"1.2 Avaa muistilistaaja...\n" +
"1.3 Tallenna muistilistaaja\n" +
"1.4 Tallenna muistilistaaja nimellä...\n" +
"1.5 Lopeta\n" +
"1.6 Muokkaa kategorioita\n" +
"1.7 Ohje\n" +
"2. Ikkunan painikkeet\n" +
"2.1 Tekstinapit\n" +
"2.2 Tallenna\n" +
"2.3 Poista\n" +
"\n" +
"---\n" +
"1.Yläpainikkeet\n" +
"\n" +
"1.1 Uusi muistilistaaja\n" +
"\n" +
"- \"Uusi muistilistaaja\" -valinta löytyy Tiedosto -menusta.\n" +
"- \"Uusi muistilistaaja\" -valinnalla voit luoda uuden muistilistaajan.\n" +
"\n" +
"\n" +
"1.2 Avaa muistilistaaja...\n" +
"\n" +
"- \"Avaa muistilistaaja...\" -valinta löytyy Tiedosto -menusta.\n" +
"- \"Avaa muistilistaaja...\" -valinnalla voit avata jo olemassa olevan muistilistaajan.\n" +
"\n" +
"\n" +
"1.3 Tallenna muistilistaaja\n" +
"\n" +
"- \"Tallenna muistilistaaja\" -valinta löytyy Tiedosto -menusta.\n" +
"- \"Tallenna muistilistaaja\" -valinnalla voit tallentaa muistilistaajan, jos muistilistaajan tallennustiedosto on määritetty.\n" +
"\n" +
"\n" +
"1.4 Tallenna muistilistaaja nimellä...\n" +
"\n" +
"- \"Tallenna muistilistaaja nimellä...\" -valinta löytyy Tiedosto -menusta.\n" +
"- \"Tallenna muistilistaaja nimellä...\" -valinnalla voit määrittää muistilistaajan tallennustiedoston ja nimetä sen.\n" +
"    -> Jos muistilistaajan tallennustiedosto on määritetty, \"Tallenna muistilistaaja\" -valinta tallentaa tähän tiedostoon.\n" +
"\n" +
"\n" +
"1.5 Lopeta\n" +
"\n" +
"- \"Lopeta\" -valinta löytyy Tiedosto menusta\n" +
"- \"Lopeta\" -valinnalla voit sulkea Muistilistaajan.\n" +
"- Muistilistaajan lopettaminen onnistuu myös pääikkunassa oikean yläkulman ruksia painamalla.\n" +
"\n" +
"\n" +
"1.6 Muokkaa kategorioita\n" +
"\n" +
"- \"Muokkaa kategorioita\" -valinta löytyy Muokkaa -menusta\n" +
"- \"Muokkaa kategorioita\" -valinnalla voit muokata näkyviä kategorioita ja kategorioiden nimiä.\n" +
"- Huomaa! Jos yhtään kategoriaa ei ole valittuna, ikkunassa ei näy mitään!\n" +
"\n" +
"\n" +
"1.7 Ohje\n" +
"\n" +
"- \"Ohje\" -valinta löytyy Ohje -menusta\n" +
"- \"Ohje\" -valinnalla löydät Muistilistaaja sovelluksen ohjeen.\n" +
"\n" +
"\n" +
"\n" +
"2. Ikkunan painikkeet\n" +
"\n" +
"2.1 Tekstinapit\n" +
"\n" +
"- Uusia tekstinappeja voi luoda painamalla sovelluksen vasemmassa reunassa kategorioiden kohdalla olevia \"+\"-painikkeita.\n" +
"- Painikkeita voi nimetä oikealla tekstikentän yläpuolella olevilla kentillä.\n" +
"- Tekstinappeja voi olla 4 per kategoria eli kaiken kaikkiaan 12 kappaletta.\n" +
"\n" +
"\n" +
"2.2 Tallenna\n" +
"\n" +
"- Keskeltä alhaalta löytyvällä \"Tallenna\"-painikkeella voidaan tallentaa tehdyt muutokset.\n" +
"- Toiminnaltaan tämä \"Tallenna\"-painike vastaa Tiedosto -menun alta löytyviä \"Tallenna\" ja \"Tallenna nimellä...\" toiminnallisuuksia.\n" +
"\n" +
"\n" +
"2.3 Poista\n" +
"\n" +
"- Oikealta alhaalta löytyvällä \"Poista\"-painikkeella voidaan poistaa valittu nappiteksti.\n" +
"\n" +
"";

        return text;
    }
}
