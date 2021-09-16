package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        // 서비스당 하나
        EntityManagerFactory emf
                =  Persistence.createEntityManagerFactory("hello");

        // 요청마다 하나.
        // 스레드에서 공유하면 안된다.
        EntityManager em = emf.createEntityManager();

        // jpa 모든 변경사항은 트랜잭션 안에서만 동작한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //비영속 상태.
            Member member = new Member();
            member.setId(100L);
            member.setName("HEEELLO");

            //영속 상태.
            em.persist(member);

            // 1차 캐시상태
            Member findMember = em.find(Member.class, 100L);
            System.out.println("findMember.id : " + findMember.getId());
            System.out.println("findMember.name : " + findMember.getName());


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
