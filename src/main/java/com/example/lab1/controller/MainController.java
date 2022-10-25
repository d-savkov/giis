package com.example.lab1.controller;

import com.example.lab1.algoritm.circle.AlgorithmCircle;
import com.example.lab1.algoritm.circle.AlgorithmEllipse;
import com.example.lab1.algoritm.circle.AlgorithmHyperbola;
import com.example.lab1.algoritm.circle.AlgorithmParabola;
import com.example.lab1.algoritm.segment.AlgorithmBR;
import com.example.lab1.algoritm.segment.AlgorithmBY;
import com.example.lab1.algoritm.segment.AlgorithmDDA;
import com.example.lab1.dialog.Dialogs;
import com.example.lab1.function.TransformFigure;
import com.example.lab1.model.Edge;
import com.example.lab1.model.Figure;
import com.example.lab1.model.Pixel;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MainController implements Initializable {

	private final List<Circle> circleList = new ArrayList<>();
	private final List<Pixel> pixelList = new ArrayList<>();
	private int tileSize = 20;
	private int X_TILES;
	private int Y_TILES;
	private boolean check = false;
	private Pixel firstPixel;
	private Pixel endPixel;
	private List<Edge> lineList;

	@FXML
	private Label labelCoordinate;

	@FXML
	private Pane paneTile;

	@FXML
	private Label nameOfAlgorithm;

	@FXML
	private TextArea textAreaDebug;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Pane pane2;

	private Pixel[][] grid;

	private ResourceBundle resourceBundle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resourceBundle = resources;
		lineList = new ArrayList<>();
		initContent();
		initAlgorithmDDA(null);
		initTab1(null);
	}

	@FXML
	private void clear() {
		if (labelCoordinate.isVisible()) {
			initContent();
		} else {
			pane2.getChildren().clear();
			textAreaDebug.setText("");
		}
		if (circleList.size() != 0) {
			circleList.clear();
		}
		if (pixelList.size() != 0) {
			pixelList.clear();
		}
		check = false;
	}

	private void initContent() {
		paneTile.getChildren().clear();
		textAreaDebug.setText("");
		lineList.clear();
		X_TILES = 895 / tileSize;
		Y_TILES = 500 / tileSize;

		if (paneTile.getScene() != null) {
			paneTile.getScene().setOnKeyPressed(null);
		}
		paneTile.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				labelCoordinate.setText(
					" x=" + (int) event.getX() / tileSize + " y=" + (int) event.getY() / tileSize);
			}
		});
		grid = new Pixel[X_TILES][Y_TILES];
		createContent();
	}

	private void createContent() {
		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Pixel pixel = new Pixel(x, y, tileSize);

				grid[x][y] = pixel;

				paneTile.getChildren().add(pixel);
			}
		}
	}

	public void cageSetting(ActionEvent actionEvent) {
		List<Integer> choices = new ArrayList<>();
		initChoices(choices);

		ChoiceDialog<Integer> dialog = new ChoiceDialog<Integer>(tileSize, choices);
		dialog.setTitle(resourceBundle.getString("settings"));
		dialog.setHeaderText("");
		dialog.setContentText(resourceBundle.getString("size_of_cage"));
		Optional<Integer> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (result.get() == 1) {
				tileSetting(null);
				tileSize = 2;
			} else {
				tileSize = result.get();
			}
			initContent();
		}
	}

	public void tileSetting(ActionEvent actionEvent) {
		Pixel.setVisibleStoke(!Pixel.isVisibleStoke());
		initContent();
	}

	private void initChoices(List<Integer> choices) {
		choices.add(1);
		choices.add(2);
		choices.add(3);
		choices.add(4);
		choices.add(5);
		choices.add(10);
		choices.add(15);
		choices.add(20);
	}

	public void initAlgorithmDDA(ActionEvent actionEvent) {
		nameOfAlgorithm.setText(resourceBundle.getString("algoritm_CDA"));
		paneTile.setOnMouseClicked(mouseEventEventHandlerDDA());
	}

	public void initAlgorithmBR(ActionEvent actionEvent) {
		nameOfAlgorithm.setText(resourceBundle.getString("algoritm_BR"));
		paneTile.setOnMouseClicked(mouseEventEventHandlerBR());

	}

	public void initAlgorithmBY(ActionEvent actionEvent) {
		nameOfAlgorithm.setText(resourceBundle.getString("algoritm_BY"));
		paneTile.setOnMouseClicked(mouseEventEventHandlerBY());
	}

	public void initAlgorithmCircle(ActionEvent actionEvent) {
		nameOfAlgorithm.setText(resourceBundle.getString("circle"));
		paneTile.setOnMouseClicked(mouseEventEventHandlerCircle());
	}

	public void initAlgorithmHyperbola(ActionEvent actionEvent) {
		nameOfAlgorithm.setText(resourceBundle.getString("hyperbola"));
		new AlgorithmHyperbola(grid, X_TILES, Y_TILES, textAreaDebug, resourceBundle);
	}

	public void initAlgorithmEllipse(ActionEvent actionEvent) {
		nameOfAlgorithm.setText(resourceBundle.getString("ellipse"));
		paneTile.setOnMouseClicked(mouseEventEventHandlerEllipse());
	}

	public void initAlgorithmParabola(ActionEvent actionEvent) {
		nameOfAlgorithm.setText(resourceBundle.getString("parabola"));
		firstPixel = grid[0][0];
		endPixel = grid[grid[1].length - 1][grid[1].length - 1];
		endPixel.setEnd(true);
		new AlgorithmParabola(grid, X_TILES, Y_TILES, textAreaDebug, resourceBundle);
		endPixel.setEnd(false);
	}

	private EventHandler<MouseEvent> mouseEventEventHandlerCircle() {
		return event -> {
			if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
				findFirstTile(event);
				new AlgorithmCircle(grid, X_TILES, Y_TILES, textAreaDebug, resourceBundle);
				firstPixel.setStart(false);
				check = false;
			}
		};
	}

	private EventHandler<MouseEvent> mouseEventEventHandlerEllipse() {
		return event -> {
			if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
				findFirstTile(event);
				new AlgorithmEllipse(grid, X_TILES, Y_TILES, textAreaDebug, resourceBundle);
				firstPixel.setStart(false);
				check = false;
			}
		};
	}

	private EventHandler<MouseEvent> mouseEventEventHandlerDDA() {
		return event -> {
			if (check) {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					if (findEndTile(event) == 1) {
						AlgorithmDDA algorithmDDA = new AlgorithmDDA(grid, X_TILES, Y_TILES,
							textAreaDebug, resourceBundle);
						lineList.add(
							new Edge(algorithmDDA.getStartPixel(), algorithmDDA.getEndPixel(),
								algorithmDDA.getLine()));
						firstPixel.setStart(false);
						endPixel.setEnd(false);
					}
				}
			} else {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					findFirstTile(event);
				}
			}
		};
	}

	private EventHandler<MouseEvent> mouseEventEventHandlerBR() {
		return event -> {
			if (check) {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					if (findEndTile(event) == 1) {
						new AlgorithmBR(grid, X_TILES, Y_TILES, textAreaDebug, resourceBundle);
						firstPixel.setStart(false);
						endPixel.setEnd(false);
					}
				}
			} else {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					findFirstTile(event);
				}
			}
		};
	}

	private EventHandler<MouseEvent> mouseEventEventHandlerBY() {
		return event -> {
			if (check) {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					if (findEndTile(event) == 1) {
						new AlgorithmBY(grid, X_TILES, Y_TILES, textAreaDebug, resourceBundle);
						firstPixel.setStart(false);
						endPixel.setEnd(false);
					}
				}
			} else {
				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					findFirstTile(event);
				}
			}
		};
	}

	private void findFirstTile(MouseEvent event) {
		firstPixel = grid[(int) event.getX() / tileSize][(int) event.getY() / tileSize];
		firstPixel.setStart(true);
		check = true;
	}

	private int findEndTile(MouseEvent event) {
		endPixel = grid[(int) event.getX() / tileSize][(int) event.getY() / tileSize];
		endPixel.setEnd(true);
		check = false;
		if (firstPixel.equals(endPixel)) {
			Dialogs.getInstance().errorDialog(resourceBundle.getString("error"),
				resourceBundle.getString("text_error"));
			return 0;
		}
		return 1;
	}

	public void initTab1(Event event) {
		if (menuBar != null) {
			ObservableList<Menu> menus = menuBar.getMenus();
			menus.get(0).setDisable(false);
			menus.get(1).setDisable(false);
			menus.get(2).setDisable(false);
			labelCoordinate.setVisible(true);
		}
	}

	private EventHandler<KeyEvent> moveFigureKeyEvent(Figure figure) {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.D)) {
					moveAction(figure, 1, 0);
				} else if (event.getCode().equals(KeyCode.A)) {
					moveAction(figure, -1, 0);
				} else if (event.getCode().equals(KeyCode.W)) {
					moveAction(figure, 0, -1);
				} else if (event.getCode().equals(KeyCode.S)) {
					moveAction(figure, 0, 1);
				} else if (event.getCode().equals(KeyCode.P)) {
					changeAction(figure, -0.5);
				} else if (event.getCode().equals(KeyCode.M)) {
					changeAction(figure, 0.5);
				} else if (event.getCode().equals(KeyCode.L)) {
					rotateAction(figure);
				}
			}
		};
	}

	private void rotateAction(Figure figure) {
		double alpha = Dialogs.getInstance().showDialog(resourceBundle.getString("alpha"));
		int m = 0;
		int n = 0;
		do {
			m = Dialogs.getInstance().showDialog(resourceBundle.getString("m"));
			n = Dialogs.getInstance().showDialog(resourceBundle.getString("n"));

		} while (m > grid.length || n > grid[0].length || m < 0 || n < 0);

		Figure newFigure = TransformFigure.getInstance()
			.rotationFigure(figure, Math.toRadians(alpha), m, n, grid.length, grid[1].length);
		clear();
		drawFigureByDDA(newFigure);
		paneTile.getScene().setOnKeyPressed(moveFigureKeyEvent(newFigure));
	}

	private void changeAction(Figure figure, double changeSize) {
		if (figure.getSize() + changeSize > 0) {
			Figure newFigure = TransformFigure.getInstance()
				.changeSizeOfFigure(figure, changeSize, grid.length, grid[1].length);
			clear();
			drawFigureByDDA(newFigure);
			paneTile.getScene().setOnKeyPressed(moveFigureKeyEvent(newFigure));
		}
	}

	private void moveAction(Figure figure, final int migrationConstantX,
							final int migrationConstantY) {
		Figure newFigure = TransformFigure.getInstance()
			.moveFigure(figure, migrationConstantX, migrationConstantY, grid.length,
				grid[1].length);
		clear();
		drawFigureByDDA(newFigure);
		paneTile.getScene().setOnKeyPressed(moveFigureKeyEvent(newFigure));
	}

	private void drawFigureByDDA(Figure figure) {
		for (Edge edge : figure.getEdgeList()) {

			AlgorithmDDA algorithmDDA = new AlgorithmDDA(grid, edge.getStarPixel(),
				edge.getEndPixel(), X_TILES, Y_TILES, textAreaDebug, resourceBundle);
			if (edge.getStarPixel().getX() == 9 && edge.getStarPixel().getY() == 13
				|| edge.getEndPixel().getX() == 9 && edge.getEndPixel().getY() == 13) {
				lineList.add(
					new Edge(edge.getStarPixel(), edge.getEndPixel(), algorithmDDA.getLine(),
						false));
			} else {
				lineList.add(
					new Edge(edge.getStarPixel(), edge.getEndPixel(), algorithmDDA.getLine(),
						true));
			}
		}
	}
}
