val sparkVersion = "3.0.0-preview2"

// Spark 3 is built with scala 2.12
scalaVersion := "2.12.11"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "com.thesamet.scalapb" %% "sparksql-scalapb" % "0.10.2",
  "org.scalactic" %% "scalactic" % "3.1.2",
  "org.scalatest" %% "scalatest" % "3.1.2" % "test"
)

// Hadoop contains an old protobuf runtime that is not binary compatible
// with 3.0.0.  We shared ours to prevent runtime issues.
assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("com.google.protobuf.**" -> "shadeproto.@1").inAll
)

PB.targets in Compile := Seq(scalapb.gen() -> (sourceManaged in Compile).value)
