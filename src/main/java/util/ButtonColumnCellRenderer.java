/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Raquel
 */
public class ButtonColumnCellRenderer extends DefaultTableCellRenderer {
     private String buttonType;
     
     public ButtonColumnCellRenderer(String buttonType){
         this.buttonType = buttonType;
     }
        
     public String getButtonType(){
         return buttonType;
     }
      
     public void setButtonType(String buttonType){
         this.buttonType = buttonType;
     }
      @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col){
        
        //cells are by default rendered as a JLabel
        JLabel label = (JLabel) super.getTableCellRendererComponent(table,
             value, isSelected, hasFocus, row, col);
     
       label.setHorizontalAlignment(CENTER);
       label.setIcon(new javax.swing.ImageIcon(getClass()
               .getResource("/" + buttonType + ".png")));
       
      //Return the JLabel witch renders the cell
      return label;
}
   
}
        
    

