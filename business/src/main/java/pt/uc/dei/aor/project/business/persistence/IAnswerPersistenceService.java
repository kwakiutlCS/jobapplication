package pt.uc.dei.aor.project.business.persistence;

import java.util.List;

import pt.uc.dei.aor.project.business.model.IAnswer;
import pt.uc.dei.aor.project.business.model.IInterview;

public interface IAnswerPersistenceService {

	IAnswer save(IAnswer answer);

	IAnswer findAnswerByInterviewAndQuestion(IInterview interview, String text);

	List<IAnswer> findAnswersByInterview(IInterview interview);
	
}
