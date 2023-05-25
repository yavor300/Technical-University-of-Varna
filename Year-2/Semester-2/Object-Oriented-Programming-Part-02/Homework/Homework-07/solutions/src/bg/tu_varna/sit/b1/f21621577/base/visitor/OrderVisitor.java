package bg.tu_varna.sit.b1.f21621577.base.visitor;

import bg.tu_varna.sit.b1.f21621577.implementation.entities.BoardGame;
import bg.tu_varna.sit.b1.f21621577.implementation.entities.Book;
import bg.tu_varna.sit.b1.f21621577.implementation.entities.Toy;

public interface OrderVisitor {

    void visit(Book book);
    void visit(Toy toy);
    void visit(BoardGame boardGame);
}