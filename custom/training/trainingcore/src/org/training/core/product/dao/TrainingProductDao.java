package org.training.core.product.dao;

import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

public interface TrainingProductDao {
    List<ProductModel> getAllProductModels();
}
