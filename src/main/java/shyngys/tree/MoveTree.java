package shyngys.tree;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MoveTree {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            manager.getTransaction().begin();
            System.out.print("Enter the id of the category to move from the table: ");
            long categoryId = Long.parseLong(input.readLine());
            System.out.print("Enter the id of the new parent category: ");
            long newParentId = Long.parseLong(input.readLine());

            Tree parent = manager.find(Tree.class, categoryId);
            Tree newParent = manager.find(Tree.class, newParentId);
            int length = parent.getRightKey() - parent.getLeftKey() + 1;

            // Сделать отрицательными ключи перемещаемой категории
            manager.createQuery("update Tree set leftKey = -(leftKey), rightKey = -(rightKey) " +
                            "where leftKey >= :leftKey " +
                            "and rightKey <= :rightKey")
                    .setParameter("leftKey", parent.getLeftKey())
                    .setParameter("rightKey", parent.getRightKey())
                    .executeUpdate();
            // Убрать образовавшийся промежуток в иерархии
            manager.createQuery("update Tree set rightKey = rightKey - :length where rightKey >= :rightKey")
                    .setParameter("length", length)
                    .setParameter("rightKey", parent.getRightKey())
                    .executeUpdate();
            manager.createQuery("update Tree set leftKey = leftKey - :length where leftKey > :leftKey")
                    .setParameter("length", length)
                    .setParameter("leftKey", parent.getRightKey())
                    .executeUpdate();
            // Выделить место в новой родительской категории
            manager.createQuery("update Tree set leftKey = leftKey + :length where leftKey > :leftKey")
                    .setParameter("length", length)
                    .setParameter("leftKey", newParent.getRightKey())
                    .executeUpdate();
            manager.createQuery("update Tree set rightKey = rightKey + :length where rightKey >= :rightKey")
                    .setParameter("length", length)
                    .setParameter("rightKey", newParent.getRightKey())
                    .executeUpdate();
            // Поменять отрицательные ключи перемещаемой категории на подходящие значения для нового местоположения
            input.close();
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
