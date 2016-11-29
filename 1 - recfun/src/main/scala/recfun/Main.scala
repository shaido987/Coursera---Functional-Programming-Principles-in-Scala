package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    println(balance(List('(','(',')',')',')')))
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = (c,r) match{
      case (0,_)           => 1
      case (c,r) if (c==r) => 1
      case (c,r)           => pascal(c-1,r-1) + pascal(c,r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      val r = chars.filter(c => c == '(' || c == ')').scanLeft(0)((r,c) => if (c == '(') r+1 else r-1)
      !r.contains(-1)
    }

    def balanceRec(chars: List[Char]): Boolean = balanceRecHelp(chars, 0)
    def balanceRecHelp(chars: List[Char], count: Int):Boolean = (chars, count) match {
      case (cs,0) if cs.isEmpty => true
      case (cs,_) if cs.isEmpty => false
      case (cs,c) => cs.head match {
        case '(' => balanceRecHelp(cs.tail,c+1)
        case ')' if c > 0 => balanceRecHelp(cs.tail,c-1)
        case ')' => false
        case _   => balanceRecHelp(cs.tail,c)
      }
    }

  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = (money,coins) match {
      case (0,_)                => 1
      case (m,_)  if m < 0      => 0
      case (_,cs) if cs.isEmpty => 0
      case (m,cs) => countChange(m - cs.head, cs) + countChange(m,cs.tail)
    }
  }
