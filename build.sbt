name := "my_notification_service"

version := "1.0.0"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
	guice,
	"javax.mail" % "mail" % "1.4",
	"org.projectlombok" % "lombok" % "1.18.12" % "provided",
	"com.fasterxml.jackson.core" % "jackson-databind" % "2.10.3",
	"com.fasterxml.jackson.core" % "jackson-core" % "2.10.3"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)