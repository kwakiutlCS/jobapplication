package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.service.IPositionBusinessService;


@Named
@RequestScoped
public class ListPositionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Inject
	private IPositionBusinessService iposition;
	
	
	public List<IPosition> getPositions() {
		return iposition.getIPositions();
	}

}
