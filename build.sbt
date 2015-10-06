name := "firstProject"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  jdbc,
  "mysql" % "mysql-connector-java" % "5.1.36"
)     

play.Project.playJavaSettings
