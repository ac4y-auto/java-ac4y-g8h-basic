package ac4y.guid8humanid.service;

import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;

import ac4y.base.Ac4yException;
import ac4y.base.Ac4yProcess;
import ac4y.base.database.DBConnection;
import ac4y.base.domain.Ac4yIdentification;
import ac4y.guid8humanid.domain.object.Ac4yG8H;
import ac4y.guid8humanid.domain.object.Ac4yG8HList;
import ac4y.guid8humanid.domain.object.Ac4yGUIDList;
import ac4y.guid8humanid.domain.persistence.Ac4yG8HDBAdapter;
import ac4y.guid8humanid.domain.persistence.Ac4yG8HTemplateDBAdapter;
import ac4y.guid8humanid.domain.persistence.Ac4yIdentificationDBAdapter;
import ac4y.service.domain.Ac4yServiceOnDB;

import javax.naming.NamingException;

public class Ac4yGUID8HumanIdService extends Ac4yServiceOnDB  {

	public String getPublicHumanId(String aHumanId) {
		return new Ac4yG8HDBAdapter().getPublicHumanId(aHumanId); 
	}
	
	public Ac4yG8H set(Ac4yIdentification aAc4yIdentification) throws ClassNotFoundException, SQLException, IOException, Ac4yException, NamingException {

		if (aAc4yIdentification == null)
			throw new Ac4yException("identifcation is missing!");
		
		if (aAc4yIdentification.getHumanId() == null || aAc4yIdentification.getHumanId()=="")
			throw new Ac4yException("humanId is empty!");

		if (aAc4yIdentification.getTemplate().getHumanId() == null || aAc4yIdentification.getTemplate().getHumanId()=="")
			throw new Ac4yException("templateHumanId is empty!");

		if (!new Ac4yGUID8HumanIdService().existTemplateByHumanId(aAc4yIdentification.getTemplate().getHumanId())) {

			if (!(aAc4yIdentification.getTemplate().getGUID() == null) && !(aAc4yIdentification.getTemplate().getGUID()=="")) {

				new Ac4yGUID8HumanIdService().setTemplateByGUIDAndHumanId(
					aAc4yIdentification.getTemplate().getGUID()
					,aAc4yIdentification.getTemplate().getHumanId()
				);
				
			}
			
		}
		
		if (!(aAc4yIdentification.getGUID() == null) && !(aAc4yIdentification.getGUID()==""))
			return 
				new Ac4yGUID8HumanIdService().setByGUIDAndHumanIds(
						aAc4yIdentification.getGUID()
						,aAc4yIdentification.getTemplate().getHumanId()
						,aAc4yIdentification.getHumanId()
				);
		else
			return 
				new Ac4yGUID8HumanIdService().setByHumanIds(
						aAc4yIdentification.getTemplate().getHumanId()
						,aAc4yIdentification.getHumanId()
				);

	} // set
	
	public Ac4yG8H insert(String aGUID, String aTemplateGUID, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).insert(aGUID, aTemplateGUID, aHumanId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // insert

	public Ac4yG8H insert(String aTemplateGUID, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).insert(aTemplateGUID, aHumanId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // insert
	
	public Ac4yG8H insert(String aTemplateGUID) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).insert(aTemplateGUID);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // insert
	
	
	public Ac4yG8H deleteByPersistentId(int aPersistentId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).deleteByPersistentId(aPersistentId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // deleteByPersistentId
	
	public Ac4yG8H deleteByGUID(String aGUID) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).deleteByGUID(aGUID);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // deleteByGUID
	
	
	public Ac4yG8H setByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException, NamingException {
		  
		Ac4yG8H result = null;

		DBConnection dBConnection = getDBConnection();

		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).setByHumanIds(aTemplateHumanId, aHumanId);

		dBConnection.getConnection().close();

		return result;
		
	} // setByHumanIds

	public Ac4yG8H setByHumanIds(String aGUID, String aTemplateHumanId, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
		
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).setByHumanIds(aGUID, aTemplateHumanId, aHumanId);
	
		dBConnection.getConnection().close();
		
		return result;
		
	} // setByHumanIds

	
	public String getGUIDByHumanIds(String aTemplateHumanId, String aHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		  
		//System.out.println("service - getGUIDByHumanIds");
		//System.out.println("templateHumanId:" + aTemplateHumanId);
		//System.out.println("humanId:" + aHumanId);
		
		if (aTemplateHumanId == null || aTemplateHumanId == "")
			throw new Ac4yException("templateHumanId is empty!");		
		
		String result = null;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getGUIDByHumanIds(aTemplateHumanId, aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // getGUIDByHumanIds

	public String getGUIDByPersistentId(int aPersistentId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		  
		//System.out.println("service - getGUIDByHumanIds");
		
		String result = null;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getGUIDByPersistentId(aPersistentId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // getGUIDByPersistentId

	
	
	public String getGUID(String aTemplateGUID, String aHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		  
		String result = null;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getGUID(aTemplateGUID, aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // getGUID

	public String getTemplateGUID(String aTemplateHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{
		  
		String result = null;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HTemplateDBAdapter(dBConnection.getConnection()).getGUID(aTemplateHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // getTemplateGUID

	
	public boolean isExistByPersistentId(int aPersistentId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		boolean result = false;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByPersistentId(aPersistentId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // isExistByGUID

	public boolean isExistByGUID(String aGUID) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		boolean result = false;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByGUID(aGUID);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // isExistByGUID

	public boolean isExistByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		//System.out.println("service - isExistByHumanIds - aTemplateHumanId:" + aTemplateHumanId + " aHumanId:" + aHumanId);
		
		boolean result = false;
		
		DBConnection dBConnection = getDBConnection();		
			
		//result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByGUID(getGUIDByHumanIds(aTemplateHumanId, aHumanId) );
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByHumanIds(aTemplateHumanId, aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // isExistByHumanIds


	public boolean existByPersistentId(int aPersistentId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		boolean result = false;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByPersistentId(aPersistentId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // existByPersistentId

	public boolean existByGUID(String aGUID) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		boolean result = false;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByGUID(aGUID);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // existByGUID

	public boolean existByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		//System.out.println("service - isExistByHumanIds - aTemplateHumanId:" + aTemplateHumanId + " aHumanId:" + aHumanId);
		
		boolean result = false;
		
		DBConnection dBConnection = getDBConnection();		
			
		//result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByGUID(getGUIDByHumanIds(aTemplateHumanId, aHumanId) );
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).isExistByHumanIds(aTemplateHumanId, aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // existByHumanIds

	
	public Ac4yG8H setByGUIDAndHumanIds(String aGUID, String aTemplateHumanId, String aHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{

		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).setByGUIDAndHumanIds(aGUID, aTemplateHumanId, aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // setByGUIDAndHumanIds
	
	public Ac4yG8H setByGUIDAndHumanId(String aGUID, String aTemplateGUID, String aHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{

		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).setByGUIDAndHumanId(aGUID, aTemplateGUID, aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
	
	} // setByGUIDAndHumanId
	
	
	public Ac4yG8H setTemplateByHumanId(String aHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{

		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HTemplateDBAdapter(dBConnection.getConnection()).setByHumanId(aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // setTemplateByHumanId

	public Ac4yG8H setTemplateByGUIDAndHumanId(String aGUID, String aHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{

		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HTemplateDBAdapter(dBConnection.getConnection()).setByGUIDAndHumanId(aGUID, aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // setTemplateByGUIDAndHumanId

	public boolean existTemplateByHumanId(String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		  
		boolean result = false;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HTemplateDBAdapter(dBConnection.getConnection()).existByHumanId(aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // existTemplateByHumanId
	
	public Ac4yG8H getTemplateByHumanId(String aHumanId) throws ClassNotFoundException, SQLException, IOException, Ac4yException{

		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HTemplateDBAdapter(dBConnection.getConnection()).setByHumanId(aHumanId);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // setTemplateByHumanId
	
	public Ac4yG8H getTemplateByGUID(String aGUID) throws ClassNotFoundException, SQLException, IOException, Ac4yException{

		Ac4yG8H result;
		
		DBConnection dBConnection = getDBConnection();		
			
		result = new Ac4yG8HTemplateDBAdapter(dBConnection.getConnection()).getByGUID(aGUID);
		
		dBConnection.getConnection().close();
		
		return result;
		
	} // getTemplateByGUID

	
	
	public int getPersistentIdByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		int result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getPersistentDByHumanIds(aTemplateHumanId, aHumanId);
	
		dBConnection.getConnection().close();
		
		return result;

	} // getPersistentIdByHumanIds
	
	public Ac4yG8H getByPersistentId(int aPersistentId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yG8H ac4yG8H = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getByPersistentId(aPersistentId);
	
		dBConnection.getConnection().close();
		
		return ac4yG8H;

	} // getByPersistentId
	
	public Ac4yG8H getByGUID(String aGUID) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yG8H ac4yG8H = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getByGUID(aGUID);
	
		dBConnection.getConnection().close();
		
		return ac4yG8H;

	} // getByGUID
	
	public Ac4yIdentification getAc4yIdentificationByPersistentId(int aPersistentId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yIdentification result = new Ac4yIdentificationDBAdapter(dBConnection.getConnection()).getByPersistentId(aPersistentId);
	
		dBConnection.getConnection().close();
		
		return result;

	} // getAc4yIdentificationByPersistentId
	
	public Ac4yIdentification getAc4yIdentificationByGUID(String aGUID) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yIdentification result = new Ac4yIdentificationDBAdapter(dBConnection.getConnection()).getByGUID(aGUID);
	
		dBConnection.getConnection().close();
		
		return result;

	} // getAc4yIdentificationByGUID
	
	public Ac4yIdentification getAc4yIdentificationByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{
		
		return 	getAc4yIdentificationByPersistentId(
					getPersistentIdByHumanIds(aTemplateHumanId, aHumanId)
				);
/*
		Ac4yG8H ac4yG8H = new Ac4yGUID8HumanIdService().getByHumanIds(aTemplateHumanId, aHumanId);
		Ac4yIdentification ac4yIdentification = ac4yG8H.getAc4yIdentification();
		Ac4yG8H template=new Ac4yGUID8HumanIdService().getTemplateByGUID(ac4yG8H.getTemplateGUID());
		ac4yIdentification.setTemplate(template.getAc4yClass());
		
		return ac4yIdentification;
*/
	} // getAc4yIdentificationByHumanIds
	
	public Ac4yG8H getByHumanId(String aTemplateGUID, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yG8H ac4yG8H = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getByHumanId(aTemplateGUID, aHumanId);
	
		dBConnection.getConnection().close();
		
		return ac4yG8H;

	} // getByHumanId

	public Ac4yG8H getByHumanIds(String aTemplateHumanId, String aHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yG8H ac4yG8H = 
			new Ac4yG8HDBAdapter(dBConnection.getConnection()).getByHumanId(
				new Ac4yG8HTemplateDBAdapter(dBConnection.getConnection()).getGUID(aTemplateHumanId)			
				,aHumanId
			);
	
		dBConnection.getConnection().close();
		
		return ac4yG8H;

	} // getByHumanIds
	
	public Ac4yG8HList getInstanceList(String aTemplateHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yG8HList ac4yG8HList = 
				new Ac4yG8HDBAdapter(dBConnection.getConnection()).getList(
						"templateGUID = '"+getTemplateGUID(aTemplateHumanId)+"'"
				);
	
		dBConnection.getConnection().close();
		
		return ac4yG8HList;

	} // getInstanceList
	
	public int getInstanceListCount(String aTemplateHumanId) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		int result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getInstanceListCount(aTemplateHumanId, "");
	
		dBConnection.getConnection().close();
		
		return result;

	} // getInstanceListCount
	
	public Object processOnInstanceList(String aTemplateGUID, String aWhere, Ac4yProcess aProcess, Object aProcessArgument) throws SQLException, Ac4yException, ClassNotFoundException, IOException, ParseException{

		DBConnection dBConnection = getDBConnection();		
		
		Object result = 
			new Ac4yG8HDBAdapter(dBConnection.getConnection()).processOnInstanceList(
				aTemplateGUID
				,aWhere
				,aProcess
				,aProcessArgument
			);
	
		dBConnection.getConnection().close();
		
		return result;
				
	} // processOnInstanceList
	
	
	public Ac4yG8HList getList(String aWhere) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yG8HList ac4yG8HList = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getList(aWhere);
		
		dBConnection.getConnection().close();
		
		return ac4yG8HList;

	} // getList

	public int getListCount(String aWhere) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		int result = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getListCount(aWhere);
		
		dBConnection.getConnection().close();
		
		return result;

	} // getListCount
	
	public Ac4yGUIDList getGUIDList(String aWhere) throws SQLException, ClassNotFoundException, IOException, Ac4yException{

		DBConnection dBConnection = getDBConnection();		
		
		Ac4yGUIDList ac4yGUIDList = new Ac4yG8HDBAdapter(dBConnection.getConnection()).getGUIDList(aWhere);
	
		dBConnection.getConnection().close();
		
		return ac4yGUIDList;

	} // getGUIDList
	
} // Ac4yG8HService