package vn.com.acb.microservices.core.product.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, String> {
    Mono<ProductEntity> findByProductId(int productId);
}