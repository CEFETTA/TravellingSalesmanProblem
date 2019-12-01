package travellingsalesmanproblem;

public class Heuristics extends Graphs {

    private final int vertices;
    private final int[] verticeConhecido;
    private final int[] verticePai;

    public Heuristics(int vertices) {
        super(vertices);
        this.vertices = vertices;
        verticeConhecido = new int[vertices];
        verticePai = new int[vertices];
    }
    
    

    private void guloso(int verticeInicial) {
        int peso = Integer.MAX_VALUE;
        int proxVertice = verticeInicial;
        verticeConhecido[verticeInicial] = 1;
        for (int i = 0; i < vertices; i++) {
            if (peso > super.getPeso(verticeInicial, i) && i != verticeInicial && verticeConhecido[i] == 0) {
                proxVertice = i;
                peso = super.getPeso(verticeInicial, i);
            }
        }
        
        if (proxVertice != verticeInicial) {
            verticePai[proxVertice] = verticeInicial;
            guloso(proxVertice);
        }
    }

    public void iniciaGuloso(int cidadeInicial) {
        for (int i = 0; i < vertices; i++) {
            verticeConhecido[i] = 0;
            verticePai[i] = -1;
        }

        guloso(cidadeInicial);
    }

    public int[] getVerticePai() {
        return verticePai;
    }
}
