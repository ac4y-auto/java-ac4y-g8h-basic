package ac4y.guid8humanid.domain.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ac4y.base.Ac4yException;
import ac4y.base.Ac4yProcess;
import ac4y.base.Ac4yProcessContext;
import ac4y.base.database.Ac4yDBAdapter;
import ac4y.guid8humanid.domain.object.Ac4yG8H;
import ac4y.guid8humanid.domain.object.Ac4yG8HList;
import ac4y.guid8humanid.domain.object.Ac4yGUIDList;
import ac4y.utility.GUIDHandler;
import ac4y.utility.StringHandler;

public class Ac4yG8HDBAdapter extends Ac4yDBAdapter {

	public Ac4yG8HDBAdapter() {
		
	}
	
	public Ac4yG8HDBAdapter(Connection aConnection){
		super(aConnection); 
	}

	public String getPublicHumanId(String aHumanId) {
		return new StringHandler().getLastPart(aHumanId, "\\.");
	}
	
	public Ac4yG8H insert(String aGUID, String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		String vGUID = aGUID;
		
		if 	(vGUID == null)
			vGUID = new GUIDHandler().getGUID();

		String vHumanId = aHumanId;

		if 	(vHumanId == null)
			vHumanId = vGUID;
		
		if (aTemplateGUID == null || aTemplateGUID == "")
			throw new Ac4yException("templateGUID is empty!");
		
		if (vHumanId == null || vHumanId == "")
			throw new Ac4yException("humanId is empty!");
		
		if (vGUID == null || vGUID == "")
			throw new Ac4yException("GUID is empty!");
		
		String vSQLStatement = "INSERT INTO Ac4yG8H"
				+ " (GUID, humanId, publicHumanId, simpledHumanId, templateGUID) VALUES"
				+ " (?,?,?,?,?)";
		
		String 	vSimpledHumanId = new StringHandler().getSimpled(vHumanId);
		String	vPublicHumanIdPart = new StringHandler().getLastPart(vHumanId, "\\.");
			
		PreparedStatement vPreparedStatement = 
			getConnection().prepareStatement(
				vSQLStatement
				,Statement.RETURN_GENERATED_KEYS
			);
		
		vPreparedStatement.setString(1, vGUID);
		vPreparedStatement.setString(2, vHumanId);
		vPreparedStatement.setString(3, vPublicHumanIdPart);
		vPreparedStatement.setString(4, vSimpledHumanId);
		vPreparedStatement.setString(5, aTemplateGUID);
		 
		if (vPreparedStatement.executeUpdate() == 0)
			throw new Ac4yException("insert failed!");

		ResultSet resultSet = vPreparedStatement.getGeneratedKeys();
		
		Ac4yG8H result = null;
			
		if (resultSet.next()) {
			
			result = 
				new Ac4yG8H(
					resultSet.getInt(1)
					,vGUID
					,vHumanId
					,vPublicHumanIdPart
					,vSimpledHumanId
					,aTemplateGUID
					,-2
				);
		}

		vPreparedStatement.close();
		
		return result;
		
	} // insert

	public Ac4yG8H insert(String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException {

		return insert(null, aTemplateGUID, aHumanId);
		
	} // insert
	
	public Ac4yG8H insert(String aTemplateGUID) throws SQLException, Ac4yException{

		return insert(null, aTemplateGUID, null);
		
	} // insert

	public Ac4yG8H deleteByPersistentId(int aPersistentId) throws SQLException, Ac4yException{

		Ac4yG8H result = null;
/*		
		String sqlStatement = 
				"UPDATE Ac4yG8H "
				+ " SET deleted = 1"
				+ " WHERE "
				+ " persistentId = ? "
				;
*/
		String sqlStatement = 
				"DELETE FROM Ac4yG8H "
				+ " WHERE "
				+ " persistentId = " + aPersistentId
				;

		;

		//System.out.println("sqlStatement:"+sqlStatement);
/*		
		PreparedStatement preparedStatement = 
			getConnection().prepareStatement(
				sqlStatement
				,Statement.RETURN_GENERATED_KEYS
			);
		
		preparedStatement.setInt(1, aPersistentId);
		
		if (preparedStatement.executeUpdate() == 0) 
			throw new Ac4yException("logical delete failed!");
		
		else {
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			result = get(resultSet);
			
		}
		
		preparedStatement.close();
*/
		
		Statement statement = getConnection().createStatement();

		statement.executeUpdate(sqlStatement);		
		
		return result;
		
	} // deleteByPersistentId
	
	public Ac4yG8H deleteByGUID(String aGUID) throws SQLException, Ac4yException {

		Ac4yG8H result = null;
	/*	
		String sqlStatement = 
				"UPDATE Ac4yG8H "
				+ " SET deleted = 1"
				+ " WHERE "
				+ " GUID = ? "
				;
*/
		String sqlStatement = 
				"DELETE FROM Ac4yG8H "
				+ " WHERE "
				+ " GUID = ? "
				;
		
		
		PreparedStatement preparedStatement = getConnection().prepareStatement(sqlStatement);
		
		preparedStatement.setString(1, aGUID);
		
		if (preparedStatement.executeUpdate() == 0)
			throw new Ac4yException("logical delete failed!");
		else
			result = getByGUID(aGUID);
		
		preparedStatement.close();
		
		return result;
		
	} // deleteByGUID
	
	public Ac4yG8H setByGUIDAndHumanId(String aGUID, String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		if (!isExistByHumanId(aTemplateGUID, aHumanId))
			return insert(aGUID, aTemplateGUID, aHumanId);
		else {
			
			if (aGUID == null)
				return getByGUID(getGUID(aTemplateGUID, aHumanId)); 
			else
				return getByGUID(aGUID);
		}
				
	} // setByGUIDAndHumanId

	public Ac4yG8H setByHumanId(String aGUID, String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		if (!isExistByHumanId(aTemplateGUID, aHumanId))
			return insert(aGUID, aTemplateGUID, aHumanId);
		else {
			
			if (aGUID == null)
				return getByGUID(getGUID(aTemplateGUID, aHumanId)); 
			else
				return getByGUID(aGUID);
		}		
		
	} // setByHumanId
	
	public Ac4yG8H setByHumanId(String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		return setByGUIDAndHumanId(null, aTemplateGUID, aHumanId);
		
	} // setByHumanId
	
	public Ac4yG8H setByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, Ac4yException{

		new Ac4yG8HTemplateDBAdapter(getConnection()).setByHumanId(aTemplateHumanId);
		return setByHumanId(new Ac4yG8HTemplateDBAdapter(getConnection()).getGUID(aTemplateHumanId), aHumanId);
				
	} // setByHumanIds
			
	public Ac4yG8H setByHumanIds(String aGUID, String aTemplateHumanId, String aHumanId) throws SQLException, Ac4yException{

		new Ac4yG8HTemplateDBAdapter(getConnection()).setByHumanId(aTemplateHumanId);
		return setByHumanId(aGUID, new Ac4yG8HTemplateDBAdapter(getConnection()).getGUID(aTemplateHumanId), aHumanId);
		
	} // setByHumanIds
			
	public Ac4yG8H setByGUIDAndHumanIds(String aGUID, String aTemplateHumanId, String aHumanId) throws SQLException, Ac4yException{

		new Ac4yG8HTemplateDBAdapter(getConnection()).setByHumanId(aTemplateHumanId);
		return setByGUIDAndHumanId(aGUID, new Ac4yG8HTemplateDBAdapter(getConnection()).getGUID(aTemplateHumanId), aHumanId);
				
	} // setByGUIDAndHumanIds

	
	public ResultSet getDoesExistByPersistentIdResultSet(Statement aStatement, int aPersistentId) throws SQLException, Ac4yException{
		
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	

		String sqlStatement = "SELECT count(*) FROM Ac4yG8H WHERE persistentId = " + aPersistentId;
		
		return aStatement.executeQuery(sqlStatement);

	} // getDoesExistByPersistentIdResultSet
	
	public ResultSet getByPersistentIdResultSet(Statement aStatement, int aPersistentId) throws SQLException, Ac4yException{
		
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	

		String sqlStatement = "SELECT * FROM Ac4yG8H WHERE persistentId = " + aPersistentId;
		
		return aStatement.executeQuery(sqlStatement);

	} // getByPersistentIdResultSet
	
	
	public boolean isExistByPersistentId(int aPersistentId) throws SQLException, Ac4yException{

		boolean	result = false;
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getDoesExistByPersistentIdResultSet(statement, aPersistentId);

		if (resultSet.next())
			result = resultSet.getInt(1)>0;

		statement.close();
		
		return result;
		
	} // isExistByPersistentId

	public boolean isExistByGUID(String aGUID) throws SQLException, Ac4yException{

		//System.out.println("adapter - isExistByGUID - GUID:" + aGUID );
		
		if (aGUID == null || aGUID == "")
			throw new Ac4yException("GUID is empty!");
		
		String 		vSQLStatement 	= "SELECT humanId FROM Ac4yG8H WHERE GUID = '" + aGUID + "'";
		Statement 	vStatement 		= null;
		boolean		vIsExists		= false;
			
		vStatement 		= getConnection().createStatement();
		
		ResultSet vResultSet = vStatement.executeQuery(vSQLStatement);

		vIsExists = vResultSet.isBeforeFirst();
		
		vStatement.close();
		
		return vIsExists;
		
	} // isExistByGUID

	
	public boolean isExistByHumanId(String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		if (aTemplateGUID == null || aTemplateGUID == "")
			throw new Ac4yException("templateGUID is empty!");		
		
		if (aHumanId == null || aHumanId == "")
			throw new Ac4yException("humanId is empty!");		
		
		String 		vSimpledHumanId = new StringHandler().getSimpled(aHumanId);
		String 		vSQLStatement 	= 
						"SELECT GUID FROM Ac4yG8H WHERE"
						+ " templateGUID = '" + aTemplateGUID + "'"
						+ " and simpledHumanId = '" + vSimpledHumanId + "'";		

		Statement 	vStatement 		= null;
		boolean		vIsExists		= false;
			
		vStatement 		= getConnection().createStatement();
		
		ResultSet vResultSet = vStatement.executeQuery(vSQLStatement);

		vIsExists = vResultSet.isBeforeFirst();
		
		vStatement.close();
		
		return vIsExists;
		
	} // isExistByHumanId
	
	public boolean isExistByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, Ac4yException{

		return (
				new Ac4yG8HTemplateDBAdapter(getConnection()).existByHumanId(aTemplateHumanId) &&
				isExistByHumanId(new Ac4yG8HTemplateDBAdapter(getConnection()).getGUID(aTemplateHumanId), aHumanId)
			);
		
	} // isExistsByHumanIds
	
	
	public boolean doesExistByPersistentId(int aPersistentId) throws SQLException, Ac4yException{

		boolean	result = false;
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getDoesExistByPersistentIdResultSet(statement, aPersistentId);

		if (resultSet.next())
			result = resultSet.getInt(1)>0;

		statement.close();
		
		return result;
		
	} // doesExistByPersistentId

	public boolean doesExistByGUID(String aGUID) throws SQLException, Ac4yException{

		//System.out.println("adapter - isExistByGUID - GUID:" + aGUID );
		
		if (aGUID == null || aGUID == "")
			throw new Ac4yException("GUID is empty!");
		
		String 		vSQLStatement 	= "SELECT humanId FROM Ac4yG8H WHERE GUID = '" + aGUID + "'";
		Statement 	vStatement 		= null;
		boolean		vIsExists		= false;
			
		vStatement 		= getConnection().createStatement();
		
		ResultSet vResultSet = vStatement.executeQuery(vSQLStatement);

		vIsExists = vResultSet.isBeforeFirst();
		
		vStatement.close();
		
		return vIsExists;
		
	} // doesExistByGUID

	public boolean doesExistByHumanId(String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		if (aTemplateGUID == null || aTemplateGUID == "")
			throw new Ac4yException("templateGUID is empty!");		
		
		if (aHumanId == null || aHumanId == "")
			throw new Ac4yException("humanId is empty!");		
		
		String 		vSimpledHumanId = new StringHandler().getSimpled(aHumanId);
		String 		vSQLStatement 	= 
						"SELECT GUID FROM Ac4yG8H WHERE"
						+ " templateGUID = '" + aTemplateGUID + "'"
						+ " and simpledHumanId = '" + vSimpledHumanId + "'";		

		Statement 	vStatement 		= null;
		boolean		vIsExists		= false;
			
		vStatement 		= getConnection().createStatement();
		
		ResultSet vResultSet = vStatement.executeQuery(vSQLStatement);

		vIsExists = vResultSet.isBeforeFirst();
		
		vStatement.close();
		
		return vIsExists;
		
	} // doesExistByHumanId
	
	public boolean doesExistByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, Ac4yException{

		return (
				new Ac4yG8HTemplateDBAdapter(getConnection()).existByHumanId(aTemplateHumanId) &&
				isExistByHumanId(new Ac4yG8HTemplateDBAdapter(getConnection()).getGUID(aTemplateHumanId), aHumanId)
			);
		
	} // doesExistByHumanIds
	
	public ResultSet getByHumanIdResultSet(Statement aStatement, String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{
		
		if (aTemplateGUID == null || aTemplateGUID == "")
			throw new Ac4yException("templateGUID is empty!");		
		
		if (aHumanId == null || aHumanId == "")
			throw new Ac4yException("humanId is empty!");		
		
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	
		
		String 		simpledHumanId = new StringHandler().getSimpled(aHumanId);
		String 		sqlStatement 	= 
						"SELECT * FROM Ac4yG8H WHERE"
						+ " templateGUID = '" + aTemplateGUID + "'"
						+ " and simpledHumanId = '" + simpledHumanId + "'";		
		
		return aStatement.executeQuery(sqlStatement);

	} // getByHumanIdResultSet
	
	public String getGUIDByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, Ac4yException{

		//System.out.println("adapter - getGUIDByHumanIds  - aTemplateHumanId:" + aTemplateHumanId + " aHumanId:" + aHumanId);
		
		return getGUID(new Ac4yG8HTemplateDBAdapter(getConnection()).getGUID(aTemplateHumanId), aHumanId);
		
	} // getGUIDByHumanIds
	
	public String getGUIDByPersistentId(int aPersistentId) throws SQLException, Ac4yException{

		String result = null;
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getByPersistentIdResultSet(statement, aPersistentId);

		if (resultSet.next())
			result = resultSet.getString("GUID");

		statement.close();
		
		return result;
		
	} // getGUIDByPersistentId

	
	
	public int getPersistentId(String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		if (aTemplateGUID == null || aTemplateGUID == "")
			throw new Ac4yException("templateGUID is empty!");		
		
		if (aHumanId == null || aHumanId == "")
			throw new Ac4yException("humanId is empty!");		

		String simpledHumanId = new StringHandler().getSimpled(aHumanId);
		String sqlStatement 	= 
						"SELECT persistentId FROM Ac4yG8H WHERE"
						+ " templateGUID = '" + aTemplateGUID + "'"
						+ " and simpledHumanId = '" + simpledHumanId + "'";		

		Statement statement = null;
		int result = -1;
			
		statement 		= getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		if (resultSet.next()){

			result = resultSet.getInt("persistentId");

		}
		
		statement.close();

		return result;
		
	} // getPersistentId
	
	public int getPersistentDByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, Ac4yException{

		return getPersistentId(new Ac4yG8HTemplateDBAdapter(getConnection()).getGUID(aTemplateHumanId), aHumanId);
		
	} // getPersistentDByHumanIds

	
	public String getGUID(String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException{

		//System.out.println("adapter - getGUID - aTemplateGUID:" + aTemplateGUID + " aHumanId:" + aHumanId + " getSimpledHumanId:" + new StringHandler().getSimpled(aHumanId));		
		
		if (aTemplateGUID == null || aTemplateGUID == "")
			throw new Ac4yException("templateGUID is empty!");		
		
		if (aHumanId == null || aHumanId == "")
			throw new Ac4yException("humanId is empty!");		
		
		String 		vSimpledHumanId = new StringHandler().getSimpled(aHumanId);
		String 		vSQLStatement 	= 
						"SELECT GUID FROM Ac4yG8H WHERE"
						+ " templateGUID = '" + aTemplateGUID + "'"
						+ " and simpledHumanId = '" + vSimpledHumanId + "'";		

		Statement 	vStatement 		= null;
		String		vGUID			= null;
			
		vStatement 		= getConnection().createStatement();
		
		vStatement.execute(vSQLStatement);
		
		ResultSet vResultSet = vStatement.executeQuery(vSQLStatement);

		if (vResultSet.next()){

			vGUID = vResultSet.getString("GUID");

		}
		
		vStatement.close();

		//System.out.println("adapter - getGUID - SQL statement: " + vSQLStatement + " result:" + vGUID);
		
		return vGUID;
		
	} // getGUID
	
	public Ac4yG8H get(ResultSet aResultSet) throws SQLException {

		return 
			new Ac4yG8H(
					aResultSet.getInt("persistentId")
					,aResultSet.getString("GUID")
					,aResultSet.getString("humanId")
					,aResultSet.getString("publicHumanId")
					,aResultSet.getString("simpledHumanId")
					,aResultSet.getString("templateGUID")
					,aResultSet.getInt("templatePersistentId")
			);

	} // get	

	public Ac4yG8H getByPersistentId(int aPersistentId) throws SQLException{
		
		Ac4yG8H result = null;
		
		String 		sqlStatement 	= "SELECT * FROM Ac4yG8H WHERE persistentId = " + aPersistentId;

		Statement 	statement 		= getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		if (resultSet.next())
			result = get(resultSet);
		
		statement.close();

		return result;
				
	} // getByPersistentId
	
	public Ac4yG8H getByGUID(String aGUID) throws SQLException, Ac4yException {

		if (aGUID == null || aGUID == "")
			throw new Ac4yException("GUID is empty!");
		
		Ac4yG8H result = null;
		
		String sqlStatement = 
					"SELECT * FROM Ac4yG8H WHERE"
					+ " GUID = '" + aGUID + "'"
					;		

		Statement 	statement 		= getConnection().createStatement();
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		if (resultSet.next())
			result = get(resultSet);
		
		statement.close();

		return result;
				
	} // getByGUID
	
	public Ac4yG8H getByHumanId(String aTemplateGUID, String aHumanId) throws SQLException, Ac4yException {

		//System.out.println("aTemplateGUID:" + aTemplateGUID);
		//System.out.println("aHumanId:" + aHumanId);
		
		if (aTemplateGUID == null || aTemplateGUID == "")
			throw new Ac4yException("templateGUID is empty!");		
		
		if (aHumanId == null || aHumanId == "")
			throw new Ac4yException("humanId is empty!");		
		
		Ac4yG8H result = null;
		
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getByHumanIdResultSet(statement, aTemplateGUID, aHumanId);

		if (resultSet.next())
			result = get(resultSet);
			
		statement.close();
		
		return result;
				
	} // getByHumanId

	public ResultSet getListResultSet(Statement aStatement, String aWhere) throws SQLException, Ac4yException{
		
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	
		
		String where = "";
		
		if ((aWhere==null)||(aWhere==""))
			where = "1=1";
		else
			where = aWhere;
			
		String sqlStatement = "SELECT * FROM Ac4yG8H WHERE " + where;
		
		return aStatement.executeQuery(sqlStatement);

	} // getListResultSet
	
	public Ac4yG8HList getList(String aWhere) throws SQLException, Ac4yException{
		
		Ac4yG8HList result = new Ac4yG8HList();
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getListResultSet(statement, aWhere);

		while (resultSet.next())
			result.getAc4yG8H().add(get(resultSet));
		
		statement.close();

		return result;
				
	} // getList

	public int getListCount(String aWhere) throws SQLException, Ac4yException {
		
		int result = -2;
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getListResultSet(statement, aWhere);

		resultSet.next();
		
		result = resultSet.getRow();
		
		statement.close();

		return result;
				
	} // getListCount

	
	public Object processOnList(String aWhere, Ac4yProcess aProcess) throws SQLException, Ac4yException, ClassNotFoundException, IOException, ParseException{

		Object result = null;
		
		if (aProcess == null)
			throw new Ac4yException("illegal process!");	
		
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getListResultSet(statement, aWhere);

		while (resultSet.next())
			result = aProcess.process(resultSet);
		
		statement.close();

		return result;
				
	} // processOnList

	

	public ResultSet getInstanceListResultSet(Statement aStatement, String aTemplateGUID, String aWhere) throws SQLException, Ac4yException{
		
		if (aStatement == null)
			throw new Ac4yException("statement is null!");	
		
		String where = "";
		
		if ((aWhere==null)||(aWhere==""))
			where = " and 1=1";
		else
			where = aWhere;
			
		String sqlStatement = 
			"SELECT * "
			+ " FROM Ac4yG8H "
			+ " WHERE "
			+ " templateGUID = '" + aTemplateGUID + "'"
			+ where;
		
		return aStatement.executeQuery(sqlStatement);

	} // getInstanceListResultSet
	
	public Ac4yG8HList getInstanceList(String aTemplateGUID, String aWhere) throws SQLException, Ac4yException{
		
		Ac4yG8HList result = new Ac4yG8HList();
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getListResultSet(statement, aWhere);


		while (resultSet.next())
			result.getAc4yG8H().add(get(resultSet));
		
		statement.close();

		return result;
				
	} // getInstanceList

	public int getInstanceListCount(String aTemplateGUID, String aWhere) throws SQLException, Ac4yException {
		
		int result = -2;
			
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getInstanceListResultSet(statement, aTemplateGUID, aWhere);

		resultSet.next();
		
		result = resultSet.getRow();
		
		statement.close();

		return result;
				
	} // getInstanceListCount

	public Object processOnInstanceList(String aTemplateGUID, String aWhere, Ac4yProcess aProcess,Object aProcessArgument) throws SQLException, Ac4yException, ClassNotFoundException, IOException, ParseException {

		Object result = null;
		
		if (aProcess == null)
			throw new Ac4yException("illegal process!");	
		
		Statement statement = getConnection().createStatement();
		
		ResultSet resultSet = getInstanceListResultSet(statement, aTemplateGUID, aWhere);

		while (resultSet.next()) {

			//System.out.println("instance");
			
			result = //aProcess.process(resultSet);
					aProcess.process(
						new Ac4yProcessContext(
								aProcessArgument,
								resultSet
						)
					);

		}
		
		
		statement.close();

		return result;
				
	} // processOnInstanceList
	
	
	public Ac4yGUIDList getGUIDList(String aWhere) throws SQLException{
		
		Ac4yGUIDList ac4yGUIDList = new Ac4yGUIDList();

		String where = "";
		
		if ((aWhere==null)||(aWhere==""))
			where = "1=1";
		else
			where = aWhere;
			
		String sqlStatement = "SELECT GUID FROM Ac4yG8H WHERE " + where;

		Statement statement = getConnection().createStatement();
		
		statement.execute(sqlStatement);
		
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		while (resultSet.next()){

			ac4yGUIDList.getGUID().add(resultSet.getString("GUID"));

		}
		
		statement.close();

		return ac4yGUIDList;
				
	} // getGUIDList
	
} // Ac4yG8H