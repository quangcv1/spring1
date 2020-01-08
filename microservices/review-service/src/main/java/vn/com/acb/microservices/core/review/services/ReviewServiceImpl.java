package vn.com.acb.microservices.core.review.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import vn.com.acb.api.core.recommendation.Recommendation;
import vn.com.acb.api.core.review.Review;
import vn.com.acb.api.core.review.ReviewService;
import vn.com.acb.microservices.core.review.persistence.ReviewEntity;
import vn.com.acb.microservices.core.review.persistence.ReviewRepository;
import vn.com.acb.util.exceptions.InvalidInputException;
import vn.com.acb.util.http.ServiceUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository repository;

    private final ReviewMapper mapper;

    private final ServiceUtil serviceUtil;

    //private final Scheduler scheduler;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper, ServiceUtil serviceUtil) {
        //this.scheduler = scheduler;
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Review createReview(Review body) {

        if (body.getProductId() < 1) throw new InvalidInputException("Invalid productId: " + body.getProductId());

        ReviewEntity entity = mapper.apiToEntity(body);
        Mono<Review> newEntity = repository.save(entity)
                .log()
                .onErrorMap(
                        DuplicateKeyException.class,
                        ex -> new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Recommendation Id:" + body.getReviewId()))
                .map(e -> mapper.entityToApi(e));

        return newEntity.block();
    }

    @Override
    public Flux<Review> getReviews(int productId) {

        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        return repository.findByProductId(productId)
                .log()
                .map(e -> mapper.entityToApi(e))
                .map(e -> {e.setServiceAddress(serviceUtil.getServiceAddress()); return e;});

//        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);
//
//        return asyncFlux(getByProductId(productId)).log();
    }

//    protected List<Review> getByProductId(int productId) {
//
//        List<ReviewEntity> entityList = repository.findByProductId(productId);
//        List<Review> list = mapper.entityListToApiList(entityList);
//        list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));
//
//        LOG.debug("getReviews: response size: {}", list.size());
//
//        return list;
//    }

    @Override
    public void deleteReviews(int productId) {

        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        LOG.debug("deleteReviews: tries to delete reviews for the product with productId: {}", productId);
        repository.deleteAll(repository.findByProductId(productId));
    }
}
