package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controlador;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class JFrameBuscador extends JFrame {
	
	private Controlador controller;
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JTextField textFieldnombre1;
	private JTextField textFieldnombre2;
	private JTextField textFieldStat;
	private JTextField textFieldapellido1;
	private JTextField textFieldapellido2;
	private JTextField textFieldROP;

	/**
	 * Create the frame.
	 */
	public JFrameBuscador(Controlador controller) {
		
		this.controller = controller;

		setTitle("Buscador_DVP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400,125,640,480);
		panelPrincipal = new JPanel();
		panelPrincipal.setName("");
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		createFields ();
	}
	
	public void createFields() {

		JLabel lblNewLabel = new JLabel("DVP Searcher Comparator");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(180, 55, 269, 30);
		panelPrincipal.add(lblNewLabel);

		textFieldnombre1 = new JTextField();
		textFieldnombre1.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldnombre1.setBounds(150, 105, 147, 35);
		panelPrincipal.add(textFieldnombre1);
		textFieldnombre1.setColumns(10);
	
		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.cogerParametros();
				//System.exit(0);
			}
		});
		btnBusqueda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBusqueda.setBounds(180, 239, 130, 60);
		panelPrincipal.add(btnBusqueda);

		JButton btnCancelarLimpiarBusqueda = new JButton("Cancelar");
		btnCancelarLimpiarBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		btnCancelarLimpiarBusqueda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelarLimpiarBusqueda.setBounds(319, 239, 130, 60);
		panelPrincipal.add(btnCancelarLimpiarBusqueda);
		
		JLabel lblDvpSearcher = new JLabel("DVP Searcher \u00A9 2019 All Rights Reserved");
		lblDvpSearcher.setHorizontalAlignment(SwingConstants.CENTER);
		lblDvpSearcher.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblDvpSearcher.setBounds(10, 390, 604, 40);
		panelPrincipal.add(lblDvpSearcher);
		
		JLabel lblFirst = new JLabel("Nombre 1:");
		lblFirst.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirst.setBounds(80, 105, 60, 35);
		panelPrincipal.add(lblFirst);
		
		JLabel lblSegundo = new JLabel("Nombre 2:");
		lblSegundo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegundo.setBounds(80, 152, 60, 35);
		panelPrincipal.add(lblSegundo);
		
		textFieldnombre2 = new JTextField();
		textFieldnombre2.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldnombre2.setColumns(10);
		textFieldnombre2.setBounds(150, 151, 147, 35);
		panelPrincipal.add(textFieldnombre2);
		
		JLabel lblTercero = new JLabel("Stat :");
		lblTercero.setHorizontalAlignment(SwingConstants.CENTER);
		lblTercero.setBounds(80, 198, 60, 35);
		panelPrincipal.add(lblTercero);
		
		textFieldStat = new JTextField();
		textFieldStat.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldStat.setColumns(10);
		textFieldStat.setBounds(150, 193, 147, 35);
		panelPrincipal.add(textFieldStat);
		
		JLabel lblCuarto = new JLabel("Apellido 1:");
		lblCuarto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCuarto.setBounds(307, 105, 60, 35);
		panelPrincipal.add(lblCuarto);
		
		textFieldapellido1 = new JTextField();
		textFieldapellido1.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldapellido1.setColumns(10);
		textFieldapellido1.setBounds(367, 105, 147, 35);
		panelPrincipal.add(textFieldapellido1);
		
		JLabel lblApellid2 = new JLabel("Apellido 2:");
		lblApellid2.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellid2.setBounds(307, 152, 60, 35);
		panelPrincipal.add(lblApellid2);
		
		textFieldapellido2 = new JTextField();
		textFieldapellido2.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldapellido2.setColumns(10);
		textFieldapellido2.setBounds(367, 152, 147, 35);
		panelPrincipal.add(textFieldapellido2);
		
		JLabel lblSexto = new JLabel("ROP :");
		lblSexto.setHorizontalAlignment(SwingConstants.CENTER);
		lblSexto.setBounds(307, 193, 60, 35);
		panelPrincipal.add(lblSexto);
		
		textFieldROP = new JTextField();
		textFieldROP.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldROP.setColumns(10);
		textFieldROP.setBounds(367, 193, 147, 35);
		panelPrincipal.add(textFieldROP);
		
		JButton btnMostrarBusqueda = new JButton("Mostrar Busqueda");
		btnMostrarBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/*ACLMessage msg = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));		
				//Cuando el agente AgenteBuscador responde, imprimimos su respuesta por pantalla
				try
				{
					List<String> mensajes=(List<String>)msg.getContentObject();
					for(int i=0;i<mensajes.size();i++)
						showTicket(mensajes.get(i));
				}
				catch (UnreadableException e)
				{
					e.printStackTrace();
				}
			*/
				showTicket("Esta ha sido tu busqueda :D");
			}

			
		});
		btnMostrarBusqueda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMostrarBusqueda.setBounds(180, 319, 269, 60);
		panelPrincipal.add(btnMostrarBusqueda);

	}
	
	public JTextField getTextFieldnombre1() {
		return textFieldnombre1;
	}

	public void setTextFieldnombre1(JTextField textFieldnombre1) {
		this.textFieldnombre1 = textFieldnombre1;
	}

	public JTextField getTextFieldnombre2() {
		return textFieldnombre2;
	}

	public void setTextFieldnombre2(JTextField textFieldnombre2) {
		this.textFieldnombre2 = textFieldnombre2;
	}

	public JTextField getTextFieldStat() {
		return textFieldStat;
	}

	public void setTextFieldStat(JTextField textFieldStat) {
		this.textFieldStat = textFieldStat;
	}

	public JTextField getTextFieldapellido1() {
		return textFieldapellido1;
	}

	public void setTextFieldapellido1(JTextField textFieldapellido1) {
		this.textFieldapellido1 = textFieldapellido1;
	}

	public JTextField getTextFieldapellido2() {
		return textFieldapellido2;
	}

	public void setTextFieldapellido2(JTextField textFieldapellido2) {
		this.textFieldapellido2 = textFieldapellido2;
	}

	public JTextField getTextFieldROP() {
		return textFieldROP;
	}

	public void setTextFieldROP(JTextField textFieldROP) {
		this.textFieldROP = textFieldROP;
	}

	public void clearFields() {
		textFieldnombre1.setText(null);
		textFieldapellido1.setText(null);
		textFieldnombre2.setText(null);
		textFieldapellido2.setText(null);
		textFieldStat.setText(null);
		textFieldROP.setText(null);
	}
	public void showTicketError(String ticket) {
		JOptionPane.showMessageDialog(this, ticket, "Resultado Busqueda Error", JOptionPane.PLAIN_MESSAGE);
	}
	public void showTicket(String ticket) {
		JOptionPane.showMessageDialog(this, ticket, "Resultado Busqueda", JOptionPane.PLAIN_MESSAGE);
	}
}
