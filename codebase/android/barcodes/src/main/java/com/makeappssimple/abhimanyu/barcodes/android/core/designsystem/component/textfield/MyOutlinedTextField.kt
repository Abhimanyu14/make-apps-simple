package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.textfield

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.extensions.shimmer
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme.cosmosFontFamily

@Composable
public fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isLoading: Boolean = false,
    singleLine: Boolean = false,
    enabled: Boolean = true,
    @StringRes labelTextStringResourceId: Int,
    @StringRes trailingIconContentDescriptionTextStringResourceId: Int,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    value: TextFieldValue = TextFieldValue(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    supportingText: @Composable (() -> Unit)? = null,
    onClickTrailingIcon: () -> Unit = {},
    onValueChange: (updatedValue: TextFieldValue) -> Unit = {},
) {
    if (isLoading) {
        MyOutlinedTextFieldLoadingUI(
            modifier = modifier,
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            OutlinedTextField(
                value = value,
                label = {
                    MyOutlinedTextFieldLabelText(
                        textStringResourceId = labelTextStringResourceId,
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = enabled && value.text.isNotNullOrBlank(),
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        MyIconButton(
                            onClickLabel = stringResource(
                                id = trailingIconContentDescriptionTextStringResourceId,
                            ),
                            onClick = onClickTrailingIcon,
                            modifier = Modifier
                                .padding(
                                    end = 4.dp,
                                ),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = stringResource(
                                    id = trailingIconContentDescriptionTextStringResourceId,
                                ),
                            )
                        }
                    }
                },
                onValueChange = onValueChange,
                supportingText = supportingText,
                isError = isError,
                enabled = enabled,
                visualTransformation = visualTransformation,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                modifier = modifier,
            )
        }
    }
}

@Composable
public fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isLoading: Boolean = false,
    singleLine: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    @StringRes labelTextStringResourceId: Int,
    @StringRes trailingIconContentDescriptionTextStringResourceId: Int,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    supportingText: @Composable (() -> Unit)? = null,
    onTrailingIconClick: () -> Unit = {},
    onValueChange: (updatedValue: String) -> Unit = {},
) {
    if (isLoading) {
        MyOutlinedTextFieldLoadingUI(
            modifier = modifier,
        )
    } else {
        OutlinedTextField(
            value = value,
            label = {
                MyOutlinedTextFieldLabelText(
                    textStringResourceId = labelTextStringResourceId,
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = enabled && !readOnly && value.isNotNullOrBlank(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    MyIconButton(
                        onClickLabel = stringResource(
                            id = trailingIconContentDescriptionTextStringResourceId,
                        ),
                        onClick = onTrailingIconClick,
                        modifier = Modifier
                            .padding(
                                end = 4.dp,
                            ),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = stringResource(
                                id = trailingIconContentDescriptionTextStringResourceId,
                            ),
                        )
                    }
                }
            },
            onValueChange = onValueChange,
            supportingText = supportingText,
            isError = isError,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = LocalTextStyle.current.copy(
                fontFamily = cosmosFontFamily,
            ),
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            modifier = modifier,
        )
    }
}

@Composable
private fun MyOutlinedTextFieldLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = 56.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 8.dp,
                ),
            )
            .shimmer(),
    )
}
