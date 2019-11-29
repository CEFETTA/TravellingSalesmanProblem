
import java.util.*;

public class TesteForcaBruta {

    public static void main(String[] args) {

        // Gerador de numeros aleatorios
        Random gerador = new Random();

        // Aqui teremos um vetor com uma possivel rota de viagem
        int[] possivelRota;

        // Aqui teremos um vetor com a melhor rota de viagem
        Rota[] melhorRota;

        // Aqui teremos o numero de cidades do grafo
        int numCidades = gerador.nextInt(14);

        // Aqui teremos o melhor custo da viagem
        int melhorCusto = 0;

        // Aqui teremos uma matriz de adjacencia com o grafo aleatorio
        int[][] grafo = new int[numCidades][numCidades];
        int pesoGrafo;

        // Aqui preenchemos o grafo
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

        // Aqui printamos o grafo criado para fins de visualizacao
        System.out.println("Cidades e custos:\n");
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

        // aqui apenas instanciamos
        possivelRota = new int[numCidades];
        melhorRota = new Rota[numCidades];

        // aqui instanciamos a implementacao
        ForcaBruta algoritmoForcaBruta = new ForcaBruta();

        // aqui fazemos as chamadas
        algoritmoForcaBruta.forcaBruta(possivelRota, grafo, melhorRota, melhorCusto);
        algoritmoForcaBruta.imprimeCaminho(melhorRota);

    }

}

// Aqui iremos ter algoritmos para implementar a forca bruta
class ForcaBruta {

    // Aqui iremos verificar se a var possivelRota passada como parametro possui
    // custo mais vantajoso com o atual,
    // em caso positivo, entao iremos montar uma nova var melhorRota e um novo var
    // melhorCusto.
    private void melhorCaminho(int[][] grafo, Rota[] melhorRota, int melhorCusto, int[] possivelRota) {

        // Cidades da melhor rota
        int cidadeA, cidadeB;

        // Custo total da melhor rota
        int custoTotal = 0;

        // Armazena a sequencia de cidades
        // Indice -> cidade em questao
        // Conteudo -> prox cidade da rota
        int[] proxDaRota;

        proxDaRota = new int[melhorRota.length];

        // Aqui montamos uma rota com a possivelRota
        cidadeA = 0;
        cidadeB = possivelRota[1];
        custoTotal = grafo[cidadeA][cidadeB];
        proxDaRota[cidadeA] = cidadeB;

        for (int i = 2; i < melhorRota.length; i++) {
            cidadeA = cidadeB;
            cidadeB = possivelRota[i];
            custoTotal += grafo[cidadeA][cidadeB]; // calcula custo parcial da rota
            proxDaRota[cidadeA] = cidadeB; // armazena rota fornecida pela possivelRota
        }

        proxDaRota[cidadeB] = 0; // completa o ciclo da viagem
        custoTotal += grafo[cidadeB][0]; // custo total da rota

        // procura pelo menor custo
        if (custoTotal < melhorCusto) {
            melhorCusto = custoTotal;
            cidadeB = 0;
            for (int i = 0; i < melhorRota.length; i++) { // aqui guardo a melhor rota
                cidadeA = cidadeB;
                cidadeB = proxDaRota[cidadeA];
                melhorRota[i].cidadeA = cidadeA;
                melhorRota[i].cidadeB = cidadeB;
                melhorRota[i].custo = grafo[cidadeA][cidadeB];
            }
        }
    }

    // Aqui geramos os possiveis caminhos entre a cidade zero e as demais envolvidas
    // na busca
    // armazenando-as no vetor possivelRota, um por vez, e a cada permutacao gerada,
    // chama o metodo
    // melhor caminho em que escolhe o caminho de menor custo
    // CÓDIGO ADAPTADO DE "Algorithms in C" (Robert Sedgewick), página 624.
    private void permutacaoForcaBruta(int[] possivelRota, int[][] grafo, Rota[] melhorRota, int melhorCusto,
            int controle, int k) {

        possivelRota[k] = ++controle;

        if (controle == (melhorRota.length - 1)) { // verifica se o caminho gerado e melhor
            melhorCaminho(grafo, melhorRota, melhorCusto, possivelRota);
        } else {
            for (int i = 1; i < melhorRota.length; i++) {
                if (possivelRota[i] == 0) {
                    permutacaoForcaBruta(possivelRota, grafo, melhorRota, melhorCusto, controle, i);
                }
            }
        }
        controle--;
        possivelRota[k] = 0;
    }

    // Aqui geramos possiveis caminhos entre a cidade zero e todas as outras
    // envolvidas na rota da viagem do
    // problema caixeiro viajante e escolhemos a melhor entre todas as possiveis,
    // sempre gerando
    // uma solucao otima
    public void forcaBruta(int[] possivelRota, int[][] grafo, Rota[] melhorRota, int melhorCusto) {

        int controle = -1;
        melhorCusto = Integer.MAX_VALUE;

        // aqui criamos rotas
        for (int i = 0; i < melhorRota.length; i++) {
            melhorRota[i] = new Rota();
        }

        // gera os caminhos possiveis e escolhe o melhor, de maneira recursiva
        permutacaoForcaBruta(possivelRota, grafo, melhorRota, melhorCusto, controle, 1);
    }

    // Aqui imprimimos o melhor caminho obtido
    public void imprimeCaminho(Rota[] melhorRota) {
        int custoTotal = 0;
        System.out.println("O melhor caminho obtido: \n");
        System.out.println("          CidadeA            CidadeB             Custo ");

        for (Rota melhorRota1 : melhorRota) {
            System.out.println("              " + melhorRota1.cidadeA + "                  " + melhorRota1.cidadeB
                    + "                " + melhorRota1.custo);
            custoTotal += melhorRota1.custo;
        }
        System.out.println("\nCusto para a viagem do caxeiro viajante: " + custoTotal);
        System.out.println();
    }

}

// Aqui criaremos uma classe Rota que armazenara uma melhor rota
// funciona como se fosse uma struct
class Rota {

    public int cidadeA;
    public int cidadeB;
    public int custo;

}
