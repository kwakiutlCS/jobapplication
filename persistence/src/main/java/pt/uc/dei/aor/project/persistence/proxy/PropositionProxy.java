package pt.uc.dei.aor.project.persistence.proxy;

import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.persistence.entity.JobProposalEntity;


public class PropositionProxy implements IProposition, IProxyToEntity<JobProposalEntity> {

	private JobProposalEntity entity;

	public PropositionProxy(JobProposalEntity entity) {
		this.entity = entity != null ? entity : new JobProposalEntity();
	}

	

	public PropositionProxy() {
		this(null);
	}



	@Override
	public JobProposalEntity getEntity() {
		return entity;
	}

	
}
