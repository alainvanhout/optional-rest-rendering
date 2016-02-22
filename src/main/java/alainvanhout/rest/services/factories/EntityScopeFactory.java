package alainvanhout.rest.services.factories;

import alainvanhout.rest.RestException;
import alainvanhout.rest.annotations.RestEntityDefinition;
import alainvanhout.rest.annotations.entity.RestEntity;
import alainvanhout.rest.scope.Scope;
import alainvanhout.rest.scope.ScopeContainer;
import alainvanhout.rest.services.ScopeRegistry;
import alainvanhout.rest.services.mapping.Mapping;
import alainvanhout.rest.services.mapping.MethodMapping;
import alainvanhout.rest.utils.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Service
public class EntityScopeFactory implements ScopeFactory {

    public static final String ENTITY = "entity";

    @Autowired
    private ScopeRegistry scopeRegistry;

    @Override
    public void processContainer(ScopeContainer container) {
        try {
            for (Method method : container.getClass().getDeclaredMethods()) {
                Mapping mapping = new MethodMapping(container, method);
                processAccessibleObject(container, method, mapping, method.getReturnType().equals(Void.TYPE));
            }

            for (Field field : container.getClass().getDeclaredFields()) {
                processAccessibleObject(container, field, null, false);
            }
        } catch (SecurityException e) {
            throw new RestException("Could not process class: " + container.getClass(), e);
        }
    }

    private void processAccessibleObject(ScopeContainer container, AccessibleObject accessibleObject, Mapping mapping, boolean passing) {
        RestEntity annRestEntity = ReflectionUtils.retrieveAnnotation(accessibleObject, RestEntity.class);

        // pass and arrive mapping
        if (annRestEntity != null) {
            String scopeName = ScopeFactoryUtils.determineParentName(annRestEntity.scope(), container);
            Scope scope = produceEntityScope(scopeName, container);
            if (passing) {
                scope.addPassMapping(mapping, annRestEntity.methods());
            } else {
                scope.addArriveMapping(mapping, annRestEntity.methods());
            }
        }
    }

    private Scope produceEntityScope(String scopeName, ScopeContainer container) {
        Scope scope = scopeRegistry.produceScope(scopeName, container);
        scope.getDefinition().type(ENTITY);
        return scope;
    }
}