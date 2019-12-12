package umu.tds.apps.vistas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Theme {
	public static Color MAIN_COLOR = new Color(141, 110, 99);
	public static Color MAIN_COLOR_LIGHT = new Color(190, 156, 145);
	public static Color SECONDARY_COLOR = new Color(255, 171, 0);
	public static Color CHAT_COLOR = new Color(245, 222, 179);
	public static Color TEXT_COLOR_DARK = new Color(0, 0, 0);
	public static Color TEXT_COLOR_LIGHT = new Color(255, 255, 255);
	public static Color SENT_MESSAGE_COLOR = Color.GREEN;
	public static Color INCOMING_MESSAGE_COLOR = Color.LIGHT_GRAY;
	public static Color WRONG_INPUT_COLOR = Color.RED;
	public static int MESSAGE_SIZE = 12;
	public static String GROUP_ICON_PATH = "/umu/tds/apps/resources/group.png";
	public static String GROUP_MINI_ICON_PATH = "/umu/tds/apps/resources/group20.png";
	
	public static ImageIcon resizeIcon (ImageIcon img, int resolution) {
		BufferedImage bi = new BufferedImage(img.getIconWidth(), img.getIconHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = bi.createGraphics();
		// paint the Icon to the BufferedImage.
		img.paintIcon(null, g, 0, 0);
		g.dispose();

		Image imgScaled = bi.getScaledInstance(resolution, resolution, Image.SCALE_DEFAULT);
		ImageIcon icon = new ImageIcon(imgScaled);

		return icon;
	}
	
	public static void applyQualityRenderingHints(Graphics2D g2d) {

	    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

	}
}
