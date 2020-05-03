package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	/**
	 * Dependencia do SellerService service
	 */
	private SellerService service;
	/*
	 * Declarando itens de tela que correspondem aos itens do MainView
	 */
	@FXML
	private TableView<Seller> tableviewSeller;

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;

	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;

	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Seller> obsList;

	@FXML
	public void obBtNewAction(ActionEvent event) {
		/**
		 * Acessando o Stage atual e passando como argumento para o parentStage
		 */
		Stage parentStage = Utils.currentStage(event);
		/**
		 * Instanciando um seller vazio e não passando nenhum dado
		 */
		Seller obj = new Seller();
		/**
		 * Chamando o metodo e passando a view que sera aberta
		 */
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	/**
	 * Injetando dependencia
	 * 
	 * @param service
	 */
	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();// Metodo auxiliar pra iniciar um componente na tela
	}

	/**
	 * Comportamento das colunas na tabela
	 */
	private void initializeNodes() {
		/*
		 * Padrão o javaFX pra iniciar o comportamento das colunas
		 */
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
		
		/**
		 * faz com que o tableview acompanhe o tamanho da vbox(se estique conforme ela
		 * estique)
		 */
		Stage stage = (Stage) Main.getMainScene().getWindow();// Referencia a sena
		tableviewSeller.prefHeightProperty().bind(stage.heightProperty());
	}

	/**
	 * Responsavel por acessar o serviço carregar os seller e jogar os
	 * seller no ObservableList
	 */
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("serviço esta nulo");
		}
		List<Seller> list = service.findAll();// Declara uma lista de departamento chamaddo list recebendo o findALL
													// do SellerService
		obsList = FXCollections.observableArrayList(list);// Instancia o obsList com o list
		tableviewSeller.setItems(obsList);// Passando o obsList para o tableviewSeller
		initEditButtons();
		initRemoveButtons();

	}

	/**
	 * Metodo responsavel por criar um Formulario dedialogo Stage referenciando a
	 * janela que criou a janela de dialogo
	 * 
	 * @param absoluteName
	 * @param parentStage
	 */
	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));// Carrega uma view
			Pane pane = loader.load();// objeto do tipo Pane com o nome de pane recebendo o loader

			/**
			 * Pegando (referencia) o controler da tela que pegou acima
			 */
			SellerFormController controller = loader.getController();
			controller.setSeller(obj);// Injetando departamento no controller
			controller.setSellerService(new SellerService());/// Injetando setSellerService no controller
			controller.subscribeDataChangerListener(this);
			controller.updateFormData();// Carregar os objetos acima, no formulario

			Stage dialogStage = new Stage();// Criando um palco na frente do outro
			dialogStage.setTitle("Seller data");// Titulo
			dialogStage.setScene(new Scene(pane));// Nova cena que vai aparecer(pane)
			dialogStage.setResizable(false);// Propriedade para que a janela possa ser redimensionada ou não
			dialogStage.initOwner(parentStage);// Indicando o pai da janela
			dialogStage.initModality(Modality.WINDOW_MODAL);// Tipo modal(enquanto n fechar n pode tocar de tela)
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "ERRO AO MOSTRAR A VIEW", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Voce tem certeza que quer deletar?");

		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("serviço nullo");
			}
			try {
			service.remove(obj);
			updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("erro remove object", null, e.getMessage(), AlertType.ERROR);
			}
	}
}
}
