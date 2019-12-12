package umu.tds.apps.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public GroupDAO getGrupoDAO() {
		return AdaptadorGroupTDS.getInstancia();
	}

	@Override
	public IndividualContactDAO getContactoIndividualDAO() {
		return AdaptadorIndividualContactTDS.getInstancia();
	}

	@Override
	public MessageDAO getMensajeDAO() {
		return AdaptadorMessageTDS.getInstancia();
	}

	@Override
	public StatusDAO getEstadoDAO() {
		return AdaptadorStatusTDS.getInstancia();
	}

	@Override
	public UserDAO getUserDAO() {
		return AdaptadorUserTDS.getInstancia();
	}

}
