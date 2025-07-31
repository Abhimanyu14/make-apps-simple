# Keep all data classes and their fields
-keepclassmembers class * {
    @kotlin.Metadata *;
}

# Keep all Kotlin classes and their members
-keep class kotlin.** { *; }
-dontwarn kotlin.**

# Keep all classes with @Keep annotation
-keep @androidx.annotation.Keep class * {*;}
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Keep all model classes (commonly used for serialization/deserialization)
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep all classes used by Gson, Moshi, kotlinx.serialization, etc.
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}

# Keep all enums
-keepclassmembers enum * { *; }

# Keep all Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep all R classes
-keep class **.R$* { *; }

# Keep all classes used by reflection (commonly used in DI frameworks)
-keepattributes *Annotation*
-keepattributes Signature,InnerClasses,EnclosingMethod

# Don't warn about missing classes for kotlinx.serialization
-dontwarn kotlinx.serialization.**

# Add any additional rules specific to your app or libraries here
-dontwarn java.lang.invoke.StringConcatFactory

# Koin

# Keep annotation definitions
-keep class org.koin.core.annotation.** { *; }

# Keep classes annotated with Koin annotations
-keep @org.koin.core.annotation.* class * { *; }
