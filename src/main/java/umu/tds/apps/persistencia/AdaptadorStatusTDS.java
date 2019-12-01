package umu.tds.apps.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.AppChat.Status;

public class AdaptadorStatusTDS implements StatusDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorStatusTDS unicaInstancia = null;

	private AdaptadorStatusTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public static AdaptadorStatusTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorStatusTDS();
		return unicaInstancia;
	}

	@Override
	public void registrarEstado(Status status) {
		Entidad eStatus;
		boolean existe = true;

		// Si la entidad está registrada no la registra de nuevo
		try {
			eStatus = servPersistencia.recuperarEntidad(status.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// Atributos propios del contacto
		eStatus = new Entidad();
		eStatus.setNombre("estado");
		eStatus.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("mensaje", status.getFrase()),
				new Propiedad("imagen", status.getImg().getDescription()))));

		// Registrar entidad usuario
		servPersistencia.registrarEntidad(eStatus);

		// Identificador unico
		status.setCodigo(eStatus.getId());
	}

	@Override
	public void borrarEstado(Status status) {
		Entidad eStatus = servPersistencia.recuperarEntidad(status.getCodigo());
		servPersistencia.borrarEntidad(eStatus);
	}

	@Override
	public void modificarEstado(Status status) {
		Entidad eStatus = servPersistencia.recuperarEntidad(status.getCodigo());

		// Se da el cambiazo a las propiedades del usuario
		servPersistencia.eliminarPropiedadEntidad(eStatus, "mensaje");
		servPersistencia.anadirPropiedadEntidad(eStatus, "mensaje", status.getFrase());
		servPersistencia.eliminarPropiedadEntidad(eStatus, "imagen");
		servPersistencia.anadirPropiedadEntidad(eStatus, "imagen", status.getImg().getDescription());
	}

	@Override
	public Status recuperarEstado(int codigo) {
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstancia().contiene(codigo))
			return (Status) PoolDAO.getInstancia().getObjeto(codigo);

		// Sino, la recupera de la base de datos
		// Recuperamos la entidad
		Entidad eStatus = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		String mensaje = null;
		mensaje = servPersistencia.recuperarPropiedadEntidad(eStatus, "mensaje");

		ImageIcon img = null;
		img = new ImageIcon(servPersistencia.recuperarPropiedadEntidad(eStatus, "imagen"));

		Status status = new Status(img, mensaje);
		status.setCodigo(codigo);

		// Metemos al estado en el pool
		PoolDAO.getInstancia().addObjeto(codigo, status);

		// Devolvemos el objeto estado
		return status;
	}

	@Override
	public List<Status> recuperarTodosEstados() {
		List<Status> estados = new LinkedList<>();
		List<Entidad> eStatus = servPersistencia.recuperarEntidades("estado");

		for (Entidad status : eStatus) {
			estados.add(recuperarEstado(status.getId()));
		}
		
		return estados;
	}

}
