package shyngys.tree;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DeleteTree {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try {
            manager.getTransaction().begin();
            System.out.println("Enter the id of the category to delete from the table : ");
            long parentId = Long.parseLong(input.readLine());

            Tree parent = manager.find(Tree.class, parentId);
            manager.createQuery("delete from Tree where leftKey >= :leftKey and rightKey <= :rightKey")
                    .setParameter("leftKey", parent.getLeftKey())
                    .setParameter("rightKey", parent.getRightKey())
                    .executeUpdate();
            int length = parent.getRightKey() - parent.getLeftKey() + 1;
            manager.createQuery("update Tree set rightKey = rightKey - :length where rightKey >= :rightKey")
                    .setParameter("length", length)
                    .setParameter("rightKey", parent.getRightKey())
                    .executeUpdate();
            manager.createQuery("update Tree set leftKey = leftKey - :length where leftKey >= :leftKey")
                    .setParameter("length", length)
                    .setParameter("leftKey", parent.getRightKey())
                    .executeUpdate();

            System.out.println("Selected category was deleted successfully!");
            manager.getTransaction().commit();
        }  catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
