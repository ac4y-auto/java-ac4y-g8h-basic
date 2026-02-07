package ac4y.guid8humanid.domain.object;

import ac4y.base.domain.Ac4yNoId;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ac4yG8HList extends Ac4yNoId {
	
	public Ac4yG8HList() {
		setAc4yG8H(new ArrayList<Ac4yG8H>());
	}
	
	protected List<Ac4yG8H> ac4yG8H;

	public List<Ac4yG8H> getAc4yG8H() {
	 	 return ac4yG8H; 
	}

	public void setAc4yG8H(List<Ac4yG8H> ac4yG8H) { 
		 this.ac4yG8H = ac4yG8H; 
	} 

}
