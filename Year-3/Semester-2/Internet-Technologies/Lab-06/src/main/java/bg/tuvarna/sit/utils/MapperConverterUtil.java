package bg.tuvarna.sit.utils;

import bg.tuvarna.sit.entities.Role;
import bg.tuvarna.sit.entities.RoleType;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class MapperConverterUtil {

  public static Converter<Set<RoleType>, Set<Role>> roleTypesToRoles = new Converter<>() {

    @Override
    public Set<Role> convert(MappingContext<Set<RoleType>, Set<Role>> context) {

      if (context.getSource() == null) {
        return null;
      }

      return context.getSource()
              .stream()
              .map(roleType -> {
                Role role = new Role();
                role.setId(roleType.getValue());
                role.setRole(roleType);
                return role;
              }).collect(Collectors.toSet());
    }
  };

  public static Converter<Set<Role>, Set<RoleType>> rolesToRoleTypes = new Converter<>() {

    @Override
    public Set<RoleType> convert(MappingContext<Set<Role>, Set<RoleType>> context) {
      if (context.getSource() == null) {
        return null;
      }

      return context.getSource().stream()
              .map(Role::getRole)
              .collect(Collectors.toSet());
    }
  };
}
