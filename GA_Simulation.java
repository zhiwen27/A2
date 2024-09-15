import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {

  // Use the instructions to identify the class variables, constructors, and methods you need
  // The name of these parameters are inherited from instructions.
  private int n; // the number of individuals in each generation
  private int k; // the number of winners in each generation
  private int r; // the number of rounds of evolution to run
  private int c_0; // the initial chromosome size
  private int c_max; // the maximum chromosome size
  private double m; // chance per round of a mutation in each gene
  private int num_letters; // number of states possible per gene (g)

  ArrayList<Individual> firstGeneration; // the ArrayList to store the first generation

  /**
   * Constructor for individual class
   * @param n the number of individuals in each generation
   * @param k the number of winners in each generation
   * @param r the number of rounds of evolution to run
   * @param c_0 the initial chromosome size
   * @param c_max the maximum chromosome size
   * @param m chance per round of a mutation in each gene
   * @param num_letters number of states possible per gene
   */
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
        Comparator<Individual> ranker = new Comparator<>() {
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
  public ArrayList<Individual> evolvePopulation(ArrayList<Individual> gen){
    // first rank the generation and select the winners
    this.rankPopulation(gen);
    ArrayList<Individual> selectGen = new ArrayList<Individual>(k);
    ArrayList<Individual> newGen = new ArrayList<Individual>(n);
    for(int i = 0; i < k; i++){
      selectGen.add(gen.get(i));
    }
    System.err.println("Winners:");
    for(Individual i: selectGen){
      System.err.println(i);
    }
    // randomly select two parents and create a new individual
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

  /**
   * Describe the generation by showing the fitness of the fittest individual, the kth individual, and the least fit individual
   * along with the actual chromosome of the those individuals.
   * @param gen the generation you want to describe
   */
  public void describeGeneration(ArrayList<Individual> gen){
    this.rankPopulation(gen); // will rank the population first inside describeGeneration
    System.err.println("The fitness of the fittest individual is: " + gen.getFirst().getFitness() + ", with its actual chromosome to be: " + gen.getFirst());
    System.err.println("The fitness of the kth individual is: " + gen.get(k - 1).getFitness() + ", with its actual chromosome to be: " + gen.get(k - 1));
    System.err.println("The fitness of the least fit individual is: " + gen.getLast().getFitness() + ", with its actual chromosome to be: " + gen.getLast());
  }

  /**
   * Run the entire experiment by initializing the population, then rank it and describe it.
   * For each round, it will evolve the population, rank it, and describe it.
   */
  public void run(){
    // initialize the first generation and do the description
    this.init();
    System.err.println("Generation 1:");
    for(Individual i: this.firstGeneration){
      System.err.println(i);
    }
    this.describeGeneration(this.firstGeneration);
    // evolve the first generation
    ArrayList<Individual> temp = new ArrayList<Individual>(n);
    ArrayList<Individual> newGen = new ArrayList<Individual>(n);
    temp = this.evolvePopulation(this.firstGeneration);
    for(int i = 0; i < r; i++){
      System.err.println("Generation " + (i + 2) + ":");
      newGen = temp;
      System.err.println("New Generation:");
      for(Individual j: newGen){
        System.err.println(j);
      }
      this.describeGeneration(newGen);
      temp = this.evolvePopulation(newGen);
    }
  }
  public static void main(String[] args) {
    GA_Simulation test = new GA_Simulation(100, 15, 100, 8, 20, 0.01, 5);
    //GA_Simulation test = new GA_Simulation(5, 5, 5, 3, 5, 0.01, 3);
    test.run();
  }
}