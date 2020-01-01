package umu.tds.apps.AppChat;

public class JuniorDiscount implements Discount {
	@Override
	public double getDescuento(double precioInicial) {
		return 0.6 * precioInicial;
	}
}
