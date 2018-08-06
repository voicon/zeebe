/*
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zeebe.model.bpmn.impl.instance;

import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN20_NS;
import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_CANCEL_EVENT_DEFINITION;

import io.zeebe.model.bpmn.instance.CancelEventDefinition;
import io.zeebe.model.bpmn.instance.EventDefinition;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

/**
 * The BPMN cancelEventDefinition element
 *
 * @author Sebastian Menski
 */
public class CancelEventDefinitionImpl extends EventDefinitionImpl
    implements CancelEventDefinition {

  public static void registerType(ModelBuilder modelBuilder) {
    final ModelElementTypeBuilder typeBuilder =
        modelBuilder
            .defineType(CancelEventDefinition.class, BPMN_ELEMENT_CANCEL_EVENT_DEFINITION)
            .namespaceUri(BPMN20_NS)
            .extendsType(EventDefinition.class)
            .instanceProvider(
                new ModelTypeInstanceProvider<CancelEventDefinition>() {
                  @Override
                  public CancelEventDefinition newInstance(
                      ModelTypeInstanceContext instanceContext) {
                    return new CancelEventDefinitionImpl(instanceContext);
                  }
                });

    typeBuilder.build();
  }

  public CancelEventDefinitionImpl(ModelTypeInstanceContext context) {
    super(context);
  }
}
