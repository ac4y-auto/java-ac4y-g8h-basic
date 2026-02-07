package ac4y.guid8humanid.domain.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import ac4y.base.Ac4yException;
import ac4y.base.database.Ac4yDBAdapter;
import ac4y.base.domain.Ac4yClass;
import ac4y.base.domain.Ac4yIdentification;

public class Ac4yIdentificationDBAdapter extends Ac4yDBAdapter {

	public Ac4yIdentificationDBAdapter(Connection aConnection){
		super(aConnection); 
	}
	
	public Ac4yIdentification get(ResultSet aResultSet) throws SQLException {

		return 
			new Ac4yIdentification(
				aResultSet.getInt("persistentId")
				,aResultSet.getString("GUID")
				,aResultSet.getString("humanId")
				,aResultSet.getString("publicHumanId")
				,aResultSet.getInt("deleted")==1
				,new Ac4yClass (
						aResultSet.getInt("templatePersistentId")
						,aResultSet.getString("templateGUID")
						,aResultSet.getString("templateHumanId")
						,aResultSet.getString("templatePublicHumanId")
				)
			);
					
	} // get

	public Ac4yIdentification getByPersistentId(int aPersistentId) throws SQLException{
		
		Ac4yIdentification result = null;
		
		String 		sqlStatement 	= "SELECT * FROM vAc4yObject WHERE persistentId = " + aPersistentId;

		Statement 	statement 		= getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		if (resultSet.next())
			result = get(resultSet);
		
		statement.close();

		return result;
				
	} // getByPersistentId
	
	public Ac4yIdentification getByGUID(String aGUID) throws SQLException, Ac4yException {

		if (aGUID == null || aGUID == "")
			throw new Ac4yException("GUID is empty!");
		
		Ac4yIdentification result = null;
		
		String sqlStatement = 
					"SELECT * FROM vAc4yObject WHERE"
					+ " GUID = '" + aGUID + "'"
					;		

		Statement 	statement 		= getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		if (resultSet.next())
			result = get(resultSet);
		
		statement.close();

		return result;
				
	} // getByGUID
	
} // Ac4yIdentificationDBAdapter