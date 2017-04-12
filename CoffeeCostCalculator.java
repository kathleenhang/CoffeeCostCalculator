/* Project		: 	Project4KHang
 * Class		: 	CoffeeCostCalculator
 * Date			: 	03/30/2017
 * Programmer	: 	Kathleen Hang
 * Description	: 	This application will help workers determine coffee purchases 
 * 					and total owed by customers. 
 */


// bring packages from API for the necessary classes

import javax.swing.*;		// package for all the swing classes
import java.awt.Font;
import java.awt.event.*;	// package for the ActionListener class
import java.text.*;			// package for the DecimalFormat class


public class CoffeeCostCalculator extends JFrame implements ActionListener
{

	// Objects to enter input and display
	JTextField nameTextField = new JTextField(15);
	JTextField quantityTextField = new JTextField(15);
	JButton addButton = new JButton("		Add to Order		");
	JButton completeButton = new JButton("		Complete Order		");
	JButton clearButton = new JButton("		Clear		");
	JTextArea outputTextArea = new JTextArea(10,37);
	JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
	JLabel companyNameLabel = new JLabel("CoffeeDuck - Morning Coffee Stop    ");
	Font titleFont = new Font("Arial",Font.PLAIN, 18);
	Font courierFont = new Font("Courier", Font.PLAIN, 13);
	JLabel programmerNameLabel = new JLabel("Programmed by: Kathleen Hang");
	JPanel mainPanel = new JPanel();
	
	// Radio buttons for the coffee flavor
	JRadioButton mochaRadioButton = new JRadioButton("Mocha");
	JRadioButton latteRadioButton = new JRadioButton("Latte");
	JRadioButton dripRadioButton = new JRadioButton("Drip");
	JRadioButton invisibleRadioButton = new JRadioButton("");
	
	// Object to make the radio button mutually exclusive
	ButtonGroup flavorButtonGroup = new ButtonGroup();
	
	// Instance variables
	float subTotalFloat;			// stores total accumulated sales of coffee
	String flavorSelectString;		// stores the flavor the user selects
	final int WIDTH_INTEGER = 317;	// stores frame width
	final int HEIGHT_INTEGER = 420; // stores frame height
	
	// main method - Instantiates the class & allows the window to be closed with x button
	public static void main(String[] args)
	{
		// object of the class and close the x button of JFrame object
		CoffeeCostCalculator myProgram = new CoffeeCostCalculator();
		myProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/* CoffeeCostCalculator method - constructor
	 	- Calls the designClass method to add components onto the mainPanel
	 	- Calls the addListeners method to add listeners to the components
	 	- Add the main panel to the frame
	 	- Set the size of the frame & frame visibility to true 
	 	- Set text area to display titles for name, quantity, coffee type, and total
	 */
	public CoffeeCostCalculator()
	{
		// call method to add objects to mainPanel
		designClass();
		// call method to add objects to ActionListener
		addListeners();
		// add the panel to the frame
		add(mainPanel);
		// set the size and visibility
		setSize(WIDTH_INTEGER, HEIGHT_INTEGER);
		setVisible(true);
		// format the title for name, quantity, coffee type, total, and display in text area
		String titleString = String.format("%-8s %-8s %-8s %-8s %n", "Name:", "Qty", "Type", "Total");
		outputTextArea.append(titleString);
	}
	
	/* designClass method
	 * - Adds RadioButtons to the object of ButtonGroup
	 * - Enables the completeButton
	 * - Add GUI objects of JLabel, JTextField, JRadioButton, JButton, JScrollPane,
	 * 	 to the mainPanel
	*/
	public void designClass()
	{
		// Add the radio buttons to button group
		flavorButtonGroup.add(mochaRadioButton);
		flavorButtonGroup.add(latteRadioButton);
		flavorButtonGroup.add(dripRadioButton);
		flavorButtonGroup.add(invisibleRadioButton);
		
		// Disable the completeButton
		completeButton.setEnabled(false);
		
		// Add the objects to the mainPanel
		companyNameLabel.setFont(titleFont);
		outputTextArea.setFont(courierFont);
		mainPanel.add(companyNameLabel);
		mainPanel.add(new JLabel(" Name		"));
		mainPanel.add(nameTextField);
		mainPanel.add(new JLabel(" Quantity		"));
		mainPanel.add(quantityTextField);
		mainPanel.add(mochaRadioButton);
		mainPanel.add(latteRadioButton);
		mainPanel.add(dripRadioButton);
		mainPanel.add(addButton);
		mainPanel.add(completeButton);
		mainPanel.add(clearButton);
		mainPanel.add(outputScrollPane);
		mainPanel.add(programmerNameLabel);
		
	}
	
	/*
	 * addListeners method - adds listeners to the objects
	 * Register the ActionListener to the quantityTextField
	 * Register the ActionListener to the addButton
	 * Register the ActionListener to the completeButton
	 * Register the ActionListener to the clearButton
	 * So action can be triggered when the buttons are clicked or enter is pressed in textfield
	 */
	public void addListeners()
	{
		// Add the objects to the actionListener
		quantityTextField.addActionListener(this);
		addButton.addActionListener(this);
		completeButton.addActionListener(this);
		clearButton.addActionListener(this);
	}
	
	/*
	 * actionPerformed method
	 * - Create an object to store which object fired the event
	 * - If fired object is either quantityTextField or addButton
	 * 		call the validation method which returns a boolean
	 * 		if true, then the addOrder method is called
	 * - If fired object is completeButton
	 * 		call the completeOrder method
	 * - If fired object is clearButton
	 * 		call the clearOrder method
	 */
	public void actionPerformed(ActionEvent evt)
	{
		// get the object that triggered the event
		Object sourceObject = evt.getSource();
		
		if(sourceObject == quantityTextField || sourceObject == addButton)
		{
		
		if(validation())
			addOrder();
		}
		if(sourceObject == completeButton)
		{
			completeOrder();
		}
		if(sourceObject == clearButton)
		{
			clearOrder();
		}
		
	}
	
	/* selectRadioButton method
	 * - Create a boolean variable selectedBoolean to store if RadioButton is selected
	 * - If any of the radio buttons are selected
	 * 		the variable is assigned true
	 * - else the variable is assigned false
	 * - boolean returns to where it was called (validation method)
	 */
	public boolean selectRadioButton()
	{
		boolean selectedBoolean;
		if(mochaRadioButton.isSelected()||latteRadioButton.isSelected()
				||dripRadioButton.isSelected())
		{
			selectedBoolean = true;
		}
		else
		{
			selectedBoolean = false;
		}
		return selectedBoolean;
	}
	/*
	 * validation method
	 * - Create a boolean variable validationBoolean to store true if the objects
	 * 	 have content or selected
	 * - Checks if the nameTextField has content. Then checks the next textfield until all
	 * 	 textfields are checked. Then calls selectRadioButton method to return if
	 * 	 RadioButton is selected or not.
	 * - If all textfields have content & RadioButton is selected then the
	 * 	 validationBoolean is assigned true.
	 * - Else if any of the textfields are empty, then a message pops up and the focus returns to
	 * 	 that textfield and the validation checking stops and assigns the validationBoolean
	 * 	 variable FALSE
	 * - The boolean returns to where it was called (in the actionPerformed method) 
	 */
	
	public boolean validation()
	{
		boolean validationBoolean;
		
		if(!(nameTextField.getText()).equals(""))
		{
			if(!(quantityTextField.getText()).equals(""))
			{
				if(selectRadioButton())
				{
					validationBoolean = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select a flavor");
					mochaRadioButton.requestFocus();
					validationBoolean = false;	
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please enter the quantity");
				quantityTextField.requestFocus();
				validationBoolean = false;
			}	
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please enter name");
			nameTextField.requestFocus();
			validationBoolean = false;
		}
		return validationBoolean;
	}
	
	/* flavorSelect method
	 * - If the mochaRadioButton is selected then flavorSelectString is assigned "Mocha"
	 * - Else if the latteRadioButton is selected then flavorSelectString is assigned "Latte"
	 * - Else flavorSelectString is assigned "Drip"
	 * - (Called by addOrder method)
	 */
	public void flavorSelect()
	{
		// Stores the selected flavor in the flavorSelectString
		if(mochaRadioButton.isSelected())
		{
			flavorSelectString = "Mocha";
		}
		else if(latteRadioButton.isSelected())
		{
			flavorSelectString = "Latte";
		}
		else 
		{
			flavorSelectString = "Drip";
		}
	}
	/*
	 * addOrder method
	 * - Create local variables: quantityInteger to store coffee quantity, 
	 * 	 itemCostFloat to store individual coffee prices
	 * - Create constants for coffee prices:
	 * 	 MOCHA_FLOAT assigned 3.75, LATTE_FLOAT assigned 3.25, DRIP_FLOAT assigned 1.75
	 * - Get input: 
	 * - Retrieve flavor of coffee by calling the flavorSelect method
	 * - In the try block, retrieve the quantity and convert into integer, and store in quantityInteger
	 * - Calculate:
	 * - if mochaRadioButton is selected, multiply MOCHA_FLOAT with quantityInteger and store result into itemCostFloat
	 * - else if latteRadioButton is selected, multiply LATTE_FLOAT with quantityInteger and store result into itemCostFloat
	 * - else if dripRadioButton is selected, multiply DRIP_FLOAT with quantityInteger and store result into itemCostFloat
	 * - Calculate the accumulated totals:
	 * - Add itemCostFloat to subTotalFloat and store itemCostFloat
	 * - Display:
	 * - Call the displayAdd method to display output
	 * - Call the clearOrder method to clear textfields and enable the completeButton
	 * - Catch block for the quantity will show a dialog box alerting the user to enter quantity
	 * - Selecting the contents of the quantityTextField and putting the focus in quantityTextField
	 */
	public void addOrder()
	{
		// Local variables
		final float MOCHA_FLOAT = 3.75f, LATTE_FLOAT = 3.25f, DRIP_FLOAT = 1.75f;
		int quantityInteger;
		float itemCostFloat = 0.0f;
		
		// Retrieve the flavor selected
		flavorSelect();
		try
		{
			
			// In the try block, to check if the user enters a number for quantity
			//convert to int datatype
			quantityInteger = Integer.parseInt(quantityTextField.getText());
			
			// Calculate the totals for the customer
			if (mochaRadioButton.isSelected())
				{
						itemCostFloat = quantityInteger * MOCHA_FLOAT;
				}
			else if (latteRadioButton.isSelected())
				{
						itemCostFloat = quantityInteger * LATTE_FLOAT;
				}
			else if (dripRadioButton.isSelected())
				{
						itemCostFloat = quantityInteger * DRIP_FLOAT;
				}
			
			
			// Accumulate the totals
			subTotalFloat += itemCostFloat;
			
			// Call the displayAdd method to display output
			displayAdd(itemCostFloat);
			
			// Call the clearOrder method to clear textfields
			// Enable the completeButton
			clearOrder();
			completeButton.setEnabled(true);
		}
		catch(NumberFormatException err)
		{
			// - If the user doesn't enter a number for quantity
			//   JOptionPane will alert the user
			// - quantityTextField will be selected and have the focus
			JOptionPane.showMessageDialog(null, "Please enter a quantity number");
			quantityTextField.selectAll();
			quantityTextField.requestFocus();
		}
	}
	
	/*
	 * clearOrder method
	 * - clears quantityTextField, 
	 *   sets radio button to invisible button, 
	 *   and cursor focus on quantityTextField
	 * - Called by the addOrder and actionPerformed method
	 */
	public void clearOrder()
	{
	    //clear the quantity text field
		quantityTextField.setText("");
		//set radio button to the invisible button
		invisibleRadioButton.setSelected(true);
		//place the cursor back in quantityTextField
		quantityTextField.requestFocus();
		
	}
	/* displayAdd method
	 * - Declare instance variables
	 * - Create an object of DecimalFormat valueDecimalFormat formatted $ and two decimals
	 * - Retrieve quantity from textfield. Convert quantity into integer and store into quantityInteger
	 * - Retrieve name from textfield and store in nameString
	 * - outputTextArea will display:
	 * - Name from the user
	 * - Quantity from the user
	 * - Flavor selected from flavorSelectString
	 * - Coffee item total with the formatted price
	 * - Called by the addOrder method
	 */
	public void displayAdd(float newItemCostFloat)
	{
		// instance variables
		int quantityInteger;
		String nameString;
		
		// Format the values to currency format
		DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");
				
		// Convert quantity into integer and store into quantityInteger
		quantityInteger = Integer.parseInt(quantityTextField.getText());
		nameString = nameTextField.getText();

		 // Format itemCostFloat with $ and 2 decimal places
		String formattedCurrency = valueDecimalFormat.format(newItemCostFloat);
		
		// Format the customer information for name, quantity, coffee type and item total and display in text area
		String outputString = String.format("%-8s %-8d %-8s %-7s %n", nameString, quantityInteger, flavorSelectString, formattedCurrency);
		outputTextArea.append(outputString);
					
	}
	/*
	 * clearAll method
	 * - Clear the nameTextField and quantityTextField
	 * - Select invisible radio button
	 * - Clear the text area
	 * - Reset the subtotal
	 * - Set the title in text area
	 * - Set cursor focus on nameTextField
	 * - Called by the completeOrder method
	 */
	public void clearAll()
	{
		// Clear the text fields
		nameTextField.setText("");
		quantityTextField.setText("");
		// Set the radio button to the invisible button
		invisibleRadioButton.setSelected(true);
		
		// Clear text area, reset subtotal, and set the title in text area
		outputTextArea.setText("");
		subTotalFloat = 0;
		String titleString = String.format("%-8s %-8s %-8s %-8s %n", "Name:", "Qty", "Type", "Total");
		outputTextArea.append(titleString);
		
		// Place the cursor back in the nameTextField
		nameTextField.requestFocus();
	}
	
	/*
	 * completeOrder method
	 * - Create instance variables
	 * - Create an object of DecimalFormat valueDecimalFormat formatted $ and two decimals
	 * - Calculate tax by multiplying subTotalFloat and SALES_TAX_FLOAT. Store result into taxFloat.
	 * - Calculate grand total by adding subTotalFloat with taxFloat. Store result into grandTotalFloat
	 * - Display the subTotalFloat formatted in JOptionPane
	 * - Display the taxFloat formatted in JOptionPane
	 * - Display the grandTotalFloat formatted in JOptionPane
	 * - Call clearAll method to reset everything for the next customer 
	 */
	public void completeOrder()
	{
		float taxFloat, grandTotalFloat;
		final float SALES_TAX_FLOAT = .0975f;
		
		// Format the values to currency format
		DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");
		
		taxFloat = subTotalFloat * SALES_TAX_FLOAT;
		grandTotalFloat = subTotalFloat + taxFloat;
		
		// Display the accumulated totals in JOptionPane
		JOptionPane.showMessageDialog(null, "Subtotal: " + valueDecimalFormat.format(subTotalFloat) + '\n' + 
											"Tax: " + valueDecimalFormat.format(taxFloat) + '\n' + 
											"Grand Total: " + valueDecimalFormat.format(grandTotalFloat));
		clearAll();
	}
}
