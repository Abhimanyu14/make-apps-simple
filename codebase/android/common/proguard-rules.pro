# region Application class
-keep class * extends android.app.Application
# endregion

# region All public methods in all classes
#-keepclassmembers class * {
#    public <methods>;
#}
# endregion

# region Data classes and their fields
#-keepclassmembers class * {
#    @kotlin.Metadata *;
#}
# endregion

# region Kotlin classes and their members
#-keep class kotlin.** { *; }
#-dontwarn kotlin.**
# endregion

# region Classes with @Keep annotation
#-keep @androidx.annotation.Keep class * {*;}
#-keepclassmembers class * {
#    @androidx.annotation.Keep *;
#}
# endregion

# region Model classes (commonly used for serialization/deserialization)
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
# endregion

# region Enums
#-keepclassmembers enum * { *; }
# endregion

# region Parcelable implementations
#-keep class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}
# endregion

# region R classes
-keep class **.R$* { *; }
# endregion

# region Classes used by reflection (commonly used in DI frameworks)
#-keepattributes *Annotation*
#-keepattributes Signature,InnerClasses,EnclosingMethod
# endregion

# region StringConcatFactory
-dontwarn java.lang.invoke.StringConcatFactory
# endregion

# region Classes used by Gson, Moshi, kotlinx.serialization, etc.
#-keepclassmembers class * {
#    @kotlinx.serialization.SerialName <fields>;
#}
# endregion

# region Kotlinx serialization
#-dontwarn kotlinx.serialization.**
# endregion

# region Koin

# Keep annotation definitions
-keep class org.koin.core.annotation.** { *; }

# Keep classes annotated with Koin annotations
-keep @org.koin.core.annotation.* class * { *; }
# endregion

# region Jetpack compose
#-dontwarn android.view.RenderNode
#-dontwarn android.view.DisplayListCanvas
#-dontwarn android.view.HardwareCanvas

#-keepclassmembers class androidx.compose.ui.platform.ViewLayerContainer {
#    protected void dispatchGetDisplayList();
#}

#-keepclassmembers class androidx.compose.ui.platform.AndroidComposeView {
#    android.view.View findViewByAccessibilityIdTraversal(int);
#}

# Users can create Modifier.Node instances that implement multiple Modifier.Node interfaces,
# so we cannot tell whether two modifier.node instances are of the same type without using
# reflection to determine the class type. See b/265188224 for more context.
#-keep,allowshrinking class * extends androidx.compose.ui.node.ModifierNodeElement

# Keep all the functions created to throw an exception. We don't want these functions to be
# inlined in any way, which R8 will do by default. The whole point of these functions is to
# reduce the amount of code generated at the call site.
#-keepclassmembers,allowshrinking,allowobfuscation class androidx.compose.**.* {
#    static void throw*Exception(...);
#    static void throw*ExceptionForNullCheck(...);
#    # For methods returning Nothing
#    static java.lang.Void throw*Exception(...);
#    static java.lang.Void throw*ExceptionForNullCheck(...);
#    # For functions generating error messages
#    static java.lang.String exceptionMessage*(...);
#    java.lang.String exceptionMessage*(...);
#}

# Keep Compose UI classes
#-keep class androidx.compose.ui.** { *; }
#-keep class androidx.compose.ui.platform.** { *; }
-keep class androidx.compose.ui.platform.AndroidComposeView { *; }
#-keep class androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat { *; }

# Keep Compose Runtime
#-keep class androidx.compose.runtime.** { *; }
#-keep class androidx.compose.runtime.internal.** { *; }
#-keep class androidx.compose.runtime.snapshots.** { *; }

# Keep Compose Foundation
#-keep class androidx.compose.foundation.** { *; }
#-keep class androidx.compose.foundation.layout.** { *; }

# Keep Material Design
#-keep class androidx.compose.material.** { *; }
#-keep class androidx.compose.material3.** { *; }

# Keep Animation
#-keep class androidx.compose.animation.** { *; }

# Keep setContent and related wrapper functions
#-keep class **.*Wrapper*Kt { *; }
#-keep class androidx.compose.ui.platform.Wrapper_androidKt { *; }

# Keep all Composable functions
#-keepclassmembers class * {
#    @androidx.compose.runtime.Composable <methods>;
#}

# Keep Compose compiler generated classes
#-keep class **.*ComposerKt { *; }
#-keep class **.*$Companion { *; }
# endregion
