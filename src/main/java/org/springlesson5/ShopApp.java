package org.springlesson5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springlesson5.config.HibernateConfig;
import org.springlesson5.dao.ProductDao;
import org.springlesson5.entity.Product;

public class ShopApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =new AnnotationConfigApplicationContext(HibernateConfig.class);
//                ProductDao productDao  = new OldJdbcManufacturerDao();
        ProductDao productDao = context.getBean(ProductDao.class);

        for (Product product : productDao.findAll()){
            System.out.println(product);
        }
    }
}
