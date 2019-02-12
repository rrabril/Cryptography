package criptografia;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Formulario extends JFrame {
	
	Formulario(){
	
	JPanel addressPanel = new JPanel();
	Border border = addressPanel.getBorder();
	Border margin = new EmptyBorder(10, 10, 10, 10);
	CompoundBorder borde_compuesto = new CompoundBorder(border, margin);
	addressPanel.setBorder(borde_compuesto);
	Container contenedor = getContentPane();
	contenedor.add(addressPanel);
	setSize(600,400);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	

	GridBagLayout panelGridBagLayout = new GridBagLayout();
	panelGridBagLayout.columnWidths = new int[] { 86, 86, 0 };
	panelGridBagLayout.rowHeights = new int[] { 20, 20, 20, 20, 20, 0 };
	panelGridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
	panelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	addressPanel.setLayout(panelGridBagLayout);

	addLabelAndTextField("City:", 0, addressPanel);
	addLabelAndTextField("Street:", 1, addressPanel);
	addLabelAndTextField("State:", 2, addressPanel);
	addLabelAndTextField("Phone:", 3, addressPanel);
	addLabelAndTextField("Mail:", 4, addressPanel);
	setVisible(true);
	}
	
	private void addLabelAndTextField(String labelText, int yPos, Container containingPanel) {

	    JLabel label = new JLabel(labelText);
	    GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
	    gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
//	    gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
	    gridBagConstraintForLabel.gridx = 0;
	    gridBagConstraintForLabel.gridy = yPos;
	    containingPanel.add(label, gridBagConstraintForLabel);

	    JTextField textField = new JTextField();
	    GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
	    gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
//	    gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
	    gridBagConstraintForTextField.gridx = 1;
	    gridBagConstraintForTextField.gridy = yPos;
	    containingPanel.add(textField, gridBagConstraintForTextField);
	    textField.setColumns(10);
	}
	
	public static void main(String[] args) {
		Formulario formulario1 = new Formulario();
	}

}
