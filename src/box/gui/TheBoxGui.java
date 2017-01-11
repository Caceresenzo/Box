package box.gui;

import java.awt.Desktop;
import java.util.Optional;

import box.TheBox;
import box.resources.ResourcesManager;
import box.resources.TranslationManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sociuris.crash.CrashReport;

public class TheBoxGui extends Application {

	private static TheBoxGui instance = null;
	private static Thread mainThread = null;

	public static TheBoxGui createGui() {
		if (Desktop.isDesktopSupported() && instance == null) {
			mainThread = Thread.currentThread();
			new Thread("TheBox GUI") {
				@Override
				public void run() {
					Application.launch(TheBoxGui.class);
				}
			}.start();
			synchronized (mainThread) {
				try {
					mainThread.wait();
				} catch (InterruptedException e) {
					CrashReport.makeCrashReport("An error occurred while waiting gui creation", e);
				}
			}
		}
		return instance;
	}

	public static boolean hasGui() {
		return (instance != null);
	}

	private final WebView webView = new WebView();
	private final WebEngine webEngine = webView.getEngine();

	public TheBoxGui() {
		Thread.currentThread().setName("TheBox GUI");
		TheBoxGui.instance = this;

		synchronized (mainThread) {
			mainThread.notify();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("TheBox v" + TheBox.VERSION);
		stage.getIcons().add(new Image(ResourcesManager.getResourceAsStream("icon.png")));

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(webView, (primaryScreenBounds.getWidth() * 0.75),
				(primaryScreenBounds.getHeight() * 0.75));
		stage.setScene(scene);

		stage.sizeToScene();
		stage.centerOnScreen();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Alert closeAlert = new Alert(AlertType.WARNING, TranslationManager.GUI_CLOSE_MESSAGE,
						ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> result = closeAlert.showAndWait();
				if (result.isPresent()) {
					if (result.get() != ButtonType.YES)
						event.consume();
				}
			}
		});

		webEngine.load(
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

	public WebView getWebView() {
		return webView;
	}

}