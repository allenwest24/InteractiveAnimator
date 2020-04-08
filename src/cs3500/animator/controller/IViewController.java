package cs3500.animator.controller;

/**
 * This is an interface to represent the mandatory functionality of a view controller.
 * A view controller serves to facilitate the interaction between views and a model.
 */
public interface IViewController {

  /**
   * This method is used to turn over control of the program to this view controller such that
   * it can facilitate the interaction between views and a model.
   */
  void startUp();
}
