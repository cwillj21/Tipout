/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * C. Will Johnson                                      07 July 2016 *
 * Tip Out Calculator                                                *
 * TipoutGUI class defination:                                       * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException; 
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.swing.border.EmptyBorder;
//import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.util.LinkedList;

public class TipoutCalculator extends JFrame implements ActionListener {
  
  //member variables
  private DecimalFormat df;
  private Dimension textFieldDim;
  private double hourly;
  private double totalHrs;
  private double totalMoney;
  private double toTeddy;
  
  private JButton calc;
  private JButton minus;
  private JButton plus;
  private JButton reset;
  
  private JLabel addRemoveDescription;
  private JLabel errorMessage;
  private JLabel hrsWorkedCol;
  private JLabel nameCol;
  private JLabel paids;
  private JLabel perHrLabel;
  private JLabel pVal;
  private JLabel tipsInCol;
  private JLabel tipsOutCol;
  private JLabel totalPaidValue;
  private JLabel welcome;
  
  private JPanel addRemove;
  private JPanel bottom;
  private JPanel contents;
  private JPanel empPanel;
  private JPanel footer;
  private JPanel head;
  private JPanel paidLine;
  private JPanel topHead;
  
  private JTextField numEmp;
  private JTextField totalPaids;
  private JTextField paidValue;
  
  private LinkedList<Employee> employeeList = new LinkedList<Employee>();
  
  //constructor used to set up the GUI
  public TipoutCalculator () {
    initUI();
  }
  
  private final void initUI() {
    df = new DecimalFormat("#.00");
    textFieldDim = new Dimension(50, 27);
    //initialize the totals to zero
    hourly = 0.0;
    totalHrs = 0.0;
    totalMoney = 0.0;
    
    //create one new employee and set up the container
    employeeList.add(new Employee());
    contents = new JPanel();
    contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
    
    contents.add(setUpHeader());
    
    contents.add(setUpAddRemove());
    
    contents.add(setUpPaidLine());
    
    contents.add(setUpColHeader());
    
    //sets up the employee row panel
    empPanel = new JPanel();
    empPanel.setLayout(new BoxLayout(empPanel, BoxLayout.Y_AXIS));
    empPanel.setSize(new Dimension(530, 27*employeeList.getLast().getCount()));
    
    addEmployee(empPanel);
    contents.add(empPanel);
    
    contents.add(setUpBottom());
    
    contents.add(setUpFooter());
    
    add(contents);
    
    pack();
    
    setTitle("Tipout Calculator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  /* Sets up the header of the application. */
  public JPanel setUpHeader() {
    head = new JPanel();
    head.setBackground(Color.lightGray);    
    welcome = new JLabel("Welcome to the Tipout Calculator, written by: C. Will Johnson");
    head.add(welcome);
    
    return head;
  }
  
  /* Sets up the top part of the application where the user can add/remove
   * the number of employees, indicated by the text field, to the body panel */
  public JPanel setUpAddRemove() {
    FlowLayout fl = new FlowLayout(FlowLayout.LEADING);
    addRemove = new JPanel(fl);
    addRemove.setBorder(new EmptyBorder(2, 10, 2, 10));
    
    plus = new JButton(" + ");
    minus = new JButton(" - ");
    
    numEmp = new JTextField("1", 2);
    numEmp.setPreferredSize(textFieldDim);
    addRemoveDescription = new JLabel("Add/Remove Employee(s)");
    
    addRemove.add(addRemoveDescription);
    addRemove.add(plus);
    addRemove.add(minus);
    addRemove.add(numEmp);
    
    plus.addActionListener(this);
    minus.addActionListener(this);
    
    return addRemove;
  }
  
  /* Sets up the paids line panel */
  public JPanel setUpPaidLine() {
    FlowLayout fl = new FlowLayout(FlowLayout.LEADING);
    paidLine = new JPanel(fl);
    paidLine.setBorder(new EmptyBorder(2, 10, 2, 10));
    
    totalPaids = new JTextField("0", 2);
    paidValue = new JTextField("0", 2);
    
    paids = new JLabel("Number of Paid Tickets");
    pVal = new JLabel("at $");
    totalPaidValue = new JLabel("");
    
    totalPaids.setPreferredSize(textFieldDim);
    paidValue.setPreferredSize(textFieldDim);
    
    paidLine.add(paids);
    paidLine.add(totalPaids);
    paidLine.add(pVal);
    paidLine.add(paidValue);
    paidLine.add(totalPaidValue);
    
    return paidLine;
  }
  
  /* Adds a header column for the employee rows*/
  public JPanel setUpColHeader() {
    
    topHead = new JPanel();
    
    nameCol = new JLabel("      Emp. Name           ");
    tipsInCol = new JLabel("         Tips In        ");
    hrsWorkedCol = new JLabel("          Hours Worked      ");
    tipsOutCol = new JLabel("         Tips Out       ");
    
    topHead.add(nameCol);
    topHead.add(tipsInCol);
    topHead.add(hrsWorkedCol);
    topHead.add(tipsOutCol);
    
    return topHead;
  }
  
  /* Adds an employee row to the specified panel */
  public void addEmployee(JPanel p) {
    p.add(employeeList.getLast().setUpRow());
  }
  
  /* Removes the employee row from the GUI */
  public void removeEmployee(JPanel p) {
    //leaves empty space when removing the panel
    p.remove(employeeList.getLast().setUpRow());
  }
  
  /* Sets up the bottom of the application where the 'calculate' button and 
   * 'reset' button are. Also sets up the JLabel that displays the $/hr */
  public JPanel setUpBottom() {
    
    bottom = new JPanel();
    bottom.setAlignmentX(CENTER_ALIGNMENT);
    bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
    bottom.setBorder(new EmptyBorder(5, 5, 10, 5));
    
    //create two buttons and the text area to display the $/hr
    calc = new JButton("Calculate");
    reset = new JButton("Reset");
    perHrLabel = new JLabel("");
    
    //add the two buttons and the label to the bottom panel
    bottom.add(calc);
    bottom.add(Box.createRigidArea(new Dimension(5, 0)));
    bottom.add(reset);
    bottom.add(Box.createRigidArea(new Dimension(15, 0)));
    bottom.add(perHrLabel);
    
    //add an action listener to the calc button
    calc.addActionListener(this);
    reset.addActionListener(this);
    
    return bottom;
  }
  
  /* Creates a footer that will display an error message is one is made */
  public JPanel setUpFooter() {
    footer = new JPanel();
    errorMessage = new JLabel("");
    footer.add(errorMessage);
    return footer;
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    //Calculate button
    if (e.getSource() == calc) {
      calculateTipout();
    }
    
    //Reset button
    if (e.getSource() == reset) {resetUI();}
    
    //Plus button
    if (e.getSource() == plus) {
      System.out.println("count: "+employeeList.getLast().getCount());
      for (int i=0; i<Integer.valueOf(numEmp.getText()); i++) {
        employeeList.add(new Employee());
        addEmployee(empPanel);
      }
    }
    
    //Minus button
    if (e.getSource() == minus) {
      System.out.println("count: "+employeeList.getLast().getCount());
      if (Integer.valueOf(numEmp.getText()) < employeeList.getLast().getCount()) {
        for (int i=0; i<Integer.valueOf(numEmp.getText()); i++) {
          removeEmployee(empPanel);
          employeeList.removeLast().close();
        }
      }
    }
    pack();
  }
  
  /* Calculates the hourly rate and sets each employee's tipsOut textField */
  public void calculateTipout() {
    //re-initialize
    totalMoney = 0.0;
    totalHrs = 0.0;
    toTeddy = 0.0;
    
    //calculate hourly rate
    for (int i=0; i<employeeList.getLast().getCount(); i++) {
      if (!validateTextField(employeeList.get(i))) {return;}
      totalMoney = totalMoney + Double.valueOf(employeeList.get(i).getTipsIn());
      totalHrs = totalHrs + Double.valueOf(employeeList.get(i).getHrsWorked());
    }
    
    //set the total paid amount
    toTeddy = Integer.valueOf(totalPaids.getText()) * Double.valueOf(paidValue.getText());
    totalPaidValue.setText(" = $"+df.format(toTeddy));
    
    //set the hourly rate
    try {hourly = (totalMoney - toTeddy) / totalHrs;}
    catch (ArithmeticException ae) {
      errorMessage.setText("Can't divide by zero");
      return;
    }
      
    //calculate each employee's tipout
    for (int i=0; i<employeeList.getLast().getCount(); i++) {
      employeeList.get(i).setTipsOut(hourly * Double.valueOf(employeeList.get(i).getHrsWorked())); 
    }
    perHrLabel.setText("$"+df.format(hourly)+"/hr");
    errorMessage.setText("");
  }
  
  /* Resets all UI */
  public void resetUI() {
    int c = employeeList.getFirst().getCount();

    totalPaids.setText("");
    paidValue.setText("");
    totalPaidValue.setText("");
    numEmp.setText("1");
    employeeList.getFirst().clear();
    
    for (int i=1; i<c; i++) {
      removeEmployee(empPanel);
      employeeList.removeLast().close();
    }
    
    perHrLabel.setText("");
    errorMessage.setText("");
  }
  
  // /* Saves the information from the current calculation to a .txt file */
  // public void save(String filename) {
  //   try {
  //     PrintWriter writer = new PrintWriter(filename, "UTF-8");
  //     writer.close();
  //     writer.writeln("");
  //   }
  //   catch (IOException e) {}
    
  // }
  
  /* Checks each textfield and makes sure it is a valid value */
  public Boolean validateTextField(Employee e) {
    String hw = e.hrsWorkedTextField().getText();
    String ti = e.tipsInTextField().getText();
       
    //test for value
    if (ti.equals("")) {
      //e.tipsInTextField().setBorder(BorderFactory.createLineBorder(Color.red));
      errorMessage.setText("Please enter a value for tips in and hours worked");
      return false;
    }
    if (hw.equals("")) {
      //e.hrsWorkedTextField().setBorder(BorderFactory.createLineBorder(Color.red));
      errorMessage.setText("Please enter a value for tips in and hours worked");
      return false;
    }

    //test for numeral value
    try {
    double d = Double.parseDouble(ti); 
    d = Double.parseDouble(hw);
    //continue checking because ti and hw are valid doubles
    } catch (NumberFormatException nfe) {
      errorMessage.setText("Please enter a valid number for tips in and hours worked");
      return false; // because ti and hw are not valid doubles
    }
    
    //test for positive value
    if (e.getTipsIn() < 0.0 || e.getHrsWorked() < 0.0) {
      errorMessage.setText("Please enter a positive number for tips in and hours worked");
      return false;
    }
    
    return true;
  }
  
  public static void main(String[] args) {
    
    SwingUtilities.invokeLater(new Runnable() {
      
      public void run() {
        TipoutCalculator ex = new TipoutCalculator();
        ex.setVisible(true);
      }
    });
  }
}