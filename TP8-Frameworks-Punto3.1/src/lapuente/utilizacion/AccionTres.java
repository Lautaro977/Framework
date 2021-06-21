package lapuente.utilizacion;

import lapuente.framework.Accion;

public class AccionTres implements Accion {

	@Override
	public String descripcionItemMenu() {
		// TODO Auto-generated method stub
		return "prueba si el framework anda bien";
	}

	@Override
	public void ejecutar() {
		// TODO Auto-generated method stub
		System.out.println("Ejecutando AccionTres...");
	}

	@Override
	public String nombreItemMenu() {
		// TODO Auto-generated method stub
		return "Accion 3";
	}

}
