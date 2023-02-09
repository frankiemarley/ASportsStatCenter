package controller;

import modelos.AgenteCliente;
import view.JFrameBuscador;

public class Controlador {
	
	private JFrameBuscador view;
	private AgenteCliente model;
	
	
	public Controlador(JFrameBuscador view,AgenteCliente model) {
		this.view = view;
		this.model = model;
	}
	
	public void setView(JFrameBuscador view) {
		this.view = view;
	}
	
	public void cogerParametros() {
		
		String nombre1 = view.getTextFieldnombre1().getText()+"-";
		String apellido1 = view.getTextFieldapellido1().getText()+"-";
		String nombre2 =view.getTextFieldnombre2().getText()+"-";
		String apellido2 =view.getTextFieldapellido2().getText()+"-";
		String stat = view.getTextFieldStat().getText()+"-";
		String rop = view.getTextFieldROP().getText();
		
		model.recibirParametros(nombre1, apellido1, nombre2, apellido2, stat, rop);
		
	}
	
}
