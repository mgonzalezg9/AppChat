package umu.tds.apps.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public GroupDAO getGrupoDAO() {
		return AdaptadorGroupTDS.getUnicaInstancia();
	}

	@Override
	public IndividualContactDAO getContactoIndividualDAO() {
		return AdaptadorIndividualContactTDS.getUnicaInstancia();
	}

	@Override
	public MessageDAO getMensajeDAO() {
		return AdaptadorMessageTDS.getUnicaInstancia();
	}

	@Override
	public StatusDAO getEstadoDAO() {
		return AdaptadorStatusTDS.getUnicaInstancia();
	}

	@Override
	public UserDAO getUserDAO() {
		return AdaptadorUserTDS.getUnicaInstancia();
	}

}
