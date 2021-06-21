package lapuente.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Pantalla {

	private List<Accion> accion = new ArrayList<>();

	public Pantalla(String ubicacion) {
		Properties prop = new Properties();
		try (InputStream configFile = getClass().getResourceAsStream(ubicacion);) {
			prop.load(configFile);
			String clase = prop.getProperty("acciones");
			String[] div = clase.split(";");
			for (int i = 0; i < div.length; i++) {
				Class c = Class.forName(div[i]);
				this.accion.add((Accion) c.getDeclaredConstructor().newInstance());
			}

		} catch (Exception e) {
			throw new RuntimeException("No pude crear la Instancia de 'accion'");
		}
	}

	public void menu() {
		Terminal terminal;
		Panel buttonPanel;
		try {
			terminal = new DefaultTerminalFactory().createTerminal();
			Screen screen = new TerminalScreen(terminal);
			screen.startScreen();

			BasicWindow window = new BasicWindow();

			window.setHints(Arrays.asList(Window.Hint.CENTERED));
			MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
					new EmptySpace(TextColor.ANSI.BLUE));

			buttonPanel = new Panel(new GridLayout(2).setHorizontalSpacing(1));

			WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

			for (int i = 0; i < accion.size(); i++) {
				int pos = i;
				buttonPanel.addComponent(new Button(accion.get(i).nombreItemMenu(), new Runnable() {
					@Override
					public void run() {
						// Actions go here
						new MessageDialogBuilder().setTitle(accion.get(pos).descripcionItemMenu())
								.setText(accion.get(pos).nombreItemMenu()).build().showDialog(textGUI);
					}
				}));

			}
			buttonPanel.addComponent(new Button("Salir", new Runnable() {
				@Override
				public void run() {
					// Actions go here
					try {
						terminal.exitPrivateMode();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}));

			window.setComponent(buttonPanel);
			gui.addWindowAndWait(window);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
