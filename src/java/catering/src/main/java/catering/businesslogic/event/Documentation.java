package catering.businesslogic.event;


public class Documentation<T> {
    T document;

    public Documentation( T document) {
        this.document = document;
    }

    public T getDocument() {
        return this.document;
    }
}
