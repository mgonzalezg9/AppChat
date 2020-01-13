package umu.tds.apps.AppChat;

public interface Discount {
	/**
	 * Devuelve el precio tras incluirle el descuento al precio inicial
	 * 
	 * @param precioInicial Precio sin decuento
	 * @return Precio tras aplicarle el descuento
	 */
	public double getDescuento(double precioInicial);
}
