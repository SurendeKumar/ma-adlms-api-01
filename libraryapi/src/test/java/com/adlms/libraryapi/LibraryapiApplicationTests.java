//package com.adlms.libraryapi;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class LibraryapiApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}

package com.adlms.libraryapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "spring.config.import=",
        "user.activity.service.base-url=http://localhost:8081"
    }
)
@ActiveProfiles("test")
class LibraryapiApplicationTests {

    @Test
    void contextLoads() {
    }
}
