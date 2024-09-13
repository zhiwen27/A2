import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {

  // Use the instructions to identify the class variables, constructors, and methods you need
  private int n; // the number of individuals in each generation
  private int k; // the number of winners in each generation
  private int r; // the number of rounds of evolution to run
  private int c_0; // the initial chromosome size
  private int c_max; // the maximum chromosome size
  private double m; // chance per round of a mutation in each gene
  private int g; // number of states possible per gene

  public GA_Simulation(){
    this.n = 100;
    this.k = 15;
    this.r = 100;
    this.c_0 = 8;
    this.c_max = 20;
    this.m = 0.01;
    this.g = 5;
  }
  
  public void init(){

  }
  /** Sorts population by fitness score, best first 
   * @param pop: ArrayList of Individuals in the current generation
   * @return: ArrayList of Individuals sorted by fitness
  */
    public void rankPopulation(ArrayList<Individual> pop) {
        // sort population by fitness
        Comparator<Individual> ranker = new Comparator<>() {
          // this order will sort higher scores at the front
          public int compare(Individual c1, Individual c2) {
            return (int)Math.signum(c2.getFitness()-c1.getFitness());
          }
        };
        pop.sort(ranker); 
      }
}