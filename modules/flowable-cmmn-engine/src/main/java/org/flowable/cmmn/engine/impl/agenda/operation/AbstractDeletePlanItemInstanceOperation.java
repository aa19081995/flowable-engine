/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.cmmn.engine.impl.agenda.operation;

import org.flowable.cmmn.engine.impl.persistence.entity.PlanItemInstanceEntity;
import org.flowable.cmmn.engine.impl.util.CommandContextUtil;
import org.flowable.cmmn.model.PlanItem;
import org.flowable.engine.common.impl.interceptor.CommandContext;

/**
 * @author Joram Barrez
 */
public abstract class AbstractDeletePlanItemInstanceOperation extends AbstractChangePlanItemInstanceStateOperation {

    public AbstractDeletePlanItemInstanceOperation(CommandContext commandContext, PlanItemInstanceEntity planItemInstanceEntity) {
        super(commandContext, planItemInstanceEntity);
    }

    @Override
    public void run() {
        super.run();
        
        boolean isRepeating = isRepeatingOnDelete();
        if (isRepeating) {
            
            // Create new repeating instance
            PlanItemInstanceEntity newPlanItemInstanceEntity = createNewPlanItemInstance();
            CommandContextUtil.getAgenda(commandContext).planCreatePlanItemInstanceOperation(newPlanItemInstanceEntity);
            
            // Set repetition counter
            int counter = getRepetitionCounter(planItemInstanceEntity);
            setRepetitionCounter(newPlanItemInstanceEntity, ++counter);
            
            // Plan item doesn't have entry criteria (checked in the if condition) and immediately goes to ACTIVE
            CommandContextUtil.getAgenda(commandContext).planActivatePlanItemInstanceOperation(newPlanItemInstanceEntity);
        }
        
        deleteSentryPartInstances();
        CommandContextUtil.getPlanItemInstanceEntityManager(commandContext).delete(planItemInstanceEntity);
    }

    protected PlanItemInstanceEntity createNewPlanItemInstance() {
        return copyAndInsertPlanItemInstance(commandContext, planItemInstanceEntity);
    }

    protected boolean isRepeatingOnDelete() {
        
        // If there are not entry criteria and the repetition rule evaluates to true, 
        // a new instance needs to be created.
        
        PlanItem planItem = planItemInstanceEntity.getPlanItem();
        if (isEvaluateRepetitionRule() && isPlanItemRepeatableOnComplete(planItem)) {
            return evaluateRepetitionRule(planItemInstanceEntity);
        }
        return false;
    }

    protected abstract boolean isEvaluateRepetitionRule();
    
}
