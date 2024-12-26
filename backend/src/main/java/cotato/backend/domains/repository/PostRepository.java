package cotato.backend.domains.repository;

import cotato.backend.domains.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByLikesDesc(Pageable pageable);

    @Modifying
    @Query("update Post p set p.views = p.views + 1 where p.id =:postId")
    void increaseView(@Param("postId") Long postId);
}
