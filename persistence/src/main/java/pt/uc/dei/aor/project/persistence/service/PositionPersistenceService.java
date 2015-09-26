package pt.uc.dei.aor.project.persistence.service;

import java.util.ArrayList;
import java.util.Date;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.project.business.filter.PositionFilter;
import pt.uc.dei.aor.project.business.model.IPosition;
import pt.uc.dei.aor.project.business.model.IWorker;
import pt.uc.dei.aor.project.business.persistence.IPositionPersistenceService;
import pt.uc.dei.aor.project.business.util.Localization;
import pt.uc.dei.aor.project.business.util.PositionState;
import pt.uc.dei.aor.project.business.util.TechnicalArea;
import pt.uc.dei.aor.project.persistence.entity.PositionEntity;
import pt.uc.dei.aor.project.persistence.entity.WorkerEntity;
import pt.uc.dei.aor.project.persistence.proxy.PositionProxy;

@Stateless
public class PositionPersistenceService implements IPositionPersistenceService {

	private static final Logger logger = LoggerFactory.getLogger(PositionPersistenceService.class);
	
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

		try	{
			code = q.getSingleResult();
		}
		catch (Exception e)	{
			// first entry
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
	public List<IPosition> findFilteredPositions(int offset, int limit, PositionFilter filter, IWorker user) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PositionEntity> q = cb.createQuery(PositionEntity.class);
			Root<PositionEntity> position = q.from(PositionEntity.class);
			q.select(position);
			
			List<Predicate> criteriaPredicates = new ArrayList<>();
			
			if (user != null) {
				Expression<WorkerEntity> userField = position.get("contactPerson");
				WorkerEntity w = GenericPersistenceService.getEntity(user);
				criteriaPredicates.add(cb.equal(userField, w));
			}
			
			
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
				List<String> titleFilter = filter.getTitles();
				if (titleFilter != null && titleFilter.size() > 0) {
					Expression<String> title = position.get("title");
					criteriaPredicates.add(GenericPersistenceService
							.orStringPredicate(titleFilter, title, cb));
				}
				
				// state
				PositionState stateFilter = filter.getState();
				if (stateFilter != null) {
					Expression<PositionState> state = position.get("state");
					Predicate statePredicate = cb.equal(state, stateFilter);
					
					criteriaPredicates.add(statePredicate);
				}
				
				// localization
				List<List<Localization>> localizationFilter = filter.getLocalizationSets();
				if (localizationFilter != null && localizationFilter.size() > 0) {
					Expression<List<Localization>> localizations = position.get("localizations");
					criteriaPredicates.add(GenericPersistenceService
							.andOrPredicate(localizationFilter, localizations, cb));
				}
				
								
				// technical area
				List<List<TechnicalArea>> areaFilter = filter.getAreaSets();
				if (areaFilter != null && areaFilter.size() > 0) {
					Expression<List<TechnicalArea>> areas = position.get("technicalAreas");
					criteriaPredicates.add(GenericPersistenceService.andOrPredicate(areaFilter, areas, cb));
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
				List<String> keywordFilter = filter.getKeywords();
				if (keywordFilter != null && keywordFilter.size() > 0) {
					
					Expression<String> keyword = position.get("title");
					Predicate keywordPredicate = GenericPersistenceService
							.orStringPredicate(keywordFilter, keyword, cb);
					
					keyword = position.get("company");
					keywordPredicate = cb.or(keywordPredicate, 
							GenericPersistenceService.orStringPredicate(keywordFilter, keyword, cb));
					
					Expression<List<Localization>> localizations = position.get("localizations");
					for (Localization l : Localization.values()) {
						for (String s : keywordFilter) {
							if (l.getLocalizationLabel().toLowerCase().equals(s.toLowerCase().trim())) {
								keywordPredicate = cb.or(keywordPredicate, cb.isMember(l, localizations));
							}
						}
					}

					Expression<List<TechnicalArea>> areas = position.get("technicalAreas");
					for (TechnicalArea a : TechnicalArea.values()) {
						for (String s : keywordFilter) {
							if (a.getTechnicalAreaLabel().toLowerCase().indexOf(s) != -1) {
								keywordPredicate = cb.or(keywordPredicate, cb.isMember(a, areas));
							}
						}
					}

					Expression<PositionState> state = position.get("state");
					for (PositionState st : PositionState.values()) {
						for (String s : keywordFilter) {
							if (st.getPositionStateLabel().toLowerCase().equals(s.toLowerCase().trim())) {
								keywordPredicate = cb.or(keywordPredicate, cb.equal(state, st));
							}
						}
					}
					
					Expression<Long> code = position.get("code");
					Predicate longPred = GenericPersistenceService
							.orLongPredicate(keywordFilter, code, cb);
					if (longPred != null) {
						keywordPredicate = cb.or(keywordPredicate, longPred);
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


	@Override
	public List<IPosition> findOpenPositions() {
		TypedQuery<PositionEntity> query = em.createNamedQuery("Position.findOpenPositions", PositionEntity.class);
		List<PositionEntity> entities = query.getResultList();
		
		List<IPosition> proxies = new ArrayList<>();
		
		for (PositionEntity pe : entities) {
			proxies.add(new PositionProxy(pe));
		}
		
		return proxies;
	}


	@Override
	public IPosition find(long id) {
		PositionEntity entity = em.find(PositionEntity.class, id);
		return new PositionProxy(entity);
	}
}
