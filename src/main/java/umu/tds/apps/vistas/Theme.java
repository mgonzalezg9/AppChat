package umu.tds.apps.vistas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Theme {
	private static final String PHOTOS_RELATIVE_PATH = "src/main/java/umu/tds/apps/photos/";
	private static final String PHOTOS_PACKAGE_PATH = "/umu/tds/apps/photos/";

	public static final Color MAIN_COLOR = new Color(141, 110, 99);
	public static final Color MAIN_COLOR_LIGHT = new Color(190, 156, 145);
	public static final Color SECONDARY_COLOR = new Color(255, 171, 0);
	public static final Color CHAT_COLOR = new Color(245, 222, 179);
	public static final Color TEXT_COLOR_DARK = new Color(0, 0, 0);
	public static final Color TEXT_COLOR_LIGHT = new Color(255, 255, 255);
	public static final Color SENT_MESSAGE_COLOR = Color.GREEN;
	public static final Color INCOMING_MESSAGE_COLOR = Color.LIGHT_GRAY;
	public static final Color WRONG_INPUT_COLOR = Color.RED;
	public static final int MESSAGE_SIZE = 12;
	public static final String GROUP_ICON_PATH = "/umu/tds/apps/resources/group.png";
	public static final int ICON_SIZE_MINI = 35;
	public static final int ICON_SIZE = 50;
	public static final int STATUS_IMAGE_SIZE = 250;
	public static final String PROFILE_PHOTO_NAME = "profilePhoto";
	public static final String STATUS_NAME = "status";
	public static final Color[] PIECHART_COLORS = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62),
			new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
	private static final int MAX_CHARS_STRING_CHAT_LIST = 35;

	public static ImageIcon resizeIcon(ImageIcon img, int newSize) {
		BufferedImage bi = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = bi.createGraphics();

		// paint the Icon to the BufferedImage.
		img.paintIcon(null, g, 0, 0);
		g.dispose();

		return resizeIcon(bi, newSize);
	}

	public static ImageIcon resizeIcon(BufferedImage img, int newSize) {
		return new ImageIcon(img.getScaledInstance(newSize, newSize, Image.SCALE_DEFAULT));
	}

	public static void saveImage(ImageIcon img, String base, int id, int pos) {
		BufferedImage image;
		try {
			image = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = image.createGraphics();
			g2.drawImage(img.getImage(), 0, 0, null);
			g2.dispose();

			String ext = img.getDescription().substring(img.getDescription().lastIndexOf("."));
			String name = base + id + "_" + pos + ext;
			File file = new File(PHOTOS_RELATIVE_PATH + name);
			img.setDescription(PHOTOS_PACKAGE_PATH + name);
			ImageIO.write(image, ext.substring(1), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage roundImage(BufferedImage bufImage) {
		int diameter = Math.min(bufImage.getWidth(), bufImage.getHeight());
		BufferedImage mask = new BufferedImage(bufImage.getWidth(), bufImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mask.createGraphics();
		applyQualityRenderingHints(g2d);
		g2d.fillOval(0, 0, diameter - 1, diameter - 1);
		g2d.dispose();

		BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
		g2d = masked.createGraphics();
		applyQualityRenderingHints(g2d);
		int x = (diameter - bufImage.getWidth()) / 2;
		int y = (diameter - bufImage.getHeight()) / 2;
		g2d.drawImage(bufImage, x, y, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		g2d.drawImage(mask, 0, 0, null);
		g2d.dispose();

		return masked;
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

	/**
	 * En caso de que sea mayor que un cierto número de caracteres añade recorta la
	 * cadena y le añade puntos suspensivos al final para que no sea tan larga
	 * 
	 * @param cadena Cadena a recortar en caso de exceder el tamaño permitido
	 * @return Cadena recortada si excede el tamaño
	 */
	public static String recortarString(String cadena) {
		if (cadena.length() > MAX_CHARS_STRING_CHAT_LIST) {
			return cadena.substring(0, Math.min(MAX_CHARS_STRING_CHAT_LIST, cadena.lastIndexOf(' '))) + "...";
		}
		return cadena;
	}
}
