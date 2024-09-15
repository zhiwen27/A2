import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Individual {

    /**
     * Chromosome stores the individual's genetic data as an ArrayList of characters
     * Each character represents a gene
     */
    ArrayList<Character> chromosome;
    
    /**
     * Chooses a letter at random, in the range from A to the number of states indicated
     * @param num_letters The number of letters available to choose from (number of possible states)
     * @return The letter as a Character
     */
    private Character randomLetter(int num_letters) {
        return Character.valueOf((char)(65+ThreadLocalRandom.current().nextInt(num_letters)));
      }
    
    /** 
     * Method to determine whether a given gene will mutate based on the parameter ***m***
     * @param m the mutation rate
     * @return true if a number randomly chosen between 0 and 1 is less than ***m***, else false
    */
    private Boolean doesMutate(double m){
        double randomNum = ThreadLocalRandom.current().nextDouble(0, 1);
        return randomNum < m;
    }

    /**
     * Expresses the individual's chromosome as a String, for display purposes
     * @return The chromosome as a String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
          builder.append(ch);
        }
        return builder.toString();
      }

    /** 
     * Inital constructor to generate initial population members
     * @param c_0 The initial chromosome size
     * @param num_letters The number of letters available to choose from
     */
    public Individual(int c_0, int num_letters) {
      this.chromosome = new ArrayList<Character>(c_0);
      for(int i = 0; i < c_0; i++){
        this.chromosome.add(this.randomLetter(num_letters));
      }
    }

     /**
      * Second constructor to create parents and offspring chromosomes
      * @param parent1 The first parent chromosome
      * @param parent2 The second parent chromosome
      * @param c_max The maximum chromosome size
      * @param m The chances per round of mutation in each gene
      */
    public Individual(Individual parent1, Individual parent2, int c_max, double m, int num_letters) {

      this.chromosome = new ArrayList<Character>();

      int prefix = ThreadLocalRandom.current().nextInt(1, parent1.chromosome.size() + 1);
      int suffix = ThreadLocalRandom.current().nextInt(1, parent2.chromosome.size() + 1);
      
      for(int i = 0; i < prefix; i++){
        this.chromosome.add(parent1.chromosome.get(i));
      }
      
      for(int i = parent2.chromosome.size() - suffix; i < parent2.chromosome.size(); i++){
        this.chromosome.add(parent2.chromosome.get(i));
      }

      // Remove the elements that are longer than c_max
      if (this.chromosome.size() > c_max){
        while (this.chromosome.size() > c_max){
          this.chromosome.removeLast();
        }
      }
      
      // Do mutation
      for(int i = 0; i < this.chromosome.size(); i++){
        if (this.doesMutate(m)){
          this.chromosome.set(i, this.randomLetter(num_letters));
        }
      }
    }


    /**
     * Calculates the fitness score of each chromosome
     * @return the fitness score as an int
     */
    public int getFitness() {
      int score = 0;
      // Create a deep copy of chromosome to avoid changing the original gene
      ArrayList<Character> chromosomeCopy = new ArrayList<Character>();
      for(int i = 0; i < this.chromosome.size(); i++){
        chromosomeCopy.add(this.chromosome.get(i));
      }
      // Do the fitness score according to the given rule
      if (chromosomeCopy.size()%2 == 0){
        for(int i = 0; i < chromosomeCopy.size() / 2; i++){
          if (chromosomeCopy.get(i).equals(chromosomeCopy.get(chromosomeCopy.size() - i - 1))){
            score += 1;
          }
          else{
            score -= 1;
          }
        }
      }
      else{
        chromosomeCopy.add((chromosomeCopy.size() - 1) / 2, chromosomeCopy.get((chromosomeCopy.size() - 1) / 2));
        for(int i = 0; i < chromosomeCopy.size() / 2; i++){
          if (chromosomeCopy.get(i).equals(chromosomeCopy.get(chromosomeCopy.size() - i - 1))){
            score += 1;
          }
          else{
            score -= 1;
          }
        }
      }
      for (int i = 0; i < chromosomeCopy.size() - 1; i++){
        if (chromosomeCopy.get(i).equals(chromosomeCopy.get(i + 1))){
          score -= 1;
        }
      }
      return score;
    }

    /*
    public static void main(String[] args) {
      Individual a = new Individual(3, 3);
      System.err.println(a.getFitness());
    }
    */
}