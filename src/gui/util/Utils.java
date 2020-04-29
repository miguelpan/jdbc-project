package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	/*
	 * Retorna o palco atual Clica no botão dai a classe pega o palco atual dele e
	 * logo apos ela abre um tela em cima
	 *
	 */
	public static Stage currentStage(ActionEvent event) {// Pegado staage a parti do objeto de evento
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	/*
	 * Converter o valor da caixa de texto em inteiro e se o valor da caixinha
	 * conter letras ou algo diferente ira retorar null
	 */
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
