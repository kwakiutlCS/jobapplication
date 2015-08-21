package pt.uc.dei.aor.project.presentation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ReorderEvent;

import pt.uc.dei.aor.project.business.exception.IllegalAnswerOptionsException;
import pt.uc.dei.aor.project.business.exception.IllegalQuestionTypeException;
import pt.uc.dei.aor.project.business.exception.IllegalScaleException;
import pt.uc.dei.aor.project.business.model.IScript;
import pt.uc.dei.aor.project.business.model.IScriptEntry;
import pt.uc.dei.aor.project.business.service.IScriptBusinessService;
import pt.uc.dei.aor.project.business.service.IScriptEntryBusinessService;

@Named
@RequestScoped
public class ScriptEntryBean implements Serializable {
	
	private static final long serialVersionUID = -6537089658441990072L;

	@Inject
	private IScriptEntryBusinessService ejb;
	
	public void updateQuestion(IScriptEntry entry) {
		ejb.save(entry);
	}
	
}
