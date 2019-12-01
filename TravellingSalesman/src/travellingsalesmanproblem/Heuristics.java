package travellingsalesmanproblem;

public class Heuristics extends Graphs {

    private final int vertices;
    private final int[] verticeConhecido;
    private final int[] verticePai;
    private int custoTotal;

    public Heuristics(int vertices) {
        super(vertices);
        this.vertices = vertices;
        verticeConhecido = new int[vertices];
        verticePai = new int[vertices];
        custoTotal = 0;
    }

    private void guloso(int origem, int verticeInicial) {
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
            custoTotal += super.getPeso(verticeInicial, proxVertice);
            guloso(origem, proxVertice);
        } else {
            custoTotal += super.getPeso(verticeInicial, origem);
        }
    }

    public void iniciaGuloso(int cidadeInicial) {
        for (int i = 0; i < vertices; i++) {
            verticeConhecido[i] = 0;
            verticePai[i] = -1;
        }
        custoTotal = 0;

        guloso(cidadeInicial, cidadeInicial);
    }

    public int[] getVerticePai() {
        return verticePai;
    }
    
    public int getCustoTotal() {
        return custoTotal;
    }
    
}
