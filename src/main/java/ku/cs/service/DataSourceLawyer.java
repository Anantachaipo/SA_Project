package ku.cs.service;

public interface DataSourceLawyer<T> {
    T readDataLawyer();
    void writeDataLawyer(T t);
}
