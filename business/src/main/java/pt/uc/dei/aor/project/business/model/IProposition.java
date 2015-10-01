package pt.uc.dei.aor.project.business.model;

import java.util.Date;

import pt.uc.dei.aor.project.business.util.ProposalSituation;

public interface IProposition {
	
	public ProposalSituation getState();

	public IApplication getApplication();

	public Date getDate();
}
