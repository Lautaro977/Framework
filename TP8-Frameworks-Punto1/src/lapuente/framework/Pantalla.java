package lapuente.framework;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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
		int num = 0;
		int pos;
		do {
			pos = 0;
			for (int i = 0; i < accion.size(); i++) {
				pos = i;
				System.out.println((pos + 1) + "." + accion.get(i).nombreItemMenu() + "("
						+ accion.get(i).descripcionItemMenu() + ")");

			}
			System.out.println((accion.size() + 1) + "." + "Salir");
			Scanner sc = new Scanner(System.in);

			try {
				num = sc.nextInt();
				if ((num > 0) && (num != accion.size() + 1) && (num <= accion.size())) {
					accion.get(num - 1).ejecutar();
				}
				if ((num <= 0) || ((num > accion.size()) && (num != accion.size() + 1))) {
					System.out.println("El numero que ingreso No es valido");
				}
			} catch (InputMismatchException e) {
				System.out.println("El caractar que ingreso No es un numero");
			}

		} while (num != accion.size() + 1);
		System.out.println("Salio del Menu");
	}

}
