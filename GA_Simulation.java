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
  private int num_letters; // number of states possible per gene

  ArrayList<Individual> firstGeneration;

  public GA_Simulation(int n, int k, int r, int c_0, int c_max, double m, int num_letters){
    this.n = n;
    this.k = k;
    this.r = r;
    this.c_0 = c_0;
    this.c_max = c_max;
    this.m = m;
    this.num_letters = num_letters;
    this.firstGeneration = new ArrayList<Individual>(n);
  }
  
  /**
   * Initialize the first generation
   */
  public void init(){
    for(int i = 0; i < this.n; i++){
      Individual newGenTemp = new Individual(this.c_0, this.num_letters);
      this.firstGeneration.add(newGenTemp);
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
    this.rankPopulation(this.firstGeneration);
    ArrayList<Individual> selectGen = new ArrayList<Individual>(k);
    ArrayList<Individual> newGen = new ArrayList<Individual>(n);
    for(int i = 0; i < k; i++){
      selectGen.add(this.firstGeneration.get(i));
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

  public void describeGeneration(ArrayList<Individual> gen){
    this.rankPopulation(gen);
    System.err.println("The fitness of the fittest individual is: " + gen.getFirst().getFitness() + " with its actual chromosome to be: " + gen.getFirst());
    System.err.println("The kth individual with its actual chromosome to be: " + gen.get(k - 1)); // do we need to print this?
    System.err.println("The least fit individual with its actual chromosome to be: " + gen.getLast());
  }

  public void run(){
    this.init();
    this.describeGeneration(this.firstGeneration);
    for(int i = 0; i < r; i++){
      this.describeGeneration(this.evolvePopulation());
    }
  }
  public static void main(String[] args) {
    GA_Simulation test = new GA_Simulation(100, 15, 20, 8, 10, 0.01, 5);
    test.run();
  }
}