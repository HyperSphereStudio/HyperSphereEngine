-verbose

-dontwarn org.lwjgl.**
-dontwarn com.esotericsoftware.**
-dontwarn org.objenesis.**
-dontwarn org.apache.**
-dontwarn com.fasterxml.**
-dontwarn net.java.**
-dontwarn com.badlogic.**

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepattributes Signature,InnerClasses,SourceFile,LineNumberTable

-keep class org.lwjgl**
-keep class com.badlogic**
-keep class com.esotericsoftware**
-keep class net.java**
-keep class com.hyperspherestudio.kryonet.Packets**


-keepclassmembers class org.lwjgl** { *; }
-keepclassmembers class com.badlogic** { *; }
-keepclassmembers class com.esotericsoftware** { *; }
-keepclassmembers class net.java** { *; }
-keepclassmembers class com.hyperspherestudio.kryonet.Packets** {*; }

-libraryjars <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)

-keepclasseswithmembernames class * {
  native <methods>;
}

-keep public class com.HyperSphere.desktop.DesktopLauncher {
    public static void main(java.lang.String[]);
}

-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}


-keep public class com.HyperSphere.ConnectionObjects** {
    *;
}

-keepclassmembers class Connection.Objects.Packet**{
    *;
}

-keep public class com.hyperspherestudio.kryonet.Packets** {
    *;
}

