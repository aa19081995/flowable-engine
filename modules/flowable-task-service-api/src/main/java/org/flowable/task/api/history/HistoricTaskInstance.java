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

package org.flowable.task.api.history;

import java.util.Date;

import org.flowable.engine.common.api.history.HistoricData;
import org.flowable.task.api.TaskInfo;

/**
 * Represents a historic task instance (waiting, finished or deleted) that is stored permanent for statistics, audit and other business intelligence purposes.
 * 
 * @author Tom Baeyens
 * @author Joram Barrez
 */
public interface HistoricTaskInstance extends TaskInfo, HistoricData {

    /**
     * The reason why this task was deleted {'completed' | 'deleted' | any other user defined string }.
     */
    String getDeleteReason();

    /** Time when the task started. */
    Date getStartTime();

    /** Time when the task was deleted or completed. */
    Date getEndTime();

    /**
     * Difference between {@link #getEndTime()} and {@link #getStartTime()} in milliseconds.
     */
    Long getDurationInMillis();

    /**
     * Difference between {@link #getEndTime()} and {@link #getClaimTime()} in milliseconds.
     */
    Long getWorkTimeInMillis();

    /** Time when the task was claimed. */
    @Override
    Date getClaimTime();

}
