package vn.com.acb.microservices.core.product;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.com.acb.api.core.product.Product;
import vn.com.acb.microservices.core.product.persistence.ProductEntity;
import vn.com.acb.microservices.core.product.persistence.ProductRepository;

@SpringBootApplication
@ComponentScan("vn.com.acb")
public class ProductServiceApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceApplication.class);

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(ProductServiceApplication.class, args);

		String mongodDbHost = ctx.getEnvironment().getProperty("spring.data.mongodb.host");
		String mongodDbPort = ctx.getEnvironment().getProperty("spring.data.mongodb.port");
		LOG.info("Connected to MongoDb: " + mongodDbHost + ":" + mongodDbPort);
	}

}
