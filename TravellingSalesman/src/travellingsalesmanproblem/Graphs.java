package travellingsalesmanproblem;

public class Graphs {

    protected final int numeroVertices;
    protected final int[][] matrizAdjacencia;

    public Graphs(int vertices) {
        numeroVertices = vertices;
        matrizAdjacencia = new int[vertices][vertices];
        int i, j;

        for (i = 0; i < numeroVertices; i++) {
            for (j = 0; j < numeroVertices; j++) {
                matrizAdjacencia[i][j] = 0;
            }
        }
    }
    
    public void insereAresta(int vertice1, int vertice2, int peso) {
        matrizAdjacencia[vertice1][vertice2] = peso;
    }
    
    public boolean existeAresta(int vertice1, int vertice2) {
        return matrizAdjacencia[vertice1][vertice2] != 0;
    }

    public void insereArestaNaoOrientada(int vertice1, int vertice2, int peso) {
        matrizAdjacencia[vertice1][vertice2] = peso;
        matrizAdjacencia[vertice2][vertice1] = peso;
    }
    
    public void setPeso(int vertice1, int vertice2, int peso) {
        matrizAdjacencia[vertice1][vertice2] = peso;
    }

    public int getPeso(int vertice1, int vertice2) {
        return matrizAdjacencia[vertice1][vertice2];
    }
    
    public int getNumVertices(){
        return numeroVertices;
    }
}
