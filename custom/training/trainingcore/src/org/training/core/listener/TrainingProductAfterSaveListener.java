package org.training.core.listener;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.tx.AfterSaveEvent;
import de.hybris.platform.tx.AfterSaveListener;
import de.hybris.platform.workflow.WorkflowProcessingService;
import de.hybris.platform.workflow.WorkflowService;
import de.hybris.platform.workflow.WorkflowTemplateService;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowItemAttachmentModel;
import de.hybris.platform.workflow.model.WorkflowModel;
import de.hybris.platform.workflow.model.WorkflowTemplateModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.training.core.enums.ActiveStatus;
import org.training.core.jalo.TrainingVariantProduct;
import org.training.core.model.TrainingVariantProductModel;

import java.util.Collection;
import java.util.List;

public class TrainingProductAfterSaveListener implements AfterSaveListener {
    private static final Logger LOG = Logger.getLogger(TrainingProductAfterSaveListener.class);

    private static final String TRAINING_PRODUCT_WORKFLOW = "Training_PA_ProductActiveStatus";

    private static final String QUERY_TO_FETCH_WORKFLOW_FOR_PRODUCT = "SELECT {wf:" + WorkflowModel.PK
            + "} FROM {WorkflowItemAttachment as wa JOIN Workflow as wf ON {wa:" + WorkflowItemAttachmentModel.WORKFLOW + "} = {wf:"
            + WorkflowModel.PK + "}} WHERE {wa:" + WorkflowItemAttachmentModel.ITEM + "} = ?item AND {wf:" + WorkflowModel.STATUS
            + "} != ?status";

    private ModelService modelService;
    private UserService userService;
    private FlexibleSearchService flexibleSearchService;
    private WorkflowService workflowService;
    private WorkflowTemplateService workflowTemplateService;
    private WorkflowProcessingService workflowProcessingService;

    @Override
    public void afterSave(Collection<AfterSaveEvent> events) {
        events.forEach(item -> {
            final PK pk = item.getPk();
//            check if this event triggered for Product Itemtype
            if (1 == pk.getTypeCode()) {
                ProductModel productModel = getProductForPk(pk);
                if (productModel instanceof TrainingVariantProductModel) {
                    TrainingVariantProductModel product = (TrainingVariantProductModel) productModel;
                    if (product.getActiveStatus().equals(ActiveStatus.INWAREHOUSE)) {
                        triggerTrainingProductWorkflow(product);
                    }
                }
            }

        });

    }

    private ProductModel getProductForPk(final PK pk) {
        return getModelService().get(pk);
    }

    private void triggerTrainingProductWorkflow(ProductModel productModel) {
        if (!checkIfWorkflowAlreadyRunningForItem(productModel)) {
            final WorkflowTemplateModel workflowTemplate = workflowTemplateService
                    .getWorkflowTemplateForCode(TRAINING_PRODUCT_WORKFLOW);
            final WorkflowModel workflow = workflowService.createWorkflow(workflowTemplate, productModel, userService.getAdminUser());
            modelService.save(workflow);
            LOG.info("Started product workflow with code" + workflow.getCode());
            for (final WorkflowActionModel action : workflow.getActions()) {
                modelService.save(action);
            }
            workflowProcessingService.startWorkflow(workflow);
            modelService.save(productModel);
        }
    }

    private boolean checkIfWorkflowAlreadyRunningForItem(final ItemModel item) {
        boolean isWorkflowAvailableForProduct = false;
        final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(QUERY_TO_FETCH_WORKFLOW_FOR_PRODUCT);
        searchQuery.addQueryParameter("item", item);
        searchQuery.addQueryParameter("Status", CronJobStatus.FINISHED);
        final SearchResult<WorkflowModel> searchResult = flexibleSearchService.search(searchQuery);
        final List<WorkflowModel> workFlows = searchResult.getResult();
        if (CollectionUtils.isNotEmpty(workFlows)) {
            isWorkflowAvailableForProduct = true;
            LOG.info("Workflow for the product with pk " + item.getPk() + "is already there " + workFlows.get(0).getCode());
        }
        return isWorkflowAvailableForProduct;
    }

    private ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    public WorkflowService getWorkflowService() {
        return workflowService;
    }

    public void setWorkflowService(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public WorkflowTemplateService getWorkflowTemplateService() {
        return workflowTemplateService;
    }

    public void setWorkflowTemplateService(WorkflowTemplateService workflowTemplateService) {
        this.workflowTemplateService = workflowTemplateService;
    }

    public WorkflowProcessingService getWorkflowProcessingService() {
        return workflowProcessingService;
    }

    public void setWorkflowProcessingService(WorkflowProcessingService workflowProcessingService) {
        this.workflowProcessingService = workflowProcessingService;
    }
}
















