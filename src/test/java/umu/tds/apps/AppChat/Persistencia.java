package umu.tds.apps.AppChat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite que contiene a todos los test que se encargan de probar la persistencia
 */
@RunWith(Suite.class)
@SuiteClasses({ TestPersistenciaMessage.class, TestPersistenciaUser.class, TestPersistenciaStatus.class,
		TestPersistenciaContactoIndividual.class, TestPersistenciaGroup.class })
public class Persistencia {
}
