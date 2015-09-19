package pt.uc.dei.aor.project.presentation.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator("fileValidator")
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
    		throws ValidatorException {
        Part part = (Part) value;
        
        System.out.println(part.getSize());
        
        if(part.getSize() > 1024*1024*9){
           throw new ValidatorException(new FacesMessage(
        		   FacesMessage.SEVERITY_ERROR, "File is too large", "File is too large"));
        }
    }

}