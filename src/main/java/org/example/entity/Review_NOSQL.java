//package org.example.entity;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Document(collection="Reviews")
//public class Review_NOSQL {
//
//
//    private String id;
//    private  Integer productId;
//    private Integer version=1;
//    private List<ReviewEntry_NoQSL> entries= new ArrayList<>();
//
//    public Review_NOSQL(String id, Integer productId, Integer version) {
//        this.id = id;
//        this.productId = productId;
//        this.version = version;
//    }
//
//    public Review_NOSQL(String id, Integer productId) {
//        this.id = id;
//        this.productId = productId;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Integer getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Integer productId) {
//        this.productId = productId;
//    }
//
//    public Integer getVersion() {
//        return version;
//    }
//
//    public void setVersion(Integer version) {
//        this.version = version;
//    }
//}
