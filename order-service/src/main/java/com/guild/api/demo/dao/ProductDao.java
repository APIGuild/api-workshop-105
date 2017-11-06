package com.guild.api.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import com.guild.api.demo.dao.exception.DaoException;
import com.guild.api.demo.dao.exception.DaoExceptionBuilder;
import com.guild.api.demo.model.ProductModel;
import com.guild.api.demo.util.logging.PerformanceLog;
import com.guild.api.demo.util.rest.RestTemplateExecutor;

@Component
public class ProductDao {
    private static final String RETRIEVE_PRODUCT_URL = "{baseUrl}/products/{productId}";
    private static final String RETRIEVE_PRODUCT_KEY = "retrieveProduct";

    @Autowired
    @Qualifier("productServiceTemplateExecutor")
    private RestTemplateExecutor restTemplateExecutor;

    @PerformanceLog
    public ProductModel getProduct(String productId) throws DaoException {
        String url = UriComponentsBuilder.fromPath(RETRIEVE_PRODUCT_URL)
                .buildAndExpand(restTemplateExecutor.getEndpointProperties().getBaseUrl(), productId).toString();
        try {
            String productInfo = restTemplateExecutor.getForEntity(RETRIEVE_PRODUCT_KEY, url, String.class);
            return new ProductModel(productId, productInfo);
        } catch (Exception exception) {
            throw new DaoExceptionBuilder(url).build(exception);
        }
    }

}
