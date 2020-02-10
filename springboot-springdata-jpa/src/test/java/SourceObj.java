import java.util.List;

public class SourceObj {
    private ComplexId id;
    private String name;
    private List<CS> list;

    public List<CS> getList() {
        return list;
    }

    public void setList(List<CS> list) {
        this.list = list;
    }

    public ComplexId getId() {
        return id;
    }

    public void setId(ComplexId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}