package model;

public class Flow {
    public FiveTuple tuple;
    public AppType app = AppType.UNKNOWN;
    public String sni = "";
    public boolean blocked = false;

    public Flow(FiveTuple tuple) {
        this.tuple = tuple;
    }

    @Override
    public String toString() {
        return "Flow{" +
                "tuple=" + tuple +
                ", app=" + app +
                ", blocked=" + blocked +
                '}';
    }
}