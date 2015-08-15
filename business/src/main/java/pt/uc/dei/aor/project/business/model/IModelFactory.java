package pt.uc.dei.aor.project.business.model;

public interface IModelFactory {

	IWorker worker(String login, String email, String password, String name, String surname);

	IScript script();	

}
