package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s = union(s1,s2)
    val t = union(s2,s3)
    val p = (e: Int) => e < 3
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton 1")
      assert(contains(s2, 2), "Singleton 2")
      assert(contains(s3, 3), "Singleton 3")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("interset contains the elements that exists in both sets") {
    new TestSets {
      val r = intersect(s,t)
      assert(contains(r,2))
      assert(!contains(r,1))
      assert(!contains(r,3))
      assert(!contains(intersect(s,s3), 1))
      assert(!contains(intersect(s,s3), 2))
      assert(!contains(intersect(s,s3), 3))
    }
  }

  test("diff contains elements from only in s but not t") {
    new TestSets {
      val r = diff(s,t)
      assert(contains(r,1))
      assert(!contains(r,2))
      assert(!contains(r,3))
    }
  }
  
  test("filter contains elements satisfying an additional condition") { 
    new TestSets {
      val fs = filter(s,p)
      val ft = filter(t,p)

      assert(contains(fs,1))
      assert(contains(fs,2))
      assert(contains(ft,2))
      assert(!contains(ft,3))
    }
  }
  
  test("forall returns if all bounded integers within Set s satisfies condition p") {
    new TestSets {
      assert(forall(s,p))
      assert(!forall(t,p))
      
      val a = union(singletonSet(1001),s)
      assert(forall(a,p))

      val b = union(singletonSet(-1001),s)
      assert(forall(b, (_ > 0)))

    }
  }

  test("exists returns if a single bounded integer exists in a set that satisfies p") {
    new TestSets {
      assert(exists(s,p), "exists 1")
      assert(exists(t,p), "exists 2")
      assert(!exists(s,(_ > 10)), "exists 3") 
    }
  }

  test("map transforms all elements in the set") {
    new TestSets {
      val f = (x:Int) => x + 1
      val r = map(s,f)
      assert(!contains(r,1))
      assert(contains(r,2))
      assert(contains(r,3))
    }
  }


}
