package Classes;

public interface SimpleQueue<T> {
    void addLast(T value);

    T removeFirst() throws Exception;

    T element() throws Exception;

    int count();

    default boolean empty() { // возвращает true когда count== 0 те, когда нет эл в листе, а false когда есть
        return count() == 0;
    }
}
