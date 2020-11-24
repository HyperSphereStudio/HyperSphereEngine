# Game1Client
 
Downloading the client

The client can run on IntelliJ Community Edition; however, it runs far better on andriod studio. Andriod studio comes with the andriod sdk and several other features that enhance the coding experience compared to IntelliJ. Andriod studio was made by IntelliJ so the majority of the screens are the same with but with IntelliJ has less andriod features. 


Creating an apk

Build>>Build bundle/apk or generate signed bundle/apk. 

Creating a jar

Type gradlew desktop:dist to create the unobfuscated private version, than type gradlew desktop:proguard to obfuscate it to make the one used publicly. 
