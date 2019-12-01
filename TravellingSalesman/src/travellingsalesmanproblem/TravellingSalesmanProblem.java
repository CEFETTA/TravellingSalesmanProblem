package travellingsalesmanproblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TravellingSalesmanProblem {

    public static void main(String[] args) {
        Heuristics graph = readArqSup(args[0]);
        int cidadeInicial = 0;
        graph.iniciaGuloso(cidadeInicial);

        int[] caminho = graph.getVerticePai();

        for (int i = 0; i < caminho.length; i++) {
            System.out.print(caminho[i] + " ");
        }
        System.out.println("");

    }

    public static Heuristics readArqInf(String arg) {
        try {
            FileReader arq = new FileReader(arg);
            BufferedReader lerArq = new BufferedReader(arq);
            int vertices;

            String linha = lerArq.readLine();
            vertices = Integer.parseInt(linha.split(" ")[0]);

            Heuristics grafo = new Heuristics(vertices);
            linha = lerArq.readLine();

            int row = 0;
            while (linha != null) {
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

    public static Heuristics readArqSup(String arg) {
        try {
            FileReader arq = new FileReader(arg);
            BufferedReader lerArq = new BufferedReader(arq);
            int vertices;

            String linha = lerArq.readLine();
            vertices = Integer.parseInt(linha.split(" ")[0]);
            System.out.println(vertices);

            Heuristics grafo = new Heuristics(vertices);
            linha = lerArq.readLine();

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
