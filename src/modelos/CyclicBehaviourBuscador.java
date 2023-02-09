package modelos;
import jade.core.behaviours.CyclicBehaviour;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.net.URL;

public class CyclicBehaviourBuscador extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	public static final int LIMITES=50;
	
	public void action() {
		// Creamos la espera de mensajes en modo bloqueante y con un filtro de tipo REQUEST
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		try
		{
			//Imprimimos por pantalla el texto a buscar y creamos una lista de posibles respuestas.
			//System.out.println(msg.getSender().getName()+":"+ (String)msg.getContentObject());
			// Llamamos a nuestro método buscarCadena(), que utilizamos para realizar la busqueda
			///////////////////////////////////////////////////////////////////////////////////////
			String respuesta=compararEstadisticasJugadores((String)msg.getContentObject());;
			///////////////////////////////////////////////////////////////////////////////////////
			//Cuando la búsqueda ha finalizado, enviamos un mensaje de respuesta
			ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
			aclMessage.addReceiver(msg.getSender());
			aclMessage.setOntology("ontologia");
			aclMessage.setLanguage(new SLCodec().getName());
			aclMessage.setEnvelope(new Envelope());
			aclMessage.getEnvelope().setPayloadEncoding("ISO8859_1");
			aclMessage.setContentObject((Serializable)respuesta);
			this.myAgent.send(aclMessage);
		}
		catch (UnreadableException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String compararEstadisticasJugadores(String cadena) throws IOException
	{
		String string = cadena;
		String[] parts = string.split("-");
		String nombre1 = parts[0]; // 123
		String apellido1= parts[1]; // 654321
		String nombre2 = parts[2];
		String apellido2 = parts[3];
		String estadistica = parts[4];
		String ROP = parts[5];
		String estad="";
		String season="";


		//Definimos la lista de sitios web
		Scanner scan;
		//System.out.println(estadistica);
		//System.out.println(ROP);
		List<String> sitios= Arrays.asList("https://www.msn.com/en-us/sports/nba/player-stats/sp-ss-inseason-vw-points",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-ss-inseason-vw-assists",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-ss-inseason-vw-rebounds",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-ss-inseason-vw-steals",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-ss-inseason-vw-blocks",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-ss-postseason-vw-pointss",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-vw-assists",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-vw-rebounds",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-vw-steals",
				"https://www.msn.com/en-us/sports/nba/player-stats/sp-vw-blocks");

		int i=0;
		String sitio="";
		boolean encontrado=false;
		//Buscamos en cada una de las páginas web si hay coincidencia con el texto que ha enviado el cliente
		while(i<sitios.size() && !encontrado){
			if(sitios.get(i).contains(ROP) && sitios.get(i).contains(estadistica)){
				sitio=sitios.get(i);
				estad=estad(estadistica);
				season=faseCampeonato(ROP);
				encontrado=true;
				// ROP -> Temporada regular o  playoff 
			}
			i++;
		}

		if(i==sitios.size())
			return "Atributo estadistica o ROP incorrectos";	
		URL url= new URL(sitio);
		scan=new Scanner(url.openStream());
		String content = new String();
		while (scan.hasNext())
			content += scan.nextLine();
		scan.close();
		String plainText= Jsoup.parse(content).text();
		Scanner s = new Scanner(plainText);
		Scanner s2 = new Scanner(plainText);
		Scanner s3 = new Scanner(plainText);
		Scanner s4 = new Scanner(plainText);
		boolean res = true, res2 = true, res3 = true, res4= true;
		String dato1="" ,dato2="", dato3 ="", dato4 = "";
		double d1=0.0;double d2=0.0;double d3=0.0;double d4=0.0;double max=0.0;
		String x="", y="";
		String maximo="";
		String nombreFinal="";
		String apellidoFinal="";

		//////////////////////////////JUGADOR 1/////////////////////////////////////////////

		//BUSQUEDA POR NOMBRE

		while(s.hasNext() && res){
			if(s.next().contains(nombre1)){
				s.next();
				s.next();
				s.next();					
				s.next();
				if(s.hasNext("\\d+\\.\\d+")) {
					dato1 = s.next();

				}
			}
			else
				res= true;
		}
		s.close();

		//BUQUEDA POR APELLIDO

		while(s2.hasNext() && res2){
			if(s2.next().contains(apellido1)){
				s2.next();		
				s2.next();
				s2.next();
				if(s2.hasNext("\\d+\\.\\d+")) {
					dato2 = s2.next();
				}
			}
			else
				res2= true;
		}
		s2.close();

		///////////////////////////////////////JUGADOR 2/////////////////////////////////

		//BUSQUEDA POR NOMBRE

		while(s3.hasNext() && res3){
			if(s3.next().contains(nombre2)){
				s3.next();
				if(!s3.hasNextDouble()) {
					s3.next();
					s3.next();
					s3.next();
					if(s3.hasNext("\\d+\\.\\d+")) {
						dato3 = s3.next();
					}
				}
				else
					res3= true;
			}
		}
		s3.close();

		//BUQUEDA POR APELLIDO

		while(s4.hasNext() && res4){
			if(s4.next().contains(apellido2)){
				s4.next();
				s4.next();
				s4.next();
				if(s4.hasNext("\\d+\\.\\d+")) {
					dato4 = s4.next();
				}
			}
			else
				res4= true;
		}
		s4.close();
		if(!dato1.equals("")){
			d1=Double.parseDouble(dato1);
		}else{
			return"nombre del jugador no existe en esta categoría";	
		}
		if(!dato2.equals("")){
			d2=Double.parseDouble(dato2);
		}else{
			return"nombre del jugador no existe en esta categoría";	
		}
		if(!dato3.equals("")){
			d3=Double.parseDouble(dato3);
		}else{
			return"nombre del jugador no existe en esta categoría";	
		}
		if(!dato4.equals("")){
			d4=Double.parseDouble(dato4);
		}else{
			return"nombre del jugador no existe en esta categoría";	
		}
		////////////////////////COMPARACIONES///////////////////////////////

		//Comparacion para coherencia entre nombre y apellido

		if (Double.compare(d1 ,d2) == 0) {
			x = Double.toString(d1);
		}else{
			return "No hay coherencia entre nombre y apellido";
		}
		if (Double.compare(d3 ,d4) == 0) {
			y = Double.toString(d3);
		}else{
			return "No hay coherencia entre nombre y apellido";
		}
		//Sacar maximo
		max=sacarMaximo(x,y);
		maximo=Double.toString(max);
		//sacar nombre y apellido del jugador con el max
		if(max==d1){
			nombreFinal=nombre1;
			apellidoFinal=apellido1;
		}else{
		nombreFinal=nombre2;
		apellidoFinal=apellido2;
		}
		///////////////////////////////////////////////////////////////////////////
		//String resultado = x +" / "+ y;
		String resultado= "\n" +estad + " " + season + ":\n"
				+ nombre1 + " " + apellido1 + " " + x + "\n"
				+ nombre2 + " " + apellido2 + " " + y + "\n"
				+ "Jugador con mas " + estad + " en " + season + " es " 
				+ nombreFinal + " " + apellidoFinal + " con " + maximo + "\n";
		return resultado;
		//////////////////////////////////////////////////////////////////////////
	}


	public static double sacarMaximo(String x, String y) {

		Double max=0.0, X=0.0, Y=0.0;
		X = Double.parseDouble(x);
		Y = Double.parseDouble(y);

		max =  (X > Y) ?  X : Y;

		return max;
	}
	public static String estad(String stat){
		String res="";
		switch(stat){
			case "points": res= "puntos"; break;
			case "assists": res= "asistencias"; break;
			case "rebounds": res= "rebotes"; break;
			case "blocks": res= "tapones"; break;
			case "steals": res= "robos"; break;
			
		}
		return res;
	}
	public static String faseCampeonato(String season){
		String res="";
		switch(season){
			case "inseason": res= "temporada regular"; break;
			case "vw": res= "playoffs";	break;
		}
		return res;
	}
	public static void main(String[] args) throws IOException 
	{    
		System.out.println("Comparación: " + "\n" + "	-------Temporada regular-------");
		System.out.println(compararEstadisticasJugadores("Giannis-Antetokounmpo-James-Harden-points-inseason"));
		/*System.out.println("Asistencias: " +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "assists", "inseason"));
		System.out.println("Rebotes: " +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "rebounds","inseason"));
		System.out.println("Robos: " +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "steals","inseason"));
		System.out.println("Tapones: " +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "blocks","inseason"));
		System.out.println("	-----------Playoffs------------");
		System.out.println("Puntos: " +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "points","vw"));
		System.out.println("Asistencias: "  +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "assists","vw"));
		System.out.println("Rebotes: "+compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "rebounds","vw"));
		System.out.println("Robos: "  +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "steals","vw"));
		System.out.println("Tapones: " +compararEstadisticasJugadores("Giannis", "Antetokounmpo","Joel", "Embiid", "blocks","vw"));
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Me molaria sacar los valores por separado (para usarlos en sacarMaximo()) pero no se hacerlo");
		System.out.println("Maximo: " +sacarMaximo("5.5","8.5"));
		System.out.println("------------------------------------------------------------------------");
		System.out.println("En cuanto al Jade, he cambiado el string respuesta a 6 parametros. Da error aqui CyclicBehaviourBuscador.java:84 y aqui CyclicBehaviourBuscador.java:39." + "\n" + "En el cliente no he tocado nada. " + "\n" + "Solo quedaria hacer la interfaz y integrar en jade");
		 */}
}