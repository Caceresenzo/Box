package box.gui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TheBoxWebBrowser extends Region {

	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();

	public TheBoxWebBrowser() {
		getStyleClass().add("browser");
		getChildren().add(browser);

	}

	public void load(String url) {
		webEngine.load(url);
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 500;
	}

}