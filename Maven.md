Since this software is nowhere near ready for release, it is not installed into the central maven repository.

Also, at the time of writing the Echo3 betas are not installed there either.

If you wish to use this in a maven project, then you will have to manually install the following jars from the distribution:
  * src/webapp/WEB-INF/lib/Echo3\_App.jar
  * src/webapp/WEB-INF/lib/Echo3\_WebContainer.jar
  * build/echo3ext20.jar

Suggested commands for doing this are as follows:

```
mvn install:install-file -DgroupId=org.sgodden -DartifactId=echo3ext20 -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=echo3ext20.jar
mvn install:install-file -DgroupId=com.nextapp -DartifactId=echo3-app -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=Echo3_App.jar
mvn install:install-file -DgroupId=com.nextapp -DartifactId=echo3-webcontainer -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=Echo3_WebContainer.jar
```

N.B. - once NextApp do actually put the artifacts into the central repository, there's no guarantee that they will follow the names I have used above, so things may change then.

And then add the following dependencies to your pom:
```
<dependency>
  <groupId>com.nextapp</groupId>
  <artifactId>echo3-app</artifactId>
  <version>1.0-SNAPSHOT</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>com.nextapp</groupId>
  <artifactId>echo3-webcontainer</artifactId>
  <version>1.0-SNAPSHOT</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.sgodden</groupId>
  <artifactId>echo3ext20</artifactId>
  <version>1.0-SNAPSHOT</version>
  <scope>compile</scope>
</dependency>
```