package muistilistaaja;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {
    
    // Paneelit
    private JPanel detailsPanel;
    private JPanel detailsPanel2;
    private JPanel detailsPanel3;
    
    // Kategoriat
    String cat1,cat2,cat3;
    boolean cat1visib,cat2visib,cat3visib;
    int cat1bs, cat2bs, cat3bs;
    
    // Tekstit
    String          p1b1t,p1b2t,p1b3t,p1b4t,
                    p2b1t,p2b2t,p2b3t,p2b4t,
                    p3b1t,p3b2t,p3b3t,p3b4t;
    
    // file data list
    List fileCatList = new ArrayList();
    List fileButtonTextList = new ArrayList();
    List fileTextList = new ArrayList();
    
    List fileCat1ButtonTextList = new ArrayList();
    List fileCat2ButtonTextList = new ArrayList();
    List fileCat3ButtonTextList = new ArrayList();
    
    List fileCat1TextList = new ArrayList();
    List fileCat2TextList = new ArrayList();
    List fileCat3TextList = new ArrayList();
    
    // Nappitekstit
    String          p1b1bt,p1b2bt,p1b3bt,p1b4bt,
                    p2b1bt,p2b2bt,p2b3bt,p2b4bt,
                    p3b1bt,p3b2bt,p3b3bt,p3b4bt;
    
    // Napit
    JToggleButton   p1b1,p1b2,p1b3,p1b4,
                    p2b1,p2b2,p2b3,p2b4,
                    p3b1,p3b2,p3b3,p3b4;
    
    public List cat1ButtonList = new ArrayList();
    public List cat2ButtonList = new ArrayList();
    public List cat3ButtonList = new ArrayList();
    
    String cat1ButtonListString[]={"p1b1","p1b2","p1b3","p1b4"};
    String cat2ButtonListString[]={"p2b1","p2b2","p2b3","p2b4"};
    String cat3ButtonListString[]={"p3b1","p3b2","p3b3","p3b4"};
    
    public List drawableButtons = new ArrayList();
    
    private JPanel textPanel;
    JTextArea textArea;
    JLabel categoryLabel;
    JTextField categoryField;
    
    private EditCategoryDialog catSettings;
    boolean catSettingsFirstTime;

    String defaultSaveFile;
    
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;

    public String pressedButton;
    
    public MainFrame(String title){
        super(title);
        
        reset();
        
        textArea = new JTextArea();
        categoryLabel = new JLabel();
        categoryField = new JTextField();
        
        textArea.setText("");
        categoryLabel.setText("CAT:");
        categoryField.setText("BUTT");

        buildMenu();
        textPanel = setTextPanel();
        setup();
    }
    
    public JPanel setTextPanel(){
        
        JPanel textpanel = new JPanel();
        
        Dimension size = getPreferredSize();
        setPreferredSize(size);

        textArea.setLineWrap(true); // Teksti siirtyy uudelle riville automaattisesti.
        
        final JScrollPane scroll = new JScrollPane(textArea);
        
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton saveBtn = new JButton("Tallenna");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);
        
        try {
            Image img = ImageIO.read(getClass().getResource("resources/tallenna.bmp"));
            saveBtn.setIcon(new ImageIcon(img));
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null,"Tallenna -ikonin avaus epäonnistui","Varoitus",JOptionPane.ERROR_MESSAGE);
        }
        
        JButton delBtn = new JButton("Poista");
        delBtn.setActionCommand("del");
        delBtn.addActionListener(this);
        try {
            Image img = ImageIO.read(getClass().getResource("resources/poista.bmp"));
            delBtn.setIcon(new ImageIcon(img));
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null,"Poista -ikonin avaus epäonnistui","Varoitus",JOptionPane.ERROR_MESSAGE);
        }
        
        textpanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // Kategoria label
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 1;
        gc.gridy = 0;
        textpanel.add(categoryLabel,gc);
        
        // Kategoria teksti
        gc.anchor = GridBagConstraints.LINE_START;
        gc.ipadx = 50;
        gc.gridx = 2;
        gc.gridy = 0;
        textpanel.add(categoryField,gc);
        
        gc.ipadx = 300;
        gc.ipady = 300;
        
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 4;
        textpanel.add(scroll,gc);
        
        gc.weighty = 0;
        gc.weightx = 0;
        gc.ipadx = 0;
        gc.ipady = 0;
        
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        textpanel.add(saveBtn, gc);
        
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        
        gc.gridx = 3;
        gc.gridy = 2;
        textpanel.add(delBtn, gc);
        
        return textpanel;
    }
    
    public void reset(){
        
        fileCat1ButtonTextList.clear();
        fileCat1TextList.clear();
        fileCat2ButtonTextList.clear();
        fileCat2TextList.clear();
        fileCat3ButtonTextList.clear();
        fileCat3TextList.clear();
        cat1ButtonList.clear();
        cat2ButtonList.clear();
        cat3ButtonList.clear();
        
        catSettingsFirstTime = true;
        defaultSaveFile = null; 
        
        pressedButton = null;
        
        // Initialize lists
        cat1 = "CAT1";
        cat2 = "CAT2";
        cat3 = "CAT3";
        
        cat1visib = true;
        cat2visib = true;
        cat3visib = true;
        
        cat1bs = 0;
        cat2bs = 0;
        cat3bs = 0;
        
        for(int i=0; i < 4; i++){ // ALUSTETAAN LISTAT
            cat1ButtonList.add(i,cat1ButtonListString[i]);
        }
        for(int i=0; i < 4; i++){ // ALUSTETAAN LISTAT
            cat2ButtonList.add(i,cat2ButtonListString[i]);
        }
        for(int i=0; i < 4; i++){ // ALUSTETAAN LISTAT
            cat3ButtonList.add(i,cat3ButtonListString[i]);
        }
        
        for(int i=0; i < 4; i++){ // ALUSTETAAN LISTAT
            fileCat1ButtonTextList.add(i,"NODRAW");
            fileCat1TextList.add(i,"NODRAW");
        }
        
        for(int i=0; i < 4; i++){ // ALUSTETAAN LISTAT
            fileCat2ButtonTextList.add(i,"NODRAW");
            fileCat2TextList.add(i,"NODRAW");
        }
        
        for(int i=0; i < 4; i++){ // ALUSTETAAN LISTAT
            fileCat3ButtonTextList.add(i,"NODRAW");
            fileCat3TextList.add(i,"NODRAW");
        }
    }
    
    
    public void setup(){
        
        // Alusta kaikki asiat
        detailsPanel = setDetailPanel1();
        detailsPanel2 = setDetailPanel2();
        detailsPanel3 = setDetailPanel3();

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        Container c = getContentPane();
        gc.anchor = GridBagConstraints.CENTER;
        
        gc.ipadx = 200;
        gc.ipady = 300;
        
        gc.weighty = 0.5;
        gc.weightx = 0.5;

        gc.gridx = 0;
        gc.gridy = 0;
        c.add(detailsPanel,gc);
        
        // Kategoria-asetuksen tarkistus!
        if(cat1visib == true){ // jos on false
            detailsPanel.setVisible(true);
        }else{
            detailsPanel.setVisible(false);
        }
        
        gc.gridx = 0;
        gc.gridy = 1;
        c.add(detailsPanel2,gc);
        
        if(cat2visib == true){ // jos on false
            detailsPanel2.setVisible(true);
        }else{
            detailsPanel2.setVisible(false);
        }
        
        gc.gridx = 0;
        gc.gridy = 2;
        c.add(detailsPanel3,gc);
        
        if(cat3visib == true){ // jos on false
            detailsPanel3.setVisible(true);
        }else{
            detailsPanel3.setVisible(false);
        }
        
        gc.ipadx = 300;
        gc.ipady = 300;
        gc.gridx = 2;
        gc.gridy = 0;
        gc.weighty = 1;
        gc.weightx = 1;
        gc.gridheight = 3;
        gc.gridwidth = 2;
        c.add(textPanel,gc);
        
        if(cat1visib == false && cat2visib == false && cat3visib == false){
            textPanel.setVisible(false);
            JOptionPane.showMessageDialog(null,"Valitse uusi muistilistaaja tai tarkista kategoria asetukset!","Varoitus",JOptionPane.ERROR_MESSAGE);
        }else{
            textPanel.setVisible(true);
        }
    }
    
    public void catAndTxtHandler(String butt){
        for(int i=0;i<cat1ButtonList.size();i++){
            if(butt == cat1ButtonList.get(i)){
                textArea.setText(fileCat1TextList.get(i).toString());
                categoryField.setText(fileCat1ButtonTextList.get(i).toString());
                categoryLabel.setText(cat1);
            }
        }
        
        for(int i=0;i<cat2ButtonList.size();i++){
            if(butt == cat2ButtonList.get(i)){
                textArea.setText(fileCat2TextList.get(i).toString());
                categoryField.setText(fileCat2ButtonTextList.get(i).toString());
                categoryLabel.setText(cat2);
            }
        }
        
        for(int i=0;i<cat3ButtonList.size();i++){
            if(butt == cat3ButtonList.get(i)){
                textArea.setText(fileCat3TextList.get(i).toString());
                categoryField.setText(fileCat3ButtonTextList.get(i).toString());
                categoryLabel.setText(cat3);
            }
        }
    }
    
    public void handleButtonPress(){
        resetToggleButtons();
        catAndTxtHandler(pressedButton);
    }
    
    public void saveChanges(){
        if(pressedButton != null){
            for(int i=0;i<cat1ButtonList.size();i++){
                    if(pressedButton.equals(cat1ButtonList.get(i))){
                        fileCat1TextList.set(i,textArea.getText());
                        fileCat1ButtonTextList.set(i,categoryField.getText());
                        resetPanels();
                        }
                    }
                for(int i=0;i<cat2ButtonList.size();i++){
                    if(pressedButton.equals(cat2ButtonList.get(i))){
                        fileCat2TextList.set(i,textArea.getText());
                        fileCat2ButtonTextList.set(i,categoryField.getText());
                        resetPanels();
                        }
                    }
                for(int i=0;i<cat3ButtonList.size();i++){
                    if(pressedButton.equals(cat3ButtonList.get(i))){
                        fileCat3TextList.set(i,textArea.getText());
                        fileCat3ButtonTextList.set(i,categoryField.getText());
                        resetPanels();
                        }
                    }
            }
        }
    
    public boolean checkChanges(){
        
        boolean changes = false;

        for(int i=0;i<cat1ButtonList.size();i++){
            if(cat1ButtonList.get(i).equals(pressedButton)){
                if(textArea.getText().equals(fileCat1TextList.get(i).toString()) &&
                categoryField.getText().equals(fileCat1ButtonTextList.get(i).toString())){
                    changes = false; // Muutoksia ei ole tapahtunut
                }else{
                    changes = true; // Muutoksia on tapahtunut
                }
            }
        }
                
        for(int i=0;i<cat2ButtonList.size();i++){
            if(cat2ButtonList.get(i).equals(pressedButton)){
                if(textArea.getText().equals(fileCat2TextList.get(i).toString()) &&
                categoryField.getText().equals(fileCat2ButtonTextList.get(i).toString())){
                    changes = false; // Muutoksia ei ole tapahtunut
                }else{
                    changes = true; // Muutoksia on tapahtunut
                }
            }
        }
                
        for(int i=0;i<cat3ButtonList.size();i++){
            if(cat3ButtonList.get(i).equals(pressedButton)){
                if(textArea.getText().equals(fileCat3TextList.get(i).toString()) &&
                categoryField.getText().equals(fileCat3ButtonTextList.get(i).toString())){
                    changes = false; // Muutoksia ei ole tapahtunut
                }else{
                    changes = true; // Muutoksia on tapahtunut
                }
            }
        }
        return changes;
    }
    
    public void actionPerformed(ActionEvent e){

        for(int i=0; i < cat1ButtonList.size(); i++){ // for loop listan pituuden verran
            if(cat1ButtonList.get(i).equals(e.getActionCommand())){ // Jos painettu nappi
                if(checkChanges() == false){  // Jos ei ole tapahtunut muutoksia
                    pressedButton = cat1ButtonList.get(i).toString();
                    handleButtonPress();
                }else{ // Jos on tapahtunut muutoksia
                    UIManager.put("OptionPane.yesButtonText", "Tallenna");
                    UIManager.put("OptionPane.noButtonText", "Älä Tallenna");
                    UIManager.put("OptionPane.cancelButtonText", "Peruuta");

                    int input = JOptionPane.showConfirmDialog(null, "Haluatko tallentaa muutokset?", "Muistilistaaja", JOptionPane.YES_NO_CANCEL_OPTION);

                    if(input == 0){ // Tallenna
                        saveChanges();
                        saveFile("");
                        pressedButton = cat1ButtonList.get(i).toString();
                        handleButtonPress();
                    }

                    if(input == 1){ // Älä Tallenna
                        pressedButton = cat1ButtonList.get(i).toString();
                        handleButtonPress();
                    }

                    if(input == 2){ // Peruuta
                        resetToggleButtons();
                    }
                }
            }
        }
        
        for(int i=0; i < cat2ButtonList.size(); i++){ // for loop listan pituuden verran
            if(cat2ButtonList.get(i).equals(e.getActionCommand())){ // Jos painettu nappi
                if(checkChanges() == false){  // Jos ei ole tapahtunut muutoksia
                    pressedButton = cat2ButtonList.get(i).toString();
                    handleButtonPress();
                }else{ // Jos on tapahtunut muutoksia
                    UIManager.put("OptionPane.yesButtonText", "Tallenna");
                    UIManager.put("OptionPane.noButtonText", "Älä Tallenna");
                    UIManager.put("OptionPane.cancelButtonText", "Peruuta");

                    int input = JOptionPane.showConfirmDialog(null, "Haluatko tallentaa muutokset?", "Muistilistaaja", JOptionPane.YES_NO_CANCEL_OPTION);

                    if(input == 0){ // Tallenna
                        saveChanges();
                        saveFile("");
                        pressedButton = cat2ButtonList.get(i).toString();
                        handleButtonPress();
                    }

                    if(input == 1){ // Älä Tallenna
                        pressedButton = cat2ButtonList.get(i).toString();
                        handleButtonPress();
                    }

                    if(input == 2){ // Peruuta
                        resetToggleButtons();
                    }
                }
            }
        }
        
        for(int i=0; i < cat3ButtonList.size(); i++){ // for loop listan pituuden verran
            if(cat3ButtonList.get(i).equals(e.getActionCommand())){ // Jos painettu nappi
                if(checkChanges() == false){  // Jos ei ole tapahtunut muutoksia
                    pressedButton = cat3ButtonList.get(i).toString();
                    handleButtonPress();
                }else{ // Jos on tapahtunut muutoksia
                    UIManager.put("OptionPane.yesButtonText", "Tallenna");
                    UIManager.put("OptionPane.noButtonText", "Älä Tallenna");
                    UIManager.put("OptionPane.cancelButtonText", "Peruuta");

                    int input = JOptionPane.showConfirmDialog(null, "Haluatko tallentaa muutokset?", "Muistilistaaja", JOptionPane.YES_NO_CANCEL_OPTION);

                    if(input == 0){ // Tallenna
                        saveChanges();
                        saveFile("");
                        pressedButton = cat3ButtonList.get(i).toString();
                        handleButtonPress();
                    }

                    if(input == 1){ // Älä Tallenna
                        pressedButton = cat3ButtonList.get(i).toString();
                        handleButtonPress();
                    }

                    if(input == 2){ // Peruuta
                        resetToggleButtons();
                    }
                }
            }
        }
        
        if("p1+".equals(e.getActionCommand())){
            for(int i=0; i < 4;i++ ){ // NELJÄ KIERROSTA
                if(fileCat1ButtonTextList.get(i).toString().equals("NODRAW")){
                    fileCat1TextList.set(i,"Kirjoita tähän..");
                    fileCat1ButtonTextList.set(i,"NULL");
                    pressedButton = cat1ButtonList.get(i).toString();
                    resetPanels();
                    break;
                    }
                }
        }
        
        if("p2+".equals(e.getActionCommand())){
            for(int i=0; i < 4;i++ ){ // NELJÄ KIERROSTA
                if(fileCat2ButtonTextList.get(i).toString().equals("NODRAW")){
                    fileCat2TextList.set(i,"Kirjoita tähän..");
                    fileCat2ButtonTextList.set(i,"NULL");
                    pressedButton = cat2ButtonList.get(i).toString();
                    resetPanels();
                    break;
                    }
                }
        }
        
        if("p3+".equals(e.getActionCommand())){
            for(int i=0; i < 4;i++ ){ // NELJÄ KIERROSTA
                if(fileCat3ButtonTextList.get(i).toString().equals("NODRAW")){
                    fileCat3TextList.set(i,"Kirjoita tähän..");
                    fileCat3ButtonTextList.set(i,"NULL");
                    pressedButton = cat3ButtonList.get(i).toString();
                    resetPanels();
                    break;
                    }
                }
        }
        
        if("save".equals(e.getActionCommand())){
            saveChanges();
            saveFile("");
        }
        
        if("save2".equals(e.getActionCommand())){
            saveChanges();
            saveFile("Tallenna nimellä");
        }
        
        if("del".equals(e.getActionCommand())){
            if (pressedButton != null){
                
                UIManager.put("OptionPane.yesButtonText", "OK");
                UIManager.put("OptionPane.noButtonText", "Peruuta");
                
                int input = JOptionPane.showConfirmDialog(null, "Haluatko varmasti poistaa muistilistan "+categoryField.getText()+"?", "Poista muistilista", JOptionPane.YES_NO_OPTION);
                
                if(input == 0){ // Jos kyllä
                    for(int i=0;i<cat1ButtonList.size();i++){
                        if(pressedButton == cat1ButtonList.get(i)){
                            fileCat1TextList.set(i,"NODRAW");
                            fileCat1ButtonTextList.set(i,"NODRAW");
                            pressedButton = null;
                            resetPanels();
                            }
                        }
                    for(int i=0;i<cat2ButtonList.size();i++){
                        if(pressedButton == cat2ButtonList.get(i)){
                            fileCat2TextList.set(i,"NODRAW");
                            fileCat2ButtonTextList.set(i,"NODRAW");
                            pressedButton = null;
                            resetPanels();
                            }
                        }
                    for(int i=0;i<cat3ButtonList.size();i++){
                        if(pressedButton == cat3ButtonList.get(i)){
                            fileCat3TextList.set(i,"NODRAW");
                            fileCat3ButtonTextList.set(i,"NODRAW");
                            pressedButton = null;
                            resetPanels();
                            }
                        }
                    }   

                if(input == 1){ // Jos Peruuta
                    // DO NOTHING
                }
            }
        }
        
        if("catSettings".equals(e.getActionCommand())){
            if (catSettingsFirstTime == true) {
                    
                    catSettings = new EditCategoryDialog(new JFrame(),
                    cat1visib, cat2visib, cat3visib, cat1, cat2, cat3);
                    
                    if(catSettings.didUserCancel == false){

                        cat1visib = catSettings.getCatSetting1();
                        cat2visib = catSettings.getCatSetting2();
                        cat3visib = catSettings.getCatSetting3();
                        
                        cat1 = catSettings.getCatName1();
                        cat2 = catSettings.getCatName2();
                        cat3 = catSettings.getCatName3();

                        catSettingsFirstTime = false;
                        
                        resetPanels();
                    }
                    
                } else {
                    catSettings.setCatName1(cat1);
                    catSettings.setCatName2(cat2);
                    catSettings.setCatName3(cat3);
                    
                    catSettings.setCatSetting1(cat1visib);
                    catSettings.setCatSetting2(cat2visib);
                    catSettings.setCatSetting3(cat3visib);
                    
                    catSettings.setVisible(true);
                    
                    if(catSettings.didUserCancel == false){

                        cat1visib = catSettings.getCatSetting1();
                        cat2visib = catSettings.getCatSetting2();
                        cat3visib = catSettings.getCatSetting3();
                        
                        cat1 = catSettings.getCatName1();
                        cat2 = catSettings.getCatName2();
                        cat3 = catSettings.getCatName3();
                        
                        catSettings.didUserCancel = true;
                        
                        resetPanels();
                    }
                }
        }
        
        if("exit".equals(e.getActionCommand())){
            
            UIManager.put("OptionPane.yesButtonText", "Tallenna");
            UIManager.put("OptionPane.noButtonText", "Älä Tallenna");
            UIManager.put("OptionPane.cancelButtonText", "Peruuta");

            int input = JOptionPane.showConfirmDialog(null, "Haluatko tallentaa muutokset?", "Muistilistaaja", JOptionPane.YES_NO_CANCEL_OPTION);
            
            if(input == 0){ // Jos Tallenna
                saveChanges();
                saveFile("");
                System.exit(0);
            }
            if(input == 1){ // Jos Älä Tallenna
                System.exit(0);
            }
            if(input == 2){ // Jos Peruuta
                // DO NOTHING
            }
        }
        
        if("new".equals(e.getActionCommand())){
            
            UIManager.put("OptionPane.yesButtonText", "Tallenna");
            UIManager.put("OptionPane.noButtonText", "Älä Tallenna");
            UIManager.put("OptionPane.cancelButtonText", "Peruuta");

            int input = JOptionPane.showConfirmDialog(null, "Haluatko tallentaa muutokset?", "Muistilistaaja", JOptionPane.YES_NO_CANCEL_OPTION);

            if(input == 0){ // Jos Tallenna
                saveChanges();
                saveFile("");
                reset();
                buildMenu();
                resetPanels();
                textArea.setText("");
                categoryLabel.setText("CAT:");
                categoryField.setText("BUTT");
            }
            if(input == 1){ // Jos Älä Tallenna
                reset();
                buildMenu();
                resetPanels();
                textArea.setText("");
                categoryLabel.setText("CAT:");
                categoryField.setText("BUTT");
            }
            if(input == 2){ // Jos Peruuta
                // DO NOTHING
            }
        }
        
        if("open".equals(e.getActionCommand())){
            openFile();
        }
        
        if("guide".equals(e.getActionCommand())){
            new GuideFrame();
        }
    }

    public void resetToggleButtons(){
        p1b1.setSelected(false);
        p1b2.setSelected(false);
        p1b3.setSelected(false);
        p1b4.setSelected(false);
        p2b1.setSelected(false);
        p2b2.setSelected(false);
        p2b3.setSelected(false);
        p2b4.setSelected(false);
        p3b1.setSelected(false);
        p3b2.setSelected(false);
        p3b3.setSelected(false);
        p3b4.setSelected(false);
    }

    public JPanel setDetailPanel1(){

        int drawCount = 0;

        JPanel panel = new JPanel();
        GridBagConstraints gc = new GridBagConstraints();
        JButton t3Btn = new JButton("+");
        Dimension size = getPreferredSize();
        size.width = 250;
        
        setPreferredSize(size);
        
        JLabel nameLabel = new JLabel(cat1);
        
        t3Btn.setActionCommand("p1+");
        t3Btn.addActionListener(this);

        p1b1 = new JToggleButton(fileCat1ButtonTextList.get(0).toString(),false);
        p1b1.setActionCommand("p1b1");
        p1b1.addActionListener(this);
        
        p1b2 = new JToggleButton(fileCat1ButtonTextList.get(1).toString(),false);
        p1b2.setActionCommand("p1b2");
        p1b2.addActionListener(this);
        
        p1b3 = new JToggleButton(fileCat1ButtonTextList.get(2).toString(),false);
        p1b3.setActionCommand("p1b3");
        p1b3.addActionListener(this);
        
        p1b4 = new JToggleButton(fileCat1ButtonTextList.get(3).toString(),false);
        p1b4.setActionCommand("p1b4");
        p1b4.addActionListener(this);
        
        panel.setLayout(new GridBagLayout());
        
        gc.gridx = 0;
        gc.gridy = 0;

        panel.add(nameLabel, gc);
        
        if(!fileCat1ButtonTextList.get(0).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p1b1,gc);
            
            drawCount++;
        }
        
        if(!fileCat1ButtonTextList.get(1).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p1b2,gc);
            
            drawCount++;
        }
        
        if(!fileCat1ButtonTextList.get(2).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p1b3,gc);
            
            drawCount++;
        }
        
        if(!fileCat1ButtonTextList.get(3).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p1b4,gc);
            
            drawCount++;
        }
        
        if(drawCount < 4){
            gc.gridx = 1;
            gc.gridy = drawCount;
            gc.gridwidth = 1;
            gc.insets = new Insets(0,20,0,20);
            panel.add(t3Btn, gc);
        }
        
        return panel;

    }
    
    public JPanel setDetailPanel2(){

        int drawCount = 0;
        
        JPanel panel = new JPanel();
        GridBagConstraints gc = new GridBagConstraints();
        JButton t3Btn = new JButton("+");
        Dimension size = getPreferredSize();
        size.width = 250;
        
        setPreferredSize(size);

        JLabel nameLabel = new JLabel(cat2);
        
        t3Btn.setActionCommand("p2+");
        t3Btn.addActionListener(this);

        p2b1 = new JToggleButton(fileCat2ButtonTextList.get(0).toString(),false);
        p2b1.setActionCommand("p2b1");
        p2b1.addActionListener(this);
        
        p2b2 = new JToggleButton(fileCat2ButtonTextList.get(1).toString(),false);
        p2b2.setActionCommand("p2b2");
        p2b2.addActionListener(this);
        
        p2b3 = new JToggleButton(fileCat2ButtonTextList.get(2).toString(),false);
        p2b3.setActionCommand("p2b3");
        p2b3.addActionListener(this);
        
        p2b4 = new JToggleButton(fileCat2ButtonTextList.get(3).toString(),false);
        p2b4.setActionCommand("p2b4");
        p2b4.addActionListener(this);
        
        panel.setLayout(new GridBagLayout());
        
        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(nameLabel, gc);
        
        if(!fileCat2ButtonTextList.get(0).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p2b1,gc);
            
            drawCount++;
        }
        
        if(!fileCat2ButtonTextList.get(1).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p2b2,gc);
            
            drawCount++;
        }
        
        if(!fileCat2ButtonTextList.get(2).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p2b3,gc);
            
            drawCount++;
        }
        
        if(!fileCat2ButtonTextList.get(3).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p2b4,gc);
            
            drawCount++;
        }
        
        if(drawCount < 4){
            gc.gridx = 1;
            gc.gridy = drawCount;
            gc.gridwidth = 1;
            gc.insets = new Insets(0,20,0,20);
            panel.add(t3Btn, gc);
        }
        return panel;
        
    }
    
    public JPanel setDetailPanel3(){

        int drawCount = 0;
        JPanel panel = new JPanel();
        GridBagConstraints gc = new GridBagConstraints();
        JButton t3Btn = new JButton("+");
        Dimension size = getPreferredSize();
        size.width = 250;
        
        setPreferredSize(size);
        
        JLabel nameLabel = new JLabel(cat3);
        
        t3Btn.setActionCommand("p3+");
        t3Btn.addActionListener(this);
        
        p3b1 = new JToggleButton(fileCat3ButtonTextList.get(0).toString(),false);
        p3b1.setActionCommand("p3b1");
        p3b1.addActionListener(this);
        
        p3b2 = new JToggleButton(fileCat3ButtonTextList.get(1).toString(),false);
        p3b2.setActionCommand("p3b2");
        p3b2.addActionListener(this);
        
        p3b3 = new JToggleButton(fileCat3ButtonTextList.get(2).toString(),false);
        p3b3.setActionCommand("p3b3");
        p3b3.addActionListener(this);
        
        p3b4 = new JToggleButton(fileCat3ButtonTextList.get(3).toString(),false);
        p3b4.setActionCommand("p3b4");
        p3b4.addActionListener(this);
        
        panel.setLayout(new GridBagLayout());
        
        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(nameLabel, gc);
        
        if(!fileCat3ButtonTextList.get(0).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p3b1,gc);
            
            drawCount++;
        }
        
        if(!fileCat3ButtonTextList.get(1).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p3b2,gc);
            
            drawCount++;
        }
        
        if(!fileCat3ButtonTextList.get(2).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p3b3,gc);
            
            drawCount++;
        }
        
        if(!fileCat3ButtonTextList.get(3).toString().equals("NODRAW")){
            gc.gridx = 1;
            gc.gridy = drawCount;
            panel.add(p3b4,gc);
            
            drawCount++;
        }
        
        if(drawCount < 4){
            gc.gridx = 1;
            gc.gridy = drawCount;
            gc.gridwidth = 1;
            gc.insets = new Insets(0,20,0,20);
            panel.add(t3Btn, gc);
        }
        
        return panel;
        
    }
    
    public void resetPanels(){
        detailsPanel.removeAll();
        detailsPanel.revalidate();
        detailsPanel.repaint();

        detailsPanel2.removeAll();
        detailsPanel2.revalidate();
        detailsPanel2.repaint();

        detailsPanel3.removeAll();
        detailsPanel3.revalidate();
        detailsPanel3.repaint();
        
        setup();
    }

    
    public void openFile(){
        UIManager.put("FileChooser.openButtonText", "Avaa");
        UIManager.put("FileChooser.cancelButtonText", "Peruuta");
        UIManager.put("FileChooser.fileNameLabelText", "Tiedostonimi:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Tiedostomuoto");
        
        boolean illegalFile = false;
        JFileChooser fc = new JFileChooser();
        BufferedReader reader;
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Avaa");
        int returnVal = fc.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            try{
                reader = new BufferedReader(new FileReader(file.getName()));
                String line = reader.readLine();
                if(line != null && illegalFile == false){

                    String[] parts = line.split("ALAKAYTATALLAISTAMISSAAN");

                    if(!"TYPE".equals(parts[0]) || !"muistilistaaja-save-file".equals(parts[1])){
                        JOptionPane.showMessageDialog(null,"Tiedoston avaus epäonnistui.","Varoitus",JOptionPane.ERROR_MESSAGE);
                        illegalFile = true;
                        reader.close();
                    } else {

                        fileCat1ButtonTextList.clear();
                        fileCat1TextList.clear();
                        fileCat2ButtonTextList.clear();
                        fileCat2TextList.clear();
                        fileCat3ButtonTextList.clear();
                        fileCat3TextList.clear();
                        
                        line = reader.readLine();
                        
                        while(line != null){

                            parts = line.split("ALAKAYTATALLAISTAMISSAAN");
                            
                            if(parts[0] == null || parts[1] == null){
                                JOptionPane.showMessageDialog(null,"Tiedoston avaus epäonnistui.","Varoitus",JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            
                            if(parts[0].equals("cat1")){
                                cat1 = parts[1];
                            }
                            if(parts[0].equals("bt1")){
                                fileCat1ButtonTextList.add(parts[1]);
                            }
                            if(parts[0].equals("t1")){
                                fileCat1TextList.add(parts[1]);
                            }
                            if(parts[0].equals("cat2")){
                                cat2 = parts[1];
                            }   
                            if(parts[0].equals("bt2")){
                                fileCat2ButtonTextList.add(parts[1]);
                            }
                            if(parts[0].equals("t2")){
                                fileCat2TextList.add(parts[1]);
                            }
                            if(parts[0].equals("cat3")){
                                cat3 = parts[1];
                            }
                            if(parts[0].equals("bt3")){
                                fileCat3ButtonTextList.add(parts[1]);
                            }
                            if(parts[0].equals("t3")){
                                fileCat3TextList.add(parts[1]);
                            }
                            line = reader.readLine();
                        }
                    }
                }

                reader.close();

                resetPanels();
                
            } catch(IOException | ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Tiedoston avaus epäonnistui.","Varoitus",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            
            }
    }
    
    public String makeSaveFile(){
        
        String splitter = "ALAKAYTATALLAISTAMISSAAN";
        
        String saveString = "TYPE"+splitter+"muistilistaaja-save-file\r\n"+
                "cat1"+splitter+cat1+"\r\n"+
                "bt1"+splitter+fileCat1ButtonTextList.get(0).toString()+"\r\n"+
                "t1"+splitter+fileCat1TextList.get(0).toString()+"\r\n"+
                "bt1"+splitter+fileCat1ButtonTextList.get(1).toString()+"\r\n"+
                "t1"+splitter+fileCat1TextList.get(1).toString()+"\r\n"+
                "bt1"+splitter+fileCat1ButtonTextList.get(2).toString()+"\r\n"+
                "t1"+splitter+fileCat1TextList.get(2).toString()+"\r\n"+
                "bt1"+splitter+fileCat1ButtonTextList.get(3).toString()+"\r\n"+
                "t1"+splitter+fileCat1TextList.get(3).toString()+"\r\n"+
                "cat2"+splitter+cat2+"\r\n"+
                "bt2"+splitter+fileCat2ButtonTextList.get(0).toString()+"\r\n"+
                "t2"+splitter+fileCat2TextList.get(0).toString()+"\r\n"+
                "bt2"+splitter+fileCat2ButtonTextList.get(1).toString()+"\r\n"+
                "t2"+splitter+fileCat2TextList.get(1).toString()+"\r\n"+
                "bt2"+splitter+fileCat2ButtonTextList.get(2).toString()+"\r\n"+
                "t2"+splitter+fileCat2TextList.get(2).toString()+"\r\n"+
                "bt2"+splitter+fileCat2ButtonTextList.get(3).toString()+"\r\n"+
                "t2"+splitter+fileCat2TextList.get(3).toString()+"\r\n"+
                "cat3"+splitter+cat3+"\r\n"+
                "bt3"+splitter+fileCat3ButtonTextList.get(0).toString()+"\r\n"+
                "t3"+splitter+fileCat3TextList.get(0).toString()+"\r\n"+
                "bt3"+splitter+fileCat3ButtonTextList.get(1).toString()+"\r\n"+
                "t3"+splitter+fileCat3TextList.get(1).toString()+"\r\n"+
                "bt3"+splitter+fileCat3ButtonTextList.get(2).toString()+"\r\n"+
                "t3"+splitter+fileCat3TextList.get(2).toString()+"\r\n"+
                "bt3"+splitter+fileCat3ButtonTextList.get(3).toString()+"\r\n"+
                "t3"+splitter+fileCat3TextList.get(3).toString()
                ;

        return saveString;
    }
    
    public void saveFile(String opt){
    
        UIManager.put("FileChooser.saveButtonText", "Tallenna");
        UIManager.put("FileChooser.cancelButtonText", "Peruuta");
        UIManager.put("FileChooser.fileNameLabelText", "Tiedostonimi:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Tiedostomuoto");
        
        if(defaultSaveFile == null || opt == "Tallenna nimellä"){

            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new java.io.File("."));
            fc.setDialogTitle("Tallenna");
            int returnVal = fc.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION){
                try(FileWriter fw = new FileWriter(fc.getSelectedFile()+".txt")) {
                    fw.write(makeSaveFile());
                    defaultSaveFile = fc.getSelectedFile().getAbsolutePath();
                } catch (IOException | NullPointerException ex) {
                    ex.printStackTrace();
                }
            } else {
                
            }
        } else{
            try(FileWriter fw = new FileWriter(defaultSaveFile+".txt")){
                fw.write(makeSaveFile());
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

      public void buildMenu(){
        
        // Luodaan menu bar
        menuBar = new JMenuBar();
        
        // TIEDOSTO - MENU
        menu = new JMenu("Tiedosto");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
        "The only menu in this program that has menu items");
        menuBar.add(menu);
        
        // Uusi muistilistaaja
        menuItem = new JMenuItem("Uusi muistilistaaja");
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        // Avaa...
        menuItem = new JMenuItem("Avaa muistilistaaja...");
        menuItem.setActionCommand("open");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        // Tallenna muistilistaaja
        menuItem = new JMenuItem("Tallenna muistilistaaja");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("save");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        // Tallenna muistilistaaja nimellä...
        menuItem = new JMenuItem("Tallenna muistilistaaja nimellä...");
        menuItem.setActionCommand("save2");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        // Lopeta
        menuItem = new JMenuItem("Lopeta");
        menuItem.setActionCommand("exit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        // MUOKKAA - MENU
        menu = new JMenu("Muokkaa");
        
        // Muokkaa kategorioita
        menuItem = new JMenuItem("Muokkaa kategorioita");
        menuItem.setActionCommand("catSettings");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);
        
        // OHJE - MENU
        menu = new JMenu("Ohje");
        
        // Katso ohje
        menuItem = new JMenuItem("Katso ohje");
        menuItem.setActionCommand("guide");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
}

