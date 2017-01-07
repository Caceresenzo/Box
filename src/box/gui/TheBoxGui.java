package box.gui;

import java.awt.Desktop;

import box.TheBox;
import box.resources.ResourcesManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TheBoxGui extends Application {

	private static TheBoxGui instance = null;

	public static TheBoxGui createGui() {
		if(Desktop.isDesktopSupported() && instance == null)
			Application.launch(TheBoxGui.class);
		return instance;
	}

	public static boolean hasGui() {
		return (instance != null);
	}

	private final TheBoxWebBrowser webBrowser = new TheBoxWebBrowser();

	public TheBoxGui() {
		TheBoxGui.instance = this;
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("TheBox v" + TheBox.VERSION);
		stage.getIcons().add(new Image(ResourcesManager.getResourceAsStream("icon.png")));

		Scene scene = new Scene(webBrowser, 800, 600);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.centerOnScreen();

		webBrowser.load(
				"http://127.0.0.1:" + TheBox.PROPERTIES.getSection("webServer").getProperty("port").getAsInteger());

		stage.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		System.exit(0);
	}
	
	public void stopProperly() throws Exception {
		super.stop();
	}

	public TheBoxWebBrowser getWebBrowser() {
		return webBrowser;
	}

}