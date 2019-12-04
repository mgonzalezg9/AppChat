package umu.tds.apps.AppChat;

public class Premium extends UserRol {
	private Discount descuento;
	
	public Premium(Discount descuento) {
		this.descuento = descuento;
	}
	
	public double realizarPago() {
		return descuento.getDescuento();
	}
	
	@Override
	public String toString() {
		return descuento.toString();
	}
}
