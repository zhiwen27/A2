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
  private int num_letters;

  ArrayList<Individual> generation;

  public GA_Simulation(){
    this.n = 100;
    this.k = 15;
    this.r = 100;
    this.c_0 = 8;
    this.c_max = 20;
    this.m = 0.01;
    this.g = 5;
    this.generation = new ArrayList<Individual>(n);
  }
  
  public void init(int num_letters){
    this.num_letters = num_letters;
    for(int i = 0; i < this.n; i++){
      Individual newGenTemp = new Individual(this.c_0, num_letters);
      this.generation.add(newGenTemp);
    }
  }

  /** Sorts population by fitness score, best first 
   * @param pop: ArrayList of Individuals in the current generation
   * @return: ArrayList of Individuals sorted by fitness
  */
    public void rankPopulation(ArrayList<Individual> pop) {
        // sort population by fitness
        Comparator<Individual> ranker = new Comparator<>() { // ❗️❗️
          // this order will sort higher scores at the front
          public int compare(Individual c1, Individual c2) {
            return (int)Math.signum(c2.getFitness()-c1.getFitness());
          }
        };
        pop.sort(ranker); 
      }

  /**
   * Evolve the population
   * @return return the created new generation
   */
  public ArrayList<Individual> evolvePopulation(){
    this.rankPopulation(this.generation);
    ArrayList<Individual> selectGen = new ArrayList<Individual>(k);
    ArrayList<Individual> newGen = new ArrayList<Individual>(n);
    for(int i = 0; i < k; i++){
      selectGen.add(this.generation.get(i));
    }
    for(int i = 0; i < n; i++){
      int parentIndex1 = ThreadLocalRandom.current().nextInt(0, k);
      int parentIndex2 = ThreadLocalRandom.current().nextInt(0, k);
      while(parentIndex1 == parentIndex2){
        parentIndex2 = ThreadLocalRandom.current().nextInt(0, k);
      }
      Individual newGenTemp = new Individual(selectGen.get(parentIndex1), selectGen.get(parentIndex2), this.c_max, this.m, this.num_letters);
      newGen.add(newGenTemp);
    }
    return newGen;
  }
}