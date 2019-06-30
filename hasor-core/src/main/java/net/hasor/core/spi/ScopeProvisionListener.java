/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package net.hasor.core.spi;
import net.hasor.core.Scope;

import java.util.EventListener;
import java.util.function.Supplier;
/**
 * Scope 注册监听器
 * @version : 2013-11-8
 * @author 赵永春 (zyc@hasor.net)
 */
public interface ScopeProvisionListener extends EventListener {
    /**
     * 发现新的作用域。
     * @param scopeName 新作用域名。
     * @param scopeSupplier 新作用域。
     */
    public void newScope(String scopeName, Supplier<? extends Scope> scopeSupplier) throws Throwable;
}