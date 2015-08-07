package pt.uc.dei.aor.project.business.model;

public interface IModelFactory {

	IWorker worker(String login, String email, String password, String name, String surname);

	ICandidate candidate(String login, String name, String surname, String email, String encrypt);	

}
