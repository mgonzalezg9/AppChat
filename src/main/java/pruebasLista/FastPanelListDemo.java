package pruebasLista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

final public class FastPanelListDemo {


    private static JFrame window = null;
    private static FastPanelList panelList = null;


    public static void main(final String[] args) {
        panelList = new FastPanelList(FastPanelList.FPLOrientation.VERTICAL,
                                      (Function<Integer, JPanel>)FastPanelListDemo::supplyPanel,
                                      0.1,
                                      0.95,
                                      0.1,
                                      false,
                                      150,
                                      30);
        
        final Container contentPane = panelList.container;
        contentPane.setPreferredSize(new Dimension(400, 800));
        contentPane.setBackground(Color.GRAY);

        // Creo la ventana y la hago visible
        window = new JFrame("List demo");
        window.setContentPane(contentPane);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


    // Cremos el panel elemento de la lista
    private static JPanel supplyPanel(final int panelIndex) {
    	
    	final JLabel label0 = new JLabel("");
    	label0.setIcon(new ImageIcon(FastPanelListDemo.class.getResource("/umu/tds/apps/resources/user.png")));
    	final JLabel label1 = new JLabel("Diego" + panelIndex + ": Estado " + panelIndex);
        final JLabel label2 = new JLabel(" 05/06/07 ");
        
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setVerticalAlignment(SwingConstants.CENTER);


        final JPanel panel = new JPanel(new BorderLayout(0,
                                                         0));
        panel.setBorder(BorderFactory.createEmptyBorder(10,
                                                        10,
                                                        10,
                                                        10));
        panel.setBackground(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
        
        panel.add(label0, BorderLayout.WEST);
        panel.add(label1, BorderLayout.CENTER);
        panel.add(label2, BorderLayout.AFTER_LINE_ENDS);
        //panel.add(button, BorderLayout.EAST);

        return panel;
    }
}