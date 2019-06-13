package org.bitbucket.nightsir.zuuladvanced.ui.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;

/**
 * Controller for changing and controlling the {@link System#out} and {@link System#err} streams.
 * 
 * @author Christian Sami
 */
public class StreamController {
	private static Alert errorAlert;
	private static TextArea errorTextArea;
	
	private static void initErrorAlert() {
		errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Fatal Zuul Error");
		errorAlert.setHeaderText("There was a fatal error in the Zuul-Game.");
		errorAlert.setContentText("Please send error message in the details if you need any asistance.");

		Label label = new Label("The error message was:");

		errorTextArea = new TextArea();
		errorTextArea.setEditable(false);
		errorTextArea.setWrapText(true);

		GridPane expContent = new GridPane();
		expContent.add(label, 0, 0);
		expContent.add(errorTextArea, 0, 1);

		errorAlert.getDialogPane().setExpandableContent(expContent);
		
		errorAlert.setOnCloseRequest((e) -> System.exit(1));
	}
	
	private static void addLineToAlert(String line) {
		if (errorAlert == null) {
			initErrorAlert();
		}
		errorTextArea.appendText(line);
		errorAlert.show();
	}
	
	/**
	 * Changes the {@link System#err} stream to output in a alert.
	 */
	public static void changeSystemError() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				addLineToAlert(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				addLineToAlert(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};
		
		System.setErr(new PrintStream(out, true));
	}
	
	/**
	 * Changes the {@link System#out} stream to output in the given node.
	 * 
	 * @param outputNode text-node to change output to
	 */
	public static void changeSystemOutTo(TextInputControl outputNode) {
		OutputStream out = new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				outputNode.appendText(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				outputNode.appendText(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};
		
		System.setOut(new PrintStream(out, true));
	}
}
