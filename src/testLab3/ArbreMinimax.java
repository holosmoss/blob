package testLab3;

public class ArbreMinimax {

	/**
	(* the minimax value of n, searched to depth d *)
	 fun minimax(n: node, d: int): int =
	   if leaf(n) or depth=0 return evaluate(n)
	   if n is a max node
	      v := L
	      for each child of n
	         v' := minimax (child,d-1)
	         if v' > v, v:= v'
	      return v
	   if n is a min node
	      v := W
	      for each child of n
	         v' := minimax (child,d-1)
	         if v' < v, v:= v'
	      return v
	   **/
}
