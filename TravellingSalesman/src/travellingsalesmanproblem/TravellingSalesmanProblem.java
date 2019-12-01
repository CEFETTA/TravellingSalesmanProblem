package travellingsalesmanproblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TravellingSalesmanProblem {

    public static void main(String[] args) {
        Heuristics graph = readArq(args[0]);
        int cidadeInicial = 0;
        graph.iniciaGuloso(cidadeInicial);

        int[] caminho = graph.getVerticePai();

        System.out.println("Vertice Pai:");
        for (int i = 0; i < caminho.length; i++) {
            System.out.print(caminho[i] + " ");
        }
        System.out.println("");
        
        int custoTotal = graph.getCustoTotal();
        System.out.println("Melhor Custo: " + custoTotal);

    }
    
    public static Heuristics readArq(String arg) {
        try {
            FileReader arq = new FileReader(arg);
            BufferedReader lerArq = new BufferedReader(arq);
            int vertices = 0;

            String linha = lerArq.readLine();
            String format = new String();
            
            while(!linha.equals("EDGE_WEIGHT_SECTION")){
                if (linha.split(":")[0].replace(" ", "").equals("DIMENSION")) {
                    vertices = Integer.parseInt(linha.split(":")[1].replace(" ", ""));
                }
                if (linha.split(":")[0].replace(" ", "").equals("EDGE_WEIGHT_FORMAT")) {
                    format = linha.split(":")[1].replace(" ", "");
                }
                linha = lerArq.readLine();
            }
            //System.out.println(vertices + "-" + format);
            
            if (format.equals("UPPER_DIAG_ROW")) {
                return readArqSup(arq, vertices, lerArq);
            } else {
                return readArqInf(arq, vertices, lerArq);
            }
            
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
            return null;
        }
    }

    public static Heuristics readArqInf(FileReader arq, int vertices, BufferedReader lerArq) {
        try {
            String linha = lerArq.readLine();
            Heuristics grafo = new Heuristics(vertices);

            int row = 0;
            while (!linha.equals("DISPLAY_DATA_SECTION")) {
                for (int column = 0; column <= row; column++) {
                    grafo.insereArestaNaoOrientada(row, column, Integer.parseInt(linha.split(" ")[column]));
                    //System.out.print(linha.split(" ")[column] + " ");
                }
                //System.out.println("");
                row++;
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            
            arq.close();
            return grafo;
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
            return null;
        }
    }

    public static Heuristics readArqSup(FileReader arq, int vertices, BufferedReader lerArq) {
        try {
            String linha = lerArq.readLine();
            Heuristics grafo = new Heuristics(vertices);
            
            int row = 0;
            int aux = 0;
            int param = 16; //geralmente cada linha tem 16 numeros
            int column;
            while (row<vertices) {
                for (column = row; column < vertices; column++) { //triang. superior
                    grafo.insereArestaNaoOrientada(row, column, Integer.parseInt(linha.split(" ")[aux+1]));
                    
                    //System.out.print(linha.split(" ")[aux+1] + " ");
                    
                    if (linha.split(" ")[aux+1].equals("0")) { //quando tem numero 0 ele nao conta
                        param++;
                    }

                    aux++;
                    
                    if (aux == param) { //quando chegar no parametro le a proxima linha
                        linha = lerArq.readLine();
                        aux = 0;
                        param = 16;
                    }
                }
                //System.out.println("----------------------"+row);
                row++;
            }
            arq.close();
            return grafo;
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
            return null;
        }
    }

}
