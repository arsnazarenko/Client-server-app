package library.command;


import library.model.UserData;

import java.util.Objects;

public class RemoveIdCommand extends Command {
    private Long id;

    public RemoveIdCommand(Long id, UserData userData) {
        super(userData);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoveIdCommand that = (RemoveIdCommand) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
