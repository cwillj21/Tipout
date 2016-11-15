/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * C. Will Johnson                                      07 July 2016 *
 * Tip Out Calculator                                                *
 * Employee class defination:                                        * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.lang.Throwable;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Employee { 
  
  //member variables
  private static int count = 0;
  private DecimalFormat df;
  private JLabel num;
  private JPanel entry;
  private JTextField hrsWorked;
  private JTextField name;
  private JTextField tipsIn;
  private JTextField tipsOut;
  
  private void init() {
    count++;
    df = new DecimalFormat("#.00");
    num = new JLabel(Integer.toString(count));
    name = new JTextField("", 10);
    tipsIn = new JTextField("", 10);
    hrsWorked = new JTextField("", 10);
    tipsOut = new JTextField("", 10);
    tipsOut.setEditable(false);
  }
  
  //a few constructors
  Employee() {
    init();
  } 
  
  public JPanel setUpRow() {
    entry = new JPanel();
        
    entry.add(num);
    entry.add(name);
    entry.add(tipsIn);
    entry.add(hrsWorked);
    entry.add(tipsOut);
    
    return entry;
  }
  
  /* Displays the object in a string */
  public String toString() {
    return (count+".) "+name.getText()+" turned in $"+tipsIn.getText()+", worked "
              +hrsWorked.getText()+" hours and got back $"+tipsOut.getText());
  }
  
  /* Clears one entry */
  public void clear() {
    name.setText("");
    tipsIn.setText("");
    hrsWorked.setText("");
    tipsOut.setText("");
  }
  
  /* Deletes the object by calling finalize() */
  public void close() {
    count--;
    try {
       //System.out.println("Finalizing...");
       this.finalize();
       //System.out.println("Finalized.");
    }
    catch (Throwable e) {
      e.printStackTrace();
    }
  }
  
  //gets
  public String getName() {return name.getText();}
  public double getTipsIn() {
    if (tipsIn.getText().equals(""))
      return 0;
    else
      return Double.valueOf(tipsIn.getText());}
  public double getHrsWorked() {
    if (hrsWorked.getText().equals(""))
      return 0.0;
    else
      return Double.valueOf(hrsWorked.getText());}
  public double getTipsOut() {return Double.valueOf(tipsOut.getText());}
  public int getCount() {return count;}
  
  public JTextField nameTextField() {return name;}
  public JTextField tipsInTextField() {return tipsIn;}
  public JTextField hrsWorkedTextField() {return hrsWorked;}
  public JTextField tipsOutTextField() {return tipsOut;}
  
  //sets
  void setTipsOut(double out) {
    tipsOut.setText(df.format(out));
  }
}