package ac4y.guid8humanid.domain.object;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

public class Ac4yGUIDList extends Ac4yNoId {
	
	public Ac4yGUIDList() {
		setGUID(new ArrayList<String>());
	}
	
	protected List<String> GUID;

	public List<String> getGUID() {
	 	 return GUID; 
	}

	public void setGUID(List<String> GUID) { 
		 this.GUID = GUID; 
	} 

}
