package gui;

/**
 * Controller do DepartmentForm
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	/*
	 * Dependencia para o Department entity = nome
	 */

	private Department entity;
	/*
	 * Dependencia para o DepartmentService service = nome
	 */

	private DepartmentService service;
	
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
		} catch (DbException e) {
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
	private Department getFormData() {
		Department obj = new Department();

		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	public void setDepartment(Department entity) {
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

	public DepartmentFormController() {
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
