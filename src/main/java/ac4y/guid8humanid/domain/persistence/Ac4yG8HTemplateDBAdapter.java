package ac4y.guid8humanid.domain.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import ac4y.base.Ac4yException;
import ac4y.guid8humanid.domain.object.Ac4yG8H;

public class Ac4yG8HTemplateDBAdapter {

	public Ac4yG8HTemplateDBAdapter(Connection aConnection){
		
		setConnection(aConnection); 
	}

	private static String TEMPLATE = "template";
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private Connection connection;

	public String getGUID(String aHumanId) throws SQLException, Ac4yException{

		return new Ac4yG8HDBAdapter(getConnection()).getGUID(TEMPLATE, aHumanId);
				
	} // getGUID
	
	public Ac4yG8H setByHumanId(String aHumanId) throws SQLException, Ac4yException{

		return new Ac4yG8HDBAdapter(getConnection()).setByHumanId(TEMPLATE, aHumanId);
				
	} // setByHumanId

	public Ac4yG8H getByGUID(String aGUID) throws SQLException, Ac4yException{

		//return new Ac4yG8HDBAdapter(getConnection()).getByHumanId(TEMPLATE, aGUID);
		return new Ac4yG8HDBAdapter(getConnection()).getByGUID(aGUID);
				
	} // getByGUID

	
	public boolean existByHumanId(String aHumanId) throws SQLException, Ac4yException{

		return new Ac4yG8HDBAdapter(getConnection()).isExistByHumanId(TEMPLATE, aHumanId);
				
	} // existByHumanId
	
	public Ac4yG8H setByGUIDAndHumanId(String aGUID, String aHumanId) throws SQLException, Ac4yException{
		
		return new Ac4yG8HDBAdapter(getConnection()).setByGUIDAndHumanId(aGUID, TEMPLATE, aHumanId);
		
	}
	
} // Ac4yG8HTemplate