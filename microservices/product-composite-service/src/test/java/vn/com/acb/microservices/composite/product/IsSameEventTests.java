package vn.com.acb.microservices.composite.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.acb.api.core.product.Product;
import vn.com.acb.api.event.Event;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static vn.com.acb.api.event.Event.Type.CREATE;
import static vn.com.acb.api.event.Event.Type.DELETE;
import static vn.com.acb.microservices.composite.product.IsSameEvent.sameEventExceptCreatedAt;

public class IsSameEventTests {

	ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testEventObjectCompare() throws JsonProcessingException {

    	// Event #1 and #2 are the same event, but occurs as different times
		// Event #3 and #4 are different events
		Event<Integer, Product> event1 = new Event<>(CREATE, 1, new Product(1, "name", 1, null));
		Event<Integer, Product> event2 = new Event<>(CREATE, 1, new Product(1, "name", 1, null));
		Event<Integer, Product> event3 = new Event<>(DELETE, 1, null);
		Event<Integer, Product> event4 = new Event<>(CREATE, 1, new Product(2, "name", 1, null));

		String event1JSon = mapper.writeValueAsString(event1);

		assertThat(event1JSon, is(sameEventExceptCreatedAt(event2)));
		assertThat(event1JSon, not(sameEventExceptCreatedAt(event3)));
		assertThat(event1JSon, not(sameEventExceptCreatedAt(event4)));
    }
}
