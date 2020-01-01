package umu.tds.apps.AppChat;

public class SeniorDiscount implements Discount {
	@Override
	public double getDescuento(double precioInicial) {
		return 0.9 * precioInicial;
	}
}
