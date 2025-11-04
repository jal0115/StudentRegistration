package bd1;

public class Subject {
    public String code;
    public String name;
    public float credit;

    public Subject(String code, String name, float credit) {
        this.code = code;
        this.name = name;
        this.credit = credit;
    }

    @Override
    public String toString() {
        return code + "/" + name + "/" + credit;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return code.equals(subject.code);
    }
}