//package org.example.Controller;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.URI;
//
//@RestController
//public class ReviewCont {
//
//    private static final Logger logger = LogManager.getLogger(ReviewCont.class);
//    private ReviewService service;
//
//    public ReviewController(ReviewService service) {
//        this.service = service;
//
//    }
//
//    @GetMapping("/review/{id}")
//    public ResponseEntity<?> getReview(@PathVariable String id) {
//        return service.findById(id)
//                .map(review -> {
//                    try {
//                        return ResponseEntity
//                                .ok()
//                                .eTag(Integer.toString(review.getVersion()))
//                                .location(new URI("/review/" + review.getId()))
//                                .body(review);
//                    } catch (URISyntaxException e) {
//                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//                    }
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//}
