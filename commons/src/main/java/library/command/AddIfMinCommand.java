package library.command;


import library.model.Organization;
import library.model.UserData;

import java.util.Objects;

public class AddIfMinCommand extends Command {
    private Organization organization;

    public AddIfMinCommand(Organization organization, UserData userData) {
        super(userData);
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddIfMinCommand that = (AddIfMinCommand) o;
        return Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization);
    }


}