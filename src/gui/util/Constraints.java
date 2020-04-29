package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
	/**
	 * Validar apenas digitos Caso contrario set o valor antigo
	 * 
	 * @param txt
	 */
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*")) {// Qualquer quantidade apenas de digito
				txt.setText(oldValue);
			}
		});
	}

	/**
	 * Validar o tamanho maximo Caso contrario set o valor antigo
	 * 
	 * @param txt
	 * @param max
	 */
	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}

	/**
	 * Validar o tipo double
	 * 
	 * @param txt
	 */
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
				txt.setText(oldValue);
			}
		});
	}
}