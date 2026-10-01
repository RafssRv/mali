import java.util.Vector;

// PROGRAMA = a main, a "chefe" que organiza todas as threads
public class Programa
{
    public static void main (String[] args)
    {
        try
        {
            // =====================================================
            // 1) PROCESSADORES
            // =====================================================

            // Descobre quantos processadores logicos a maquina possui
            // e deixa 1 de fora
            int qtdPedacos = Runtime.getRuntime().availableProcessors() - 1;

            // Se o resultado for menor que 1,
            // garante que pelo menos 1 pedaco sera usado
            if (qtdPedacos<1)
                qtdPedacos = 1;


            // =====================================================
            // 2) PERGUNTAS E PREENCHER O VETOR
            // =====================================================

            // Pergunta quantos elementos o vetor vai ter
            System.out.print ("Quantos elementos quer no vetor? ");
            int tamanho = Teclado.getUmInt ();

            // Nao aceita tamanho 0 ou negativo
            if (tamanho<=0)
                throw new Exception ("Tamanho invalido");


            // Pergunta se o usuario quer digitar
            // ou gerar os valores aleatoriamente
            System.out.print ("Quer (D)igitar ou (G)erar aleatoriamente? ");
            char opcao = Teclado.getUmChar ();


            // Cria o vetor com o tamanho informado
            int[] vetor = new int [tamanho];


            // Preenche o vetor
            for (int i=0; i<tamanho; i++)
            {
                // Se escolheu D, pede cada valor
                if (opcao=='D' || opcao=='d')
                {
                    System.out.print ("Elemento " + i + ": ");
                    vetor[i] = Teclado.getUmInt ();
                }

                // Senao gera numero aleatorio
                else
                    vetor[i] = (int)(Math.random()*1000000);
            }


            // Nao pode existir mais pedacos do que elementos
            // Exemplo: vetor com 3 elementos nao pode ter 7 pedacos
            if (qtdPedacos>tamanho)
                qtdPedacos = tamanho;


            // Comeca a contar o tempo da ordenacao
            long inicio = System.nanoTime ();


            // =====================================================
            // 3) DIVIDIR O VETOR E STARTAR AS ORDENADORAS
            // =====================================================

            // Quantidade minima de elementos que cada pedaco vai ter
            int base = tamanho/qtdPedacos;

            // Quantos elementos sobraram da divisao
            int resto = tamanho%qtdPedacos;


            // Cria um vetor para guardar todas as threads Ordenadora
            Ordenadora[] ordenadoras = new Ordenadora [qtdPedacos];


            // Guarda a posicao atual do vetor original
            int posicao = 0;


            // Cria cada pedaco
            for (int p=0; p<qtdPedacos; p++)
            {
                // Comeca com o tamanho base
                int tamPedaco = base;


                // Os primeiros pedacos recebem 1 elemento a mais
                // enquanto houver resto
                if (p<resto)
                    tamPedaco++;


                // Cria o pedaco
                int[] pedaco = new int [tamPedaco];


                // Copia os elementos do vetor original para o pedaco
                for (int i=0; i<tamPedaco; i++)
                {
                    pedaco[i] = vetor[posicao];
                    posicao++;
                }


                // Cria uma Ordenadora para esse pedaco
                ordenadoras[p] = new Ordenadora (pedaco);


                // Inicia a thread
                ordenadoras[p].start ();
            }


            // =====================================================
            // 4) ESPERAR AS ORDENADORAS E COLETAR
            // =====================================================

            // Espera todas as threads Ordenadora terminarem
            for (int p=0; p<qtdPedacos; p++)
                ordenadoras[p].join ();


            // Cria um Vector para guardar todos os pedacos ja ordenados
            Vector<int[]> pedacos = new Vector<int[]> ();


            // Pega o resultado de cada Ordenadora
            for (int p=0; p<qtdPedacos; p++)
                pedacos.add (ordenadoras[p].getVetorOrdenado ());


            // =====================================================
            // 5) RODADAS DE MISTURADORAS
            // =====================================================

            // Continua misturando enquanto existir mais de um pedaco
            while (pedacos.size()>1)
            {
                // Vector que recebe os resultados desta rodada
                Vector<int[]> resultados = new Vector<int[]> ();


                // Cada Misturadora trabalha com 2 pedacos
                int qtdMisturadoras = pedacos.size()/2;


                // Cria um vetor para guardar as threads Misturadora
                Misturadora[] misturadoras =
                    new Misturadora [qtdMisturadoras];


                // Cria e inicia as Misturadoras
                for (int m=0; m<qtdMisturadoras; m++)
                {
                    // Cada Misturadora recebe dois pedacos:
                    // 0 e 1
                    // 2 e 3
                    // 4 e 5
                    // ...
                    misturadoras[m] =
                        new Misturadora (
                            pedacos.get(2*m),
                            pedacos.get(2*m+1),
                            resultados
                        );

                    // Inicia a thread
                    misturadoras[m].start ();
                }


                // Se existir uma quantidade impar de pedacos,
                // o ultimo nao possui par
                // entao ele passa direto para a proxima rodada
                if (pedacos.size()%2==1)
                    resultados.add (
                        pedacos.get(pedacos.size()-1)
                    );


                // Espera todas as Misturadoras terminarem
                for (int m=0; m<qtdMisturadoras; m++)
                    misturadoras[m].join ();


                // Apenas mostra quantas Misturadoras foram usadas
                System.out.println (
                    "Rodada com " +
                    qtdMisturadoras +
                    " misturadora(s)"
                );


                // Os resultados desta rodada passam a ser
                // os pedacos da proxima rodada
                pedacos = resultados;
            }


            // =====================================================
            // 6) VETOR FINAL E TEMPO
            // =====================================================

            // Quando o while termina,
            // sobrou apenas um vetor:
            // o vetor final completamente ordenado
            int[] vetorFinal = pedacos.get(0);


            // Calcula quanto tempo passou
            // nanoTime retorna nanossegundos
            // divide por 1.000.000 para transformar em milissegundos
            double tempoEmMs =
                (System.nanoTime()-inicio)/1000000.0;


            // =====================================================
            // 7) MENU
            // =====================================================

            int escolha = 0;


            // Continua mostrando o menu ate o usuario escolher 3
            while (escolha!=3)
            {
                try
                {
                    System.out.println (
                        "1-Printar trecho  2-Printar tempo  3-Sair"
                    );

                    escolha = Teclado.getUmInt ();


                    // Opcao 1:
                    // imprime uma parte do vetor final
                    if (escolha==1)
                    {
                        System.out.print ("De qual posicao? ");
                        int de = Teclado.getUmInt ();

                        System.out.print ("Ate qual posicao? ");
                        int ate = Teclado.getUmInt ();


                        // Imprime da posicao "de" ate "ate"
                        for (int i=de; i<=ate; i++)
                            System.out.println (
                                "[" + i + "] " + vetorFinal[i]
                            );
                    }


                    // Opcao 2:
                    // mostra o tempo total da ordenacao
                    else if (escolha==2)
                        System.out.println (
                            "Tempo: " + tempoEmMs + " ms"
                        );
                }


                // Se o usuario escolher uma opcao invalida
                // ou uma posicao inexistente no vetor
                catch (Exception erro)
                {
                    System.err.println (
                        "Opcao ou posicao invalida!"
                    );
                }
            }
        }


        // Captura erros gerais do programa
        catch (Exception erro)
        {
            System.err.println (erro.getMessage());
        }
    }
}