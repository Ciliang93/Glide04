package be.iesca.colcinetFlights.daoimpl;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import be.iesca.colcinetFlights.dao.Dao;

public class DaoFactory {
	private static final String CONFIG_FILE = "config.xml";
	private static final DaoFactory INSTANCE = new DaoFactory();

	private Persistance persistance;
	private BoneCP connectionPool = null;
	
	public static DaoFactory getInstance() {
		return INSTANCE;
	}
	
	private DaoFactory() {
		try {
			this.persistance = ParserConfig.readConfig(CONFIG_FILE);
			this.persistance.config();
			if (this.persistance.getType().equals(Persistance.DB)) {
				this.connectionPool = createConnectionPool();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private BoneCP createConnectionPool() {
		BoneCP connectionPool = null;
		try {
			/*
			 * Création d'une configuration de pool de connexions via l'objet
			 * BoneCPConfig et les différents setters associés.
			 */
			BoneCPConfig config = new BoneCPConfig();
			/* Mise en place de l'URL, du nom et du mot de passe */
			config.setJdbcUrl(persistance.getUrl());
			config.setUsername(persistance.getUser());
			config.setPassword(persistance.getPassword());
			/* Paramétrage de la taille du pool */
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(2);
			/* Création du pool à partir de la configuration, via l'objet BoneCP */
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connectionPool;
	}

	// renvoie une connexion
	public Connection getConnexion() {
		Connection connection = null;
		try {
			connection = this.connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	// renvoie l'instance du dao dont on spécifie l'interface
	public Dao getDaoImpl(Class<? extends Dao> interfaceDao) {
		return this.persistance.getDaoImpl(interfaceDao);
	}
}
