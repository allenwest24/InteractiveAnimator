package cs3500.excellence;

import java.util.ArrayList;

abstract class AbstractShape {

  ShapeType type;
  final ArrayList<Motion> motions;


  //TODOluke decide access level of this constructor
  protected AbstractShape(ShapeType type) {
    this.type = type;
    motions = new ArrayList<Motion>();

  }




}
