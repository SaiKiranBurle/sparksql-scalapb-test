import org.apache.spark.sql.SparkSession
import org.scalatest._
import scalapb.docs.person.Person
import scalapb.docs.person.Person._

class ProtoTest extends FunSuite {

  test("Spark ProtoBuf DataFrame") {

    val spark: SparkSession = SparkSession
      .builder()
      .appName("Proto Test")
      .master("local")
      .getOrCreate()
    System.out.println("Spark version: " + spark.version)

    import scalapb.spark.Implicits._
    val ints = Seq("one", "two", "three", "four")
    val persons = spark
      .createDataFrame(ints.map(Tuple1(_)))
      .withColumnRenamed("_1", "person")
    persons.persist()
    persons.show()

    val personDS =
      persons.map(e => Person().withName(e.getAs[String]("person")))
    personDS.persist
    personDS.show

    assert(personDS.count() == persons.count)
  }

}
