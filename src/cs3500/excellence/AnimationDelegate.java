package cs3500.excellence;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copy-Safe interface for affording "read only" access to potential views.
 */
public interface AnimationDelegate<T, K> {

  /**
   * Method to retrieve the current state of the animation, represented as a String.
   */
  String getStringAnimation();

  /**
   * Method to retrieve a COPY-SAFE representation of the state of the animation, represented as a
   * HashMap of Object names mapped to generic types.
   *
   * <p>All objects that implement this interface should only pass immutable or copy-safe
   * types via this method.
   */
  HashMap<String, T> retrieveCurrentGameState();

  /**
   * Retrieve the K associated with the object correlating with the NON-NULL String name.
   *
   * @param name a non-null String representing the name to be associated with the new shape.
   * @throws IllegalArgumentException if the provided String is null, or the name is not associated
   *                                  with a Shape.
   */
  ArrayList<K> retrieveMotionsForObjectWithName(String name);

  // TODOluke javadoc
  Bounds retrieveCanvasBoundaries();

}
