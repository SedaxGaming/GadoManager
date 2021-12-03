package com.gadomanager.gadomanager.controllers;

import org.controlsfx.control.Notifications;

import com.gadomanager.gadomanager.classes.Empresas_Pessoas;
import com.gadomanager.gadomanager.classes.Rebanhos;
import com.gadomanager.gadomanager.classes.Usuarios;
import com.gadomanager.gadomanager.utils.DAOHibernate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class cadastroRebanhoController {

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;
	
	private Rebanhos rebanho;
	
	public void populateFields(Rebanhos reb) {

		setRebanho(reb);

		txtADescricao.setText(reb.getDescricao());
		txtNome.setText(reb.getNome());

	}	
	
	public void setRebanho(Rebanhos rebanho) {
		this.rebanho = rebanho;
	}

	private Boolean editMode = false;

	@FXML
	private TextField txtNome;

	@FXML
	private TextArea txtADescricao;

	private Usuarios user;
	
	public void setEdit(boolean EditMode) {
		if (EditMode) {
			this.editMode = true;
		} else {
			this.editMode = false;
		}
	}

	public Usuarios getUser() {
		return user;
	}

	public void setUser(Usuarios user) {
		this.user = user;
	}
	
	public Rebanhos getRebanho() {
		return rebanho;
	}


	@FXML
	public void salvar() throws Exception {
		
		if (editMode) {

			rebanho = this.getRebanho();

			DAOHibernate<Rebanhos> daoV = new DAOHibernate<>(Rebanhos.class);

			Rebanhos rebanhosEdit = daoV.getAllById(rebanho.getIdRebanho());

			String desc = txtADescricao.getText();
			rebanhosEdit.setDescricao(desc);
			String nome = txtNome.getText();
			rebanhosEdit.setNome(nome);
			
			daoV.beginTransaction().update(rebanhosEdit).commitTransaction().closeAll();

			Stage window = (Stage) btnCancelar.getScene().getWindow();
			window.close();
			
			Notifications.create().title("Alerta").text("Rebanho editada com sucesso").showConfirm();
			

		}else { 
		

		String nome = txtNome.getText();
		String descricao = txtADescricao.getText();
		Empresas_Pessoas empresa = user.getIdEmpresas_Pessoa();

		DAOHibernate<Rebanhos> daoR = new DAOHibernate<Rebanhos>(Rebanhos.class);

		Rebanhos rebanho = new Rebanhos(nome, descricao, empresa);

		if (txtNome.getText().isEmpty()) {
			Notifications.create().title("Cadastro de Rebanhos").text("O rebanho precisa de um nome!").showError();
		} else {

			daoR.beginTransaction().save(rebanho).commitTransaction().closeAll();
			Notifications.create().title("Alerta").text("Rebanho " + rebanho.getNome() + " Adicionado com sucesso! ")
					.showConfirm();
			txtNome.clear();
			txtADescricao.clear();
		}
	}	}

	@FXML
	public void cancelar() {

		Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
		currentStage.close();
	}

}
