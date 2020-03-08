package cs3500.excellence;

import java.util.ArrayList;

abstract class AbstractShape {

  //Push test
  ShapeType type;
  final ArrayList<Motion> motions;


  //TODOluke decide access level of this constructor
  protected AbstractShape(ShapeType type) {
    this.type = type;
    motions = new ArrayList<Motion>();

  }

  // Branch demonstration
  // Imagine this was a ton of crazy changes
  // that may affect files we both are working on




}
