package muistilistaaja;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditCategoryDialog extends JDialog {
    
    JCheckBox firstCatButt;
    JCheckBox secCatButt;
    JCheckBox thirdCatButt;
    
    JTextField firstCatName;
    JTextField secCatName;
    JTextField thirdCatName;
    
    String firstCatNameString;
    String secCatNameString;
    String thirdCatNameString;
    
    boolean firstCatVisib;
    boolean secCatVisib;
    boolean thirdCatVisib;
    
    boolean didUserCancel;
    
    public EditCategoryDialog(JFrame parent, boolean firstCatVisib,
            boolean secCatVisib, boolean thirdCatVisib,
            String firstCatNameString, String secCatNameString,
            String thirdCatNameString){
    
        super(parent, "Muokkaa kategorioita", true);
        if (parent != null) {
            Dimension parentSize = parent.getSize(); 
            Point p = this.getLocation(); 
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
        }

        didUserCancel = true;

        // Paneeli, johon asetetaan sisältö
        Container c = getContentPane();
        
        JPanel messagePane = new JPanel();
        messagePane.add(new JLabel("Muokkaa asetuksia"));
        add(messagePane);
        
        JPanel editCategoryPanel = new JPanel();
        
        editCategoryPanel.setLayout(new GridBagLayout());
        
        // Checkboxit
        firstCatButt = new JCheckBox("Kategoria 1:",firstCatVisib);
        secCatButt = new JCheckBox("Kategoria 2:",secCatVisib);
        thirdCatButt = new JCheckBox("Kategoria 3:",thirdCatVisib);
        
        // Tekstikentät
        firstCatName = new JTextField(firstCatNameString,6);
        secCatName = new JTextField(secCatNameString,6);
        thirdCatName = new JTextField(thirdCatNameString,6);
        
        // Napit
        JButton saveCatBtn = new JButton("Tallenna");
        JButton cancelCatBtn = new JButton("Peruuta");
        
        GridBagConstraints gc = new GridBagConstraints();
        
        gc.weightx = 0;
        gc.weighty = 0;
        
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        editCategoryPanel.add(firstCatButt, gc);
        
        gc.gridx = 0;
        gc.gridy = 1;
        editCategoryPanel.add(secCatButt, gc);
        
        gc.gridx = 0;
        gc.gridy = 2;
        editCategoryPanel.add(thirdCatButt, gc);
        
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        editCategoryPanel.add(firstCatName, gc);
        
        gc.gridx = 1;
        gc.gridy = 1;
        editCategoryPanel.add(secCatName, gc);
        
        gc.gridx = 1;
        gc.gridy = 2;
        editCategoryPanel.add(thirdCatName, gc);
        
        // TALLENNA
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 3;
        editCategoryPanel.add(saveCatBtn, gc);
        // PERUUTA
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 3;
        editCategoryPanel.add(cancelCatBtn, gc);
        
        // Lisätään paneeli frameen
        c.add(editCategoryPanel);
        
        // Painikkeiden toiminta
        saveCatBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tallenna kategoriat
                didUserCancel = false;
                
                if(     firstCatName.getText().length() > 6 ||
                        secCatName.getText().length() > 6 ||
                        thirdCatName.getText().length() > 6){
                    // Ikkuna, että liian pitkiä nimiä !
                    JOptionPane.showMessageDialog(null,"Liian pitkä kategorian nimi!","Varoitus",JOptionPane.ERROR_MESSAGE);
                } else {
                    dispose();
                }
            }
        });
        
        cancelCatBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Peruuttaa kategorioiden tallennuksen
                didUserCancel = true;
                dispose();
            }
        });

        pack(); 
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    public boolean getCatSetting1(){
        return firstCatButt.isSelected();
    }
    
    public void setCatSetting1(boolean set){
        firstCatButt.setSelected(set);
    }
    
    public boolean getCatSetting2(){
        return secCatButt.isSelected();
    }
    
    public void setCatSetting2(boolean set){
        secCatButt.setSelected(set);
    }
    
    public boolean getCatSetting3(){
        return thirdCatButt.isSelected();
    }
    
    public void setCatSetting3(boolean set){
        thirdCatButt.setSelected(set);
    }
    
    public String getCatName1(){
        return firstCatName.getText();
    }
    
    public void setCatName1(String name){
        firstCatName.setText(name);
    }
    
    public String getCatName2(){
        return secCatName.getText();
    }
    
    public void setCatName2(String name){
        secCatName.setText(name);
    }
    
    public String getCatName3(){
        return thirdCatName.getText();
    }
    
    public void setCatName3(String name){
        thirdCatName.setText(name);
    }
    
    public boolean getChoice(){
        return didUserCancel;
    }
}