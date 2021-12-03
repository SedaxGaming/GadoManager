package com.gadomanager.gadomanager.controllers.filtros;

import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.gadomanager.gadomanager.classes.Rebanhos;
import com.gadomanager.gadomanager.controllers.consultaController;
import com.gadomanager.gadomanager.utils.DAODatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class filtroRebanhoController {

	@FXML
	private TextField txtNome;

	@FXML
	private TextArea txtADescricao;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnFiltrar;
	
	private consultaController consultaController;

	public void setConsultaController(consultaController consultaController) {
		this.consultaController = consultaController;
	}
	
	@FXML
	public void filtrar() throws Exception {

		String sql = "SELECT * from rebanhos ";

		sql += "WHERE 1=1 ";
		if (!txtNome.getText().isEmpty()) {
			String nome = txtNome.getText();
			sql += "AND rebanhos.nome LIKE '%" + nome + "%' ";

		}
		if (!txtADescricao.getText().isEmpty()) {
			String desc = txtNome.getText();
			sql += "AND rebanhos.descricao LIKE '%" + desc + "%' ";
		}
		
		System.out.println(sql);

		DAODatabase daoJDBC = new DAODatabase();
		ResultSet queryResult = null;
		try {
			queryResult = daoJDBC.selectLazy(sql);
		} catch (Exception e) {

		}

		ObservableList<Object> result = FXCollections.observableArrayList();

		if (!(queryResult == null)) {

			while (queryResult.next()) {

				Rebanhos rebanho = new Rebanhos();

				String Nome = queryResult.getString("nome");
				rebanho.setNome(Nome);

				String Desc = queryResult.getString("descricao");
				rebanho.setDescricao(Desc);

				result.add(rebanho);
			}

			Stage window = (Stage) btnFiltrar.getScene().getWindow();
			window.close();
			consultaController.setPerspectiveList(result);

		} else {
			Stage window = (Stage) btnFiltrar.getScene().getWindow();
			window.close();
		}

	}

	@FXML
	public void cancelar() {
		
		consultaController.setPerspectiveList(null);
		Stage window = (Stage) btnCancelar.getScene().getWindow();
		window.close();
	}
}
