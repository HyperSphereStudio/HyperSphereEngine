# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-verbose
-optimizationpasses 5
-verbose
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,EnclosingMethod
-repackageclasses ''


-dontwarn android.support.**
-dontwarn com.esotericsoftware.**
-dontwarn org.objenesis.**
-dontwarn org.apache.**
-dontwarn com.fasterxml.**
-dontwarn net.java.**
-dontwarn com.badlogic.**

-keep class com.badlogic**
-keep class com.esotericsoftware**
-keep class net.java**
-keep class com.hyperspherestudio.kryonet.Packets**

-keepclassmembers class com.badlogic** { *; }
-keepclassmembers class com.esotericsoftware** { *; }
-keepclassmembers class net.java** { *; }
-keepclassmembers class com.hyperspherestudio.kryonet.Packets** {*; }


-keepclasseswithmembernames class * {
  native <methods>;
}

-keepclassmembers class com.badlogic.gdx.backends.android.AndroidInput* {
   <init>(com.badlogic.gdx.Application, android.content.Context, java.lang.Object, com.badlogic.gdx.backends.android.AndroidApplicationConfiguration);
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



