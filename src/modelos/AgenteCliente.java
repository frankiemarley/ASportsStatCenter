package modelos;
import jade.core.Agent;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import view.JFrameBuscadorMain;

public class AgenteCliente extends Agent {
	//Semaforo para parar el hilo y poder introducir los datos en la interfaz
	Semaphore mutex = new Semaphore(0);
	//Main de la interfaz para ejecutarla
	JFrameBuscadorMain interfaz = new JFrameBuscadorMain();
	//Variable que recibira los parametros escritos en la interfaz
	String busqueda = "";
	
	/*Metodo que recibe los parametros de la interfaz atraves del controlador y lo guarda en la variable*/
	public void recibirParametros(String uno,String dos,String tres, String cuatro,String cinco,String seis) {
		System.out.println(uno+dos+tres+cuatro+cinco+seis);
		String parametros = uno+dos+tres+cuatro+cinco+seis;
		System.out.println(parametros);
		this.busqueda = parametros;
		System.out.println(busqueda);
		System.out.println(getBusqueda());
		mutex.release();
	}
	
	/*Metodo que da el valor de la variable busqueda */
	public String getBusqueda() {
		return busqueda;
	}
	// METODO PRINCIPAL E HILO DE JADE 
	public void setup()
	{	//Lanzamos a la vez la interfaz 
		interfaz.run1();
		//Entramos en el behaviour
		addBehaviour(new CyclicBehaviour(this){
			private static final long serialVersionUID = 1L;
			public void action()
			{
				//Metodo action realiza el comportamiento del agenteCliente
				System.out.println(getBusqueda());//Sistem para ver si la variable sigue sin alterar
				//Usamos el semaforo para parar el hilo y poder introducir los datos 
				try { // Tambien con espera activa se puede hacer
					/*while (busqueda == null || busqueda == "") {
					//ESPERA ACTIVA
					getBusqueda(); }*/
					mutex.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////// USANDO SCANER EN VEZ DE LA INTERFAZ //////////////////////////////////////
				/////////////////////////////////////////////////////////////////////////////////////////////////
				
				/*Scanner scanner=new Scanner(System.in);
				System.out.print("Introduzca el texto a buscar: ");
				String temp=scanner.nextLine();
				Scanner scanner2=new Scanner(System.in);
				System.out.print("Introduzca el texto a buscar: ");
				String temp2=scanner.nextLine();
				Scanner scanner3=new Scanner(System.in);
				System.out.print("Introduzca el texto a buscar: ");
				String temp3=scanner.nextLine();
				Scanner scanner4=new Scanner(System.in);
				System.out.print("Introduzca el texto a buscar: ");
				String temp4=scanner.nextLine();
				Scanner scanner5=new Scanner(System.in);
				System.out.print("Introduzca el texto a buscar: ");
				String temp5=scanner.nextLine();*/

				//Volvemos a comprobar busqueda 
				System.out.println("El valor de la cadena es"+busqueda);
				/*Scanner scanner6=new Scanner(System.in);
				System.out.print("Introduzca el texto a buscar: ");
				String temp6=scanner6.nextLine();*/
				
				//Asignamos a cadenaAbuscar la busqueda
				String cadenaAbuscar = busqueda;
				//Enviamos la solicitud de busqueda defiendo agente , que realiza buscar , la cadena a buscar
				Utils.enviarMensaje(this.myAgent, "buscar", cadenaAbuscar);

				//ESPERA HASTA CONTESTAR CICLYC MEDIANTE BUSCADOR 
				ACLMessage msg=blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));		
				//Cuando el agente AgenteBuscador responde, imprimimos su respuesta por pantalla
				try
				{
					String mensajes= (String) msg.getContentObject();
					System.out.println(mensajes);
				}
				catch (UnreadableException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}