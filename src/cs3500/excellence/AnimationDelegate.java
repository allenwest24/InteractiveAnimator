package cs3500.excellence;

import java.util.ArrayList;
import java.util.HashMap;

// Comments needed here TODOluke
public interface AnimationDelegate<T, K> {

  String getStringAnimation();

  // ESSENTIALTODO -> JUSTIFY THAT THIS IS SAFE IN JAVADOC
  // COPIES OF STRING NAME, COPY OF HASHMAP, COPY OF SHAPE ARRAY
  // SHAPES AND MOTIONS ARE COMPLETELY IMMUTABLE + SUBCLASSABLE (Note that in desc)
  HashMap<String, T> retrieveCurrentGameState();


  // Throws for bad name
  ArrayList<K> retrieveMotionsForObjectWithName(String name);



}
