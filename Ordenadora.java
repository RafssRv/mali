// ORDENADORA = thread que ordena UM pedaco do vetor
// usando Merge Sort recursivo
public class Ordenadora extends Thread
{
    // Vetor que essa thread vai ordenar
    private int[] vetor;

    // Controla se a ordenacao ja terminou
    // Comeca como false
    private boolean terminou = false;


    // CONSTRUTOR
    // Recebe o pedaco do vetor que essa thread vai ordenar
    public Ordenadora (int[] pdc) throws Exception
    {
        // Se nao recebeu nenhum vetor, da erro
        if (pdc==null)
            throw new Exception ("Pedaco ausente");

        // Guarda o pedaco recebido no atributo vetor
        this.vetor = pdc;
    }


    // RUN = o trabalho que a thread vai executar
    @Override
    public void run ()
    {
        // Ordena o vetor usando o mergeSort
        // E guarda o resultado novamente no atributo vetor
        this.vetor = this.mergeSort (this.vetor);

        // Marca que a thread terminou a ordenacao
        this.terminou = true;
    }


    // MERGE SORT
    // Recebe um vetor e devolve esse vetor ordenado
    private int[] mergeSort (int[] v)
    {
        // CASO BASE DA RECURSAO
        // Se o vetor tiver 0 ou 1 elemento,
        // ele ja esta ordenado
        if (v.length<=1)
            return v;


        // Descobre o meio do vetor
        int meio = v.length/2;


        // Cria o vetor da esquerda
        // Ele vai ter tamanho igual ao meio
        int[] esquerda = new int [meio];

        // Cria o vetor da direita
        // Ele recebe o restante dos elementos
        int[] direita = new int [v.length-meio];


        // Copia a primeira metade de v para esquerda
        for (int i=0; i<esquerda.length; i++)
            esquerda[i] = v[i];


        // Copia a segunda metade de v para direita
        for (int i=0; i<direita.length; i++)
            direita[i] = v[meio+i];


        // RECURSAO
        // Ordena a metade esquerda usando o proprio mergeSort
        esquerda = this.mergeSort (esquerda);

        // Ordena a metade direita usando o proprio mergeSort
        direita = this.mergeSort (direita);


        // Agora que esquerda e direita estao ordenadas,
        // chama a Misturadora para juntar as duas
        // e devolver um unico vetor ordenado
        return Misturadora.misture (esquerda, direita);
    }


    // Metodo usado pela main para pegar o vetor ja ordenado
    public int[] getVetorOrdenado () throws Exception
    {
        // Se a thread ainda nao terminou,
        // nao pode pegar o resultado
        if (!this.terminou)
            throw new Exception ("A ordenacao ainda nao terminou");


        // Cria uma copia com o mesmo tamanho do vetor ordenado
        int[] copia = new int [this.vetor.length];


        // Copia todos os elementos do vetor original para copia
        for (int i=0; i<this.vetor.length; i++)
            copia[i] = this.vetor[i];


        // Devolve a copia do vetor ordenado
        return copia;
    }
}