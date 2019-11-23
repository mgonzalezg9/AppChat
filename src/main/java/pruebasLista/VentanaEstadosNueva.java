package pruebasLista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import umu.tds.apps.vistas.Estados;

import static umu.tds.apps.vistas.Theme.*;

public class VentanaEstadosNueva extends JFrame {

	private JPanel contentPane;
	private static FastPanelList panelList = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEstadosNueva frame = new VentanaEstadosNueva();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaEstadosNueva() {
		panelList = new FastPanelList(FastPanelList.FPLOrientation.VERTICAL,
                (Function<Integer, JPanel>)VentanaEstadosNueva::supplyPanel,
                0.1,
                0.95,
                0.1,
                false,
                150,
                30);
		
		final Container contentPane = panelList.container;
        contentPane.setPreferredSize(new Dimension(400, 800));
        contentPane.setBackground(Color.GRAY);
        
		setContentPane(contentPane);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Estados.class.getResource("/umu/tds/apps/resources/icon.png")));
		setTitle("Status");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// Cremos el panel elemento de la lista
    private static JPanel supplyPanel(final int panelIndex) {
    	
    	final JLabel label0 = new JLabel("");
    	label0.setIcon(new ImageIcon(FastPanelListDemo.class.getResource("/umu/tds/apps/resources/user.png")));
    	final JLabel label1 = new JLabel("Diego" + panelIndex + ": Estado " + panelIndex);
    	label1.setForeground(TEXT_COLOR_LIGHT);
        final JLabel label2 = new JLabel(" 05/06/07 ");
        label2.setForeground(TEXT_COLOR_LIGHT);
        
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setVerticalAlignment(SwingConstants.CENTER);


        final JPanel panel = new JPanel(new BorderLayout(0,
                                                         0));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        panel.setBackground(MAIN_COLOR_LIGHT);
        
        panel.add(label0, BorderLayout.WEST);
        panel.add(label1, BorderLayout.CENTER);
        panel.add(label2, BorderLayout.AFTER_LINE_ENDS);

        return panel;
    }

}
