package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;


@Named
@SessionScoped
public class ListPositionBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	

	@Inject
	private IPositionBusinessService iposition;
	
	public List<IPosition> getPositions() {
		
//		List<IPosition> positions = new ArrayList<IPosition>();
//		positions.addAll(iposition.getIPositions());
//		System.out.println("primeito da lista"+positions.get(0));
		
		return iposition.getIPositions();
	}

}
