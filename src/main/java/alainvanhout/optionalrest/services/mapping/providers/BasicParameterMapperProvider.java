package alainvanhout.optionalrest.services.mapping.providers;

import alainvanhout.optionalrest.RestException;
import alainvanhout.optionalrest.request.Headers;
import alainvanhout.optionalrest.request.Parameters;
import alainvanhout.optionalrest.request.RestRequest;
import alainvanhout.optionalrest.request.meta.HttpMethod;
import alainvanhout.optionalrest.services.factories.FromContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class BasicParameterMapperProvider implements ParameterMapperProvider {

    @Override
    public Map<Class, BiFunction<Parameter, RestRequest, Object>> getParameterMappersForClass() {
        Map<Class, BiFunction<Parameter, RestRequest, Object>> map = new HashMap<>();

        map.put(RestRequest.class, (p, r) -> r);
        map.put(HttpMethod.class, (p, r) -> r.getMethod());
        map.put(Headers.class, (p, r) -> r.getHeaders());
        map.put(Parameters.class, (p, r) -> r.getParameters());
        map.put(RestException.class, (p, r) -> r.getContext().get("exception"));

        return map;
    }

    @Override
    public Map<Function<Parameter, Boolean>, BiFunction<Parameter, RestRequest, Object>> getParameterMappers() {
        Map<Function<Parameter,Boolean>,BiFunction<Parameter,RestRequest,Object>> map = new HashMap<>();

        map.put(p -> p.getAnnotation(FromContext.class)!= null, (p, r) -> {
            FromContext fromContext = p.getAnnotation(FromContext.class);
            return r.getContext().get(fromContext.value());
        });

        return map;
    }
}