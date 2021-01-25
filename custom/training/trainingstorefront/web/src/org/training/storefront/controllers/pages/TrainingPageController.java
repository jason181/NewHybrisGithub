package org.training.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.facades.product.TrainingProductFacade;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/")
public class TrainingPageController extends AbstractPageController {
    private static final String TRAINING_PAGE = "training-page";
    private static final String LEARNING_PAGE = "learning-page";

    @Resource(name = "trainingProductFacade")
    TrainingProductFacade trainingProductFacade;

    @RequestMapping(value = TRAINING_PAGE, method = RequestMethod.GET)
    public String getView(final Model model) throws CMSItemNotFoundException {
        final ContentPageModel contentPageModel = getContentPageForLabelOrId(TRAINING_PAGE);
        storeCmsPageInModel(model,contentPageModel);

        List<ProductData> productDataList = trainingProductFacade.getAllProducts();
        model.addAttribute("product", productDataList);

        return getViewForPage(model);
    }

    @RequestMapping(value = LEARNING_PAGE, method = RequestMethod.GET)
    public String getLearningView(final Model model) throws CMSItemNotFoundException {
        final ContentPageModel contentPageModel = getContentPageForLabelOrId(LEARNING_PAGE);
        storeCmsPageInModel(model,contentPageModel);

        return getViewForPage(model);
    }

}
