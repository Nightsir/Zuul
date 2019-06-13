package org.bitbucket.nightsir.zuuladvanced.ui.dev;

import org.bitbucket.nightsir.zuuladvanced.configuration.Configuration;
import org.bitbucket.nightsir.zuuladvanced.ui.layoutfix.CloseableController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;

/**
 * Controller class for the resource monitor.
 * 
 * @author Christian Sami
 */
public class ResourceMonitorUIController implements CloseableController {
	private static final long UPDATE_INTERVAL_MILLI = 1000;
	private static final int MB = 1024 * 1024;
	
	@FXML
	private GridPane rootPane;
	@FXML
	private Label memoryLabel;
	@FXML
	private TextField memoryUsage;
	
	private Runtime runtime;
	private Thread updateThread;
	
	@FXML
	private void initialize() {
		runtime = Runtime.getRuntime();
		
		memoryLabel.setText(Configuration.get("resmon_mem"));
		
		updateThread = new Thread(this::update);
		updateThread.start();
	}
	
	@Override
	public void onExit(WindowEvent event) {
		updateThread.interrupt();
	}
	
	private void update() {
		while(!Thread.currentThread().isInterrupted()) {
			memoryUsage.setText((runtime.totalMemory() - runtime.freeMemory()) / MB + "/" + runtime.totalMemory() / MB + " MB");
			try {
				Thread.sleep(UPDATE_INTERVAL_MILLI);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
