package Triglav.cat3

/** 3-category, and function composition ()
  *   - functions (0-cells, objects)
  *   - functions between functions (1-morphisms)
  *   - functions between functions with the same domain and codomain between
  *     functions (2-morphism)
  *
  * See Tom Leinster [An introduction to
  * n-categories](https://www.youtube.com/watch?v=6bnU7_6CNa0)
  */
object Cat3Functions {

  type Obj[A, B] = A => B

  //  A   ------>   B
  // (A=>AA) => (B=>BB)
  type Morphism1[A, AA, B, BB] = (A => AA) => (B => BB)
  def identityMorphism1[A, B]: Morphism1[A, B, A, B] = identity[A => B]

  //          f
  //     A  -----> B
  //          ||
  //          ||  α
  //          ||
  //          \/
  //     A  -----> B
  //          g
  //
  //                      α
  //         ( f     ---------->      g )
  //   ( A   --->     B )        ( A --->  B )
  // ((A=>AA) => (B=>BB)) => ((A=>AA) => (B=>BB))
  type Morphism2[A, AA, B, BB] =
    Morphism1[A, AA, B, BB] => Morphism1[A, AA, B, BB]
  def identityMorphism2[A, B]: Morphism2[A, B, A, B] =
    identity[(A => B) => (A => B)]

  //                 f
  //     A  --------------------> B
  //          ||            ||
  //          ||      Γ     ||
  //        α || ========>  || β
  //          ||            ||
  //          \/            \/
  //     A  --------------------> B
  //               g
  //
  //                                           Γ
  //                      α       ----------------------------->             β
  //         ( f                      g )                      ( f                      g )
  //   ( A   --->     B )        ( A --->  B )          ( A   --->     B )        ( A --->  B )
  // ((A=>AA) => (B=>BB)) => ((A=>AA) => (B=>BB))     ((A=>AA) => (B=>BB)) => ((A=>AA) => (B=>BB))
  type Morphism3[A, AA, B, BB] =
    Morphism2[A, AA, B, BB] => Morphism2[A, AA, B, BB]

  //            f          ---->           g                    g*f
  //      ( A --->  B )     =>       ( B   -->  C )   =>    ( A  => C )
  // ( (A=>AA) => (B=>BB) ) => ( (B=>BB) => (C=>CC) ) => ( (A=>AA) => (C=>CC) )
  def morphism1Compose[A, AA, B, BB, C, CC]
      : Morphism1[A, AA, B, BB] => Morphism1[B, BB, C, CC] => Morphism1[
        A,
        AA,
        C,
        CC
      ] =
    f => g => f andThen g

  //           f                      f
  //     A  ------> B            A  ------> B
  //          ||                      ||
  //        α ||                      ||
  //          ||                      ||
  //          \/                      ||
  //          g                       ||
  //     A  -----> B             β*α  ||
  //          ||                      ||
  //        β ||                      ||
  //          ||                      ||
  //          \/                      \/
  //     A  -----> B             A  -----> B
  //          h                       h

  //                      α                        β                                         β*α
  //           f                         g                           h                        f                       h
  //      ( A        B )           ( A         B )              A         B                 A       B               A         B
  // ( (A=>AA) => (B=>BB) ) => ( (A=>AA) => (B=>BB) )  =>  ( (A=>AA) => (B=>BB) )  => ( (A=>AA) => (B=>BB) ) => ( (A=>AA) => (B=>BB) )
  def morphism2VerticalComposeLeft[A, AA, B, BB]
      : Morphism2[A, AA, B, BB] => Morphism2[A, AA, B, BB] => Morphism2[
        A,
        AA,
        B,
        BB
      ] =
    alpha => _ => alpha

  def morphism2VerticalComposeRight[A, AA, B, BB]
      : Morphism2[A, AA, B, BB] => Morphism2[A, AA, B, BB] => Morphism2[
        A,
        AA,
        B,
        BB
      ] =
    _ => beta => beta

  def morphism2VerticalComposeId[A, AA, B, BB]
      : Morphism2[A, AA, B, BB] => Morphism2[A, AA, B, BB] => Morphism2[
        A,
        AA,
        B,
        BB
      ] =
    _ => _ => identity

  //          f         f2                  f2*f
  //     A  -----> B  -----> C          A  -----> C
  //          ||        ||                   ||
  //          || α      || β                 || β*α
  //          ||        ||                   ||
  //          \/        \/                   \/
  //     A  -----> B  -----> C          A  -----> C
  //          g         g2                  g2*g
  def morphism2HorizontalCopose[A, AA, B, BB, C, CC]
      : Morphism2[A, AA, B, BB] => Morphism2[B, BB, C, CC] => Morphism2[
        A,
        AA,
        C,
        CC
      ] =
    _ => _ => identity

  def morphism3VerticalCompose[A, AA, B, BB]
      : Morphism3[A, AA, B, BB] => Morphism3[A, AA, B, BB] => Morphism3[
        A,
        AA,
        B,
        BB
      ] =
    _ => _ => identity

  def morphism3HorizontalCopose[A, AA, B, BB, C, CC]
      : Morphism3[A, AA, B, BB] => Morphism3[B, BB, C, CC] => Morphism3[
        A,
        AA,
        C,
        CC
      ] =
    _ => _ => identity

  // loooks like at some point morphisms collapse into identity
}
