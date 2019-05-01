package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


/**
 * Class responsible for launching the Vencimiento System.
 * 
 * @author acerNacho
 * @version 0.1b
 */
public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private final String[] COLUMN_NAMES = { "Insumo", "Fecha vto.", "Lote", "Estado" };

	
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPaneTabla;
	
	private final JMenuBar menu = new JMenuBar();
	private final JMenu menuOpciones = new JMenu("Opciones");
	private final JMenu menuAcercaDe = new JMenu("Acerca de");;
	private final JMenuItem menuOpcionesAgregar = new JMenuItem("Agregar");
	private final JMenuItem menuOpcionesQuitar = new JMenuItem("Quitar");
	private final Container cp = getContentPane();
	private JPanel panelDeContenido;
	private JTextField dia;
	private JTextField mes;
	private JTextField anio;
	private JTextField lote;
	private JTextField tipo;
	private JButton botonCargar;

	/**
	 * Initialise the JFrame containing the various JSwing components
	 * responsible of the Vencimiento System.
	 */
	public View() {
		
		// Tabla & Modelo de tabla
		tableModel = new DefaultTableModel(COLUMN_NAMES,0);
		table = new JTable(tableModel);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		showPanelTabla();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Vencimiento");
		setLocationRelativeTo(null);
		setSize(400, 200);
	}


	public void showPanelTabla() {
		cp.setLayout(new BorderLayout());

		
		
		// Menu de Opciones
		menuOpciones.add(menuOpcionesAgregar);
		menuOpciones.add(menuOpcionesQuitar);
		menu.add(menuOpciones);
		menu.add(menuAcercaDe);
		cp.add(menu, BorderLayout.NORTH);

		// ScrollPane
		scrollPaneTabla = new JScrollPane(table);
		
		Container contenedorPanelVencimiento = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 15));
		
		JPanel panelAgregarVencimiento = new JPanel();
		panelAgregarVencimiento.setLayout(new GridLayout(4, 2, 5, 9));
		
		
		// Panel Fecha
		JPanel panelFecha = new JPanel();
		panelAgregarVencimiento.add(new JLabel("Fecha:", SwingConstants.RIGHT));
		panelFecha.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 0));
		dia = new JTextField("DD", 3);
		panelFecha.add(dia);
		panelFecha.add(new JLabel("-"));
		mes = new JTextField("MM", 3);
		panelFecha.add(mes);
		panelFecha.add(new JLabel("-"));
		anio = new JTextField("YYYY", 3);
		panelFecha.add(anio);
		panelAgregarVencimiento.add(panelFecha);
		
		// TextField Lote
		panelAgregarVencimiento.add(new JLabel("Lote:", SwingConstants.RIGHT));
		lote = new JTextField(8);
		panelAgregarVencimiento.add(lote);
		
		// TextField Tipo
		panelAgregarVencimiento.add(new JLabel("Tipo:", SwingConstants.RIGHT));
		tipo = new JTextField(10);
		panelAgregarVencimiento.add(tipo);

		// Panel vacio
		panelAgregarVencimiento.add(new JPanel());
		
                // Panel de contenido
                panelDeContenido = new JPanel(new CardLayout());
                
		// Boton cargar datos
		botonCargar = new JButton("Cargar datos");
		panelAgregarVencimiento.add(botonCargar);
		contenedorPanelVencimiento.add(panelAgregarVencimiento);
		panelDeContenido.add(scrollPaneTabla,"scrollPane");
		panelDeContenido.add(contenedorPanelVencimiento,"panelVencimiento");
		cp.add(panelDeContenido, BorderLayout.CENTER);
	}
	
	public void changePanel(String panel) {
                if (menu.isVisible() && panel.equals("panelVencimiento")) {
                    menu.setVisible(false);
                    
                } else {
                    menu.setVisible(true);
                }
		CardLayout cl = (CardLayout)(panelDeContenido.getLayout());
		cl.show(panelDeContenido, panel);
	}

		
		
	

	
	
	/*
	 *  Agregar Listeners
	 */
	
	public void agregarListenersTextoFecha(FocusListener action) {
		dia.addFocusListener(action);
		mes.addFocusListener(action);
		anio.addFocusListener(action);
	}

	public void agregarListenersPanelAgregarVencimiento(ActionListener action) {
		botonCargar.addActionListener(action);
	}

	public void agregarListeners(ActionListener action) {
		menuAcercaDe.addActionListener(action);
		menuOpcionesAgregar.addActionListener(action);
		menuOpcionesQuitar.addActionListener(action);
	}

	/*
	 * Getters & Setters
	 * 
	 * */

	public String getTipo() {
		return tipo.getText();
	}

	public JTable getTable() {
		return table;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	
	public LocalDate getFecha() {
		try {
			int fecha[] = new int[3];

			fecha[0] = Integer.parseInt(dia.getText());
			fecha[1] = Integer.parseInt(mes.getText());
			fecha[2] = Integer.parseInt(anio.getText());
			return LocalDate.of(fecha[2], fecha[1], fecha[0]);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Formato de fecha invalido", "Error",
					JOptionPane.WARNING_MESSAGE);
			System.err.println("Formato de fecha invalido");
			// e.printStackTrace();
		} catch (DateTimeException e) {
			JOptionPane.showMessageDialog(this, "Fecha no valida", "Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("Fecha no valida");
		} catch (Exception e) {
			// TODO agregar error correspondiente a la fecha
			// invalida
			e.printStackTrace();
		}
		return null;
	}
	
	public String getLote() {
		return lote.getText();
	}
	

}
