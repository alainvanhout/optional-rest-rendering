package optionalrest.core.services.mapping;

import optionalrest.core.scope.Scope;
import optionalrest.core.scope.Supported;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicMapping implements Mapping {

    private boolean passing;
    private int order;
    private Supported supported = new Supported();

    private List<Scope> before = new ArrayList<>();
    private List<Scope> after = new ArrayList<>();

    @Override
    public boolean isPassing() {
        return passing;
    }

    public BasicMapping passing(boolean passing) {
        this.passing = passing;
        return this;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public BasicMapping order(int order) {
        this.order = order;
        return this;
    }

    public BasicMapping incrementOrder(int order) {
        this.order += order;
        return this;
    }

    @Override
    public Supported getSupported() {
        return this.supported;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + getSupported().toString();
    }

    public BasicMapping addBefore(Scope scope) {
        before.add(scope);
        return this;
    }

    public BasicMapping addAfter(Scope scope) {
        after.add(scope);
        return this;
    }

    @Override
    public List<Scope> getBefore() {
        return before;
    }

    @Override
    public List<Scope> getAfter() {
        return after;
    }
}
