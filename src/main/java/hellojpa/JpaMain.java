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
            // 등록
            //  Member member = new Member();
            //  member.setId(2L);
            //  member.setName("HelloB");
            // em.persist(member);


            // 수정 // persist 하지 않아도 update 쿼리를 실행한다.
            // 업데이트 여부(변경감지)를 확인하고 tx.commit() 전 자동 업데이트 진행.
            // Member findMember =  em.find(Member.class, 1L);
            // findMember.setName("HelloJPA");


            //삭제
            //em.remove(findMember);

            // JPQL
            List<Member> memberList
                    = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(2)
                    .getResultList();

            for(Member item : memberList){
                System.out.println(item.getId() + ", " + item.getName());
            }


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
