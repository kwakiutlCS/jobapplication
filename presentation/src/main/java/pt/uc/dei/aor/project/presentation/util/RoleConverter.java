package pt.uc.dei.aor.project.presentation.util;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import pt.uc.dei.aor.project.business.util.Role;



@FacesConverter(value="roleConverter")
public class RoleConverter extends EnumConverter{

		public RoleConverter() {
			super(Role.class);
		}
}
