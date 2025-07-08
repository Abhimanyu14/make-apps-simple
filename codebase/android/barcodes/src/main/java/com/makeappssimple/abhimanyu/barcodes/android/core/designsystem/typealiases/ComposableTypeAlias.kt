/*
 * Copyright 2025-2025 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.typealiases

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

internal typealias ComposableContent = @Composable () -> Unit
internal typealias NullableComposableContent = @Composable (() -> Unit)?

internal typealias BoxScopedComposableContent = @Composable BoxScope.() -> Unit
internal typealias ColumnScopedComposableContent = @Composable ColumnScope.() -> Unit
internal typealias RowScopedComposableContent = @Composable RowScope.() -> Unit
