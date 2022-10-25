package com.example.lab1.dialog;

import java.util.Objects;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;


public class Dialogs {

	private static Dialogs instance = null;

	public static Dialogs getInstance() {
		if (instance == null) {
			instance = new Dialogs();
		}
		return instance;
	}

	public int showDialog(String name) {
		String res = null;
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle(name);
		dialog.setHeaderText("");
		dialog.setContentText(name);
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			res = result.get();
		}
		return Integer.parseInt(Objects.requireNonNull(res));
	}

	public int showDialog(String name, int limit) {
		String res = null;
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle(name);
		dialog.setHeaderText("");
		dialog.setContentText(name + " <" + limit);
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			res = result.get();
		}
		return Integer.parseInt(Objects.requireNonNull(res));
	}

	public void errorDialog(String error, String textError) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(error);
		alert.setHeaderText("");
		alert.setContentText(textError);

		alert.showAndWait();
	}

	public void errorDialog(String error, String textError, int limit) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(error);
		alert.setHeaderText("");
		alert.setContentText(textError + " " + limit);

		alert.showAndWait();
	}
}
