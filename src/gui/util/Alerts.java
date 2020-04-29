package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);//Instanciando o Alert
		alert.setTitle(title);//Titulo
		alert.setHeaderText(header);//Cabeçalho
		alert.setContentText(content);//Conteudo
		alert.show();//Mostrar alert
	}
}