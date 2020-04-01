package engine.util;

public interface TripleConsumer<FirstType, SecondType, ThirdType>{
    void accept(FirstType firstObject, SecondType secondObject, ThirdType thirdObject);
}
