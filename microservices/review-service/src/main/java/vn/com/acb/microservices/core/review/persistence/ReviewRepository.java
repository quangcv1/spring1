package vn.com.acb.microservices.core.review.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ReviewRepository extends ReactiveCrudRepository<ReviewEntity, Integer> {

    //@Transactional(readOnly = true)
    Flux<ReviewEntity> findByProductId(int productId);
}
