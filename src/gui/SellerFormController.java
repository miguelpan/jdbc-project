package gui;

/**
 * Controller do SellerForm
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	/*
	 * Dependencia para o Seller entity = nome
	 */

	private Seller entity;
	/*
	 * Dependencia para o SellerService service = nome
	 */

	private SellerService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();// atualizar dados da lista ao alterar um dado
		@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private Label labelErrorName;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	/**
	 * metodos para os butoes
	 */
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity esta nula");
		}
		if (service == null) {
			throw new IllegalStateException("Service esta nula");
		}
		try {
			entity = getFormData();// pegando os dados do formulario e salvando na variavel entity
			service.saveOrUpdate(entity);// Salvando no banco de dados
			notifyDataChangerListeners();// ira notificar que teve uma alteração nos dados
			Utils.currentStage(event).close();// Pegando a janela atual e fechando 
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} 
		catch (DbException e) {
			Alerts.showAlert("Erro ao salvar", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangerListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	/**
	 * pega os dados do formulario e retorna como um obj
	 * 
	 * @return obj
	 */
	private Seller getFormData() {
		Seller obj = new Seller();
		/* 
		 * instanciando
		 */
		ValidationException exception = new ValidationException("validation erro");

		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "o campo n pode ser vazio");
		}
		obj.setName(txtName.getText());

		if(exception.getErrors().size()>0) {
			throw exception;
		}
		return obj;
	}
	
	private  void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors .keySet();
		
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}

	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	public void subscribeDataChangerListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	/*
	 * Metodo responsavel por pegar os dados da entity e popular as caixas de texto
	 */
	public void updateFormData() {
		if (entity == null) {// Testa se esta valendo nulo
			throw new IllegalStateException("Erro nulo");
		}
		txtId.setText(String.valueOf(entity.getId()));// setando o valor do id no campo id/String.valueOf faz a
													  // conversão pro text receber
		txtName.setText(entity.getName());// setando o valor do id no campo name
	}

	public SellerFormController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();// Chamando o metodo abaixo
	}

	/**
	 * Limitando campos de entradas de texto
	 */
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);//
		Constraints.setTextFieldMaxLength(txtName, 15);
	}

}
