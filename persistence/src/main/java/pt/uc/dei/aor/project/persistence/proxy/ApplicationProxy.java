package pt.uc.dei.aor.project.persistence.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import pt.uc.dei.aor.project.business.model.IApplication;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IProposition;
import pt.uc.dei.aor.project.business.model.IUser;
import pt.uc.dei.aor.project.business.util.ProposalSituation;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.JobProposalEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.UserEntity;
import pt.uc.dei.aor.project.persistence.service.GenericPersistenceService;

public class ApplicationProxy implements IApplication, IProxyToEntity<ApplicationEntity> {

	private ApplicationEntity entity;

	public ApplicationProxy(ApplicationEntity entity) {
		this.entity = entity != null ? entity : new ApplicationEntity();
	}

	
	public ApplicationProxy(String coverLetter, String cv, String sourceInfo, Date date, IUser candidate, IPosition position) {
		
		UserEntity candidateEntity = GenericPersistenceService.getEntity(candidate);
		
		PositionEntity positionEntity = GenericPersistenceService.getEntity(position);
					
		this.entity = new ApplicationEntity(coverLetter, cv, sourceInfo, date, candidateEntity, positionEntity);
	}

	public ApplicationProxy() {
		this(null);
	}

	public ApplicationProxy(String coverLetter, String cv, String sourceInfo, Date date, IUser candidate) {
		UserEntity candidateEntity = GenericPersistenceService.getEntity(candidate);
		
		this.entity = new ApplicationEntity(coverLetter, cv, sourceInfo, date, candidateEntity);
	}


	@Override
	public ApplicationEntity getEntity() {
		return entity;
	}


	@Override
	public IUser getCandidate() {
		return new UserProxy(entity.getCandidate());
	}


	@Override
	public IPosition getPosition() {
		PositionEntity position = entity.getPosition();

		return new PositionProxy(position);
	}


	@Override
	public List<IInterview> getInterviews() {
		List<IInterview> proxies = new ArrayList<>();
		SortedSet<InterviewEntity> entities = entity.getInterviews();

		for (InterviewEntity ie : entities) {
			proxies.add(new InterviewProxy(ie));
		}

		return proxies;
	}


	@Override
	public long getId() {
		return entity.getId();
	}


	@Override
	public void addInterview(IInterview interview) {
		entity.addInterview(GenericPersistenceService.getEntity(interview));
	}


	@Override
	public IInterview getInterviewByDate(Date date) {
		for (IInterview i : getInterviews()) {
			if (i.getDateObject().equals(date)) return i;
		}
		return null;
	}


	@Override
	public void remove(IInterview interview) {
		entity.remove(GenericPersistenceService.getEntity(interview));
	}


	@Override
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		return sdf.format(entity.getDate());
	}


	@Override
	public boolean reachedAllPhases() {
		int scripts = getPosition().getScripts().size();
		int interviews = getInterviews().size();

		if (interviews < scripts) return false;
		return true;
	}


	@Override
	public String getLetter() {
		return entity.getLetter();
	}


	@Override
	public boolean getAnalyzed() {
		return entity.getAnalyzed();
	}


	@Override
	public void changeAnalyzed(boolean value) {
		entity.changeAnalyzed(value);
	}


	@Override
	public boolean isRefused() {
		return entity.isRefused();
	}


	@Override
	public boolean isAccepted() {
		return entity.isAccepted();
	}


	@Override
	public boolean isPropositionSent() {
		return entity.isPropositionSent();
	}

	@Override
	public boolean isRefusedByCandidate() {
		return entity.isRefusedByCandidate();
	}

	@Override 
	public boolean isProposed() {
		return isAccepted() || isPropositionSent() || isRefusedByCandidate();		
	};


	@Override
	public void refuse() {
		entity.setRefused(true);
	}


	@Override
	public void sendProposition(IProposition proposition) {
		entity.setProposition(GenericPersistenceService.getEntity(proposition));
	}


	@Override
	public ProposalSituation getProposition() {
		JobProposalEntity p = entity.getProposition();
		if (p == null) return null;

		return p.getSituation();
	}



	@Override
	public void setCv(String filename) {
		entity.setCv(filename);
	}


	@Override
	public void addPositon(IPosition positionToAdd) {
		entity.setPosition(GenericPersistenceService.getEntity(positionToAdd));
	}


	@Override
	public String getCv() {
		return entity.getCv();
	}


	@Override
	public boolean isSpontaneous() {
		return entity.isSpontaneous();
	}


	@Override
	public Date getDateObject() {
		return entity.getDate();
	}


	@Override
	public Date getPropositionDate() {
		return entity.getPropositionDate();
	}


	@Override
	public void acceptByCandidate() {
		entity.acceptByCandidate();
	}


	@Override
	public void refuseByCandidate() {
		entity.refuseByCandidate();
	}


	@Override
	public boolean hasPosition() {
		if (GenericPersistenceService.getEntity(getPosition()) == null) return false;
		else if (getPosition().getTitle() == null) return false;
		return true;
	}
}
