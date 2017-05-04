package org.gvanya.calculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.form.Button;

/**
 * Calculator implementation using Apache Wicket.
 * 
 * @author Gerasymchuk Ivan
 */

public class Calculator extends WebPage{	
	

	// Main input / output field.
	private final TextField<String> displayField = new TextField<String>("display",  Model.of(""));
	
	// Notes output field.
	private final TextArea<String> additionalField = new TextArea<String>("notes",  Model.of(""));
	
	// Whole calculator form.
	private Form<?> form = new Form<String>("form");
	
	// List of number buttons.
	private List<String> numbers = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
	
	// List of operation buttons.
	private List<String> operations = Arrays.asList("+", "-", "x", "/");
	
	// The active operation.
	private String operation;
	
	// The first entered value.
	private String firstOperand;
	
	// The second entered value.
	private String secondOperand;
	
	// If operation entered.
	private boolean isOperat;
	
	// If dot button is entered.
	private boolean isDot;
	
	// If display filled.
	private boolean isFilled;
	
	// If equal button is entered.
	private boolean isEqual; 
	
	/**
	 * Default constructor which is responsible for the form content.
	 */
	
	public Calculator() {	
		form.add(displayField);
		form.add(additionalField);
		displayField.setModel(Model.of("0"));
		additionalField.setModel(Model.of("0"));
		setDefault();
		isNumbersBtn();
		isOperationsBtn();
		isDotBtn();
		isClearBtn();
		isBackspaceBtn();
		isPlusMinusBtn();
		isEqualBtn();
		add(form);
	}
	
	/**
     * Finds and initializes all number buttons.
     */
	private void isNumbersBtn() {
		
		for (final String number : numbers) {
			
			Button numbers = new Button(number) {
			
				@Override
				public void onSubmit() {
					String currentBtn = displayField.getInput();
					if (isFilled) {
						if (currentBtn.equals("0")) {
							currentBtn = number;
						} 
						else {
							currentBtn += number;
						}
					} 
					else {
						currentBtn = number;
						isFilled = true;
					}
					displayField.setModelObject(currentBtn);
				}
			};
		
			form.add(numbers);
		}
	}
	
	/**
     * Finds and initializes all operation buttons.
     */
	private void isOperationsBtn() {

		for (final String sign : operations) {

			Button signButton = new Button(sign) {
			
				@Override
				public void onSubmit() {

					String currentOperand = displayField.getInput();
					if (currentOperand.equals("Error")) {
						setDefault();
						displayField.setModel(Model.of("0"));
					}

					if (!isOperat) {
						operation = sign;
						isDot = false;
						isFilled = false;
						isOperat = true;
						firstOperand = currentOperand;
						setHistoryOperation();
					} 
					else {
						secondOperand = currentOperand;
						setHistoryOperation();
						getResult();
						operation = sign;
						isDot = false;
					}
				}
			};
			
			form.add(signButton);
		}
	}
	
	
	/**
     * Finds and initializes dot button.
     */
	private void isDotBtn() {
		
		Button dotButton = new Button("dotBtn") {
			
			@Override
			public void onSubmit() {
				
				if(!isDot) {
					displayField.setModelObject(displayField.getInput()+".");
				isDot = true;
				}
			}
		};
		
		form.add(dotButton);
	}
	
	/**
     * Finds and initializes clear button.
     */
	private void isClearBtn() {
		
		Button clearButton = new Button("clearBtn") {
			
			@Override
			public void onSubmit() {
				setDefault();
				displayField.setModel(Model.of("0"));
				additionalField.setModel(Model.of("0"));
			}
		};

		form.add(clearButton);
	}
	
	/**
     * Finds and initializes plus/minus button.
     */
	private void isPlusMinusBtn() {
		
		Button plusMinusButton = new Button("plusMinusBtn") {
		
			@Override
			public void onSubmit() {
				if (isFilled || isEqual) {
					String currentOperand = displayField.getInput();
					if (currentOperand.startsWith("-")) {
						currentOperand = currentOperand.substring(1);
					} 
					else {
						currentOperand = "-" + currentOperand;
					}
					displayField.setModelObject(currentOperand);
				}
			}
		};
		
		form.add(plusMinusButton);
	}

	/**
     * Finds and initializes backspace button.
     */
	private void isBackspaceBtn() {
		
		Button backspaceButton = new Button("backspaceBtn") {
			
			@Override
			public void onSubmit() {
				if (isFilled || isEqual) {
					String currentOperand = displayField.getInput();
					if (currentOperand.length() > 1) {
						currentOperand = currentOperand.substring(0, currentOperand.length() - 1);
						displayField.setModelObject(currentOperand);
					}
					else {
						displayField.setModel(Model.of("0"));
					}
				}
			}
		};
		
		form.add(backspaceButton);
	}
	
	/**
     * Finds and initializes equal button.
     */
	private void isEqualBtn() {

		Button equalButton = new Button("equalBtn") {
		
			@Override
			public void onSubmit() {
				
				if (isOperat) {
					secondOperand = displayField.getInput();
					isOperat = false;
					getResult();
					isEqual = true; 
				}
			}
		};
		
		form.add(equalButton);
	}
	
	/**
     * Resets all parameters to the initial level.
     */
	private void setDefault() {
		
		firstOperand = "0";
		secondOperand = "0";
		isDot = false;
		isFilled = false;
		isOperat = false;
	}
	
	/**
     * Initializes notes on display (firstOperand + operation).
     */
	private void setHistoryOperation() {
		
		additionalField.setModel(Model.of(firstOperand + operation));
	}
	
	/**
     * Initializes notes on display (firstOperand + operation + secondOperand).
     *
     * @param math operation result
	 */
	private void setHistoryEqual(String result) {
		
		additionalField.setModel(Model.of(firstOperand + operation + secondOperand + "=" + result));
	}
	
	/**
     * Gets result.
     */
	private void getResult() {
		
		String result = mathOperation(firstOperand, secondOperand);
		displayField.setModelObject(result);
		setHistoryEqual(result);
		firstOperand = result;
		isDot = true;
		isFilled = false;
	}
	
	/**
	 * Calculates result.
	 * 
	 * @param the first entered operand
     * @param the second entered operand
     * @return math result between first and second operands
	 */
	private String mathOperation(String first, String second) {

		BigDecimal firstOperand = new BigDecimal(first);
		BigDecimal secondOperand = new BigDecimal(second);
		BigDecimal result = new BigDecimal("0");
		
		switch (operation) {

		case ("+"):
			result = firstOperand.add(secondOperand);
			break;
			
		case ("-"):
			result = firstOperand.subtract(secondOperand);
			break;
		
		case ("x"):
			result = firstOperand.multiply(secondOperand);
			break;
			
		case ("/"):
			if (secondOperand.equals(result)) {
				setDefault();
				return "Infinity";
			}
			result = firstOperand.divide(secondOperand, 6, RoundingMode.HALF_UP);			
			break;		
		}
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
    	DecimalFormat df = (DecimalFormat) nf;
    	df.setMaximumFractionDigits(6);
    	df.setMinimumFractionDigits(0);
    	df.setGroupingUsed(false); 
    	return df.format(result);
	}
}

	