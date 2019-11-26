package umu.tds.apps.AppChat;

public class Premium implements UserRol {
	private Discount descuento;
	
	public Premium(Discount descuento) {
		this.descuento = descuento;
	}
	
	public double realizarPago() {
		return descuento.calcDescuento();
	}
}
