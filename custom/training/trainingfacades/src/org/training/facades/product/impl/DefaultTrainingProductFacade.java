package org.training.facades.product.impl;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.training.core.product.TrainingProductService;
import org.training.facades.product.TrainingProductFacade;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultTrainingProductFacade implements TrainingProductFacade {

    @Resource(name = "trainingProductService")
    TrainingProductService trainingProductService;

    @Resource(name =  "productConverter")
    private Converter<ProductModel, ProductData> productConverter;

    @Override
    public List<ProductData> getAllProducts() {
        List<ProductModel> productModels = trainingProductService.getAllProductModels();
        if(CollectionUtils.isNotEmpty(productModels)){
            final List<ProductData> productDataList = new ArrayList<>(productModels.size());
            for (final ProductModel productModel : productModels) {
                productDataList.add(productConverter.convert(productModel));
            }
            return productDataList;
        }
        return Collections.emptyList();
    }
}
