package pt.uc.dei.aor.project.persistence.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pt.uc.dei.aor.project.business.filter.InterviewFilter;
import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IInterview;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.persistence.entity.ApplicationEntity;
import pt.uc.dei.aor.project.persistence.entity.CandidateEntity;
import pt.uc.dei.aor.project.persistence.entity.InterviewEntity;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.IProxyToEntity;
import pt.uc.dei.aor.project.persistence.proxy.InterviewProxy;
import pt.uc.dei.aor.project.persistence.proxy.PositionProxy;

@Stateless
public class PositionPersistenceService implements IPositionPersistenceService {

	@PersistenceContext(unitName = "jobs")
	private EntityManager em;

	@Override
	public IPosition save(IPosition position) {
		PositionEntity entity = GenericPersistenceService.getEntity(position);

		entity = em.merge(entity);

		return new PositionProxy(entity);
	}

	
	@Override
	public List<IPosition> findAllPositions() {

		TypedQuery<PositionEntity> q =  em.createNamedQuery("position.findAll", PositionEntity.class);

		List<PositionEntity> positions = q.getResultList();
		List<IPosition> ipositions = new ArrayList<IPosition>();
		for(PositionEntity pos : positions){
			ipositions.add(new PositionProxy(pos));
		}

		return ipositions;
	}


	@Override
	public long findBiggestCode() {
		long code = 0;
		TypedQuery<Long> q = em.createNamedQuery("position.findMaxCode", Long.class);


		try
		{
		code = q.getSingleResult();
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return code;

	}


	@Override
	public List<IPosition> findPositionByTitle(String title) {
		TypedQuery<PositionEntity> query = em.createNamedQuery(
				"Position.findPositionByTitle", PositionEntity.class);
		query.setParameter("title", "%"+title+"%");
		
		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		
		for (PositionEntity pe : entities) {
			proxies.add(new PositionProxy(pe));
		}
		
		return proxies;
	}


	@Override
	public List<IPosition> findFilteredPositions(int offset, int limit, PositionFilter filter) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PositionEntity> q = cb.createQuery(PositionEntity.class);
			Root<PositionEntity> position = q.from(PositionEntity.class);
			q.select(position);
			
			List<Predicate> criteriaPredicates = new ArrayList<>();
			// start where
			if (filter != null) {
				
				// code
				int codeFilter = filter.getCode();
				if (codeFilter != -1) {
					Expression<Integer> code = position.get("code");
					Predicate codePredicate = cb.equal(code, codeFilter);
										
					criteriaPredicates.add(codePredicate);
				}
				
				// title
				String titleFilter = filter.getTitle();
				if (titleFilter != null) {
					Expression<String> title = position.get("title");
					Predicate titlePredicate = cb.like(cb.lower(title), "%"+titleFilter.toLowerCase()+"%");
										
					criteriaPredicates.add(titlePredicate);
				}
				
				// state
				PositionState stateFilter = filter.getState();
				if (stateFilter != null) {
					Expression<PositionState> state = position.get("state");
					Predicate statePredicate = cb.equal(state, stateFilter);
					
					criteriaPredicates.add(statePredicate);
				}
				
				// localization
				Localization localizationFilter = filter.getLocalization();
				if (localizationFilter != null) {
					Expression<List<Localization>> localizations = position.get("localizations");
					Predicate localizationPredicate = cb.isMember(localizationFilter, localizations);
					
					criteriaPredicates.add(localizationPredicate);
				}
				
								
				// technical area
				List<List<TechnicalArea>> areaFilter = filter.getAreaSets();
				if (areaFilter != null && areaFilter.size() > 0) {
					Expression<List<TechnicalArea>> areas = position.get("technicalAreas");
					Predicate or = null;

					for (List<TechnicalArea> set : areaFilter) {
						Predicate and = null;
						
						for (TechnicalArea area : set) {
							if (and == null) and = cb.isMember(area, areas);
							else and = cb.and(and, cb.isMember(area, areas));
						}
						
						if (or == null) or = and;
						else or = cb.or(or, and);
					}
					criteriaPredicates.add(or);
				}
				
				
				// company
				String companyFilter = filter.getCompany();
				if (companyFilter != null) {
					Expression<String> company = position.get("company");
					Predicate companyPredicate = cb.like(cb.lower(company), "%"+companyFilter.toLowerCase()+"%");
										
					criteriaPredicates.add(companyPredicate);
				}
				
				// dates filter
				Expression<Date> date = position.get("closingDate");
				
				// start date
				Date startFilter = filter.getStartDate();
				if (startFilter != null) {
					Predicate startPredicate = cb.greaterThanOrEqualTo(date, startFilter);
										
					criteriaPredicates.add(startPredicate);
				}
				
				// finish date
				Date finishFilter = filter.getFinishDate();
				if (finishFilter != null) {
					Predicate finishPredicate = cb.lessThanOrEqualTo(date, finishFilter);
										
					criteriaPredicates.add(finishPredicate);
				}
				
				// keyword
				String keywordFilter = filter.getKeyword();
				if (keywordFilter != null) {
					keywordFilter = keywordFilter.toLowerCase();
					Expression<String> keyword = position.get("title");
					Predicate keywordPredicate = cb.like(cb.lower(keyword), "%"+keywordFilter+"%");
					
					keyword = position.get("company");
					keywordPredicate = cb.or(keywordPredicate, 
							cb.like(cb.lower(keyword), "%"+keywordFilter+"%"));
					
					Expression<List<Localization>> localizations = position.get("localizations");
					for (Localization l : Localization.values()) {
						if (l.getLocalizationLabel().toLowerCase().indexOf(keywordFilter) != -1) {
							keywordPredicate = cb.or(keywordPredicate, cb.isMember(l, localizations));
						}
					}
					
					Expression<List<TechnicalArea>> areas = position.get("technicalAreas");
					for (TechnicalArea a : TechnicalArea.values()) {
						if (a.getTechnicalAreaLabel().toLowerCase().indexOf(keywordFilter) != -1) {
							keywordPredicate = cb.or(keywordPredicate, cb.isMember(a, areas));
						}
					}
					
					Expression<PositionState> state = position.get("state");
					for (PositionState st : PositionState.values()) {
						if (st.getPositionStateLabel().toLowerCase().indexOf(keywordFilter) != -1) {
							keywordPredicate = cb.or(keywordPredicate, cb.equal(state, st));
						}
					}
					
					Expression<Long> code = position.get("code");
					try {
						codeFilter = Integer.parseInt(keywordFilter);
						keywordPredicate = cb.or(keywordPredicate, cb.equal(code, codeFilter));
					}
					catch (Exception e) {
						// no int
					}
					
					criteriaPredicates.add(keywordPredicate);
				}
			}
			
			q.where(cb.and(criteriaPredicates.toArray(new Predicate[0])));
			// finish where
			
			TypedQuery<PositionEntity> query = em.createQuery(q);
			
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			
			List<PositionEntity> entities = query.getResultList();
			List<IPosition> proxies = new ArrayList<>();
			
			for (PositionEntity ie : entities) {
				proxies.add(new PositionProxy(ie));
			}
			
			return proxies;
		
	}
}
