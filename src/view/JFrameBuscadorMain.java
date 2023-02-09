package view;

import controller.Controlador;
import modelos.AgenteCliente;

public class JFrameBuscadorMain extends Thread {

	/**
	 * Launch the application.
	 */
	public void run1() {
			AgenteCliente model = new AgenteCliente();
			Controlador controller = new Controlador(null,model);
			JFrameBuscador view = new JFrameBuscador(controller);
			controller.setView(view);
			view.setVisible(true);
	}

}
