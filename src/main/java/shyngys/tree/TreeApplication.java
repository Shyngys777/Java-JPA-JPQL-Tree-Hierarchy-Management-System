package shyngys.tree;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TreeApplication {
    public static void main(String[] args) {

        // Коплектующие
        // - Процессоры
        // - - Intel
        // - - AMD
        // - ОЗУ
        // Аудиотехника
        // - Наушники
        // - - С микрофоном
        // - - Без микрофона

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        List<Tree> trees = manager.createQuery("select t from Tree t order by t.leftKey", Tree.class)
                .getResultList();
        for (Tree tree : trees) {
            for (int i = 0; i <= tree.getLevel(); i++) {
                System.out.print("- ");
            }
            System.out.println(tree.getName());
        }
    }
}