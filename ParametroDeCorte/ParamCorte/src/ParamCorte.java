
import java.util.*;

public class ParamCorte {

    public static void main(String[] args) {

        //Aqui definimos o parametro de corte
        Scanner scan = new Scanner(System.in);
        System.out.println("Qual o valor de corte?");
        int cut = scan.nextInt();

        //Aqui instanciamos de maneira automatica
        ForcaBruta forcaBrutaN[] = new ForcaBruta[15];

        //Aqui instanciamos o forca bruta de acordo com o numero de cidades
        for (int i = 0; i < 15; i++) {
            forcaBrutaN[i] = new ForcaBruta(i, cut);
        }

        //Aqui alteramos qual instancia queremos testar
        int n = 13;

        long inicio = System.currentTimeMillis();

        //Aqui fazemos as chamadas dos algoritmos
        forcaBrutaN[n].AlgoritmoForcaBruta();
        forcaBrutaN[n].imprimeGrafo();
        forcaBrutaN[n].imprimeMelhorRota();

        long fim = System.currentTimeMillis();

        System.out.println("\nTempo em Ms: " + (fim - inicio));
        System.out.println("");

    }

}

//Aqui iremos ter uma classe que contem uma instancia que executara o
//algoritmo de forca bruta
class ForcaBruta {

    //Aqui temos o numero de cidades do algoritmo
    private int numCidades;

    //Aqui temos o grafo em questao representado por uma matriz de adjacencia
    private int[][] grafo;

    //Aqui temos uma possivel rota
    private int[] possivelRota;

    //Aqui temos a melhor rota encontrada
    private int[] melhorRota;

    //Aqui armazenamos quais ja foram explorados
    private int[] cidadesExploradas;

    //Aqui guardamos o nivel da busca em profundidade
    //se o nivel for igual ao numCidades, temos uma possivel rota
    private int nivelDFS;

    //Aqui guardamos o menor custo do caminho
    private int menorCusto;

    //Aqui guardamos o valor objetivo
    private int cut;

    //Construtor onde inicializamos todas variaveis
    public ForcaBruta(int numCidades, int cut) {

        this.cut = cut;

        this.numCidades = numCidades;
        this.grafo = new int [numCidades][numCidades];
        this.possivelRota = new int[numCidades];
        this.cidadesExploradas = new int[numCidades];
        this.melhorRota = new int[numCidades];
        this.nivelDFS = 0;
        this.menorCusto = Integer.MAX_VALUE;

        for (int i = 0; i < numCidades; i++) {
            cidadesExploradas[i] = 0;
        }

        preencheGrafo(grafo);

    }

    //Apenas uma chamada
    public void AlgoritmoForcaBruta(){
        RealizaAlgoritmoforcaBruta(0, 0);
    }

    //Aqui implementamos um algoritmo de busca em profundidade
    //para implementar a forca bruta onde todos os possiveis caminhos
    //sao testados
    private boolean RealizaAlgoritmoforcaBruta(int verticeInicial, int nivelDFS) {

        int custoCiclo;
        cidadesExploradas[verticeInicial] = 1;
        possivelRota[nivelDFS] = verticeInicial;

        //Aqui terminamos uma possivel rota, iremos testa-la
        if (nivelDFS == (numCidades - 1)) {
            custoCiclo = verificaCustoCiclo(possivelRota);
            if (custoCiclo < menorCusto) {
                menorCusto = custoCiclo;
                for (int j = 0; j < numCidades; j++) {
                    melhorRota[j] = possivelRota[j];
                }
            }
            if(custoCiclo <= this.cut){
                menorCusto = custoCiclo;
                for (int j = 0; j < numCidades; j++) {
                    melhorRota[j] = possivelRota[j];
                }
                return true;
            }
        }

        //Aqui chamamos de maneira recursiva o algoritmo
        //aumentamos o nivel de DFS e possibilitamos uma cidade ser
        //explorada novamente
        for (int i = 0; i < numCidades; i++) {
            if (cidadesExploradas[i] != 1) {
                if(RealizaAlgoritmoforcaBruta(i, nivelDFS + 1)){
                    return true;
                }
                RealizaAlgoritmoforcaBruta(i, nivelDFS + 1);
                cidadesExploradas[i] = 0;
            }
        }

        return false;
    }

    //Aqui medimos o custo de um ciclo qualquer
    //o vetor rota ciclo armazena os indices das cidades em questao
    //que formam uma possivel rota
    private int verificaCustoCiclo(int[] rotaCiclo) {
        int custoCiclo = 0;

        for (int i = 0; i < numCidades - 1; i++) {
            custoCiclo = custoCiclo + grafo[rotaCiclo[i]][rotaCiclo[i + 1]];
        }
        custoCiclo = custoCiclo + grafo[rotaCiclo[numCidades - 1]][rotaCiclo[0]];

        return custoCiclo;
    }

    //Aqui imprimimos o melhor caminho obtido
    public void imprimeMelhorRota() {
        System.out.print("Melhor Rota: ");
        for (int i = 0; i < numCidades; i++) {
            if (i > 0) {
                System.out.print("-");
            }
            System.out.print(melhorRota[i]);
        }
        System.out.println("-0");
    }

    //Aqui imprimimos o grafo para fins de visualizacao
    public void imprimeGrafo(){
        System.out.println("Cidades e custos:");
        for (int i = 0; i < numCidades; i++) {
            System.out.print("  " + i);
        }
        System.out.println();
        for (int i = 0; i < numCidades; i++) {
            System.out.print("" + i);
            for (int j = 0; j < numCidades; j++) {
                System.out.print(" " + grafo[i][j]);
            }
            System.out.println("\n");
        }
    }


    //Aqui preenchemos um grafo passado como parametro
    public void preencheGrafo(int [][] grafo) {

        //Gerador de numeros aleatorios
        Random gerador = new Random();

        int pesoGrafo;

        for (int i = 0; i < numCidades; i++) {
            for (int j = 0; j < numCidades; j++) {
                pesoGrafo = gerador.nextInt(60);
                if (i < j) {
                    grafo[i][j] = pesoGrafo;
                } else if (i == j) {
                    grafo[i][j] = 0;
                } else {
                    grafo[i][j] = grafo[j][i];
                }
            }
        }
    }

}
