package ac4y.guid8humanid.domain.object;

import javax.xml.bind.annotation.XmlRootElement;

import ac4y.base.Ac4yException;
import ac4y.base.domain.Ac4yClass;
import ac4y.base.domain.Ac4yIdentification;
import ac4y.base.domain.Ac4yNoId;

@XmlRootElement
public class Ac4yG8H extends Ac4yNoId {

	public Ac4yG8H(){}
	
	public Ac4yG8H(
			int aPersistentId
			,String aGUID
			,String aHumanId
			,String aPublicHumanId
			,String aSimpledHumanId
			,String aTemplateGUID
			,int aTemplatePersistentId) {
		
		setPersistentId(aPersistentId);
		setGUID(aGUID);
		setHumanId(aHumanId);
		setPublicHumanId(aPublicHumanId);
		setSimpledHumanId(aSimpledHumanId);
		setTemplateGUID(aTemplateGUID);
		setTemplatePersistentId(aTemplatePersistentId);
		
	}
	
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public String getHumanId() {
		return humanId;
	}
	public void setHumanId(String humanId) {
		this.humanId = humanId;
	}
	public String getPublicHumanId() {
		return publicHumanId;
	}
	public void setPublicHumanId(String publicHumanId) {
		this.publicHumanId = publicHumanId;
	}
	public String getSimpledHumanId() {
		return simpledHumanId;
	}
	public void setSimpledHumanId(String simpledHumanId) {
		this.simpledHumanId = simpledHumanId;
	}
	public String getTemplateGUID() {
		return templateGUID;
	}
	public void setTemplateGUID(String templateGUID) {
		this.templateGUID = templateGUID;
	}
	
	public int getPersistentId() {
		return persistentId;
	}

	public void setPersistentId(int persistentId) {
		this.persistentId = persistentId;
	}

	public int getTemplatePersistentId() {
		return templatePersistentId;
	}

	public void setTemplatePersistentId(int templatePersistentId) {
		this.templatePersistentId = templatePersistentId;
	}
	
	protected	String	GUID;
	protected	String	humanId;
	protected	String	publicHumanId;
	protected	String	simpledHumanId;
	protected	String	templateGUID;
	protected	int		persistentId;
	protected	int		templatePersistentId;
	protected 	boolean	deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Ac4yIdentification getAc4yIdentificationFromAc4yG8H(Ac4yG8H aAc4yG8H) throws Ac4yException {
		
		if (aAc4yG8H == null)
			throw new Ac4yException("aAc4yG8H is null!");
		
		return 
			new Ac4yIdentification(
				aAc4yG8H.getPersistentId()
				,aAc4yG8H.getGUID()
				,aAc4yG8H.getHumanId()
				,aAc4yG8H.getPublicHumanId()
				,aAc4yG8H.getTemplateGUID()
				,null
				,aAc4yG8H.isDeleted()
			);
		
	} // getAc4yObjectFromAc4yG8H

	public Ac4yIdentification getAc4yIdentification() throws Ac4yException {
		
		return getAc4yIdentificationFromAc4yG8H(this);
		
	} // getAc4yIdentification
		
	
	public Ac4yClass getAc4yClassFromAc4yG8H(Ac4yG8H aAc4yG8H) throws Ac4yException {
		
		if (aAc4yG8H == null)
			throw new Ac4yException("aAc4yG8H is null!");
		
		return 
			new Ac4yClass(
				aAc4yG8H.getPersistentId()
				,aAc4yG8H.getGUID()
				,aAc4yG8H.getHumanId()
				,aAc4yG8H.getPublicHumanId()
				,aAc4yG8H.isDeleted()
			);
		
	} // getAc4yObjectFromAc4yG8H

	public Ac4yClass getAc4yClass() throws Ac4yException {
		return getAc4yClassFromAc4yG8H(this);
	}
		
	
}