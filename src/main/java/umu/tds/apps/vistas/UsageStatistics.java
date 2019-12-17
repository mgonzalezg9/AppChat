package umu.tds.apps.vistas;

import static umu.tds.apps.vistas.Theme.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;

import umu.tds.apps.controlador.Controlador;

import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;

class GraficaUso extends JFrame {
	private JPanel contentPane;

	public GraficaUso() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(UsageStatistics.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Graphics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 213);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
	}

}

public class UsageStatistics extends JFrame {
	private static final String HISTOGRAMA = "Messages sent monthly";
	private static final String TARTA = "Most frequented groups";

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsageStatistics frame = new UsageStatistics();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UsageStatistics() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(UsageStatistics.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Statistics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 343, 186);
		contentPane = new JPanel();
		contentPane.setBackground(MAIN_COLOR_LIGHT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);

		JLabel lblGraphicType = new JLabel("Graphic type");
		lblGraphicType.setForeground(TEXT_COLOR_LIGHT);
		panel.add(lblGraphicType);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { HISTOGRAMA, TARTA }));
		comboBox.setPreferredSize(new Dimension(100, 20));
		comboBox.setMinimumSize(new Dimension(78, 20));
		panel.add(comboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(MAIN_COLOR_LIGHT);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);

		JButton btnView = new JButton("VIEW");
		btnView.setBackground(SECONDARY_COLOR);
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Creacion del grafico
				Chart chart;
				if (comboBox.getSelectedItem().equals(HISTOGRAMA)) {
					chart = Controlador.getInstancia().crearHistograma((String) comboBox.getSelectedItem());
				} else {
					chart = Controlador.getInstancia().crearTarta((String) comboBox.getSelectedItem());
				}

				// Nueva ventana con el grafico
				setVisible(false);
				JPanel panel = new XChartPanel<>(chart);
				JFrame frame = new JFrame((String) comboBox.getSelectedItem());
				frame.setIconImage(getIconImage());
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(panel);
				frame.pack();
				frame.setVisible(true);

			}
		});
		panel_1.add(btnView);

		JButton btnExport = new JButton("EXPORT");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Se pregunta donde guardar la grafica
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Choose a folder to save the chart");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setAcceptAllFileFilterUsed(false);

				// Guardado
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					// Creacion del grafico
					Chart chart;
					if (comboBox.getSelectedItem().equals(HISTOGRAMA)) {
						chart = Controlador.getInstancia().crearHistograma((String) comboBox.getSelectedItem());
					} else {
						chart = Controlador.getInstancia().crearTarta((String) comboBox.getSelectedItem());
					}

					try {
						System.out.println(
								jfc.getSelectedFile().getAbsolutePath() + "\\" + comboBox.getSelectedItem() + ".png");
						BitmapEncoder.saveBitmap(chart,
								jfc.getSelectedFile().getAbsolutePath() + "\\" + comboBox.getSelectedItem() + ".png",
								BitmapFormat.PNG);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnExport.setBackground(SECONDARY_COLOR);
		panel_1.add(btnExport);
	}

}
