package org.training.core.product.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.impl.DefaultProductService;
import org.training.core.product.TrainingProductService;
import org.training.core.product.dao.TrainingProductDao;

import javax.annotation.Resource;
import java.util.List;

public class DefaultTrainingProductService extends DefaultProductService implements TrainingProductService {

    @Resource(name = "trainingProductDao")
    private TrainingProductDao trainingProductDao;


    @Override
    public List<ProductModel> getAllProductModels() {
        return trainingProductDao.getAllProductModels();
    }
}
